package com.example.brianb.demo1;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DbHandler helper = new DbHandler(this);
    EditText et_name,et_email, et_pass, et_cpass;
    private String usrname, email_text, passText, cpass;
    Spinner spinner;
    TextView signin;
    Button register, button_success; //doesn't have to be "register"
    ArrayAdapter<CharSequence>adapter;
    String SpinnerValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final Spinner spinner;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }

        spinner = (Spinner) findViewById(R.id.spinner_select);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.account_type)) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(android.R.id.text1)).setText("");
                    ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount()-1; // you dont display last item. It is used as hint.
            }

        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getCount()); //display hint

        Spinner spinner1 = (Spinner)findViewById(R.id.spinner_select);
       /*adapter = ArrayAdapter.createFromResource(this, R.array.account_type, android.R.layout.simple_spinner_item); */
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
             // Toast.makeText(getBaseContext(), parent.getItemIdAtPosition(position)+" selected", Toast.LENGTH_LONG).show();
                SpinnerValue = (String)spinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        et_name = (EditText)findViewById(R.id.usrname);
        et_email= (EditText)findViewById(R.id.email_text);
        et_pass= (EditText)findViewById(R.id.passText);
        et_cpass= (EditText)findViewById(R.id.cpass);
        register = (Button)findViewById(R.id.register_button);

        //db = new dbHandler(getApplicationContext());
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(); // call when the button is clicked to validate the input
            }
        });
    }
    public void to_login(View view) {
                Intent ii = new Intent();
                ii.setClass(MainActivity.this, SigninActivity.class);
                startActivity(ii);
            }

    public void register(){
        usrname = et_name.getText().toString().trim();
        email_text =et_email.getText().toString().trim();
        passText = et_pass.getText().toString().trim();
        cpass = et_cpass.getText().toString().trim();

        Person p = new Person();
        p.setName(usrname);
        p.setEmail(email_text);
        p.setPass(passText);

        helper.insertInfo(p);
       // initialize(); //initialize the input to string variables
    if (!validate()){
        Toast.makeText(this, "Try Again", Toast.LENGTH_SHORT).show();
            }
        else{
        onSignupSuccess();
            }
    }

    public boolean validate(){
        boolean valid = true;
        if(usrname.isEmpty()||usrname.length()>32){
            et_name.setError("Please Enter Name within 32 Characters");
            valid= false;
        }
        if (email_text.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(email_text).matches()){
            et_email.setError("Please Enter a Valid Email");
            valid = false;
        }
        if (passText.isEmpty()){
            et_pass.setError("Please Enter Valid Password");
            valid = false;
        }
        if (cpass.isEmpty()){
            et_cpass.setError("Please Re-Enter Password");
            valid = false;
        }
        if (!et_cpass.getText().toString().equals(et_pass.getText().toString())){
            et_cpass.setError("Passwords don't match, Please Try Again");
            valid = false;
        }
        return valid;
    }

    public void onSignupSuccess(){
            button_success = (Button)findViewById(R.id.register_button);
            button_success.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  switch(SpinnerValue){
                      case "Landlord":
                          startActivity(new Intent(MainActivity.this, TabActivity.class));
                          break;
                      case "Renter":
                          startActivity(new Intent(MainActivity.this, RenterHome.class));
                          break;
                  }
                }
            });
    }
}