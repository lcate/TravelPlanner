package com.example.travelplanner;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ShowDestionationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String lat="-1",lng="-1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_destionation);
        getData();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void getData() {
        Bundle bundle=getIntent().getExtras();
        if(getIntent().hasExtra("lat")) {
            lat=bundle.getString("lat");
        }
        if(getIntent().hasExtra("lng")) {
            lng = bundle.getString("lng");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(!lat.equals("-1") && !lng.equals("-1")) {
            LatLng sydney = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
            CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                    sydney, 15);
            mMap.addMarker(new MarkerOptions().position(sydney).title("Your destination"));
            mMap.animateCamera(location);

        }
    }
}