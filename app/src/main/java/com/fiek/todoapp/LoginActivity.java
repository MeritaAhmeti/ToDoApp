package com.fiek.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText mTextUsername;
    EditText mTextPassword;
    Button mButtonLogin;
    Button mButtonSignup;
    DatabaseHelper DB;

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
        mTextUsername = (EditText)findViewById(R.id.edittext_username);
        mTextPassword = (EditText)findViewById(R.id.edittext_password);
        mButtonLogin = (Button)findViewById(R.id.button_login);
        mButtonSignup = (Button) findViewById(R.id.btSignUp);
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
                String user = mTextUsername.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();
                Boolean res = DB.checkUser(user, pwd);
                if (res == true) {
                    Intent HomePage = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(HomePage);
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Login Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void checkSharedPreferences(){
        String checkbox = mPreferences.getString(getString(R.string.checkbox), "False");
        String username = mPreferences.getString(getString(R.string.username), "");
        String password = mPreferences.getString(getString(R.string.password), "");

        mTextUsername.setText(username);
        mTextPassword.setText(password);

        if (checkbox.equals("True")){
            mcheckBox.setChecked(true);
        }else{
            mcheckBox.setChecked(false);
        }
    }
}
