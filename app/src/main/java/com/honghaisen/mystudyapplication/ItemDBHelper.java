package com.honghaisen.mystudyapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hison7463 on 5/2/16.
 */
public class ItemDBHelper extends SQLiteOpenHelper {


    public ItemDBHelper(Context context) {
        super(context, Values.ITEM_DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
