package com.mytest.secret.net;

import org.json.JSONException;
import org.json.JSONObject;

import com.mytest.secret.config;

public class PublishMessage {
	public PublishMessage(String PhoneMd5, String token, String msg, final SucessCallback sucessCallback, final FailedCallback failedCallback){
		new NetConnection(config.SERVER_URL, HttpMethod.Post, new NetConnection.SucessCallback() {
			
			@Override
			public void onSucessed(String result) {
				try {
					JSONObject jObject =new JSONObject(result);
					switch (jObject.getInt(config.KEY_STATUS)) {
					case config.RESULT_STATUS_SUCESS:
						if (sucessCallback !=null) {
							sucessCallback.OnSucess();
						}
						break;
					case config.RESULT_STATUS_INVALID_TOKEN:
						if (failedCallback !=null) {
							failedCallback.OnFailed(config.RESULT_STATUS_INVALID_TOKEN);
						}
						break;
					default:
						if (failedCallback !=null) {
							failedCallback.OnFailed(config.RESULT_STATUS_FAILED);
						}
						break;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					if (failedCallback !=null) {
						failedCallback.OnFailed(config.RESULT_STATUS_FAILED);
					}
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
		}, 	config.KEY_ACTION, config.ACTION_PUBMESSAGE, 
			config.KEY_PHONE_MD5, PhoneMd5, 
			config.KEY_TOKEN, token,
			config.KEY_MSG, msg);
	}
	public static interface SucessCallback{
		void OnSucess();
	}
	public static interface FailedCallback{
		void OnFailed(int errorCode);
	}
}
