package com.example.tingkao;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageMapsActivity extends BaseActivity {

	ImageView imageView;
	FrameLayout frameLayout;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_maps);
		imageView = (ImageView) findViewById(R.id.imageView1);
		Picasso.with(this).load(R.drawable.map_pic).into(imageView);

		// width="1920" height="1080"
		// <area shape="circle" coords="689,560,166" href="#" /> x,y,radius
		frameLayout = (FrameLayout) findViewById(R.id.RootFrameLayout);

	}

}
