package com.romans.bakucis;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.romans.utils.ChosenObject;

public class MapView extends ParentActivity {

	//LatLng MyLocation = new LatLng(53.558, 9.927);
	//static final LatLng KIEL = new LatLng(53.551, 9.993);
	private GoogleMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.map_layout);
		
		LatLng myLoc = ChosenObject.getMyLocation();
		LatLng objectLoc = ChosenObject.getSelectedItem().getLocation();
	
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		map.addMarker(new MarkerOptions().position(myLoc).title("You"));
		map.addMarker(new MarkerOptions().position(objectLoc).title(ChosenObject.getSelectedItem().getTitle()));
		map.moveCamera( CameraUpdateFactory.newLatLngZoom(myLoc , 14.0f) );
	}



}
