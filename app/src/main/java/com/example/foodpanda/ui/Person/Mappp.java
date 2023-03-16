package com.example.foodpanda.ui.Person;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.foodpanda.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Mappp extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mappp);

        SupportMapFragment mapFragment =(SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng hutech = new LatLng(10.801912513804607, 106.71524300056417);
        googleMap.addMarker(new MarkerOptions().position(hutech).title("475A Điện Biên Phủ, P.25, Q.Bình Thạnh, TP.HCM"));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(hutech, 13);
        googleMap.animateCamera(cameraUpdate);
    }
}