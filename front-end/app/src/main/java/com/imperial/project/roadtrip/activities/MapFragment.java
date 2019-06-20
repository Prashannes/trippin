package com.imperial.project.roadtrip.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.imperial.project.roadtrip.R;
import com.imperial.project.roadtrip.data.Trip;
import com.imperial.project.roadtrip.data.TripMember;
import com.imperial.project.roadtrip.workers.TrippinHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
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
    private List<TripMember> tripMembers = new ArrayList<>();
    private MapboxMap map;
    Handler updateHandler = new Handler();
    int updateDelay = 3*1000;
    Runnable runnable;

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

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        map = mapboxMap;
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.4935671, -0.1762766), 12));
                        updateLocations();
                        updateHandler.postDelayed(runnable = new Runnable() {
                            @Override
                            public void run() {
//                if (tripMembers.size() > 0) {
                                updateLocations();
                                updateHandler.postDelayed(this, updateDelay);
//                }
                            }
                        }, updateDelay);
                    }
                });
            }
        });
    }

    public void updateLocations() {
        final RequestParams params = new RequestParams();
        params.put("tripcode", trip.getTripCode());
        TrippinHttpClient.get("trips", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONArray result = new JSONArray(new String(responseBody));
                    if (tripMembers.size() == 0) {
                        for (int i = 0; i < result.length(); i++) {
                            String name = result.getJSONArray(i).get(0).toString();
                            String latitude = result.getJSONArray(i).get(1).toString();
                            String longitude = result.getJSONArray(i).get(2).toString();
                            LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                            String eta = result.getJSONArray(i).get(5).toString();

                            IconFactory mIconFactory = IconFactory.getInstance(getActivity());
                            Icon icon = mIconFactory.fromBitmap(createImage(i, name.substring(0,1)));

//                            IconFactory mIconFactory = IconFactory.getInstance(getActivity());
//                            Icon icon = mIconFactory.defaultMarker();

                            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(name + "\n" + eta).icon(icon);
                            Marker m = map.addMarker(markerOptions);
                            tripMembers.add(new TripMember(name, latLng, eta, m));
                        }
                    } else {
                        for (int i = 0; i < result.length(); i++) {
                            String name = result.getJSONArray(i).get(0).toString();
                            String latitude = result.getJSONArray(i).get(1).toString();
                            String longitude = result.getJSONArray(i).get(2).toString();
                            LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                            String eta = result.getJSONArray(i).get(5).toString();
                            tripMembers.get(i).setLatLng(latLng);
                            tripMembers.get(i).setETA(eta);
                            tripMembers.get(i).getMarker().setPosition(latLng);
                            tripMembers.get(i).getMarker().setTitle(name + "\n" + eta);
//                            map.updateMarker(tripMembers.get(i).getMarker());
                        }
                    }

                } catch (Exception e) { }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });
    }

    public Bitmap createImage(int color, String name) {
        switch (color) {
            case 0:
                color = Color.BLUE;
                break;
            case 1:
                color = Color.GREEN;
                break;
            case 2:
                color = Color.RED;
                break;
            case 3:
                color = Color.MAGENTA;
                break;
            case 4:
                color = Color.YELLOW;
                break;
            default:
                color = Color.BLACK;
        }

        int width = 100;
        int height = 100;
        int textSize = 72;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint2 = new Paint();
        paint2.setColor(color);
        canvas.drawCircle(50, 50, 50, paint2);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(textSize);
        paint.setTextScaleX(1);
        canvas.drawText(name, (width - textSize + 20)/2, (height + textSize)/2, paint);
        return bitmap;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        updateHandler.removeCallbacks(runnable);
        super.onPause();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(rootView);
        return builder.create();
    }


}

