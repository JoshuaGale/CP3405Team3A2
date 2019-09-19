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
                0, "nothing", "zilch", "joshua",
                "gale", "nada", false, "nowhere");

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
                intent = new Intent(this, HomeActivity.class);
                //attach the user type so we can show the appropriate view
                //(could possibly just check the userType on creation of each activity)
                intent.putExtra("userType", databaseHelper.getUserType(email));
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
