package com.example.brianb.demo1;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by BrianB on 24-Jun-17.
 */

public class ListingCheckView extends AppCompatActivity {

    private ArrayList<MyImage> displayListings = new ArrayList<MyImage>();
    private ListView listView;
    private Uri mCapturedImageURI;
    private DAOdb daOdb;
    //DBhelper db1 = new DBhelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listings_listview);

        listView = (ListView) findViewById(R.id.main_list_view);
        initDB();
        showListings();
    }

    private void showListings() {
        ImageAdapter imageAdapter = new ImageAdapter(this, displayListings);
        listView.setAdapter(imageAdapter);
    }

    private void initDB() {
        daOdb = new DAOdb(this);        //add images from database to images ArrayList
        for (MyImage mi : daOdb.getAllListings()) {
            displayListings.add(mi);
        }
    }

}
