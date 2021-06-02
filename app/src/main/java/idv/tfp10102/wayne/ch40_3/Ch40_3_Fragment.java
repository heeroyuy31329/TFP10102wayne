package idv.tfp10102.wayne.ch40_3;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import idv.tfp10102.wayne.MainActivity;
import idv.tfp10102.wayne.R;

public class Ch40_3_Fragment extends Fragment {
    MainActivity activity;

    EditText editStart, editEnd;
    Button btnCalculate;
    TextView textResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();

        return inflater.inflate(R.layout.fragment_ch40_3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editStart = view.findViewById(R.id.ch40_3_editTextStart);
        editEnd = view.findViewById(R.id.ch40_3_editTextEnd);
        btnCalculate = view.findViewById(R.id.ch40_3_btnCalculate);
        textResult = view.findViewById(R.id.ch40_3_textResult);

        // 檢查Geocoder是否可用
        if (!Geocoder.isPresent()) {
            btnCalculate.setEnabled(false);
            return;
        }

        // 實例化Geocoder
        Geocoder geocoder = new Geocoder(activity);

        // 計算距離
        btnCalculate.setOnClickListener(v -> {
            String start = editStart.getText().toString();
            String end = editEnd.getText().toString();

            if (start.isEmpty() || end.isEmpty()) {
                Toast.makeText(activity, R.string.ch40_3_text_pleaseInput, Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                // 計算起點緯經度
                double latStart = 0.0;
                double lntStart = 0.0;

                List<Address> addressListStart = geocoder.getFromLocationName(start, 1);
                Address addressStart = addressListStart.get(0);
                if (addressStart != null) {
                    latStart = addressStart.getLatitude();
                    lntStart = addressStart.getLongitude();
                }

                // 計算終點緯經度
                double latEnd = 0.0;
                double lntEnd = 0.0;

                List<Address> addressListEnd = geocoder.getFromLocationName(end, 1);
                Address addressEnd = addressListEnd.get(0);
                if (addressEnd != null) {
                    latEnd = addressEnd.getLatitude();
                    lntEnd = addressEnd.getLongitude();
                }

                // 計算距離
                float[] destance = new float[1];
                Location.distanceBetween(latStart, lntStart, latEnd, lntEnd, destance);
                textResult.setText(String.valueOf(destance[0])); // 單位為公尺

            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }
}