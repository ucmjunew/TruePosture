package com.mesa.trueposture;

/**
 * Created by jrpa1 on 4/5/2016.
 */

    import android.graphics.Typeface;
    import android.os.Bundle;
    import android.support.design.widget.FloatingActionButton;
    import android.support.design.widget.Snackbar;
    import android.support.v7.app.AppCompatActivity;
    import android.support.v7.widget.Toolbar;
    import android.view.View;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.content.Intent;
    import android.widget.EditText;
    import android.widget.TextView;
    import android.widget.Toast;


    import static com.mesa.trueposture.R.id.bLogin;
    import static com.mesa.trueposture.R.id.bRemove;

public class StarterPage extends AbstractActivity {

    DatabaseHelper helper = new DatabaseHelper(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starter_page);

        Typeface myTypefaceTitle = Typeface.createFromAsset(getAssets(), "blippo.ttf");
        TextView tv = (TextView)findViewById(R.id.TVTitle);
        tv.setTypeface(myTypefaceTitle);

    }

    public void onButtonClick(View v) {            //this is the method we used when it onClick's display

        if (v.getId() == R.id.bLogin) {             //this is so the login button will have a function to do
            //seeing if the view object is the login
            EditText aUsername = (EditText) findViewById(R.id.TFusername);   //view what the text was
            String strUsername = aUsername.getText().toString();                    //change the text to string
            //check if the username and password matches
            EditText aPassword = (EditText) findViewById(R.id.TFpassword);   //view what the text was
            String strPassword = aPassword.getText().toString();                    //change the text to string
            //typed in by the user


            String password = helper.searchPass(strUsername);               //i think P needs to be capitalized
            if (strPassword.equals(password)) {
                //if they are equal
                Intent i = new Intent(StarterPage.this, MainPage.class);        //(context,class name of the new activity)
                                                                                /*we need to transfer the old activity information to the new activity*/
                i.putExtra("Username", strUsername);                           //parameters (string name, string value)
                //the "string name" can be anything as long as it matches the display.java file
                startActivity(i);
                finish();
            } else {
                //popup message
                //Toast temp = Toast.makeText(MainActivity.this, "Sorry. Your username and passwords do not match. Please Try Again", Toast.LENGTH_SHORT) ;          //three parameters(context(class name), text, duration)
                Toast temp = Toast.makeText(StarterPage.this, "Get your shit together, your username and passwords do not match! -Kelvin Lwin", Toast.LENGTH_SHORT);
                temp.show();                //and it should show the original menu again
            }
        }

        if (v.getId() == R.id.bSignUp) {                                      //if the user clicks on the sign up button
            Intent i = new Intent(StarterPage.this, SignUp.class);         //so now, the intent of this new activity is to open SignUp.class
            startActivity(i);
            //dont need a finish.
    }
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


}