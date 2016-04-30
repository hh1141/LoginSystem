package com.honghaisen.mystudyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class Second extends AppCompatActivity {

    private TextView greeting;
    private LoginButton fbBtn;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        greeting = (TextView) findViewById(R.id.hello);
        fbBtn = (LoginButton) findViewById(R.id.fbBtn);
        callbackManager = CallbackManager.Factory.create();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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
    }

    @Override
    protected void onResume() {
        super.onResume();

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
