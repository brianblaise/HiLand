package com.example.brianb.demo1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by BrianB on 06-Jun-17.
 */
public class MyListAdapter extends ArrayAdapter<String> {
    public int layout;
    public MyListAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        layout = resource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mainViewholder;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
            ViewHolder viewHolder = new ViewHolder();
//            viewHolder.vId = (TextView)convertView.findViewById(R.id.txtRowId);
            viewHolder.vAddress = (TextView)convertView.findViewById(R.id.propertyRowAddress);
            viewHolder.vUnits = (TextView)convertView.findViewById(R.id.propertyRowUnits);
            convertView.setTag(viewHolder);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Button was clicked for " +position, Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            mainViewholder =(ViewHolder)convertView.getTag();
            mainViewholder.vAddress.setText(getItem(position));
        }
        return convertView;
    }
    public class ViewHolder{
        TextView vId, vAddress, vUnits;
    }
}

