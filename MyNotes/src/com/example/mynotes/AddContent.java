package com.example.mynotes;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Video;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.VideoView;

public class AddContent extends Activity implements OnClickListener{

	private int iflags = Common.unknowFlags;
	private Button saveBtn,cancelBtn;
	private ImageView img_View;
	private EditText et_Text;
	private VideoView video_View;
	private NoteDB noteDb;
	private SQLiteDatabase dbWriter;
	private File img_path = null;
	private File video_path = null;
	private int iImgReturn = 1, iVideoReturn = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addcontent);
		iflags = getIntent().getIntExtra("flags", Common.unknowFlags);
		initView();
	}
	private void initView(){
		saveBtn = (Button) findViewById(R.string.e_save);
		cancelBtn = (Button) findViewById(R.string.e_cancel);
		img_View = (ImageView) findViewById(R.string.e_img);
		et_Text = (EditText) findViewById(R.string.e_Text);
		video_View = (VideoView) findViewById(R.string.e_video);
		
		if(iflags == Common.imgFlags){
			img_View.setVisibility(View.VISIBLE);
			video_View.setVisibility(View.GONE);
			
			Intent iimg = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			img_path = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/" + getTime() + ".jpg");
			System.out.println(img_path.getAbsolutePath());
			iimg.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(img_path));
			startActivityForResult(iimg, iImgReturn);
		}
		else if(iflags == Common.textFlags){
			img_View.setVisibility(View.GONE);
			video_View.setVisibility(View.GONE);
		}else if(iflags == Common.videoFlags){
			img_View.setVisibility(View.GONE);
			video_View.setVisibility(View.VISIBLE);
			
			Intent ivideo = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
			video_path = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/" + getTime() + ".mp4");
			System.out.println(video_path.getAbsolutePath());
			ivideo.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(video_path));
			startActivityForResult(ivideo, iVideoReturn);
		}
		saveBtn.setOnClickListener(this);
		cancelBtn.setOnClickListener(this);
		
		noteDb = new NoteDB(this);
		dbWriter = noteDb.getWritableDatabase();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.string.e_save:
			addDB();
			finish();
			break;
		case R.string.e_cancel:
			finish();
			break;
		default:
				break;
		}
	}
	
	private void addDB(){
		ContentValues cv = new ContentValues();
		cv.put(NoteDB.CONTENT, et_Text.getText().toString());
		cv.put(NoteDB.TIME, getTime());
		if(iflags == Common.imgFlags){
			cv.put(NoteDB.PATH, img_path+"");
		}
		if(iflags == Common.videoFlags){
			cv.put(NoteDB.VIDEO, video_path+"");
		}
		
		dbWriter.insert(NoteDB.TABLE_NAME, null, cv);
	}
	
	private String getTime(){
		SimpleDateFormat format =  new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
		Date date = new Date();
		String str = format.format(date);
		return str;
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == iImgReturn){
			Bitmap img_bitmap =  BitmapFactory.decodeFile(img_path.getAbsolutePath());
			img_View.setImageBitmap(img_bitmap);
		}
		if(requestCode == iVideoReturn){
			video_View.setVideoURI(Uri.fromFile(video_path));
			video_View.start();
		}
	}
	
}
