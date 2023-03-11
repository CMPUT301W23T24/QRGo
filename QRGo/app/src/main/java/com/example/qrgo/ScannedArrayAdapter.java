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

public class ScannedArrayAdapter extends ArrayAdapter<String> {
    public ScannedArrayAdapter(Context context, ArrayList<String> users){
        super(context, 0, users);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup
            parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.scanned_content,
                    parent, false);
        } else {
            view = convertView;
        }

        String user = getItem(position);
        TextView scanned_user = view.findViewById(R.id.scanned_user_name);
        scanned_user.setText(user);
        return view;
    }

}
