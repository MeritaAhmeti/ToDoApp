package com.fiek.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");
    DatabaseHelper DB;
    EditText mTextEmail;
    EditText mTextPassword;
    EditText mTextCnfPassword;
    Button mButtonRegister;
    Button mButtonLogin;

    ConstraintLayout signupbackground;
    AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        signupbackground = (ConstraintLayout) findViewById(R.id.signupbackground);
        animationDrawable = (AnimationDrawable) signupbackground.getBackground();
        animationDrawable.setEnterFadeDuration(4500);
        animationDrawable.setExitFadeDuration(4500);
        animationDrawable.start();


        DB = new DatabaseHelper(this);
        mTextEmail = (EditText)findViewById(R.id.edittext_email);
        mTextPassword = (EditText)findViewById(R.id.edittext_password);
        mTextCnfPassword = (EditText)findViewById(R.id.edittext_cnf_password);
        mButtonRegister = (Button)findViewById(R.id.button_register);
        mButtonLogin = (Button) findViewById(R.id.button_cancel);
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LoginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(LoginIntent);
            }
        });

        mButtonRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                if (!validateEmail() | !validatePassword()) {
                    return;
                }
                String email = mTextEmail.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();
                String cnf_pwd = mTextCnfPassword.getText().toString().trim();

                if (pwd.equals(cnf_pwd)) {
                    long val = DB.addUser(email,pwd);
                    if(val > 0){
                        Toast.makeText(RegisterActivity.this, "You have registered", Toast.LENGTH_SHORT).show();
                        Intent moveToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(moveToLogin);
                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this, "Registeration Error", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(RegisterActivity.this, "Password is not matching", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
    private boolean validateEmail() {
        String emailInput = mTextEmail.getText().toString().trim();
        if (emailInput.isEmpty()) {
            mTextEmail.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            mTextEmail.setError("Please enter a valid email address");
            return false;
        } else {
            mTextEmail.setError(null);
            return true;
        }
    }
    private boolean validatePassword() {
        String passwordInput = mTextPassword.getText().toString().trim();
        if (passwordInput.isEmpty()) {
            mTextPassword.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            mTextPassword.setError("Password too weak");
            return false;
        } else {
            mTextPassword.setError(null);
            return true;
        }
    }

}
