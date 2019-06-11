package com.imperial.project.roadtrip.data;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.services.android.navigation.ui.v5.NavigationView;

public class NavigationMulti extends NavigationView {
    MapMulti mapView;

    public NavigationMulti(Context context) {
        super(context);
//        mapView.initi
    }

    public NavigationMulti(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NavigationMulti(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
