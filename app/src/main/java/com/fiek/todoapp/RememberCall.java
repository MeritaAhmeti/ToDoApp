package com.fiek.todoapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class RememberCall extends AppCompatActivity {
    EditText edText;
    Button btAdd;
    ListView listView;
    private Toolbar mToolbar;

    DatabaseHelp databaseHelp;
    ArrayList arrayList;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remember_call);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Remember call");

        edText = findViewById(R.id.et_text);
        btAdd = findViewById(R.id.bt_add);
        listView = findViewById(R.id.list_view);

        databaseHelp = new DatabaseHelp(RememberCall.this);

        arrayList = databaseHelp.getAllText();
        arrayAdapter = new ArrayAdapter(RememberCall.this,android.R.layout.simple_list_item_1,arrayList);

        listView.setAdapter(arrayAdapter);

        btAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String text = edText.getText().toString();
                if(!text.isEmpty()){
                    if(databaseHelp.addText(text)){
                        edText.setText("");
                        Toast.makeText(getApplicationContext(),"Data Inserted...",Toast.LENGTH_SHORT).show();
                        arrayList.clear();
                        arrayList.addAll(databaseHelp.getAllText());
                        arrayAdapter.notifyDataSetChanged();
                        listView.invalidateViews();
                        listView.refreshDrawableState();
                    }
                }
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                Intent intent = new Intent(RememberCall.this, ContactFragment.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}