package com.amarpatel.eventmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Helper extends SQLiteOpenHelper {

    String t_name = "Book_event";
//    String col_1 = "id";
    String col_2 = "name";
    String col_3 = "capacity";
    String col_4 = "address";
    String col_5 = "date";
    String col_6 = "time";

    public Helper(Context context) {
        super(context, "event_management.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + t_name + "(id integer primary key, name text, capacity integer, address text, date text, time text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + t_name);
        onCreate(sqLiteDatabase);
    }

    public boolean insertdata(String name, String capacity, String address, String date, String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
//        contentValues.put(col_1,id);
        contentValues.put(col_2,name);
        contentValues.put(col_3,capacity);
        contentValues.put(col_4,address);
        contentValues.put(col_5,date);
        contentValues.put(col_6,time);
        long result = db.insert(t_name,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public String deletedata(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        return String.valueOf(db.delete(t_name,"name = ?",new String[]{name}));
    }

    public Cursor showdata(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from " + t_name,null);
    }
}
