package com.spp.keispp.sqllibv2;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragMenuConfigUser extends Fragment {

    EditText
            txt_ChangeName, txt_ChangePassword, txt_ChangePIN;

    Button
            btn_ClearText, btn_UpdateUser, btn_DeleteAccount;

    DatabaseHelper dbHelper = new DatabaseHelper(getContext());

    //for sharedprefs
    public static final String _userDataPrefs = "userDataPrefs";
    public static final String _userIDPrefs = "userIDPrefs";
    public static final String _usernamePrefs = "usernamePrefs";
    public static final String _passwordPrefs = "passwordPrefs";
    public static final String _userPINPrefs = "userPINPrefs";

    SharedPreferences sharedPreferences;

    //get sharedprefs data
    int _userID;
    String _userUsername, _userPassword, _userPIN;

    protected void init(View view) {
        txt_ChangeName = (EditText) view.findViewById(R.id.txt_configuser_changeUsername);
        txt_ChangePassword = (EditText) view.findViewById(R.id.txt_configuser_changePassword);
        txt_ChangePIN = (EditText) view.findViewById(R.id.txt_configuser_changePIN);

        btn_UpdateUser = (Button) view.findViewById(R.id.btn_configuser_UpdateUser);
        btn_ClearText = (Button) view.findViewById(R.id.btn_configuser_ClearText);
        btn_DeleteAccount = (Button) view.findViewById(R.id.btn_configuser_DeleteThisUser);

        dbHelper = new DatabaseHelper(getActivity().getBaseContext());
        sharedPreferences = getContext().getSharedPreferences(_userDataPrefs, Context.MODE_PRIVATE);

    }

    protected void configButton() {
        btn_DeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //no confirmation, gonna go for confirmation later
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                List<ModelUser> users = dbHelper.getAllModelUser();
                for (ModelUser mu : users) {
                    _userID = mu.get_id();
                    _userUsername = mu.get_User();
                    _userPassword = mu.get_Pass();
                    _userPIN = mu.get_PIN();

                    String log = "id: " + mu.get_id()+ ",\nName: " +
                            mu.get_User() + ",\nPassword: " +
                            mu.get_Pass() + ",\nPIN: " +
                            mu.get_PIN();
                    Log.d(TAG, "Delete User: \n" + log);
                }

                dbHelper.deleteUser(new ModelUser(_userID, _userUsername, _userPassword, _userPIN));

                for (ModelUser mu : users) {
                    _userID = mu.get_id();
                    _userUsername = mu.get_User();
                    _userPassword = mu.get_Pass();
                    _userPIN = mu.get_PIN();
                }

                Intent intent = new Intent(getActivity().getBaseContext(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }


    public fragMenuConfigUser() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_menu_config_user, container, false);

        init(view);
        configButton();

        return view;
    }

}
