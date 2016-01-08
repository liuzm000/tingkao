package com.example.tingkao.adapter;

import com.example.tingkao.R;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MyViewHolder extends RecyclerView.ViewHolder {

	public ImageView imageView;
	public TextView title;

	public MyViewHolder(View view) {
		super(view);
		imageView = (ImageView) view.findViewById(R.id.pic);
		title = (TextView) view.findViewById(R.id.name);
	}
}