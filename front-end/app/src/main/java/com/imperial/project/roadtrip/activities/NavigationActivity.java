package com.imperial.project.roadtrip.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.imperial.project.roadtrip.R;
import com.imperial.project.roadtrip.data.RouteWrap;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_navigation);


        Point origin = Point.fromLngLat(Double.parseDouble("-0.177066"), Double.parseDouble("51.4986929"));
        Point destination = Point.fromLngLat(Double.parseDouble("-0.1761444"), Double.parseDouble("51.4941614"));

        NavigationRoute.builder(this)
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
                        DirectionsRoute route = response.body().routes().get(0);
                        NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                                .directionsRoute(route)
                                .shouldSimulateRoute(true)
                                .build();
                        NavigationLauncher.startNavigation(NavigationActivity.this, options);
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable t) {

                    }
                });

    }


}
