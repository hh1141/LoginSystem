package com.honghaisen.mystudyapplication;


import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowItemFragment extends Fragment {

    private TextView itemName;
    private TextView itemQuantity;
    private String item;
    private String num;

    public ShowItemFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_item, container, false);
        itemName = (TextView) view.findViewById(R.id.showItemName);
        itemQuantity = (TextView) view.findViewById(R.id.showItemQuantity);
        itemName.setText(item);
        itemQuantity.setText(num);
        return view;
    }

    public void setItemName(String item) {
        this.item = item;
    }

    public void setItemQuantity(String num) {
        this.num = num;
    }
}
