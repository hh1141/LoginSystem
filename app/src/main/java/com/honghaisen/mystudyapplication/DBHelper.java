package com.honghaisen.mystudyapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hison7463 on 4/26/16.
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, Values.DATABASE_NAME, null, 6);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("onCreate", "table created");
        db.execSQL("CREATE TABLE Items" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT NOT NULL, item TEXT NOT NULL, quantity INTEGER NOT NULL, done BOOLEAN)");
        db.execSQL("CREATE TABLE User" +
                "(email TEXT PRIMARY KEY, password TEXT NOT NULL, name TEXT NOT NULL, phone TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS Items");
        onCreate(db);
    }

    public boolean matches(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM User WHERE email=? AND password=?", new String[]{email, password});
        return res.getCount() == 1;
    }

    public boolean insertItem(String email, String item, int quantity, boolean done) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(Values.ITEM_COLUMN_EMAIL, email);
        content.put(Values.ITEM_COLUMN_ITEM_NAME, item);
        content.put(Values.ITEM_COLUMN_QUANTITY, quantity);
        content.put(Values.ITEM_COLUMN_DONE, done);
        long before = db.rawQuery("SELECT * FROM Items", null).getCount();
        long after = db.insert("Items", null, content);
        if(after - before == 1) {
            return true;
        }
        return false;
    }

    public boolean insert(String email, String password, String name, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(Values.USER_COLUMN_EMAIL, email);
        content.put(Values.USER_COLUMN_PASS, password);
        content.put(Values.USER_COLUMN_NAME, name);
        content.put(Values.USER_COLUMN_PHONE, phone);
        long before = db.rawQuery("SELECT * FROM User", null).getCount();
        long after = db.insert("User", null, content);
        //debug
//        getAll();
        Log.d("num of res before", String.valueOf(before));
        Log.d("num of res after", String.valueOf(after));

        if(after - before == 1) {
            return true;
        }
        return false;
    }

    public Cursor getAllCompletedItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Items WHERE done=1", null);
        c.moveToFirst();
        return c;
    }

    public Cursor getAllUncompletedItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Items WHERE done=0", null);
        c.moveToFirst();
        return c;
    }

    public Cursor getAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM User", null);
        c.moveToFirst();
        return c;
    }

    public Cursor getData(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM User WHERE email=?", new String[]{email});
        return res;
    }

    public void updateItem(int id, boolean done) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(Values.ITEM_COLUMN_DONE, done);
        db.update("Items", content, "id=?", new String[]{String.valueOf(id)});
    }

    public void update(String email, String password, String name, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(Values.USER_COLUMN_PASS, password);
        content.put(Values.USER_COLUMN_NAME, name);
        content.put(Values.USER_COLUMN_PHONE, phone);
        db.update("User", content, "email = ?", new String[]{email});
    }

    public void deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Items", "id=?", new String[]{String.valueOf(id)});
    }

    public void delete(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("User", "email = ?", new String[]{email});
    }
}
