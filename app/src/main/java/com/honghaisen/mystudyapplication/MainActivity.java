package com.honghaisen.mystudyapplication;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {


    Button register;
    Button login;
    EditText email;
    EditText password;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //instantiate widgets
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        db = new DBHelper(this);

        //clicker for login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = MainActivity.this.email.getText().toString();
                String password = MainActivity.this.password.getText().toString();
                if(db.matches(email, password)) {
                    Intent i = new Intent(MainActivity.this, Second.class);
                    MainActivity.this.startActivity(i);
                }
                else {
                    Toast.makeText(MainActivity.this, "Email or password not match", Toast.LENGTH_SHORT).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            //move to register activity
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Register.class);
                MainActivity.this.startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("onresume", "The onResume() event");
    }

}
