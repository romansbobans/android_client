package com.romans.utils;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class LocationHelper implements LocationListener

{

@Override

public void onLocationChanged(Location loc)

{

loc.getLatitude();

loc.getLongitude();

String Text = "My current location is: " +

"Latitud = " + loc.getLatitude() +

"Longitud = " + loc.getLongitude();

Log.d("TAG", Text);

}

@Override

public void onProviderDisabled(String provider)

{


}

@Override

public void onProviderEnabled(String provider)

{


}

@Override

public void onStatusChanged(String provider, int status, Bundle extras)

{

}

}