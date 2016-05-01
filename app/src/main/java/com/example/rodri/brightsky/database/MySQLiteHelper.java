package com.example.rodri.brightsky.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by rodri on 5/1/2016.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database name
    public static final String DATABASE_NAME = "brightSky.db";

    // Database version
    public static final int DATABASE_VERSION = 1;

    // Table name
    public static final String TABLE_CITY = "cities";

    // cities' columns
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";

    // Create cities table
    public static final String CREATE_TABLE_CITY = "CREATE TABLE " + TABLE_CITY + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT NOT NULL);";

    // Constructor
    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CITY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(), "Upgrading database from " + oldVersion + " to " + newVersion
                + ". All old data will be deleted!");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITY);
        onCreate(db);
    }
}
