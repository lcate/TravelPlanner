package com.example.travelplanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class DetailsActivity extends AppCompatActivity {
    ImageView imgV;
    TextView txtnametrip,txtdate,txtnamehotel,txtmycomment;
    private byte[] img;
    String name,date,hotel,comment,lat,lng,checked;
    Button btnMap,btnPacking;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        imgV=findViewById(R.id.img);
        txtnametrip=findViewById(R.id.nametrip);
        txtdate=findViewById(R.id.date);
        txtnamehotel=findViewById(R.id.namehotel);
        txtmycomment=findViewById(R.id.mycomment);
        btnMap=findViewById(R.id.btnMap);
        btnPacking=findViewById(R.id.btnPacking);

        btnPacking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),PackingActivity.class);
                i.putExtra("checked",checked);
                startActivity(i);
            }
        });

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), ShowDestionationActivity.class);
                intent.putExtra("lat",lat);
                intent.putExtra("lng",lng);
                startActivity(intent);
            }
        });
        getData();

    }

    private void getData() {
        Bundle bundle=getIntent().getExtras();
        if(getIntent().hasExtra("img")) {
            img=bundle.getByteArray("img");
            Bitmap bmp= BitmapFactory.decodeByteArray(img,0,img.length);
            imgV.setImageBitmap(bmp);
        }
        if(getIntent().hasExtra("name")) {
            name = bundle.getString("name");
            txtnametrip.setText("Trip name: "+name);
        }
        if(getIntent().hasExtra("date")) {
            date = bundle.getString("date");
            txtdate.setText(date);
        }
        if(getIntent().hasExtra("hotel")) {
            hotel = bundle.getString("hotel");
            txtnamehotel.setText(hotel);
        }
        if(getIntent().hasExtra("lat")) {
            lat = bundle.getString("lat");
        }
        if(getIntent().hasExtra("lng")) {
            lng  = bundle.getString("lng");
        }
        if(getIntent().hasExtra("comment")) {
            comment = bundle.getString("comment");
            txtmycomment.setText(comment);
        }
        if(getIntent().hasExtra("checked")) {
            checked = bundle.getString("checked");
        }
    }

}
