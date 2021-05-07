package idv.tfp10102.wayne.ch18_1;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import idv.tfp10102.wayne.MainActivity;
import idv.tfp10102.wayne.R;

public class Ch18_1_Fragment extends Fragment {
    MainActivity activity;

    ImageView imageView;
    Button button;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();

        View view = inflater.inflate(R.layout.fragment_ch18_1, container, false);

        imageView = view.findViewById(R.id.ch18_1_ImageView);
        button = view.findViewById(R.id.ch18_1_Button);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 把圖片讀入成Drawable
        Drawable drawable1 = activity.getResources().getDrawable(R.drawable.android);
        Drawable drawable2 = activity.getResources().getDrawable(R.drawable.java);
        Drawable drawable3 = activity.getResources().getDrawable(R.drawable.linux);

        // 把圖片設定至動畫(AnimationDrawable)中
        AnimationDrawable animationDrawable = new AnimationDrawable();
        animationDrawable.addFrame(drawable1, 500);
        animationDrawable.addFrame(drawable2, 500);
        animationDrawable.addFrame(drawable3, 500);

        // 把動畫設定到ImageView中
        imageView.setBackground(animationDrawable);

        // 播放動畫
        button.setOnClickListener((v) -> {
            if (animationDrawable.isRunning()) {
                animationDrawable.stop();
                button.setText(R.string.ch18_1_text_start);
            } else {
                animationDrawable.start();
                button.setText(R.string.ch18_1_text_stop);
            }
        });
    }
}