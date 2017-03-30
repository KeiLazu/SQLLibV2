package com.spp.keispp.sqllibv2;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    //for sharedPrefs keyname
    public static final String _userDataPrefs = "userDataPrefs";
    public static final String _userIDPrefs = "userIDPrefs";
    public static final String _usernamePrefs = "usernamePrefs";
    public static final String _passwordPrefs = "passwordPrefs";
    public static final String _userPINPrefs = "userPINPrefs";

    SharedPreferences sharedPreferences;

    //taking data from sharedPrefs
    int _userID;
    String
    _userUsername, _userPassword, _userPIN;

    protected void init(View view) {
        lbl_id = (TextView) view.findViewById(R.id.lbl_menuHome_ID);
        lbl_username = (TextView) view.findViewById(R.id.lbl_menuHome_Username);
        lbl_password = (TextView) view.findViewById(R.id.lbl_menuHome_Password);
        lbl_PIN = (TextView) view.findViewById(R.id.lbl_menuHome_PIN);

        sharedPreferences = getContext().getSharedPreferences(_userDataPrefs, Context.MODE_PRIVATE);
        getDataPrefs();
    }

    //display sharedPrefs here
    public void getDataPrefs() {
        _userID = sharedPreferences.getInt(_userIDPrefs, 0);
        _userUsername = sharedPreferences.getString(_usernamePrefs, "");
        _userPassword = sharedPreferences.getString(_passwordPrefs, "");
        _userPIN = sharedPreferences.getString(_userPINPrefs, "");


        String log = "ID: " + _userID +
                "\nUsername: " + _userUsername +
                "\nPassword: " + _userPassword +
                "\nPIN: " + _userPIN;
        Log.d(TAG, "getIntentData: putting sharedprefs\n" + log);

    }

    protected void showInlabel() {
        lbl_id.setText(String.valueOf(_userID));
        lbl_username.setText(_userUsername);
        lbl_password.setText(_userPassword);
        lbl_PIN.setText(_userPIN);
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
        showInlabel();

        return view;
    }

}
