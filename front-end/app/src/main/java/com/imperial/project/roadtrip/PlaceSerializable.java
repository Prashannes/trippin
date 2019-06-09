package com.imperial.project.roadtrip;

import com.google.android.libraries.places.api.model.Place;

import java.io.Serializable;

public class PlaceSerializable implements Serializable{
    private Place place;

    PlaceSerializable(Place place) {
        this.place = place;
    }

    public Place getPlace() {
        return place;
    }
}
