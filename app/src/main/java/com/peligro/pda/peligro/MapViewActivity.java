package com.peligro.pda.peligro;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;


public class MapViewActivity extends Activity {

    WebView wv;
    Button confirm, previous;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);
        confirm=(Button)findViewById(R.id.confirm);
        previous=(Button)findViewById(R.id.previous);
        wv=(WebView)findViewById(R.id.webview);
        wv.getSettings().setJavaScriptEnabled(true);
        String url=ConnectionUtility.url+"mapview.jsp?lat="+userData.latic+"&long="+userData.longic;
        wv.loadUrl(url);

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String request=ConnectionUtility.url+"updateUserHome?lat="+userData.latih+"&lon="+userData.longih+"&phone="+userData.phone;
                try {
                    String response = ConnectionUtility.getResponse(request);
                    if (response.equals("success")) {
                        Toast.makeText(MapViewActivity.this, "registration is successfull", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MapViewActivity.this, "Registration failed ", Toast.LENGTH_LONG).show();
                    }
                }catch (NullPointerException e){
                    e.printStackTrace();
                    Toast.makeText(MapViewActivity.this, "Connectivity error", Toast.LENGTH_LONG).show();

                }
                finish();
            }
        });

    }


}
