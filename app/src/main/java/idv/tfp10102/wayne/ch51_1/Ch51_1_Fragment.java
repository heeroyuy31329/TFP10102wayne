package idv.tfp10102.wayne.ch51_1;

import android.content.Intent;
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

public class Ch51_1_Fragment extends Fragment {
    MainActivity activity;

    Button btnStart, btnStop;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();

        View view = inflater.inflate(R.layout.fragment_ch51_1, container, false);

        btnStart = view.findViewById(R.id.ch51_1_start);
        btnStop = view.findViewById(R.id.ch51_1_stop);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Intent intent = new Intent(activity, Ch51_1_MyService.class);

        btnStart.setOnClickListener(v -> activity.startService(intent));

        btnStop.setOnClickListener(v -> activity.stopService(intent));
    }
}