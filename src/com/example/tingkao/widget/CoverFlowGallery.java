package com.example.tingkao.widget;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;

public class CoverFlowGallery extends Gallery {
	
	String tag = "CoverFlowGallery";

	@Override
	protected boolean getChildStaticTransformation(View child, Transformation t) {
		Log.i(tag, "====================");
		t.clear();
		t.setTransformationType(Transformation.TYPE_MATRIX);
		final float offset = calculateOffsetOfCenter(child);
		transformViewRoom(child, t, offset);
		return true;
	}

	// 获取父控件中心点 X 的位置
	protected int getCenterOfCoverflow() {
		return ((getWidth() - getPaddingLeft() - getPaddingRight()) >> 1)
				+ getPaddingLeft();
	}

	// 获取 child 中心点 X 的位置
	protected int getCenterOfView(View view) {
		return view.getLeft() + (view.getWidth() >> 1);
	}

	// 计算 child 偏离 父控件中心的 offset 值， -1 <= offset <= 1
	protected float calculateOffsetOfCenter(View view) {
		final int pCenter = getCenterOfCoverflow();
		final int cCenter = getCenterOfView(view);

		float offset = (cCenter - pCenter) / (pCenter * 1.0f);
		offset = Math.min(offset, 1.0f);
		offset = Math.max(offset, -1.0f);

		return offset;
	}

	Camera mCamera = new Camera();

	void transformViewRoom(View child, Transformation t, float race) {

		mCamera.save();
		final Matrix matrix = t.getMatrix();
		final int halfHeight = child.getMeasuredHeight() >> 1;
		final int halfWidth = child.getMeasuredWidth() >> 1;

		// 平移 X、Y、Z 轴已达到立体效果
		mCamera.translate(-race * 50, 0.0f, Math.abs(race) * 200);
		// 也可设置旋转效果
		mCamera.getMatrix(matrix);
		// 以 child 的中心点变换
		matrix.preTranslate(-halfWidth, -halfHeight);
		matrix.postTranslate(halfWidth, halfHeight);
		mCamera.restore();
		// 设置 alpha 变换
		t.setAlpha(1 - Math.abs(race));
	}


	public CoverFlowGallery(Context context) {
		super(context);
	}

	public CoverFlowGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CoverFlowGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

}
