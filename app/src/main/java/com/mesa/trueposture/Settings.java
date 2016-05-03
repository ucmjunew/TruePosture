package com.mesa.trueposture;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.InputType;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.*;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.content.Intent;
import com.mesa.trueposture.*;

/**
 * Created by June Wu on 4/18/2016.
 */
public class Settings extends MainPage {
    DatabaseHelper helper = new DatabaseHelper(this);
    String m_Text = "";
    String username;
    Notification note = new Notification();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
/*
        Switch switchNotification = (Switch) findViewById(R.id.switchNotification);
        switchNotification.setOnClickListener(new CompoundButton.OnCheckedChangeListener(){

            public void onCheckChanged(CompoundButton buttonView, boolean isChecked){
                if(isChecked){
                    Toast.makeText(getApplicationContext(), "ON", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "OFF", Toast.LENGTH_LONG).show();
                }
            }
        }
*/

        username = getIntent().getStringExtra("Username");       //this corresponds with MainActivity.java (i.putExtra("Username", strUsername)) the cases have to be identical

    }
    public void onSignOutClick(View v) {
        if (v.getId() == R.id.bSignOut) {            //it it matches we need all the values (name,email address, username,password, confirm password) and convert them to string
            helper.signOutIdentity();
        }
        Toast.makeText(Settings.this, "So Far, So Good!", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(Settings.this, StarterPage.class);
        ActivityCompat.finishAffinity(this);
        startActivity(i);
        finish();
    }

    public void onBackClick(View v) {
        if (v.getId() == R.id.bBackButton) {            //it it matches we need all the values (name,email address, username,password, confirm password) and convert them to string
            Intent i = new Intent(Settings.this, MainPage.class);
            finish();
        }
    }

    public void onButtonClick(View v) {            //this is the method we used when it onClick's display
        if (v.getId() == R.id.bRemove) {             //this is so the login button will have a function to do

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Enter Password");

            // Set up the input
            final EditText input = new EditText(this);
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    m_Text = input.getText().toString();
                    checkPasswordCheck();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
    }

    public void checkPasswordCheck(){

        String password = helper.searchPass(username);               //i think P needs to be capitalized
        //Toast.makeText(username, Toast.LENGTH_SHORT).show();

        if (m_Text.equals(password)) {
            helper.deleteContact(username);
            Toast.makeText(Settings.this, "User Information Deleted", Toast.LENGTH_SHORT).show();                //and it should show the original menu again

            Intent i = new Intent(Settings.this, StarterPage.class);         //so now, the intent of this new activity is to open SignUp.class
            startActivity(i);

        } else {
            //popup message
            //Toast temp = Toast.makeText(MainActivity.this, "Sorry. Your username and passwords do not match. Please Try Again", Toast.LENGTH_SHORT) ;          //three parameters(context(class name), text, duration)
            Toast temp = Toast.makeText(Settings.this, "Password was Incorrect, Account was not Deleted.", Toast.LENGTH_SHORT);
            temp.show();                //and it should show the original menu again
        }

    }
/*
    public void onDisplayClick(View v) {
        if (v.getId() == R.id.bDisplayButton) {            //it it matches we need all the values (name,email address, username,password, confirm password) and convert them to string
            Intent i = new Intent(Settings.this, Display.class);
            ActivityCompat.finishAffinity(this);
            startActivity(i);
            //finish();
        }
    }
    */

//    private void checkPassword(String strPassword) {
//        String password = helper.searchPass("jrpa12");               //i think P needs to be capitalized
//
//        if (strPassword.equals(password)) {
//
//            helper.deleteContact("jrpa12");
//            Toast temp = Toast.makeText(Settings.this, "User Information Deleted", Toast.LENGTH_SHORT);
//            temp.show();                //and it should show the original menu again
//
//        } else {
//            //popup message
//            //Toast temp = Toast.makeText(MainActivity.this, "Sorry. Your username and passwords do not match. Please Try Again", Toast.LENGTH_SHORT) ;          //three parameters(context(class name), text, duration)
//            Toast temp = Toast.makeText(Settings.this, "Information do not line up!", Toast.LENGTH_SHORT);
//            temp.show();                //and it should show the original menu again
//        }
//    }

    public void onUpdateClick(View v) {
        if (v.getId() == R.id.bUpdate) {            //it it matches we need all the values (name,email address, username,password, confirm password) and convert them to string
            Intent i = new Intent(Settings.this, Update.class);
            i.putExtra("Username", username);                           //parameters (string name, string value)
            Toast.makeText(Settings.this, username, Toast.LENGTH_LONG).show();
            startActivity(i);
        }
    }

    public void onIntervalClick (View view){
        int second = 5000;                  //default value
        Intent i = new Intent(Settings.this, Notification.class);

        switch (view.getId()){
            case R.id.notify1:                              //20,000 = 60,000/3
                second = 20000;
                /*
                i.putExtra("secondInfo", second);
                startService(i);
                */

                note.changeInterval(second);
                break;
            case R.id.notify2:                              //60,000 = 180,000/3
                second = 60000;

                note.changeInterval(second);
                break;
            case R.id.notify3:                              //100,000 = 120,000/3
                second = 100000;

                note.changeInterval(second);
                break;
        }
    }


}



