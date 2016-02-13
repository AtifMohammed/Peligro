package com.peligro.pda.peligro;

import android.app.ActionBar;
import android.app.Activity;
//import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
//import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


public class MainActivity extends Activity {

    LinearLayout police, fire, ambulance, emergency;
    CommentsDataSource datasource;
    Button alert, exit, reset;
    ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datasource = new CommentsDataSource(this);
        datasource.open();
        datasource.getData();

        police=(LinearLayout)findViewById(R.id.policeLayout);
        ambulance=(LinearLayout)findViewById(R.id.ambulanceLayout);
        fire=(LinearLayout)findViewById(R.id.fireLayout);
        emergency=(LinearLayout)findViewById(R.id.emergencyLayout);
        alert=(Button)findViewById(R.id.alert);
        exit=(Button)findViewById(R.id.exit);
        reset=(Button)findViewById(R.id.reset);


        police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:9701326535"));
                startActivity(callIntent);
            }
        });

        fire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:9701326535"));
                startActivity(callIntent);
            }
        });

        ambulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:9701326535"));
                startActivity(callIntent);
            }
        });

        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:9701326535"));
                startActivity(callIntent);
            }
        });

        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String request=ConnectionUtility.url+"AlertClass?phone="+userData.phone;
                try {
                    String response = ConnectionUtility.getResponse(request);
                    if (response.equals("success")) {
                        Toast.makeText(MainActivity.this, "Alert sent succesfully", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Alert Failed", Toast.LENGTH_LONG).show();
                    }
                }catch (NullPointerException e){
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Connectivity error", Toast.LENGTH_LONG).show();

                }
            }
        });


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, userData.phone, Toast.LENGTH_LONG).show();
                finish();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String request = ConnectionUtility.url + "logout?phone=" + userData.phone;
                try {
                    String response = ConnectionUtility.getResponse(request);
                    if (response.equals("success")) {
                        Toast.makeText(MainActivity.this, "logout successful", Toast.LENGTH_LONG).show();
                        datasource.clearTable();
                        Toast.makeText(MainActivity.this, "phone table cleared", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "logout Failed", Toast.LENGTH_LONG).show();
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Connectivity error", Toast.LENGTH_LONG).show();

                }
            }

        });
    }

}
