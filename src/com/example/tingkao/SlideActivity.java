package com.example.tingkao;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
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
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

@SuppressLint("NewApi")
public class SlideActivity extends BaseActivity {

	String tag = "SlideActivity";

	private FrameLayout content;

	private View cnParent;

	private RLScrollView rLScrollView;
	private TextView textView, mTextView;
	int[] location = new int[2];
	int[] location2 = new int[2];
	private ImageView floatView, imageView1;
	int imageWidth = 0, imageHeight = 0;

	private final float offset_radio = 1.9f;

	private int pullDown = 1, back = 2, finish = 3, txt = 4;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		findViewById();
		setListener();
	}

	private void findViewById() {
		rLScrollView = (RLScrollView) findViewById(R.id.rLScrollView);
		textView = (TextView) findViewById(R.id.textView);

		mTextView = (TextView) findViewById(R.id.mTextView);
		handler.sendEmptyMessage(txt);

		content = (FrameLayout) findViewById(android.R.id.content);
		imageView1 = (ImageView) findViewById(R.id.imageView1);
		cnParent = content.getRootView();
		Log.i(tag, String.format("cnParent id %s ", cnParent.getId()));

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		mScreenWidth = dm.widthPixels;// 获取屏幕分辨率宽度
		mScreenHeight = dm.heightPixels;
	}

	private void setListener() {
		rLScrollView.setOnScrollListener(onScrollChangedListener);
		textView.setOnClickListener(clickListener);
		rLScrollView.setPullListener(pullListener);
		rLScrollView.setSlideListener(slideListener);
	}

	private int paddingLeft = 0, paddingRight = 0;
	private boolean isTranslateAnimation = true;

	private Handler handler = new Handler() {

		private LinearLayout.LayoutParams params = null;

		public void dispatchMessage(android.os.Message msg) {

			if (msg.what == pullDown) {

				int x = msg.arg1;
				int y = msg.arg2;

				paddingLeft = x;
				paddingRight = -x;
				if (isTranslateAnimation) {

					// cnParent.setTranslationX(paddingLeft);// 水平位移也是可以的
					// 调用View的scrollTo()和scrollBy()是用于滑动View中的内容，而不是把某个View的位置进行改变
					cnParent.scrollTo(-paddingLeft, 0);// 需要是负值
					// scrollBy(int x, int y)其实是对scrollTo的包装，移动的是相当位置。
					// cnParent.scrollBy(paddingLeft, 0);

				} else {
					cnParent.setPadding(paddingLeft, 0, paddingRight, 0);
				}

			} else if (msg.what == back) {

				int x = msg.arg1;
				int y = msg.arg2;

				Log.i(tag, String.format(
						"toRight paddingLeft %s mScreenWidth %s", paddingLeft,
						mScreenWidth));

				if (paddingLeft >= (mScreenWidth / 3)) {
					anim(cnParent, paddingLeft, paddingRight);

				} else {
					if (isTranslateAnimation) {
						// cnParent.setTranslationX(0);
						cnParent.scrollTo(0, 0);
						// cnParent.scrollBy(0, 0);

					} else {
						cnParent.setPadding(0, 0, 0, 0);
					}

				}

				paddingLeft = 0;
				paddingRight = 0;

			} else if (msg.what == finish) {

			} else if (msg.what == txt) {
				mTextView.setText(getTxt());
			}

		};
	};

	int mScreenWidth = 0;
	int mScreenHeight = 0;

	private void anim(final View view, int paddingLeft, int paddingRight) {

		ObjectAnimator ob = ObjectAnimator.ofInt(view, "paddingLeft",
				paddingLeft, view.getWidth());
		ob.addUpdateListener(new ObjectAnimator.AnimatorUpdateListener() {

			public void onAnimationUpdate(ValueAnimator animation) {
				int cVal = (Integer) animation.getAnimatedValue();
				if (isTranslateAnimation) {

					// cnParent.setTranslationX(cVal);
					cnParent.scrollTo(-cVal, 0);
					// cnParent.scrollBy(cVal, 0);

				} else {
					view.setPadding(cVal, 0, -cVal, 0);
				}
			}
		});

		ob.addListener(animatorListener);
		// ob.setRepeatCount(1);//只跑一次
		// ob.setInterpolator(new AccelerateDecelerateInterpolator());
		ob.setDuration(1 * 100).start();

	}

	private void anim2(final View view, int paddingLeft, int paddingRight) {

		float fromXDelta = paddingLeft, toXDelta = mScreenWidth, fromYDelta = 0, toYDelta = 0;

		TranslateAnimation ta = new TranslateAnimation(fromXDelta, toXDelta,
				fromYDelta, toYDelta);

		ta.setAnimationListener(new TranslateAnimation.AnimationListener() {

			public void onAnimationStart(Animation animation) {

			}

			public void onAnimationRepeat(Animation animation) {

			}

			public void onAnimationEnd(Animation animation) {
				finish();
			}
		});
		ta.setDuration(100);
		// ta.start();
		// view.setAnimation(ta);
		// ta.startNow();
		// ta.start();
		// 无效
		view.startAnimation(ta);

	}

	/**
	 * 
	 * 到这里基本的用法应该也会了，但是这里之前我学的时候，一直有一个难点搞不定，就是传入的这些"alpha"，"scaleY"，
	 * "translationX"等是哪里来的？哪里定义好的呢？
	 * 后来才发现，这些值并不是view有的属性值，ObjectAnimator的工作机制并不是直接对我们传入的属性名进行操作的
	 * ，而是会去寻找这个属性名对应的get和set方法. 比如"alpha"即：
	 * 
	 * @param view
	 */
	void test2(View view) {
		AnimatorSet set = new AnimatorSet();

		set.playTogether(ObjectAnimator.ofFloat(view, "rotationX", 0, 360),
				ObjectAnimator.ofFloat(view, "rotationY", 0, 180),
				ObjectAnimator.ofFloat(view, "rotation", 0, -90),
				ObjectAnimator.ofFloat(view, "translationX", 0, 90),
				ObjectAnimator.ofFloat(view, "translationY", 0, 90),
				ObjectAnimator.ofFloat(view, "scaleX", 1, 1.5f),
				ObjectAnimator.ofFloat(view, "scaleY", 1, 0.5f),
				ObjectAnimator.ofFloat(view, "alpha", 1, 0.25f, 1));
		set.setDuration(5 * 1000).start();
	}

	void test3() {
		ValueAnimator anim;// = ValueAnimator.ofObject(evaluator, values);
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
			// boolean disallowIntercept = (y > 0);
			// rLScrollView.requestDisallowInterceptTouchEvent(disallowIntercept);
			// 失去了平滑滚动的效果
		}
	};

	public int getStatusBarHeight() {
		return Resources.getSystem().getDimensionPixelSize(
				Resources.getSystem().getIdentifier("status_bar_height",
						"dimen", "android"));
	}

	private View getT() {
		View v = LayoutInflater.from(this).inflate(R.layout.tingkao, null);
		return v;
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
