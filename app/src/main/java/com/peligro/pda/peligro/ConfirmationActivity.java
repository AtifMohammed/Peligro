package com.peligro.pda.peligro;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;


public class ConfirmationActivity extends Activity {

    String name, password, phone, email;
    String code;
    TextView numberAlert, confirmAlert;
    Button resend, confirm, previous, next;
    EditText enterCode;
    private CommentsDataSource datasource;
   // private userData ud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        //starting the datasource
        datasource = new CommentsDataSource(this);
        datasource.open();

        datasource.getData();

        //initialising the views
        numberAlert=(TextView)findViewById(R.id.confirmNumberTV);
        confirmAlert=(TextView)findViewById(R.id.confirmAlert);
        resend=(Button)findViewById(R.id.resend);
        confirm=(Button)findViewById(R.id.confirmBtn);
        enterCode=(EditText)findViewById(R.id.getCode);
        previous=(Button)findViewById(R.id.previous2);
        next=(Button)findViewById(R.id.next2);

        //getting the values from registration page
        name=userData.name;
        password=userData.password;
        phone=userData.phone;
        email=userData.email;
        //ud.updateInfo();
        code="SomeText";

        //disabling the next button
        next.setEnabled(false);

        //generating the alert
        numberAlert.setText("Confirmation code has been sent to " + userData.phone);

        //Confirm button
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (enterCode.getText().toString().equals(code)) {
                    confirmAlert.setTextColor(Color.parseColor("blue"));
                    confirmAlert.setText("User is verified\nClick next to continue");
                    next.setEnabled(true);
                    resend.setEnabled(false);
                    confirm.setEnabled(false);
                    enterCode.setEnabled(false);

                  /*  //setting the value
                    userData.name=name;
                    userData.password=password;
                    userData.email=email;
                    userData.phone=phone; */
                } else {
                    confirmAlert.setTextColor(Color.parseColor("red"));
                    confirmAlert.setText("Wrong registration code");
                }
            }
        });


        //Resend Button
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmAlert.setTextColor(Color.parseColor("red"));
                confirmAlert.setText("Authentication code resent to " + phone);
            }
        });

        //previous button

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datasource.clearTable();
                finish();
            }
        });

        //next button

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                datasource.updateData();
                String request=ConnectionUtility.url+"UpdateUserInfo?name="+userData.name+"&phone="+userData.phone+
                        "&email="+userData.email+"&password="+userData.password;
                try {
                    String response = ConnectionUtility.getResponse(request);
                    if (response.equals("success")) {
                        Toast.makeText(ConfirmationActivity.this, "Data updated to server", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ConfirmationActivity.this, "Data couldnt be updated to server", Toast.LENGTH_LONG).show();
                    }
                }catch (NullPointerException e){
                    e.printStackTrace();
                    Toast.makeText(ConfirmationActivity.this, "Connectivity error", Toast.LENGTH_LONG).show();

                }
                Intent intent=new Intent(ConfirmationActivity.this, InitSettingActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }


    @Override
    protected void onRestart(){
        super.onRestart();
        finish();
    }
}
