package com.example.tingkao;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tingkao.widget.RLScrollView;
import com.example.tingkao.widget.RLScrollView.OnPullListener;
import com.example.tingkao.widget.RLScrollView.OnScrollChangedListener;
import com.example.tingkao.widget.RLScrollView.OnSlideListener;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

@SuppressLint("NewApi")
public class KuGouSlideActivity extends BaseActivity {

	String tag = "KuGouSlideActivity";

	private FrameLayout content;

	private View cnParent;

	private RLScrollView rLScrollView;
	private TextView textView;
	int[] location = new int[2];
	int[] location2 = new int[2];
	private ImageView floatView, imageView1;
	int imageWidth = 0, imageHeight = 0;

	private final float offset_radio = 4.13f;

	private int pullDown = 1, back = 2, finish = 3, txt = 4;

	private boolean hasUp = false, hasDown = false, hasLeft = false,
			hasRight = false, isHorizontal = false, isVertical = false;
	private float startX, endX, startY, endY, distanceX, distanceY;
	private final int START_PULL_DOWN = 2, START_PULL_UP = -2,
			START_TO_LEFT = -20, START_TO_RIGHT = 20;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.c_activity_main);

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		mScreenWidth = dm.widthPixels;// 获取屏幕分辨率宽度
		mScreenHeight = dm.heightPixels;

		Log.i(tag, String.format("mScreenWidth = %s mScreenHeight = %s",
				mScreenWidth, mScreenHeight));

		findViewById();
		setListener();
	}

	private void findViewById() {
		rLScrollView = (RLScrollView) findViewById(R.id.rLScrollView);
		textView = (TextView) findViewById(R.id.mTextView);
		handler.sendEmptyMessage(txt);

		content = (FrameLayout) findViewById(android.R.id.content);

		int gray2 = getResources().getColor(R.color.gray);
		// content.setBackgroundColor(gray2);

		imageView1 = (ImageView) findViewById(R.id.imageView1);

		// cnParent = content.getRootView();

		cnParent = findViewById(R.id.root);

		FrameLayout.LayoutParams lp2 = new FrameLayout.LayoutParams(-1, 1920);

		LayoutParams lp3 = cnParent.getLayoutParams();
		lp3.width = -1;
		lp3.height = 1020;
		lp3.height = -2;
		// cnParent.setLayoutParams(lp3); // 没用

		LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) rLScrollView
				.getLayoutParams();
		lp.width = -1;
		lp.height = -2;
		// rLScrollView.setLayoutParams(lp);// 失效

		// cnParent.setRotationY(15.3f); // 只是3D旋转
		// cnParent.setRotationX(15.3f);
		int gray = getResources().getColor(R.color.red_light);
		// cnParent.setBackgroundColor(gray);

		// rLScrollView.setBackgroundColor(gray);

		// cnParent.setRotation(20f);
		// cnParent.setPivotX(mScreenWidth - 75);
		// cnParent.setPivotY(mScreenHeight - 75);

		// cnParent.setPivotX(0);
		// cnParent.setPivotY(0);

		// cnParent.setX(12);
		// cnParent.setY(-12);

		rLScrollView.setRotation(10f);
	}

	OnTouchListener touchListener = new OnTouchListener() {

		public boolean onTouch(View v, MotionEvent ev) {
			return touchEvent(ev);
		}
	};

	public boolean touchEvent(MotionEvent ev) {

		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:

			startX = ev.getRawX();// 相对于屏幕的位置
			startY = ev.getRawY();

			break;
		case MotionEvent.ACTION_MOVE:
			endX = ev.getRawX();
			endY = ev.getRawY();

			// ↓ = y , → = x ,
			distanceX = endX - startX;
			distanceY = endY - startY;

			if (distanceX > 0) {// 还要获取滑动的速度
				if (distanceX >= START_TO_RIGHT) {// 从左往右

					msg = handler.obtainMessage();
					msg.arg1 = (int) (distanceX + START_TO_LEFT);
					msg.arg2 = (int) (distanceY + START_TO_LEFT);
					msg.what = pullDown;
					handler.sendMessage(msg);

				}
			} else {
				if (distanceX <= START_TO_LEFT) {// 从左往右

				}
			}

			break;
		case MotionEvent.ACTION_UP:

			msg = handler.obtainMessage();
			msg.what = back;
			handler.sendMessage(msg);

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

		return false;
	}

	public boolean onTouchEvent2(MotionEvent ev) {
		return touchEvent(ev);
	}

	private void setListener() {
		rLScrollView.setOnScrollListener(onScrollChangedListener);
		cnParent.setOnTouchListener(touchListener);
		textView.setOnClickListener(clickListener);
		// rLScrollView.setPullListener(pullListener);
		// rLScrollView.setSlideListener(slideListener);
	}

	private int paddingLeft = 0, paddingRight = 0;

	private int index = 0;

	private Handler handler = new Handler() {

		private LinearLayout.LayoutParams params = null;

		public void dispatchMessage(android.os.Message msg) {

			if (msg.what == pullDown) {

				int x = msg.arg1;
				int y = msg.arg2;

				int f = x / mScreenWidth;

				index++;

				// 酷狗的是一个扇形轨迹，并不是以某个点旋转
				cnParent.setRotation(x / offset_radio);
				cnParent.setPivotX(mScreenWidth / 2);
				cnParent.setPivotY(mScreenHeight);

			} else if (msg.what == back) {

				int x = msg.arg1;
				int y = msg.arg2;

				cnParent.setRotation(0);
				cnParent.setPivotX(0);
				cnParent.setPivotY(0);

			} else if (msg.what == finish) {

			} else if (msg.what == txt) {
				textView.setText(getTxt());
			}

		};
	};



	private void anim(final View view, int paddingLeft, int paddingRight) {

		ObjectAnimator ob = ObjectAnimator.ofInt(view, "paddingLeft",
				paddingLeft, view.getWidth());
		ob.addUpdateListener(new ObjectAnimator.AnimatorUpdateListener() {

			public void onAnimationUpdate(ValueAnimator animation) {
				int cVal = (Integer) animation.getAnimatedValue();
				view.setPadding(cVal, 0, -cVal, 0);
			}
		});

		ob.addListener(animatorListener);
		ob.setDuration(1 * 100).start();

	}

	private AnimatorListener animatorListener = new AnimatorListener() {

		public void onAnimationStart(Animator animation) {

		}

		public void onAnimationRepeat(Animator animation) {

		}

		public void onAnimationEnd(Animator animation) {
			finish();
		}

		public void onAnimationCancel(Animator animation) {

		}
	};

	private OnClickListener clickListener = new OnClickListener() {

		public void onClick(View v) {
			if (v.getId() == R.id.textView) {
			}
		}
	};

	public boolean dispatchTouchEvent(MotionEvent ev) {
		return super.dispatchTouchEvent(ev);
	}

	private OnScrollChangedListener onScrollChangedListener = new OnScrollChangedListener() {

		public void onScrollChanged(int x, int y, int oldX, int oldY) {
		}
	};

	public int getStatusBarHeight() {
		return Resources.getSystem().getDimensionPixelSize(
				Resources.getSystem().getIdentifier("status_bar_height",
						"dimen", "android"));
	}

	private Message msg = null;

	private OnPullListener pullListener = new OnPullListener() {

		// 完美解决
		public void onStart(float x, float y) {

		}

		public void onPullUp(float x, float y, float dx, float dy) {
		}

		public void onPullDown(float x, float y, float dx, float dy) {

		}

		public void onFinish(float x, float y) {

		}
	};

	private OnSlideListener slideListener = new OnSlideListener() {

		public void toRight(float x, float y, float dx, float dy) {
			Log.i(tag, String.format("toRight x %s y %s", dx, dy));
			msg = handler.obtainMessage();
			msg.arg1 = (int) dx;
			msg.arg2 = (int) dy;
			msg.what = pullDown;
			handler.sendMessage(msg);
		}

		public void toLeft(float x, float y, float dx, float dy) {

		}

		public void onStart(float x, float y) {

		}

		public void onFinish(float x, float y) {
			msg = handler.obtainMessage();
			msg.what = back;
			handler.sendMessage(msg);
		}
	};
}
