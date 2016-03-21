package com.mytest.secret.net;

public class Comment {
	private String content;
	private String phone_md5;
	public Comment(String content, String phoneMd5){
		this.content = content;
		this.phone_md5 =phoneMd5;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPhone_md5() {
		return phone_md5;
	}
	public void setPhone_md5(String phone_md5) {
		this.phone_md5 = phone_md5;
	}
	
}
