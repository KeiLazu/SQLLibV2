package com.spp.keispp.sqllibv2;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragMenuHome extends Fragment {

    //objects for fragmenuhome
    TextView
            lbl_id, lbl_username, lbl_password, lbl_PIN;

    //basic variable for taking extra (experimental)
    int _id;
    String _username, _password, _PIN;

    protected void init(View view) {
        lbl_id = (TextView) view.findViewById(R.id.lbl_menuHome_ID);
        lbl_username = (TextView) view.findViewById(R.id.lbl_menuHome_Username);
        lbl_password = (TextView) view.findViewById(R.id.lbl_menuHome_Password);
        lbl_PIN = (TextView) view.findViewById(R.id.lbl_menuHome_PIN);
    }

    protected void getIntentData() {
        Intent intent = new Intent(getActivity().getBaseContext(), FragLogin.class);
        _id = intent.getIntExtra("id",0);
        _username = intent.getStringExtra("username");
        _password = intent.getStringExtra("password");
        _PIN = intent.getStringExtra("PIN");

        lbl_id.setText("ID: \n" + _id);
        lbl_username.setText("Username: \n" + _username);
        lbl_password.setText("Password: \n" + _password);
        lbl_PIN.setText("PIN: \n" + _PIN);

        String log = "ID: " + _id +
                "\nUsername: " + _username +
                "\nPassword: " + _password +
                "\nPIN: " + _PIN;
        Log.d(TAG, "getIntentData: putting IntentData\n" + log);

    }


    public FragMenuHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_menu_home, container, false);

        init(view);
        getIntentData();

        return view;
    }

}
