package idv.tfp10102.wayne.ch10;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import idv.tfp10102.wayne.R;

public class Ch10 {
    private Button btn;

    private List<View> imgList;
    private ImageView iv0, iv1, iv2, iv3, iv4, iv5;
    private View.OnClickListener listener = (v) -> {
        // 隱藏，會佔位
        v.setVisibility(View.INVISIBLE);

        // 把隱藏的存起來，方便後續顯示
        imgList.add(v);
    };

    public Ch10(Activity activity) {
        // 加入畫面
        activity.setContentView(R.layout.ch10_layout);

        imgList = new ArrayList<>();

        // 取得圖片元件
        iv0 = activity.findViewById(R.id.ch10Image0);
        iv1 = activity.findViewById(R.id.ch10Image1);
        iv2 = activity.findViewById(R.id.ch10Image2);
        iv3 = activity.findViewById(R.id.ch10Image3);
        iv4 = activity.findViewById(R.id.ch10Image4);
        iv5 = activity.findViewById(R.id.ch10Image5);

        // 圖片點擊事件
        iv0.setOnClickListener(listener);
        iv1.setOnClickListener(listener);
        iv2.setOnClickListener(listener);
        iv3.setOnClickListener(listener);
        iv4.setOnClickListener(listener);
        iv5.setOnClickListener(listener);

        // 重置按鈕處理
        btn = activity.findViewById(R.id.ch10Button);
        btn.setOnClickListener((v) -> {
            for (View view : imgList) {
                view.setVisibility(View.VISIBLE);
            }
            // 全部顯示完後清空
            imgList.clear();
        });
    }
}

