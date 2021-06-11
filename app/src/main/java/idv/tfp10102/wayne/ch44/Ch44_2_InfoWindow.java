package idv.tfp10102.wayne.ch44;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import idv.tfp10102.wayne.R;

public class Ch44_2_InfoWindow implements GoogleMap.InfoWindowAdapter {
    Context context;

    TextView tvTitle, tvSnippet;

    public Ch44_2_InfoWindow(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        View view = View.inflate(context, R.layout.info_window, null);

        tvTitle = view.findViewById(R.id.tvTitle);
        tvSnippet = view.findViewById(R.id.tvSnippet);

        tvTitle.setText(marker.getTitle());
        tvSnippet.setText(marker.getSnippet());

        return view;
    }

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        return null;
    }
}
