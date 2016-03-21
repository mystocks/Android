package com.mytest.secret.net;

import org.json.JSONException;
import org.json.JSONObject;

import com.mytest.secret.config;

public class GetCode {

	public String GetCode(String phone, final SucessCallBack sucessCallBack,
			final FailedCallBack failedCallBack) {

		new NetConnection(config.SERVER_URL, HttpMethod.Post,
				new NetConnection.SucessCallback() {

					@Override
					public void onSucessed(String result) {
						// TODO Auto-generated method stub
						try {
							JSONObject jsonObj = new JSONObject(result);
							
							switch (jsonObj.getInt(config.KEY_STATUS)) {
							case config.RESULT_STATUS_SUCESS:
								if (sucessCallBack != null) {
									sucessCallBack.onSucess();
								}
								break;

							default:
								if (failedCallBack != null) {
									failedCallBack.onFail();
								}
								break;
							}
						} catch (JSONException e) {
							// TODO: handle exception
							e.printStackTrace();
							if (failedCallBack != null) {
								failedCallBack.onFail();
							}
						}
					}
				}, new NetConnection.FailCallback() {

					@Override
					public void onFailed() {
						// TODO Auto-generated method stub
						if (failedCallBack != null) {
							failedCallBack.onFail();
						}

					}
				}, config.KEY_ACTION
				, config.ACTION_GET_CODE,
				config.KEY_PHONE_NUM, phone);
		return null;
	}

	public static interface SucessCallBack {
		void onSucess();
	}

	public static interface FailedCallBack {
		void onFail();
	}
}
