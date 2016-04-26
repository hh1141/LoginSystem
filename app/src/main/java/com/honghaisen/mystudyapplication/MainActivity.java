package com.honghaisen.mystudyapplication;

import android.content.Intent;
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
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //instantiate widgets
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        et = (EditText) findViewById(R.id.email);

        //clicker for login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pattern for validation
                String pattern = Values.emailPattern;
                Pattern p = Pattern.compile(pattern);
                Matcher m = p.matcher(MainActivity.this.et.getText());

                //if pattern matches the valid email format, then login
                if (m.matches()) {
                    Intent i = new Intent(MainActivity.this, Second.class);
                    MainActivity.this.startActivity(i);
                }
                //if pattern not matched the valid email format, the show the error message
                else {
                    Toast.makeText(MainActivity.this, "invalid email address", Toast.LENGTH_SHORT).show();
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
