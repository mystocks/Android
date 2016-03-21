package com.mytest.secret.net;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mytest.secret.config;

public class Timeline {
	public Timeline(String phone_md5, String token,int page,int num_perpage,final SucessCallback sucessCallback,final FailedCallback failedCallback) {
		new NetConnection(config.SERVER_URL, HttpMethod.Post, new NetConnection.SucessCallback() {
			
			@Override
			public void onSucessed(String result) {
				// TODO Auto-generated method stub
				try {
					JSONObject jObject = new JSONObject(result);
					
					switch (jObject.getInt(config.KEY_STATUS)) {
					case config.RESULT_STATUS_SUCESS:
						if (sucessCallback != null) {
							
							List<Message> msgs =new ArrayList<Message>();
							JSONArray msgJsonArray = jObject.getJSONArray(config.KEY_TIMELINE);
							JSONObject msgObj;
							for (int i = 0; i < msgJsonArray.length(); i++) {
								msgObj =msgJsonArray.getJSONObject(i);
								msgs.add(new Message(msgObj.getString(config.KEY_MSGID), 
													 msgObj.getString(config.KEY_MSG), 
													 msgObj.getString(config.KEY_PHONE_MD5)));
							}
							sucessCallback.OnSucess(jObject.getInt(config.KEY_PAGE), jObject.getInt(config.KEY_PERPAGE), msgs);
						}
						break;
					case config.RESULT_STATUS_INVALID_TOKEN:
						if (failedCallback != null) {
							failedCallback.OnFailed(config.RESULT_STATUS_INVALID_TOKEN);
						}
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
		}, config.KEY_ACTION, config.ACTION_TIMELINE, 
		config.KEY_PHONE_MD5,phone_md5,
		config.KEY_TOKEN,token,
		config.KEY_PAGE,page+"",
		config.KEY_PERPAGE,num_perpage+"");
	}
	public static interface SucessCallback{
		void OnSucess(int page,int perpage,List<Message> timeline);
	}
	public static interface FailedCallback{
		void OnFailed(int errorCode);
	}
}
