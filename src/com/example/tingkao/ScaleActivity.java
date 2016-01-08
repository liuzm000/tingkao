package com.example.tingkao;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tingkao.widget.RLScrollView;
import com.example.tingkao.widget.RLScrollView.OnPullListener;
import com.example.tingkao.widget.RLScrollView.OnScrollChangedListener;
import com.nineoldandroids.view.ViewHelper;

@SuppressLint("NewApi")
public class ScaleActivity extends BaseActivity {

	String tag = "ScaleActivity";

	private FrameLayout content;

	private RLScrollView rLScrollView;
	private TextView textView,mTextView;
	int[] location = new int[2];
	int[] location2 = new int[2];
	private ImageView floatView, imageView1;
	int imageWidth = 0, imageHeight = 0;

	private final float offset_radio = 1.9f;

	private int pullDown = 1, back = 2,txt=4;
	
	Matrix matrix ;

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

		
		
	}

	private void setListener() {
		rLScrollView.setOnScrollListener(onScrollChangedListener);
		textView.setOnClickListener(clickListener);
		rLScrollView.setPullListener(pullListener);
	}

	private Handler handler = new Handler() {

		private LinearLayout.LayoutParams params = null;

		public void dispatchMessage(android.os.Message msg) {

			if (msg.what == pullDown) {

				int x = msg.arg1;
				int y = msg.arg2;

				if (params == null) {
					params = (android.widget.LinearLayout.LayoutParams) imageView1
							.getLayoutParams();
				}
				params.width = x;
				params.height = y;
				imageView1.setLayoutParams(params);
				
				imageView1.setScaleType(ScaleType.CENTER_CROP);
				// imageView1.setScaleX(scaleX)
				
			} else if (msg.what == back) {

				int x = msg.arg1;
				int y = msg.arg2;

				Log.i(tag, String.format("back x %s y %s", x, y));

				if (params == null) {
					params = (android.widget.LinearLayout.LayoutParams) imageView1
							.getLayoutParams();
				}
				params.width = x;
				params.height = y;
				imageView1.setLayoutParams(params);

				// setDuration(long durationMillis)：设置动画持续事件（单位：毫秒）
				// setFillAfter(boolean
				// fillAfter)：如果fillAfter设为true，则动画执行后，控件将停留在动画结束的状态
				// setFillBefore(boolean
				// fillBefore)：如果fillBefore设为true，则动画执行后，控件将回到动画开始的状态
				// setStartOffset(long startOffset)：设置动画执行之前等待的时间（单位：毫秒）
				// setRepeatCount(int repeatCount)：设置动画重复的次数
				// setInterpolator(Interpolator i)：设置动画的变化速度
				// setInterpolator(new AccelerateDecelerateInterpolator())：先加速，后减速
				//
				// setInterpolator(new AccelerateInterpolator())：加速
				//
				// setInterpolator(new DecelerateInterpolator())：减速
				//
				// setInterpolator(new
				// CycleInterpolator())：动画循环播放特定次数，速率改变沿着正弦曲线
				//
				// setIn terpolator(new LinearInterpolator())：匀速

				// 初始化
				ScaleAnimation scaleAnimation = new ScaleAnimation(x, 1.0f, y,
						1.0f);
				// 设置动画时间
				scaleAnimation.setDuration(500);
				scaleAnimation.setInterpolator(new DecelerateInterpolator());
				// imageView1.startAnimation(scaleAnimation);
			}else if(msg.what == txt){
				mTextView.setText(getTxt());
			}

		};
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

	private Message msg = null;

	private OnPullListener pullListener = new OnPullListener() {

		// 完美解决

		public void onStart(float x, float y) {
			Log.i(tag, String.format("onStart x %s y %s", x, y));
			if (imageWidth == 0) {
				imageWidth = imageView1.getWidth();
			}
			if (imageHeight == 0) {
				imageHeight = imageView1.getHeight();
			}
		}

		public void onPullUp(float x, float y, float dx, float dy) {
			Log.i(tag, String.format("onPullUp x %s y %s", dx, dy));
		}

		public void onPullDown(float x, float y, float dx, float dy) {
			Log.i(tag, String.format("onPullDown x %s y %s", dx, dy));
			if (imageWidth == 0) {
				imageWidth = imageView1.getWidth();
			}
			if (imageHeight == 0) {
				imageHeight = imageView1.getHeight();
			}
			msg = handler.obtainMessage();
			msg.arg1 = (int) (dy / offset_radio) + imageWidth;
			msg.arg2 = (int) (dy / offset_radio) + imageHeight;
			msg.what = pullDown;
			handler.sendMessage(msg);

		}

		public void onFinish(float x, float y) {
			Log.i(tag, String.format("onFinish x %s y %s", x, y));
			msg = handler.obtainMessage();
			msg.arg1 = imageWidth;
			msg.arg2 = imageHeight;
			msg.what = back;
			handler.sendMessage(msg);

		}
	};
}
