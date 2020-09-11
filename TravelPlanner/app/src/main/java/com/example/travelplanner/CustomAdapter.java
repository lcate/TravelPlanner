package com.example.travelplanner;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    private Context mContext;
    private Cursor mCursor;
    private customClickListener mCustomClickListener;

    public CustomAdapter(Context context, Cursor cursor, customClickListener customClickListener){
        mContext=context;
        mCursor=cursor;
        mCustomClickListener=customClickListener;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nameText,dateText,hotelText;
        public ImageView mImage;
        public customClickListener ccl;
        RatingBar ratingBar;

        public CustomViewHolder(@NonNull View itemView, customClickListener x) {
            super(itemView);
            nameText=itemView.findViewById(R.id.nameLeft);
            mImage=itemView.findViewById(R.id.imageView);
            dateText=itemView.findViewById(R.id.portionsRight);
            hotelText=itemView.findViewById(R.id.caloriesLeft);
            ratingBar=itemView.findViewById(R.id.ratingBar);
            ccl=x;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ccl.onCustomClick(getAdapterPosition());
        }
    }


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mContext);
        View view=inflater.inflate(R.layout.example_item,parent,false);
        return new CustomViewHolder(view,mCustomClickListener);
    }


    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        if(!mCursor.moveToPosition(position)){
            return;
        }
        String name=mCursor.getString(mCursor.getColumnIndex("name"));
        byte[] img=mCursor.getBlob(mCursor.getColumnIndex("img"));
        String date=mCursor.getString(mCursor.getColumnIndex("date"));
        String hotel=mCursor.getString(mCursor.getColumnIndex("hotel"));


        holder.nameText.setText(name);
        Bitmap bmp= BitmapFactory.decodeByteArray(img,0,img.length);
        holder.mImage.setImageBitmap(bmp);
        holder.dateText.setText(date);
        holder.hotelText.setText(hotel);
        if(position==0){
            holder.ratingBar.setVisibility(View.GONE);
        }
        holder.ratingBar.setRating(5);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor){
        if(mCursor!=null){
            mCursor.close();
        }
        mCursor=newCursor;
        if(newCursor!=null){
            notifyDataSetChanged();
        }
    }

    public interface customClickListener{
        void onCustomClick(int position);
    }

}
