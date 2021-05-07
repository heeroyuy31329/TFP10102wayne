package idv.tfp10102.wayne.ch11;

import android.app.Activity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import idv.tfp10102.wayne.R;

public class Ch11 {
    private TextView infoText;

    public Ch11(Activity activity) {
        // 加入畫面
        activity.setContentView(R.layout.ch11_layout);

        // 顯示訊息
        infoText = activity.findViewById(R.id.ch11_infoText);
        infoText.setText(R.string.ch11_text_start);

        // Button
        Button btn = activity.findViewById(R.id.ch11_button);
        btn.setOnClickListener((view) -> {
            infoText.setText(activity.getResources().getString(R.string.ch11_btn_message) + btn.getText());
        });

        // ImageButton
        ImageButton imageButton = activity.findViewById(R.id.ch11_imageButton);
        imageButton.setOnClickListener((view) -> {
            infoText.setText(R.string.ch11_btn_submit);
        });

        // RadioGroup
        RadioGroup radioGroup = activity.findViewById(R.id.ch11_radioGroup);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = group.findViewById(checkedId);
            infoText.setText(activity.getResources().getString(R.string.ch11_btn_message) + radioButton.getText());
        });

        // CheckBox
        CompoundButton.OnCheckedChangeListener listener = (checkBox, checkedId) -> {
            infoText.setText(activity.getResources().getString(R.string.ch11_btn_message) + checkBox.getText());
        };

        CheckBox checkBoxFire = activity.findViewById(R.id.ch11_checkBox_fire);
        CheckBox checkBoxWtaer = activity.findViewById(R.id.ch11_checkBox_water);
        CheckBox checkBoxWind = activity.findViewById(R.id.ch11_checkBox_wind);
        CheckBox checkBoxEarth = activity.findViewById(R.id.ch11_checkBox_earth);

        checkBoxFire.setOnCheckedChangeListener(listener);
        checkBoxWtaer.setOnCheckedChangeListener(listener);
        checkBoxWind.setOnCheckedChangeListener(listener);
        checkBoxEarth.setOnCheckedChangeListener(listener);

        // ToggleButton
        ToggleButton toggleButton = activity.findViewById(R.id.ch11_toggleButton);
        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int colorId = isChecked ? R.color.light : R.color.dark;
            ConstraintLayout rootLayout = activity.findViewById(R.id.ch11_rootLayout);
            rootLayout.setBackgroundColor(activity.getResources().getColor(colorId));

            colorId = isChecked ? R.color.dark : R.color.light;
            infoText.setTextColor(activity.getResources().getColor(colorId));

            String text = isChecked ? String.valueOf(toggleButton.getTextOn()) : String.valueOf(toggleButton.getTextOff());
            infoText.setText(activity.getResources().getString(R.string.ch11_btn_message) + text);
        });

        // SwitchCompat
        SwitchCompat switchCompat = activity.findViewById(R.id.ch11_switchCompat);
        switchCompat.setOnCheckedChangeListener((switchView, checked) -> {
            CharSequence text = checked ? switchCompat.getTextOn() : switchCompat.getTextOff();
            infoText.setText(switchCompat.getText() + " : " + text);
        });
    }

}
