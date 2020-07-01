package com.fiek.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.allyants.notifyme.NotifyMe;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import java.util.Calendar;


public class EditTask extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    EditText titletodo,desctodo,datetodo;
    Button btnSaveUpdate, btnDelete, notify;
    DatabaseReference reference;
    Calendar now = Calendar.getInstance();
    TimePickerDialog tpd;
    DatePickerDialog dpd;

    private ToDoAdapter mAdapter;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    public static String uploadID;
    public static String Key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        titletodo = findViewById(R.id.titletodo);
        desctodo = findViewById(R.id.desctodo);
        datetodo = findViewById(R.id.datetodo);

        btnSaveUpdate = findViewById(R.id.btnSaveUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        notify = findViewById(R.id.notify);

        titletodo.setText(getIntent().getStringExtra("titletodo"));
        desctodo.setText(getIntent().getStringExtra("desctodo"));
        datetodo.setText(getIntent().getStringExtra("datetodo"));

        dpd = DatePickerDialog.newInstance(
                EditTask.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        tpd = TimePickerDialog.newInstance(
                EditTask.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                now.get(Calendar.SECOND),
                false
        );

        final String keykeytodo = getIntent().getStringExtra("keytodo");
        final String userId=FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotifyMe.cancel(getApplicationContext(),"test");
                MyToDo myToDo;
                mDatabaseRef.child("ToDos").child(userId).child(keykeytodo).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            Toast.makeText(EditTask.this, "Task Deleted!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(EditTask.this, "Authentication Error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotifyMe.cancel(getApplicationContext(),"test");
                dpd.show(getSupportFragmentManager(), "Datepickerdialog");
                mDBListener = mDatabaseRef.child("ToDos").child(userId).child(keykeytodo).addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String uploadId = mDatabaseRef.push().getKey();

                        uploadID = uploadId;

                        final String title= titletodo.getText().toString();
                        final String date = datetodo.getText().toString();
                        final String desc = desctodo.getText().toString();
                        final String key = keykeytodo;
                        if(title.equals("titletodo")) {
                            dataSnapshot.getRef().child("ToDos").child(userId).child(keykeytodo).setValue(titletodo.getText().toString());
                        }else if(date.equals("datetodo")) {
                            dataSnapshot.getRef().child("ToDos").child(userId).child(keykeytodo).setValue(datetodo.getText().toString());
                        }else if (desc.equals("desctodo")) {
                            dataSnapshot.getRef().child("ToDos").child(userId).child(keykeytodo).setValue(desctodo.getText().toString());
                        }else if (key.equals("keykeytodo")) {
                            dataSnapshot.getRef().child("ToDos").child(userId).child(keykeytodo).setValue(keykeytodo);
                        }else {
                            writeNewPost(userId, uploadId, title, date, desc);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Snackbar.make(findViewById(R.id.rledit), "No Data.", Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        });

        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDBListener = mDatabaseRef.child("ToDos").child(userId).child(keykeytodo).addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String uploadId = mDatabaseRef.push().getKey();
                        uploadID = uploadId;
                        final String title= titletodo.getText().toString();
                        final String date = datetodo.getText().toString();
                        final String desc = desctodo.getText().toString();
                        final String key = keykeytodo;
                        if(title.equals("titletodo")) {
                            dataSnapshot.getRef().child("ToDos").child(userId).child(keykeytodo).setValue(titletodo.getText().toString());
                        }else if(date.equals("datetodo")) {
                            dataSnapshot.getRef().child("ToDos").child(userId).child(keykeytodo).setValue(datetodo.getText().toString());
                        }else if (desc.equals("desctodo")) {
                            dataSnapshot.getRef().child("ToDos").child(userId).child(keykeytodo).setValue(desctodo.getText().toString());
                        }else if (key.equals("keykeytodo")) {
                            dataSnapshot.getRef().child("ToDos").child(userId).child(keykeytodo).setValue(keykeytodo);
                        }else {
                            writeNewPost(userId, uploadId, title, date, desc);
                        }
                        Intent a = new Intent(EditTask.this, MainActivity.class);
                        startActivity(a);
                        Toast.makeText(EditTask.this, "Task Updated", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Snackbar.make(findViewById(R.id.rledit), "No Data.", Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        now.set(Calendar.YEAR, year);
        now.set(Calendar.MONTH, monthOfYear);
        now.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        tpd.show(getSupportFragmentManager(), "Timepickerdialog");
    }
    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        now.set(Calendar.HOUR_OF_DAY, hourOfDay);
        now.set(Calendar.MINUTE, minute);
        now.set(Calendar.SECOND, second);
        Intent intent = new Intent(getApplicationContext(), SnoozingActivity.class);
        intent.putExtra("test", "I am a String");

        NotifyMe notifyMe = new NotifyMe.Builder(getApplicationContext())
                .title(titletodo.getText().toString())
                .content(desctodo.getText().toString())
                .color(255, 0, 0, 255)
                .led_color(255, 255, 255, 255)
                .time(now)
                .addAction(intent, "Snooze", false)
                .key("test")
                .addAction(new Intent(), "Dismiss", true, false)
                .addAction(intent, "Done")
                .large_icon(R.mipmap.ic_launcher_round)
                .rrule("FREQ=MINUTELY;INTERVAL=5;COUNT=2")
                .build();
        Intent a = new Intent(EditTask.this, MainActivity.class);
        startActivity(a);
    }

    private boolean writeNewPost(String userId,String uploadID, String title,String date,String desc) {
        final String key = getIntent().getStringExtra("keytodo");
        MyToDo myToDo = new MyToDo(title,date,desc,key);
        mDatabaseRef.child("ToDos").child(userId).child("/" + key).setValue(myToDo);
        return true;
    }
}
