package com.honghaisen.mystudyapplication;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class Completed extends AppCompatActivity {

    private DBHelper db;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed);

        db = new DBHelper(this);
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Cursor items = db.getAllCompletedItems();
        while(!items.isAfterLast()) {
            String itemName = items.getString(items.getColumnIndex(Values.ITEM_COLUMN_ITEM_NAME));
            String itemQuantity = items.getString(items.getColumnIndex(Values.ITEM_COLUMN_QUANTITY));
            ShowItemFragment cur = new ShowItemFragment();
            cur.setItemName(itemName);
            cur.setItemQuantity(itemQuantity);
            fragmentTransaction.add(R.id.completedItem, cur);
            items.moveToNext();
        }
        fragmentTransaction.commit();
    }
}
