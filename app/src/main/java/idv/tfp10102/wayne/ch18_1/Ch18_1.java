package idv.tfp10102.wayne.ch18_1;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.widget.Button;
import android.widget.ImageView;

import idv.tfp10102.wayne.R;

public class Ch18_1 {

    public Ch18_1(Activity activity) {
        // 加入畫面
        activity.setContentView(R.layout.ch18_1_layout);

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
        ImageView imageView = activity.findViewById(R.id.ch18_1_ImageView);
        imageView.setBackground(animationDrawable);

        // 播放動畫
        Button btn = activity.findViewById(R.id.ch18_1_Button);
        btn.setOnClickListener((v) -> {
            if (animationDrawable.isRunning()) {
                animationDrawable.stop();
                btn.setText("Start");
            } else {
                animationDrawable.start();
                btn.setText("Stop");
            }
        });
    }
}
