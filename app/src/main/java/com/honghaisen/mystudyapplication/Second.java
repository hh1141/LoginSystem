package com.honghaisen.mystudyapplication;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.login.widget.LoginButton;



public class Second extends AppCompatActivity {

    private TextView greeting;
    private LoginButton fbBtn;
    private Button add;
    private Button done;
    private DBHelper db;
    private FragmentManager fragmentManager;
    private static boolean fb;
    private Bundle extras;
    private static String name;
    private static String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        greeting = (TextView) findViewById(R.id.hello);
        fbBtn = (LoginButton) findViewById(R.id.fbBtn);
        add = (Button) findViewById(R.id.addBtn);
        done = (Button) findViewById(R.id.done);
        Log.d("db", "Created db");
        db = new DBHelper(this);
        extras = getIntent().getExtras();
        Log.d("onCreate", "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("onStart", "onStart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(extras != null && extras.get("email") != null) {
            email = extras.getString("email");
        }
        if(extras != null && extras.get("fb") != null) {
            fb = extras.getBoolean("fb");
        }
        if(extras != null && extras.get(Values.USER_COLUMN_NAME) != null) {
            name = extras.getString(Values.USER_COLUMN_NAME);
        }
        if (fb) {
            fbBtn.setVisibility(View.VISIBLE);
        }
        if (name == null || name.length() == 0) {
            greeting.setText("Hello");
            return;
        }
        greeting.setText("Hello, " + name);

        //clicker for add a new fragment
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Second.this, Add.class).putExtra(Values.USER_COLUMN_EMAIL, email);
                Second.this.startActivity(i);
//                fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                ItemFragment item = new ItemFragment();
//                item.setCurrentUser(extras.getString("email"));
//                fragmentTransaction.add(R.id.fragmentContainer, item);
//                fragmentTransaction.commit();
            }
        });

        //clicker for add all items into database
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Second.this, Completed.class);
                Second.this.startActivity(i);
            }
        });

        //get and list All uncompleted item
        Log.d("onResume", "onResume");
        Cursor res = db.getAllUncompletedItems();
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        while (!res.isAfterLast()) {
            ItemFragment currentFragment = new ItemFragment();
            String item = res.getString(res.getColumnIndex(Values.ITEM_COLUMN_ITEM_NAME));
            int num = res.getInt(res.getColumnIndex(Values.ITEM_COLUMN_QUANTITY));
            int id = res.getInt(res.getColumnIndex("id"));
            Log.d("item", item);
            Log.d("item", String.valueOf(num));
            currentFragment.setItemName(item);
            currentFragment.setItemQuantity(num);
            currentFragment.setId(id);
            fragmentTransaction.add(R.id.fragmentContainer, currentFragment);
            res.moveToNext();
        }
        fragmentTransaction.commit();

        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    Intent i = new Intent(Second.this, MainActivity.class);
                    Second.this.startActivity(i);
                }
            }
        };
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }
}
