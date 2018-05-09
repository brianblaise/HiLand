package com.example.brianb.demo1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

public class RenterAdapter extends ArrayAdapter<Renter>{

    BitmapFactory.Options options;
    private final int THUMBSIZE = 96;
    Activity activity;
    LayoutInflater inflater;

    public static class ViewHolder {
        ImageView renterRowIcon;
        TextView renterRowName, renterRowAddress, renterRowDate, renterRowHseNo;
        Renter renter;
    }

    public RenterAdapter(Context context, List<Renter> lstRenter) {
      super( context,0, lstRenter);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // view lookup cache stored in tag
        ViewHolder viewHolder;
        // Check if an existing view is being reused, otherwise inflate the item view
        if (convertView == null) {
            viewHolder = new RenterAdapter.ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tenant_row, parent, false);
            viewHolder.renterRowName = (TextView) convertView.findViewById(R.id.renterRowName);
            viewHolder.renterRowAddress = (TextView) convertView.findViewById(R.id.renterRowAddress);
            viewHolder.renterRowDate = (TextView) convertView.findViewById(R.id.renterRowDate);
            viewHolder.renterRowHseNo = (TextView) convertView.findViewById(R.id.renterRowHseNo);
            viewHolder.renterRowIcon = (ImageView) convertView.findViewById(R.id.renterRowIcon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (RenterAdapter.ViewHolder) convertView.getTag();
        }
        // Get the data item for this position
        Renter renter = getItem(position);

        String houseNumber = Integer.toString(renter.getrHouseNumber());

        viewHolder.renterRowName.setText(renter.getrName());
        viewHolder.renterRowAddress.setText(renter.getrPath()); //Todo getrAddress
        viewHolder.renterRowDate.setText(renter.getrDate());
        viewHolder.renterRowHseNo.setText(houseNumber);
        viewHolder.renterRowIcon.setImageBitmap(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(renter.getrPath()),
                THUMBSIZE, THUMBSIZE));

        viewHolder.renter = renter;
        // Return the completed view to render on screen
        return convertView;
    }
}
        // set renter icon

     /*  try{
            File imgFile = new File(renter.getrPath());
            if(imgFile.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                viewHolder.renterRowIcon.setImageBitmap(myBitmap);
                //viewHolder.renterRowIcon.setImageURI(Uri.fromFile(imgFile));

            }
        }catch(Exception e){

        } */
       // viewHolder.renterRowIcon.setImageBitmap(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(renter.getrPath()), THUMBSIZE, THUMBSIZE));
      //  viewHolder.renterRowIcon.setImageBitmap(BitmapFactory.decodeFile(renter.getrPath()));
        //assigning  the position to the renter object in ViewHolder class on line 27


//Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);
        /*  try{
            File imgFile = new File(renter.getrPath());
            if(imgFile.exists()){

             //   Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                //viewHolder.renterRowIcon.setImageBitmap(myBitmap);
          //      viewHolder.renterRowIcon.setImageURI(Uri.fromFile(imgFile));

            }
        }catch(Exception e){

        } */



           /*try{
               options = new BitmapFactory.Options();
               options.inSampleSize = 2;
               Bitmap bitmap = BitmapFactory.decodeFile(renter.getrPath(), options);
               viewHolder.renterRowIcon.setImageBitmap(bitmap);
               //  viewHolder.renterRowIcon.setImageBitmap(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(renter.getrPath()), THUMBSIZE, THUMBSIZE));
        }catch(OutOfMemoryError e){
        try{
            options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            Bitmap bitmap = BitmapFactory.decodeFile(renter.getrPath(), options);
            viewHolder.renterRowIcon.setImageBitmap(bitmap);
        }catch(Exception ex){
            options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            Bitmap bitmap = BitmapFactory.decodeFile(renter.getrPath(), options);
            viewHolder.renterRowIcon.setImageBitmap(bitmap);
        }
        } */