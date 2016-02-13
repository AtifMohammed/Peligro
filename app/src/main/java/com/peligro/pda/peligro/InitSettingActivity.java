package com.peligro.pda.peligro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.widget.Toast.*;


public class InitSettingActivity extends FragmentActivity {

    Button previous, next, location;
    EditText dd, mm, yy;
    TextView alert, skip, genderTV, diseaseTV , addressLL;
    Switch gender, disease;
    String gen, dis;
    int day, month, year;
    CommentsDataSource datasource;

    GPSTracker gps;
    Geocoder geocoder;
    List<Address> addresses;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_setting);


        //
        //accessing the views
        //
        previous = (Button) findViewById(R.id.previous3);
        next = (Button) findViewById(R.id.next3);
        location = (Button) findViewById(R.id.locationInput);
        dd = (EditText) findViewById(R.id.day);
        mm = (EditText) findViewById(R.id.month);
        yy = (EditText) findViewById(R.id.year);
        alert = (TextView) findViewById(R.id.AlertText4);
        gender = (Switch) findViewById(R.id.genderSwitch);
        disease = (Switch) findViewById(R.id.disease);
        skip = (TextView) findViewById(R.id.skipTV);
        genderTV=(TextView)findViewById(R.id.genderTV);
        diseaseTV=(TextView)findViewById(R.id.diseaseTV);
        addressLL=(TextView)findViewById(R.id.address);

        //initialising geo coder
        geocoder = new Geocoder(this, Locale.getDefault());
        //initialising datasource
        datasource = new CommentsDataSource(this);
        datasource.open();

        //initialising gender and disease
        gen = "unassigned";
        dis = "unassigned";
        day = 0;
        month = 0;
        year = 0;


        try {
            if (gps.canGetLocation()) {
                gps.getLatitude();
                gps.getLongitude();
            }
        }catch(NullPointerException e){
            e.printStackTrace();
                }




        //setting the gender
        gender.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    gen = "Male";
                    genderTV.setTextColor(Color.parseColor("blue"));
                } else {
                    gen = "Female";
                    genderTV.setTextColor(Color.parseColor("cyan"));
                }
                genderTV.setText(gen);
            }
        });

        //setting the disease
        disease.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dis = "Yes";
                    diseaseTV.setTextColor(Color.parseColor("red"));
                } else {
                    dis = "No";
                    diseaseTV.setTextColor(Color.parseColor("blue"));
                }
                diseaseTV.setText(dis);
            }
        });


        //skip button
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InitSettingActivity.this, FinalInfoActivity.class);
                userData.dob = "DOB unavailable";
                userData.disease = "Disease Unavailable";
                userData.gender = "Gender Unavailable";
                datasource.updateData();
                datasource.close();
                finish();
                startActivity(intent);
            }
        });

        //previous Button
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datasource.clearTable();
                finish();
            }
        });

        //next Button
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (dd.getText().toString().length()==0 || mm.getText().toString().length()==0
                        || yy.getText().toString().length()==0) {
                    alert.setText("Please enter the date");
                } else if (Integer.parseInt(dd.getText().toString()) > 31 || Integer.parseInt(mm.getText().toString()) > 12 || yy.getText().toString().length() != 4) {
                    alert.setText("Please enter a valid date");
                } else {
                    day = Integer.parseInt(dd.getText().toString());
                    month = Integer.parseInt(mm.getText().toString());
                    year = Integer.parseInt(yy.getText().toString());

                    userData.dob = day + "-" + month + "-" + year;
                    userData.gender = gen;
                    userData.disease = dis;

                    datasource.updateData();
                    datasource.close();
                    Intent intent = new Intent(InitSettingActivity.this, FinalInfoActivity.class);
                    startActivity(intent);

                }
            }
        });





        //locationButton
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //initialising location provider
                gps = new GPSTracker(InitSettingActivity.this);
                try {

                    if (gps.canGetLocation()) {
                        gps.getLatitude();
                        gps.getLongitude();
                        userData.longih = userData.longic;
                        userData.latih = userData.latic;
                        datasource.updateHomeLocation();

                        Intent i = new Intent(InitSettingActivity.this, MapViewActivity.class);
                        startActivity(i);

                        // \n is for new line
                        Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + userData.latih + "\nLong: " + userData.longih, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Location not available", Toast.LENGTH_LONG).show();
                    }

                    getLocation();
                }catch(Exception e) {
                    e.printStackTrace();
                    location.setBackgroundColor(Color.parseColor("red"));
                    location.setText("Please try again in 2 seconds");
                }
            }
        });

    }

    public void getLocation(){
        try {
            addresses = geocoder.getFromLocation(userData.latic, userData.longic, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        }catch (IOException e){
            e.printStackTrace();
        }

        try {
            String address="Street address not available";
            for(int i=0; i<addresses.get(0).getMaxAddressLineIndex();i++)
                address.concat("\n"+addresses.get(0).getAddressLine(i)); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            // String knownName = addresses.get(0).getFeatureName();
            addressLL.setText(address+"\n" + city + "\n" + state + "\n" + country + "\n" + postalCode);
        }catch(Exception e){
            addressLL.setText("Array index out of bounds");
            e.printStackTrace();
        }
    }


     @Override
    protected void onResume(){
         super.onResume();
        datasource.open();
     }

    @Override
    protected void onRestart(){
        super.onRestart();
        datasource.open();
    }

}



