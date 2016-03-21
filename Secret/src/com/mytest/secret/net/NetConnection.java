package com.mytest.secret.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.mytest.secret.config;

import android.graphics.Bitmap.Config;
import android.os.AsyncTask;

public class NetConnection {
	public NetConnection(final String url, final HttpMethod method,
			final SucessCallback sucessCallback,
			final FailCallback failCallback, final String... kvs) {
		// TODO Auto-generated constructor stub
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				StringBuffer paramBuffer = new StringBuffer();
				for (int i = 0; i < kvs.length; i+=2) {
					paramBuffer.append(kvs[i]).append("=").append(kvs[i + 1])
							.append("&");
				}
				try {
					URLConnection uc;

					switch (method) {
					case Post:
						uc = new URL(url).openConnection();
						uc.setDoOutput(true);
						BufferedWriter bw = new BufferedWriter(
								new OutputStreamWriter(uc.getOutputStream(),
										config.CHARSET));
						bw.write(paramBuffer.toString());
						bw.flush();
						break;
					default:
						uc = new URL(url + "?" + paramBuffer.toString())
								.openConnection();
						break;
					}

					System.out.println("Request Url:" + uc.getURL());
					System.out.println("Request data:" + paramBuffer);
					BufferedReader br = new BufferedReader(
							new InputStreamReader(uc.getInputStream(),
									config.CHARSET));
					String line = null;
					StringBuffer result = new StringBuffer();
					while ((line = br.readLine()) != null) {
						result.append(line);
					}
					System.out.println("Result:" + result);
					return result.toString();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub\
				if (result != null) {
					if (sucessCallback != null) {
						sucessCallback.onSucessed(result);
					}
				} else {
					if (failCallback != null) {
						failCallback.onFailed();
					}
				}
				super.onPostExecute(result);
			}
		}.execute();
	}

	public static interface SucessCallback {
		void onSucessed(String result);
	}

	public static interface FailCallback {
		void onFailed();
	}
}