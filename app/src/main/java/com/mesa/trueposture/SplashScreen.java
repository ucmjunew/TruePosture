package com.mesa.trueposture;/*
To put in the manifest


<activity
        android:name=".SplashActivity"
        android:theme="@android:style/Theme.DeviceDefault.Light.NoActionBar">
        <intent-filter>
            <action android:name="android.intent.action.MAIN" />
            <category android:name="android.intent.category.LAUNCHER" />
        </intent-filter>
    </activity>

	
*/

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity {
    DatabaseHelper helper = new DatabaseHelper(this);
	protected boolean _active = true;
    private final int SPLASH_DISPLAY_LENGTH = 3000;


	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.splashscreen);

        if(helper.checkIfUserLoggedIn()==false){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(SplashScreen.this, StarterPage.class);
                    startActivity(mainIntent);
                    SplashScreen.this.finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
        }
        else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    /* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(SplashScreen.this, MainPage.class);
                    startActivity(mainIntent);
                    SplashScreen.this.finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
        }

}
	

}