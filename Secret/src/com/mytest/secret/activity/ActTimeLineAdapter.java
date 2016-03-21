package com.mytest.secret.activity;

import java.util.ArrayList;
import java.util.List;

import com.mytest.secret.R;
import com.mytest.secret.net.Message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ActTimeLineAdapter extends BaseAdapter {

	private Context context = null;
	private List<Message> data = new ArrayList<Message>();

	public ActTimeLineAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Message getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.timeline_list_cell, null);
			ListCell lc = new ListCell(
					(TextView) convertView
							.findViewById(R.id.timeline_cell_tvText));
			convertView.setTag(lc);
		}
		ListCell lc = (ListCell) convertView.getTag();
		Message msg = getItem(position);
		lc.tvText.setText(msg.getMsg());
		return convertView;
	}

	public Context getContext() {
		return this.context;
	}

	public void AddData(List<Message> dataTemp) {
		data.addAll(dataTemp);
		notifyDataSetChanged();
	}

	public void ClearData() {
		data.clear();
		notifyDataSetChanged();
	}

	private static class ListCell {
		private TextView tvText;

		public ListCell(TextView tv) {
			// TODO Auto-generated constructor stub
			tvText = tv;
		}

		public TextView getTextView() {
			return tvText;
		}
	}
}
