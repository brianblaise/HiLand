package com.example.brianb.demo1;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.brianb.demo1.R.layout.fragment_listings;
import static com.example.brianb.demo1.R.layout.listings_grid;
import com.example.brianb.demo1.ListingAdapterGrid;
import com.example.brianb.demo1.MyImage;


/**
 * Created by BrianB on 29-Apr-17.
 */
public class ListingsFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static final String LOGCAT = null;
    //private static final String RESULT_OK = null;
    //public Uri CONTENT_URI;
   /* GridView gridView;
    ArrayList<Listings> list;
    ListingsListAdapter adapter; */
    DAOdb daOdb;
    GridView gridView;
    ArrayList<MyImage> list = new ArrayList<MyImage>();
    ListingAdapterGrid adapter;
    SimpleAdapter simpleAdapter;
    Cursor cursor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(listings_grid, container, false);

        //int iconSize = getResources().getDimensionPixelSize(android.R.dimen.app_icon_size);
       /* gridView = (GridView) view.findViewById(R.id.gridView); list = new ArrayList<>();gridView.setAdapter(new ListingsListAdapter(view.getContext()));gridView.setOnItemClickListener(this);adapter.notifyDataSetChanged();return view; */


        daOdb = new DAOdb(getActivity());
        initDB();
        gridView = (GridView)view.findViewById(R.id.listingsGrid);

        adapter = new ListingAdapterGrid(view.getContext(), list);
        gridView.setAdapter(adapter);

        return view;
    }
    private void initDB() {
        daOdb = new DAOdb(getActivity());        //add images from database to images ArrayList
        for (MyImage mi : daOdb.getImages()) {
            list.add(mi);
        }
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
