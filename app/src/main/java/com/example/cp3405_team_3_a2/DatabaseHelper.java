package com.example.cp3405_team_3_a2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 2;



    DatabaseHelper(Context context) {
        super(context, "helper", null, VERSION);
        Log.i("MyHelper", "constructor called");
    }

    public void onCreate(SQLiteDatabase db) {
        Log.i("MyHelper", "onCreate called");
        db.execSQL("CREATE TABLE IF NOT EXISTS USER (EMAIL TEXT PRIMARY KEY, PASSWORD TEXT, USER_TYPE INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS COMPANY (EMAIL TEXT PRIMARY KEY, COMPANY_NAME TEXT, COMPANY_DESCRIPTION TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS PERSON (EMAIL TEXT PRIMARY KEY, QUALIFICATIONS TEXT, ACADEMIC_HISTORY TEXT, FIRST_NAME TEXT, LAST_NAME TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS STUDENT (EMAIL TEXT PRIMARY KEY,  JOB_INTERESTS TEXT, LOCATION_VISIBILITY BOOLEAN, INTERESTED_LOCATION TEXT, GITHUB_LINK TEXT, LINKEDIN_LINK TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS STAFF (EMAIL TEXT PRIMARY KEY,  JOB_POSITION TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS RECOMMENDATION (RECOMMENDATION_ID INTEGER PRIMARY KEY AUTOINCREMENT,  STUDENT_RECOMMENDED TEXT, RECOMMENDED_BY TEXT, JOB INTEGER, DATE_CREATED INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS JOB (JOB_ID INTEGER PRIMARY KEY AUTOINCREMENT,  COMPANY TEXT, JOB_TITLE TEXT, JOB_DESCRIPTION TEXT, JOB_TYPE TEXT, JOB_SALARY TEXT, JOB_DUE_DATE TEXT, DATE_CREATED INTEGER)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USER");
        db.execSQL("DROP TABLE IF EXISTS COMPANY");
        db.execSQL("DROP TABLE IF EXISTS PERSON");
        db.execSQL("DROP TABLE IF EXISTS STUDENT");
        db.execSQL("DROP TABLE IF EXISTS STAFF");
        db.execSQL("DROP TABLE IF EXISTS LINK");
        db.execSQL("DROP TABLE IF EXISTS RECOMMENDATION");
        db.execSQL("DROP TABLE IF EXISTS JOB");
    }

    void insertStudent(String email, String password, int userType,
                              String qualifications, String academicHistory,
                              String firstName, String lastName, String jobInterests,
                              boolean locationVisibility, String interestedLocation){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues userValues = new ContentValues();
        ContentValues personValues = new ContentValues();
        ContentValues studentValues = new ContentValues();
        userValues.put("EMAIL", email);
        userValues.put("PASSWORD", password);
        userValues.put("USER_TYPE", userType);
        personValues.put("EMAIL", email);
        personValues.put("QUALIFICATIONS", qualifications);
        personValues.put("ACADEMIC_HISTORY", academicHistory);
        personValues.put("FIRST_NAME", firstName);
        personValues.put("LAST_NAME", lastName);
        studentValues.put("EMAIL", email);
        studentValues.put("JOB_INTERESTS", jobInterests);
        studentValues.put("LOCATION_VISIBILITY", locationVisibility);
        studentValues.put("INTERESTED_LOCATION", interestedLocation);

        db.insert("USER", null, userValues);
        db.insert("PERSON", null, personValues);
        db.insert("STUDENT", null, studentValues);

    }

    void insertStaff (String email, String password, int userType, String qualifications,
                      String academicHistory, String firstName,
                      String lastName, String jobPosition){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues userValues = new ContentValues();
        ContentValues personValues = new ContentValues();
        ContentValues staffValues = new ContentValues();

        userValues.put("EMAIL", email);
        userValues.put("PASSWORD", password);
        userValues.put("USER_TYPE", userType);
        personValues.put("EMAIL", email);
        personValues.put("QUALIFICATIONS", qualifications);
        personValues.put("ACADEMIC_HISTORY", academicHistory);
        personValues.put("FIRST_NAME", firstName);
        personValues.put("LAST_NAME", lastName);
        staffValues.put("JOB_POSITION", jobPosition);

        db.insert("USER", null, userValues);
        db.insert("PERSON", null, personValues);
        db.insert("STUDENT", null, staffValues);
    }

    void insertCompany (String email, String password, int userType,
                        String companyName, String companyDescription){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues userValues = new ContentValues();
        ContentValues companyValues = new ContentValues();

        userValues.put("EMAIL", email);
        userValues.put("PASSWORD", password);
        userValues.put("USER_TYPE", userType);
        companyValues.put("COMPANY_NAME", companyName);
        companyValues.put("COMPANY_DESCRIPTION", companyDescription);

        db.insert("USER", null, userValues);
        db.insert("PERSON", null, companyValues);
    }

    void insertRecommendation(String studentRecommended, String recommendedBy, int job, Long dateCreated){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues recommendationValues = new ContentValues();
        recommendationValues.put("STUDENT_RECOMMENDED", studentRecommended);
        recommendationValues.put("RECOMMENDED_BY", recommendedBy);
        recommendationValues.put("JOB", job);
        recommendationValues.put("DATE_CREATED", dateCreated);

        db.insert("RECOMMENDATION", null, recommendationValues);
    }

    public String getUserType(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT USER_TYPE FROM USER WHERE EMAIL = " + "\'" + email + "\'";
        Cursor data = db.rawQuery(query, null);
        data.moveToFirst();
        String answer = data.getString(0);
        data.close();
        return answer;
    }

    public ArrayList<String> getJobDetails(int job){
        ArrayList<String> jobDetails = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM USER WHERE JOB_ID = " + "\'" + job + "\'";
        Cursor data = db.rawQuery(query, null);
        data.moveToFirst();
        for (int i = 0; i < data.getCount(); i++) {
            jobDetails.add(data.getString(i));
            data.moveToNext();
        }
        data.close();
        return jobDetails;
    }

    Cursor getUserRecommendations(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM RECOMMENDATION WHERE STUDENT_RECOMMENDED = " + "\'" + email + "\'" + " ORDER BY DATE_CREATED DESC";
        return db.rawQuery(query, null);
    }

    public boolean checkLogin(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        //search in the users table for any users with a matching email/password
        String query = "SELECT * FROM USER WHERE EMAIL = " + "\'" + email + "\'" + " AND PASSWORD = " + "\'" + password + "\'";
        Cursor cursor = db.rawQuery(query, null);
        //if a user is found with matching credentials, return true
        if(cursor.getCount() == 0){
            cursor.close();
            db.close();
            return false;
        }
        else{
            cursor.close();
            db.close();
            return true;
        }
    }

    public Cursor getStudentProfile(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM STUDENT, PERSON WHERE STUDENT.EMAIL  = PERSON.EMAIL AND STUDENT.EMAIL = " + "\'" + email + "\'";
        return db.rawQuery(query, null);
    }

    public Cursor getStaffProfile(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM STAFF, PERSON WHERE STUDENT.EMAIL  = PERSON.EMAIL AND STAFF.EMAIL = " + "\'" + email + "\'";
        return db.rawQuery(query, null);
    }

    public Cursor getCompanyProfile(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM COMPANY WHERE COMPNAY.EMAIL = " + "\'" + email + "\'";
        return db.rawQuery(query, null);
    }

}
