package com.mesa.trueposture;

/* TO BE CALLED FROM MAINACTIVITY:

        Intent moreInfoIntent = new Intent(this, MainActivity.class);
        startService(moreInfoIntent);
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
//import android.os.IBinder;
import android.os.IBinder;
//import android.os.Binder;
import android.support.v4.app.NotificationCompat;
//import android.view.View;
import android.app.Service;
import android.util.Log;
import android.widget.Button;
import android.view.View;


public class Notification extends Service {
    private static final String TAG = "HelloService";
    private boolean isRunning  = false;
    double data = 5000;
    int waitSeconds = 5000;     //15seconds as default

    // Allows us to notify the user that something happened in the background
    NotificationManager notificationManager;

    // Used to track notifications
    int notifID = 33;

    // Used to track if notification is active in the task bar
    boolean isNotificActive = false;

    @Override
    public void onCreate() {
        Log.i(TAG, "Service onCreate");
        isRunning = true;

        //waitSeconds = getIntent().getStringExtra("secondInfo");
    }

    public void changeInterval(int second){
        waitSeconds = second;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Service onStartCommand");

        //Creating new thread for my service
        //Always write your long running tasks in a separate thread, to avoid ANR
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                int count =0;
//                //Your logic that service will perform will be placed here
//                //In this example we are just looping and waits for 1000 milliseconds in each loop.
//                while(true) {
//                    try {
//                        if (data > 1000) {
//                            while(data > 1000) {
//                                if (count == 3) //count 3 *5 seconds per loop =15 seconds
//                                    break;
//                                Thread.sleep(5000); //sleep 5 seconds
//                                count++;
//                            }
//                                if (count >= 3) {   //if 15 seconds of bad posture
//                                    showNotification();
//                                    count = 0;      //reset count
//                                    break;
//                                }
//                        }
//                            Thread.sleep(5000); //sleep for 5 seconds
//                    } catch (Exception e) {
//                    }
//                    if (isRunning) {
//                        Log.i(TAG, "Service running");
//                    }
//                }
//
//                //Stop service once it finishes its task
//                stopSelf();
//            }
//        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                int count =0;
                //Your logic that service will perform will be placed here
                //In this example we are just looping and waits for 1000 milliseconds in each loop.

                //if i am going to do a minute that is 60,000s
                //3 minutes = 180,000
                //5 minutes = 300,000

                while(true) {
                    try {
                        if (data > 1000) {
                            while(data > 1000) {
                                if (count == 3) //count 3 *5 seconds per loop =15 seconds
                                    break;
                                Thread.sleep(waitSeconds); //sleep 5 seconds
                                count++;
                            }
                            if (count >= 3) {   //if 15 seconds of bad posture
                                showNotification();
                                count = 0;      //reset count
                                break;
                            }
                        }
                        Thread.sleep(waitSeconds); //sleep for 5 seconds
                    } catch (Exception e) {
                    }
                    if (isRunning) {
                        Log.i(TAG, "Service running");
                    }
                }

                //Stop service once it finishes its task
                stopSelf();
            }
        }).start();


        return Service.START_STICKY;
    }


    @Override
    public void onDestroy() {   //call this at the end of the program

        isRunning = false;

        Log.i(TAG, "Service onDestroy");
    }

    public void showNotification() {

        // Builds a notification
        NotificationCompat.Builder notificBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Fix your posture")
                .setContentText("You're showing signs of bad posture!")
                .setTicker(" ")
                .setSmallIcon(R.drawable.app_icon_v3); //here we can set the notification icon

        //Here we tell where to open when we click on the notification
        Intent moreInfoIntent = new Intent(this, MainPage.class);

        // Used to stack tasks across activites so we go to the proper place when back is clicked
        TaskStackBuilder tStackBuilder = TaskStackBuilder.create(this);

        // Add all parents of this activity to the stack
        tStackBuilder.addParentStack(MainPage.class);

        // Add our new Intent to the stack
        tStackBuilder.addNextIntent(moreInfoIntent);

        // Define an Intent and an action to perform with it by another application
        // FLAG_UPDATE_CURRENT : If the intent exists keep it but update it if needed
        PendingIntent pendingIntent = tStackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // Defines the Intent to fire when the notification is clicked
        notificBuilder.setContentIntent(pendingIntent);

        // Gets a NotificationManager which is used to notify the user of the background event
        notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Post the notification
        notificationManager.notify(notifID, notificBuilder.build());

        // Used so that we can't stop a notification that has already been stopped
        isNotificActive = true;


    }
    // METHOD TO STOP THE NOTIFICATION
    public void stopNotification() {

        // If the notification is still active close it
        if (isNotificActive) {
            notificationManager.cancel(notifID);
            isNotificActive = false;
        }


    }
}
