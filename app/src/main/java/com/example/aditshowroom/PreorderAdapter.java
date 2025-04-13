package com.example.aditshowroom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class PreorderAdapter extends ArrayAdapter<PreorderItem> {
    public PreorderAdapter(Context context, ArrayList<PreorderItem> preorderList) {
        super(context, 0, preorderList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_preorder, parent, false);
        }

        PreorderItem item = getItem(position);

        TextView mobilTextView = convertView.findViewById(R.id.mobilTextView);
        TextView pembayaranTextView = convertView.findViewById(R.id.pembayaranTextView);
        TextView uangMukaTextView = convertView.findViewById(R.id.uangMukaTextView);
        TextView warnaTextView = convertView.findViewById(R.id.warnaTextView);

        if (item != null) {
            mobilTextView.setText("Mobil: " + item.getMobil());
            pembayaranTextView.setText("Pembayaran: " + item.getPembayaran());
            uangMukaTextView.setText("Uang Muka: Rp " + item.getUangMuka());
            warnaTextView.setText("Warna: " + item.getWarna());
        }

        return convertView;
    }
}
