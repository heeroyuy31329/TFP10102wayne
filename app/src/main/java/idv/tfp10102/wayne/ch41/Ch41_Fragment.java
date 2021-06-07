package idv.tfp10102.wayne.ch41;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.RelativeDateTimeFormatter;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import idv.tfp10102.wayne.MainActivity;
import idv.tfp10102.wayne.R;

public class Ch41_Fragment extends Fragment {
    private MainActivity activity;

    private static final String TAG = "TAG_MainActivity";
    private EditText etOri, etDest;
    private Button btLocationOri, btStreetViewOri, btLocationDest, btStreetViewDest, btDirect;
    private Geocoder geocoder;
    private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();

        return inflater.inflate(R.layout.fragment_ch41, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 抓元件
        etOri = view.findViewById(R.id.ch41_editStart);
        etDest = view.findViewById(R.id.ch41_editEnd);
        btLocationOri = view.findViewById(R.id.ch41_LocateStart);
        btStreetViewOri = view.findViewById(R.id.ch41_StreetStart);
        btLocationDest = view.findViewById(R.id.ch41_LocateEnd);
        btStreetViewDest = view.findViewById(R.id.ch41_StreetEnd);
        btDirect = view.findViewById(R.id.ch41_Direct);

        // 初始化相關套件
        // geocoder
        if (!Geocoder.isPresent()) {
            btLocationOri.setEnabled(false);
            btStreetViewOri.setEnabled(false);
            btLocationDest.setEnabled(false);
            btStreetViewDest.setEnabled(false);
            btDirect.setEnabled(false);
            return;
        }
        geocoder = new Geocoder(activity);

        // intent，打開google map app
        intent = new Intent(Intent.ACTION_VIEW);
        intent.setPackage("com.google.android.apps.maps");

        // 按鈕處理
        btLocationOri.setOnClickListener(v -> location(etOri.getText().toString()));
        btLocationDest.setOnClickListener(v -> location(etDest.getText().toString()));

        btStreetViewOri.setOnClickListener(v -> streetView(etOri.getText().toString()));
        btStreetViewDest.setOnClickListener(v -> streetView(etDest.getText().toString()));

        btDirect.setOnClickListener(v -> {
            Address addressStart = nameToLatLng(etOri.getText().toString());
            Address addressEnd = nameToLatLng(etDest.getText().toString());
            if (addressStart == null || addressEnd == null) {
                Toast.makeText(activity, "找不到地點", Toast.LENGTH_SHORT).show();
                return;
            }

            String uriString = String.format(Locale.TAIWAN,
                    "https://www.google.com/maps/dir/?api=1&origin=%f,%f&destination=%f,%f",
                    addressStart.getLatitude(), addressStart.getLongitude(),
                    addressEnd.getLatitude(), addressEnd.getLongitude());
            Uri uri = Uri.parse(uriString);
            intent.setData(uri);
            if (isIntentAvailable(intent)) {
                startActivity(intent);
            }
        });
    }

    // 顯示地點
    private void location (String locationName) {
        Address address = nameToLatLng(locationName);
        if (address == null) {
            Toast.makeText(activity, "找不到地點", Toast.LENGTH_SHORT).show();
            return;
        }

        double lat = address.getLatitude();
        double lnt = address.getLongitude();
        Uri uri = Uri.parse(String.format(Locale.TAIWAN, "geo:%f,%f", lat, lnt));
        intent.setData(uri);
        if (isIntentAvailable(intent)) {
            startActivity(intent);
        }
    }

    // 顯示街景
    private void streetView (String locationName) {
        Address address = nameToLatLng(locationName);
        if (address == null) {
            Toast.makeText(activity, "找不到地點", Toast.LENGTH_SHORT).show();
            return;
        }

        double lat = address.getLatitude();
        double lnt = address.getLongitude();
        Uri uri = Uri.parse(String.format(Locale.TAIWAN, "google.streetview:cbll=%f,%f", lat, lnt));
        intent.setData(uri);
        if (isIntentAvailable(intent)) {
            startActivity(intent);
        }
    }

    // 檢查是否有內建的Google Maps App
    private boolean isIntentAvailable (Intent intent) {
        PackageManager packageManager = activity.getPackageManager();
        return intent.resolveActivity(packageManager) != null;
    }

    // 地名/地址 轉 緯經度
    private Address nameToLatLng(String name) {
        try {
            List<Address> addressList = geocoder.getFromLocationName(name, 1);
            if (addressList != null && addressList.size() > 0) {
                return addressList.get(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}