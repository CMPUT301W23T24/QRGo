package com.example.qrgo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


/**
 * Shows the user the profile of someone they searched up
 */
public class ViewFriendProfileFragment extends DialogFragment {
    private  User user;
    private TextView userName;
    private TextView name;
    private TextView email;
    private TextView phoneNum;


    /**
     * Creates the constructor of the fragment
     * @param user
     */
    public ViewFriendProfileFragment(User user){
        this.user = user;
    }
    public ViewFriendProfileFragment(){
    }

    private ViewFriendProfileFragment.OnFragmentInteractionListener listener;

    public interface OnFragmentInteractionListener{
        void onOkPressed();
    }
    /**
     * Attaches the fragment to the View
     * @param context Fragment Context
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ViewFriendProfileFragment.OnFragmentInteractionListener){
            listener = (ViewFriendProfileFragment.OnFragmentInteractionListener) context;
        }
        else{
            throw new RuntimeException(context.toString()
                    + "Must implement OnFragmentInteractionListener");
        }
    }
    /**
     * Creates the Dialog
     * @param savedInstanceState The last saved instance state of the Fragment,
     * or null if this is a freshly created Fragment.
     *
     * @return the builder
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.view_friend_profile, null);
        userName = view.findViewById(R.id.userNameView);
        name = view.findViewById(R.id.nameView);
        email = view.findViewById(R.id.emailView);
        phoneNum = view.findViewById(R.id.phoneNumView);
        userName.setText(user.getUserName());
        name.setText(user.getName());
        email.setText(user.getEmail());
        phoneNum.setText(String.valueOf(user.getPhoneNum()));
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("View Profile")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String newUserName= userName.getText().toString();
                        String newName= name.getText().toString();
                        String newEmail= email.getText().toString();
                        Integer newPhone= Integer.valueOf(phoneNum.getText().toString());
                        listener.onOkPressed();
                    }
                }).create();
    }
}
