package com.example.brianb.demo1;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by BrianB on 26-Jun-17.
 */

public class ListingAdapterGrid extends ArrayAdapter<MyImage> {

    private final int THUMBSIZE = 96;

    /**
     * applying ViewHolderGrid pattern to speed up ListView, smoother and faster
     * item loading by caching view in A ViewHolderGrid object
     */
    private static class ViewHolderGrid {
        ImageView imgIcon;
        TextView description;
    }

    public ListingAdapterGrid(Context context, ArrayList<MyImage> images) {
        super(context, 0, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // view lookup cache stored in tag
            ViewHolderGrid viewHolderGrid;
        // Check if an existing view is being reused, otherwise inflate the
        // item view
        if (convertView == null) {
            viewHolderGrid = new ViewHolderGrid();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listings_row, parent, false);
            viewHolderGrid.description = (TextView) convertView.findViewById(R.id.txtListingName);
            viewHolderGrid.imgIcon = (ImageView) convertView.findViewById(R.id.imgListing);
            convertView.setTag(viewHolderGrid);
        } else {
            viewHolderGrid = (ViewHolderGrid) convertView.getTag();
        }
        // Get the data item for this position
        MyImage image = getItem(position);
        // set description text
        viewHolderGrid.description.setText(image.toString());
        // set image icon
        viewHolderGrid.imgIcon.setImageBitmap(ThumbnailUtils
                .extractThumbnail(BitmapFactory.decodeFile(image.getPath()), THUMBSIZE, THUMBSIZE));

        // Return the completed view to render on screen
        return convertView;
    }
}
