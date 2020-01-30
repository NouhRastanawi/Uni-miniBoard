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
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {

    public void goToSignUp(View v) {
        Intent intSignUp = new Intent(LogInActivity.this, SignUpActivity.class);
        startActivity(intSignUp);
    }

    EditText emailId, passwordId;
    Button btnLogIn;
    TextView tvLogIn;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.userEmail);
        passwordId = findViewById(R.id.password);
        btnLogIn = findViewById(R.id.LogIn);
        tvLogIn = findViewById(R.id.tvLogIn);
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            FirebaseUser mfirebaseUser = mFirebaseAuth.getCurrentUser();
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(mfirebaseUser != null) {
                    Toast.makeText(LogInActivity.this, "Sie sind eingeloggt", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LogInActivity.this, BoardActivity.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(LogInActivity.this, "Bitte loggen Sie sich ein", Toast.LENGTH_SHORT).show();
                }

            }
        };
        btnLogIn.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(LogInActivity.this, "Felder sind leer!", Toast.LENGTH_SHORT).show();
                }
                else if(!(email.isEmpty() && password.isEmpty())) {
                    mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(! task.isSuccessful()) {
                                Toast.makeText(LogInActivity.this, "Log-In Fehler, bitte melden Sie sich erneut an", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Intent intToBoard = new Intent(LogInActivity.this, BoardActivity.class);
                                startActivity(intToBoard);
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(LogInActivity.this, "Ein Fehler ist aufgetreten!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
