package com.example.tingkao.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.HorizontalScrollView;

public class MyHorizontalScrollView extends HorizontalScrollView {

	private onScrollChangedListener scrollChangedListener;
	private Context context;

	private void init(){
		
	}
	
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if (scrollChangedListener != null) {
			scrollChangedListener.onScrollChange(l, t, oldl, oldt);
		}
	}
	
	protected boolean getChildStaticTransformation(View child, Transformation t) {
		return super.getChildStaticTransformation(child, t);
	}

	public interface onScrollChangedListener {
		public void onScrollChange(int l, int t, int oldl, int oldt);
	}

	public MyHorizontalScrollView(Context context) {
		super(context);
		this.context = context;
	}

	public MyHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public MyHorizontalScrollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}

	public onScrollChangedListener getScrollChangedListener() {
		return scrollChangedListener;
	}

	public void setScrollChangedListener(
			onScrollChangedListener scrollChangedListener) {
		this.scrollChangedListener = scrollChangedListener;
	}

}
