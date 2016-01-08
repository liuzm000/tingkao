package com.example.tingkao;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tingkao.widget.RLScrollView;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

@SuppressLint("NewApi")
public class RotationActivity extends BaseActivity {

	String tag = "RotationActivity";

	private FrameLayout content;

	private View cnParent;

	private Message msg = null;

	private RLScrollView rLScrollView;
	private TextView textView;
	int[] location = new int[2];
	int[] location2 = new int[2];
	private ImageView floatView, imageView1;
	int imageWidth = 0, imageHeight = 0;
	double degrees = 0;

	private final float offset_radio = 6.13f;

	private int pullDown = 1, back = 2, finish = 3, txt = 4;

	private boolean hasUp = false, hasDown = false, hasLeft = false,
			hasRight = false, isHorizontal = false, isVertical = false;
	private float startX, endX, startY, endY, distanceX, distanceY;
	private final int START_PULL_DOWN = 2, START_PULL_UP = -2,
			START_TO_LEFT = -3, START_TO_RIGHT = 3;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rotation);

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		mScreenWidth = dm.widthPixels;// 获取屏幕分辨率宽度
		mScreenHeight = dm.heightPixels;

		cnParent = findViewById(R.id.root);

		cnParent.setPivotX(mScreenWidth / 2);
		cnParent.setPivotY(mScreenHeight);

		// cnParent.setOnTouchListener(touchListener);
		// cnParent.setRotation(10f);
		textView = (TextView) findViewById(R.id.mTextView);
		// handler.sendEmptyMessage(txt);
	}

	OnTouchListener touchListener = new OnTouchListener() {

		public boolean onTouch(View v, MotionEvent ev) {
			return touchEvent(ev);
		}
	};

	public boolean onTouchEvent(MotionEvent event) {
		return touchEvent(event);
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

			degrees = Math.toDegrees(Math.atan(distanceX
					/ (mScreenHeight - endY)));// 求角度

			Log.i("degrees", String.format("int degrees %s  ", degrees));

			if (distanceX > 0) {// 还要获取滑动的速度
				if (degrees >= START_TO_RIGHT) {// 从左往右

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

			// degrees = 0;

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

	private Handler handler = new Handler() {

		private LinearLayout.LayoutParams params = null;

		public void dispatchMessage(android.os.Message msg) {

			if (msg.what == pullDown) {

				int x = msg.arg1;
				int y = msg.arg2;

				int f = x / mScreenWidth;

				float r = (distanceX + START_TO_LEFT) / offset_radio;

				// 酷狗的是一个扇形轨迹，并不是以某个点旋转
				cnParent.setRotation((float) degrees + START_TO_LEFT);
				// cnParent.setRotation(r);

				Log.i(tag,
						String.format("cnParent.getRotation x %s ",
								cnParent.getRotation()));

			} else if (msg.what == back) {

				int x = msg.arg1;
				int y = msg.arg2;

				float r = cnParent.getRotation();

				Log.i(tag, String.format("cnParent.getRotation x %s ", r));

				r = -r - 90;
				Log.i("degrees",
						String.format("int getRotation %s  ",
								cnParent.getRotation()));
				// back(r, 0);
				// back2(cnParent.getRotation());
				// back2((float) degrees + START_TO_LEFT);

				// cnParent.setRotation(-(float) degrees + START_TO_LEFT);

				float a = (float) Math.toDegrees(cnParent.getRotation());
				// cnParent.setRotation((float) degrees + START_TO_LEFT);

				// float abs = Math.abs(cnParent.getRotation());

				// -- 不会得正 ！
				float abs = Math.abs((float) (degrees + START_TO_LEFT));

				if (degrees >= 22) {
					back3(abs, 180, 500, cnParent);
				} else {
					back3(abs, 0, 100, cnParent);
				}

				// back2(abs);

			} else if (msg.what == finish) {

			} else if (msg.what == txt) {
				textView.setText(getTxt());
			}

		};
	};

	void back3(final float fromDegrees, final float toDegrees, long duration,
			View v) {// 这都行
		ObjectAnimator ob = ObjectAnimator.ofFloat(v, "rotation", fromDegrees,
				toDegrees);

		ob.addUpdateListener(new ObjectAnimator.AnimatorUpdateListener() {

			public void onAnimationUpdate(ValueAnimator animation) {
				Object cVal = animation.getAnimatedValue();

				Log.i("cVal", String.format("int cVal %s  ", cVal));
			}
		});

		ob.addListener(new AnimatorListener() {

			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub

			}

			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub

			}

			public void onAnimationEnd(Animator animation) {
				if (toDegrees != 0) {
					finish();
				}

				degrees = 0;
			}

			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub

			}
		});
		ob.setDuration(duration).start();
	}

	void back2(float fromDegrees) {
		float pivotX = mScreenWidth / 2, pivotY = mScreenHeight;
		// 没卵用
		RotateAnimation ra = new RotateAnimation(fromDegrees, 0, pivotX, pivotY);
		ra.setDuration(500);
		cnParent.startAnimation(ra);
	}

	void back(float fromDegrees, float toDegrees) {

		// x 0 is the left edge.
		// y 0 is the top edge.
		float pivotX = mScreenWidth / 2, pivotY = mScreenHeight;
		RotateAnimation ra = new RotateAnimation(fromDegrees, toDegrees,
				pivotX, pivotY);

		int a = Animation.RELATIVE_TO_SELF;

		ra.setDuration(500);
		ra.setFillAfter(true);// 动画执行完后是否停留在执行完的状态
		ra.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation animation) {

			}

			public void onAnimationRepeat(Animation animation) {

			}

			public void onAnimationEnd(Animation animation) {
				// finish();
			}
		});
		cnParent.startAnimation(ra);
		// ObjectAnimator ob = ObjectAnimator.ofFloat(cnParent, "", r, 360f);

	}
}
