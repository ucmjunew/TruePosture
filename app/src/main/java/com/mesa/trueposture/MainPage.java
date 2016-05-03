package com.mesa.trueposture;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


import java.util.Calendar;
import java.util.Random;


public class MainPage extends AbstractActivity {
    private SharedPreferences prefs;





    private static final Random RANDOM = new Random();
    private LineGraphSeries<DataPoint> series;
    private int lastX = 0;
    private int lastZ = 0;
    private LineGraphSeries<DataPoint> series2;

    boolean notificationOn = true;

    private GoogleApiClient client;
    DatabaseHelper helper = new DatabaseHelper(this);


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);


        addGraph();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();


        editor.putBoolean("notificationKey", notificationOn);


        Intent moreInfoIntent = new Intent(this, MainPage.class);
        startService(moreInfoIntent);


        Button bSetting = (Button) findViewById(R.id.bSettings);
        bSetting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MainPage.this, Settings.class);
                startActivity(i);
            }
        });


        //get to graphComparison
        Button gCompare = (Button) findViewById(R.id.gCompare);
        gCompare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MainPage.this, graphComparison.class);
//                i.putExtra("Data", data); //It is for any data you want to send to next activity
                startActivity(i);
            }
        });


        //bConnect
        Button bConnect = (Button) findViewById(R.id.recordB);
        bConnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                Intent i = new Intent(MainPage.this, Notification.class);
//                i.putExtra("Data", data); //It is for any data you want to send to next activity
//                startService(i);
            }
        });





        //start recording
        ToggleButton recordB = (ToggleButton) findViewById(R.id.recordB);
        recordB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    Intent i1 = new Intent(MainPage.this, collectData.class);
                    Intent i2 = new Intent(MainPage.this, Notification.class);
//                i.putExtra("Data", data); //It is for any data you want to send to next activity
                        startService(i1);
                        startService(i2);
                } else {
                    // The toggle is disabled
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // we're going to simulate real time with thread that append data to the graph
        new Thread(new Runnable() {

            @Override
            public void run() {
                // we add 100 new entries
                for (int i = 0; i < 1000; i++) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            addEntry();
                        }
                    });

                    // sleep to slow down the add of entries
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // manage error ...
                    }
                }
            }
        }).start();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_page, menu);
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


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /*
    public void onSettingsClick(View v) {
        if (v.getId() == R.id.bSettings) {
            Intent i = new Intent(MainPage.this, StarterPage.class);
            startActivity(i);
        }
    }
    */

    public void addGraph() {
        // we get graph view instance
        GraphView graph = (GraphView) findViewById(R.id.graph);

        // data
        GridLabelRenderer editor = graph.getGridLabelRenderer();
        series = new LineGraphSeries<DataPoint>();
        series2 = new LineGraphSeries<DataPoint>();

        graph.addSeries(series);
        graph.addSeries(series2);

        // customize a little bit viewport
        Viewport viewport = graph.getViewport();
        viewport.setMinY(0);
        viewport.setMaxY(10);
        viewport.setMinX(0);
        viewport.setMaxX(24);
        viewport.setScrollable(false);
        viewport.setYAxisBoundsManual(true);
        viewport.setXAxisBoundsManual(true);

        series.setColor(Color.rgb(226, 192, 102));
        series2.setColor(Color.rgb(31, 120, 178));
        editor.setHorizontalLabelsColor(Color.WHITE);
        editor.setVerticalLabelsColor(Color.WHITE);
        editor.setGridColor(Color.WHITE);
        editor.setHorizontalLabelsColor(Color.WHITE);
        editor.setVerticalLabelsColor(Color.WHITE);
        editor.setHorizontalLabelsVisible(true);
        editor.setVerticalLabelsVisible(true);
        editor.reloadStyles();
    }

    // add random data to graph
    private void addEntry() {
        // here, we choose to display max 10 points on the viewport and we scroll to end
        series.appendData(new DataPoint(lastX++, RANDOM.nextDouble() * 7.5d), false, 1000);
        series2.appendData(new DataPoint(lastZ++, (double) (5.0)), false, 1000);

        //WHERE DO I ADD THE INFORMATION...

        Calendar c = Calendar.getInstance();
        int startYear = c.get(Calendar.YEAR);
        int startMonth = c.get(Calendar.MONTH);
        int startDay = c.get(Calendar.DAY_OF_MONTH);

        //  Vector<String> currentPosture = new Vector<String>();
        // currentPosture = helper.fetchCurrentPosture(startMonth, startDay, startYear);                    //this will get all of today's posture

    }

}
