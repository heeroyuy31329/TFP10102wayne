package idv.tfp10102.wayne.ch29;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import idv.tfp10102.wayne.MainActivity;
import idv.tfp10102.wayne.R;

public class Ch29_Fragment extends Fragment {
    MainActivity activity;

    Button btnDate, btnTime, btnSubmit;
    TextView txtDate, txtTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();

        return inflater.inflate(R.layout.fragment_ch29, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnDate = view.findViewById(R.id.ch29_btnDate);
        btnTime = view.findViewById(R.id.ch29_btnTime);
        btnSubmit = view.findViewById(R.id.ch29_btnSubmit);
        txtDate = view.findViewById(R.id.ch29_txtDate);
        txtTime = view.findViewById(R.id.ch29_txtTime);

        // 挑選日期
        btnDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    activity,
                    (view1, year, month, dayOfMonth) -> {
                        String date = year + "/" + (month + 1) + "/" + dayOfMonth;
                        txtDate.setText(date);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );

            // 透過DatePicker來設定可選的日期範圍
            DatePicker datePicker = datePickerDialog.getDatePicker();
            // 設定最小日期
            datePicker.setMinDate(calendar.getTimeInMillis());
            // 設定最大日期
            calendar.add(Calendar.MONTH, 2);
            datePicker.setMaxDate(calendar.getTimeInMillis());

            // 顯示DatePickerDialog
            datePickerDialog.show();
        });

        // 挑選時間
        btnTime.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    activity,
                    (view12, hourOfDay, minute) -> {
                        String time = hourOfDay + ":" + minute;
                        txtTime.setText(time);
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
            );

            timePickerDialog.show();
        });

        // 處理AlertDialog的按鈕
        DialogInterface.OnClickListener listener = (dialog, which) -> {
            switch (which) {
                // 確定
                case DialogInterface.BUTTON_POSITIVE:
                    Toast.makeText(activity, "成功送出!", Toast.LENGTH_SHORT).show();
                    break;
                // 否定/不決定
                case DialogInterface.BUTTON_NEGATIVE:
                case DialogInterface.BUTTON_NEUTRAL:
                    dialog.cancel();
                    break;
            }
        };

        // 送出
        btnSubmit.setOnClickListener(v -> new AlertDialog.Builder(activity)
                .setTitle("送出")
                .setMessage(txtDate.getText().toString() + " " + txtTime.getText().toString())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("是", listener)
                .setNegativeButton("否", listener)
                .setNeutralButton("不決定", listener)
                .show());
    }
}