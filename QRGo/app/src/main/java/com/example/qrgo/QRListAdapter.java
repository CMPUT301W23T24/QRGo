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

public class QRListAdapter extends ArrayAdapter<QR> {
    private ArrayList<QR> qrs;
    private Context context;

    public QRListAdapter(Context context, ArrayList<QR> qrs){
        super(context, 0, qrs);
        this.qrs = qrs;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.search_qr_activity_listview,  parent, false);

        }
        QR qr = qrs.get(position);

        TextView qrFace = view.findViewById(R.id.qrFaceLV);
        TextView qrName = view.findViewById(R.id.qrNameLV);

        qrFace.setText(qr.getFace());
        qrName.setText(qr.getId());


        return view;
    }
}
