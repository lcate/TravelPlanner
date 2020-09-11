package com.example.travelplanner;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class SelectDestinationActivity extends AppCompatActivity implements OnMapReadyCallback {
    SupportMapFragment supportMapFragment;
    GoogleMap map;
    String lat="-1",lng="-1";
    SearchView searchView;
    Button btnChoseLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_destination);
        searchView=findViewById(R.id.svLocation);
        supportMapFragment=(SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);
        btnChoseLocation=findViewById(R.id.btnChoseLocation);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location=searchView.getQuery().toString();
                List<Address> addressList=null;
                if ( location!=null || !location.equals("")){
                    Geocoder geocoder = new Geocoder(SelectDestinationActivity.this);
                    try {
                        addressList=geocoder.getFromLocationName(location,1);
                        if(addressList.size()>0) {
                            Address address = addressList.get(0);
                            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                            lat= String.valueOf(address.getLatitude());
                            lng= String.valueOf(address.getLongitude());
                            map.addMarker(new MarkerOptions().position(latLng).title(location));
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                        }
                        else{
                            Toast.makeText(SelectDestinationActivity.this, "please enter a valid location", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        btnChoseLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                String text = lat+","+lng;
                data.setData(Uri.parse(text));
                setResult(RESULT_OK, data);
                finish();
            }
        });
        supportMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
    }
}