package com.rast.uniminiboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BoardActivity extends AppCompatActivity {

    //Button btnLogOut;
    Button btnPost;
    TextView UserName;
    ListView listViewPosts;
    DatabaseReference databasePost;
    List<Post> postList;

    public static final String PostTitle = "PostTitle";
    public static final String PostContent = "PostContent";
    public static final String PostID = "PostID";

    FirebaseUser nuser = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        btnPost = findViewById(R.id.button2);
        UserName = findViewById(R.id.textView4);
        listViewPosts = findViewById(R.id.listViewPosts);

        postList = new ArrayList<>();

        databasePost = FirebaseDatabase.getInstance().getReference("Post");

        final String name = nuser.getDisplayName();
        UserName.setText(name);


        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intToPost = new Intent(BoardActivity.this, PostActivity.class);

                startActivity(intToPost);
            }
        });

        listViewPosts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Post post = postList.get(position);
                Intent intToCom = new Intent(BoardActivity.this, CommentActivity.class);
                intToCom.putExtra(PostID, post.getId());
                intToCom.putExtra(PostContent, post.getContent());
                intToCom.putExtra(PostTitle, post.getTitle());

                startActivity(intToCom);
            }
        });

        listViewPosts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Post post = postList.get(position);
                if(nuser.getEmail().equals(post.getEmail())) {
                    DatabaseReference drPost = FirebaseDatabase.getInstance().getReference("Post").child(post.getId());
                    DatabaseReference drComment = FirebaseDatabase.getInstance().getReference("Comment").child(post.getId());

                    drPost.removeValue();
                    drComment.removeValue();

                    Toast.makeText(BoardActivity.this, "Post wird gelöscht", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(BoardActivity.this, "Sie können nur Ihre Posten löschen", Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });

        UserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intToProfile = new Intent(BoardActivity.this, ProfileActivity.class);
                startActivity(intToProfile);
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        databasePost.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                postList.clear();

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Post post = postSnapshot.getValue(Post.class);
                    postList.add(post);
                }
                Collections.reverse(postList);

                PostList adapter = new PostList(BoardActivity.this, postList);
                listViewPosts.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
