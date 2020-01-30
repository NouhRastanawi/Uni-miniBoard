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

import java.util.List;

public class CommentList extends ArrayAdapter<Comment>{
    private Activity context;
    private List<Comment> commentlist;
    FirebaseUser nuser = FirebaseAuth.getInstance().getCurrentUser();

    public CommentList(Activity context, List<Comment> commentlist){
        super(context, R.layout.list_layout2, commentlist);
        this.context = context;
        this.commentlist = commentlist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout2, null, true);

        TextView textViewContent = listViewItem.findViewById(R.id.textViewCommentContent);
        TextView textViewName = listViewItem.findViewById(R.id.textViewName);
        TextView textViewDate = listViewItem.findViewById(R.id.textViewDate);
        Comment comment = commentlist.get(position);

        textViewContent.setText(comment.getContent());
        textViewName.setText("Von: " + comment.getName());
        textViewDate.setText(comment.getDate());
        return listViewItem;
    }

}
