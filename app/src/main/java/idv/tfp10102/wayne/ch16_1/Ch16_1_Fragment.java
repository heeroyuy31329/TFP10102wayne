package idv.tfp10102.wayne.ch16_1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import idv.tfp10102.wayne.MainActivity;
import idv.tfp10102.wayne.R;

public class Ch16_1_Fragment extends Fragment {
    private MainActivity mainActivity;

    private Button buttonL, buttonR;
    private Ch16_1_MyView myView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainActivity = (MainActivity)getActivity();
        View view = inflater.inflate(R.layout.fragment_ch16_1, container, false);

        buttonL = view.findViewById(R.id.ch16_1_btnLeft);
        buttonR = view.findViewById(R.id.ch16_1_btnRight);
        myView = view.findViewById(R.id.ch16_1_myView);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonL.setOnClickListener(v -> {
            changeMyView(-1);
        });

        buttonR.setOnClickListener(v -> {
            changeMyView(1);
        });
    }

    private void changeMyView(int dir) {
        // 設定新的位置
        myView.setOffset(myView.getOffset() + (dir * 10));

        // 重畫
        myView.invalidate();
    }
}