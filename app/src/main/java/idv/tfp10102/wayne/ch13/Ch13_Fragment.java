package idv.tfp10102.wayne.ch13;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

import idv.tfp10102.wayne.MainActivity;
import idv.tfp10102.wayne.R;

public class Ch13_Fragment extends Fragment {
    MainActivity mainActivity;

    ProgressBar progressBar;

    SeekBar seekBar;
    TextView seekText;

    RatingBar ratingBar;
    TextView ratingText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainActivity = (MainActivity)getActivity();
        View view = inflater.inflate(R.layout.fragment_ch13, container, false);

        progressBar = view.findViewById(R.id.ch13_progressBar);
        seekBar = view.findViewById(R.id.ch13_seekBar);
        seekText = view.findViewById(R.id.ch13_seekText);
        ratingBar = view.findViewById(R.id.ch13_ratingBar);
        ratingText = view.findViewById(R.id.ch13_ratingText);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // progressBar，每0.2秒加1進度
        new Thread(() -> {
            int max = progressBar.getMax();
            int progress;
            while ((progress = progressBar.getProgress()) < max) {
                progressBar.setProgress(++progress);

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // seek bar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekText.setTextSize(Ch13_Fragment.this.seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        // rating bar
        ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            ratingText.setText(Ch13_Fragment.this.ratingBar.getRating() + "");
        });
    }
}