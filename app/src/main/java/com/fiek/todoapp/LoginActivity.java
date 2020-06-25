package com.fiek.todoapp;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");
    private static final String TAG = "MainActivity";

    EditText mTextEmail;
    EditText mTextPassword;
    Button mButtonLogin;
    Button mButtonSignup;
    CheckBox mcheckBox;
    DatabaseHelper DB;

    private SharedPreferences mPreferences;
    private  SharedPreferences.Editor mEditor;

    ConstraintLayout loginbackground;
    AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginbackground = (ConstraintLayout) findViewById(R.id.loginbackground);
        animationDrawable = (AnimationDrawable) loginbackground.getBackground();
        animationDrawable.setEnterFadeDuration(4500);
        animationDrawable.setExitFadeDuration(4500);
        animationDrawable.start();

        DB = new DatabaseHelper(this);
        mTextEmail = (EditText)findViewById(R.id.edittext_email);
        mTextPassword = (EditText)findViewById(R.id.edittext_password);
        mButtonLogin = (Button)findViewById(R.id.button_login);
        mButtonSignup = (Button) findViewById(R.id.btSignUp);
        mcheckBox = (CheckBox) findViewById(R.id.checkBox);

        //SharedPreferences
        mPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        mPreferences=getSharedPreferences("com.fiek.todoapp", Context.MODE_PRIVATE);
        mEditor=mPreferences.edit();

        checkSharedPreferences();


        mButtonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent  = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
        mButtonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (!validateEmail() | !validatePassword()) {
                    return;
                }
                String user = mTextEmail.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();
                Boolean res = DB.checkUser(user, pwd);
                if (res == true ) {

                    Intent HomePage = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(HomePage);
                    if (mcheckBox.isChecked()) {

                        mEditor.putString(getString(R.string.checkbox), "True");
                        mEditor.commit();

                        String email = user;
                        mEditor.putString(getString(R.string.email_login), email);
                        mEditor.commit();

                        String password = pwd;
                        mEditor.putString(getString(R.string.password), password);
                        mEditor.commit();
                    }else{
                        mEditor.putString(getString(R.string.checkbox), "False");
                        mEditor.commit();

                        String email = user;
                        mEditor.putString(getString(R.string.email_login), "");
                        mEditor.commit();

                        String password = pwd;
                        mEditor.putString(getString(R.string.password), "");
                        mEditor.commit();
                    }

                }else {


                    Toast.makeText(LoginActivity.this, "Login Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void checkSharedPreferences(){
        String checkbox = mPreferences.getString(getString(R.string.checkbox), "False");
        String email = mPreferences.getString(getString(R.string.email_login), "");
        String password = mPreferences.getString(getString(R.string.password), "");

        mTextEmail.setText(email);
        mTextPassword.setText(password);

        if (checkbox.equals("True")){
            mcheckBox.setChecked(true);
        }else{
            mcheckBox.setChecked(false);
        }
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
