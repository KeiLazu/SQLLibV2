package com.spp.keispp.sqllibv2;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import static android.content.ContentValues.TAG;

import java.sql.Time;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected Button
            btn_ForgotPass, btn_GetPass;

    protected EditText
            txt_InputPIN;

    protected TextView
            lbl_ShowUsername, lbl_ShowPassword;

    //for toggle
    protected boolean _compVisible = true;

    //for delaying visibility
    protected Handler handler = new Handler();

    //for checking PIN
    protected boolean _checker;
    protected String _pin, _username, _password;

    protected void init() {
        btn_ForgotPass = (Button) findViewById(R.id.btn_login_ForgetPass);
        btn_GetPass = (Button) findViewById(R.id.btn_forgot_ForgotPassword);

        txt_InputPIN = (EditText) findViewById(R.id.txt_forgot_PIN);
        lbl_ShowUsername = (TextView) findViewById(R.id.lbl_forgot_Username);
        lbl_ShowPassword = (TextView) findViewById(R.id.lbl_forgot_Password);
    }

    protected void animateFadeInPIN() {

        txt_InputPIN.setVisibility(View.VISIBLE);
        btn_GetPass.setVisibility(View.VISIBLE);

        YoYo.with(Techniques.FadeIn)
                .duration(700)
                .repeat(1)
                .playOn(txt_InputPIN);

        YoYo.with(Techniques.FadeIn)
                .duration(700)
                .repeat(1)
                .playOn(btn_GetPass);

    }

    protected void animateFadeOutPIN() {
        YoYo.with(Techniques.FadeOut)
                .duration(700)
                .repeat(1)
                .playOn(txt_InputPIN);

        YoYo.with(Techniques.FadeOut)
                .duration(700)
                .repeat(1)
                .playOn(btn_GetPass);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                txt_InputPIN.setVisibility(View.INVISIBLE);
                btn_GetPass.setVisibility(View.INVISIBLE);
            }
        }, 700);

    }

    protected void animateFadeInPass(String usernyaa, String passnyaa) {

        lbl_ShowUsername.setText("Username: " + usernyaa);
        lbl_ShowPassword.setText("Password: " + passnyaa);

        lbl_ShowUsername.setVisibility(View.VISIBLE);
        lbl_ShowPassword.setVisibility(View.VISIBLE);

        YoYo.with(Techniques.FadeIn)
                .duration(700)
                .repeat(1)
                .playOn(lbl_ShowUsername);

        YoYo.with(Techniques.FadeIn)
                .duration(700)
                .repeat(1)
                .playOn(lbl_ShowPassword);
    }

    protected void animateFadeOutPass() {
        YoYo.with(Techniques.FadeOut)
                .duration(700)
                .repeat(1)
                .playOn(lbl_ShowUsername);

        YoYo.with(Techniques.FadeOut)
                .duration(700)
                .repeat(1)
                .playOn(lbl_ShowPassword);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                lbl_ShowUsername.setVisibility(View.GONE);
                lbl_ShowPassword.setVisibility(View.GONE);
            }
        }, 700);
    }

    protected void checkPIN() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        List<ModelUser> modelUserList = dbHelper.getAllModelUser();

        for (ModelUser mu : modelUserList) {
            _pin = mu.get_PIN();
            _username = mu.get_User();
            _password = mu.get_Pass();

            //check PIN in kucing-log
            // *pengennya nulis kucing-poi tapi ya sudahlah lol
            // **if you don't understand that language please don't search it, thank you
            Log.d(TAG, "checkPIN: checking . . ." +
                    "\nPIN: " + _pin +
                    "\nUsername: " + _username +
                    "\nPassword: " + _password);

            //check if txt_pin matched the data in SQL
            if (!txt_InputPIN.getText().toString().matches(_pin)) {
                _checker = false;
                Log.d(TAG, "checkPIN: false"); //only an indicator for me in kucing log

            } else {
                _checker = true;
                Log.d(TAG, "checkPIN: true"); //indicator for me in kucing log
                break; //stop the loop loop loop loop loop loop loop loop loop loop loop

            }

        }

        if (!_checker) {
            Toast.makeText(this, "Wrong PIN", Toast.LENGTH_SHORT).show();

            txt_InputPIN.setFocusable(true);
            txt_InputPIN.setText("");

        } else {
            animateFadeOutPIN();
            animateFadeInPass(_username, _password);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    animateFadeOutPass();
                }
            }, 5000);
        }

        dbHelper.close();

    }

    protected void configButton() {
        btn_ForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (_compVisible) {
                    animateFadeInPIN();
                    _compVisible = false;
                } else {
                    animateFadeOutPIN();
                    _compVisible = true;
                }
            }
        });

        btn_GetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPIN();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        configButton();
    }
}
