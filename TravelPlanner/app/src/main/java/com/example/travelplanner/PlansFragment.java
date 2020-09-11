package com.example.travelplanner;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;



public class PlansFragment extends Fragment implements CustomAdapter.customClickListener {
    DataBaseHelper mDatabaseHelper;
    SQLiteDatabase mDatabase;
    RecyclerView nov;
    ArrayList<String> lista;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_plans,container,false);
        mDatabaseHelper=new DataBaseHelper(getContext());
        mDatabase=mDatabaseHelper.getWritableDatabase();
        Cursor mCursor = mDatabase.rawQuery("SELECT * FROM tabela", null);
        if(mCursor.getCount() == 0)
            createList();
        nov=v.findViewById(R.id.recyclerview);
        nov.setLayoutManager(new LinearLayoutManager(getContext()));
        nov.setAdapter(new CustomAdapter(getActivity(),mDatabase.query("tabela",null,null,null,null,null,null),this));

        return v;
    }

    @Override
    public void onCustomClick(int position) {
        if(position==0){
            startActivity(new Intent(getContext(),AddDetailsActivity.class));
        }
        else {
            Cursor cursor = mDatabase.rawQuery("select * from tabela", null);
            if (cursor.getCount() == 0) {
            } else {
                cursor.moveToPosition(position);
                String name = cursor.getString(1);
                String date = (cursor.getString(2));
                String hotel = (cursor.getString(3));
                String comment = (cursor.getString(4));
                String lat = (cursor.getString(5));
                String lng = (cursor.getString(6));
                byte[] image = cursor.getBlob(7);
                String checked = cursor.getString(8);

                Intent intentStart = new Intent(getContext(), DetailsActivity.class);
                intentStart.putExtra("name", name);
                intentStart.putExtra("date", date);
                intentStart.putExtra("hotel", hotel);
                intentStart.putExtra("comment", comment);
                intentStart.putExtra("lat", lat);
                intentStart.putExtra("lng", lng);
                intentStart.putExtra("img", image);
                intentStart.putExtra("checked", checked);
                startActivity(intentStart);
            }
        }
    }

    private void createList() {
        Resources res=getResources();

        ByteArrayOutputStream stream= new ByteArrayOutputStream();
        Drawable d=res.getDrawable(R.drawable.add);
        Bitmap bitmap=((BitmapDrawable)d).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] eden = stream.toByteArray();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", "");
        contentValues.put("date", "Create new ");
        contentValues.put("hotel","plan here..." );
        contentValues.put("comment", "1.2l chicken stock (fresh is best), heated until simmering");
        contentValues.put("lat", (float) 42.52);
        contentValues.put("lng",(float) 39.52);
        contentValues.put("img",eden);
        contentValues.put("checked","dsad, dsadad, dsadsa");
        mDatabase.insert("tabela", null, contentValues);
        

    }

}
