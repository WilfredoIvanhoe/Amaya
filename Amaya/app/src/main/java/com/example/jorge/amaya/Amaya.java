package com.example.jorge.amaya;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;


public class Amaya extends ActionBarActivity {

    private EditText emailTextView;
    private EditText passwordTextView;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private String emailVal;
    private String passwordVal;

    public static final String PREFERENCES = "UserData" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        String mail = sharedPref.getString("email", null);
        if(mail != null){
            Log.i("Not:","NULL");
            setContentView(R.layout.activity_amaya_registered);
        }
        else{
            setContentView(R.layout.activity_amaya);
            emailTextView = (EditText)findViewById(R.id.email_field);
            passwordTextView = (EditText)findViewById(R.id.password_field);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_amaya, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void submitForm(View w){
        emailVal = emailTextView.getText().toString();
        passwordVal = passwordTextView.getText().toString();
        if(isValidEmail(emailVal) && !passwordVal.matches("")) {
            EditText myEditText = (EditText) findViewById(R.id.password_field);
            InputMethodManager imm = (InputMethodManager)getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);

            Intent intent = new Intent(this, RegistrationIntentService.class);
            intent.putExtra("email", emailVal);
            intent.putExtra("password", passwordVal);
            startService(intent);
            
            setContentView(R.layout.activity_amaya_registered);
        }
    }
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
