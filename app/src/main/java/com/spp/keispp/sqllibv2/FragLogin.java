package com.spp.keispp.sqllibv2;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class FragLogin extends Fragment {

    Button
    btn_Register;

    protected void init(View view) {
        btn_Register = (Button)view.findViewById(R.id.btn_login_Register);
    }

    public FragLogin() {
        // Required empty public constructor
    }

    protected void configButton(){

        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getBaseContext(), MainRegister.class);
                startActivity(intent);
                getActivity().finish();
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
