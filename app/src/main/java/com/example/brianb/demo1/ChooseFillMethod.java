package com.example.brianb.demo1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseFillMethod extends AppCompatActivity implements View.OnClickListener {
    Button importFromContacts;
    Button fillTenantForm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_fill_method);

        importFromContacts = (Button)findViewById(R.id.importFromContacts);
        fillTenantForm = (Button)findViewById(R.id.fillTenantForm);

        importFromContacts.setOnClickListener(this);
        fillTenantForm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
    switch(v.getId()){
        case R.id.importFromContacts:
        Intent intent = new Intent(ChooseFillMethod.this, ImportTenantContact.class);
        startActivity(intent);
        break;
        case R.id.fillTenantForm:
            Intent il = new Intent(ChooseFillMethod.this, RenterForm.class);
            startActivity(il);
            break;
    }
    }
}
