package com.rast.uniminiboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.EventListener;

public class PostActivity extends AppCompatActivity {

    EditText postTitle, postContent;
    Button btnPost;
    DatabaseReference myref, myRefUser;
    FirebaseUser nuser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_post);

        postTitle = findViewById(R.id.textPostTitle);
        postContent = findViewById(R.id.textPostContent);
        btnPost = findViewById(R.id.button4);



        myref = FirebaseDatabase.getInstance().getReference("Post");
        myRefUser = FirebaseDatabase.getInstance().getReference("User");
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePost();

                Intent intToBoard = new Intent(PostActivity.this, BoardActivity.class);
                startActivity(intToBoard);
            }
        });

    }

    private void savePost(){
        String title = postTitle.getText().toString().trim();
        String content = postContent.getText().toString().trim();

        String id = myref.push().getKey();
        String email = nuser.getEmail();
        String name = nuser.getDisplayName();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-YYY, hh:mm");
        String date = simpleDateFormat.format(calendar.getTime());
        //String date = DateFormat.getDateInstance().format(calendar.getTime());
        Post post = new Post(id, title, content, name, email, date);
        myref.child(id).setValue(post);
        Toast.makeText(this, "es wurde erfolgreich gepostet", Toast.LENGTH_LONG).show();

    }
}
