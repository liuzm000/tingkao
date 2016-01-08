package com.example.tingkao;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class BaseActivity extends Activity {

	int mScreenWidth = 0;
	int mScreenHeight = 0;
	
	 String[] mImgIds = {
			 "http://p.qq181.com/cms/13052/2013052018501536915.jpg",
			 "http://imglf4.ph.126.net/2_W0ENBeLGvJ6apXRHwwvQ==/3064418071466796984.jpg",
			 	"http://www.askcici.com/wp-content/uploads/2010/05/72.jpg",
				"http://tupian.qqjay.com/u/2012/0718/2_72952_7.jpg",
				"http://pic.baike.soso.com/p/20120526/20120526171339-353210352.jpg",
				"http://attimg.dospy.com/img/day_110514/20110514_88ef9efa10909a72aa3fy424v2JjbvwW.jpg",
				"http://imgsrc.baidu.com/forum/pic/item/359b033b5bb5c9ea901ee402d539b6003bf3b366.jpg",
				"http://p.qq181.com/cms/1211/2012112721574159142.jpg" };

	Context ctx = this;

	public void startActivity(Class cls) {
		startActivity(new Intent(this, cls));
		// overridePendingTransition(android.R.anim.slide_in_left, 0);
	}

	public String getTxt() {
		InputStream is = null;
		try {
			is = getResources().getAssets().open("content.txt");
			String txt = IOUtils.toString(is);
			return txt;
		} catch (Exception e) {

		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception e2) {

				}
			}
		}
		return "";
	}

}
