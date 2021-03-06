package idv.tfp10102.wayne;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashSet;
import java.util.Set;

import idv.tfp10102.wayne.ch44.Ch44_2_InfoWindow;

public class MainFragment_37_45 extends Fragment {
    MainActivity activity;

    static int REQ_AUDIO = 0;
    static int REQ_LOCATION = 1;
    Button volumeUp, volumeDown, volumePause;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        //ch37-3
//        volumeUp = view.findViewById(R.id.ch37_3_btnUp);
//        volumeDown = view.findViewById(R.id.ch37_3_btnDwn);
//        volumePause = view.findViewById(R.id.ch37_3_btnPause);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        // ch37-1
//        // ?????????????????????
//        String[] permissions = {Manifest.permission.RECORD_AUDIO};
//
//        // ??????????????????????????????
//        Set<String> permissionRequest = new HashSet<>();
//        for (String permission : permissions) {
//            int result = ContextCompat.checkSelfPermission(activity, permission);
//            if (result != PackageManager.PERMISSION_GRANTED) {
//                permissionRequest.add(permission);
//            }
//        }
//
//        // ??????????????????
//        if (!permissionRequest.isEmpty()) {
//            requestPermissions(
//                    permissionRequest.toArray(new String[]{}),
//                    REQ_AUDIO
//            );
//        }
//
//        //ch37-3
//        AudioManager audioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
//
//        volumeUp.setOnClickListener((btnView) -> {
//            audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
//        });
//
//        volumeDown.setOnClickListener((btnView) -> {
//            audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
//        });
//
//        volumePause.setOnClickListener((btnView) -> {
//            audioManager.adjustVolume(AudioManager.ADJUST_TOGGLE_MUTE, AudioManager.FLAG_SHOW_UI);
//        });

        // ch40-1
        // ?????????????????????
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        // ??????????????????????????????
        Set<String> permissionRequest = new HashSet<>();
        for (String permission : permissions) {
            int result = ContextCompat.checkSelfPermission(activity, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                permissionRequest.add(permission);
            }
        }

        // ??????????????????
        if (!permissionRequest.isEmpty()) {
            requestPermissions(
                    permissionRequest.toArray(new String[]{}),
                    REQ_AUDIO
            );
        }
//
//        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
//        // ??????????????????
////        Task<Location> task = fusedLocationClient.getLastLocation();
//        // ??????????????????
//        Task<Location> task = fusedLocationClient.getCurrentLocation(
//                LocationRequest.PRIORITY_HIGH_ACCURACY,
//                new CancellationTokenSource().getToken()
//        );
//
//        task.addOnSuccessListener(location -> {
//            if (location != null) {
//                // ????????????
//                double latitude = location.getLatitude();
//                // ????????????
//                double longitude = location.getLongitude();
//
//                StringBuilder text = new StringBuilder()
//                        .append("?????????").append(latitude).append("\n")
//                        .append("?????????").append(longitude);
//                TextView textView = activity.findViewById(R.id.ch40_1_textView);
//                textView.setText(text);
//            }
//        });
//
//        //????????????
//        LocationRequest locationRequest = LocationRequest.create();
//        locationRequest.setInterval(10000);
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//
//        LocationSettingsRequest locationSettingsRequest = new LocationSettingsRequest.Builder()
//                .addLocationRequest(locationRequest).build();
//
//        // ??????????????????(????????????)
//        SettingsClient settingsClient = LocationServices.getSettingsClient(activity);
//        Task<LocationSettingsResponse> locationRespTask = settingsClient.checkLocationSettings(locationSettingsRequest);
//        locationRespTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                if(e instanceof ResolvableApiException) {
//                    try {
//                        ResolvableApiException resolvable = (ResolvableApiException) e;
//                        // ?????????????????????????????????
//                        resolvable.startResolutionForResult(activity, REQ_LOCATION);
//                    } catch (IntentSender.SendIntentException sendIntentException) {
//                        sendIntentException.printStackTrace();
//                    }
//                }
//            }
//        });
//
//        // ????????????
//        LocationCallback locationCallback = new LocationCallback() {
//            @Override
//            public void onLocationResult(@NonNull LocationResult locationResult) {
//                Location location = locationResult.getLastLocation();
//
//                StringBuilder text = new StringBuilder()
//                        .append("??????111???").append(location.getLatitude()).append("\n")
//                        .append("??????111???").append(location.getLongitude());
//                TextView textView = activity.findViewById(R.id.ch40_1_textView);
//                textView.setText(text);
//            }
//        };
//
//        // ??????????????????
//        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
//
//        // ??????????????????
////        fusedLocationClient.removeLocationUpdates(locationCallback);

