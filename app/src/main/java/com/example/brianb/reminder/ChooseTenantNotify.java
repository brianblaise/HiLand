package com.example.brianb.reminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.brianb.demo1.DbRenter;
import com.example.brianb.demo1.R;
import com.example.brianb.demo1.Renter;
import com.example.brianb.demo1.RenterSingleView;

import java.util.List;

public class ChooseTenantNotify extends AppCompatActivity {
    ListView renterList;
    List<Renter> listRenter;

    DbRenter db = new DbRenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_tenant_notify);

        renterList = (ListView)findViewById(R.id.chooseTenantList);
        showRenters();
    }
    private void showRenters() {

        listRenter = db.getAllRenters();
        final ChooseTenantAdapter adapter = new ChooseTenantAdapter(this, listRenter);
        renterList.setAdapter(adapter);

        renterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ChooseTenantAdapter.ChooseViewHolder viewHolder = (ChooseTenantAdapter.ChooseViewHolder)view.getTag();

                int renterID = viewHolder.renter.getrId();
                Intent intent = new Intent(ChooseTenantNotify.this, RenterSingleView.class);
                intent.putExtra("ID", renterID);
                startActivity(intent);
            }
        });
        adapter.notifyDataSetChanged();
    }

}
