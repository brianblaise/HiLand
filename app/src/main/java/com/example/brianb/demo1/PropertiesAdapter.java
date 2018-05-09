package com.example.brianb.demo1;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BrianB on 01-May-17.
 */

//You need one class for setters and getters and another that extends BaseAdapter, one for database helper with methods to implement 5 important methods
//add, update, delete, getProperty and getAllProperties here, the pre-declared getters and setters of Properties class are implemented and assigned to database
// the final class will call all the get
public class PropertiesAdapter extends ArrayAdapter<Properties> {

    private final int THUMBSIZE = 96;
    Activity activity;
    LayoutInflater inflater;


    private static class ViewHolder {
        ImageView propertyRowIcon;
        TextView propertyRowType, propertyRowAddress, propertyRowUnits;
    }

    public PropertiesAdapter(Context context, List<Properties> lstProperties) {
        super(context,0, lstProperties);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) { //Rowview implemented to lstProperties Listview. Get a View that displays the data at the specified position in the data set.

      ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new PropertiesAdapter.ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, viewGroup, false);
            viewHolder.propertyRowAddress = (TextView) convertView.findViewById(R.id.propertyRowAddress);
            viewHolder.propertyRowType = (TextView) convertView.findViewById(R.id.propertyRowType);
            viewHolder.propertyRowUnits = (TextView) convertView.findViewById(R.id.propertyRowUnits);
            viewHolder.propertyRowIcon = (ImageView) convertView.findViewById(R.id.propertyRowIcon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (PropertiesAdapter.ViewHolder) convertView.getTag();
        }
        Properties property = getItem(position);

        viewHolder.propertyRowAddress.setText(property.getAddress());
        viewHolder.propertyRowType.setText(property.getpPath()); //Todo getProperty
        viewHolder.propertyRowUnits.setText(property.getUnits());
        viewHolder.propertyRowIcon.setImageBitmap(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(property.getpPath()),
                THUMBSIZE, THUMBSIZE));
        return convertView;
    }
}
          /* @Override
    public int getCount() { //How many items are in the data set represented by this Adapter.
        return lstProperties.size();
    }

    @Override
    public Object getItem(int i) { //Get the data item associated with the specified position in the data set.
        return lstProperties.get(i);
    }

    @Override
    public long getItemId(int i) {   //Get the row id associated with the specified position in the list
        return lstProperties.get(i).getId();
    } */

        /*  final TextView propertyRowId, propertyRowAddress, propertyRowUnits, propType;
        View rowView;

        rowView = inflater.inflate(R.layout.row, null); //set up layout inflater where the outputs are going to look like the created xml
                                                        // effective to inflate the layout/appearance to ProjectForm
        propertyRowId = (TextView)rowView.findViewById(R.id.propertyRowId);  //assign objects to ids of icons of that particular xml file
        propertyRowAddress = (TextView)rowView.findViewById(R.id.propertyRowAddress);
        propertyRowUnits = (TextView)rowView.findViewById(R.id.propertyRowUnits);
        propType = (TextView)rowView.findViewById(R.id.propType);
        edtId = (EditText)rowView.findViewById(R.id.edtId);

        propertyRowId.setText(""+lstProperties.get(i).getId());
        propType.setText(""+lstProperties.get(i).getProperty());
        propertyRowAddress.setText(""+lstProperties.get(i).getAddress());
        propertyRowUnits.setText(""+lstProperties.get(i).getUnits());

    return rowView; */


