package com.imperial.project.roadtrip.data;

import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.geometry.LatLng;

public class TripMember {
    String username;
    String ETA;
    Marker marker;
    LatLng latLng;

    public TripMember(String username, LatLng latLng, String ETA, Marker marker) {
        this.username = username;
        this.latLng = latLng;
        this.ETA = ETA;
        this.marker = marker;

    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public void setETA(String ETA) {
        this.ETA = ETA;
    }

    public String getUsername() {
        return username;
    }

    public String getETA() {
        return ETA;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

}
