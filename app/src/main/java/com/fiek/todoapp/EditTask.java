package com.fiek.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditTask extends AppCompatActivity {

    EditText titletodo,desctodo,datetodo;
    Button btnSaveUpdate, btnDelete;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        titletodo = findViewById(R.id.titletodo);
        desctodo = findViewById(R.id.desctodo);
        datetodo = findViewById(R.id.datetodo);

        btnSaveUpdate = findViewById(R.id.btnSaveUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        titletodo.setText(getIntent().getStringExtra("titletodo"));
        desctodo.setText(getIntent().getStringExtra("desctodo"));
        datetodo.setText(getIntent().getStringExtra("datetodo"));

        final String keykeytodo = getIntent().getStringExtra("keytodo");

        reference = FirebaseDatabase.getInstance().getReference().child("ToDoApp").
                child("Todo" + keykeytodo);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                         if(task.isSuccessful()){
                             Intent a = new Intent(EditTask.this,MainActivity.class);
                             startActivity(a);
                         }else {
                             Toast.makeText(getApplicationContext(),"Failure!",Toast.LENGTH_SHORT).show();
                         }
                    }
                });
            }
        });

        //make an event for button
        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = FirebaseDatabase.getInstance().getReference().child("ToDoApp").
                        child("Todo" + keykeytodo);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("titletodo").setValue(titletodo.getText().toString());
                        dataSnapshot.getRef().child("desctodo").setValue(desctodo.getText().toString());
                        dataSnapshot.getRef().child("datetodo").setValue(datetodo.getText().toString());
                        dataSnapshot.getRef().child("keytodo").setValue(keykeytodo);

                        Intent a = new Intent(EditTask.this,MainActivity.class);
                        startActivity(a);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}
