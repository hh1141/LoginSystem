package com.honghaisen.mystudyapplication;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MainActivity extends AppCompatActivity {


    private Button register;
    private Button login;
    private EditText email;
    private EditText password;
    private DBHelper db;
    private LoginButton fbBtn;
    private CallbackManager callbackManager;
    private AccessToken accessToken;
    private ProfileTracker profileTracker;
    private AccessTokenTracker accessTokenTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //FaceBook sdk initialization
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        setContentView(R.layout.activity_main);

        //instantiate widgets
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        db = new DBHelper(this);
        fbBtn = (LoginButton) findViewById(R.id.fbBtn);
        callbackManager = CallbackManager.Factory.create();

        fbBtn.setReadPermissions("public_profile");
        fbBtn.setReadPermissions("email");

        accessToken = AccessToken.getCurrentAccessToken();

        //start accessToken Tracker
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };
        accessTokenTracker.startTracking();

        //start profile token tracker
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                Intent i = new Intent(MainActivity.this, Second.class).putExtra("fb", true);
                if(currentProfile != null) {
                    i.putExtra(Values.USER_COLUMN_NAME, currentProfile.getName());
                    MainActivity.this.startActivity(i);
                }

            }
        };
        profileTracker.startTracking();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    @Override
    protected void onResume() {
        super.onResume();
        //clicker for login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (accessToken != null) {
                    Toast.makeText(MainActivity.this, "Login already", Toast.LENGTH_SHORT).show();
                    return;
                }
                String email = MainActivity.this.email.getText().toString();
                String password = MainActivity.this.password.getText().toString();
                if (db.matches(email, password)) {
                    Cursor temp = db.getData(email);
                    temp.moveToFirst();

                    String name = temp.getString(temp.getColumnIndex(Values.USER_COLUMN_NAME));
                    Intent i = new Intent(MainActivity.this, Second.class).putExtra(Values.USER_COLUMN_NAME, name).putExtra("fb", false);
                    MainActivity.this.startActivity(i);
                } else {
                    Toast.makeText(MainActivity.this, "Email or password not match", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //clicker for registration
        register.setOnClickListener(new View.OnClickListener() {
            //move to register activity
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Register.class);
                MainActivity.this.startActivity(i);
            }
        });

        //clicker for FB login
        fbBtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {


            }

            @Override
            public void onCancel() {
                Log.i("fbBtn", "cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("fbBtn", "error");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }
}
