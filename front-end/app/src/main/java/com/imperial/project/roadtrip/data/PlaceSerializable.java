package com.imperial.project.roadtrip.data;

import com.google.android.libraries.places.api.model.Place;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class PlaceSerializable implements Serializable{
    private String latitiude;
    private String longitude;
    private String name;

    PlaceSerializable(@NotNull Place place) {
        this.latitiude = Double.toString(place.getLatLng().latitude);
        this.longitude = Double.toString(place.getLatLng().longitude);
        this.name = place.getName();
    }

    public String getLatitude() {
        return this.latitiude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public String getName() {
        return this.name;
    }
}
