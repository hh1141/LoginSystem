package com.honghaisen.mystudyapplication;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.honghaisen.mystudyapplication.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;


public class ItemFragment extends Fragment {

    private Button remove;
    private Button ok;
    private TextView item;
    private TextView quantity;
    private DBHelper db;
    private String currentUser;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private int num;
    private String itemName;
    private int id;

    public ItemFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        remove = (Button) view.findViewById(R.id.remove);
        ok = (Button) view.findViewById(R.id.ok);
        item = (TextView) view.findViewById(R.id.showItemName);
        quantity = (TextView) view.findViewById(R.id.showItemQuantity);
        db = new DBHelper(getActivity());
        fragmentManager = getFragmentManager();

        return view;
    }

    public void setCurrentUser(String email) {
        this.currentUser = email;
    }

    public void setItemName(String name) {
        itemName = name;
    }

    public void setItemQuantity(int num) {
        this.num = num;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        //clicker for remove fragment
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteItem(id);
                ((Second) getActivity()).getFragmentList().remove(ItemFragment.this);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(ItemFragment.this);
                fragmentTransaction.commit();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.updateItem(id, true);
                ((Second) getActivity()).getFragmentList().remove(ItemFragment.this);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(ItemFragment.this);
                fragmentTransaction.commit();
                ShowItemFragment showItemFragment = new ShowItemFragment();
                showItemFragment.setItemName(itemName);
                showItemFragment.setItemQuantity(String.valueOf(num));
                ((Second) getActivity()).getCompletedList().add(showItemFragment);
            }
        });

        item.setText(itemName);
        quantity.setText(String.valueOf(num));
    }

}
