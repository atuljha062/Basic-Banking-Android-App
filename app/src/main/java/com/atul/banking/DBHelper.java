package com.atul.banking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private final String TABLE1 = "userTable";
    private final String TABLE2 = "transactionTable";

    public DBHelper(@Nullable Context context) {
        super(context, "User.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table userTable(name TEXT, mobileNo TEXT primary key, email VARCHAR, accountNo TEXT, ifsc VARCHAR, balance DECIMAL)");
        db.execSQL("create table transactionTable(transactionID INTEGER primary key autoincrement, date TEXT, fromName TEXT, toName TEXT, amount DECIMAL, status TEXT)");

        db.execSQL("insert into userTable values('Ashish','9876543210', 'ashish123@gmail.com','12XXXXX89', 'ABC123X',100000)");
        db.execSQL("insert into userTable values('Arnav','8562494610', 'arnav001@gmail.com','63XXXXX20', 'CBA123X', 75000)");
        db.execSQL("insert into userTable values('David','7492016451', 'david124@gmail.com','57XXXXX28', 'XYZ123A',50000)");
        db.execSQL("insert into userTable values('Gautam','6759355324', 'gautam421@gmail.com','93XXXXX63', 'EGS321X',25000)");
        db.execSQL("insert into userTable values('Hardik','9647396779', 'hardik007@gmail.com','12XXXXX89', 'WWU123M',10000)");
        db.execSQL("insert into userTable values('Jacky','8463065428', 'jacky01@gmail.com','21XXXXX73', 'KOL743J',80000)");
        db.execSQL("insert into userTable values('Nelson','6028174106', 'nelson96@gmail.com','49XXXXX12', 'DEL856M',35000)");
        db.execSQL("insert into userTable values('Pawan','7078695097', 'pawan02@gmail.com','39XXXXX37', 'BHU054Q',90000)");
        db.execSQL("insert into userTable values('Rahul','6213532133', 'rahul20@gmail.com','72XXXXX28', 'MUM823R',5000)");
        db.execSQL("insert into userTable values('Suman','9746587467', 'suman7@gmail.com','60XXXXX67', 'CHE381L',60000)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists userTable");
        db.execSQL("drop table if exists transactionTable");
    }

    public Cursor getAllUser(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from userTable order by name asc",null);
        return cursor;
    }

//    public Cursor getSpecificUser(String mobileNo){
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("select * from userTable where mobileNo = ?",new String[]{mobileNo});
//    }

    public Boolean insertTransaction(String date, String fromName, String toName, String amount, String status){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("date",date);
        contentValues.put("fromName",fromName);
        contentValues.put("toName",toName);
        contentValues.put("amount",amount);
        contentValues.put("status",status);

        long result = db.insert(TABLE2,null,contentValues);

        if(result == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public Cursor getAllTransaction(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from transactionTable order by transactionId desc",null);

        return cursor;
    }

    public Boolean updateBalance(String mobileNo, double newBalance){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("balance",newBalance);

        Cursor cursor = db.rawQuery("select * from userTable where mobileNo = ?",new String[]{mobileNo});

        if(cursor.getCount()>0){
            long result = db.update("userTable",contentValues,"mobileNo = ?",new String[]{mobileNo});

            if(result == -1){
                return false;
            }
            else {
                return true;
            }
        }
        else{
            return false;
        }
    }
}
