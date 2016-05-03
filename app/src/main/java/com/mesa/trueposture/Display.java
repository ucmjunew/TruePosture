package com.mesa.trueposture;

/**
 * Created by ucmjunew on 3/3/16.
 */
    import android.app.Activity;
    import android.content.Intent;
    import android.graphics.Color;
    import android.graphics.Typeface;
    import android.net.Uri;
    import android.os.Bundle;
    import android.view.Menu;
    import android.view.View;
    import android.widget.EditText;
    import android.widget.TextView;
    import android.widget.Toast;
    import android.widget.Toolbar;

    import com.google.android.gms.appindexing.Action;
    import com.google.android.gms.appindexing.AppIndex;
    import com.google.android.gms.common.api.GoogleApiClient;
    import com.jjoe64.graphview.GraphView;
    import com.jjoe64.graphview.GridLabelRenderer;
    import com.jjoe64.graphview.Viewport;
    import com.jjoe64.graphview.series.DataPoint;
    import com.jjoe64.graphview.series.LineGraphSeries;

    import java.util.Calendar;
    import java.util.Random;

public class Display extends Activity {

    private static final Random RANDOM = new Random();
    private LineGraphSeries<DataPoint> series;
    private int lastX = 0;
    private int lastZ = 0;
    private LineGraphSeries<DataPoint> series2;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    DatabaseHelper helper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);
        Typeface myTypeFaceGreeting = Typeface.createFromAsset(getAssets(), "CM_Sans_Serif_2012.ttf");
        TextView tv = (TextView)findViewById(R.id.textView);
        tv.setTypeface(myTypeFaceGreeting);

        Typeface myTypeFaceCurrent = Typeface.createFromAsset(getAssets(), "soft_sans_serif_7.ttf");
        TextView tvCur = (TextView)findViewById(R.id.textViewC);
        tvCur.setTypeface(myTypeFaceCurrent);

        addGraph();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

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
        viewport.setScrollable(true);
        viewport.setYAxisBoundsManual(true);
        viewport.setXAxisBoundsManual(true);

        series.setColor(Color.YELLOW);
        series2.setColor(Color.CYAN);
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
        series.appendData(new DataPoint(lastX++, RANDOM.nextDouble() * 7.5d), false, 100);
        series2.appendData(new DataPoint(lastZ++, (double) (5.0)), false, 100);

        //WHERE DO I ADD THE INFORMATION...

        Calendar c = Calendar.getInstance();
        int startYear = c.get(Calendar.YEAR);
        int startMonth = c.get(Calendar.MONTH);
        int startDay = c.get(Calendar.DAY_OF_MONTH);

        //  Vector<String> currentPosture = new Vector<String>();
        // currentPosture = helper.fetchCurrentPosture(startMonth, startDay, startYear);                    //this will get all of today's posture

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "graphToday Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
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
                "graphToday Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.mesa.trueposture/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

}


