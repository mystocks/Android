package com.example.mynotes;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter{

	private Context context;
	private Cursor cursor;
	private LinearLayout layout = null;
	
	public MyAdapter(Context context, Cursor cursor)
	{
		this.context = context;
		this.cursor = cursor;
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return cursor.getCount();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		
		return cursor.getPosition();
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHold holder;
		if(convertView == null){
			LayoutInflater inflater = LayoutInflater.from(context);
			//layout = (LinearLayout) inflater.inflate(R.layout.cell, null);
			convertView =  inflater.inflate(R.layout.cell, null);
			holder = new ViewHold();
			holder.contenttv = (TextView) convertView.findViewById(R.string.lst_content);
			holder.timetv = (TextView) convertView.findViewById(R.string.lst_time);
			holder.imgiv = (ImageView) convertView.findViewById(R.string.lst_img);
			holder.videoiv = (ImageView) convertView.findViewById(R.string.lst_video);
			convertView.setTag(holder);
		}
		cursor.moveToPosition(position);
		String content = cursor.getString(cursor.getColumnIndex(NoteDB.CONTENT));
		String time = cursor.getString(cursor.getColumnIndex(NoteDB.TIME));
		String uri = cursor.getString(cursor.getColumnIndex(NoteDB.PATH));
		String videoPath = cursor.getString(cursor.getColumnIndex(NoteDB.VIDEO));
		
		holder = (ViewHold) convertView.getTag();
		holder.contenttv.setText(content.toString());
		holder.timetv.setText(time.toString());
		holder.imgiv.setImageBitmap(getImageThumbnail(uri, 200, 200));
		holder.videoiv.setImageBitmap(getVideoThumbnail(videoPath, 200, 200, MediaStore.Images.Thumbnails.MICRO_KIND));
		return convertView;
	}
	
	public static class ViewHold{
		TextView contenttv;
		TextView timetv;
		ImageView imgiv;
		ImageView videoiv;
	}
	
	public Bitmap getImageThumbnail(String uri, int width, int height){
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		bitmap = BitmapFactory.decodeFile(uri, options);
		options.inJustDecodeBounds = false;
		int beWidth = options.outWidth / width;
		int beHeight = options.outHeight / height;
		
		int be = 1;
		if(beWidth < beHeight){
			be = beWidth;
		}
		else{
			be = beHeight;
		}
		if(be <= 0){
			be = 1;
		}
		options.inSampleSize = be;
		bitmap = BitmapFactory.decodeFile(uri, options);
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, 
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}
	
	public Bitmap getVideoThumbnail(String uri, int width, int height, int kind){
		Bitmap bitmap = null;
		
		bitmap = ThumbnailUtils.createVideoThumbnail(uri, kind);
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
		
	}
}
