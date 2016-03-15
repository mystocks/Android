package com.example.mynotes;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

public class select extends Activity implements OnClickListener{
	private Button s_back, s_delete;
	private ImageView s_img;
	private VideoView s_video;
	private TextView s_comment, s_time;
	
	private NoteDB notesDb;
	private SQLiteDatabase dbWriter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select);
		
		initData();
		
	}
	private void initData(){
		s_back = (Button) findViewById(R.string.s_back);
		s_delete = (Button) findViewById(R.string.s_delete);
		s_img = (ImageView) findViewById(R.string.s_image);
		s_video = (VideoView) findViewById(R.string.s_video);
		s_comment = (TextView) findViewById(R.string.s_Text);
		s_time = (TextView) findViewById(R.string.s_time);
		
		s_back.setOnClickListener(this);
		s_delete.setOnClickListener(this);
		
		notesDb = new NoteDB(this);
		dbWriter = notesDb.getWritableDatabase();
		
		if(getIntent().getStringExtra(NoteDB.PATH) == null){
			//Í¼Æ¬Â·¾¶Îª¿Õ£¬²»ÏÔÊ¾Í¼Æ¬
			s_img.setVisibility(View.GONE);
		}
		else{
			s_img.setVisibility(View.VISIBLE);
			Bitmap bitmap = BitmapFactory.decodeFile(getIntent().getStringExtra(NoteDB.PATH));
			s_img.setImageBitmap(bitmap);
		}
		if(getIntent().getStringExtra(NoteDB.VIDEO) == null){
			s_video.setVisibility(View.GONE);
		}
		else
		{
			s_video.setVisibility(View.VISIBLE);
			s_video.setVideoURI(Uri.parse(getIntent().getStringExtra(NoteDB.VIDEO)));
			s_video.start();
		}
		
		s_comment.setText(getIntent().getStringExtra(NoteDB.CONTENT));
		s_time.setText(getIntent().getStringExtra(NoteDB.TIME));
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.string.s_delete:
			deleteDB();
			finish();
			break;
		case R.string.s_back:
			finish();
			break;
		default:
				break;
		}
	}
	private void deleteDB(){
		dbWriter.delete(NoteDB.TABLE_NAME, NoteDB.ID+"="+getIntent().getIntExtra(NoteDB.ID, 0), null);
	}
}
