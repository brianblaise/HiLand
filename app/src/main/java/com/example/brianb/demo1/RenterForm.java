package com.example.brianb.demo1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;


public class RenterForm extends AppCompatActivity {
    Spinner spinner;


    public Context mContext;
    String tenantAddress;
    Cursor c;
    DatabaseHelper helper = new DatabaseHelper(this);


    Spinner spinner1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_form);
        //showHouseNumber();
        loadSpinnerData();
    }

    //Todo redundant class this

    public void loadSpinnerData() {

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
                String renterAddress = helper.searchAddress(tenantAddress);
                SQLiteDatabase hn = new DatabaseHelper(RenterForm.this).getWritableDatabase();

                Cursor cursor = hn.rawQuery("select units from propertiesTB where address = ?", new String[]{renterAddress});
                if (tenantAddress.equals(renterAddress)) {

                    if (cursor != null) {

                        if ((cursor.moveToFirst())) {
                            int test = cursor.getInt(cursor.getColumnIndex("units"));
                            do {
                                // int test = cursor.getInt(Integer.parseInt(cursor.getString(position)));
                                List<String> list = new ArrayList<String>();
                                spinner1 = (Spinner) findViewById(R.id.spinner_renterHouseNumber);
                                // looping through all rows and adding to list
                                if (cursor.moveToFirst()) {
                                    int i = 1;
                                    while (i <= test) {
                                        list.add("House " + i);
                                        cursor.moveToNext();
                                        i++;
                                    }
                                    //   list.add(cursor.getString(2));//adding 2nd column data
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(RenterForm.this, android.R.layout.simple_spinner_dropdown_item, list);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinner1.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
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


}










    /* public void showDate(){
    super.onStart();
        rtxtDate =(EditText)findViewById(R.id.renterRowDate);
       try{
           rtxtDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
               @Override
               public void onFocusChange(View v, boolean hasFocus) {
                   if(hasFocus){
                       DateDialog dialog = new DateDialog(v);
                       android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                       dialog.show(ft, "DatePicker");
                   }
               }
           });
       }catch(NullPointerException e){
           Log.e("NullException Error ","Displayed: " + e.getMessage());
       }

    } */

