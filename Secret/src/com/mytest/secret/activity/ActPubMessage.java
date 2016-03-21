package com.mytest.secret.activity;

import com.mytest.secret.R;
import com.mytest.secret.config;
import com.mytest.secret.net.PublishMessage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActPubMessage extends Activity{

	EditText et_message;
	Button btn_pub_message;
	String phone_md5, token;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pubmessage);
		Intent i =getIntent();
		phone_md5 = i.getStringExtra(config.KEY_PHONE_MD5);
		token =i.getStringExtra(config.KEY_TOKEN);
		init();
	}
	private void init()
	{
		et_message = (EditText) findViewById(R.id.pubmessage_et_comment);
		btn_pub_message =(Button) findViewById(R.id.pubmessage_btn_publish);
		btn_pub_message.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (TextUtils.isEmpty(et_message.getText())) {
					Toast.makeText(ActPubMessage.this, R.string.publish_content_notnull, Toast.LENGTH_LONG).show();
					return;
				}
				final ProgressDialog pd =ProgressDialog.show(ActPubMessage.this, getResources().getText(R.string.pub_message_publishing_title), getResources().getText(R.string.pub_message_publishing_tips));
				new PublishMessage(phone_md5, token, et_message.getText().toString(), new PublishMessage.SucessCallback() {
					
					@Override
					public void OnSucess() {
						// TODO Auto-generated method stub
						pd.dismiss();
						setResult(config.ACTIVITY_RESULT_NEED_REFRESH);
						Toast.makeText(ActPubMessage.this, R.string.pub_message_sucess, Toast.LENGTH_LONG).show();
						finish();
					}
				}, new PublishMessage.FailedCallback() {
					
					@Override
					public void OnFailed(int errorCode) {
						// TODO Auto-generated method stub
						pd.dismiss();
						if (errorCode == config.RESULT_STATUS_INVALID_TOKEN) {
							//token错误，重新登录
							startActivity(new Intent(ActPubMessage.this, ActLogin.class));
							finish();
						}
						else {
							Toast.makeText(ActPubMessage.this, R.string.pub_message_failed, Toast.LENGTH_SHORT).show();
						}
					}
				});
			}
		});
	}
}
