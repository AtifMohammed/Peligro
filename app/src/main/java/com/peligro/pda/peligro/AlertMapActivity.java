package com.peligro.pda.peligro;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;


public class AlertMapActivity extends Activity {
    Button exit;
    WebView wv;
    CommentsDataSource datasource;
    GPSTracker gps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gps = new GPSTracker(AlertMapActivity.this);
        try {

            if (gps.canGetLocation()) {
                gps.getLatitude();
                gps.getLongitude();
            }
        }catch(Exception e){e.printStackTrace();}
        setContentView(R.layout.activity_alert_map);
        //datasource = new CommentsDataSource(this);
        //datasource.open();

        //datasource.getData();

        exit=(Button)findViewById(R.id.emergencyExit);
        wv=(WebView)findViewById((R.id.alertMap));
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        wv.getSettings().setJavaScriptEnabled(true);
        String url=ConnectionUtility.url+"AlertMap?lat="+userData.latic+"&lon="+userData.longic+
                "&phone="+userData.phone;
        wv.loadUrl(url);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alert_map, menu);
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
}
