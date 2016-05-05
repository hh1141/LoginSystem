package com.honghaisen.mystudyapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Add extends AppCompatActivity {

    private Button addBtn;
    private EditText itemName;
    private EditText quantity;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //initialize widgets and database
        addBtn = (Button) findViewById(R.id.addBtn);
        itemName = (EditText) findViewById(R.id.itemName);
        quantity = (EditText) findViewById(R.id.quantity);
        db = new DBHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        final Bundle extras = getIntent().getExtras();

        //clicker for add item
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = itemName.getText().toString();
                String num = quantity.getText().toString();
                String email = extras.getString("email");
                db.insertItem(email, item, Integer.parseInt(num), false);
                Intent i = new Intent(Add.this, Second.class);
                Add.this.startActivity(i);
            }
        });
    }
}
