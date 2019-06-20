package com.imperial.project.roadtrip.data;

import java.io.Serializable;

public class Trip implements Serializable{
    private final String tripCode;
    private String latitude;
    private String longitude;
    private final String destLat;
    private final String destLong;
    private String ETA = "";


    public Trip(String tripCode, String latitude, String longitude, String destLat, String destLong) {
        this.tripCode = tripCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.destLat = destLat;
        this.destLong = destLong;
    }

    public String getETA() {
        return ETA;
    }

    public void setETA(String ETA) {
        this.ETA = ETA;
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
