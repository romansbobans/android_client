package com.romans.utils;

import com.google.android.gms.maps.model.LatLng;

public class ChosenObject {
	//By default lv language
	private static int languageIndex = 2;
	
	private static RetrievedObject selectedItem;
	
	private static LatLng myLocation;

	public static LatLng getMyLocation() {
		return myLocation;
	}

	public static void setMyLocation(LatLng myLocation) {
		ChosenObject.myLocation = myLocation;
	}

	public static int getLanguageIndex() {
		return languageIndex;
	}

	public static void setLanguageIndex(int languageIndex) {
		ChosenObject.languageIndex = languageIndex;
	}

	public static RetrievedObject getSelectedItem() {
		return selectedItem;
	}

	public static void setSelectedItem(RetrievedObject selectedItem) {
		ChosenObject.selectedItem = selectedItem;
	}
	
	

}
