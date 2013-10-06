package com.romans.utils;

import android.os.AsyncTask;
import android.util.Log;

public class DownloadService extends AsyncTask<String, String, String> {
	
	DownloadCallback mCallBack;

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		
		Log.d("TAG", params[0]);
			return Post.Execute(params[0]);
		
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		mCallBack.updateAfterDownload(result);
		
		super.onPostExecute(result);
	}
	
	
	
	public interface DownloadCallback{
		void updateAfterDownload(String resultingInput);
	}
	
	public void setCallback(DownloadCallback c){
		mCallBack = c;
	}

}
