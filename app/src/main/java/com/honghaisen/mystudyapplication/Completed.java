package com.honghaisen.mystudyapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Completed extends AppCompatActivity {

    private DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed);

        db = new DBHelper(this);
    }


}
