package com.mytest.secret.net;

import org.json.JSONException;
import org.json.JSONObject;

import com.mytest.secret.config;

public class UploadContacts {
	
	public UploadContacts(String phoneMd5, String token, String contacts,final SucessCallback sucessCallback,final FailedCallback failedCallback)
	{
		new NetConnection(config.SERVER_URL, HttpMethod.Post, new NetConnection.SucessCallback() {
			
			@Override
			public void onSucessed(String result) {
				// TODO Auto-generated method stub
				try {
					JSONObject jObject = new JSONObject(result);
					switch (jObject.getInt(config.KEY_STATUS)) {
					case config.RESULT_STATUS_SUCESS:
						if (sucessCallback != null) {
							sucessCallback.OnSucess();
						}
						break;
					case config.RESULT_STATUS_INVALID_TOKEN:
						if (failedCallback != null) {
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
					if (failedCallback != null) {
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
		}, config.KEY_ACTION, config.ACTION_UPLOAD_CONTACTS, config.KEY_PHONE_MD5, phoneMd5, config.KEY_TOKEN, token, config.KEY_CONTACTS, contacts);
	}
	public static interface SucessCallback
	{
		void OnSucess();
	}
	public static interface FailedCallback{
		void OnFailed(int errorCode);
	}
}
