package com.rast.uniminiboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class InfoActivity extends AppCompatActivity {

    EditText txtname, txtfaculty;
    Button fertig;
    User user;
    FirebaseUser nuser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    FirebaseAuth mAuth;
    public static final String UserName = "UserName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        txtname = findViewById(R.id.txtname);
        txtfaculty = findViewById(R.id.txtfaculty);
        fertig = findViewById(R.id.button);
        mAuth = FirebaseAuth.getInstance();
        myRef = database.getInstance().getReference("User");
        fertig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(InfoActivity.this, "Warten Sie ein bisschen", Toast.LENGTH_SHORT).show();
                String name = txtname.getText().toString();
                String faculty = txtfaculty.getText().toString();
                if(!TextUtils.isEmpty(name)) {
                    String email = nuser.getEmail();
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                    nuser.updateProfile(profileUpdates);

                    String id = myRef.push().getKey();
                    User user = new User(id, name, email, faculty);
                    myRef.child(id).setValue(user);
                    Toast.makeText(InfoActivity.this, "Daten erfolgreich eingefügt", Toast.LENGTH_SHORT).show();
                    Intent Int = new Intent(InfoActivity.this, BoardActivity.class);
                    startActivity(Int);
                }
                else {
                    Toast.makeText(InfoActivity.this, "Name sollte nicht leer sein", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
