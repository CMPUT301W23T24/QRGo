package com.example.qrgo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * This class is responsible for the add comment fragment
 */
public class AddCommentFragment extends DialogFragment {

    private String username;
    interface AddCommentDialogListener {
        void addComment(Comment comment);
    }

    private AddCommentDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCommentDialogListener) {
            listener = (AddCommentDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCommentDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.add_comment, null);
        EditText editComment = view.findViewById(R.id.add_comment_here);

        MainDoop activity = (MainDoop) getActivity();
        String username= activity.getCommenterUsername();


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle("Add Comment")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Add", (dialog, which) -> {
                    String comment = editComment.getText().toString();
                    listener.addComment(new Comment(username, comment));
                })
                .create();

    }
}
