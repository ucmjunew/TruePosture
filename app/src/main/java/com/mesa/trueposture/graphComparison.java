package com.mesa.trueposture;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;


public class graphComparison extends AbstractActivity {
    DatabaseHelper helper = new DatabaseHelper(this);
    TextView tv;

    Calendar c = Calendar.getInstance();
    int startYear = c.get(Calendar.YEAR);
    int startMonth = c.get(Calendar.MONTH);
    int startDay = c.get(Calendar.DAY_OF_MONTH);
    String pickedMonth = "    ";                     //for now the previous month will be the default, not actually January
    String UpdatedText;

    PieChart pieChart1;
    ArrayList<Entry> entries1 = new ArrayList<>();
    ArrayList<String> labels1 = new ArrayList<>();
    ArrayList<Integer> piecolors1 = new ArrayList<>();

    PieChart pieChart2;
    ArrayList<Entry> entries2 = new ArrayList<>();
    ArrayList<String> labels2 = new ArrayList<>();
    ArrayList<Integer> piecolors2 = new ArrayList<>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    //monthName(startMonth);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_comparison);

        Button bSetting = (Button) findViewById(R.id.bBackButton);
        bSetting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        monthName(startMonth + 1, startYear);
        tv = (TextView)findViewById(R.id.TVcompareMonth);
        tv.setText("Month");
        //tv.setText(pickedMonth);                                           //set the TextView tv to the String username

        //EditText et = (EditText) findViewById(R.id.TVcompareMonth);
        //et.setText(pickedMonth);

        addGraph();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

/*
    @Override

    protected void onResume() {
        super.onResume();
        // we're going to simulate real time with thread that append data to the graph
        new Thread(new Runnable() {

            @Override
            public void run() {
                // we add 100 new entries
                for (int i = 0; i < 100; i++) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            addEntry();
                        }
                    });


                    // sleep to slow down the add of entries
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        // manage error ...
                    }
                }
            }
        }).start();
    }

*/

    public void addGraph() {
        pieChart1 = (PieChart) findViewById(R.id.graph1);
        pieChart2 = (PieChart) findViewById(R.id.graph2);

        entries1.add(new Entry(25f, 0));
        entries1.add(new Entry(75f, 0));

        piecolors1.add(Color.rgb(31, 120, 178));
        piecolors1.add(Color.rgb(226, 192, 102));
        pieChart1.setUsePercentValues(true);
        pieChart2.setUsePercentValues(true);


        PieDataSet dataset1 = new PieDataSet(entries1, "# of Calls");
        dataset1.setValueTextColor(Color.WHITE);
        dataset1.setValueTextSize(15f);
        dataset1.setColors(piecolors1);

        labels1.add("Good Posture");
        labels1.add("Bad Posture");


        PieData data1 = new PieData(labels1, dataset1);
        data1.setValueTextColor(Color.WHITE);
        data1.setValueTextSize(15f);
        data1.setValueFormatter(new PercentFormatter());
        pieChart1.setData(data1);


        pieChart1.setDescription("");
        pieChart1.setHoleRadius(0f);
        pieChart1.setHoleColorTransparent(true);
        pieChart1.getLegend().setEnabled(false);


        //Second PieChart
        entries2.add(new Entry(55f, 0));
        entries2.add(new Entry(45f, 0));
        piecolors2.add(Color.rgb(31, 120, 178));
        piecolors2.add(Color.rgb(226, 192, 102));

        PieDataSet dataset2 = new PieDataSet(entries2, "# of Calls");
        dataset2.setColor(Color.WHITE);
        dataset2.setValueTextSize(15f);
        dataset2.setColors(piecolors2);

        labels2.add("Good Posture");
        labels2.add("Bad Posture");

        PieData data2 = new PieData(labels2, dataset2);
        data2.setValueTextColor(Color.WHITE);
        data2.setValueTextSize(15f);
        data2.setValueFormatter(new PercentFormatter());
        pieChart2.setData(data2);

        pieChart2.setDescription("");
        pieChart2.setHoleRadius(0f);
        pieChart2.setHoleColorTransparent(true);
        pieChart2.getLegend().setEnabled(false);


    }

    public void monthName(int currentMonth, int currentYear) {
        switch (currentMonth) {
            case 1:
                pickedMonth = "January";
                break;
            case 2:
                pickedMonth = "February";
                break;
            case 3:
                pickedMonth = "March";
                break;
            case 4:
                pickedMonth = "April";
                break;
            case 5:
                pickedMonth = "May";
                break;
            case 6:
                pickedMonth = "June";
                break;
            case 7:
                pickedMonth = "July";
                break;
            case 8:
                pickedMonth = "August";
                break;
            case 9:
                pickedMonth = "September";
                break;
            case 10:
                pickedMonth = "October";
                break;
            case 11:
                pickedMonth = "November";
                break;
            case 12:
                pickedMonth = "December";
                break;
            default:
                pickedMonth = "Invalid month";
                break;
        }
        UpdatedText = pickedMonth + " " + currentYear;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "graphComparison Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.mesa.trueposture/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "graphComparison Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.mesa.trueposture/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    // add random data to graph

/*
    public void onBackClick(View v) {
        if (v.getId() == R.id.bBackButton) {            //it it matches we need all the values (name,email address, username,password, confirm password) and convert them to string
            Intent i = new Intent(graphComparison.this, MainPage.class);
            startActivity(i);
            finish();
        }
    }
*/

    public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // TODO Auto-generated method stub
            // Use the current date as the default date in the picker
            DatePickerDialog dialog = new DatePickerDialog(graphComparison.this, this, startYear, startMonth, startDay);
            return dialog;

        }

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            // Do something with the date chosen by the user
            startYear = year;
            startMonth = monthOfYear + 1;
            startDay = dayOfMonth;

            monthName(startMonth, startYear);
            tv.setText(UpdatedText);
            //Toast.makeText(graphComparison.this, "Month = " + startMonth, Toast.LENGTH_SHORT).show();
            //Toast.makeText(graphComparison.this, "Date = " + startDay , Toast.LENGTH_SHORT).show();
            //Toast.makeText(graphComparison.this, "Year = " + startYear, Toast.LENGTH_SHORT).show();

            //Vector<String> informationPast = new Vector<String>();
            //informationPast = helper.fetchMonthInformation(startYear, startMonth, startDay);
        }
    }

    public void showStartDateDialog(View v) {
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getFragmentManager(), "start_date_picker");

    }


}