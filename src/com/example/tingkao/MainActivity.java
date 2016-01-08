package com.example.tingkao;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tingkao.widget.RLScrollView;
import com.example.tingkao.widget.RLScrollView.OnScrollChangedListener;

@SuppressLint("NewApi")
public class MainActivity extends BaseActivity {

	String tag = "MainActivity";

	private FrameLayout content;

	private RLScrollView rLScrollView;
	private TextView textView,mTextView;
	int[] location = new int[2];
	int[] location2 = new int[2];
	private ImageView floatView, imageView1;
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById();
		setListener();
		
		int i = 0x7FFFFFFF ;
		
		Log.i(tag,i+"");

		// View view = getActionBar().getCustomView();
	}

	private int pullDown = 1, back = 2, finish = 3,txt=4;
	private Handler handler = new Handler() {

		private LinearLayout.LayoutParams params = null;

		public void dispatchMessage(android.os.Message msg) {
			if (msg.what == pullDown) {
			} else if (msg.what == back) {

			} else if (msg.what == finish) {

			}else if(msg.what == txt){
				mTextView.setText(getTxt());
			}

		};
	};
	private void findViewById() {
		rLScrollView = (RLScrollView) findViewById(R.id.rLScrollView);
		
		textView = (TextView) findViewById(R.id.textView);
		
		mTextView = (TextView) findViewById(R.id.mTextView);
		handler.sendEmptyMessage(txt);
		
		content = (FrameLayout) findViewById(android.R.id.content);

		Log.i(tag,
				String.format("content.getChildCount() = %s",
						content.getChildCount()));

		// content.addView(v, content.getChildCount());

		Log.i(tag,
				String.format("getStatusBarHeight = %s", getStatusBarHeight()));

		imageView1 = (ImageView) findViewById(R.id.imageView1);

	}

	private void setListener() {
		rLScrollView.setOnScrollListener(onScrollChangedListener);
		textView.setOnClickListener(clickListener);
	}

	private OnClickListener clickListener = new OnClickListener() {

		public void onClick(View v) {
			if (v.getId() == R.id.textView) {
				//startActivity(ListActivity.class);
			}
		}
	};


	private OnScrollChangedListener onScrollChangedListener = new OnScrollChangedListener() {

		private boolean VISIBLE = false;
		private boolean GONE = false;
		private boolean addView = false;

		public void onScrollChanged(int x, int y, int oldX, int oldY) {

			// 行不通  
			Log.i(tag, String.format("imageView VISIBLE %s", (imageView1.getVisibility()==View.VISIBLE)));
			
			Log.i(tag, String.format("int %s, int %s, int %s, int %s", x, y,
					oldX, oldY));

			textView.getLocationInWindow(location);

			Log.i(tag, String.format("getLocationInWindow x %s ,y %s",
					location[0], location[1]));

			textView.getLocationOnScreen(location2);

			Log.i(tag, String.format("getLocationOnScreen x %s ,y %s",
					location2[0], location2[1]));

			// → y, ↓ x ;

			Log.i(tag,
					String.format("textView.getHeight() = %s ",
							textView.getHeight()));

			if ((location[1] + textView.getHeight()) == y) {

				Log.i(tag, String.format(
						"(location[1] + textView.getHeight()) %s ",
						(location[1] + textView.getHeight())));

			}

			if (y == location[1]) {
				Log.i("tinkao", "tinkao");
			}

			if (textView.getVisibility() == View.INVISIBLE) {
				Log.i("tinkao", "INVISIBLE");
			}

			if ((location[1] - textView.getHeight() - getStatusBarHeight()) <= 0) {

				Log.i("tinkao", String.format("getLocationInWindow x %s ,y %s",
						location[0], location[1]));

				// 一个view不能有同级别的两个parent
				if (content.getChildAt(1) == null) {

					int width = ViewGroup.LayoutParams.MATCH_PARENT;
					int height = textView.getHeight();

					FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
							width, height);
					content.addView(getT(), 1, params);

				} else {

					content.getChildAt(1).setVisibility(View.VISIBLE);

				}

			} else {

				if (content.getChildAt(1) != null) {
					content.getChildAt(1).setVisibility(View.GONE);
					Log.i("tinkao", "INVISIBLE");
				}

			}
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

}
