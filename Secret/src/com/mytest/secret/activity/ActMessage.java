package com.mytest.secret.activity;

import java.util.List;

import com.mytest.secret.R;
import com.mytest.secret.config;
import com.mytest.secret.net.Comment;
import com.mytest.secret.net.PubComment;
import com.mytest.secret.net.getComment;

import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ActMessage extends ListActivity{

	private String phone_md5,msg,msgId,token;
	private TextView tv_content;
	private EditText et_comment;
	private Button btn_PubComment;
	ActMessageCommentListAdapter msgCommentAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message);
		
		Intent i=getIntent();
		phone_md5 =i.getStringExtra(config.KEY_PHONE_MD5);
		msg =i.getStringExtra(config.KEY_MSG);
		msgId =i.getStringExtra(config.KEY_MSGID);
		token =i.getStringExtra(config.KEY_TOKEN);
		init_pubcommentAction();
		init_listcomment();
	}
	private void init_pubcommentAction()
	{
		et_comment = (EditText) findViewById(R.id.message_Et_comment);
		btn_PubComment = (Button) findViewById(R.id.message_btn_AddContent);
		btn_PubComment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (TextUtils.isEmpty(et_comment.getText())) {
					Toast.makeText(ActMessage.this, R.string.pubcomment_is_null, Toast.LENGTH_SHORT).show();;
					return;
				}
				final ProgressDialog pd = ProgressDialog.show(ActMessage.this, getResources().getText(R.string.pubcomment_title), getResources().getText(R.string.pubcomment_tips));
				new PubComment(phone_md5, token, et_comment.getText().toString(), msgId, new PubComment.SucessCallback() {
					
					@Override
					public void OnSucess() {
						// TODO Auto-generated method stub
						pd.dismiss();
						Toast.makeText(ActMessage.this, R.string.pubcomment_success, Toast.LENGTH_LONG).show();
						et_comment.setText("");
						updateComment();
					}
				}, new PubComment.FailedCallback() {
					
					@Override
					public void OnFailed(int errorCode) {
						// TODO Auto-generated method stub
						pd.dismiss();
						if (errorCode == config.RESULT_STATUS_INVALID_TOKEN) {
							//如果是无效token，需要重新登录
							startActivity(new Intent(ActMessage.this, ActLogin.class));
							//当前界面finish
						}
						else {
							Toast.makeText(ActMessage.this, R.string.pubcomment_filed,Toast.LENGTH_LONG).show();
						}
					}
				});
			}
		});
	}
	/**
	 * 
	 */
	private void init_listcomment(){
		tv_content =(TextView) findViewById(R.id.message_Text_Content);
		tv_content.setText(msg);
		msgCommentAdapter =new ActMessageCommentListAdapter(this);
		setListAdapter(msgCommentAdapter);
		
		updateComment();
	}
	private void updateComment() {
		final ProgressDialog pd =ProgressDialog.show(this, getResources().getText(R.string.Net_connecting_title), getResources().getText(R.string.Net_connecting_tips));
		new getComment(phone_md5, token, msgId, 1, 20, new getComment.SucessCallback() {
			
			@Override
			public void OnSucess(String msgId, int page, int perpage,
					List<Comment> comments) {
				// TODO Auto-generated method stub
				pd.dismiss();
				msgCommentAdapter.ClearAll();
				msgCommentAdapter.AddAll(comments);
				
			}
		}, new getComment.FailedCallback() {
			
			@Override
			public void OnFailed(int errorCode) {
				// TODO Auto-generated method stub
				pd.dismiss();
				if (errorCode == config.RESULT_STATUS_INVALID_TOKEN) {
					startActivity(new Intent(ActMessage.this, ActLogin.class));
					finish();
				}
				else {
					Toast.makeText(ActMessage.this, R.string.message_failed_to_getcomments, Toast.LENGTH_LONG).show();;
				}
			}
		});
	}
}
