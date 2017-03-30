package com.spp.keispp.sqllibv2;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import static android.content.ContentValues.TAG;


public class FragLogin extends Fragment {

    private Button
            btn_Register, btn_Login;

    private EditText
            txt_Username, txt_Password;

    //for check login
    int eyedi;
    String username, pass, pin;
    protected boolean checker;

    //for sharedPrefs
    public static final String _userDataPrefs = "userDataPrefs";
    public static final String _userIDPrefs = "userIDPrefs";
    public static final String _usernamePrefs = "usernamePrefs";
    public static final String _passwordPrefs = "passwordPrefs";
    public static final String _userPINPrefs = "userPINPrefs";

    SharedPreferences sharedPreferences;

    //for checking, if there's no null
    int _checkUserIDPrefs;
    String _checkUsernamePrefs,
            _checkPasswordPrefs, _checkPINPrefs;

    protected void init(View view) {
        btn_Register = (Button) view.findViewById(R.id.btn_login_Register);
        btn_Login = (Button) view.findViewById(R.id.btn_login_Login);

        txt_Username = (EditText) view.findViewById(R.id.txt_login_Username);
        txt_Password = (EditText) view.findViewById(R.id.txt_login_Password);

        sharedPreferences = getContext().getSharedPreferences(_userDataPrefs, Context.MODE_PRIVATE);

        _checkUserIDPrefs = sharedPreferences.getInt(_userIDPrefs, 0);
        _checkUsernamePrefs = sharedPreferences.getString(_usernamePrefs, "");
        _checkPasswordPrefs = sharedPreferences.getString(_passwordPrefs, "");
        _checkPINPrefs = sharedPreferences.getString(_userPINPrefs, "");

        //checking the remnants of checkuserdata
        Log.d(TAG, "init: check userDataPrefs: \n" +
                "ID: " + _checkUserIDPrefs +
        "\nUsername: " + _checkUsernamePrefs +
        "\nPassword: " + _checkPasswordPrefs +
        "\nPIN: " + _checkPINPrefs);

        if (_checkUserIDPrefs != 0 || !_checkUsernamePrefs.matches("")
                || !_checkPasswordPrefs.matches("") || !_checkPINPrefs.matches("")) {
            Intent intent = new Intent(getActivity().getBaseContext(), MainMenuActivity.class);
            startActivity(intent);
            Toast.makeText(getActivity().getBaseContext(), "Welcome back: " + _checkUsernamePrefs, Toast.LENGTH_SHORT).show();

        }

    }

    public FragLogin() {
        // Required empty public constructor
    }

    protected void checkLogin() {

        DatabaseHelper dbHelper = new DatabaseHelper(getActivity().getBaseContext());

        List<ModelUser> users = dbHelper.getAllModelUser();

        for (ModelUser user : users) {
            eyedi = user.get_id();
            username = user.get_User();
            pass = user.get_Pass();
            pin = user.get_PIN();

            //check repeating
            Log.d(TAG, "checkLogin: check things\n" +
                    "username: " + username +
                    "\npassword: " + pass);

            //let's try inputting a boolean
            if (!txt_Username.getText().toString().trim().matches(username) || !txt_Password.getText().toString().trim().matches(pass)) {
                checker = false;
                Log.d(TAG, "checkLogin: " + checker); //only indicator for me in kucing log
            } else {
                checker = true;
                Log.d(TAG, "checkLogin: " + checker); //indicator for me in kucing log
                break;
            }
            //conclusion: no matter if the username and password is correct, this one is still searching for all data in SQLite
            //need someway to stop after the username and password is correct
            /**
             * SOLVED! - Use "break;" if the condition is true
             */
        }

        // how to bring the checker here
        /**
         * SOLVED! - bring the checker outside from this method
         */
        if (!checker) {
            Toast.makeText(getActivity().getBaseContext(), "Something is wrong", Toast.LENGTH_SHORT).show();

            txt_Username.setText("");
            txt_Password.setText("");

            txt_Username.setFocusable(true);
            txt_Password.setFocusable(true);
        } else {
            Intent intent = new Intent(getActivity().getBaseContext(), MainMenuActivity.class);

            //put shared intent here

            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.clear();
            editor.putInt(_userIDPrefs, eyedi);
            editor.putString(_usernamePrefs, username);
            editor.putString(_passwordPrefs, pass);
            editor.putString(_userPINPrefs, pin);
            editor.apply();

            //putting this for testing only
            String log = "ID: " + eyedi +
                    "\nUsername: " + username +
                    "\nPassword: " + pass +
                    "\nPIN: " + pin;
            Log.d(TAG, "checkLogin: putting arguments\n" + log);

            Toast.makeText(getActivity().getBaseContext(), "Logging: " + txt_Username.getText().toString().trim(), Toast.LENGTH_SHORT).show();

            dbHelper.close();
            startActivity(intent);
            getActivity().finish();
        }
    }

    protected void configButton() {

        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getBaseContext(), MainRegister.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_login, container, false);

        init(view);
        configButton();

        return view;
    }

}
