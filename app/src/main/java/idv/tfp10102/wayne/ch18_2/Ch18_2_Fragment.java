package idv.tfp10102.wayne.ch18_2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Map;

import idv.tfp10102.wayne.MainActivity;
import idv.tfp10102.wayne.R;

public class Ch18_2_Fragment extends Fragment {
    MainActivity activity;

    CheckBox cbTranslate, cbRotate, cbScale, cbAlpha;
    ImageView imageView;

    Map<Integer, Animation> animationMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (MainActivity)getActivity();

        View view = inflater.inflate(R.layout.fragment_ch18_2, container, false);

        cbTranslate = view.findViewById(R.id.ch18_2_checkBoxTranslate);
        cbRotate = view.findViewById(R.id.ch18_2_checkBoxRotate);
        cbScale = view.findViewById(R.id.ch18_2_checkBoxScale);
        cbAlpha = view.findViewById(R.id.ch18_2_checkBoxAlpha);
        imageView = view.findViewById(R.id.ch18_2_imageView);

        animationMap = new HashMap<>();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CompoundButton.OnCheckedChangeListener listener = (buttonView, isChecked) -> {
            final int resId = buttonView.getId();
            if (isChecked) {
                // 選到的存入animationMap中
                Animation animation = null;
                if (resId == R.id.ch18_2_checkBoxTranslate) {
                    // 位移動畫
                    animation = new TranslateAnimation(imageView.getTranslationX(), imageView.getTranslationX() + 500,
                                                       imageView.getTranslationY(), imageView.getTranslationY() + 100);
                } else if (resId == R.id.ch18_2_checkBoxRotate) {
                    // 旋轉動畫
                    animation = new RotateAnimation(0, -180);
                } else if (resId == R.id.ch18_2_checkBoxScale) {
                    // 縮放動畫
                    animation = new ScaleAnimation(1, 1.5f, 1, 2);
                } else if (resId == R.id.ch18_2_checkBoxAlpha) {
                    // 透明度動畫
                    animation = new AlphaAnimation(1, 0.5f);
                }
                // 設定動畫參數
                animation.setDuration(1000);
                animation.setRepeatCount(Animation.INFINITE);
                animation.setRepeatMode(Animation.REVERSE);

                animationMap.put(resId, animation);
            } else {
                // 取消的從animationMap中移除
                animationMap.remove(resId);
            }

            // 開始播動畫
            AnimationSet animationSet = new AnimationSet(true);
            for (Integer resKey : animationMap.keySet()) {
                animationSet.addAnimation(animationMap.get(resKey));
            }
            imageView.startAnimation(animationSet);
        };

        // 處理CheckBox
        cbTranslate.setOnCheckedChangeListener(listener);
        cbRotate.setOnCheckedChangeListener(listener);
        cbScale.setOnCheckedChangeListener(listener);
        cbAlpha.setOnCheckedChangeListener(listener);
    }
}