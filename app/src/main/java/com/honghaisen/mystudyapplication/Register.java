package com.honghaisen.mystudyapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    private Button regBtn;
    private EditText email;
    private EditText password;
    private EditText name;
    private EditText phone;
    private DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //instantiate widgets
        regBtn = (Button)findViewById(R.id.regBtn);
        email = (EditText)findViewById(R.id.regEmail);
        password = (EditText)findViewById(R.id.regPass);
        name = (EditText)findViewById(R.id.regName);
        phone = (EditText)findViewById(R.id.regPhone);
        db = new DBHelper(this);

        //click to do validation and database insertion operation
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().equals("")) {
                    Toast.makeText(Register.this, "please enter your email address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.getText().toString().equals("")) {
                    Toast.makeText(Register.this, "please enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }
                Pattern phonePattern = null;
                Matcher phoneMatcher = null;
                if(!phone.getText().toString().equals("")) {
                    phonePattern = Pattern.compile(Values.phonePattern);
                    phoneMatcher = phonePattern.matcher(phone.getText());
                }
                Log.d("register", phone.getText().toString());
                Pattern emailPattern = Pattern.compile(Values.emailPattern);
                Matcher emailMatcher = emailPattern.matcher(email.getText());
                //if phone and email matches, insert data into database
                if(((phoneMatcher != null && phoneMatcher.matches()) || phoneMatcher == null) && emailMatcher.matches()) {
                    boolean successInsert = db.insert(email.getText().toString(), password.getText().toString(), name.getText().toString(), phone.getText().toString());
                    if(successInsert) {
                        Intent i = new Intent(Register.this, MainActivity.class);
                        Register.this.startActivity(i);
                    }
                    else {
                        Toast.makeText(Register.this, "email address duplication", Toast.LENGTH_SHORT).show();
                    }
                }
                //if email address's format is invalid
                else if(!emailMatcher.matches()) {
                    Toast.makeText(Register.this, "invalid email address", Toast.LENGTH_SHORT).show();
                    Log.d("register", "invalid email");
                }
                //if phone number's format is invalid
                else if(phoneMatcher != null && !phoneMatcher.matches()) {
                    Toast.makeText(Register.this, "invalid phone number", Toast.LENGTH_SHORT).show();
                    Log.d("register", "invalid phone number");
                }
            }
        });

        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length() == 3 || s.toString().length() == 7) {
                    s.append('-');
                }

            }
        });
    }
}
