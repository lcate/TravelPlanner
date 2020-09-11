package com.example.travelplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class AddPackageActivity extends AppCompatActivity implements CustomAdapter.customClickListener {

    Button btnFinish;
    CheckBox ch1,ch2,ch3,ch4,ch5,ch6,ch7,ch8,ch9,ch10;
    String name,date,hotel,comment,lat,lng,checked;
    byte[] img;
    SQLiteDatabase mDatabase;
    CustomAdapter mAdapter;
    DataBaseHelper mDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_package);

        init();
        getData();
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToDatabase();
            }
        });

    }

    private void addToDatabase() {
        if (name != null && date != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", name);
            contentValues.put("date", date);
            contentValues.put("hotel", hotel);
            contentValues.put("comment", comment);
            contentValues.put("lat", lat);
            contentValues.put("lng", lng);
            contentValues.put("img",img);
            createChecked();
            contentValues.put("checked",checked);
            long result = mDatabase.insert("tabela", null, contentValues);
            mAdapter.swapCursor(mDatabase.query("tabela", null, null, null, null, null, null));
            if (result == -1)
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
            else {
                Toast.makeText(getApplicationContext(), "SUCCESS", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(getApplicationContext(),PlannerActivity.class);
                startActivity(i);
            }
        }
    }

    private void createChecked() {
        if(ch1.isChecked()) {
            checked += ch1.getText();
            checked+=",";
        }
        if(ch2.isChecked()) {
            checked += ch2.getText();
            checked+=",";
        }
        if(ch3.isChecked()) {
            checked += ch3.getText();
            checked+=",";
        }
        if(ch4.isChecked()) {
            checked += ch4.getText();
            checked+=",";
        }
        if(ch5.isChecked()) {
            checked += ch5.getText();
            checked+=",";
        }
        if(ch6.isChecked()) {
            checked += ch6.getText();
            checked+=",";
        }
        if(ch7.isChecked()) {
            checked += ch7.getText();
            checked+=",";
        }
        if(ch8.isChecked()) {
            checked += ch8.getText();
            checked+=",";
        }
        if(ch9.isChecked()) {
            checked += ch9.getText();
            checked+=",";
        }
        if(ch10.isChecked()) {
            checked += ch10.getText();
            checked+=",";
        }
    }

    private void getData() {
        Bundle bundle=getIntent().getExtras();
        if(getIntent().hasExtra("img")) {
            img=bundle.getByteArray("img");
        }
        if(getIntent().hasExtra("name")) {
            name = bundle.getString("name");
        }
        if(getIntent().hasExtra("date")) {
            date = bundle.getString("date");
        }
        if(getIntent().hasExtra("hotel")) {
            hotel = bundle.getString("hotel");
        }
        if(getIntent().hasExtra("lat")) {
            lat = bundle.getString("lat");
        }
        if(getIntent().hasExtra("lng")) {
            lng  = bundle.getString("lng");
        }
        if(getIntent().hasExtra("comment")) {
            comment = bundle.getString("comment");
        }
    }


    public void init(){
        ch1=findViewById(R.id.checkBox1);
        ch2=findViewById(R.id.checkBox2);
        ch3=findViewById(R.id.checkBox3);
        ch4=findViewById(R.id.checkBox4);
        ch5=findViewById(R.id.checkBox5);
        ch6=findViewById(R.id.checkBox6);
        ch7=findViewById(R.id.checkBox7);
        ch8=findViewById(R.id.checkBox8);
        ch9=findViewById(R.id.checkBox9);
        ch10=findViewById(R.id.checkBox10);
        btnFinish=findViewById(R.id.btnFinish);
        checked="";
        mDataBaseHelper=new DataBaseHelper(getApplicationContext());
        mDatabase=mDataBaseHelper.getWritableDatabase();
        mAdapter=new CustomAdapter(this,mDatabase.query("tabela",null,null,null,null,null,null),this);

    }

    @Override
    public void onCustomClick(int position) {

    }
}