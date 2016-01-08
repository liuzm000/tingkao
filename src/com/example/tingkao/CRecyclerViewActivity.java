package com.example.tingkao;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.widget.LinearLayout;

import com.example.tingkao.adapter.ModelBean;
import com.example.tingkao.adapter.RecyclerAdapter;
import com.example.tingkao.widget.MyRecyclerView;

public class CRecyclerViewActivity extends BaseActivity {

	private MyRecyclerView recylerView;
	private List<ModelBean> list;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.c_activity_recycler_view);
		initViews();
		get();
	}

	void get() {
		list = new ArrayList<ModelBean>();
		for (int i = 0; i < mImgIds.length; i++) {
			ModelBean b = new ModelBean();
			b.setResId(i);
			b.setTitle("【" + i + "】");
			b.setUrl(mImgIds[i]);
			list.add(b);
		}

		RecyclerAdapter ba = new RecyclerAdapter(this, list);
		recylerView.setAdapter(ba);

		recylerView.addOnScrollListener(new OnScrollListener() {
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
			}

			public void onScrollStateChanged(RecyclerView recyclerView,
					int newState) {
				super.onScrollStateChanged(recyclerView, newState);
			}
		});
	}

	/**
	 * 
	 * 
	 * LinearLayoutManager：线性布局 GridLayoutManager：网格布局
	 * StaggeredGridLayoutManager：流式布局
	 * 
	 */
	private void initViews() {
		recylerView = (MyRecyclerView) findViewById(R.id.recyclerview);

		// this.recylerView.unselectedAlpha = (1.0f);
		// this.recylerView.unselectedSaturation = (0.0f);
		// this.recylerView.unselectedScale = (0.5f);
		// // this.recylerView.spacing=(50);
		// this.recylerView.maxRotation = (0);
		// this.recylerView.scaleDownGravity = (0.2f);
		// this.recylerView.actionDistance =
		// (MyRecyclerView.ACTION_DISTANCE_AUTO);

		GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);

		// recylerView.setLayoutManager(gridLayoutManager);

		// 设置布局显示方式
		recylerView.setLayoutManager(new LinearLayoutManager(this,
				LinearLayout.HORIZONTAL, true));

		// 设置添加删除item时候的动画
		recylerView.setItemAnimator(new DefaultItemAnimator());

	}
}
