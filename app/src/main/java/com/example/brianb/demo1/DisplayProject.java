package com.example.brianb.demo1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BrianB on 01-May-17.
 */

public class DisplayProject extends AppCompatActivity{

    List<Properties> propertyList = new ArrayList();
    ListView propertyList1;
    DatabaseHelper db1 = new DatabaseHelper(this);
    Button plusProperty;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displayproject);

        propertyList1 = (ListView)findViewById(R.id.propertyList1);
        showProperties();

        plusProperty = (Button)findViewById(R.id.plusProperty);
        assert plusProperty != null;
        plusProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(DisplayProject.this, ProjectForm.class);
                startActivity(intent);
                onAddTenant();
            }
        });
    }
    private void showProperties(){ //populate ListView method
        propertyList = db1.getAllProperties();
        final PropertiesAdapter displayList = new PropertiesAdapter(this, propertyList);
        propertyList1.setAdapter(displayList);
        propertyList1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /*   Object obj = propertyList1.getAdapter().getItem(position); String selected = obj.toString(); Toast.makeText(DisplayProject.this, selected+ " selected", Toast.LENGTH_LONG).show(); */
                       Object obj = displayList.getItem(position);
                        String selected = obj.toString();
               /*       Cursor cursor = (Cursor)parent.getItemAtPosition(position);
                String propertyAddress = cursor.getString(cursor.getColumnIndex("address")); */
                        Toast.makeText(getApplicationContext(), selected + " Selected", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void onAddTenant(){
        Toast.makeText(DisplayProject.this, "Add a Property", Toast.LENGTH_SHORT).show();
    }
}
