package com.example.brianb.reminder;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.brianb.demo1.R;
import com.example.brianb.demo1.Renter;

import java.util.List;

/**
 * Created by BrianB on 11-Aug-17.
 */

public class ChooseTenantAdapter extends ArrayAdapter<Renter> {

    private final int THUMBSIZE = 96;
    Activity activity;
    LayoutInflater inflater;

    public static class ChooseViewHolder {
        ImageView chooseRowIcon;
        TextView chooseRowName, chooseRowAddress, chooseRowDate, chooseRowHseNo;
        Renter renter;
    }

    public ChooseTenantAdapter(Context context, List<Renter> lstRenter) {
        super( context,0, lstRenter);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // view lookup cache stored in tag
        ChooseTenantAdapter.ChooseViewHolder viewHolder;
        // Check if an existing view is being reused, otherwise inflate the item view
        if (convertView == null) {
            viewHolder = new ChooseTenantAdapter.ChooseViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tenant_row, parent, false);
            viewHolder.chooseRowName = (TextView) convertView.findViewById(R.id.chooseRowName);
            viewHolder.chooseRowAddress = (TextView) convertView.findViewById(R.id.chooseRowAddress);
            viewHolder.chooseRowHseNo = (TextView) convertView.findViewById(R.id.chooseRowHseNo);
            viewHolder.chooseRowIcon = (ImageView) convertView.findViewById(R.id.chooseRowIcon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ChooseTenantAdapter.ChooseViewHolder) convertView.getTag();
        }
        // Get the data item for this position
        Renter renter = getItem(position);

        String houseNumber = Integer.toString(renter.getrHouseNumber());

        viewHolder.chooseRowName.setText(renter.getrName());
        viewHolder.chooseRowAddress.setText(renter.getrAddress());
        viewHolder.chooseRowHseNo.setText(houseNumber);
        viewHolder.chooseRowIcon.setImageBitmap(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(renter.getrPath()),
                THUMBSIZE, THUMBSIZE));
        //assigning  the position to the renter object in ChooseViewHolder class on line 27
        viewHolder.renter = renter;
        // Return the completed view to render on screen
        return convertView;
    }
}
