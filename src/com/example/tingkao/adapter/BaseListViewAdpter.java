package com.example.tingkao.adapter;

import java.util.List;
import java.util.Map;

import com.example.tingkao.R;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class BaseListViewAdpter extends SimpleAdapter {

	private class ViewHolder {
		TextView textView;
	}

	private void findViewById(final View convertView) {
		holder.textView = (TextView) convertView.findViewById(R.id.textView);
	}

	public void initValue(final Map<String, Object> map) {
		if (map == null) {
			return;
		}

		String name = map.get("name") + "";
		if (!TextUtils.isEmpty(name)) {
			holder.textView.setText(name);
		} else {
			holder.textView.setText("");
		}

		String type = map.get("type") + "";

		if (type.equals("mouth")) {
			holder.textView.setPadding(40, 20, 40, 20);
			holder.textView.setBackgroundColor(gray);
		} else {
			holder.textView.setPadding(60, 40, 60, 40);
			holder.textView.setBackgroundColor(white);
		}
	}

	private void setListener(final Map<String, Object> map) {
	}

	private static int resource = R.layout.list_item;
	private static String[] from = { "textView" };
	private static int[] to = { R.id.textView };

	private LayoutInflater mInflater;
	private List<Map<String, Object>> data;
	private Context mContext;
	private ViewHolder holder = null;

	private int gray = 0;
	private int white = 0;

	public BaseListViewAdpter(Context context, List<Map<String, Object>> data) {
		super(context, data, resource, from, to);
		this.data = data;
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
	}

	public int getCount() {
		return data.size();
	}

	public Map<String, Object> getItem(int position) {
		return data.get(position);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Map<String, Object> map = getItem(position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(resource, null);
			findViewById(convertView);
			convertView.setTag(holder);

			gray = holder.textView.getResources().getColor(R.color.gray_light_1);
			white = holder.textView.getResources().getColor(R.color.white);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		initValue(map);
		setListener(map);
		return convertView;
	}

	public List<Map<String, Object>> getData() {
		return data;
	}

}
