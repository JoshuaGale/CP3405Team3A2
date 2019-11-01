package com.example.cp3405_team_3_a2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    TextView errorText, forgotPasswordText;
    EditText emailText, passwordText;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //add to the database
        App.getContext().deleteDatabase("helper");
        databaseHelper = new DatabaseHelper(App.getContext());
        databaseHelper.insertStudent("joshua", "yeaboi",
                0, "Bachelor of Information Technology", "James Cook University", "Joshua Gale", "Network Security", false, "Townsville",
                "https://github.com/JoshuaGale", "https://www.linkedin.com/in/joshua-j-gale/");
        databaseHelper.insertStudent("thomas", "yeet",
                0, "nothing", "Bachelor of Information Technology", "thomas napier", "Data Analyst", true, "Brisbane",
                "https://github.com/thomasnapier", "https://www.linkedin.com/in/thomas-napier-466b6a170/");
        databaseHelper.insertStaff("lindsay", "pog", 1, "IT Honours",
                "James Cook University", "Lindsay Ward", "Lecturer at JCU");
        databaseHelper.insertCompany("google", "ezclap", 2,
                "Google", "Multinational, publicly-traded organization built around the company's hugely popular search engine.");
        long unixTime = System.currentTimeMillis() / 1000L;
        databaseHelper.insertJob("google", "DevOps Engineer", "A DevOps (development and operations) engineer is a specialised role that involves delivering a lot of code, quickly.", "computers", "$2,000,000.01", "10/11/10",unixTime, "sydney");
        databaseHelper.insertJob("google", "Enterprise Architect", "An enterprise architect develops the plans and workflows for deploying and maintaining servers, software, and other IT assets. In other words, an enterprise architect is on the hook to come up with (or at least, contribute to) the IT strategy.", "computers", "$100,000.00", "10/11/10",unixTime, "sydney");
        databaseHelper.insertJob("google", "Software Architect", "A software architect is responsible for setting the overall plan and strategy for executing on building software. You need to be technical for the role, but you’re really setting the pace and goals for other teams of developers", "computers", "$45,000.00", "10/11/10",unixTime, "sydney");
        databaseHelper.insertJob("google", "Applications Architect", "The applications architect chooses the strategy for applications within a company: When to use existing tools, when to build new ones, and occasionally developing their own prototypes for further development.", "computers", "$87,000.00", "10/11/10",unixTime, "sydney");
        databaseHelper.insertJob("google", "Data Warehouse Architect", "A data warehouse architect oversees all of a company’s generally-vast troves of data, and helps prepare it for analysis.", "computers", "$123,000.00", "10/11/10",unixTime, "sydney");
        databaseHelper.insertJob("google", "Software Developer", "A Software developer will be responsible for developing and testing certain systems or applications within a company.", "computers", "$326,000.00", "10/11/10",unixTime, "sydney");
        databaseHelper.insertJob("google", "Software Engineer", "A Software engineer will be tasked with developing, researching, and testing the company's software.", "computers", "$78,000.53", "10/11/10",unixTime, "sydney");
        databaseHelper.insertJob("google", "Network Administrator", "A network administrator is the person designated in an organization whose responsibility includes maintaining computer infrastructures with emphasis on networking.", "computers", "$93,000.00", "10/11/10",unixTime, "sydney");
        databaseHelper.insertJob("google", "Graduate / Junior Developer", "Multiple entry-level positions are available to develop heterogeneous software for our award-winning enterprise storage technology. Talented graduates will be invited to join our core development team and engage in a rare opportunity to gain experience across a wide range of technologies.", "Full Time", "$80,000-100,000 p.a", "10/11/10",unixTime, "Brisbane Inner City");
        databaseHelper.insertJob("google", "DevOps Engineer", "A DevOps (development and operations) engineer is a specialised role that involves delivering a lot of code, quickly.", "computers", "$100,000.00", "10/11/10",unixTime, "sydney");
        databaseHelper.insertJob("google", "Enterprise Architect", "An enterprise architect develops the plans and workflows for deploying and maintaining servers, software, and other IT assets. In other words, an enterprise architect is on the hook to come up with (or at least, contribute to) the IT strategy.", "computers", "$100,000.00", "10/11/10",unixTime, "sydney");
        databaseHelper.insertJob("google", "Software Architect", "A software architect is responsible for setting the overall plan and strategy for executing on building software. You need to be technical for the role, but you’re really setting the pace and goals for other teams of developers", "computers", "$210.00", "10/11/10",unixTime, "sydney");
        databaseHelper.insertJob("google", "Applications Architect", "The applications architect chooses the strategy for applications within a company: When to use existing tools, when to build new ones, and occasionally developing their own prototypes for further development.", "$132,00.00", "100", "10/11/10",unixTime, "sydney");
        databaseHelper.insertRecommendation("joshua", "Lindsay", 1, unixTime);
        databaseHelper.insertRecommendation("joshua", "Lindsay", 2, unixTime);
        databaseHelper.insertRecommendation("joshua", "Lindsay", 3, unixTime);
        databaseHelper.insertRecommendation("joshua", "Lindsay", 4, unixTime);
        databaseHelper.insertRecommendation("joshua", "Jason", 5, unixTime);
        databaseHelper.insertRecommendation("joshua", "Dianna", 6, unixTime);
        databaseHelper.insertRecommendation("joshua", "Jason", 7, unixTime);
        databaseHelper.insertRecommendation("joshua", "Dianna", 8, unixTime);
        databaseHelper.insertRecommendation("joshua", "Mangalem", 9, unixTime);
        databaseHelper.insertRecommendation("joshua", "Trina", 10, unixTime);
        databaseHelper.insertRecommendation("joshua", "Mangalem", 11, unixTime);
        databaseHelper.insertRecommendation("joshua", "Trina", 12, unixTime);
        databaseHelper.insertRecommendation("joshua", "Jason", 0, unixTime);
        databaseHelper.insertRecommendation("joshua", "Jason", 13, unixTime);

        errorText = findViewById(R.id.error_text);
        emailText = findViewById(R.id.email_text_field);
        passwordText = findViewById(R.id.password_text_field);
        forgotPasswordText = findViewById(R.id.forgot_password_link);

        forgotPasswordText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this,
                        ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    public void signInOnClick(View view){
        String email, password;
        Intent intent;
        boolean isValidUser;
        email = emailText.getText().toString();
        password = passwordText.getText().toString();

        //validate user input
        if(email.equals("") || password.equals("")){
            errorText.setText(R.string.missingError);
        }
        else{
            isValidUser = databaseHelper.checkLogin(email, password);
            if(isValidUser){
                intent = new Intent(this, MainActivity.class);
                //attach the user type so we can show the appropriate view
                //(could possibly just check the userType on creation of each activity)
                intent.putExtra("userType", databaseHelper.getUserType(email));
                intent.putExtra("userEmail", email);
                startActivity(intent);
            }
            else{
                errorText.setText(R.string.incorrectError);
            }
        }
    }

    public void createAccountOnClick(View view){
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        startActivity(intent);
    }
}
