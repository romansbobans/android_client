package com.romans.utils;


import java.net.UnknownHostException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;


public class Post
{
	private static final String BASE_URL = "http://mongo.tdlbox.com/rombak.php";

	public static String Execute(String request)
	{
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(BASE_URL);

		httppost.setHeader("Accept", "application/json");
		httppost.setHeader("Content-type", "application/json");

			StringEntity jsonString;
			try {
				jsonString = new StringEntity(request);
			

			Log.d("POST", String.valueOf(request));

			httppost.setEntity(jsonString);

			HttpResponse response = httpclient.execute(httppost);

			if (response!=null)
			{
				String result = EntityUtils.toString(response.getEntity());

				Log.d("TAG", result);

				return result;
			}

			}
			catch (UnknownHostException ex){
				return null;
			}	catch (Exception e) {
			
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	

		return null;
	}
	
}
