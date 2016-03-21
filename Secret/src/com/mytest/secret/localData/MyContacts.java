package com.mytest.secret.localData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mytest.secret.config;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Phone;

public class MyContacts {

	public static String getContactsJSONString(Context context)
	{
		JSONArray jsonArray = new JSONArray();
		JSONObject jObject =null;
		Cursor cursor = context.getContentResolver().query(Phone.CONTENT_URI, null, null, null, null);
		String phoneNum;
		boolean b = cursor.moveToNext();
		while(b)
		{
			phoneNum =cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
			jObject = new JSONObject();
			try {
				jObject.put(config.KEY_PHONE_MD5, phoneNum);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jsonArray.put(jObject);
			b = cursor.moveToNext();
		}
		return jsonArray.toString();
	}
}
