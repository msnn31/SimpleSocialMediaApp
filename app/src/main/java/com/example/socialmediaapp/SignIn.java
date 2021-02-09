package com.example.socialmediaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignIn extends AppCompatActivity {

    TextView registerTxtView;
    EditText SigninEmailAddress, SigninPassword;
    Button signInBtn;
    DatabaseReference databaseReference;
    FirebaseUser user;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        registerTxtView = findViewById(R.id.registerTxtView);
        SigninEmailAddress = findViewById(R.id.SigninEmailAddress);
        SigninPassword = findViewById(R.id.SigninPassword);
        signInBtn = findViewById(R.id.signInBtn);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("socialmedia");
        user = mAuth.getCurrentUser();

        login();

        signup();
    }

    private void login() {
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String signin_email = SigninEmailAddress.getText().toString();
                String signin_password = SigninPassword.getText().toString();

                mAuth.signInWithEmailAndPassword(signin_email, signin_password)
                        .addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if ((task.isSuccessful())) {
                                    Toast.makeText(SignIn.this, "Sign In successful.", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignIn.this, Home.class));
                                } else {
                                    Toast.makeText(SignIn.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private void signup() {
        registerTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this, Register.class));
            }
        });
    }
}