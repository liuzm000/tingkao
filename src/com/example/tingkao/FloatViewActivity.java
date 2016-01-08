package com.example.tingkao;

import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class FloatViewActivity extends BaseActivity {

	private ImageView floatView;
	private FrameLayout content;
	private boolean eable = false;
	float x = 0;
	float y = 0;
	String tag = "FloatViewActivity";

	int mScreenWidth = 0;// dm.widthPixels;// 获取屏幕分辨率宽度
	int mScreenHeight = 0;// dm.heightPixels;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_float_view);
		content = (FrameLayout) findViewById(android.R.id.content);
		// floatView = (ImageView) LayoutInflater.from(this).inflate(
		// R.layout.float_view, null);

		floatView = new ImageView(this);
		floatView.setImageResource(R.drawable.add_sub_p);
		floatView.setId(R.id.floatView);

		int width = ViewGroup.LayoutParams.WRAP_CONTENT;
		int height = ViewGroup.LayoutParams.WRAP_CONTENT;
		params = new FrameLayout.LayoutParams(width, height);
		content.addView(floatView, 1, params);
		floatView.setOnTouchListener(touchListener);

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		mScreenWidth = dm.widthPixels;// 获取屏幕分辨率宽度
		mScreenHeight = dm.heightPixels;

	}

	public boolean onTouchEvent(MotionEvent event) {
		if (eable) {
			if (event.getAction() == MotionEvent.ACTION_MOVE) {
				x = event.getX();
				y = event.getY();
				handler.sendEmptyMessage(10);
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				eable = false;
			}
			return false;
		}
		return super.onTouchEvent(event);
	};

	private FrameLayout.LayoutParams params = null;

	Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			// floatView.setBottom((int) y);
			// floatView.setRight((int) x);
			// floatView.setTop((int) y);
			// floatView.setLeft((int) x);
			if (params == null) {
				// params = (LayoutParams) floatView.getLayoutParams();
			}
			int left = ((int) x) - 200;
			int top = ((int) y) - 200;
			if (left < 0) {
				left = 0;
			}
			if (top < 0) {
				top = 0;
			}

			if (left + 200 > mScreenWidth) {// 没有用？？
				left = mScreenWidth;
			}

			if (top + 200 > mScreenHeight) {
				top = mScreenHeight;
			}

			params.setMargins(left, top, 0, 0);
			floatView.setLayoutParams(params);
			Log.i(tag, String.format("x %s, y %s,", x, y));

		};
	};

	OnTouchListener touchListener = new OnTouchListener() {

		public boolean onTouch(View v, MotionEvent event) {

			if (v.getId() == R.id.floatView) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {

					eable = true;

					// float x = event.getX();
					// float y = event.getY();
					// floatView.setTop((int) y);
					// floatView.setLeft((int) x);

				} else if (event.getAction() == MotionEvent.ACTION_UP) {

					eable = false;
				}

			}

			return false;
		}
	};
}
