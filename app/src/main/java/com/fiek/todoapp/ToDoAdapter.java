package com.fiek.todoapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder>{
    Context context;
    ArrayList<MyToDo> myTodo;

    public ToDoAdapter(Context c, ArrayList<MyToDo> p) {
        context = c;
        myTodo = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_todo,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        myViewHolder.titletodo.setText(myTodo.get(i).getTitle());
        myViewHolder.desctodo.setText(myTodo.get(i).getDesc());
        myViewHolder.datetodo.setText(myTodo.get(i).getDate());
        final String getTitletodo = myTodo.get(i).getTitle();
        final String getDesctodo = myTodo.get(i).getDesc();
        final String getDatetodo = myTodo.get(i).getDate();
        final String getKeytodo = myTodo.get(i).getKey();
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aa = new Intent(context,EditTask.class);
                aa.putExtra("titletodo", getTitletodo);
                aa.putExtra("desctodo", getDesctodo);
                aa.putExtra("datetodo", getDatetodo);
                aa.putExtra("keytodo", getKeytodo);
                context.startActivity(aa);
            }
        });
    }
    @Override
    public int getItemCount() {
        return myTodo.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titletodo, desctodo, datetodo, keytodo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titletodo = (TextView) itemView.findViewById(R.id.titletodo);
            desctodo = (TextView) itemView.findViewById(R.id.desctodo);
            datetodo = (TextView) itemView.findViewById(R.id.datetodo);
        }
    }
}