package com.example.brianb.demo1;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.brianb.reminder.EditNotification;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ImportTenantContact extends AppCompatActivity {
    private ArrayList<Renter> renterList = new ArrayList<>();
    String imagePath;
    private static final String DATABASE_NAME = "ImageDb.db";
    private SQLiteDatabase db=null;

    private static final int RESULT_LOAD_IMAGE = 4;
    private static final String TAG = "RenterForm";

    CoordinatorLayout coordinatorLayout;
    FloatingActionButton btnSelectImage;
    ImageView imgView;
    DbRenter dbRenter;
    Spinner spinner;
    EditText renterEditDate;
    ImageButton rbtnDate;
    Button saveTenant, cancelTenant;
    String tenantAddress;
    Cursor c;
    int rHouseNumber;
    int rId;
    EditText rName, rPhone, rEmail;
    public ImageView rImage;

    DatabaseHelper helper = new DatabaseHelper(this);


    Spinner spinner1;
    static final int PICK_CONTACT = 1;

    EditText txtNo, txtName, tenantEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        initDB();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_form);

        getPermissionToReadUserContacts();
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
        
        loadSpinData();

        rbtnDate = (ImageButton)findViewById(R.id.rbtnDate);
        renterEditDate = (EditText)findViewById(R.id.renterEditDate);
        rbtnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowDate(v);
            }
        });

        btnSelectImage = (FloatingActionButton) findViewById(R.id.btnSelectImage);
        imgView = (AppCompatImageView) findViewById(R.id.renterImage);

        rName = (EditText)findViewById(R.id.renterName);
        rEmail = (EditText)findViewById(R.id.renterEmail);
        rPhone = (EditText)findViewById(R.id.renterPhone);
        saveTenant = (Button)findViewById(R.id.saveTenant);
        cancelTenant = (Button)findViewById(R.id.cancelTenant);

        //btnSelectImage.setOnClickListener(this);
        dbRenter = new DbRenter(this);
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(ImportTenantContact.this);
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
                                dialog.dismiss();
                            }
                        });
            }
        });
        saveTenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(v.getContext(), "Save Successful!", Toast.LENGTH_SHORT).show();
            }
        });

        cancelTenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    private void initDB() {
        dbRenter = new DbRenter(this);        //add images from database to images ArrayList
        for (Renter mi : dbRenter.getAllRenters()) {
            renterList.add(mi);
        }
    }

    private void activeGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    RESULT_LOAD_IMAGE);
            return;
        } //was initially: new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        intent.setType("image/*");

        startActivityForResult(intent, RESULT_LOAD_IMAGE);
        //startActivityForResult(Intent.createChooser(intent,"Select Image "), RESULT_LOAD_IMAGE);

    }


    private static final int READ_CONTACTS_PERMISSIONS_REQUEST = 1;

    // Called when the user is performing an action which requires the app to read the user's contacts
    public void getPermissionToReadUserContacts() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, READ_CONTACTS_PERMISSIONS_REQUEST);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
        }
    }

    // Callback with the request from calling requestPermissions(...)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == READ_CONTACTS_PERMISSIONS_REQUEST) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Read Contacts permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Read Contacts permission denied", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        txtNo = (EditText) findViewById(R.id.renterPhone);
        txtName = (EditText) findViewById(R.id.renterName);

        switch (reqCode) {
            case (PICK_CONTACT):
                if (resultCode == AppCompatActivity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c = getContentResolver().query(contactData, null, null, null, null);

                    if (c.moveToFirst()) {

                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);

                            phones.moveToFirst();

                            String cNumber = phones.getString(phones.getColumnIndex("data1"));
                            txtNo.setText(cNumber);
                        }

                        String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        txtName.setText(name);

                        Cursor emailCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null);

                        while (emailCursor.moveToNext()) {
                            String contactEmail = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                            int type = emailCursor.getInt(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
                            String s = (String) ContactsContract.CommonDataKinds.Email.getTypeLabel(getApplicationContext().getResources(), type, "");
                            tenantEmail = (EditText) findViewById(R.id.renterEmail);
                            tenantEmail.setText(contactEmail);
                        }
                        emailCursor.close();
                        c.close();
                    }
                }


            case RESULT_LOAD_IMAGE:

                if (reqCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imagePath = cursor.getString(columnIndex);
                    cursor.close();
                    //Also consider AppCompatImageView
                    Renter renter = new Renter(); //Was commented before
                    renter.setrPath(imagePath);
                    rImage = (ImageView)findViewById(R.id.renterImage);
                    rImage.setImageURI(selectedImage);

                }
                break;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_save, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_property:
                Renter renter = new Renter(rHouseNumber, rName.getText().toString(), rPhone.getText().toString(),  renterEditDate.getText().toString(),
                        tenantAddress, rEmail.getText().toString(), imagePath);
                dbRenter.addRenter(renter);
                startActivity(new Intent(ImportTenantContact.this, EditNotification.class));
                Toast.makeText(ImportTenantContact.this, "Save Successful!", Toast.LENGTH_SHORT).show();

                finish();
        }
        return true;
    }


    private void loadSpinData() {

        SQLiteDatabase db = new DatabaseHelper(this).getWritableDatabase();
        spinner = (Spinner) findViewById(R.id.spinner_renterAddress);

        // get a cursor from the database with an "_id" field
        c = db.rawQuery("SELECT id AS _id, address FROM propertiesTB", null);

        // make an adapter from the cursor
        String[] from = new String[]{"address"};
        int[] to = new int[]{android.R.id.text1};
        SimpleCursorAdapter sca = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, c, from, to);

        // set layout for activated adapter
        sca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // get xml file spinner and set adapter
        spinner = (Spinner) this.findViewById(R.id.spinner_renterAddress);
        spinner.setAdapter(sca);

        // set spinner listener to display the selected item id

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cursor cr = (Cursor) spinner.getSelectedItem();
                tenantAddress = cr.getString(c.getColumnIndex("address"));
                //tenantAddress = parent.getItemAtPosition(position).toString(); will not work in this case
                Toast.makeText(ImportTenantContact.this, tenantAddress + " selected ", Toast.LENGTH_LONG).show();

                String renterAddress = helper.searchAddress(tenantAddress);
                SQLiteDatabase hn = new DatabaseHelper(ImportTenantContact.this).getWritableDatabase();

                Cursor cursor = hn.rawQuery("select units from propertiesTB where address = ?",  new String[]{renterAddress});
                if (tenantAddress.equals(renterAddress)) {

                    if (cursor != null) {

                        if ((cursor.moveToFirst())) {
                            int test = cursor.getInt(cursor.getColumnIndex("units"));
                            do {
                                // int test = cursor.getInt(Integer.parseInt(cursor.getString(position)));
                                List<Integer> list = new ArrayList<>();
                                spinner1 = (Spinner) findViewById(R.id.spinner_renterHouseNumber);
                                // looping through all rows and adding to list
                                if (cursor.moveToFirst()) {
                                    int i = 1;
                                    while (i <= test) {
                                        list.add(i);
                                        cursor.moveToNext();
                                        i++;
                                    }
                                    //   list.add(cursor.getString(2));//adding 2nd column data
                                    ArrayAdapter<Integer> adapter = new ArrayAdapter<>(ImportTenantContact.this, android.R.layout.simple_spinner_dropdown_item, list);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinner1.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                    spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                         rHouseNumber = Integer.parseInt(parent.getItemAtPosition(position).toString());
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });
                                }
                            } while (cursor.moveToNext());
                        }
                        cursor.close();
                    }
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    public void onShowDate(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(),"Date Picker");
    }
}