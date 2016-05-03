package com.mesa.trueposture;

/**
 * Created by ucmjunew on 3/5/16.
 * when you get everything going, remember to delete the information off sensor too, not just contacts
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.Vector;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
/*
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.http.OkHttpClientFactory;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncContext;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncTable;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.ColumnDataType;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.MobileServiceLocalStoreException;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.SQLiteLocalStore;
import com.microsoft.windowsazure.mobileservices.table.sync.synchandler.SimpleSyncHandler;
import com.squareup.okhttp.OkHttpClient;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.security.KeyStore;
import static com.microsoft.windowsazure.mobileservices.table.query.QueryOperations.val;
import android.os.AsyncTask;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;

*/
public class DatabaseHelper extends SQLiteOpenHelper{
    SQLiteDatabase db;
    /**
     * Mobile Service Client reference
     */
 //   private MobileServiceClient mClient;

    /**
     * Mobile Service Table used to access data
     */
    //private MobileServiceTable<ToDoItem> mToDoTable;

    //Offline Sync
    /**
     * Mobile Service Table used to access and Sync data
     */
    //private MobileServiceSyncTable<ToDoItem> mToDoTable;

    /**
     * Adapter to sync the items list with the view
     */
    //private ToDoItemAdapter mAdapter;

    /**
     * EditText containing the "New To Do" text
     */
    private EditText mTextNewToDo;

    /**
     * Progress spinner to use for table operations
     */
    private ProgressBar mProgressBar;

    /**
     * Initializes the activity
     */


    //private static int deleteAmount=0;
    private static int count=0;
    private static int mostRecentIdentity=-99999;                                   //default identity

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_OFFICIAL = "official.db";
    private static final String TABLE_CONTACTS = "contacts";
    private static final String COLUMN_ID = "id";       //identification            //column 0
    private static final String COLUMN_NAME = "name";                               //column 1
    private static final String COLUMN_EMAIL = "email";                             //column 2
    private static final String COLUMN_USERNAME = "username";                       //column 3
    private static final String COLUMN_PASSWORD = "password";                       //column 4

    private static final String TABLE_SENSORS = "sensors";
    private static final String COLUMN_IDENTITY = "identity";
    private static final String COLUMN_date = "date";
    private static final String COLUMN_time = "time";
    private static final String COLUMN_resistor1 = "resistor1";
    private static final String COLUMN_resistor2 = "resistor2";
    private static final String COLUMN_magnet = "magnet";
    private static final String COLUMN_averageSensor = "averageSensor";

    private static final String TABLE_CREATE_CONTACTS =
            "CREATE TABLE Contacts (" +
                    "id int NOT NULL ," +
                    "name text NOT NULL , " +
                    "email text NOT NULL , " +
                    "username text NOT NULL , " +
                    "password text NOT NULL);";

    private static final String TABLE_CREATE_SENSORS =
            "CREATE TABLE Sensors (" +
                    "identity int NOT NULL ," +
                    "date text NOT NULL ," +
                    "time text NOT NULL , " +
                    "resistor1 double NOT NULL , " +
                    "resistor2 double NOT NULL , " +
                    "magnet double NOT NULL , " +
                    "averageSensor double NOT NULL);";

    /*
    private static final String TABLE_CREATE = "create table contacts (id integer primary key not null , " +
            "name text not null , email text not n                                                                                                                                  ull , uname text not null , pass text not null);";
    */
    //create a contructor to the DatabaseHelper, there is no return type for contructors
    public DatabaseHelper(Context context) {
        super(context, DATABASE_OFFICIAL, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {           //create the database
        db.execSQL(TABLE_CREATE_CONTACTS);
        db.execSQL(TABLE_CREATE_SENSORS);
        /*
        try {
            // Create the Mobile Service Client instance, using the provided
            // Mobile Service URL and key
            mClient = new MobileServiceClient("https://trueposture.database.windows.net", this).withFilter(new ProgressFilter());
            mClient.setAndroidHttpClientFactory(new OkHttpClientFactory() {
            */
                /**
                 * makes an http request to the server for this application and
                 * passes the response to progress filter.
                 *
                 * @return
                 */
    //            @Override
    //            public OkHttpClient createOkHttpClient() {
    //                OkHttpClient client = new OkHttpClient();
    //                client.setReadTimeout(20, TimeUnit.SECONDS);
    //                return client;
    //            }
    //        });

            // Get the Mobile Service Table instance to use
            //mToDoTable = mClient.getTable(ToDoItem.class);

            // Offline Sync
            //mToDoTable = mClient.getSyncTable("ToDoItem", ToDoItem.class);

            //Init local storage
    //        initLocalStore().get();
    //        syncAsync();

    //        mTextNewToDo = (EditText) findViewById(R.id.textNewToDo);

            // Create an adapter to bind the items with the view
    //        mAdapter = new ToDoItemAdapter(this, R.layout.row_list_to_do);
    //        ListView listViewToDo = (ListView) findViewById(R.id.listViewToDo);
    //        listViewToDo.setAdapter(mAdapter);

            // Load the items from the Mobile Service
    //        refreshItemsFromTable();

    //    } catch (MalformedURLException e) {
    //        createAndShowDialog(new Exception("There was an error creating the Mobile Service. Verify the URL"), "Error");
    //    } catch (Exception e){
    //        createAndShowDialog(e, "Error");
    //    }
    //}
}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //execute the query above
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_SENSORS);

        onCreate(db);
    }

