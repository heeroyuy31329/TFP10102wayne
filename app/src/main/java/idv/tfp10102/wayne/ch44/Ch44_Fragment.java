package idv.tfp10102.wayne.ch44;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;

import idv.tfp10102.wayne.MainActivity;
import idv.tfp10102.wayne.R;

public class Ch44_Fragment extends Fragment {
    // 取得單一權限
    private final ActivityResultLauncher<String> checkPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            result -> {
                // result代表有無授權
                if (result) {
                    showMyLocation();
                }
            }
    );

    // 取得多個權限
    private  final ActivityResultLauncher<String[]> checkMultiPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(),
            result -> {
                // 取得多個權限是回傳Map<String, Boolean> result
                Log.d("wayne_permissions", result.get(Manifest.permission.ACCESS_FINE_LOCATION) + "");
                Log.d("wayne_permissions", result.get(Manifest.permission.ACCESS_COARSE_LOCATION) + "");
            }
    );

    MainActivity activity;

    MapView mapView;
    GoogleMap googleMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();

        return inflater.inflate(R.layout.fragment_ch44, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = view.findViewById(R.id.ch44_map);

        // 檢查權限
        requestPermissions();

        // 初始化地圖
        mapView.onCreate(savedInstanceState);
        mapView.onStart();
        mapView.getMapAsync(googleMap -> {
            this.googleMap = googleMap;
            // 加入自訂的訊息視窗
            googleMap.setInfoWindowAdapter(new Ch44_2_InfoWindow(activity));

            // 長按地圖，加入標記
            googleMap.setOnMapLongClickListener(latLng -> {
                //地圖標記
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(latLng)
                        .title("Marker")
                        .snippet(latLngToName(latLng.latitude, latLng.longitude))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                        .draggable(true);

                googleMap.addMarker(markerOptions);
            });

            // 長按訊息視窗，刪除標記
            googleMap.setOnInfoWindowLongClickListener(marker -> marker.remove());

            showMyLocation();
        });
    }

    // 檢查定位權限是否開啟
    private void requestPermissions() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);

        if (result != PackageManager.PERMISSION_GRANTED) {
            // 權限未開啟則跳出詢問權限
            // 要求單一權限
            checkPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);

            // 要求多個權限
//            checkMultiPermissionLauncher.launch(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});
        }
    }

    // 顯示現在位置的小藍點 + 移動攝影機到現在位置
    private void showMyLocation() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        // 顯示現在位置的小藍點
        googleMap.setMyLocationEnabled(true);

        // 取得現在位置
        FusedLocationProviderClient fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(activity);

        // 取得最新位置
        Task<Location> task = fusedLocationProviderClient.getCurrentLocation(
                LocationRequest.PRIORITY_HIGH_ACCURACY,
                new CancellationTokenSource().getToken());

        task.addOnSuccessListener(location -> {
            if (location != null) {
                // 這邊只是為了移動攝影機才要取得位置，map上小藍點的位置我們無法控制
                double lat = location.getLatitude();
                double lnt = location.getLongitude();

                // 建立CameraPosition和CameraUpdate
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(lat, lnt))
                        .zoom(18)
                        .build();

                CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);

                googleMap.moveCamera(cameraUpdate);
            }
        });
    }

    /**
     * 緯經度 轉 地名/地址
     */
    private String latLngToName(final double lat, final double lng) {
        try {
            if (!Geocoder.isPresent()) {
                return null;
            }
            Geocoder geocoder = new Geocoder(activity);
            List<Address> addressList = geocoder.getFromLocation(lat, lng, 1);
            StringBuilder name = new StringBuilder();
            Address address = addressList.get(0);
            if (address != null) {
                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    name.append(address.getAddressLine(i))
                            .append("\n");
                }
            }
            return name.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}