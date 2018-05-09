package com.example.brianb.demo1;

import android.*;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ProjectForm extends AppCompatActivity {
    private ArrayList<Properties> propertyList = new ArrayList<>();
    EditText edtId, edtAddress, edtUnitno;
    Button btnAdd, btnUpdate, btnDelete;
    ListView listProperties;
    String propType;
    Cursor cursor;
    String imagePath;
    AppCompatImageView propertyImage;
    AppCompatImageView imgView;
    private static final int RESULT_LOAD_IMAGE = 4;

    List<Properties> data = new ArrayList<Properties>();
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_form);

        initDB();
        db = new DatabaseHelper(this);

        Spinner spinner = (Spinner) findViewById(R.id.spinnerProperty);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView) v.findViewById(android.R.id.text1)).setText("");
                    ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }
                return v;
            }

            @Override
            public int getCount() {
                return super.getCount() - 1; // you don't display last item. It is used as hint.
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.add("House(s)");
        adapter.add("Land");        //// TODO: 05-Jun-17 populate spinner 
        adapter.add("Select Property Type");
        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getCount()); //display hint
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0 || position == 1) {
                    propType = parent.getItemAtPosition(position).toString();
                    Toast.makeText(ProjectForm.this, propType + " Selected", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //End of spinner

        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        listProperties = (ListView) findViewById(R.id.propertyList);

        edtAddress = (EditText) findViewById(R.id.edtAddress);
        edtUnitno = (EditText) findViewById(R.id.edtUnitno);
        propertyImage = (AppCompatImageView) findViewById(R.id.propertyImage);

        refreshData();

        listProperties.setVisibility(View.INVISIBLE);

        propertyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(ProjectForm.this);
                dialog.show();
                dialog.setContentView(R.layout.custom_dialog_box);
                dialog.setTitle("Select Option");
                Button btnExit = (Button) dialog.findViewById(R.id.btnExit);
                btnExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "Enter Id to delete", Toast.LENGTH_LONG).show();
                Properties property = new Properties(propType,
                        edtAddress.getText().toString(), edtUnitno.getText().toString(), imagePath);
                db.updateProperty(property);
                refreshData();
                finish();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Properties property = new Properties(propType,
                                edtAddress.getText().toString(), edtUnitno.getText().toString(), imagePath);
                db.deleteProperty(property);
                refreshData();
                finish();
            }
        });
    }
    private void initDB() {
        db = new DatabaseHelper(this);        //add images from database to images ArrayList
        for (Properties mi : db.getAllProperties()) {
            propertyList.add(mi);
        }
    }
        private void activeGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    RESULT_LOAD_IMAGE);
            return;
        }
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        switch (reqCode) {
            case RESULT_LOAD_IMAGE:
                if (resultCode == RESULT_OK && null != data) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imagePath = cursor.getString(columnIndex);
                    cursor.close();
                    Properties property = new Properties();
                    property.setpPath(imagePath);
                    imgView = (AppCompatImageView) findViewById(R.id.propertyImage);
                    imgView.setImageURI(selectedImage);
                }
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
                    Properties property = new Properties(propType, edtAddress.getText().toString(),
                                                        edtUnitno.getText().toString(), imagePath);
                db.addProperty(property);
                startAct();
                Toast.makeText(ProjectForm.this, "Save Successful!", Toast.LENGTH_SHORT).show();
                refreshData();
                finish();
                    }
                    return true;
                }

    private void startAct() {
        Intent intent = new Intent(ProjectForm.this, DisplayProject.class);
        startActivity(intent);
    }

    private void refreshData(){
        data = db.getAllProperties();
        final PropertiesAdapter adapter = new PropertiesAdapter(ProjectForm.this, data);
        listProperties.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        listProperties.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if(cursor.moveToFirst()) {
                        do {
                            cursor = (Cursor)listProperties.getSelectedItem();
                            String selected = cursor.getString(cursor.getColumnIndex("address"));
                            Toast.makeText(ProjectForm.this, selected + " Selected", Toast.LENGTH_LONG).show();
                        } while (cursor.moveToNext());
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

}
