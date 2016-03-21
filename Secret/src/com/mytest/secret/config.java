package com.mytest.secret;

import android.R.integer;
import android.content.Context;
import android.content.SharedPreferences.Editor;

public class config {

	public static final String SERVER_URL = "http://192.168.1.103:8080/testSecretServer/api.jsp";
	public static final String APP_ID = "com.mytest.secret";
	public static final String CHARSET ="utf-8";
	
	public static final String KEY_ACTION ="action";
	public static final String KEY_TOKEN = "token";
	public static final String KEY_PHONE_NUM ="phone";
	public static final String KEY_PHONE_MD5 ="phone_md5";
	public static final String KEY_STATUS ="status";
	public static final String KEY_CODE ="code";
	public static final String KEY_CONTACTS = "contacts";
	public static final String KEY_PAGE = "page";
	public static final String KEY_PERPAGE = "perpage";
	public static final String KEY_TIMELINE = "timeline";
	public static final String KEY_MSGID = "msgid";
	public static final String KEY_MSG = "msg";
	public static final String KEY_CONTENT = "content";
	public static final String KEY_COMMENTS = "comments";
	
	public static final String ACTION_GET_CODE= "send_pass";
	public static final String ACTION_LOGIN ="login";
	public static final String ACTION_UPLOAD_CONTACTS = "upload_contacts";
	public static final String ACTION_TIMELINE = "timeline";
	public static final String ACTION_GETCOMMENT ="get_comment";
	public static final String ACTION_PUBCOMMENT = "pub_comment";
	public static final String ACTION_PUBMESSAGE = "pub_message";
	
	
	public static final int RESULT_STATUS_SUCESS =1;
	public static final int RESULT_STATUS_FAILED =0;
	public static final int RESULT_STATUS_INVALID_TOKEN =2;
	
	public static final int ACTIVITY_RESULT_NEED_REFRESH = 1;
	
	
	public static String getCatchedToken(Context context){
		return context.getSharedPreferences(APP_ID, context.MODE_PRIVATE).getString(KEY_TOKEN, null);
	}
	
	public static void setCatchedToken(Context context, String token){
		Editor e = context.getSharedPreferences(APP_ID, context.MODE_PRIVATE).edit();
		e.putString(KEY_TOKEN, token);
		e.commit();
	}
	
	public static String getCatchedPhoneNum(Context context){
		return context.getSharedPreferences(APP_ID, context.MODE_PRIVATE).getString(KEY_PHONE_NUM, null);
	}
	
	public static void setCatchedPhoneNum(Context context, String phonenum){
		Editor e = context.getSharedPreferences(APP_ID, context.MODE_PRIVATE).edit();
		e.putString(KEY_PHONE_NUM, phonenum);
		e.commit();
	}
}
