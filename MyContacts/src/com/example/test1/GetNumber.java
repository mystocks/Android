package com.example.test1;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Phone;

public class GetNumber {
	public static List<PhoneInfo> Mylists = new ArrayList<PhoneInfo>();
	
	public static String getNumber(Context context)
	{
		System.out.println("Enter getNumber");
		Cursor cursor = context.getContentResolver().query(Phone.CONTENT_URI, null, null, null, null);
		String PhoneNumber = "";
		String PhoneName = "";
		boolean b = cursor.moveToNext();
		while(b)
		{
			PhoneNumber = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
			PhoneName = cursor.getString(cursor.getColumnIndex(Phone.DISPLAY_NAME));
			PhoneInfo phoneInfo = new PhoneInfo(PhoneName, PhoneNumber);
			Mylists.add(phoneInfo);
			System.out.println(PhoneName + PhoneNumber);
			
			b = cursor.moveToNext();
		}
		System.out.println("Leaver getNumber777");
		return null;
	}
}
