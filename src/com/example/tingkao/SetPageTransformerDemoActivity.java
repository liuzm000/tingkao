package com.example.tingkao;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.example.tingkao.animation.DepthPageTransformer;
import com.squareup.picasso.Picasso;

public class SetPageTransformerDemoActivity extends BaseActivity {

	private ViewPager mViewPager;

	private List<ImageView> mImageViews = new ArrayList<ImageView>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_page_transformer_demo);
		initData();
		get();
	}

	void get() {

		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
		
		 mViewPager.setPageTransformer(true, new DepthPageTransformer());
//		 mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
		
		mViewPager.setAdapter(new PagerAdapter() {

			public Object instantiateItem(ViewGroup container, int position) {
				
				Picasso.with(ctx).load(mImgIds[position]).into(mImageViews.get(position));
				container.addView(mImageViews.get(position));
				
				return mImageViews.get(position);
			}

			public void destroyItem(ViewGroup container, int position,
					Object object) {

				container.removeView(mImageViews.get(position));
			}

			public boolean isViewFromObject(View view, Object object) {
				return view == object;
			}

			public int getCount() {
				return mImgIds.length;
			}
		});
	}

	private void initData() {
		for (String imgId : mImgIds) {
			ImageView imageView = new ImageView(this);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			mImageViews.add(imageView);
		}
	}
}
