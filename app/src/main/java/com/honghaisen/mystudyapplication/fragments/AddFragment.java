package com.honghaisen.mystudyapplication.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.honghaisen.mystudyapplication.DBHelper;
import com.honghaisen.mystudyapplication.ItemFragment;
import com.honghaisen.mystudyapplication.R;
import com.honghaisen.mystudyapplication.Second;


public class AddFragment extends Fragment {

    private EditText itemName;
    private EditText quantity;
    private Button addBtn;
    private DBHelper db;
    private Bundle extras;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        itemName = (EditText) getView().findViewById(R.id.itemName);
        quantity = (EditText) getView().findViewById(R.id.quantity);
        addBtn = (Button) getView().findViewById(R.id.addBtn);
        db = new DBHelper(getActivity());
        extras = getArguments();
    }

    @Override
    public void onStart() {
        super.onStart();
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = itemName.getText().toString();
                String num = quantity.getText().toString();
                String email = extras.getString("email");
                if(item == null || item.length() == 0) {
                    Toast.makeText(getActivity(), "please enter item's name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(num == null || num.length() == 0) {
                    Toast.makeText(getActivity(), "please enter the number of item", Toast.LENGTH_SHORT).show();
                    return;
                }
                db.insertItem(email, item, Integer.parseInt(num), false);
                ItemFragment itemFragment = new ItemFragment();
                itemFragment.setCurrentUser(email);
                itemFragment.setItemQuantity(Integer.parseInt(num));
                itemFragment.setItemName(item);
                ((Second) getActivity()).getFragmentList().add(itemFragment);
                getActivity().getFragmentManager().beginTransaction().remove(AddFragment.this).add(R.id.fragmentContainer, itemFragment).commit();
            }
        });
    }
}
