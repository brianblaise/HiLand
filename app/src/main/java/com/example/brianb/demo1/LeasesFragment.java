package com.example.brianb.demo1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.brianb.reminder.*;

/**
 * Created by BrianB on 29-Apr-17.
 */
public class LeasesFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.leases, container, false);

        Button addTenant = (Button)v.findViewById(R.id.addTenant);
        Button getNotified = (Button)v.findViewById(R.id.btnNotify);
        Button viewTenant = (Button)v.findViewById(R.id.viewTenants);
        Button msgbutton1 = (Button)v.findViewById(R.id.msgbutton1);

        addTenant.setOnClickListener(this);
        getNotified.setOnClickListener(this);
        viewTenant.setOnClickListener(this);
        msgbutton1.setOnClickListener(this);

        return v;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addTenant:
                startActivity(new Intent(getActivity(), ChooseFillMethod.class));
                Toast.makeText(getActivity(), "Choose detail source", Toast.LENGTH_LONG).show();
                break;
            case R.id.btnNotify:
                startActivity(new Intent(getActivity(), ReminderHomeActivity.class));
                break;
            case R.id.viewTenants:
                startActivity(new Intent(getActivity(), RenterAdd.class));
                break;
            case R.id.msgbutton1:
                startActivity(new Intent(getActivity(), ConversationListActivity.class));
                break;
        }
    }
}
