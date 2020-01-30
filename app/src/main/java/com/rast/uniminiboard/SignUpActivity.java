package com.rast.uniminiboard;

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

public class SignUpActivity extends AppCompatActivity {

    EditText emailId, passwordId;
    Button btnSignUp;
    TextView tvLogIn;
    FirebaseAuth myFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        myFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.userEmail);
        passwordId = findViewById(R.id.password);
        btnSignUp = findViewById(R.id.LogIn);
        tvLogIn = findViewById(R.id.tvLogIn);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailId.getText().toString();
                String password = passwordId.getText().toString();
                if(email.isEmpty()) {
                    emailId.setError("Bitte geben Sie eine E-mail-Adresse ein");
                    emailId.requestFocus();
                }
                else if(password.isEmpty()) {
                    passwordId.setError("Bitte geben Sie ein Passwort ein");
                    passwordId.requestFocus();
                }
                else if(email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Felder sind leer!", Toast.LENGTH_SHORT).show();
                }
                else if(!(email.isEmpty() && password.isEmpty())) {
                    myFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(! task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, "Anmeldefehler, Bitte versuchen Sie es erneut", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                startActivity(new Intent(SignUpActivity.this, InfoActivity.class));
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(SignUpActivity.this, "Ein Fehler ist aufgetreten!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUpActivity.this, LogInActivity.class);
                startActivity(i);
            }
        });

    }
}
