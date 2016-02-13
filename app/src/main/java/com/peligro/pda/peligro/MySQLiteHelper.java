package com.peligro.pda.peligro;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "user_info";
    public static final String name = "name";
    public static final String password = "password";
    public static final String phone="phone";
    public static final String email="email";
    public static final String dob="DOB";
    public static final String gender="gender";
    public static final String disease="disease";
    public static final String latitude_h="latitude_h";
    public static final String longitude_h="longitude_h";
    public static final String latitude_c="latitude_c";
    public static final String longitude_c="longitude_c";
    public static final String regKey="regKey";


    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "(" +phone+ " integer primary key, " + name
            + " text," +password+ " text," +email+ " text,"
            + dob + " text," +gender+ " text," + disease + " text," + latitude_h + " number," + longitude_h + " number,"
            + latitude_c + " number," + longitude_c+ " number,"+regKey + " text);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

} 
