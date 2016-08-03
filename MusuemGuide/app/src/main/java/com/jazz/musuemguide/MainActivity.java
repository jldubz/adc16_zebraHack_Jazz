package com.jazz.musuemguide;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.FloatMath;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorMan;
    private Sensor accelerometer;

    private float[] mGravity;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;

    boolean bStill = false;

    DBController db;

    private BroadcastReceiver dataWedgeIntentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent i) {

            // get the source of the data
            String source = i.getStringExtra("com.motorolasolutions.emdk.datawedge.source");
            if (source == null) source = "scanner";

            // get the data from the intent
            final String data = i.getStringExtra("com.motorolasolutions.emdk.datawedge.data_string");

            //get the type
            String barcodeType = i.getStringExtra("com.motorolasolutions.emdk.datawedge.label_type");

            // check if the data has come from the barcode scanner
            if (source.equalsIgnoreCase("scanner")) {
                // check if there is anything in the data
                if (data != null && data.length() > 0) {


                    final TextView tvHello = (TextView) findViewById(R.id.tvHello);
                    tvHello.post(new Runnable() {
                        @Override
                        public void run() {
                            tvHello.setText(data);
                        }
                    });
                    View rootView = (View) findViewById(android.R.id.content);
                    Snackbar.make(rootView, data, Snackbar.LENGTH_LONG)
                            .show();
                    Painting result = db.getAsset(data);
                    Intent newIntent = new Intent();
                    newIntent.putExtra("com.jazz.extra.title",result.getTitle());
                    newIntent.putExtra("com.jazz.extra.description",result.getDescription());
                    newIntent.putExtra("com.jazz.extra.author",result.getAuthor());
                    newIntent.putExtra("com.jazz.extra.assetpath",result.getAssetPath());
                    startActivity(newIntent);
                    //barcodeScannedListener.onBarcodeScanned(data,barcodeType);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        sensorMan = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = sensorMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        IntentFilter filter = new IntentFilter("com.jazz.musuemguide.RECVR");
        filter.addCategory("android.intent.category.DEFAULT");
        registerReceiver(dataWedgeIntentReceiver, filter);

        db = new DBController(this);
    }

    @Override
    protected void onResume() {
        sensorMan.registerListener(this, accelerometer,
                SensorManager.SENSOR_DELAY_UI);
        super.onResume();
    }

    @Override
    protected void onPause() {
        sensorMan.unregisterListener(this);
        unregisterReceiver(dataWedgeIntentReceiver);
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        /*if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            mGravity = event.values.clone();
            // Shake detection
            float x = mGravity[0];
            float y = mGravity[1];
            float z = mGravity[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt(x*x + y*y + z*z);
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            // Make this higher or lower according to how much
            // motion you want to detect
            Log.d("LOGTAG", String.valueOf(mAccel));
            if(mAccel < 0.1){
                if (!bStill) {
                    bStill = true;
                    Intent i = new Intent();
                    i.setAction("com.symbol.datawedge.api.ACTION_SOFTSCANTRIGGER");
                    i.putExtra("com.symbol.datawedge.api.EXTRA_PARAMETER", "START_SCANNING");
                    sendBroadcast(i);
                }
                *//*View rootView = (View) findViewById(android.R.id.content);
                Snackbar.make(rootView, "Enable Scanner", Snackbar.LENGTH_LONG)
                        .show();*//*
                // do something

            } else {
                bStill = false;
                Intent i = new Intent();
                i.setAction("com.symbol.datawedge.api.ACTION_SOFTSCANTRIGGER");
                i.putExtra("com.symbol.datawedge.api.EXTRA_PARAMETER", "STOP_SCANNING");
                sendBroadcast(i);
                //Disable
            }
        }*/

    }
}
