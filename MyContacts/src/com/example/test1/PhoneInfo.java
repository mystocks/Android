package com.example.test1;

public class PhoneInfo {
	private String PhoneName;
	private String PhoneNum;
	public PhoneInfo(String Name, String Num)
	{
		setPhoneName(Name);
		setPhoneNum(Num);
	}
	public String getPhoneName() {
		return PhoneName;
	}
	public void setPhoneName(String phoneName) {
		PhoneName = phoneName;
	}
	public String getPhoneNum() {
		return PhoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		PhoneNum = phoneNum;
	}
	
}
