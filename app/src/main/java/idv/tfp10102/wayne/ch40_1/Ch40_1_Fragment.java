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

        // ????????????????????????
        btnPositioning = view.findViewById(R.id.ch40_1_btn);
        textResult = view.findViewById(R.id.ch40_1_textView);

        btnPositioning.setOnClickListener(v -> {
            // ??????????????????????????????
            checkPositioning();

            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            // ???????????????????????????
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
            // ??????????????????
            Task<Location> task = fusedLocationClient.getLastLocation();
//            // ??????????????????
//            Task<Location> task = fusedLocationClient.getCurrentLocation(
//                    LocationRequest.PRIORITY_HIGH_ACCURACY,
//                    new CancellationTokenSource().getToken()
//            );

            task.addOnSuccessListener(location -> {
                if (location != null) {
                    // ??????
                    double latitude = location.getLatitude();
                    // ??????
                    double longitude = location.getLongitude();

                    StringBuilder string = new StringBuilder()
                            .append("?????????").append(latitude).append("\n")
                            .append("?????????").append(longitude);

                    textResult.setText(string);
                    autoUpdatePosition();
                }
            });


        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // ??????????????????????????????
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);

        if (result != PackageManager.PERMISSION_GRANTED) {
            // ????????????????????????????????????
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQ_POSITIONING);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQ_POSITIONING) {
            // ??????????????????????????????????????????0???
            btnPositioning.setEnabled(grantResults[0] == PackageManager.PERMISSION_GRANTED);
        }
    }

    private void checkPositioning() {
        // ????????????????????????
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // ????????????????????????
        LocationSettingsRequest locationSettingsRequest = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .build();

        // ?????????????????????
        SettingsClient settingsClient = LocationServices.getSettingsClient(activity);

        // ??????????????????????????????
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(locationSettingsRequest);

        // ??????????????????????????????????????????????????????????????????????????????
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

    // ??????????????????
    void autoUpdatePosition() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                // ?????????????????????
                Location location = locationResult.getLastLocation();
                // ??????
                double latitude = location.getLatitude();
                // ??????
                double longitude = location.getLongitude();
                // ??????
                long time = location.getTime();

                StringBuilder string = new StringBuilder()
                        .append("?????????").append(latitude).append("\n")
                        .append("?????????").append(longitude).append("\n")
                        .append("?????????").append(time);

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
            // ????????????
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }
}