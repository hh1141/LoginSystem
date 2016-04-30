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
    private static final char SEPARATOR = '-';
    private static final int FIRST_SEPARATOR_POSITION = 3;
    private static final int SECOND_SEPARATOR_POSITION = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //instantiate widgets
        regBtn = (Button) findViewById(R.id.regBtn);
        email = (EditText) findViewById(R.id.regEmail);
        password = (EditText) findViewById(R.id.regPass);
        name = (EditText) findViewById(R.id.regName);
        phone = (EditText) findViewById(R.id.regPhone);
        db = new DBHelper(this);

        //click to do validation and database insertion operation
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If user didn't input anything in email text editor, r
                if (email.getText().toString().equals("")) {
                    Toast.makeText(Register.this, "please enter your email address", Toast.LENGTH_SHORT).show();
                    return;
                }
                //If user didn't input anything in password text editor
                if (password.getText().toString().equals("")) {
                    Toast.makeText(Register.this, "please enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }
                //if phone number is not in length of 12
                if(!phone.getText().toString().equals("") && phone.getText().toString().length() != 12) {
                    Toast.makeText(Register.this, "invalid phone format", Toast.LENGTH_SHORT).show();
                    return;
                }
                Pattern emailPattern = Pattern.compile(Values.emailPattern);
                Matcher emailMatcher = emailPattern.matcher(email.getText());
                //if email is in a validate format, insert data into database
                if (emailMatcher.matches()) {
                    boolean successInsert = db.insert(email.getText().toString(), password.getText().toString(), name.getText().toString(), phone.getText().toString());
                    if (successInsert) {
                        Intent i = new Intent(Register.this, MainActivity.class);
                        Register.this.startActivity(i);
                    } else {
                        Toast.makeText(Register.this, "email address duplication", Toast.LENGTH_SHORT).show();
                    }
                }
                //if email address's format is invalid
                else {
                    Toast.makeText(Register.this, "invalid email address", Toast.LENGTH_SHORT).show();
                    Log.d("register", "invalid email");
                }
            }
        });
        //auto formatter for phone number
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Editable phoneNumberEditable = phone.getEditableText();
                Log.d("before", String.valueOf(before));
                Log.d("count", String.valueOf(count));
                if (before == 1) {
                    if (start == FIRST_SEPARATOR_POSITION || start == SECOND_SEPARATOR_POSITION) {
                        return;
                    }
                }

                switch (parsePhoneNumber(phoneNumberEditable.toString())) {
                    //Not formatted cause one index at least is a separator, except index 3 and index 7, case 1
                    case 1:
                        int oneInvalidSeparatorIndex = getOneInvalidSeparatorIndex(phoneNumberEditable.toString());
                        phoneNumberEditable.delete(oneInvalidSeparatorIndex, oneInvalidSeparatorIndex + 1);
                        break;
                    //When length equals to first separator index, add '-', case 2
                    case 2:
                        phoneNumberEditable.insert(FIRST_SEPARATOR_POSITION, String.valueOf(SEPARATOR));
                        break;
                    //When length equals to second separator index, add '-', case 3
                    case 3:
                        phoneNumberEditable.insert(SECOND_SEPARATOR_POSITION, String.valueOf(SEPARATOR));
                        break;
                    //When length is larger than 11, case 4
                    case 4:
                        phoneNumberEditable.delete(phoneNumberEditable.length() - 1, phoneNumberEditable.length());
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private int parsePhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return -1;
        }

        //Not formatted cause one index at least is a separator, except index 3 and index 7, case 1
        if (getOneInvalidSeparatorIndex(phoneNumber) != -1) {
            return 1;
        }

        //When length equals to first separator index, add '-', case 2
        if (phoneNumber.length() == FIRST_SEPARATOR_POSITION) {
            return 2;
        }

        //When length equals to second separator index, add '-', case 3
        if (phoneNumber.length() == SECOND_SEPARATOR_POSITION) {
            return 3;
        }

        //When length is larger than 11, case 4
        if (phoneNumber.length() > 12) {
            return 4;
        }

        return 0;
    }

    private int getOneInvalidSeparatorIndex(String phoneNumber) {
        if (phoneNumber == null) {
            return -1;
        }
        for (int i = 0; i < phoneNumber.length(); i++) {
            if (i == FIRST_SEPARATOR_POSITION || i == SECOND_SEPARATOR_POSITION) {
                continue;
            }
            if (phoneNumber.charAt(i) == SEPARATOR) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("onStart", "call onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("onResume", "call onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("onPause", "call onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("onStop", "call onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("onDestroy", "call onDestroy");
    }
}
