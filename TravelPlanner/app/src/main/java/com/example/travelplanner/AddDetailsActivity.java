package com.example.travelplanner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddDetailsActivity extends AppCompatActivity {

    Button btnIzberiDestinacija, btnNext, btnChangePhoto, btnCamera;
    ImageView mImageView;
    EditText imeHotel,komentar,imePatuvanje,datum;
    int PLACE_PICKER_REQUEST=4;
    static int GALLERY_CODE=1;
    static int CAMERA_CODE=2;
    String lat="-1",lng="-1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details);

        imePatuvanje=findViewById(R.id.imePatuvanje);
        datum=findViewById(R.id.datum);
        imeHotel=findViewById(R.id.imeHotel);
        komentar=findViewById(R.id.komentar);
        btnCamera =findViewById(R.id.cameraPhoto);
        btnChangePhoto =findViewById(R.id.changePhoto);
        mImageView=findViewById(R.id.slika);
        btnNext =findViewById(R.id.btnNext);
        btnIzberiDestinacija =findViewById(R.id.izberiLokacija);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CAMERA_CODE);

            }
        });
        btnChangePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        AddDetailsActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        GALLERY_CODE);

            }
        });
        btnIzberiDestinacija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), SelectDestinationActivity.class);
                startActivityForResult(intent,PLACE_PICKER_REQUEST);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ime=imePatuvanje.getText().toString();
                String dat=datum.getText().toString();
                String hotel=imeHotel.getText().toString();
                String comment=komentar.getText().toString();
                if(!lat.equals("-1") && !lng.equals("-1")) {
                    if (!ime.equals("") && !dat.equals("")) {
                        Intent next = new Intent(getApplicationContext(), AddPackageActivity.class);
                        next.putExtra("name", ime);
                        next.putExtra("date", dat);
                        next.putExtra("hotel", hotel);
                        next.putExtra("comment", comment);
                        next.putExtra("lat", lat);
                        next.putExtra("lng", lng);
                        next.putExtra("img", imageViewToByte(mImageView));
                        startActivity(next);
                    } else {
                        Toast.makeText(AddDetailsActivity.this, "Name and date are required!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(AddDetailsActivity.this, "Please select a destination", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public byte[] imageViewToByte(ImageView image){
        Bitmap bitmap=((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] bytes=stream.toByteArray();
        return bytes;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST && resultCode==RESULT_OK && data!=null) {
            String latlng=data.getDataString();
            if(latlng.equals("-1,-1")){
                Toast.makeText(this, "Please select a destination", Toast.LENGTH_SHORT).show();
            }
            String[] spl=latlng.split(",");
            lat=spl[0];
            lng=spl[1];
            Toast.makeText(this, latlng, Toast.LENGTH_SHORT).show();
        }
        if(requestCode==GALLERY_CODE && resultCode==RESULT_OK && data!=null){
            Uri uri=data.getData();
            try {
                InputStream inputStream=getContentResolver().openInputStream(uri);
                Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                mImageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap,mImageView.getWidth(),
                        mImageView.getHeight(),false));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else if(requestCode==CAMERA_CODE && resultCode==RESULT_OK && data!=null){
            Bitmap bitmap=(Bitmap) data.getExtras().get("data");
            mImageView.setImageBitmap(bitmap);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==GALLERY_CODE){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_CODE);
            }
        }
    }
}