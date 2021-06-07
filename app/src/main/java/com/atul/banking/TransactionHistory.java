package com.atul.banking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistory extends AppCompatActivity {

    public static List<TransactionData> data;
    public static TransactionRecyclerAdapter adapter;
    public static RecyclerView recyclerView;
    public static DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        data = new ArrayList<>();
        db = new DBHelper(this);

        storeTransactionDataInArrayList(this);

        recyclerView = (RecyclerView) findViewById(R.id.transactionHistoryRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TransactionRecyclerAdapter(data,this);
        recyclerView.setAdapter(adapter);
    }

    public static void storeTransactionDataInArrayList(Context context){
        Cursor cursor = db.getAllTransaction();

        if(cursor.getCount() == 0){
            Toast.makeText(context, "No Transaction Exists!", Toast.LENGTH_SHORT).show();
        }
        else{
            while (cursor.moveToNext()){
                data.add(new TransactionData(cursor.getInt(0), cursor.getString(1), cursor.getString(2),cursor.getString(3), cursor.getDouble(4),cursor.getString(5)));
            }
        }
    }
}