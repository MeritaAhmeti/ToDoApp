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
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    EditText mTextUsername;
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
        mTextUsername = (EditText)findViewById(R.id.edittext_username);
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
                String user = mTextUsername.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();
                Boolean res = DB.checkUser(user, pwd);
                if (res == true ) {

                    Intent HomePage = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(HomePage);
                    if (mcheckBox.isChecked()) {

                        mEditor.putString(getString(R.string.checkbox), "True");
                        mEditor.commit();

                        String username = user;
                        mEditor.putString(getString(R.string.username), username);
                        mEditor.commit();

                        String password = pwd;
                        mEditor.putString(getString(R.string.password), password);
                        mEditor.commit();
                    }else{
                        mEditor.putString(getString(R.string.checkbox), "False");
                        mEditor.commit();

                        String username = user;
                        mEditor.putString(getString(R.string.username), "");
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
