package com.example.user.new_italk.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.user.new_italk.R;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    public TextView userid;
    public TextView username;

    public RecyclerViewHolder(View itemView) {
        super(itemView);

        userid = (TextView) itemView.findViewById(R.id.useridTextView);
        username = (TextView) itemView.findViewById(R.id.usernameTextView);
    }
}