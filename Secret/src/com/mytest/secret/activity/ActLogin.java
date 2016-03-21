package com.mytest.secret.activity;

import com.mytest.secret.R;
import com.mytest.secret.config;
import com.mytest.secret.net.GetCode;
import com.mytest.secret.net.Login;
import com.mytest.secret.tools.Md5;

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

public class ActLogin extends Activity {

	public EditText etPhone = null;
	public Button btnGetNum = null;
	public EditText etCode = null;
	public Button btnLogin = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		init();
	}

	public void init() {
		etPhone = (EditText) findViewById(R.id.login_phone);
		btnGetNum = (Button) findViewById(R.id.login_btn_getNum);
		etCode =(EditText) findViewById(R.id.login_et_code);
		btnLogin =(Button) findViewById(R.id.login_btn_login);
		
		btnGetNum.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (TextUtils.isEmpty(etPhone.getText())) {
					Toast.makeText(ActLogin.this, R.string.login_phoneNotnull_tips, Toast.LENGTH_LONG).show();
					return;
				}
				final ProgressDialog pd = ProgressDialog.show(ActLogin.this, getResources().getString(R.string.Net_connecting_title), getResources().getString(R.string.Net_connecting_tips));
				new GetCode().GetCode(etPhone.getText().toString(), new GetCode.SucessCallBack() {
					
					@Override
					public void onSucess() {
						// TODO Auto-generated method stub
						pd.dismiss();
						Toast.makeText(ActLogin.this, R.string.login_getCode_Sucess, Toast.LENGTH_LONG).show();;
					}
				}, new GetCode.FailedCallBack() {
					
					@Override
					public void onFail() {
						// TODO Auto-generated method stub
						pd.dismiss();
						Toast.makeText(ActLogin.this, R.string.login_getCode_Failed, Toast.LENGTH_LONG).show();;
					}
				});
			}
		});
		
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (TextUtils.isEmpty(etPhone.getText())) {
					Toast.makeText(ActLogin.this, R.string.login_phoneNotnull_tips, Toast.LENGTH_LONG).show();
					return;
				}
				
				if (TextUtils.isEmpty(etCode.getText())) {
					Toast.makeText(ActLogin.this, R.string.login_codeNotnull_tips, Toast.LENGTH_LONG).show();
					return;
				}
				
				final ProgressDialog pd =ProgressDialog.show(ActLogin.this, getResources().getString(R.string.login_loginning_title), getResources().getString(R.string.login_loginning_tips));
				new Login(Md5.getMD5(etPhone.getText().toString()), etCode.getText().toString(), new Login.SucessCallBack() {
					
					@Override
					public void OnSuccess(String token) {
						// TODO Auto-generated method stub
						pd.dismiss();
						config.setCatchedToken(ActLogin.this, token);
						config.setCatchedPhoneNum(ActLogin.this, etPhone.getText().toString());
						Intent i =new Intent(ActLogin.this,ActTimeLine.class);
						i.putExtra(config.KEY_TOKEN, token);
						i.putExtra(config.KEY_PHONE_NUM, etPhone.getText().toString());
						startActivity(i);
						finish();
					}
				}, new Login.FailCallBack() {
					
					@Override
					public void OnFail() {
						// TODO Auto-generated method stub
						pd.dismiss();
						Toast.makeText(ActLogin.this, R.string.login_loginning_error, Toast.LENGTH_LONG).show();
						return;
					}
				});
			}
		});
	}

}
