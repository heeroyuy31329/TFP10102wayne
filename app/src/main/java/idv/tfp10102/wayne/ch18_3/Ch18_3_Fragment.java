package idv.tfp10102.wayne.ch18_3;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import idv.tfp10102.wayne.MainActivity;
import idv.tfp10102.wayne.R;

public class Ch18_3_Fragment extends Fragment {
    private MainActivity activity;

    private RadioGroup animationRadio, interpolatorRadio;
    private ImageView imageView;
    private Animator animator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (MainActivity)getActivity();

        View view = inflater.inflate(R.layout.fragment_ch18_3, container, false);

        animationRadio = view.findViewById(R.id.ch18_3_animationRadioGroup);
        interpolatorRadio = view.findViewById(R.id.ch18_3_interpolatorRadioGroup);

        imageView = view.findViewById(R.id.ch18_3_imageView);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 動畫的RadioGroup
        animationRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (animator != null) {
                    animator.cancel();
                    animator.removeAllListeners();
                }

                if (checkedId == R.id.ch18_3_tranRadioButton) {
                    animator = AnimatorInflater.loadAnimator(activity, R.animator.animator_translate);
                } else if (checkedId == R.id.ch18_3_scaleRadioButton) {
                    animator = AnimatorInflater.loadAnimator(activity, R.animator.animator_scale);
                } else if (checkedId == R.id.ch18_3_rotateRadioButton) {
                    animator = AnimatorInflater.loadAnimator(activity, R.animator.animator_rotate);
                } else if (checkedId == R.id.ch18_3_alphaRadioButton) {
                    animator = AnimatorInflater.loadAnimator(activity, R.animator.animator_alpha);
                } else if (checkedId == R.id.ch18_3_colorRadioButton) {
                    animator = AnimatorInflater.loadAnimator(activity, R.animator.animator_color);
                }

                // 註冊監聽器
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        Toast.makeText(activity, "Start", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Toast.makeText(activity, "End", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

                // 播動畫
                animator.setTarget(imageView);
                animator.start();
            }
        });

        // 特效的RadioGroup
        interpolatorRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (animator == null) {
                    return;
                }

                // 切換特效前須先停止後再播放
                animator.cancel();

                if (checkedId == R.id.ch18_3_linearRadioButton) {
                    animator.setInterpolator(new LinearInterpolator());
                } else if (checkedId == R.id.ch18_3_accelerateRadioButton) {
                    animator.setInterpolator(new AccelerateInterpolator());
                } else if (checkedId == R.id.ch18_3_decelerateRadioButton) {
                    animator.setInterpolator(new DecelerateInterpolator());
                } else if (checkedId == R.id.ch18_3_accelerateDecelerateRadioButton) {
                    animator.setInterpolator(new AccelerateDecelerateInterpolator());
                } else if (checkedId == R.id.ch18_3_anticipateRadioButton) {
                    animator.setInterpolator(new AnticipateInterpolator());
                } else if (checkedId == R.id.ch18_3_overshootRadioButton) {
                    animator.setInterpolator(new OvershootInterpolator());
                } else if (checkedId == R.id.ch18_3_anticipateOvershootRadioButton) {
                    animator.setInterpolator(new AnticipateOvershootInterpolator());
                } else if (checkedId == R.id.ch18_3_bounceRadioButton) {
                    animator.setInterpolator(new BounceInterpolator());
                } else if (checkedId == R.id.ch18_3_cycleRadioButton) {
                    animator.setInterpolator(new CycleInterpolator(3));
                }

                animator.start();
            }
        });
    }
}