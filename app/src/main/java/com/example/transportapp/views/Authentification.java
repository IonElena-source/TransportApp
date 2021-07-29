package com.example.transportapp.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.transportapp.R;
import com.example.transportapp.authentification.Login;
import com.example.transportapp.authentification.Register;

public class Authentification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);
        Button buttonLogin=findViewById(R.id.btnLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Authentification.this, Login.class);
                startActivity(intent);
            }
        });
        TextView textRegister=findViewById(R.id.txtRegister);
        textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Authentification.this, Register.class);
                startActivity(intent);
            }
        });
    }
}