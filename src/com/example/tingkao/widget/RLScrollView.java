package com.example.tingkao.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class RLScrollView extends ScrollView {

	String tag = "RLScrollView";

	private OnPullListener pullListener;
	private OnSlideListener slideListener;

	public RLScrollView(Context context) {
		super(context);
	}

	public RLScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RLScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public interface OnScrollChangedListener {
		public void onScrollChanged(int x, int y, int oldxX, int oldY);
	}

	public interface OnPullListener {

		public void onStart(float x, float y);

		public void onFinish(float x, float y);

		public void onPullUp(float x, float y, float dx, float dy);

		public void onPullDown(float x, float y, float dx, float dy);
	}

	public interface OnSlideListener {

		public void onStart(float x, float y);

		public void onFinish(float x, float y);

		public void toLeft(float x, float y, float dx, float dy);

		public void toRight(float x, float y, float dx, float dy);
	}

	/**
	 * OnBorderListener, Called when scroll to top or bottom
	 * 
	 * @author Trinea 2013-5-22
	 */
	public interface OnBorderListener {

		/**
		 * Called when scroll to bottom
		 */
		public void onBottom();

		/**
		 * Called when scroll to top
		 */
		public void onTop();
	}

	private OnScrollChangedListener onScrollChangedListener;

	/**
	 * 
	 * @param onScrollChangedListener
	 */
	public void setOnScrollListener(
			OnScrollChangedListener onScrollChangedListener) {
		this.onScrollChangedListener = onScrollChangedListener;
	}

	protected void onScrollChanged(int x, int y, int oldX, int oldY) {
		super.onScrollChanged(x, y, oldX, oldY);
		if (onScrollChangedListener != null) {
			onScrollChangedListener.onScrollChanged(x, y, oldX, oldY);
		}
	}

	// public boolean dispatchTouchEvent(MotionEvent ev); //用来分派event
	// public boolean onInterceptTouchEvent(MotionEvent ev); //用来拦截event
	// public boolean onTouchEvent(MotionEvent ev); //用来处理event
	public boolean dispatchTouchEvent(MotionEvent ev) {// 1
		return super.dispatchTouchEvent(ev);
	}

	public boolean onInterceptTouchEvent(MotionEvent ev) { // 2
		return super.onInterceptTouchEvent(ev);
		// return false ;
		// 若返回值为True事件会传递到自己的onTouchEvent()；
		// 若返回值为False传递到下一个view的dispatchTouchEvent()；
	}

	private boolean hasUp = false, hasDown = false, hasLeft = false,
			hasRight = false, isHorizontal = false, isVertical = false;
	private float startX, endX, startY, endY, distanceX, distanceY;

	private final int START_PULL_DOWN = 2, START_PULL_UP = -2,
			START_TO_LEFT = -50, START_TO_RIGHT = 50;

	public boolean onTouchEvent(MotionEvent ev) {
		if (pullListener != null) {
			boolean slide = (slideListener != null);
			int action = ev.getAction();
			
			switch (action) {
			case MotionEvent.ACTION_DOWN:

				startX = ev.getRawX();// 相对于屏幕的位置
				startY = ev.getRawY();

				if (getScrollY() == 0) {// 直接返回，任由其滚动
					pullListener.onStart(startX, startY);
				}
				if (slide) {
					slideListener.onStart(startX, startY);
				}
				break;
			case MotionEvent.ACTION_MOVE:

				if (startX == 0) {
					startX = ev.getRawX();
				}
				if (startY == 0) {
					startY = ev.getRawY();
				}

				endX = ev.getRawX();
				endY = ev.getRawY();

				// ↓ = y , → = x ,
				distanceX = endX - startX;
				distanceY = endY - startY;

				if (slide) {

					if (distanceX > 0) {// 还要获取滑动的速度
						if (distanceX >= START_TO_RIGHT) {// 从左往右
							slideListener.toRight(endX, endY, distanceX
									+ START_TO_LEFT, distanceY);
							hasRight = true;
							isHorizontal = true;
							return false;
						}
					} else {
						if (hasRight) {
							slideListener.toLeft(endX, endY, distanceX
									+ START_TO_RIGHT, distanceY);
							isHorizontal = true;
						}
						if (distanceX <= START_TO_LEFT) {// 从左往右
							slideListener.toLeft(endX, endY, distanceX
									+ START_TO_RIGHT, distanceY);
							isHorizontal = true;
						}
					}
				}

				if (getScrollY() == 0) {// 直接返回，任由其滚动

					if (distanceY > 0) {
						if (distanceY >= START_PULL_DOWN) {
							pullListener.onPullDown(endX, endY, distanceX,
									distanceY + START_PULL_UP);
							isVertical = true;
							return false;
						}
					} else {
						if (distanceY <= START_PULL_UP) {
							pullListener.onPullUp(endX, endY, distanceX,
									distanceY + START_PULL_DOWN);
							isVertical = true;
						}
					}

					return super.onTouchEvent(ev);
				}
				break;
			case MotionEvent.ACTION_UP:

				pullListener.onFinish(endX, endY);
				if (slide) {
					slideListener.onFinish(endX, endY);
				}

				startX = 0;
				startY = 0;

				endX = 0;
				endY = 0;

				distanceX = 0;
				distanceY = 0;

				hasRight = false;
				hasDown = false;
				hasUp = false;
				hasLeft = false;

				isVertical = false;
				isHorizontal = false;

				break;
			}
		}
		return super.onTouchEvent(ev);
	}

	public OnPullListener getPullListener() {
		return pullListener;
	}

	public void setPullListener(OnPullListener pullListener) {
		this.pullListener = pullListener;
	}

	public OnSlideListener getSlideListener() {
		return slideListener;
	}

	public void setSlideListener(OnSlideListener slideListener) {
		this.slideListener = slideListener;
	}

}