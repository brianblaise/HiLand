package com.example.brianb.demo1;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class SigninActivity extends AppCompatActivity{
    DbHandler helper = new DbHandler(this);
    Button et_email_sign_in_button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
        }

    }

    public void onSignin(View view) {
        if (view.getId() == R.id.email_sign_in_button){

            AutoCompleteTextView a= (AutoCompleteTextView)findViewById(R.id.email);
            String str = a.getText().toString();
            EditText b = (EditText) findViewById(R.id.password);
            String passw = b.getText().toString();

            String passwd = helper.searchPass(str);

            if(passw.equals(passwd)){
                Intent ini = new Intent(SigninActivity.this, TabActivity.class);
                ini.putExtra("Username", str);
                startActivity(ini);
            }
            else{
                Toast fToast = Toast.makeText(SigninActivity.this, "Email and Password don't match", Toast.LENGTH_LONG);
                fToast.show();
            }
        }
    }
}
