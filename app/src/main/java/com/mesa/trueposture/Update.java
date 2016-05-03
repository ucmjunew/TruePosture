package com.mesa.trueposture;

/**
 * Created by ucmjunew on 3/13/16.
 */
    import android.content.Context;
    import android.content.Intent;
    import android.os.Bundle;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.EditText;
    import android.widget.TextView;
    import android.widget.Toast;

public class Update extends Display{
    DatabaseHelper helper = new DatabaseHelper(this);
    private static final int MY_PASSWORD_DIALOG_ID = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);
        //case MY_PASSWORD_DIALOG_ID:
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View layout = inflater.inflate(R.layout.password_dialog, (ViewGroup) findViewById(R.id.root));
        final EditText password1 = (EditText) layout.findViewById(R.id.EditText_Pwd1);
        final EditText password2 = (EditText) layout.findViewById(R.id.EditText_Pwd2);
        final TextView error = (TextView) layout.findViewById(R.id.TextView_PwdProblem);

        // TODO: Create Dialog here and return it (see subsequent steps)
    }

    public void onUpdateClick(View v){
        if(v.getId() == R.id.bChange){            //it it matches we need all the values (name,email address, username,password, confirm password) and convert them to string
            /*this section is just reading in the text so we can edit it*/
            EditText oldPass = (EditText)findViewById(R.id.TFoldPass);
            EditText pass1 = (EditText)findViewById(R.id.TFnewPass1);
            EditText pass2 = (EditText)findViewById(R.id.TFnewPass2);

            /*this section is going to get the texts from above and make it a string*/
            String oldPassStr = oldPass.getText().toString();
            String pass1Str = pass1.getText().toString();
            String pass2Str = pass2.getText().toString();

            /* now check if pass1 is the same to pass2*/
            if(!pass1Str.equals(pass2Str)){             //if the strings do not match, display error message
                Toast pass = Toast.makeText(Update.this, "The Two New Passwords Do Not Match", Toast.LENGTH_SHORT) ;
                pass.show();                //and it should show the original menu again
            }

            //so if the passwords match, we will be ready to input the data into the database
            else{       //so if the two new passwords do match, figure out if the old password was correct
                String username = getIntent().getStringExtra("Username");       //this corresponds with MainActivity.java (i.putExtra("Username", strUsername)) the cases have to be identical
                String password = helper.searchPass(username);               //i think P needs to be capitalized
                //Toast.makeText(Update.this, "Password " + password, Toast.LENGTH_SHORT).show();
                if (oldPassStr.equals(password)) {
                    //Toast.makeText(Update.this, "Allowed Change of Password", Toast.LENGTH_SHORT).show();

                    String IDfound = helper.searchID(username);             //we will be creating this method
                    String bringINid = helper.updatePassword(IDfound, oldPassStr, pass1Str);
                    //Toast.makeText(Update.this, "New Change of Password at ID: " + IDfound, Toast.LENGTH_SHORT).show();                //and it should show the original menu again
                    //Toast.makeText(Update.this, "there was a change of password. " + bringINid, Toast.LENGTH_SHORT).show();
                    Toast.makeText(Update.this, "password changed : " + bringINid, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Update.this, Settings.class);
                    startActivity(i);
                    finish();
                }
                else {                                    //username and passwords do not match
                    //popup message
                    //Toast temp = Toast.makeText(MainActivity.this, "Sorry. Your username and passwords do not match. Please Try Again", Toast.LENGTH_SHORT) ;          //three parameters(context(class name), text, duration)
                    Toast temp = Toast.makeText(Update.this, "Your Old Password is Incorrect", Toast.LENGTH_SHORT);
                    temp.show();                //and it should show the original menu again
                }

            }
        }
    }

}
