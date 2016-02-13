package com.peligro.pda.peligro;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class FinalInfoActivity extends ListActivity {

    //TextView buffer;
   // String name, phone, email, dob, gender, disease;
    Button previous, confirm;
    CommentsDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_info);
        previous = (Button) findViewById(R.id.previous4);
        confirm = (Button) findViewById(R.id.confirm4);

        datasource = new CommentsDataSource(this);
        datasource.open();
        datasource.getData();
        ArrayList<String> ar=new ArrayList<>();
        ar.add(userData.name);
        ar.add(userData.phone);
        ar.add(userData.email);
        if(userData.dob.length()>0) {
            ar.add(userData.gender);
            ar.add(userData.disease + " for disease");
            ar.add(userData.dob);
        }

        // Use the SimpleCursorAdapter to show the
        // elements in a ListView
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, ar);
            setListAdapter(adapter);



    //previous button


        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datasource.close();
                finish();
            }
        });

        //confirm button
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String request=ConnectionUtility.url+"UpdateUserData?lat="+userData.latih+"&lon="+userData.longih+"&name="+userData.name+"&phone="+userData.phone+
                        "&email="+userData.email+"&password="+userData.password+"&gender="+userData.gender+"&disease="+userData.disease+"&dob="+userData.dob;
                try {
                    String response = ConnectionUtility.getResponse(request);
                    if (response.equals("success")) {
                        Toast.makeText(FinalInfoActivity.this, "Data updated succesfully", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(FinalInfoActivity.this, "Data updation failed", Toast.LENGTH_LONG).show();
                    }
                }catch (NullPointerException e){
                    e.printStackTrace();
                    Toast.makeText(FinalInfoActivity.this, "Connectivity error", Toast.LENGTH_LONG).show();

                }

                Intent intent = new Intent(FinalInfoActivity.this, MainActivity.class);
                datasource.close();
                finish();
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        datasource.open();
    }

}
