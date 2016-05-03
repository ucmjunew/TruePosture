package com.mesa.trueposture;

/**
 * Created by ucmjunew on 3/5/16.
 *
 * THERE IS THE JUMPING TO MAINPAGE I CAN DO TO MAKE IT MORE EFFICIENT
 */

    import android.app.Activity;
    import android.content.Intent;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.EditText;
    import android.widget.TextView;
    import android.widget.Toast;


public class SignUp extends Activity{

    DatabaseHelper helper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);        //change the setContentView to the layout you want
    }

    public void onSignUpClick(View v){
        if(v.getId() == R.id.bSignUpButton){            //it it matches we need all the values (name,email address, username,password, confirm password) and convert them to string
            /*this section is just reading in the text so we can edit it*/
            EditText name = (EditText)findViewById(R.id.TFname);
            EditText email = (EditText)findViewById(R.id.TFemail);
            EditText uname = (EditText)findViewById(R.id.TFuname);
            EditText pass1 = (EditText)findViewById(R.id.TFpass1);
            EditText pass2 = (EditText)findViewById(R.id.TFpass2);

            /*this section is going to get the texts from above and make it a string*/
            String nameStr = name.getText().toString();
            String emailStr = email.getText().toString();
            String unameStr = uname.getText().toString();
            String pass1Str = pass1.getText().toString();
            String pass2Str = pass2.getText().toString();

            /* now check if pass1 is the same to pass2*/
            if(!pass1Str.equals(pass2Str)){             //if the strings do not match, display error message
                //popup message
                //Toast pass = Toast.makeText(SignUp.this, "The Password you entered do not match! Please Try Again.", Toast.LENGTH_SHORT) ;          //three parameters(context(class name), text, duration)
                Toast pass = Toast.makeText(SignUp.this, "Kelvin thinks you are an idiot, your passwords do not match! Try Again!", Toast.LENGTH_SHORT) ;
                pass.show();                //and it should show the original menu again
            }
            //so if the passwords match, we will be ready to input the data into the database
            else{
                Contact c = new Contact();
                //set values of name...pass...
                c.setName(nameStr);
                c.setEmail(emailStr);
                c.setUsername(unameStr);
                c.setPassword(pass1Str);

                //before going into the inserting, let's check if the username exist already, so we don't have overlaps
                    boolean existingUser = helper.checkExistenceUsername(c);
                //before going into the inserting, let's check if the email exist already, so we ask them to reconsider
                    boolean existingEmail = helper.checkExistenceEmail(c);

                    if(existingUser == true) {                          //when it is true, that means a match was found
                        Toast.makeText(SignUp.this, "Username has already been taken, Please Try Again!", Toast.LENGTH_SHORT).show();
                    }
                    else if (existingEmail == true) {
                        Toast.makeText(SignUp.this, "Email already has an account registered to it.", Toast.LENGTH_SHORT).show();                //and it should show the original menu again
                    }
                    else {
                        helper.insertContact(c);             //we will be creating this method
                        Toast.makeText(SignUp.this, "User Information Stored ", Toast.LENGTH_SHORT).show();                //and it should show the original menu again
                        Intent i = new Intent(SignUp.this, StarterPage.class);
                        startActivity(i);
                        finish();
                    }

            }
        }
    }
}
