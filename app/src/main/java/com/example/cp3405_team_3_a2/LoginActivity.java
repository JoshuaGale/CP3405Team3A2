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
                0, "nothing", "zilch", "joshua gale", "nada", false, "nowhere",
                "https://github.com/JoshuaGale", "https://www.linkedin.com/in/joshua-j-gale/");
        databaseHelper.insertStudent("thomas", "yeet",
                0, "nothing", "zilch", "thomas napier", "something", true, "nowhere",
                "https://github.com/thomasnapier", "https://www.linkedin.com/in/thomas-napier-466b6a170/");
        databaseHelper.insertStaff("lindsay", "pog", 1, "IT Honours",
                "Academic for 25 years", "Lindsay Ward", "Lecturer at JCU");
        databaseHelper.insertCompany("google", "ezclap", 2,
                "Google", "We are google, give us your data");
        long unixTime = System.currentTimeMillis() / 1000L;
        databaseHelper.insertJob("google", "top meme", "you make memes", "computers", "100", (unixTime + 10000L),unixTime);
        databaseHelper.insertJob("google", "top meme2", "you make memes", "computers", "100", (unixTime + 10000L),unixTime);
        databaseHelper.insertJob("google", "top meme3", "you make memes", "computers", "100", (unixTime + 10000L),unixTime);
        databaseHelper.insertJob("google", "top meme4", "you make memes", "computers", "100", (unixTime + 10000L),unixTime);
        databaseHelper.insertJob("google", "top meme5", "you make memes", "computers", "100", (unixTime + 10000L),unixTime);
        databaseHelper.insertJob("google", "top meme6", "you make memes", "computers", "100", (unixTime + 10000L),unixTime);
        databaseHelper.insertJob("google", "top meme7", "you make memes", "computers", "100", (unixTime + 10000L),unixTime);
        databaseHelper.insertJob("google", "top meme8", "you make memes", "computers", "100", (unixTime + 10000L),unixTime);
        databaseHelper.insertJob("google", "top meme9", "you make memes", "computers", "100", (unixTime + 10000L),unixTime);
        databaseHelper.insertJob("google", "top meme10", "you make memes", "computers", "100", (unixTime + 10000L),unixTime);
        databaseHelper.insertJob("google", "top meme11", "you make memes", "computers", "100", (unixTime + 10000L),unixTime);
        databaseHelper.insertJob("google", "top meme12", "you make memes", "computers", "100", (unixTime + 10000L),unixTime);
        databaseHelper.insertRecommendation("joshua", "Tom", 1, unixTime);
        databaseHelper.insertRecommendation("joshua", "lindsay", 2, unixTime);
        databaseHelper.insertRecommendation("joshua", "Tom3", 3, unixTime);
        databaseHelper.insertRecommendation("joshua", "Tom4", 4, unixTime);
        databaseHelper.insertRecommendation("joshua", "Tom5", 5, unixTime);
        databaseHelper.insertRecommendation("joshua", "Tom6", 6, unixTime);
        databaseHelper.insertRecommendation("joshua", "Tom7", 7, unixTime);
        databaseHelper.insertRecommendation("joshua", "Tom8", 8, unixTime);
        databaseHelper.insertRecommendation("joshua", "Tom9", 9, unixTime);
        databaseHelper.insertRecommendation("joshua", "Tom10", 10, unixTime);
        databaseHelper.insertRecommendation("joshua", "Tom11", 11, unixTime);
        databaseHelper.insertRecommendation("joshua", "Tom12", 12, unixTime);
        databaseHelper.insertRecommendation("joshua", "James", 0, unixTime);

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
