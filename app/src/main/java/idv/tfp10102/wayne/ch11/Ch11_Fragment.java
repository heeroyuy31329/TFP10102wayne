package idv.tfp10102.wayne.ch11;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import idv.tfp10102.wayne.MainActivity;
import idv.tfp10102.wayne.R;

public class Ch11_Fragment extends Fragment {
    MainActivity activity;

    private TextView infoText;
    Button button;
    ImageButton imageButton;
    RadioGroup radioGroup;
    CheckBox checkBoxFire;
    CheckBox checkBoxWater;
    CheckBox checkBoxWind;
    CheckBox checkBoxEarth;
    ToggleButton toggleButton;
    SwitchCompat switchCompat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();

        View view = inflater.inflate(R.layout.fragment_ch11, container, false);

        // 顯示訊息
        infoText = view.findViewById(R.id.ch11_infoText);

        // Button
        button = view.findViewById(R.id.ch11_button);

        // ImageButton
        imageButton = view.findViewById(R.id.ch11_imageButton);

        // RadioGroup
        radioGroup = view.findViewById(R.id.ch11_radioGroup);

        // CheckBox
        checkBoxFire = view.findViewById(R.id.ch11_checkBox_fire);
        checkBoxWater = view.findViewById(R.id.ch11_checkBox_water);
        checkBoxWind = view.findViewById(R.id.ch11_checkBox_wind);
        checkBoxEarth = view.findViewById(R.id.ch11_checkBox_earth);


        // ToggleButton
        toggleButton = view.findViewById(R.id.ch11_toggleButton);

        // SwitchCompat
        switchCompat = view.findViewById(R.id.ch11_switchCompat);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 顯示訊息
        infoText.setText(R.string.ch11_text_start);

        // Button
        button.setOnClickListener((buttonView) -> {
            infoText.setText(activity.getResources().getString(R.string.ch11_btn_message) + button.getText());
        });

        // ImageButton
        imageButton.setOnClickListener((buttonView) -> {
            infoText.setText(R.string.ch11_btn_submit);
        });

        // RadioGroup
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = group.findViewById(checkedId);
            infoText.setText(activity.getResources().getString(R.string.ch11_btn_message) + radioButton.getText());
        });

        // CheckBox
        CompoundButton.OnCheckedChangeListener listener = (checkBox, checkedId) -> {
            infoText.setText(activity.getResources().getString(R.string.ch11_btn_message) + checkBox.getText());
        };

        checkBoxFire.setOnCheckedChangeListener(listener);
        checkBoxWater.setOnCheckedChangeListener(listener);
        checkBoxWind.setOnCheckedChangeListener(listener);
        checkBoxEarth.setOnCheckedChangeListener(listener);

        // ToggleButton
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
        switchCompat.setOnCheckedChangeListener((switchView, checked) -> {
            CharSequence text = checked ? switchCompat.getTextOn() : switchCompat.getTextOff();
            infoText.setText(switchCompat.getText() + " : " + text);
        });
    }
}