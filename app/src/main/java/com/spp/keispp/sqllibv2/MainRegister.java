package com.spp.keispp.sqllibv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainRegister extends AppCompatActivity {

    Button
    btn_Cancel;

    public void init() {
        btn_Cancel = (Button)findViewById(R.id.btn_register_Cancel);
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_register);

        init();
        configButton();

    }

}
