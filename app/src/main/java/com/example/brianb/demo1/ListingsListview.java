package com.example.brianb.demo1;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import com.google.gson.Gson;

/**
 * Created by BrianB on 24-Jun-17.
 */

public class ListingsListview  extends AppCompatActivity{


        private ArrayList<MyImage> arrayList;
        private ImageAdapter imageAdapter;
        private ListView listView;
        private Uri mCapturedImageURI;
        private static final int RESULT_LOAD_IMAGE = 1;
        private static final int REQUEST_IMAGE_CAPTURE = 2;
        private DAOdb daOdb;
        final MyImage p = new MyImage();
        SQLiteDatabase mDb;
        private DBhelper db1;
        SQLiteDatabase db;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_listings_listview);
            db = new DatabaseHelper(this).getWritableDatabase();

            refresh();
        }
    private void refresh() {
        // Construct the data source
        arrayList = new ArrayList<>();
        imageAdapter = new ImageAdapter(this, arrayList);
        listView = (ListView) findViewById(R.id.main_list_view);
        listView.setAdapter(imageAdapter);
        addItemClickListener(listView);
        initDB();
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                removeItemFromList(position, id);
                return true;
            }
        });
    }

    protected void removeItemFromList(final int position, long id) {
        final int deletePosition = position;
        final long ids = id;

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Delete");
        alert.setMessage("Do you want delete this whitelist?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TOD O Auto-generated method stub
               // arrayList.remove(deletePosition);
                //daOdb.deleteSelected(String.valueOf(ids));
                Cursor mCursor;
                mCursor = db.rawQuery("SELECT * FROM propertiesTB", null);
                mCursor.moveToPosition(position);
                String rowId = mCursor.getString(0); //Column 0 of the cursor is the id
                mDb.delete(db1.TABLE_NAME, "id = ?", new String[]{rowId});
                mCursor.requery();
                refresh();
                imageAdapter.notifyDataSetChanged();
                mCursor.close();
            }
        });
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        alert.show();
    }



    /**
         * initialize database
         */
        private void initDB() {
            daOdb = new DAOdb(this);        //add arrayList from database to arrayList ArrayList
            for (MyImage mi : daOdb.getImages()) {
                arrayList.add(mi);
            }
        }

        /**
         * take a photo
         */
        private void activeTakePhoto() {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                String fileName = "temp.jpg";
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, fileName);
                mCapturedImageURI = getContentResolver()
                        .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                values);
                takePictureIntent
                        .putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);

                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }

        /**
         * to gallery
         */
        private void activeGallery() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        RESULT_LOAD_IMAGE);
                return;
            }
            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, RESULT_LOAD_IMAGE);
        }

        @Override protected void onActivityResult(int requestCode, int resultCode,
                                                  Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            switch (requestCode) {
                case RESULT_LOAD_IMAGE:

                    if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        cursor.close();
                        MyImage image = new MyImage();
                        image.setTitle("");
                        image.setDescription("");
                        image.setPath(picturePath);
                        arrayList.add(image);
                        daOdb.addImage(image);
                    }
                case REQUEST_IMAGE_CAPTURE:
                    if (requestCode == REQUEST_IMAGE_CAPTURE &&
                            resultCode == RESULT_OK) {

                        String[] projection = {MediaStore.Images.Media.DATA};
                        Cursor cursor =
                                managedQuery(mCapturedImageURI, projection, null,
                                        null, null);
                        int column_index_data = cursor.getColumnIndexOrThrow(
                                MediaStore.Images.Media.DATA);
                        cursor.moveToFirst();
                        String picturePath = cursor.getString(column_index_data);       //NoteDisplay obj1 = new NoteDisplay();

                        Intent jill = new Intent(ListingsListview.this, ListingDetails.class);
                        jill.putExtra("PIC", picturePath);
                        startActivityForResult(jill, 3);
                    }
                case 3:
                    if (requestCode == 3 &&
                            data != null) {
                        // int msg1 = data.getIntExtra();
                        String msg2 = data.getStringExtra("PICPATH");
                        MyImage image = new MyImage();
                        image.setTitle("Image Title");
                        image.setDescription(
                                "This is the photo description");

                        //image.setDatetime(System.currentTimeMillis());
                        image.setPath(msg2);
                        //image.setPrice(msg1);
                        arrayList.add(image);
                        daOdb.addImage(image);
                    }
            }
        }

        /**
         * item clicked listener used to implement the react action when an item is
         * clicked.
         *
         * @param listView
         */
        private void addItemClickListener(final ListView listView) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    MyImage image = (MyImage) listView.getItemAtPosition(position);
                    Intent intent = new Intent(getBaseContext(), DisplayImage.class);
                    intent.putExtra("IMAGE", (new Gson()).toJson(image));
                    startActivity(intent);
                }
            });
        }

        @Override protected void onSaveInstanceState(Bundle outState) {
            // Save the user's current game state
            if (mCapturedImageURI != null) {
                outState.putString("mCapturedImageURI",
                        mCapturedImageURI.toString());
            }
            // Always call the superclass so it can save the view hierarchy state
            super.onSaveInstanceState(outState);
        }

        @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
            // Always call the superclass so it can restore the view hierarchy
            super.onRestoreInstanceState(savedInstanceState);

            // Restore state members from saved instance
            if (savedInstanceState.containsKey("mCapturedImageURI")) {
                mCapturedImageURI = Uri.parse(savedInstanceState.getString("mCapturedImageURI"));
            }
        }
    }