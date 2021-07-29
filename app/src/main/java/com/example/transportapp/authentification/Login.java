package com.example.transportapp.authentification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.transportapp.R;
import com.example.transportapp.views.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    EditText editTextEmail;
    EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button buttonLogin=findViewById(R.id.btnLogin);

        firebaseAuth=FirebaseAuth.getInstance();
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextEmail=findViewById(R.id.email);
                editTextPassword=findViewById(R.id.password);
                login();
            }
        });
    }
    public void login(){
        if(validateProfile()){
            firebaseAuth.signInWithEmailAndPassword(editTextEmail.getText().toString().trim(),
                    editTextPassword.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Login.this,"Logare reusita",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(Login.this,"Logare reusita",Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
    public boolean validateProfile(){
        if (TextUtils.isEmpty(editTextEmail.getText().toString())) {
            editTextEmail.setError("Please enter your email");
            return false;
        }
        if(TextUtils.isEmpty(editTextPassword.getText().toString())){
            editTextPassword.setError("Please enter your password");
            return false;
        }
        return true;
    }
}