package com.example.qrgo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * Creates the textview for the list view of user
 */
public class UserListAdapter extends ArrayAdapter<User> {

    private ArrayList<User> users;

    private Context context;

    /**
     * Sets up the context and users for the list view
     * @param context
     * @param users
     */
    public UserListAdapter(Context context, ArrayList<User> users){
        super(context,0,users);
        this.users = users;
        this.context = context;
    }
    /*@NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.search_user_listview,  parent, false);
        }
        User user2 = users.get(position);
        user2.getValuesFromDb(user2.getDeviceID(), new User.OnUserLoadedListener() {
            @Override
            public void onUserLoaded(User user) {
                TextView userName = view.findViewById(R.id.friendUser);
                userName.setText(user2.getUserName());
            }
        });
        return view;*/

    /**
     * gets the listview
     * @param position
     * @param convertView
     * @param parent
     * @return returns the view of the ListView
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup
            parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.search_user_listview,
                    parent, false);
        } else {
            view = convertView;
        }

        User user2 = users.get(position);
        user2.getValuesFromDb(user2.getDeviceID(), new User.OnUserLoadedListener() {
            @Override
            public void onUserLoaded(User user) {
                TextView scanned_user = view.findViewById(R.id.friendUser);
                scanned_user.setText(user.getUserName());
            }
        });
        return view;
    }
}