    public void signOutIdentity(){
        mostRecentIdentity = -99999;                        //by making it -99999, it will be our default parameter to check that there is no identity turned on at the moment
    }

    public void insertSensorReadings(Sensor sensorInformation){
        db = this.getWritableDatabase();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        //get current date time with Calendar()
        Calendar cal = Calendar.getInstance();
        String dateANDtime = dateFormat.format(cal.getTime());

        String date = dateANDtime.substring(0,10);
        int getHour = Integer.parseInt(dateANDtime.substring(11,13))+1;
        int getMinute = Integer.parseInt(dateANDtime.substring(14,16))+30;
        int getSecond = Integer.parseInt(dateANDtime.substring(17,19));

        if(getHour == 24){
            getHour = 00;
        }

        String time = "" + getHour + ":" + getMinute + ":" + getSecond;

        ContentValues values = new ContentValues();
        values.put(COLUMN_IDENTITY , mostRecentIdentity);
        values.put(COLUMN_date , date);
        values.put(COLUMN_time, time);
        values.put(COLUMN_resistor1 , sensorInformation.getResistor1());
        values.put(COLUMN_resistor2 , sensorInformation.getResistor2());
        values.put(COLUMN_magnet, sensorInformation.getMagnet());
        //double avgSense = (sensorInformation.getResistor1()+sensorInformation.getResistor2())/2;
        values.put(COLUMN_averageSensor, sensorInformation.getAverage());

        db.insert(TABLE_SENSORS, null, values);
        db.close();//close the database
    }

    public void insertContact(Contact c){

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //String query = ("SELECT * " +
        //        "FROM contacts;");

        //String query = "select * from contacts";
        //Cursor cursor = db.rawQuery(query, null);
        //pass count as id
        //int count = cursor.getCount()+deleteAmount;

        count++;

        values.put(COLUMN_ID, String.valueOf(count));
        values.put(COLUMN_NAME , c.getName());
        values.put(COLUMN_EMAIL , c.getEmail());
        values.put(COLUMN_USERNAME , c.getUsername());
        values.put(COLUMN_PASSWORD, c.getPassword());

        db.insert(TABLE_CONTACTS, null, values);            //this will insert contact object into database

        /*
        these were values just to see if the inputs were going in

        ContentValues fakeValues = new ContentValues();
        fakeValues.put(COLUMN_date, "date");
        fakeValues.put(COLUMN_time , "time");
        fakeValues.put(COLUMN_IDENTITY , String.valueOf(count));
        fakeValues.put(COLUMN_resistor1, "resist");
        fakeValues.put(COLUMN_resistor2, "resist");
        fakeValues.put(COLUMN_magnet, "magnet");
        */

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        //get current date time with Calendar()

        Calendar cal = Calendar.getInstance();
        String dateANDtime = dateFormat.format(cal.getTime());
        String date = dateANDtime.substring(0,10);
        int getHour = Integer.parseInt(dateANDtime.substring(11,13))+1;
        int getMinute = Integer.parseInt(dateANDtime.substring(14,16))+30;
        int getSecond = Integer.parseInt(dateANDtime.substring(17,19));

        if(getHour == 24){
            getHour = 00;
        }

        String time = "" + getHour + ":" + getMinute + ":" + getSecond;

        //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        //get current date time with Date()
        //Date dateT = new Date();
        //String date = dateFormat.format(dateT);

        //get current date time with Calendar()
        //Calendar cal = Calendar.getInstance();
        //String time = dateFormat.format(cal.getTime());

        /*dummy values*/
        Random random = new Random();
        double rand = random.nextDouble();
        double resist1 = rand * 3.3;
        rand = random.nextDouble();
        double resist2 = rand * 5.8;

        ContentValues fakeValues = new ContentValues();
        fakeValues.put(COLUMN_IDENTITY , String.valueOf(count));
        fakeValues.put(COLUMN_date , date);
        fakeValues.put(COLUMN_time, time);
        fakeValues.put(COLUMN_resistor1 , resist1);
        fakeValues.put(COLUMN_resistor2 , resist2);
        fakeValues.put(COLUMN_magnet, "magnet");
        double avgSense = (resist1+resist2)/2;
        values.put(COLUMN_averageSensor, avgSense);

        db.insert(TABLE_SENSORS, null, fakeValues);
        db.close();//close the database
    }

