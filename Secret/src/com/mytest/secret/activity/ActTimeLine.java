package com.mytest.secret.activity;

import java.util.List;

import org.json.JSONArray;

import com.mytest.secret.R;
import com.mytest.secret.config;
import com.mytest.secret.localData.MyContacts;
import com.mytest.secret.net.Message;
import com.mytest.secret.net.PublishMessage;
import com.mytest.secret.net.Timeline;
import com.mytest.secret.net.UploadContacts;
import com.mytest.secret.tools.Md5;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class ActTimeLine extends ListActivity{

	public String phoneNum;
	public String token;
	ActTimeLineAdapter adapter= null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timeline);
		
		adapter= new ActTimeLineAdapter(this);
		setListAdapter(adapter);
		
		phoneNum =getIntent().getStringExtra(config.KEY_PHONE_NUM);
		token =getIntent().getStringExtra(config.KEY_TOKEN);
		final ProgressDialog pd =ProgressDialog.show(this, getResources().getString(R.string.Net_connecting_title), getResources().getString(R.string.Net_connecting_tips));
		new UploadContacts(Md5.getMD5(phoneNum), token, MyContacts.getContactsJSONString(this), new UploadContacts.SucessCallback() {
			
			@Override
			public void OnSucess() {
				// TODO Auto-generated method stub
				pd.dismiss();
				LoadMessage();
			}
		}, new UploadContacts.FailedCallback() {
			
			@Override
			public void OnFailed(int errorCode) {
				// TODO Auto-generated method stub
				pd.dismiss();
				startActivity(new Intent(ActTimeLine.this, ActLogin.class));
				finish();
			}
		});
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Message msg =adapter.getItem(position);
		Intent i=new Intent(this,ActMessage.class);
		i.putExtra(config.KEY_MSGID, msg.getMsgId());
		i.putExtra(config.KEY_MSG, msg.getMsg());
		i.putExtra(config.KEY_PHONE_MD5, msg.getPhone_md5());
		i.putExtra(config.KEY_TOKEN, token);
		startActivity(i);
	}

	public void LoadMessage(){
		final ProgressDialog pd =ProgressDialog.show(this, getResources().getString(R.string.Net_connecting_title), getResources().getString(R.string.Net_connecting_tips));
		System.out.println(">>>>>>>LoadMessage...");
		new Timeline(phoneNum, token, 1, 20, new Timeline.SucessCallback() {

			@Override
			public void OnSucess(int page, int perpage, List<Message> timeline) {
				// TODO Auto-generated method stub
				pd.dismiss();
				//adapter.ClearData();
				adapter.AddData(timeline);
			}
		}, new Timeline.FailedCallback() {
			
			@Override
			public void OnFailed(int errorCode) {
				// TODO Auto-generated method stub
				pd.dismiss();
				if(errorCode == config.RESULT_STATUS_FAILED){
					Toast.makeText(ActTimeLine.this, R.string.time_failed_to_upload_data, Toast.LENGTH_LONG).show();
				}
				else if (errorCode == config.RESULT_STATUS_INVALID_TOKEN) {
					finish();
					startActivity(new Intent(ActTimeLine.this, ActLogin.class));
				}
			}
		});
	}
	//增加一个方法，重写窗口标题绘制方法，用于增加一个添加联系人的方法
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		//return super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.menu_pub_message, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		//这里的Id是xml中新分配的，而不是menu本身的Id
		case R.id.menu_pub_message:
			Intent i = new Intent(ActTimeLine.this, ActPubMessage.class);
			i.putExtra(config.KEY_PHONE_NUM, phoneNum);
			i.putExtra(config.KEY_TOKEN, token);
			startActivityForResult(i, 0);
			//如果不finish，那么安卓点击返回的时候，还会返回到message界面，如果finish调，点击返回，就退出了
			//finish();
			break;

		default:
			break;
		}
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		//System.out.println("Enter onActivityResult");
		switch (resultCode) {
		case config.ACTIVITY_RESULT_NEED_REFRESH:
			LoadMessage();
			break;

		default:
			break;
		}
	}
	
	
}
