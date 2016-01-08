package com.example.tingkao;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;

import com.example.tingkao.animation.CircleAnimation;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

public class LocusAnimationActivity extends BaseActivity {

	/** Button */
	private Button mButton;
	private ImageView mImageView;

	int[] location = new int[2];
	int mR = 0;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_locus_animation);
		DisplayMetrics dm = new DisplayMetrics();

		getWindowManager().getDefaultDisplay().getMetrics(dm);
		mScreenWidth = dm.widthPixels;// 获取屏幕分辨率宽度
		mScreenHeight = dm.heightPixels;

		mR = mScreenWidth / 4;// 半径 为 宽 的 1/4
		// 圆形坐标 x,y
		location[0] = mScreenWidth / 2;
		location[1] = mScreenHeight / 2;

		findViewById();
		setListener();
	}

	private void findViewById() {
		mImageView = (ImageView) findViewById(R.id.mImageView);
		/** Button */
		mButton = (Button) findViewById(R.id.mButton);

		// mImageView.setTranslationX(location[0]);
		// mImageView.setTranslationY(location[1]);

		ViewHelper.setTranslationX(mImageView, location[0]);
		ViewHelper.setTranslationY(mImageView, location[1]);
	}

	void yuan() {
		// x²+y²=r²
		int[] location = { 10, 10 };
		double r = 10f;
		double x = location[0] + r * Math.sin(r);// x 坐标
		double y = location[1] - r * Math.cos(r); // y 坐标

	}

	void a(View v) {

		AnimatorSet set = new AnimatorSet();
		// translationX , translationY 是相对于父容器的位置貌似
		set.playTogether(
				ObjectAnimator.ofFloat(v, "translationX", 0, location[0]),
				ObjectAnimator.ofFloat(v, "translationY", 0, location[1]));
		set.setDuration(2000).start();
	}

	void circle() {
		Animation anim = new CircleAnimation(mImageView, 200);
		anim.setDuration(3000);
		mImageView.startAnimation(anim);
	}

	private void setListener() {
		mButton.setOnClickListener(clickListener);
	}

	private OnClickListener clickListener = new OnClickListener() {
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.mButton:// Button
				// a(mImageView);

				circle();

				break;
			default:
			}
		}
	};
}
