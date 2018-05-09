package com.example.brianb.demo1;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ListingsMain extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<MyImage> images = new ArrayList<MyImage>();
    private ImageAdapter imageAdapter;
    private ListView listView;
    private Uri mCapturedImageURI;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    DAOdb daOdb;

    Button btnSave, btnCancel, btnList;
    EditText propertyTitle, propertyPrice, propertyDetails ;
    ImageView insertImage;
    String imagePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listings_main);

        initDB();                   //To start a database connection

        propertyTitle = (EditText)findViewById(R.id.propertyTitle);
        propertyPrice = (EditText)findViewById(R.id.propertyPrice);
        propertyDetails = (EditText)findViewById(R.id.propertyDetails);

        btnSave = (Button)findViewById(R.id.btnSaveProperty);
        btnCancel = (Button)findViewById(R.id.btnCancelListing);
        btnList = (Button)findViewById(R.id.btnList);

        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnList.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSaveProperty:
                MyImage displayDetails = new MyImage(propertyTitle.getText().toString(),
                        propertyDetails.getText().toString(),
                        Integer.parseInt(propertyPrice.getText().toString()),  imagePath );
                daOdb.addImage(displayDetails);
                startActivity(new Intent(ListingsMain.this, ListingsListview.class));

                break;
            case R.id.btnCancelListing:
                finish();
                break;
            case R.id.btnList:
                startActivity(new Intent(ListingsMain.this, ListingsListview.class));
                break;
        }
    }
    /**
     * initialize database
     */
    private void initDB() {
        daOdb = new DAOdb(this);        //add images from database to images ArrayList
        for (MyImage mi : daOdb.getImages()) {
            images.add(mi);
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
            mCapturedImageURI = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
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
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
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
                    imagePath = cursor.getString(columnIndex);
                    cursor.close();
                    MyImage image = new MyImage();
                    image.setPath(imagePath);
                    insertImage = (ImageView)findViewById(R.id.insertImage);
                    insertImage.setImageURI(selectedImage);

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
                    String picturePath = cursor.getString(column_index_data);
                    //NoteDisplay obj1 = new NoteDisplay();

                    Intent jill = new Intent(ListingsMain.this, ListingsListview.class);
                    jill.putExtra("PIC", picturePath);
                    startActivityForResult(jill, 3);
                }
            case 3:
                if (requestCode == 3 &&
                        data != null) {
                    String msg1 = data.getStringExtra("MESSAGE");
                    String msg2 = data.getStringExtra("PICPATH");
                    MyImage image = new MyImage();
                    image.setTitle("Image Title");
                    image.setDescription("This is the photo description");

                    image.setPath(msg2);
                    images.add(image);
                    daOdb.addImage(image);
                }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_image, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_image:
                final Dialog dialog = new Dialog(this);
                // show dialog on screen
                dialog.show();
                dialog.setContentView(R.layout.custom_dialog_box);
                dialog.setTitle("Select Option");
                Button btnExit = (Button) dialog.findViewById(R.id.btnExit);
                btnExit.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        dialog.dismiss();

                    }
                });
                dialog.findViewById(R.id.btnChoosePath)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                activeGallery();
                            }
                        });
                dialog.findViewById(R.id.btnTakePhoto)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                activeTakePhoto();
                            }
                        });
           /*     Toast.makeText(this, "Menu Item 1 selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_app:
                Uri packageURI = Uri.parse("package:com.example.brianb.demo1");
                startActivity(new Intent(Intent.ACTION_DELETE, packageURI));
                //Toast.makeText(this, "Menu item 2 selected", Toast.LENGTH_SHORT).show();
                break; */
        }
        return true;
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
                Intent intent =
                        new Intent(getBaseContext(), DisplayImage.class);
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
            mCapturedImageURI = Uri.parse(
                    savedInstanceState.getString("mCapturedImageURI"));
        }
    }


}
