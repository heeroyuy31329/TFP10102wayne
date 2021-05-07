package idv.tfp10102.wayne.ch10;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import idv.tfp10102.wayne.MainActivity;
import idv.tfp10102.wayne.R;

public class Ch10_Fragment extends Fragment {
    MainActivity activity;

    private Button btn;

    private List<View> imgList;
    private ImageView iv0, iv1, iv2, iv3, iv4, iv5;
    private View.OnClickListener listener = (v) -> {
        // 隱藏，會佔位
        v.setVisibility(View.INVISIBLE);

        // 把隱藏的存起來，方便後續顯示
        imgList.add(v);
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_ch10, container, false);

        imgList = new ArrayList<>();

        // 取得圖片元件
        iv0 = view.findViewById(R.id.ch10Image0);
        iv1 = view.findViewById(R.id.ch10Image1);
        iv2 = view.findViewById(R.id.ch10Image2);
        iv3 = view.findViewById(R.id.ch10Image3);
        iv4 = view.findViewById(R.id.ch10Image4);
        iv5 = view.findViewById(R.id.ch10Image5);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
            for (View imgView : imgList) {
                imgView.setVisibility(View.VISIBLE);
            }
            // 全部顯示完後清空
            imgList.clear();
        });
    }
}