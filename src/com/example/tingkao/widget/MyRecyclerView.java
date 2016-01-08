package com.example.tingkao.widget;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Transformation;

import com.nineoldandroids.view.ViewHelper;

public class MyRecyclerView extends RecyclerView {

	String tag = "MyRecyclerView";

	protected boolean getChildStaticTransformation2(View child, Transformation t) {

		Log.i(tag, "====================");

		ViewHelper.setAlpha(child, 1);

		ViewHelper.setScaleX(child, 1.5f);
		ViewHelper.setScaleY(child, 1.5f);

		return true;
	}

	@Override
	protected boolean getChildStaticTransformation(View child, Transformation t) {
		Log.i(tag, "====================");
		t.clear();
		t.setTransformationType(Transformation.TYPE_MATRIX);
		final float offset = calculateOffsetOfCenter(child);
		transformViewRoom(child, t, offset);
		return true;
	}

	// ��ȡ���ؼ����ĵ� X ��λ��
	protected int getCenterOfCoverflow() {
		return ((getWidth() - getPaddingLeft() - getPaddingRight()) >> 1)
				+ getPaddingLeft();
	}

	// ��ȡ child ���ĵ� X ��λ��
	protected int getCenterOfView(View view) {
		return view.getLeft() + (view.getWidth() >> 1);
	}

	// ���� child ƫ�� ���ؼ����ĵ� offset ֵ�� -1 <= offset <= 1
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

		// ƽ�� X��Y��Z ���Ѵﵽ����Ч��
		mCamera.translate(-race * 50, 0.0f, Math.abs(race) * 200);
		// Ҳ��������תЧ��
		mCamera.getMatrix(matrix);
		// �� child �����ĵ�任
		matrix.preTranslate(-halfWidth, -halfHeight);
		matrix.postTranslate(halfWidth, halfHeight);
		mCamera.restore();
		// ���� alpha �任
		t.setAlpha(1 - Math.abs(race));
	}

	public MyRecyclerView(Context context) {
		super(context);
	}

	public MyRecyclerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyRecyclerView(Context arg0, AttributeSet arg1, int arg2) {
		super(arg0, arg1, arg2);
	}

}
