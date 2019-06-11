package com.imperial.project.roadtrip.data;

import com.mapbox.api.directions.v5.models.DirectionsRoute;

public class RouteWrap {
    DirectionsRoute directionsRoute;

    public RouteWrap() {
    }

    public DirectionsRoute getDirectionsRoute() {
        return directionsRoute;
    }

    public void setDirectionsRoute(DirectionsRoute directionsRoute) {
        this.directionsRoute = directionsRoute;
    }
}
