package com.peligro.pda.peligro;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class RegistrationActivity extends Activity {

    String name, password, vpass, phone, email, alert[];
    StringBuffer st;
    EditText Ename, Epassword, Evpass, Ephone, Eemail;
    TextView AlertView;
    Button submit;
    boolean flag;
    int i;
    CommentsDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
      alert = new String[10];
        datasource = new CommentsDataSource(this);
        datasource.open();

        //initialising text boxes and buttons
        AlertView=(TextView)findViewById(R.id.AlertTV);
        Ename=(EditText)findViewById(R.id.name);
        Epassword=(EditText)findViewById(R.id.password);
        Evpass=(EditText)findViewById(R.id.ConfirmPassword);
        Ephone=(EditText)findViewById(R.id.phone);
        Eemail=(EditText)findViewById(R.id.email);
        submit=(Button)findViewById(R.id.submit);
        i=0;
        flag=true;

       final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        //initialising the submit button

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //initialising strings
                flag=true;

                //validating and verifying the data
                    if (Ename.getText().toString().equals("")) {
                        flag = false;
                        alert[i++] = "\nName cannot be empty";
                    }
                    if (Ephone.getText().toString().equals("")) {
                        flag = false;
                        alert[i++] = "\nNumber cannot be empty";
                    }
                    else{
                        phone=Ephone.getText().toString();
                        if ((phone.length() != 10)) {
                            flag = false;
                            alert[i++] = "\nNumber field incorrect";
                        }
                    }
                    if (Epassword.getText().toString().equals("")) {
                        flag = false;
                        alert[i++] = "\nPlease enter the password";
                    }
                else {
                        password = Epassword.getText().toString();
                        vpass=Evpass.getText().toString();
                        if (!password.equals(vpass)) {
                            flag = false;
                            alert[i++] = "\nPassword do not match";
                            Epassword.setText("");
                            Evpass.setText("");
                        }
                    }
                    if (Eemail.getText() == null) {
                        flag = false;
                        alert[i++] = "\nEmail field cannot be empty";
                    }
                else {
                        email=Eemail.getText().toString();
                        if (!email.matches(emailPattern)) {
                            flag = false;
                            alert[i++] = "\nPlease enter the valid email";
                        }
                    }


                //starting the activity
                if(flag){
                    AlertView.setText("");
                    userData.name=Ename.getText().toString();
                    userData.phone=Ephone.getText().toString();
                    userData.password=Epassword.getText().toString();
                    userData.email=Eemail.getText().toString();
                    datasource.putData();
                    Intent registration=new Intent(RegistrationActivity.this, TestActivity.class);
                    startActivity(registration);
                 /* Intent intent = new Intent(RegistrationActivity.this, ConfirmationActivity.class);
                    intent.putExtra("name", Ename.getText().toString());
                    intent.putExtra("password", Epassword.getText().toString());
                    intent.putExtra("phone", Ephone.getText().toString());
                    intent.putExtra("email", Eemail.getText().toString());
                    startActivity(intent); */
                }
               else{
                    for(int j=1; j<i; j++)
                       alert[0]=alert[0].concat(alert[j]);

                   AlertView.setTextColor(Color.parseColor("red"));
                    AlertView.setText(alert[0]);
                    Toast.makeText(RegistrationActivity.this, alert[0], Toast.LENGTH_LONG).show();
                    i=0;
                }

            }
        });


    }
}
