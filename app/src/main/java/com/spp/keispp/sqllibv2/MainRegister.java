package com.spp.keispp.sqllibv2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import static android.content.ContentValues.TAG;

public class MainRegister extends AppCompatActivity {

    Button
            btn_Cancel, btn_Register;

    EditText
            txt_NewUsername, txt_newPassword, txt_ConfirmPassword, txt_NewPIN, txt_ConfirmPIN;

    public void init() {
        btn_Cancel = (Button) findViewById(R.id.btn_register_Cancel);
        btn_Register = (Button) findViewById(R.id.btn_register_Register);

        txt_NewUsername = (EditText) findViewById(R.id.txt_register_NewUsername);
        txt_newPassword = (EditText) findViewById(R.id.txt_register_NewPassword);
        txt_ConfirmPassword = (EditText) findViewById(R.id.txt_register_ConfirmPassword);
        txt_NewPIN = (EditText) findViewById(R.id.txt_register_NewPIN);
        txt_ConfirmPIN = (EditText) findViewById(R.id.txt_register_ConfirmPIN);

    }

    public void RegisterUser() {

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        /**
         * CHECKING if something is empty
         */

        if (txt_NewUsername.getText().toString().trim().isEmpty() ||
                txt_newPassword.getText().toString().trim().isEmpty() ||
                txt_ConfirmPassword.getText().toString().trim().isEmpty() ||
                txt_NewPIN.getText().toString().trim().isEmpty() ||
                txt_ConfirmPIN.getText().toString().trim().isEmpty()) {

            if (txt_NewUsername.getText().toString().trim().isEmpty()) {
                txt_NewUsername.setFocusable(true);
                Toast.makeText(this, "Enter a Username", Toast.LENGTH_SHORT).show();

            } else if (txt_newPassword.getText().toString().trim().isEmpty() || txt_ConfirmPassword.getText().toString().trim().isEmpty()) {
                if (txt_ConfirmPassword.getText().toString().trim().isEmpty()) {
                    txt_ConfirmPassword.setFocusableInTouchMode(true);
                    txt_ConfirmPassword.setFocusable(true);

                } else {
                    txt_newPassword.setFocusableInTouchMode(true);
                    txt_newPassword.setFocusable(true);

                }

                Toast.makeText(this, "Enter a Password", Toast.LENGTH_SHORT).show();

            } else if (txt_NewPIN.getText().toString().trim().isEmpty() || txt_ConfirmPIN.getText().toString().trim().isEmpty()) {
                if (txt_NewPIN.getText().toString().trim().isEmpty()) {
                    txt_NewPIN.setFocusable(true);

                } else {
                    txt_ConfirmPIN.setFocusable(true);

                }

                Toast.makeText(this, "Enter a PIN", Toast.LENGTH_SHORT).show();

            }

        } else if (!txt_newPassword.getText().toString().trim().matches(txt_ConfirmPassword.getText().toString().trim())) {
            txt_newPassword.setText("");
            txt_ConfirmPassword.setText("");

            txt_newPassword.setFocusable(true);
            txt_ConfirmPassword.setFocusable(true);

            Toast.makeText(this, "Password doesn't matched", Toast.LENGTH_SHORT).show();

        } else if (!txt_NewPIN.getText().toString().trim().matches(txt_ConfirmPIN.getText().toString().trim())) {
            txt_NewPIN.setText("");
            txt_ConfirmPIN.setText("");

            txt_NewPIN.setFocusable(true);
            txt_ConfirmPIN.setFocusable(true);

            Toast.makeText(this, "PIN doesn't matched", Toast.LENGTH_SHORT).show();

        } else {

            dbHelper.addUser(new ModelUser(txt_NewUsername.getText().toString().trim(),
                    txt_newPassword.getText().toString().trim(),
                    txt_NewPIN.getText().toString().trim()));

            //read all contacts
            Log.d(TAG, "RegisterUser: reading . . .");
            List<ModelUser> users = dbHelper.getAllModelUser();

            for (ModelUser mu : users) {
                String log = "id: " + mu.get_id()+ " ,Name: " +
                        mu.get_User() + " ,Password: " +
                        mu.get_Pass() + " ,PIN: " +
                        mu.get_PIN();
                Log.d(TAG, "RegisterUser: log \n" + log);
            }

            Toast.makeText(this, "Welcome, Registered User: " + txt_NewUsername.getText().toString().trim(), Toast.LENGTH_SHORT).show();

            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("userData", 0);
            SharedPreferences.Editor editorPrefs = sharedPref.edit();

            editorPrefs.putString("username", txt_NewUsername.getText().toString().trim()); //store username here
            editorPrefs.putString("password", txt_ConfirmPassword.getText().toString().trim()); //store password here
            editorPrefs.apply();

            //show in kucing-log for looking at the prefs
            Log.d(TAG, "RegisterUser: editorprefs talking \n" +
                    "Username: " + sharedPref.getString("username", null) + "\n" +
                    "Password: " + sharedPref.getString("password", null));

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("firstRegister", 1);
            startActivity(intent);
            finish();

        }

    }

    public void configButton() {
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainRegister.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterUser();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_register);

        init();
        configButton();

    }

}
