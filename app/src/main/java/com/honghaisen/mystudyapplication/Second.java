package com.honghaisen.mystudyapplication;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.login.widget.LoginButton;

import java.util.List;

public class Second extends AppCompatActivity {

    private TextView greeting;
    private LoginButton fbBtn;
    private Button add;
    private Button done;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        greeting = (TextView) findViewById(R.id.hello);
        fbBtn = (LoginButton) findViewById(R.id.fbBtn);
        add = (Button) findViewById(R.id.addBtn);
        done = (Button) findViewById(R.id.done);
        db = new DBHelper(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Bundle extras = getIntent().getExtras();
        boolean fb = extras.getBoolean("fb");
        if(fb) {
            fbBtn.setVisibility(View.VISIBLE);
        }
        if(extras.get(Values.USER_COLUMN_NAME) == null) {
            greeting.setText("Hello");
            return;
        }
        greeting.setText("Hello, " + extras.getString(Values.USER_COLUMN_NAME));

        //get All uncompleted item
        List<Cursor> res = db.getAllUncompletedItems();
        
    }

    @Override
    protected void onResume() {
        super.onResume();

        //clicker for add a new fragment
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment item = new ItemFragment();
                fragmentTransaction.add(R.id.fragmentContainer, item, item.toString());
                fragmentTransaction.commit();
            }
        });

        //clicker for add all items into database
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(currentAccessToken == null) {
                    Intent i = new Intent(Second.this, MainActivity.class);
                    Second.this.startActivity(i);
                }
            }
        };
    }
}
