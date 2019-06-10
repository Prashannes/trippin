package com.imperial.project.roadtrip;

import java.io.Serializable;

public class Trip implements Serializable{
    private final String tripCode;
    private String latitude;
    private String longitude;
    private final String destLong;
    private final String destLat;

    public Trip(String tripCode, String latitude, String longitude, String destLong, String destLat) {
        this.tripCode = tripCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.destLong = destLong;
        this.destLat = destLat;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTripCode() {
        return tripCode;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getDestLong() {
        return destLong;
    }

    public String getDestLat() {
        return destLat;
    }

}