    public String searchPass(String Username){
        db = this.getReadableDatabase();                //reading databse
        //query to fetch the value
        String query = ("SELECT username, password, id " +
                "FROM " + TABLE_CONTACTS);

        Cursor cursor = db.rawQuery(query,null);
        String Suser, Spass;

        Spass = "not found";                            //it will display this text, if the password was not found

        if(cursor.moveToFirst()){
            do{
                Suser = cursor.getString(0);

                if(Suser.equals(Username)){
                    Spass = cursor.getString(1);
                    mostRecentIdentity = Integer.parseInt(cursor.getString(2));
                    break;
                }
            }
            while(cursor.moveToNext());
        }

        return Spass;
    }

    public void deleteContact(String uname) {       //delete user's information
        //DELETE * FROM TABLE_NAME WHERE username=uname

        db = this.getReadableDatabase();
        String query = ("SELECT id " +
                "FROM " + TABLE_CONTACTS +
                " WHERE username = '" + uname + "';");
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        String Sid = cursor.getString(0);
        db.close();

        db = this.getWritableDatabase();
        String[] whereArgsContact ={uname};
        db.delete(TABLE_CONTACTS, COLUMN_USERNAME + "=?", whereArgsContact);

        String[] whereArgsSensor ={Sid};
        db.delete(TABLE_SENSORS, COLUMN_IDENTITY + "=?", whereArgsSensor);

        db.close();
    }

    public String searchID(String username) {
        db = this.getReadableDatabase();                //reading databse
        //query to fetch the value
        String query = ("SELECT username, id " +
                "FROM " + TABLE_CONTACTS);

        Cursor cursor = db.rawQuery(query,null);
        String Suser, Sid;

        Sid = "not found";                            //it will display this text, if the password was not found

        if(cursor.moveToFirst()){
            do{
                Suser = cursor.getString(0);

                if(Suser.equals(username)){
                    Sid = cursor.getString(1);
                    //mostRecentIdentity = Integer.parseInt(cursor.getString(2));
                    break;
                }
            }
            while(cursor.moveToNext());
        }

        return Sid;
    }

    public boolean checkExistenceUsername(Contact c){
        boolean exist = false;              //starting with there is NO mention of this user name
        db = this.getReadableDatabase();                //reading databse
        String query = ("SELECT username " +
                "FROM " + TABLE_CONTACTS);
        Cursor cursor = db.rawQuery(query,null);
        String Suser;

        if(cursor.moveToFirst()){
            do{
                Suser = cursor.getString(0);

                if(Suser.equals(c.getUsername())){
                    exist = true;
                    break;
                }
            }
            while(cursor.moveToNext());
        }
        return exist;
    }

    public boolean checkExistenceEmail(Contact c){
        boolean exist = false;              //starting with there is NO mention of this user name
        db = this.getReadableDatabase();                //reading databse
        String query = ("SELECT email " +
                "FROM " + TABLE_CONTACTS);
        Cursor cursor = db.rawQuery(query,null);
        String Semail;

        if(cursor.moveToFirst()){
            do{
                Semail = cursor.getString(0);

                if(Semail.equals(c.getEmail())){
                    exist = true;
                    break;
                }
            }
            while(cursor.moveToNext());
        }
        return exist;
    }

    public String updatePassword(String idLocation, String oldPassword, String newPassword){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD, newPassword);

        //good practice to use parameter(?), instead of concatenate string
        db.update(TABLE_CONTACTS, values, COLUMN_ID + "= ?", new String[] {String.valueOf(idLocation)});
        db.close();
        return idLocation;
    }

    public boolean checkIfUserLoggedIn(){
        if(mostRecentIdentity == -99999){
            //this means that there is not user logged in
            return false;
        }
        return true;
    }

    /***********                    THIS IS FOR FETCHING INFORMATION                    ***********/
    public Vector<String> fetchMonthInformation(int month, int day, int year){
        double temp = 0.0;
        Vector<String> informationPast = new Vector<String>();







        return informationPast;
    }

    public Vector<String> fetchCurrentPosture(int month, int day, int year){
        double temp = 0.0;
        Vector<String> currentPosture = new Vector<String>();

        db = this.getReadableDatabase();
        String queryIndividual = "SELECT SUM(resistor1+resistor2) " +
                "FROM Sensors " +
                "WHERE identity = " + mostRecentIdentity + ";";

        Cursor cursor = db.rawQuery(queryIndividual,null);
        String resistor;
/*
        if(cursor.moveToFirst()){
            do{
                resistor = Double.toString(Double.parseDouble(cursor.getString(0))/2);
                currentPosture.push_back(resistor);

            }
            while(cursor.moveToNext());
        }
*/


        return currentPosture;
    }

}