package com.mytest.secret.net;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;

import com.mytest.secret.config;

public class getComment {
	public getComment(String phone_md5,String token,String msgId,int page,int perpage,final SucessCallback sucessCallback,final FailedCallback failedCallback){
		new NetConnection(config.SERVER_URL, HttpMethod.Post, new NetConnection.SucessCallback() {
			
			@Override
			public void onSucessed(String result) {
				// TODO Auto-generated method stub
				try {
					JSONObject jObject = new JSONObject(result);
					switch (jObject.getInt(config.KEY_STATUS)) {
					case config.RESULT_STATUS_SUCESS:
						
						List<Comment> comments =new ArrayList<Comment>();
						JSONArray jsonArray =jObject.getJSONArray(config.KEY_COMMENTS);
						JSONObject commentsObject;
						for (int i = 0; i < jsonArray.length(); i++) {
							commentsObject = jsonArray.getJSONObject(i);
							comments.add(new Comment(commentsObject.getString(config.KEY_CONTENT), commentsObject.getString(config.KEY_PHONE_MD5)));
						}
						if (sucessCallback !=null) {
							sucessCallback.OnSucess(jObject.getString(config.KEY_MSGID),jObject.getInt(config.KEY_PAGE),
													jObject.getInt(config.KEY_PERPAGE),comments);
						}
						break;

					case config.RESULT_STATUS_INVALID_TOKEN:
						if (failedCallback!=null) {
							failedCallback.OnFailed(config.RESULT_STATUS_INVALID_TOKEN);
						}
						break;
					default:
						if (failedCallback != null) {
							failedCallback.OnFailed(config.RESULT_STATUS_FAILED);
						}
						break;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, new NetConnection.FailCallback() {
			
			@Override
			public void onFailed() {
				// TODO Auto-generated method stub
				if (failedCallback != null) {
					failedCallback.OnFailed(config.RESULT_STATUS_FAILED);
				}
			}
		},  config.KEY_ACTION, config.ACTION_GETCOMMENT,
			config.KEY_PHONE_MD5,phone_md5,
			config.KEY_TOKEN,token,
			config.KEY_MSGID,msgId,
			config.KEY_PAGE,page+"",
			config.KEY_PERPAGE,perpage+"");
	}
	public static interface SucessCallback{
		void OnSucess(String msgId,int page,int perpage,List<Comment> comments);
	}
	public static interface FailedCallback{
		void OnFailed(int errorCode);
	}
}
