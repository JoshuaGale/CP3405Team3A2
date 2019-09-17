package com.example.cp3405_team_3_a2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;



    DatabaseHelper(Context context) {
        super(context, "helper", null, VERSION);
        Log.i("MyHelper", "constructor called");
    }

    public void onCreate(SQLiteDatabase db) {
        Log.i("MyHelper", "onCreate called");
        db.execSQL("CREATE TABLE IF NOT EXISTS USER (EMAIL TEXT PRIMARY KEY, PASSWORD TEXT, USER_TYPE INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS COMPANY (EMAIL TEXT PRIMARY KEY, COMPANY_NAME TEXT, COMPANY_DESCRIPTION TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS PERSON (EMAIL TEXT PRIMARY KEY, QUALIFACATIONS TEXT, ACADEMIC_HISTORY TEXT, FIRST_NAME TEXT, LAST_NAME TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS STUDENT (EMAIL TEXT PRIMARY KEY,  JOB_INTRESTS TEXT, LOCATION_VISABILITY BOOLEAN, INTERESTED_LOCATION TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS STAFF (EMAIL TEXT PRIMARY KEY,  JOB_POSITION TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS LINK (EMAIL TEXT PRIMARY KEY,  LINK_TYPE TEXT PRIMARY KEY, LINK_URL TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS RECOMMENDATION (RECOMMENDATION_ID INTEGER PRIMARY KEY AUTOINCREMENT,  STUDENT_RECOMMENDED TEXT, RECOMMENDED_BY TEXT, JOB INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS JOB (JOB_ID INTEGER PRIMARY KEY AUTOINCREMENT,  COMPANY TEXT, JOB_TITLE TEXT, JOB_DESCRIPTION TEXT, JOB_TYPE TEXT, JOB_SALARY TEXT, JOB_DUE_DATE TEXT)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS LEADERBOARD");
    }

}
