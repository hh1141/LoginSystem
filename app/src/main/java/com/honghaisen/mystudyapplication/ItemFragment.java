package com.honghaisen.mystudyapplication;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.honghaisen.mystudyapplication.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ItemFragment extends Fragment {

    private Button remove;
    private Button ok;
    private EditText item;
    private EditText quantity;
    private DBHelper db;
    private String currentUser;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private List<Fragment> fragments;

    public ItemFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        remove = (Button) view.findViewById(R.id.remove);
        ok = (Button) view.findViewById(R.id.ok);
        item = (EditText) view.findViewById(R.id.itemName);
        quantity = (EditText) view.findViewById(R.id.quantity);
        db = new DBHelper(getActivity());
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragments = new ArrayList<Fragment>();

        return view;
    }

    public void setCurrentUser(String email) {
        this.currentUser = email;
    }

    public void setItemName(String name) {
        this.item.setText(name);
    }

    public void setItemQuantity(int num) {
        this.quantity.setText(String.valueOf(num));
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

//        clicker for remove fragment
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.remove(ItemFragment.this);
                fragmentTransaction.commit();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.insertItem(currentUser, item.getText().toString(), Integer.parseInt(quantity.getText().toString()), true);
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
}
