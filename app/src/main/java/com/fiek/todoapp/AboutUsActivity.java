package com.fiek.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AboutUsActivity extends AppCompatActivity {
   public static TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        data = (TextView)findViewById(R.id.fetcheddata);

        fetchData process = new fetchData();
        process.execute();


    }
}
