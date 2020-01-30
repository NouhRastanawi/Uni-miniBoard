package com.rast.uniminiboard;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;
import java.util.List;

public class PostList extends ArrayAdapter<Post> {

    private Activity context;
    private List<Post> postList;
    FirebaseUser nuser = FirebaseAuth.getInstance().getCurrentUser();

    public PostList(Activity context, List<Post> postsList){
        super(context, R.layout.list_layout, postsList);
        this.context = context;
        this.postList = postsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView textViewTitle = listViewItem.findViewById(R.id.textViewPostTitle);
        TextView textViewContent = listViewItem.findViewById(R.id.textViewCommentContent);
        TextView textViewName = listViewItem.findViewById(R.id.textViewName);
        TextView textViewDate = listViewItem.findViewById(R.id.textViewDate);
        Post post = postList.get(position);

        textViewTitle.setText(post.getTitle());
        textViewContent.setText(post.getContent());
        textViewName.setText("Von: " + post.getUserName());
        textViewDate.setText(post.getDate());
        return listViewItem;
    }


}
