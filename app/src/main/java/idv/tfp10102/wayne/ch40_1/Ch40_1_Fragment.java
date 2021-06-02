package idv.tfp10102.wayne.ch40_1;

import android.Manifest;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import idv.tfp10102.wayne.MainActivity;
import idv.tfp10102.wayne.R;

public class Ch40_1_Fragment extends Fragment {
    private static final int REQ_POSITIONING = 1;
    private static final int REQ_LOCATION_SETTINGS = 2;

    FusedLocationProviderClient fusedLocationClient;
    LocationRequest locationRequest;
    LocationCallback locationCallback;

    private MainActivity activity;

    Button btnPositioning;
    TextView textResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();

        return inflater.inflate(R.layout.fragment_ch40_1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 取得畫面上的元件
        btnPositioning = view.findViewById(R.id.ch40_1_btn);
        textResult = view.findViewById(R.id.ch40_1_textView);

        btnPositioning.setOnClickListener(v -> {
            // 檢查定位功能是否開啟
            checkPositioning();

            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            // 取得定位供應器物件
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
            // 取得最後位置
            Task<Location> task = fusedLocationClient.getLastLocation();
//            // 取得最新位置
//            Task<Location> task = fusedLocationClient.getCurrentLocation(
//                    LocationRequest.PRIORITY_HIGH_ACCURACY,
//                    new CancellationTokenSource().getToken()
//            );

            task.addOnSuccessListener(location -> {
                if (location != null) {
                    // 緯度
                    double latitude = location.getLatitude();
                    // 精度
                    double longitude = location.getLongitude();

                    StringBuilder string = new StringBuilder()
                            .append("緯度：").append(latitude).append("\n")
                            .append("緯度：").append(longitude);

                    textResult.setText(string);
                    autoUpdatePosition();
                }
            });


        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // 檢查定位權限是否開啟
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);

        if (result != PackageManager.PERMISSION_GRANTED) {
            // 權限未開啟則跳出詢問權限
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQ_POSITIONING);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQ_POSITIONING) {
            // 只檢查一個權限，所以直接抓第0個
            btnPositioning.setEnabled(grantResults[0] == PackageManager.PERMISSION_GRANTED);
        }
    }

    private void checkPositioning() {
        // 建立定位請求物件
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // 建立定位設定物件
        LocationSettingsRequest locationSettingsRequest = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .build();

        // 抓取手機的設定
        SettingsClient settingsClient = LocationServices.getSettingsClient(activity);

        // 檢查是否開啟定位設定
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(locationSettingsRequest);

        // 加入失敗監聽器，當定位功能沒開啟時，讓其跳至設定畫面
        task.addOnFailureListener(e -> {
            if (e instanceof ResolvableApiException) {
                try {
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    resolvable.startResolutionForResult(activity, REQ_LOCATION_SETTINGS);
                } catch (IntentSender.SendIntentException sendIntentException) {
                    sendIntentException.printStackTrace();
                }
            }
        });
    }

    // 自動更新位置
    void autoUpdatePosition() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                // 取得現在的位置
                Location location = locationResult.getLastLocation();
                // 緯度
                double latitude = location.getLatitude();
                // 精度
                double longitude = location.getLongitude();
                // 時間
                long time = location.getTime();

                StringBuilder string = new StringBuilder()
                        .append("緯度：").append(latitude).append("\n")
                        .append("緯度：").append(longitude).append("\n")
                        .append("時間：").append(time);

                textResult.setText(string);
            }
        };

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    @Override
    public void onPause() {
        super.onPause();

        if (fusedLocationClient != null && locationCallback != null) {
            // 停止更新
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }
}