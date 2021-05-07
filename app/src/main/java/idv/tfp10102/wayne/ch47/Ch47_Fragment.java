package idv.tfp10102.wayne.ch47;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;

import idv.tfp10102.wayne.MainActivity;
import idv.tfp10102.wayne.R;

public class Ch47_Fragment extends Fragment {
    private MainActivity activity;

    private TextView textViewAcc, textViewGyr, textViewOri, textViewPro, textViewLit;

    private SensorManager sensorManager;

    private float[] valuesAccelerometer, valuesMagnitude;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (MainActivity)getActivity();

        View view = inflater.inflate(R.layout.fragment_ch47, container, false);

        textViewAcc = view.findViewById(R.id.ch47_textViewAcc);
        textViewGyr = view.findViewById(R.id.ch47_textViewGyr);
        textViewOri = view.findViewById(R.id.ch47_textViewOri);
        textViewPro = view.findViewById(R.id.ch47_textViewPro);
        textViewLit = view.findViewById(R.id.ch47_textViewLit);

        sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 加速
        Sensor sensorAcc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(listener, sensorAcc, SensorManager.SENSOR_DELAY_NORMAL);

        // 陀螺
        Sensor sensorGyr = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(listener, sensorGyr, SensorManager.SENSOR_DELAY_NORMAL);

        // 方位(需使用加速度和磁場來配合)
        Sensor accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor magSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorManager.registerListener(listenerOri, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(listenerOri, magSensor, SensorManager.SENSOR_DELAY_NORMAL);

        // 接近
        Sensor sensorPro = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorManager.registerListener(listener, sensorPro, SensorManager.SENSOR_DELAY_NORMAL);

        // 光度
        Sensor sensorLit = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(listener, sensorLit, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        // 註銷監聽器
        sensorManager.unregisterListener(listener);
        sensorManager.unregisterListener(listenerOri);
    }

    private final SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            Sensor sensor = event.sensor;
            String name = sensor.getName();// 名稱
            int type = sensor.getType();// 類型
            float[] sensorValues = event.values;// 數值

            switch (type) {
                case Sensor.TYPE_ACCELEROMETER:
                    StringBuilder textAcc = new StringBuilder();
                    textAcc.append(getString(R.string.ch47_text_Accelerometer)).append("\n")
                            .append(name).append("\n")
                            .append(Arrays.toString(sensorValues));

                    textViewAcc.setText(textAcc);
                    break;
                case Sensor.TYPE_GYROSCOPE:
                    StringBuilder textGyr = new StringBuilder();
                    textGyr.append(getString(R.string.ch47_text_Gyroscope)).append("\n")
                            .append(name).append("\n")
                            .append(Arrays.toString(sensorValues));

                    textViewGyr.setText(textGyr);
                    break;
                case Sensor.TYPE_PROXIMITY:
                    StringBuilder textPro = new StringBuilder();
                    textPro.append(getString(R.string.ch47_text_Proximity)).append("\n")
                            .append(name).append("\n")
                            .append(Arrays.toString(sensorValues));

                    textViewPro.setText(textPro);
                    break;
                case Sensor.TYPE_LIGHT:
                    StringBuilder textLit = new StringBuilder();
                    textLit.append(getString(R.string.ch47_text_Light)).append("\n")
                            .append(name).append("\n")
                            .append(Arrays.toString(sensorValues));

                    textViewLit.setText(textLit);
                    break;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private final SensorEventListener listenerOri = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            Sensor sensor = event.sensor;
            String name = sensor.getName();// 名稱
            int type = sensor.getType();// 類型

            switch (type) {
                case Sensor.TYPE_ACCELEROMETER:
                    valuesAccelerometer = event.values.clone(); // 因為陣列是參考值，避免現在存的值會被動到，所以複製一份出來
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    valuesMagnitude = event.values.clone();
                    break;
            }

            // 一開始的時候沒有值，所以要做null檢查
            if (valuesAccelerometer == null || valuesMagnitude == null) {
                return;
            }

            // 做成計算所需的旋轉矩陣
            float[] rotateMatrix = new float[9];
            SensorManager.getRotationMatrix(rotateMatrix, null, valuesAccelerometer, valuesMagnitude);

            // 計算出現在的方位值
            float[] values = new float[3];
            SensorManager.getOrientation(rotateMatrix, values);

            StringBuilder textLit = new StringBuilder();
            textLit.append(getString(R.string.ch47_text_Orientation)).append("\n")
                    .append(name).append("\n")
                    .append(Arrays.toString(values));

            textViewOri.setText(textLit);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}