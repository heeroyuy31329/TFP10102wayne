package idv.tfp10102.wayne.ch16_2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import idv.tfp10102.wayne.MainActivity;
import idv.tfp10102.wayne.R;

public class Ch16_2_Fragment extends Fragment {
    private MainActivity mainActivity;

    private Button addBtn;
    private TextView textCount;
    private LinearLayout linearLayout;

    int count;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        count = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainActivity = (MainActivity)getActivity();

        View view = inflater.inflate(R.layout.fragment_ch16_2, container, false);

        addBtn = view.findViewById(R.id.ch16_2_button);
        textCount = view.findViewById(R.id.ch16_2_textView);
        linearLayout = view.findViewById(R.id.ch16_2_linearLayout);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 按下按鈕後加入一個TextView
        addBtn.setOnClickListener(v -> {
            // 設定"元件"在"容器"中的屬性
            // 因為容器是LinearLayout，所以使用LinearLayout的參數設定
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            // 水平置中
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL;

            // 把設定設給元件
            TextView textView = new TextView(mainActivity);
            textView.setLayoutParams(layoutParams);

            // 元件加入到容器中
            linearLayout.addView(textView);

            // 設定顯示文字
            String countText = String.valueOf(++count);
            textView.setText(countText);
            textCount.setText(countText);
        });
    }
}