package com.example.brianb.demo1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

//Button on fragment to launch form to add tenant
public class RenterAdd extends AppCompatActivity {
    ListView renterList;
    List<Renter>listRenter;
    Button plusTenant;

    DbRenter db = new DbRenter(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_add);

        renterList = (ListView)findViewById(R.id.tenantList);
        showRenters();

        plusTenant = (Button)findViewById(R.id.plusTenant);
        plusTenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddTenant();
            }
        });
    }

    private void showRenters() {
        listRenter = db.getAllRenters();
        final RenterAdapter adapter = new RenterAdapter(this, listRenter);
        renterList.setAdapter(adapter);

       renterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

               RenterAdapter.ViewHolder viewHolder = (RenterAdapter.ViewHolder)view.getTag();

               int renterID = viewHolder.renter.getrId();
               Intent intent = new Intent(RenterAdd.this, RenterSingleView.class);
               intent.putExtra("ID", renterID);
               startActivity(intent);
           }
       });
    adapter.notifyDataSetChanged();
    }

    public void onAddTenant() {
        Intent intent = new Intent(RenterAdd.this, ChooseFillMethod.class);
        startActivity(intent);
        Toast.makeText(RenterAdd.this, "Choose detail source", Toast.LENGTH_LONG).show();
    }
}