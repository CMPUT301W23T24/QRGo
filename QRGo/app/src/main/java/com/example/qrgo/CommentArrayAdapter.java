package com.example.qrgo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CommentArrayAdapter extends ArrayAdapter<Comment> {

    public CommentArrayAdapter(Context context, ArrayList<Comment> comments) {
        super(context, 0 , comments);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup
            parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.content,
                    parent, false);
        } else {
            view = convertView;
        }

        Comment comment = getItem(position);
        // Updates user name to show in list of comments
        TextView comment_userName = view.findViewById(R.id.user_name);
        comment_userName.setText(comment.getUserName());
        // Updates comment to show in list of comments
        TextView comment_text = view.findViewById(R.id.comment_preview);
        comment_text.setText(comment.getComment());


        return view;
    }

}
