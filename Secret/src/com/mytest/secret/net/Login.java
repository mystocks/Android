package com.mytest.secret.net;

import org.json.JSONException;
import org.json.JSONObject;

import com.mytest.secret.config;

public class Login {
	
	public Login(String phone_md5,String code, final SucessCallBack sucessCallBack, final FailCallBack failCallBack) {
		// TODO Auto-generated constructor stub
		System.out.println("In Login,phone_md5:" + phone_md5);
		new NetConnection(config.SERVER_URL, HttpMethod.Post, new NetConnection.SucessCallback() {
			
			@Override
			public void onSucessed(String result) {
				// TODO Auto-generated method stub
				try {
					JSONObject obj = new JSONObject(result);
					switch (obj.getInt(config.KEY_STATUS)) {
					case config.RESULT_STATUS_SUCESS:
						if (sucessCallBack != null) {
							sucessCallBack.OnSuccess(obj.getString(config.KEY_TOKEN));
						}
						break;

					default:
						if (failCallBack != null) {
							failCallBack.OnFail();
						}
						break;
					}
				} catch (JSONException e) {
					// TODO: handle exception
					e.printStackTrace();
					if (failCallBack != null) {
						failCallBack.OnFail();
					}
				}
			}
		}, new NetConnection.FailCallback() {
			
			@Override
			public void onFailed() {
				// TODO Auto-generated method stub
				if (failCallBack != null) {
					failCallBack.OnFail();
				}
			}
		}, config.KEY_ACTION, config.ACTION_LOGIN, config.KEY_PHONE_MD5, phone_md5, config.KEY_CODE, code);
	}
	
	public static interface SucessCallBack{
		void OnSuccess(String token);
	}
	public static interface FailCallBack{
		void OnFail();
	}
}
