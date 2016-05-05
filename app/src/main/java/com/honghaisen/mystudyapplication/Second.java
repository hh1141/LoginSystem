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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.login.widget.LoginButton;
import com.honghaisen.mystudyapplication.fragments.AddFragment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


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
    private boolean bought;
    private List<ItemFragment> fragmentList;
    private List<ShowItemFragment> completedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        greeting = (TextView) findViewById(R.id.hello);
        fbBtn = (LoginButton) findViewById(R.id.fbBtn);
        add = (Button) findViewById(R.id.addBtn);
        done = (Button) findViewById(R.id.done);
        fragmentList = new LinkedList<ItemFragment>();
        completedList = new LinkedList<ShowItemFragment>();
        db = new DBHelper(this);
        extras = getIntent().getExtras();
        bought = false;
        Log.d("onCreate", "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("onStart", "onStart");
        fragmentManager = getFragmentManager();

        //get and list All uncompleted items
        Cursor res = db.getAllUncompletedItems();
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
            fragmentList.add(currentFragment);
            fragmentTransaction.add(R.id.fragmentContainer, currentFragment);
            res.moveToNext();
        }
        fragmentTransaction.commit();

        //get all completed items
        Cursor items = db.getAllCompletedItems();
        while (!items.isAfterLast()) {
            String itemName = items.getString(items.getColumnIndex(Values.ITEM_COLUMN_ITEM_NAME));
            String itemQuantity = items.getString(items.getColumnIndex(Values.ITEM_COLUMN_QUANTITY));
            ShowItemFragment cur = new ShowItemFragment();
            cur.setItemName(itemName);
            cur.setItemQuantity(itemQuantity);
            items.moveToNext();
            completedList.add(cur);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //clicker for add a new fragment
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if(bought) {
                    bought = false;
                    for(ShowItemFragment currentFragment : completedList) {
                        fragmentTransaction.remove(currentFragment);
                    }
                    fragmentTransaction.commit();

                    for(int i = 0; i < fragmentList.size(); i++) {
                        fragmentTransaction = fragmentManager.beginTransaction();
                        ItemFragment currentFragment = fragmentList.get(i);
                        fragmentTransaction.add(R.id.fragmentContainer, currentFragment);
                        fragmentTransaction.commit();
                    }
                    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.addContainer);
                    linearLayout.setVisibility(View.VISIBLE);

                }
                else {
                    Bundle extras = new Bundle();
                    extras.putString(Values.ITEM_COLUMN_EMAIL, email);
                    AddFragment fragment = new AddFragment();
                    fragment.setArguments(extras);
                    fragmentTransaction.add(R.id.addContainer, fragment);
                    fragmentTransaction.commit();
                }
            }
        });

        //clicker for add all items into database
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bought) {
                    Log.d("bought", "bought");
                    return;
                }
                bought = true;
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                for (ItemFragment fragment : fragmentList) {
                    fragmentTransaction.remove(fragment);
                }
                fragmentTransaction.commit();
                for (int i = 0; i < completedList.size(); i++) {
                    fragmentTransaction = fragmentManager.beginTransaction();
                    ShowItemFragment showItemFragment = completedList.get(i);
                    fragmentTransaction.add(R.id.fragmentContainer, showItemFragment);
                    fragmentTransaction.commit();
                }
                LinearLayout addLayout = (LinearLayout) findViewById(R.id.addContainer);
                addLayout.setVisibility(View.INVISIBLE);
//                Intent i = new Intent(Second.this, Completed.class);
//                Second.this.startActivity(i);

            }
        });

        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    Intent i = new Intent(Second.this, MainActivity.class);
                    Second.this.startActivity(i);
                }
            }
        };

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


    }

    public List<ItemFragment> getFragmentList() {
        return this.fragmentList;
    }

    public List<ShowItemFragment> getCompletedList() {
        return this.completedList;
    }

}
