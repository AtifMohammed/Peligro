package com.peligro.pda.peligro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class CommentsDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    //allColumn declaration
    private String[] allColumns = { MySQLiteHelper.name,
            MySQLiteHelper.password,MySQLiteHelper.phone, MySQLiteHelper.email,
            MySQLiteHelper.dob, MySQLiteHelper.gender, MySQLiteHelper.disease, MySQLiteHelper.latitude_h, MySQLiteHelper.longitude_h,
            MySQLiteHelper.latitude_c, MySQLiteHelper.longitude_c};

   //giving value of id
    private String idVal[]={String.valueOf(userData.phone)};

   //constructor
    public CommentsDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }


    //opening the database
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    //closing the database
    public void close() {
        dbHelper.close();
    }

    //keeping the initial data
   public void putData() {
       ContentValues values = new ContentValues();
       values.put(MySQLiteHelper.name, userData.name);
       values.put(MySQLiteHelper.password, userData.password);
       values.put(MySQLiteHelper.phone, userData.phone);
       values.put(MySQLiteHelper.email, userData.email);
      /* try {
               values.put(MySQLiteHelper.dob, userData.dob);
               values.put(MySQLiteHelper.gender, userData.gender);
               values.put(MySQLiteHelper.disease, userData.disease);
       }
       catch (NullPointerException e){
           System.out.print(e);
       } */

     //  finally{
       database.insert(MySQLiteHelper.TABLE_NAME, null, values);
      // }
   }


    //updating the userdata from sqlite
    public void getData(){
        //List<String> contactList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MySQLiteHelper.TABLE_NAME;
        Cursor cursor = database.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            userData.phone=cursor.getString(0);
            userData.name=cursor.getString(1);
            userData.password=cursor.getString(2);
            userData.email=cursor.getString(3);
            userData.regKey=cursor.getString(7);
            try {
                userData.dob = cursor.getString(4);
                userData.gender = cursor.getString(5);
                userData.disease = cursor.getString(6);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }


        cursor.close();
    }


    //updating user data to sqlite
    public void updateData(){

        ContentValues values = new ContentValues();

        try {
            values.put(MySQLiteHelper.name, userData.name);
            values.put(MySQLiteHelper.password, userData.password);
            values.put(MySQLiteHelper.phone, userData.phone);
            values.put(MySQLiteHelper.email, userData.email);
            values.put(MySQLiteHelper.dob, userData.dob);
            values.put(MySQLiteHelper.gender, userData.gender);
            values.put(MySQLiteHelper.disease, userData.disease);
            values.put(MySQLiteHelper.latitude_h, userData.latih);
            values.put(MySQLiteHelper.longitude_h,userData.longih);
            values.put(MySQLiteHelper.regKey, userData.regKey);
        }
        catch (NullPointerException e){
           e.printStackTrace();
        }


        database.update(MySQLiteHelper.TABLE_NAME, values, null,
                null);
    }


    //deleting the table
    public void deleteTable(){
        database.execSQL("DROP TABLE IF EXISTS " + MySQLiteHelper.TABLE_NAME);
    }

    //checking existence of table

    public boolean isTableExists() {
        Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME, allColumns, null,null,null, null, null, null);
        cursor.moveToFirst();
        if(cursor.getCount()>0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }


    //deleting records
    public void clearTable() {
        database.delete(MySQLiteHelper.TABLE_NAME, null, null);
    }


   //update home latitude and longitute
   public void updateHomeLocation() {

       ContentValues values = new ContentValues();
       values.put(MySQLiteHelper.latitude_h, userData.latih);
       values.put(MySQLiteHelper.longitude_h, userData.longih);
       database.update(MySQLiteHelper.TABLE_NAME, values, MySQLiteHelper.phone+"= ?",
              new String[]{userData.phone});
   }


}
