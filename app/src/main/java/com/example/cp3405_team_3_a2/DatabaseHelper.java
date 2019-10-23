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
        db.execSQL("CREATE TABLE IF NOT EXISTS PERSON (EMAIL TEXT PRIMARY KEY, QUALIFICATIONS TEXT, ACADEMIC_HISTORY TEXT, NAME TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS STUDENT (EMAIL TEXT PRIMARY KEY,  JOB_INTERESTS TEXT, LOCATION_VISIBILITY BOOLEAN, INTERESTED_LOCATION TEXT, GITHUB_LINK TEXT, LINKEDIN_LINK TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS STAFF (EMAIL TEXT PRIMARY KEY,  JOB_POSITION TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS RECOMMENDATION (RECOMMENDATION_ID INTEGER PRIMARY KEY AUTOINCREMENT,  STUDENT_RECOMMENDED TEXT, RECOMMENDED_BY TEXT, JOB INTEGER, DATE_CREATED INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS JOB (JOB_ID INTEGER PRIMARY KEY AUTOINCREMENT,  COMPANY TEXT, JOB_TITLE TEXT, JOB_DESCRIPTION TEXT, JOB_TYPE TEXT, JOB_SALARY TEXT, JOB_DUE_DATE INTEGER, DATE_CREATED INTEGER)");
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
                              String name, String jobInterests,
                              boolean locationVisibility, String interestedLocation,
                       String gitHubLink, String linkedInLink){
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
        personValues.put("NAME", name);
        studentValues.put("EMAIL", email);
        studentValues.put("JOB_INTERESTS", jobInterests);
        studentValues.put("LOCATION_VISIBILITY", locationVisibility);
        studentValues.put("INTERESTED_LOCATION", interestedLocation);
        studentValues.put("GITHUB_LINK", gitHubLink);
        studentValues.put("LINKEDIN_LINK", linkedInLink);

        db.insert("USER", null, userValues);
        db.insert("PERSON", null, personValues);
        db.insert("STUDENT", null, studentValues);

    }

    void insertStaff (String email, String password, int userType, String qualifications,
                      String academicHistory, String name, String jobPosition){

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
        personValues.put("NAME", name);
        staffValues.put("JOB_POSITION", jobPosition);
        staffValues.put("EMAIL", email);

        db.insert("USER", null, userValues);
        db.insert("PERSON", null, personValues);
        db.insert("STAFF", null, staffValues);
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
        companyValues.put("EMAIL", email);

        db.insert("USER", null, userValues);
        db.insert("COMPANY", null, companyValues);
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

    void insertJob(String company, String jobTitle, String jobDescription, String jobType, String jobSalary, Long jobDueDate, Long dateCreated){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues jobValues = new ContentValues();
        jobValues.put("COMPANY", company);
        jobValues.put("JOB_TITLE", jobTitle);
        jobValues.put("JOB_DESCRIPTION", jobDescription);
        jobValues.put("JOB_TYPE", jobType);
        jobValues.put("JOB_SALARY", jobSalary);
        jobValues.put("JOB_DUE_DATE", jobDueDate);
        jobValues.put("DATE_CREATED", dateCreated);

        db.insert("JOB", null, jobValues);
    }

    public int getUserType(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT USER_TYPE FROM USER WHERE EMAIL = " + "\'" + email + "\'";
        Cursor data = db.rawQuery(query, null);
        data.moveToFirst();
        String answer = data.getString(0);
        data.close();
        int intAnswer = Integer.valueOf(answer);
        return intAnswer;
    }

    Cursor getUserRecommendations(String email, int userType){
        SQLiteDatabase db = this.getWritableDatabase();
        String query;
        switch (userType){
            case 0:
                query = "SELECT * FROM RECOMMENDATION WHERE STUDENT_RECOMMENDED = " + "\'" + email + "\'" + " ORDER BY DATE_CREATED DESC";
                break;
            case 1:
                query = "SELECT * FROM RECOMMENDATION r, JOB j WHERE j.JOB_ID = r.JOB AND RECOMMENDED_BY != " + "\'" + email + "\'" + " ORDER BY DATE_CREATED DESC";
                break;
            case 2:
                query = "SELECT * FROM RECOMMENDATION r, JOB j WHERE j.JOB_ID = r.JOB AND j.COMPANY = " + "\'" + email + "\'" + " ORDER BY DATE_CREATED DESC";
                break;
            default:
                query = "";
                break;

        }
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
        String query = "SELECT p.QUALIFICATIONS, p.ACADEMIC_HISTORY, p.NAME, s.JOB_INTERESTS, s.LOCATION_VISIBILITY, s.INTERESTED_LOCATION, s.GITHUB_LINK, s.LINKEDIN_LINK FROM STUDENT s, PERSON p WHERE s.EMAIL  = p.EMAIL AND s.EMAIL = " + "\'" + email + "\'";
        return db.rawQuery(query, null);
    }

    public Cursor getStaffProfile(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT p.QUALIFICATIONS, p.ACADEMIC_HISTORY, p.NAME, s.JOB_POSITION FROM STAFF s, PERSON p WHERE s.EMAIL  = p.EMAIL AND s.EMAIL = " + "\'" + email + "\'";
        return db.rawQuery(query, null);
    }

    public Cursor getCompanyProfile(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT COMPANY_NAME, COMPANY_DESCRIPTION FROM COMPANY WHERE EMAIL = " + "\'" + email + "\'";
        return db.rawQuery(query, null);
    }

    public Cursor getJobDetails(String email, int userType){
        SQLiteDatabase db = this.getWritableDatabase();
        String query;
        switch (userType){
            case 0:
                query = "SELECT j.COMPANY, j.JOB_TITLE, j.JOB_DESCRIPTION," +
                        " j.JOB_TYPE, j.JOB_SALARY, j.JOB_DUE_DATE, j.DATE_CREATED, r.STUDENT_RECOMMENDED," +
                        " r.RECOMMENDED_BY FROM JOB j, RECOMMENDATION r WHERE j.JOB_ID = r.JOB AND r.STUDENT_RECOMMENDED = " + "\'" + email + "\'";

                break;
            case 1:
                query = "SELECT j.COMPANY, j.JOB_TITLE, j.JOB_DESCRIPTION," +
                        " j.JOB_TYPE, j.JOB_SALARY, j.JOB_DUE_DATE, j.DATE_CREATED, r.STUDENT_RECOMMENDED," +
                        " r.RECOMMENDED_BY FROM JOB j, RECOMMENDATION r WHERE j.JOB_ID = r.JOB AND r.RECOMMENDED_BY = " + "\'" + email + "\'";

                break;
            case 2:
                query = "SELECT j.COMPANY, j.JOB_TITLE, j.JOB_DESCRIPTION," +
                        " j.JOB_TYPE, j.JOB_SALARY, j.JOB_DUE_DATE, j.DATE_CREATED, r.STUDENT_RECOMMENDED," +
                        " r.RECOMMENDED_BY FROM JOB j, RECOMMENDATION r WHERE j.JOB_ID = r.JOB AND j.COMPANY = " + "\'" + email + "\'";
                break;
            default:
                query = "";

        }
        return db.rawQuery(query, null);
    }



    public void updateCompany(String email, String name, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues companyValues = new ContentValues();
        companyValues.put("COMPANY_NAME", name);
        companyValues.put("COMPANY_DESCRIPTION", description);
        db.update("COMPANY",  companyValues, "EMAIL = " + "\'" + email + "\'", null);
    }


    public void updateStaff(String email, String name, String jobPositionText, String qualificationsText, String academicHistoryText){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues staffValues = new ContentValues();
        ContentValues personValues = new ContentValues();
        personValues.put("NAME", name);
        staffValues.put("JOB_POSITION", jobPositionText);
        personValues.put("QUALIFICATIONS", qualificationsText);
        personValues.put("ACADEMIC_HISTORY", academicHistoryText);

        db.update("PERSON",  personValues, "EMAIL = " + "\'" + email + "\'", null);
        db.update("STAFF",  staffValues, "EMAIL = " + "\'" + email + "\'", null);
    }

    public void updateStudent(String email, String qualifications, String academicHistory,
                              String name, String jobInterests,
                              boolean locationVisibility, String interestedLocation,
                              String gitHubLink, String linkedInLink){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues personValues = new ContentValues();
        ContentValues studentValues = new ContentValues();
        personValues.put("EMAIL", email);
        personValues.put("QUALIFICATIONS", qualifications);
        personValues.put("ACADEMIC_HISTORY", academicHistory);
        personValues.put("NAME", name);
        studentValues.put("EMAIL", email);
        studentValues.put("JOB_INTERESTS", jobInterests);
        studentValues.put("LOCATION_VISIBILITY", locationVisibility);
        studentValues.put("INTERESTED_LOCATION", interestedLocation);
        studentValues.put("GITHUB_LINK", gitHubLink);
        studentValues.put("LINKEDIN_LINK", linkedInLink);

        db.update("PERSON",  personValues, "EMAIL = " + "\'" + email + "\'", null);
        db.update("STUDENT",  studentValues, "EMAIL = " + "\'" + email + "\'", null);
    }

}
