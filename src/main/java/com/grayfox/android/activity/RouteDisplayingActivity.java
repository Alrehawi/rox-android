package com.grayfox.android.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import com.grayfox.android.R;
import com.grayfox.android.client.model.Location;
import com.grayfox.android.client.model.Poi;
import com.grayfox.android.client.model.Recommendation;

import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.ContentView;

@ContentView(R.layout.activity_route_displaying)
public class RouteDisplayingActivity extends RoboActionBarActivity implements OnMapReadyCallback {

    private static final String RECOMMENDATION_ARG = "RECOMMENDATION";
    private static final String ORIGIN_LOCATION_ARG = "ORIGIN_LOCATION";

    private Location origin;
    private Recommendation recommendation;

    public static Intent getIntent(Context context, Location origin, Recommendation recommendation) {
        Intent intent = new Intent(context, RouteDisplayingActivity.class);
        intent.putExtra(ORIGIN_LOCATION_ARG, origin);
        intent.putExtra(RECOMMENDATION_ARG, recommendation);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        origin = (Location) getIntent().getExtras().getSerializable(ORIGIN_LOCATION_ARG);
        recommendation = (Recommendation) getIntent().getExtras().getSerializable(RECOMMENDATION_ARG);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (recommendation.getPoiSequence().length > 0) {
            for (Poi poi : recommendation.getPoiSequence()) {
                // TODO: Fetch category icon from web
                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(poi.getLocation().getLatitude(), poi.getLocation().getLongitude()))
                        .title(poi.getName()));
            }
        }
        if (recommendation.getRoutePoints().length > 0) {
            PolylineOptions pathOptions = new PolylineOptions().color(Color.RED);
            for (int index = 0; index < recommendation.getRoutePoints().length; index++) {
                pathOptions.add(new LatLng(recommendation.getRoutePoints()[index].getLatitude(), recommendation.getRoutePoints()[index].getLongitude()));
            }
            googleMap.addPolyline(pathOptions);
        }
        LatLng latLngOrigin = new LatLng(origin.getLatitude(), origin.getLongitude());
        googleMap.addMarker(new MarkerOptions()
                .position(latLngOrigin)
                .title(getString(R.string.your_location))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngOrigin, 13f));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }
}