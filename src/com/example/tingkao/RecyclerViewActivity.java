package com.example.tingkao;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.widget.LinearLayout;

import com.example.tingkao.adapter.ModelBean;
import com.example.tingkao.adapter.RecyclerAdapter;

public class RecyclerViewActivity extends BaseActivity {

	private RecyclerView recylerView;
	private List<ModelBean> list;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recycler_view);
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

			private boolean scrollingUp;

			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				scrollingUp = dy < 0;
			}

			public void onScrollStateChanged(RecyclerView recyclerView,
					int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				if (newState == RecyclerView.SCROLL_STATE_IDLE) {
					LayoutManager layout = recyclerView
							.getLayoutManager();
					LinearLayoutManager layoutManager = null ;
					if(layout instanceof LinearLayoutManager){
						layoutManager = (LinearLayoutManager)layout ; 
					}
					
				}
			}
			
			public void onScrollStateChanged2(RecyclerView recyclerView,
					int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				if (newState == RecyclerView.SCROLL_STATE_IDLE) {
					LayoutManager layout = recyclerView
							.getLayoutManager();
					LinearLayoutManager layoutManager = null ;
					if(layout instanceof LinearLayoutManager){
						layoutManager = (LinearLayoutManager)layout ; 
					}
					
					// layoutManager is the recyclerview's layout manager which
					// you need to have reference in advance
					int visiblePosition = scrollingUp ? layoutManager
							.findFirstVisibleItemPosition() : layoutManager
							.findLastVisibleItemPosition();
					int completelyVisiblePosition = scrollingUp ? layoutManager
							.findFirstCompletelyVisibleItemPosition()
							: layoutManager
									.findLastCompletelyVisibleItemPosition();
					// Check if we need to snap
					if (visiblePosition != completelyVisiblePosition) {
						recyclerView.smoothScrollToPosition(visiblePosition);
						return;
					}
				}
			}
		});

		int position = 0;
		//recylerView.smoothScrollToPosition(position);

	}

	/**
	 * 
	 * 
	 * LinearLayoutManager：线性布局 GridLayoutManager：网格布局
	 * StaggeredGridLayoutManager：流式布局
	 * 
	 */
	private void initViews() {
		recylerView = (RecyclerView) findViewById(R.id.recyclerview);

		GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);

		// recylerView.setLayoutManager(gridLayoutManager);

		// 设置布局显示方式
		recylerView.setLayoutManager(new LinearLayoutManager(this,
				LinearLayout.HORIZONTAL, true));

		// 设置添加删除item时候的动画
		recylerView.setItemAnimator(new DefaultItemAnimator());

	}
}
