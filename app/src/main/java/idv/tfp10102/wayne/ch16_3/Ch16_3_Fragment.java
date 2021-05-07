package idv.tfp10102.wayne.ch16_3;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import idv.tfp10102.wayne.MainActivity;
import idv.tfp10102.wayne.R;

public class Ch16_3_Fragment extends Fragment {
    MainActivity mainActivity;

    private TextView tvAction, tvPointCount, tvCoordinates, tvClickEvent;
    private Ch16_3_ClickView clickView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();

        View view = inflater.inflate(R.layout.fragment_ch16_3, container, false);

        tvAction = view.findViewById(R.id.ch16_3_textAction);
        tvPointCount = view.findViewById(R.id.ch16_3_textPointCount);
        tvCoordinates = view.findViewById(R.id.ch16_3_textCoordinate);
        tvClickEvent = view.findViewById(R.id.ch16_3_textClick);
        clickView = view.findViewById(R.id.ch16_3_clickView);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        clickView.setOnTouchListener(new View.OnTouchListener() {
            // 用來判斷使否為按下狀態
            boolean isTouchDown = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 取得動作訊息
                int action = event.getAction();

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        tvClickEvent.setText("");
                        tvAction.setText(R.string.ch16_3_text_down);
                        isTouchDown = true;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        tvClickEvent.setText("");
                        tvAction.setText(R.string.ch16_3_text_move);
                        isTouchDown = false;    // 移動及判斷為非按下
                        break;
                    case MotionEvent.ACTION_UP:
                        tvClickEvent.setText("");
                        tvAction.setText(R.string.ch16_3_text_up);
                        if (isTouchDown) {
                            // 觸發點擊事件
                            clickView.performClick();
                            isTouchDown = false;
                        }
                        break;
                    default:
                        tvClickEvent.setText("");
                        tvAction.setText("");
                        isTouchDown = false;
                        break;
                }

                // 取得點擊數量
                int pointCount = event.getPointerCount();
                tvPointCount.setText(String.valueOf(pointCount));

                // 取得點擊座標(多點)
                StringBuilder textCoordinate = new StringBuilder();
                for (int index = 0; index < pointCount; index++) {
                    textCoordinate.append("P").append(index)
                            .append("(")
                            .append(event.getX(index))  // 取得Ｘ座標
                            .append(", ")
                            .append(event.getY(index))  // 取得Ｙ座標
                            .append(")\n");
                }
                tvCoordinates.setText(textCoordinate);

                return true;
            }
        });

        clickView.setOnClickListener(v -> tvClickEvent.setText(R.string.ch16_3_text_clickEvent));
    }
}