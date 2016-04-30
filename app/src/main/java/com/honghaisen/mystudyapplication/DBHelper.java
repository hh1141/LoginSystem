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
        super(context, Values.DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Users" +
                "(email text PRIMARY KEY, password text NOT NULL, name text NOT NULL, phone text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Users");
        onCreate(db);
    }

    public boolean matches(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM Users WHERE email=? AND password=?", new String[]{email, password});



        return res.getCount() == 1;
    }

    public boolean insert(String email, String password, String name, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(Values.USER_COLUMN_EMAIL, email);
        content.put(Values.USER_COLUMN_PASS, password);
        content.put(Values.USER_COLUMN_NAME, name);
        content.put(Values.USER_COLUMN_PHONE, phone);
        long before = db.rawQuery("SELECT * FROM Users", null).getCount();
        long after = db.insert("Users", null, content);
        //debug
        getAll();
        Log.d("num of res before", String.valueOf(before));
        Log.d("num of res after", String.valueOf(after));

        if(after - before == 1) {
            return true;
        }
        return false;
    }

    public void getAll() {
        List<Cursor> res = new ArrayList<Cursor>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Users", null);
        c.moveToFirst();
        while(!c.isAfterLast()) {
            Log.d("register", c.getString(c.getColumnIndex(Values.USER_COLUMN_EMAIL)));
            c.moveToNext();
        }
    }

    public Cursor getData(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM Users WHERE email=?", new String[]{email});
        return res;
    }

    public void update(String email, String password, String name, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(Values.USER_COLUMN_PASS, password);
        content.put(Values.USER_COLUMN_NAME, name);
        content.put(Values.USER_COLUMN_PHONE, phone);
        db.update("Users", content, "email = ?", new String[]{email});
    }

    public void delete(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Users", "email = ?", new String[]{email});
    }
}
