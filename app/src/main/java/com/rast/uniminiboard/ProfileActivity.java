package com.rast.uniminiboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {


    Button buttonLogOut;
    TextView textViewName, textViewMail;

    FirebaseUser nuser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        buttonLogOut = findViewById(R.id.button3);
        textViewName = findViewById(R.id.ProfileName);
        textViewMail = findViewById(R.id.ProfileMail);


        textViewMail.setText("Email: " + nuser.getEmail());
        textViewName.setText("Name: " + nuser.getDisplayName());

        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent s = new Intent(ProfileActivity.this, LogInActivity.class);
                startActivity(s);
            }
        });
    }
}
