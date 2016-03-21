package com.mytest.secret.activity;

import java.util.ArrayList;
import java.util.List;

import com.mytest.secret.R;
import com.mytest.secret.net.Comment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ActMessageCommentListAdapter extends BaseAdapter{

	Context context =null;
	List<Comment> comments =new ArrayList<Comment>();
	
	public ActMessageCommentListAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return comments.size();
	}

	@Override
	public Comment getItem(int position) {
		// TODO Auto-generated method stub
		return comments.get(position);
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
			convertView = LayoutInflater.from(context).inflate(R.layout.message_list_cell, null);
			List_cell lc =new List_cell((TextView) convertView.findViewById(R.id.message_comment_text));
			convertView.setTag(lc);
		}
		List_cell lc =(List_cell) convertView.getTag();
		TextView tv =lc.getView();
		tv.setText(comments.get(position).getContent());
		return convertView;
	}
	
	private static class List_cell
	{
		private TextView tv;
		public List_cell(TextView view){
			this.tv =view;
		}
		public TextView getView()
		{
			return tv;
		}
		public void setView(TextView tv) {
			this.tv =tv;
		}
	}
	public void AddAll(List<Comment> comments){
		this.comments.addAll(comments);
		notifyDataSetChanged();
	}
	public void ClearAll() {
		this.comments.clear();
	}
}
