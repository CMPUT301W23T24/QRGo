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

public class TotalArrayAdapter extends ArrayAdapter<User> {

    /**
     * Displays the content into the List view
     * @param context
     * @param users
     */
    public TotalArrayAdapter(Context context, ArrayList<User> users){
        super(context, 0, users);
    }

    /**
     *  gets the view of the branch
     * @param position
     * @param convertView
     * @param parent
     * @return the view of the current page
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup
            parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.total_score_content,
                    parent, false);
        } else {
            view = convertView;
        }

        User userId = getItem(position);
        User user = new User(userId.getDeviceID());
        user.getValuesFromDb(userId.getDeviceID(), new User.OnUserLoadedListener() {
            @Override
            public void onUserLoaded(User user) {
                TextView loaded_user = view.findViewById(R.id.user_name);
                loaded_user.setText(user.getUserName());

                TextView total_score = view.findViewById(R.id.total_score);
                total_score.setText(user.getScore().toString());
            }
        });

        Log.d("userId", userId.getDeviceID());
        Log.d("userName", user.getUserName());
        Log.d("Total Score", user.getScore().toString());

        return view;
    }
}
