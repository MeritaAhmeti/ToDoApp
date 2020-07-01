package com.fiek.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    EditText mTextEmail;
    EditText mTextPassword;
    Button mButtonLogin;
    Button mButtonRegister;
    CheckBox mcheckBox;
    FirebaseAuth fAuth;


    private SharedPreferences mPreferences;
    private  SharedPreferences.Editor mEditor;

    ConstraintLayout loginbackground;
    AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fAuth = FirebaseAuth.getInstance();

        loginbackground = (ConstraintLayout) findViewById(R.id.loginbackground);
        animationDrawable = (AnimationDrawable) loginbackground.getBackground();
        animationDrawable.setEnterFadeDuration(4500);
        animationDrawable.setExitFadeDuration(4500);
        animationDrawable.start();

        mTextEmail = (EditText)findViewById(R.id.edittext_email);
        mTextPassword = (EditText)findViewById(R.id.edittext_password);
        mButtonLogin = (Button)findViewById(R.id.button_login);
        mButtonRegister = (Button) findViewById(R.id.button_register);
        mcheckBox = (CheckBox) findViewById(R.id.checkBox);

        mPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        mPreferences=getSharedPreferences("com.fiek.todoapp", Context.MODE_PRIVATE);
        mEditor=mPreferences.edit();

        checkSharedPreferences();

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mTextEmail.getText().toString().trim();
                String password = mTextPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mTextEmail.setError("Email is Required.");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mTextPassword.setError("Password is Required.");
                    return;
                }
                if (password.length() < 6) {
                    mTextPassword.setError("Password Must be >= 6 Characters");
                    return;
                }
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String user = mTextEmail.getText().toString().trim();
                            String pwd = mTextPassword.getText().toString().trim();
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

                                mTextEmail.setText("");
                                mTextPassword.setText("");
                            }
                            Toast.makeText(LoginActivity.this, "Logged in Successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });
    }
    private void checkSharedPreferences(){
        String checkbox = mPreferences.getString(getString(R.string.checkbox), "False!");
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
}