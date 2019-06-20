package com.imperial.project.roadtrip.activities;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.util.TimeUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imperial.project.roadtrip.R;
import com.imperial.project.roadtrip.data.Trip;
import com.imperial.project.roadtrip.workers.TrippinHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.NavigationView;
import com.mapbox.services.android.navigation.ui.v5.NavigationViewOptions;
import com.mapbox.services.android.navigation.ui.v5.OnNavigationReadyCallback;
import com.mapbox.services.android.navigation.ui.v5.listeners.NavigationListener;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.mapbox.services.android.navigation.v5.routeprogress.ProgressChangeListener;
import com.mapbox.services.android.navigation.v5.routeprogress.RouteProgress;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NavigationFragment extends Fragment implements OnNavigationReadyCallback, NavigationListener,
        ProgressChangeListener{

    private static final double ORIGIN_LONGITUDE = -0.1762766;
    private static final double ORIGIN_LATITUDE = 51.4935671;

    private NavigationView navigationView;
    private DirectionsRoute directionsRoute;
    Handler updateHandler = new Handler();
    int updateDelay = 3*1000;
    Runnable runnable;

    String username;
    Trip trip;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nav, container, false);
    }

    public void updateCurrentLocation() {
        final RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("tripcode", trip.getTripCode());
        params.put("lat", trip.getLatitude());
        params.put("long", trip.getLongitude());
        params.put("eta", trip.getETA());
        TrippinHttpClient.put("trips", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navigationView = view.findViewById(R.id.navigation_view_fragment);
        navigationView.onCreate(savedInstanceState);
        navigationView.initialize(this);
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void onStart() {
        super.onStart();
        navigationView.onStart();
    }

    @Override
    public void onResume() {
        updateCurrentLocation();
        updateHandler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                updateCurrentLocation();
                updateHandler.postDelayed(this, updateDelay);
            }
        }, updateDelay);
        super.onResume();
        navigationView.onResume();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        navigationView.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            navigationView.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public void onPause() {
        updateHandler.removeCallbacks(runnable);
        super.onPause();
        navigationView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        navigationView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        navigationView.onLowMemory();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        navigationView.onDestroy();
    }

    @Override
    public void onNavigationReady(boolean isRunning) {
//        Point origin = Point.fromLngLat(Double.parseDouble(trip.getLatitude()), Double.parseDouble(trip.getLongitude()));
        Point origin = Point.fromLngLat(ORIGIN_LONGITUDE, ORIGIN_LATITUDE);
        Point destination = Point.fromLngLat(Double.parseDouble(trip.getDestLong()), Double.parseDouble(trip.getDestLat()));
        fetchRoute(origin, destination);
    }

    @Override
    public void onCancelNavigation() {
        navigationView.stopNavigation();
//        stopNavigation();
    }

    @Override
    public void onNavigationFinished() {
// no-op
    }

    @Override
    public void onNavigationRunning() {
// no-op
    }

    @Override
    public void onProgressChange(Location location, RouteProgress routeProgress) {
        this.trip.setLongitude(Double.toString(location.getLongitude()));
        this.trip.setLatitude(Double.toString(location.getLatitude()));
        int totalSecs = (int) routeProgress.durationRemaining();
        int hours = totalSecs / 3600;
        int minutes = (totalSecs % 3600) / 60;
        this.trip.setETA(hours + "h " + minutes + "m");
//        updateCurrentLocation();
    }


    private void fetchRoute(Point origin, Point destination) {
        NavigationRoute.builder(getContext())
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        if (response.body() == null) {
                            return;
                        } else if (response.body().routes().size() == 0) {
                            return;
                        }
                        directionsRoute = response.body().routes().get(0);
                        startNavigation();
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable t) {

                    }
                });
    }

    private void startNavigation() {
        if (directionsRoute == null) {
            return;
        }
        NavigationViewOptions options = NavigationViewOptions.builder()
                .directionsRoute(directionsRoute)
                .shouldSimulateRoute(true)
                .navigationListener(NavigationFragment.this)
                .progressChangeListener(this)
                .build();
        navigationView.startNavigation(options);
    }
}
