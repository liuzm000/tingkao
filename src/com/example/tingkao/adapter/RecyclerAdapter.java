package com.example.tingkao.adapter;

import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tingkao.R;
import com.squareup.picasso.Picasso;

/**
 * Description:RecyclerView   ≈‰∆˜ User: xjp Date: 2015/6/8 Time: 10:15
 */

public class RecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {

	private Context context;
	private List<ModelBean> list;
	private Resources res;

	public RecyclerAdapter(Context context, List<ModelBean> list) {
		this.context = context;
		this.list = list;
		res = context.getResources();
	}

	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.item_card_view, parent, false);
		return new MyViewHolder(view);
	}

	public void onBindViewHolder(final MyViewHolder holder, final int position) {
		final ModelBean bean = list.get(position);
		holder.title.setText(bean.getTitle());
		// holder.imageView.setImageResource(bean.getResId());
		Picasso.with(context).load(bean.getUrl()).into(holder.imageView);
	}

	public int getItemCount() {
		return null == list ? 0 : list.size();
	}

}