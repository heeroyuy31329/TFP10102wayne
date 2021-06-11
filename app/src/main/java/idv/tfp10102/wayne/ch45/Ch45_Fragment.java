package idv.tfp10102.wayne.ch45;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import idv.tfp10102.wayne.MainActivity;
import idv.tfp10102.wayne.R;

public class Ch45_Fragment extends Fragment {
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

    private MainActivity activity;

    private MapView mapView;
    private CheckBox cbFill;

    private GoogleMap googleMap;

    private List<LatLng> pointList;
    private Polyline polyline;
    private Polygon polygon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pointList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();

        return inflater.inflate(R.layout.fragment_ch45, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = view.findViewById(R.id.ch45_map);
        cbFill = view.findViewById(R.id.ch45_cbFill);

        // 檢查權限
        requestPermissions();

        // 初始化地圖
        mapView.onCreate(savedInstanceState);
        mapView.onStart();
        mapView.getMapAsync(googleMap -> {
            this.googleMap = googleMap;
            googleMap.setOnMapLongClickListener(latLng -> {
                addMarker(latLng);
                drawCircle(latLng);
                pointList.add(latLng);

                if (cbFill.isChecked()) {
                    drawPolygon();
                } else {
                    drawPolyline();
                }
            });

            showMyLocation();
        });

        cbFill.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (polyline != null) {
                    polyline.remove();
                }
                drawPolygon();
            } else {
                if (polygon != null) {
                    polygon.remove();
                }
                drawPolyline();
            }
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

    private void addMarker(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title("marker")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));

        googleMap.addMarker(markerOptions);
    }

    // 畫連續線
    private void drawPolyline() {
        PolylineOptions polylineOptions = new PolylineOptions()
                .width(10)          // 3. 設定線寬
                .color(Color.CYAN)  // 4. 設定線色
                .zIndex(3);         // 5. 設定Z軸高低

        // 加入地圖
        polyline = googleMap.addPolyline(polylineOptions);

        // 加入點
        polyline.setPoints(pointList);
    }

    // 畫多邊形
    private void drawPolygon() {
        PolygonOptions polygonOptions = new PolygonOptions()
                .add(new LatLng(0, 0))    // 2. 加入點。邏輯上不應加此行(因為加入點的動作交給了Polygon物件)，但不加會拋出例外
                .strokeWidth(10)                // 3. 設定線寬
                .strokeColor(Color.CYAN)        // 4. 設定線色
                .fillColor(Color.argb(170, 236, 211, 208)); // 5. 設定填滿色

        // 加入地圖
        polygon = googleMap.addPolygon(polygonOptions);

        // 加入點
        if (!pointList.isEmpty()) {
            polygon.setPoints(pointList);
        }
    }

    // 畫圓
    private void drawCircle(LatLng latLng) {
        CircleOptions circleOptions = new CircleOptions()
                .center(latLng)     // 2. 設定圓心
                .radius(100)        // 3. 設定半徑(公尺)
                .strokeWidth(3)     // 4. 設定線寬
                .strokeColor(Color.rgb(239, 119, 220))  // 5. 設定線色
                .fillColor(Color.argb(170, 236, 211, 208));  // 6. 設定填滿色

        googleMap.addCircle(circleOptions);
    }
}