        // 40-2
//        EditText addrEdit = activity.findViewById(R.id.ch40_2_address);
//        EditText latEdit = activity.findViewById(R.id.ch40_2_latitude);
//        EditText lngEdit = activity.findViewById(R.id.ch40_2_longitude);
//        Button btnToLatLng = activity.findViewById(R.id.ch40_2_btnToLatLng);
//        Button btnToAddr = activity.findViewById(R.id.ch40_2_btnToAddr);
//
//        if (Geocoder.isPresent()) {
//            Geocoder geocoder = new Geocoder(activity);
//
//            btnToLatLng.setOnClickListener((btnView) -> {
//                try {
//                    List<Address> addrList = geocoder.getFromLocationName(String.valueOf(addrEdit.getText()), 1);
//                    Address address = addrList.get(0);
//                    if (address != null) {
//                        double lat = address.getLatitude();
//                        double lng = address.getLongitude();
//                        latEdit.setText(String.valueOf(lat));
//                        lngEdit.setText(String.valueOf(lng));
//
//                        // 41-----------------------------------
//                        // map
//                        //Uri uri = Uri.parse("geo:" + lat + "," + lng);
//                        // ??????
//                        //Uri uri = Uri.parse("google.streetview:cbll=" + lat + "," + lng);
//                        // ??????
//                        Uri uri = Uri.parse("https://www.google.com/maps/dir/?api=1&origin=" + lat + "," + lng + "&destination=25.047305984740774,121.51724751975398");
//                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                        intent.setPackage("com.google.android.apps.maps");
//                        startActivity(intent);
//                        // 41-----------------------------------
//                    }
////                    Log.d("aaa", "?????????" + lat + " | ?????????" + lng);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//
//            btnToAddr.setOnClickListener((btnView) -> {
//                try {
//                    double lat = Double.parseDouble(String.valueOf(latEdit.getText()));
//                    double lng = Double.parseDouble(String.valueOf(lngEdit.getText()));
//                    List<Address> addrList = geocoder.getFromLocation(lat, lng, 1);
//
//                    StringBuilder text = new StringBuilder();
//                    Address address = addrList.get(0);
//                    if (address != null) {
//                        // getMaxAddressLineIndex ???Index??????????????????????????????
//                        for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
//                            text.append(address.getAddressLine(i)).append("\n");
//                        }
//                        addrEdit.setText(text);
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//        }

        // ch42
        MapView mapView = activity.findViewById(R.id.ch42_mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onStart();
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                // ????????????
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                // ??????????????????
                googleMap.setMyLocationEnabled(true);

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(25.047305984740774,121.51724751975398))
                        .zoom(15)
                        //.tilt(45)
                        //.bearing(90)
                        .build();

                CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                googleMap.animateCamera(cameraUpdate);

                // ch43-1 ??????????????????
//                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

                // ch43-2 ????????????
//                googleMap.setTrafficEnabled(true);
//                googleMap.setBuildingsEnabled(true);

                // ch43-3 ???????????????????????????
                UiSettings uiSettings = googleMap.getUiSettings();
                uiSettings.setZoomControlsEnabled(true);
                uiSettings.setCompassEnabled(true);

                uiSettings.setTiltGesturesEnabled(false);

                // ch44-1 ??????
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(new LatLng(25.047305984740774,121.51724751975398))
                        .title("??????")
                        .snippet("????????????")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                        .draggable(true);

                googleMap.addMarker(markerOptions);

                // ch44-2 ????????????
                googleMap.setInfoWindowAdapter(new Ch44_2_InfoWindow(activity));

                // 44-3 ???????????????
                googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(@NonNull LatLng latLng) {
                        MarkerOptions markerOptions = new MarkerOptions()
                                .position(latLng)
                                .title("??????")
                                .snippet("????????????")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                                .draggable(true);

                        googleMap.addMarker(markerOptions);

                        // ch45-3 ????????????
                        CircleOptions circleOptions = new CircleOptions()
                                .center(latLng)
                                .radius(100)
                                .strokeWidth(1.5f)
                                .fillColor(Color.CYAN);

                        Circle circle = googleMap.addCircle(circleOptions);
                    }
                });

                googleMap.setOnInfoWindowLongClickListener(new GoogleMap.OnInfoWindowLongClickListener() {
                    @Override
                    public void onInfoWindowLongClick(@NonNull Marker marker) {
                        marker.remove();
                    }
                });

                googleMap.setOnInfoWindowCloseListener(new GoogleMap.OnInfoWindowCloseListener() {
                    @Override
                    public void onInfoWindowClose(@NonNull Marker marker) {
                        Toast.makeText(activity, "12345", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    //ch37-1
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQ_AUDIO) {
            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(activity, "?????????????????????", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, "??????????????????", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}