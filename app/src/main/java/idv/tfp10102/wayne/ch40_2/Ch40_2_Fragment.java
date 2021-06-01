package idv.tfp10102.wayne.ch40_2;

import android.location.Address;
import android.location.Geocoder;
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

public class Ch40_2_Fragment extends Fragment {
    MainActivity activity;

    EditText editAddr;
    Button btnGeocode;
    TextView textLatLng, textAddr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();

        return inflater.inflate(R.layout.fragment_ch40_2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editAddr = view.findViewById(R.id.ch40_2_editAddr);
        btnGeocode = view.findViewById(R.id.ch40_2_btnGeocode);
        textLatLng = view.findViewById(R.id.ch40_2_textLatLng);
        textAddr = view.findViewById(R.id.ch40_2_textAddr);

        // 檢查Geocoder是否可用
        if (!Geocoder.isPresent()) {
            btnGeocode.setEnabled(false);
            return;
        }

        // 實例化Geocoder
        Geocoder geocoder = new Geocoder(activity);

        // 送出後開始轉換
        btnGeocode.setOnClickListener(v -> {
            String inputText = editAddr.getText().toString();
            if (inputText.isEmpty()) {
                Toast.makeText(activity, R.string.ch40_2_text_PleaseInput, Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                // 地址轉換成緯經度
                StringBuilder strLatLnt = new StringBuilder();
                double lat = 0.0;
                double lnt = 0.0;

                List<Address> addressList = geocoder.getFromLocationName(inputText, 1);
                Address address = addressList.get(0);
                if (address != null) {
                    lat = address.getLatitude();
                    lnt = address.getLongitude();

                    strLatLnt.append("Lat: ").append(lat).append("\n")
                             .append("Lnt: ").append(lnt);
                }

                textLatLng.setText(strLatLnt);

                // 緯經度轉換成地址
                StringBuilder strAddress = new StringBuilder();

                List<Address> addressList2 = geocoder.getFromLocation(lat, lnt, 1);
                Address address2 = addressList2.get(0);
                if (address2 != null) {
                    // getMaxAddressLineIndex 是Index，所以判斷上會加等號
                    for (int i = 0; i <= address2.getMaxAddressLineIndex(); i++) {
                        strAddress.append(address2.getAddressLine(i)).append("\n");
                    }
                }
                textAddr.setText(strAddress);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}