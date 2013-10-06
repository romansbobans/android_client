package com.romans.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public final class HelperMethods {

	public static String getField(final JSONObject item, final String name) {
		try {
			return item.getString(name);
		} catch (final JSONException e) {
			// Cannot find the field
		}
		return "";
	}
	
	public static JSONArray getJSONArray(final JSONObject item, final String name){
		try { 
			return item.getJSONArray(name);
		}
		catch (final JSONException e){
			
		}
		return null;
		
	}

	public static float parseStringToFloat(final String name) {
		try {
			return Float.parseFloat(name);
		} catch (final NumberFormatException e) {
			// Field is "" or corrupt
			return 0;
		}
	}
	
	public static int paseStringToInt(final String name) {
		try {
			return Integer.parseInt(name);
		} catch (final NumberFormatException e) {
			// Field is "" or corrupt
			return 0;
		}
		
	}
	
	public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter(); 
        if (listAdapter == null) {
            // pre-condition
            return;
        }
//Shows only last 10 comments
        int k = 0;
        if (listView.getCount() > 10){
        	k = listView.getCount() - 10;
        }
        int totalHeight = 0;
        for (int i = k; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
	
	
	public static LatLng _getLocation(Object o) {
		double lat = 0, lon = 0;
		// Get the location manager
		LocationManager locationManager = (LocationManager) o;
		Criteria criteria = new Criteria();
		String bestProvider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(bestProvider);

		LocationListener loc_listener = new LocationListener() {
			public void onLocationChanged(Location l) {
			}

			public void onProviderEnabled(String p) {
			}

			public void onProviderDisabled(String p) {
			}

			public void onStatusChanged(String p, int status, Bundle extras) {
			}
		};
		locationManager
				.requestLocationUpdates(bestProvider, 0, 0, loc_listener);
		location = locationManager.getLastKnownLocation(bestProvider);

		try {
			lat = location.getLatitude();
			lon = location.getLongitude();
		} catch (NullPointerException e) {
			lat = -1.0;
			lon = -1.0;
		}
		return new LatLng(lat, lon);
		// Log.e("TAG", lat + ", " + lon);
	}

}
