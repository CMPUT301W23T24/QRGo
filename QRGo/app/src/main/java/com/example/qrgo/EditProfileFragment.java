package com.example.qrgo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EditProfileFragment extends DialogFragment {
    private EditText userName;
    private EditText name;
    private EditText email;
    private EditText phoneNum;
    private User user;

    public EditProfileFragment(User user){
        this.user = user;
    }
    public EditProfileFragment(){
    }

    private OnFragmentInteractionListener listener;

    public interface OnFragmentInteractionListener{
        void onOkkPressed(User newUser,String userName, String name, String email, Integer phoneNum);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener){
            listener = (OnFragmentInteractionListener) context;
        }
        else{
            throw new RuntimeException(context.toString()
                    + "Must implement OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.user_profile_edit, null);
        userName = view.findViewById(R.id.userNameEdit);
        name = view.findViewById(R.id.nameEdit);
        email = view.findViewById(R.id.emailEdit);
        phoneNum = view.findViewById(R.id.phoneNumEdit);

        userName.setText(user.getUserName());
        name.setText(user.getName());
        email.setText(user.getEmail());
        phoneNum.setText(String.valueOf(user.getPhoneNum()));
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Edit profile")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    String newUserName= userName.getText().toString();
                    String newName= name.getText().toString();
                    String newEmail= email.getText().toString();
                    Integer newPhone= Integer.valueOf(phoneNum.getText().toString());
                    listener.onOkkPressed(user, newUserName, newName, newEmail, newPhone);
                    }
                }).create();
    }
}
