package com.imperial.project.roadtrip.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.Marker;
import com.imperial.project.roadtrip.R;
import com.imperial.project.roadtrip.data.Trip;
import com.imperial.project.roadtrip.workers.TrippinHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.SupportMapFragment;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MapFragment extends DialogFragment {
    private MapView mapView;
    private View rootView;
    private Trip trip;

    public MapView getMapView() {
        return this.mapView;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Mapbox.getInstance(getContext(), getString(R.string.mapbox_access_token));

        LayoutInflater inflater = getActivity().getLayoutInflater();
        rootView = inflater.inflate(R.layout.fragment_map, null);

        mapView = rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        updateMap();
    }

    public void updateMap() {
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
//                                            mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.4935671, -0.1762766), 16));

                        final RequestParams params = new RequestParams();
                        params.put("tripcode", trip.getTripCode());
                        TrippinHttpClient.get("trips", params, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                try {
                                    JSONArray result = new JSONArray(new String(responseBody));

                                    LatLngBounds.Builder latLngBoundsBuilder = new LatLngBounds.Builder();
                                    for (com.mapbox.mapboxsdk.annotations.Marker m : mapboxMap.getMarkers()) {
                                        mapboxMap.removeMarker(m);
                                    }

//                                    SymbolLayer layer = new SymbolLayer("", "");


                                    for (int i = 0; i < result.length(); i++) {
                                        String name = result.getJSONArray(i).get(0).toString();
                                        String latitude = result.getJSONArray(i).get(1).toString();
                                        String longitude = result.getJSONArray(i).get(2).toString();
                                        LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

                                        latLngBoundsBuilder.include(latLng);
                                        mapboxMap.addMarker(new MarkerOptions().position(latLng).title(name));
                                        mapboxMap.addMarker(new MarkerOptions().position(new LatLng(51.4935671, -0.1762766)));
                                    }

                                    mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.4935671, -0.1762766), 12));

                                } catch (Exception e) {
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            }

                        });
                    }
                });
            }
        });


    }





//        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(rootView);
        return builder.create();
    }

    private class UpdateMap implements Runnable {
        private MapboxMap map;
        private Handler handler;

        public UpdateMap(MapboxMap map, Handler handler) {
            this.map = map;
            this.handler = handler;
        }

        @Override
        public void run() {
//            map.
            handler.postDelayed(this, 2500);
        }
    }

}

