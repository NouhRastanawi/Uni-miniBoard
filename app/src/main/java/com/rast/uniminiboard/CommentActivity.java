package com.rast.uniminiboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class CommentActivity extends AppCompatActivity {

    TextView textViewTitle, textViewContent;
    EditText editTextComment;
    Button addComment;
    ListView listviewComment;

    DatabaseReference databaseComment;
    FirebaseUser nuser = FirebaseAuth.getInstance().getCurrentUser();
    List<Comment> commentList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        textViewTitle = findViewById(R.id.textViewTitle);
        editTextComment = findViewById(R.id.editTextComment);
        textViewContent = findViewById(R.id.textViewContent);
        addComment = findViewById(R.id.buttonComment);
        listviewComment = findViewById(R.id.ListViewComment);

        final Intent intent = getIntent();
        String id = intent.getStringExtra(BoardActivity.PostID);
        String postTitle = intent.getStringExtra(BoardActivity.PostTitle);
        String postContent = intent.getStringExtra(BoardActivity.PostContent);

        commentList = new ArrayList<>();

        //Toast.makeText(CommentActivity.this, postTitle, Toast.LENGTH_LONG).show();
        textViewTitle.setText(postTitle);
        textViewContent.setText(postContent);

        //databaseComment = FirebaseDatabase.getInstance().getReference("Post").child(id);
        databaseComment = FirebaseDatabase.getInstance().getReference("Comment").child(id);

        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveComment();
            }
        });

        listviewComment.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Comment comment = commentList.get(position);
                if(nuser.getDisplayName().equals(comment.getName())) {
                    String PostID = intent.getStringExtra(BoardActivity.PostID);
                    DatabaseReference drComment = FirebaseDatabase.getInstance().getReference("Comment").child(PostID).child(comment.getId());

                    drComment.removeValue();

                    Toast.makeText(CommentActivity.this, "Comment wird gelöscht", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(CommentActivity.this, "Sie können nur Ihre Kommentare löschen", Toast.LENGTH_LONG).show();
                }

                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseComment.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentList.clear();

                for(DataSnapshot commentSnapshot : dataSnapshot.getChildren()){
                    Comment comment = commentSnapshot.getValue(Comment.class);
                    commentList.add(comment);

                }

                Collections.reverse(commentList);
                CommentList commentListAdapter = new CommentList(CommentActivity.this, commentList);
                listviewComment.setAdapter(commentListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void saveComment() {
        String content = editTextComment.getText().toString().trim();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-YYY, hh:mm");
        String date = simpleDateFormat.format(calendar.getTime());
        //String date = DateFormat.getDateInstance().format(calendar.getTime());
        String name = nuser.getDisplayName();

        if(! TextUtils.isEmpty(content)) {
            String id = databaseComment.push().getKey();
            Comment comment = new Comment(id, name, content, date);
            databaseComment.child(id).setValue(comment);
            Toast.makeText(this, "Kommentar erfolgreich hinzugefügt", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "Kommentar sollte nicht leer sein", Toast.LENGTH_LONG).show();
        }

    }
}
