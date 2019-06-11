package com.imperial.project.roadtrip.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;

public class MapMulti extends MapView {

    public MapMulti(@NonNull Context context) {
        super(context);
    }

    public MapMulti(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MapMulti(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MapMulti(@NonNull Context context, @Nullable MapboxMapOptions options) {
        super(context, options);
    }
}
