package com.romans.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.google.android.gms.maps.model.LatLng;

import android.os.AsyncTask;

public class DistanceCatcher extends AsyncTask<LatLng, String, String> {

	@Override
	protected String doInBackground(LatLng... params) {
		// TODO Auto-generated method stub
		return getDocument(params[0], params[1]);
	}
	
	
	
	
	
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}





	public String getDocument(LatLng a, LatLng b) {
	//	LatLng a = new LatLng(57.393785,21.565054);
	//	LatLng b = new LatLng(57.392675,21.573652);
        String url = "http://maps.googleapis.com/maps/api/directions/json?" 
                + "origin=" + a.latitude + "," + a.longitude  
                + "&destination=" + b.latitude + "," + b.longitude 
                + "&sensor=false&units=metric";

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpPost httpPost = new HttpPost(url);
            HttpResponse response = httpClient.execute(httpPost, localContext);
            InputStream in = response.getEntity().getContent();
            
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
    		StringBuilder sb = new StringBuilder();
    		
    		String line;
    		while ((line = br.readLine()) != null)
    			sb.append(line);
    		br.close();
    		return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	public interface DistanceNotifier{
		
	}

}
