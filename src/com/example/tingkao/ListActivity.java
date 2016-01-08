package com.example.tingkao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tingkao.adapter.BaseListViewAdpter;

public class ListActivity extends BaseActivity {

	String tag = "ListActivity";
	private int gray = 0;
	private ListView listView;
	private BaseListViewAdpter simpleAdapter;
	private FrameLayout content;
	private String current = "";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		findViewById();
	}

	private void findViewById() {

		gray = getResources().getColor(R.color.gray_light_1);

		content = (FrameLayout) findViewById(android.R.id.content);
		listView = (ListView) findViewById(R.id.listView);

		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

		for (int i = 1; i <= 12; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", i + " 月");
			map.put("type", "mouth");
			map.put("collection", i + " 月");
			dataList.add(map);
			for (int j = 1; j <= 30; j++) {
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("name", j + " 日");
				map2.put("type", "day");
				map2.put("collection", i + " 月");
				dataList.add(map2);
			}
		}
		simpleAdapter = new BaseListViewAdpter(this, dataList);
		listView.setAdapter(simpleAdapter);

		listView.setOnScrollListener(onScrollListener);

		listView.setOnItemClickListener(itemClickListener);

	}

	OnItemClickListener itemClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Map<String, Object> map = (Map<String, Object>) parent
					.getItemAtPosition(position);

			String name = map.get("name") + "";
			String type = map.get("type") + "";
			String collection = map.get("collection") + "";

			if (type.equals("mouth")) {
				return;
			}

			if (position == 1) {
				startActivity(FloatViewActivity.class);
			} else if (position == 2) {
				startActivity(ScaleActivity.class);
			} else if (position == 3) {
				startActivity(SlideActivity.class);
			} else if (position == 4) {
				startActivity(KuGouSlideActivity.class);
			} else if (position == 5) {
				startActivity(RotationActivity.class);
			} else if (position == 6) {
				startActivity(LocusAnimationActivity.class);
			} else if (position == 7) {
				startActivity(SetPageTransformerDemoActivity.class);
			} else if (position == 8) {
				startActivity(RecyclerViewActivity.class);
			} else if (position == 9) {
				startActivity(ImageMapsActivity.class);
			} else if (position == 10) {
				startActivity(CRecyclerViewActivity.class);
			} else if (position == 11) {
				startActivity(ViewGroupReflectionExample.class);
			}else if (position == 12) {
				startActivity(GalleryActivity.class);
			}else if (position == 13) {
				startActivity(MainActivity.class);
			}

			// RecyclerViewActivity
			// ImageMapsActivity
			// ViewGroupReflectionExample
		}
	};

	private OnScrollListener onScrollListener = new OnScrollListener() {

		public void onScrollStateChanged(AbsListView view, int scrollState) {

		}

		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {

			Log.i(tag,
					String.format(
							"firstVisibleItem %s , visibleItemCount %s, totalItemCount %s",
							firstVisibleItem, visibleItemCount, totalItemCount));

			Map<String, Object> map = (Map<String, Object>) listView
					.getItemAtPosition(firstVisibleItem + 1);

			String name = map.get("name") + "";
			String type = map.get("type") + "";
			String collection = map.get("collection") + "";

			Log.i(tag, String.format("name %s , type %s ", name, type));

			// if (type.equals("mouth")) {

			if (content.getChildAt(1) == null) {

				int width = ViewGroup.LayoutParams.MATCH_PARENT;
				int height = ViewGroup.LayoutParams.WRAP_CONTENT;
				View v = getT();

				TextView tv = (TextView) v.findViewById(R.id.textView);
				tv.setText(collection);
				tv.setPadding(40, 20, 40, 20);
				tv.setBackgroundColor(gray);

				FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
						width, height);

				content.addView(v, 1, params);

			} else {
				if (!collection.equals(current)) {
					TextView tv = (TextView) content.getChildAt(1)
							.findViewById(R.id.textView);
					tv.setText(collection);
					Log.i("collection",
							String.format("tv.setText(%s);", collection));
				}
				current = collection;

			}

			// } else {

			// }
		}
	};

	private View getT() {
		return LayoutInflater.from(this).inflate(R.layout.list_item, null);
	}
}
