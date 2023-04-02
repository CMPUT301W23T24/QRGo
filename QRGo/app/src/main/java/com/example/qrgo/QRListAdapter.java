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

/**
 * This helps display the QRs that a user searches for in SearchQR
 */
public class QRListAdapter extends ArrayAdapter<QR> {
    private ArrayList<QR> qrs;
    private Context context;

    /** Creates a qr list Adapter that will be used to paste any verifiable results taken from SearchQR.java into the search_qr_listview_activity.xml
    * @param context takes the context of the activity it is being used by
     * @param qrs which implements a list of QRs that will be used for searching
     */
    public QRListAdapter(Context context, ArrayList<QR> qrs){
        super(context, 0, qrs);
        this.qrs = qrs;
        this.context = context;
    }


    /**
     * Gets the View for the specific xml file
     * @param position gets the position of a view
     * @param convertView gets the view that it should be converted into
     * @param parent gets the ViewGroup
     * @return the view updated
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.search_qr_activity_listview,  parent, false);
        }
        QR qr = qrs.get(position);

        TextView qrFace = view.findViewById(R.id.qrFaceLV);
        TextView qrName = view.findViewById(R.id.qrNameLV);
        TextView qrScore = view.findViewById(R.id.qrScoreLV);

        qrFace.setText(qr.getFace());
        qrName.setText(qr.getId());
        qrScore.setText(qr.getScore().toString());


        return view;
    }
}
