package com.honghaisen.mystudyapplication;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class Completed extends AppCompatActivity {

    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed);

        db = new DBHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        List<Cursor> items = db.getAllCompletedItems();
        for(Cursor item : items) {

        }
    }
}
