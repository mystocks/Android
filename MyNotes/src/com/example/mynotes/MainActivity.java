package com.example.mynotes;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Settings.System;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity implements OnClickListener{
	
	private Button textBtn, imgBtn, videoBtn;
	private ListView lv;
	private Intent iTo;
	private NoteDB noteDb;
	private SQLiteDatabase dbReader;
	private MyAdapter myAdapter;
	private Cursor cursor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}
	
	private void initView(){
		textBtn = (Button) findViewById(R.string.text);
		imgBtn = (Button) findViewById(R.string.img);
		videoBtn = (Button) findViewById(R.string.video);
		textBtn.setOnClickListener(this);
		imgBtn.setOnClickListener(this);
		videoBtn.setOnClickListener(this);
		
		lv = (ListView) findViewById(R.string.list);
		lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, select.class);
				cursor.moveToPosition(position);
				i.putExtra(NoteDB.ID, cursor.getInt(cursor.getColumnIndex(NoteDB.ID)));
				i.putExtra(NoteDB.CONTENT, cursor.getString(cursor.getColumnIndex(NoteDB.CONTENT)));
				i.putExtra(NoteDB.PATH, cursor.getString(cursor.getColumnIndex(NoteDB.PATH)));
				i.putExtra(NoteDB.TIME, cursor.getString(cursor.getColumnIndex(NoteDB.TIME)));
				i.putExtra(NoteDB.VIDEO, cursor.getString(cursor.getColumnIndex(NoteDB.VIDEO)));
				startActivity(i);
			}
			
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		iTo = new Intent(this, AddContent.class);

		switch(v.getId()){
		case R.string.text:
			iTo.putExtra("flags", Common.textFlags);
			startActivity(iTo);
			break;
		case R.string.video:
			iTo.putExtra("flags", Common.videoFlags);
			startActivity(iTo);
			break;
		case R.string.img:
			iTo.putExtra("flags", Common.imgFlags);
			startActivity(iTo);
			break;
		default:
			break;
		}
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		selectDB();
	}

	private void selectDB(){
		noteDb = new NoteDB(this);
		dbReader = noteDb.getReadableDatabase();
		cursor = dbReader.query(NoteDB.TABLE_NAME, null, null, null, null, null, null);
		myAdapter = new MyAdapter(this, cursor);
		lv.setAdapter(myAdapter);
	}
}
