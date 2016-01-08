package com.example.tingkao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.example.tingkao.adapter.ModelBean;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.squareup.picasso.Picasso;

public class GalleryActivity extends BaseActivity {

	private List<ModelBean> list;
	String tag = "GalleryActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);
		t();
	}

	void t() {
		Gallery gallery = (Gallery) findViewById(R.id.gallery);
		gallery.setAdapter(new ImageAdapter(this));
		gallery.setSelection(1);
//		gallery.setSpacing(-50);
		gallery.setUnselectedAlpha(0.7f);
		
		gallery.setCallbackDuringFling(true);
		
		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long id) {
				Log.i(tag, String.format("int position %s, long id %s", position,id));
				
			     AnimatorSet animatorSet = new AnimatorSet();
	                ObjectAnimator imgScaleUpYAnim = ObjectAnimator.ofFloat(v, "scaleY", 0.7f, 1f);
	                imgScaleUpYAnim.setDuration(600);
	                //imgScaleUpYAnim.setInterpolator(DECCELERATE_INTERPOLATOR);
	                ObjectAnimator imgScaleUpXAnim = ObjectAnimator.ofFloat(v, "scaleX", 0.7f, 1f);
	                imgScaleUpXAnim.setDuration(600);
	                animatorSet.playTogether(imgScaleUpYAnim,imgScaleUpXAnim);
	                animatorSet.start();
	     
	                for(int i = 0;i < parent.getChildCount();i++){
	                    if(parent.getChildAt(i) != v){
	                        View s = parent.getChildAt(i);
	                        ObjectAnimator imgScaleDownYAnim = ObjectAnimator.ofFloat(s, "scaleY", 1f, 0.7f);
	                        imgScaleDownYAnim.setDuration(100);
	                        //imgScaleUpYAnim.setInterpolator(DECCELERATE_INTERPOLATOR);
	                        ObjectAnimator imgScaleDownXAnim = ObjectAnimator.ofFloat(s, "scaleX", 1f, 0.7f);
	                        imgScaleDownXAnim.setDuration(100);                            
	                        animatorSet.playTogether(imgScaleDownXAnim,imgScaleDownYAnim);
	                        animatorSet.start();
	                    }
	                }
				
			}

			public void onNothingSelected(AdapterView<?> parent) {
				
			}
			
		});
	}

	class ImageAdapter extends BaseAdapter {
		private Context context;
		// Í¼Æ¬Ô´Êý×é
		private Integer[] imageInteger = { R.drawable.hezixiansheng,
				R.drawable.hezixiansheng, R.drawable.hezixiansheng,
				R.drawable.hezixiansheng, R.drawable.hezixiansheng,
				R.drawable.hezixiansheng, R.drawable.hezixiansheng,
				R.drawable.hezixiansheng };

		public ImageAdapter(Context c) {
			context = c;
		}

		@Override
		public int getCount() {
			return imageInteger.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = new ImageView(context);

			// imageView.setImageResource(imageInteger[position]);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			ViewHelper.setScaleX(imageView,0.7f);
			ViewHelper.setScaleY(imageView,0.7f);
			imageView.setLayoutParams(new Gallery.LayoutParams(500, 400));
			Picasso.with(ctx).load(mImgIds[position]).into(imageView);
			// Picasso.with(ctx).load(imageInteger[position]).into(imageView);

			return imageView;
		}
	}
}
