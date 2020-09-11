package com.example.travelplanner;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class PackingActivity extends AppCompatActivity {
    String checked;
    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packing);

        ll=findViewById(R.id.linear);

        Bundle bundle=getIntent().getExtras();
        if(getIntent().hasExtra("checked")) {
            checked = bundle.getString("checked");
            String [] spl=checked.split(",");

            for(int i=0;i<spl.length;i++){
                CheckBox cb=new CheckBox(getApplicationContext());
                cb.setPadding(15,1,5,1);
                cb.setText(spl[i]);
                ll.addView(cb);
            }
        }




    }
}