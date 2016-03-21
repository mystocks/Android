package com.mytest.secret;

import com.mytest.secret.activity.ActLogin;
import com.mytest.secret.activity.ActTimeLine;
import com.mytest.secret.localData.MyContacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		
		String token = config.getCatchedToken(this);
		String phoneNum =config.getCatchedPhoneNum(this);
		if(token != null && phoneNum != null){
			Intent i = new Intent(this, ActTimeLine.class);
			i.putExtra(config.KEY_TOKEN, token);
			i.putExtra(config.KEY_PHONE_NUM, phoneNum);
			startActivity(i);
		}
		else{
			startActivity(new Intent(this, ActLogin.class));
			System.out.println(MyContacts.getContactsJSONString(this));
		}
		finish();
	}
}
