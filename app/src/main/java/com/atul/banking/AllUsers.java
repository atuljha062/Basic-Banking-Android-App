package com.atul.banking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AllUsers extends AppCompatActivity implements RecyclerAdapter.RecyclerClickListner {

    RecyclerView recyclerView;
    public static List<UserData> data;
    public static DBHelper db;
    public static RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);

        data = new ArrayList<>();
        db = new DBHelper(this);

        storeDataInArrayList(this);

        recyclerView = (RecyclerView) findViewById(R.id.AllUserRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter = new RecyclerAdapter(data,this);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(getApplicationContext(),IndividualUser.class);
        intent.putExtra("name",data.get(position).getName());
        intent.putExtra("mobile",data.get(position).getMobileNo());
        intent.putExtra("email",data.get(position).getEmail());
        intent.putExtra("account",data.get(position).getAccountNo());
        intent.putExtra("ifsc",data.get(position).getIfsc());
        intent.putExtra("balance",data.get(position).getBalance());

        startActivity(intent);
    }

    public static void storeDataInArrayList(Context context){
        Cursor cursor = db.getAllUser();
        if(cursor.getCount() == 0){
            Toast.makeText(context, "No Data Exist", Toast.LENGTH_SHORT).show();
        }
        else{
            while(cursor.moveToNext()){
                data.add(new UserData(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getDouble(5)));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(getApplicationContext(),TransactionHistory.class);
        startActivity(intent);
        return true;
    }

    int backPressed = 0;
    @Override
    public void onBackPressed() {
        backPressed += 1;

        if(backPressed > 1){
            super.onBackPressed();
        }
        else{
            Toast.makeText(this, "Press Back again to Exit!", Toast.LENGTH_SHORT).show();
        }
    }
}