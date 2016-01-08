package com.example.tingkao.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.OnGestureListener;
import android.widget.LinearLayout;

public class MyView extends LinearLayout {
	
	private GestureDetector gestureDetector ;
	Context context ;
	
	void init(Context context){
		this.context = context ;
		gestureDetector = new GestureDetector(context, listener);
	}
	
	 OnGestureListener listener = new OnGestureListener() {
		
		public boolean onSingleTapUp(MotionEvent e) {
			return false;
		}
		
		public void onShowPress(MotionEvent e) {
		}
		
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
				float distanceY) {
			return false;
		}
		
		public void onLongPress(MotionEvent e) {
			
		}
		
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			return false;
		}
		
		public boolean onDown(MotionEvent e) {
			return false;
		}
		
	};
	
	public MyView(Context context) {
		super(context);
	}

	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@SuppressLint("NewApi")
	public MyView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

}
