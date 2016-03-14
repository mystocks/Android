package com.example.test1;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter{

	private List<PhoneInfo> myLists;
	private Context context;
	private LinearLayout mylayout;
	
	
	public MyAdapter(List<PhoneInfo> myLists, Context context) {
		this.myLists = myLists;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return myLists.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return myLists.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//LayoutInflater inflater = LayoutInflater.from(context);
		//mylayout = (LinearLayout) inflater.inflate(R.layout.cell, null);
		//TextView nametv = (TextView) mylayout.findViewById(R.id.name);
		//TextView numtv = (TextView) mylayout.findViewById(R.id.number);
		//nametv.setText(myLists.get(position).getPhoneName());
		//numtv.setText(myLists.get(position).getPhoneNum());
		ViewHold holder;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.cell, null);
			holder = new ViewHold();
			holder.nametv = (TextView) convertView.findViewById(R.id.name);
			holder.numbertv = (TextView) convertView.findViewById(R.id.number);
			convertView.setTag(holder);
			holder.nametv.setText(myLists.get(position).getPhoneName());
			holder.numbertv.setText(myLists.get(position).getPhoneNum());
		}
		else{
			holder = (ViewHold) convertView.getTag();
			holder.nametv.setText(myLists.get(position).getPhoneName());
			holder.numbertv.setText(myLists.get(position).getPhoneNum());
		}
		return convertView;
	}

	public static class ViewHold{
		TextView nametv;
		TextView numbertv;
	}
	
}
