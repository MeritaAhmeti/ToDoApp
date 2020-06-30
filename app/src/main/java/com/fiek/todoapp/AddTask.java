package com.fiek.todoapp;

import com.allyants.notifyme.NotifyMe;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Calendar;
import java.util.Random;


public class AddTask extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    TextView titlepage, addtitle, adddesc, adddate;
    EditText titletodo, desctodo, datetodo;
    Button btnSaveTask, btnCancel,notify;
    DatabaseReference reference;
    Integer todoNum = new Random().nextInt();
    String keytodo = Integer.toString(todoNum);
    Calendar now = Calendar.getInstance();
    TimePickerDialog tpd;
    DatePickerDialog dpd;
    private DatabaseReference mDatabaseRef;


    private ValueEventListener mDBListener;
    public static String uploadID;
    public static String Key;
    ToDoAdapter toDoAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);


        titlepage = findViewById(R.id.titlepage);

        addtitle = findViewById(R.id.addtitle);
        adddesc = findViewById(R.id.adddesc);
        adddate = findViewById(R.id.adddate);

        titletodo = findViewById(R.id.titletodo);
        desctodo = findViewById(R.id.desctodo);
        datetodo = findViewById(R.id.datetodo);

        btnSaveTask = findViewById(R.id.btnSaveTask);
        btnCancel = findViewById(R.id.btnCancel);
        notify = findViewById(R.id.notify);


        dpd = DatePickerDialog.newInstance(
                AddTask.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)

        );

        tpd = TimePickerDialog.newInstance(
                AddTask.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                now.get(Calendar.SECOND),
                false
        );



        btnSaveTask.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                mDatabaseRef = FirebaseDatabase.getInstance().getReference();


                mDBListener = mDatabaseRef.child("ToDos").child(userId).child("MyDos").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String uploadId = mDatabaseRef.push().getKey();
                        uploadID = uploadId;


                        final String title = titletodo.getText().toString();
                        final String date = datetodo.getText().toString();
                        final String desc = desctodo.getText().toString();

                        writeNewPost(userId, uploadId, title, date, desc);
                        if (TextUtils.isEmpty(title)) {
                            titletodo.setError("title is Required.");
                            return;
                        } else {

                            Intent a = new Intent(AddTask.this, MainActivity.class);
                            startActivity(a);
                            Toast.makeText(AddTask.this, "Todo created", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Snackbar.make(findViewById(R.id.rl), "No Data.", Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        });




        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String title = titletodo.getText().toString();
                if (TextUtils.isEmpty(title)) {
                    titletodo.setError("title is Required.");
                    return;
                } else {
                    dpd.show(getSupportFragmentManager(), "Datepickerdialog");
                    final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    mDatabaseRef = FirebaseDatabase.getInstance().getReference();

                    mDBListener = mDatabaseRef.child("ToDos").child(userId).child("MyDos").addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String uploadId = mDatabaseRef.push().getKey();

                            uploadID = uploadId;

                            final String title = titletodo.getText().toString();
                            final String date = datetodo.getText().toString();
                            final String desc = desctodo.getText().toString();

                            writeNewPost(userId, uploadId, title, date, desc);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Snackbar.make(findViewById(R.id.rl), "No Data.", Snackbar.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(AddTask.this, MainActivity.class);
                startActivity(a);
            }
        });

    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        now.set(Calendar.YEAR,year);
        now.set(Calendar.MONTH,monthOfYear);
        now.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        tpd.show(getSupportFragmentManager(), "Timepickerdialog");
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        now.set(Calendar.HOUR_OF_DAY,hourOfDay);
        now.set(Calendar.MINUTE,minute);
        now.set(Calendar.SECOND,second);
        Intent intent = new Intent(getApplicationContext(),SnoozingActivity.class);
        intent.putExtra("test","I am a String");

        NotifyMe notifyMe = new NotifyMe.Builder(getApplicationContext())
                .title(titletodo.getText().toString())
                .content(desctodo.getText().toString())
                .color(255,0,0,255)
                .led_color(255,255,255,255)
                .time(now)
                .addAction(intent,"Snooze",false)
                .key("test")
                .addAction(new Intent(),"Dismiss",true,false)
                .addAction(intent,"Done")
                .large_icon(R.mipmap.ic_launcher_round)
                .rrule("FREQ=MINUTELY;INTERVAL=5;COUNT=2")
                .build();
        Intent a = new Intent(AddTask.this,MainActivity.class);
        startActivity(a);
    }


    private void writeNewPost(String userId,String uploadID, String title,String date,String desc) {

        String key = mDatabaseRef.child("ToDos").push().getKey();
        if (TextUtils.isEmpty(title)) {
            return;
        }else{
        MyToDo myToDo = new MyToDo(title,date,desc,key);
        Key = key;
        mDatabaseRef.child("ToDos").child(userId).child(key).setValue(myToDo);}
    }
}
