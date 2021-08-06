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
import com.example.transportapp.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    EditText editTextUsername;
    EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextConfirmPassword;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextUsername = findViewById(R.id.etUsername);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextConfirmPassword = findViewById(R.id.confirm_password);
        firebaseAuth=FirebaseAuth.getInstance();
        Button buttonRegister = findViewById(R.id.btnRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        firestore=FirebaseFirestore.getInstance();

    }

    public void register() {
        if (validateFields()) {
            String username = editTextUsername.getText().toString();
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();
            User user=new User(username,email,password);
            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            HashMap<String,Object> userDocument=new HashMap<>();
                            userDocument.put("Username",username);
                            userDocument.put("Email",email);
                            userDocument.put("Password",password);
                            userDocument.put("Experiences",user.getExperienceList());

                            firestore.collection("users").document(firebaseAuth.getCurrentUser().getUid())
                                    .set(userDocument).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(Register.this,"Inregistrarea  a fost realizata cu succes",Toast.LENGTH_LONG).show();
                                    Intent intentLogin=new Intent(Register.this,Login.class);
                                    startActivity(intentLogin);
                                }
                            });
                        }
                        else {
                            Toast.makeText(Register.this,"Inregistrarea nu a fost realizata cu succes"+task.getException().toString(),Toast.LENGTH_LONG).show();

                        }
                }
            });
        }
    }

    public boolean validateFields() {

        if (TextUtils.isEmpty(editTextUsername.getText().toString().trim())) {
            editTextUsername.setError("Please enter your username");
            return false;
        }
        if (TextUtils.isEmpty(editTextEmail.getText().toString().trim())) {
            editTextEmail.setError("Please enter your email");
            return false;
        }
        if (TextUtils.isEmpty(editTextPassword.getText().toString().trim())) {
            editTextEmail.setError("Please enter your password");
            return false;
        }
        if (!editTextPassword.getText().toString().trim().equals(editTextConfirmPassword.getText().toString().trim())) {
            editTextConfirmPassword.setError("The password and confirmation password do not match.");
            return false;
        }
        return true;
    }
}