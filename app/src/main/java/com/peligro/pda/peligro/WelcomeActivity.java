package com.peligro.pda.peligro;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;


public class WelcomeActivity extends Activity {

    //initialising progress bar variables
    CommentsDataSource datasource;
   // private SQLiteDatabase database;


    //starting the test screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        datasource = new CommentsDataSource(this);
        datasource.open();
        boolean flag=datasource.isTableExists();
        if(!flag) {
            datasource.close();
            Intent intent = new Intent(WelcomeActivity.this, RegistrationActivity.class);
            startActivity(intent);
        }
        else{
            //start the main activity
            datasource.close();
            //datasource.getData();
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }


    //killing the application


    @Override
    protected void onRestart()
    {
     super.onRestart();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        finish();
    }
}
