package com.example.tingkao.widget;

import android.animation.TypeEvaluator;
import android.annotation.SuppressLint;
import android.view.View;

@SuppressLint("NewApi")
public class MyTypeEvaluator implements TypeEvaluator<Integer> {

	private View view;

	public MyTypeEvaluator() {
	}

	public MyTypeEvaluator(View view) {
		this.view = view;
	}

	public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
		return null;
	}

}
