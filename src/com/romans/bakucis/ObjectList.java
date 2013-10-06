package com.romans.bakucis;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.romans.bakucis.NetworkErrorDialog.DialogAction;
import com.romans.utils.RetrievedObject;
import com.romans.utils.ChosenObject;
import com.romans.utils.Constans;
import com.romans.utils.DownloadService;
import com.romans.utils.DownloadService.DownloadCallback;
import com.romans.utils.HelperMethods;

public class ObjectList extends ParentActivity implements DownloadCallback, DialogAction {

	private ArrayList<RetrievedObject> mSelectedObjects;

	private int type;
	
	NetworkErrorDialog dialog;
	
	private ListView mObjects;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_object_list);

		initVarsAndFields();
		setListeners();

		Intent i = getIntent();
		type = i.getExtras().getInt("type");
		Log.d("TAG", type + "");

		new Runnable() {

			@Override
			public void run() {
				while (true) {
					LatLng myLoc = HelperMethods._getLocation(getSystemService(LOCATION_SERVICE));
					// No exception caught
					if (myLoc.latitude != -1) {
						ChosenObject.setMyLocation(myLoc);
						Log.d("TAG", "BREAK");
						break;
					}

					Log.d("TAG", "RAAAAABL");

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
						break;
					}
				}
			}
		}.run();

		startDownload(createReqest(type));

	}

	private void initVarsAndFields() {
		mObjects = (ListView) findViewById(R.id.object_list);

		mSelectedObjects = new ArrayList<RetrievedObject>();

	}

	private void setListeners() {
		mObjects.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				ChosenObject.setSelectedItem((RetrievedObject) arg0
						.getItemAtPosition(arg2));
				Intent i = new Intent(ObjectList.this,
						ObjectDescriptionActivity.class);
				startActivity(i);
			}
		});
	}

	private void startDownload(String request) {

		DownloadService lService = new DownloadService();
		lService.setCallback(this);

		lService.execute(request);
	}

	private String createReqest(int type) {
		String requestType;
		switch (type) {
		case Constans.TYPE_COWS:
			requestType = Constans.REQ_COWS;
			break;
		case Constans.TYPE_EVENTS:
			requestType = Constans.REQ_EVENTS;
			break;
		case Constans.TYPE_MONUMENTS:
			requestType = Constans.REQ_MONUMENTS;
			break;
		case Constans.TYPE_MUSEUMS:
			requestType = Constans.REQ_MUSEUMS;
			break;
		case Constans.TYPE_OTHERS:
			requestType = Constans.REQ_OTHERS;
			break;
		default:
			requestType = "";
			break;
		}

		try {
			JSONObject o = new JSONObject();
			o.put(Constans.JSON_GET, "all");
			o.put(Constans.JSON_TYPE, requestType);
			o.put("lang", Constans.langs[ChosenObject.getLanguageIndex()]);
			return o.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}
		return "";
	}

	// Gets JSON answer from server, parses it and builds listView
	private void updateUI() {
		final ArrayAdapter<RetrievedObject> lAdapter = new ArrayAdapter<RetrievedObject>(
				ObjectList.this, android.R.layout.simple_list_item_1,
				mSelectedObjects) {
			class TagHolder {
				// Views defined in inflated xml car_item_cell
				TextView name;
				TextView description;
				TextView distance;
				ImageView priceIcon, workingHrsIcon;
				ImageView icon;
			}

			@Override
			public View getView(final int position, final View convertView,
					final ViewGroup parent) {
				View v = convertView;
				TagHolder tagHolder = null;
				if (v == null) {
					// Inflates car_item_cell
					final LayoutInflater vi = (LayoutInflater) getContext()
							.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					v = vi.inflate(R.layout.object_item_layout, null);
					// Initializing tagHolder object where views are saved
					tagHolder = new TagHolder();
					// carSecs component must not be visible, so it is set to
					// Visibility GONE

					// carSpecs.setVisibility(View.GONE);

					tagHolder.name = (TextView) v
							.findViewById(R.id.object_name);
					tagHolder.description = (TextView) v
							.findViewById(R.id.object_description);
					tagHolder.distance = (TextView) v
							.findViewById(R.id.distance_text);

					// if (ChosenObject.getMyLocation() != null)
					getDocument(tagHolder.distance,
							ChosenObject.getMyLocation(), getItem(position)
									.getLocation());
					Log.e("TAG", ChosenObject.getMyLocation().latitude + "");
					// Sets Tag to current cell, so every view has it's oen
					// tagHolder
					v.setTag(tagHolder);
				} else {
					// if view has been already initialized, it gets it
					// respective TagHolder
					tagHolder = (TagHolder) v.getTag();
				}
				try {
					// Gets car item and fills all views with info
					final RetrievedObject currCar = getItem(position);
					tagHolder.name.setText(currCar.getTitle());

					tagHolder.description.setText(currCar.getDescription());

				} catch (final Exception e) {
					e.printStackTrace();
					System.exit(0);
					Log.e("CarList", "Error setting cell" + e);
				}
				return v;
			}

		};
		mObjects.setAdapter(lAdapter);
	}

	private void getObjects(String res) throws JSONException {
		JSONArray result = new JSONArray(res);
		// String result = getJSONObj(res);
		// Log.d("TAG", res);

		for (int i = 0; i < result.length(); i++) {
			JSONObject o = result.getJSONObject(i);
			String name = getTitle(o);
			String description = getDescription(o);
			String workingHours = getWorkingHours(o);
			String prices = getPrice(o);
			String plot = getPlot(o);
			String id = getId(o);
			String homepage = getHomepage(o);
			String email = getEmail(o);
			String phoneNo = getPhoneNo(o);
			String address = getAddress(o);

			mSelectedObjects.add(new RetrievedObject(1, id, name, description, plot,
					getComments(o), workingHours, prices,
					getLatLong(o), address, email, homepage, phoneNo));
		}

		updateUI();
	}

	private ArrayList<String> getComments(JSONObject o) {
		ArrayList<String> result = new ArrayList<String>();
		JSONArray array = HelperMethods.getJSONArray(o, "comments");
		Log.e("TAG", array.length() + "");
		for (int i = 0; i < array.length(); i++) {
			try {
				result.add(array.getString(i));// (i).toString());
				Log.d("TAG", result.get(i));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;

	}

	private String getPhoneNo(JSONObject o) {
		return HelperMethods.getField(o, "phone");
	}

	private String getEmail(JSONObject o) {
		return HelperMethods.getField(o, "email");
	}

	private String getHomepage(JSONObject o) {
		return HelperMethods.getField(o, "homepage");
	}

	private String getAddress(JSONObject o) {
		return HelperMethods.getField(o, "address");
	}

	private LatLng getLatLong(JSONObject o) {
		String loc = HelperMethods.getField(o, "location");
		// Log.e("TAG", o.toString());
		String[] latlng = loc.split(",");
		// Log.e("TAG", latlng[0] + ", " + latlng[1]);
		try {
			return new LatLng(Double.parseDouble(latlng[0]),
					Double.parseDouble(latlng[1]));
		} catch (Exception e) {
			return null;
		}

	}

	private String getTitle(JSONObject o) {
		return HelperMethods.getField(o, "name");
	}

	private String getId(JSONObject o) {
		return HelperMethods.getField(o, "id");
	}

	private String getDescription(JSONObject o) {
		return HelperMethods.getField(o, "description");
	}

	private String getWorkingHours(JSONObject o) {
		return HelperMethods.getField(o, "workinghours");

	}

	private String getPrice(JSONObject o) {
		return HelperMethods.getField(o, "price");
	}

	private String getPlot(JSONObject o) {
		return HelperMethods.getField(o, "plot");
	}

	
	// Passes textview which will be set and coordinates of current location and
	// destination
	public void getDocument(final TextView v, LatLng start, LatLng destination) {

		LatLng a = start;// new LatLng(57.393785, 21.565054);
		LatLng b = destination;// new LatLng(57.392675, 21.573652);
		// Didn't set latitude
		if (start.latitude == -1 || destination == null) {
			v.setVisibility(View.GONE);
			return;
		}

		new AsyncTask<LatLng, String, String>() {

			@Override
			protected String doInBackground(LatLng... params) {
				try {
					String url = "http://maps.googleapis.com/maps/api/directions/json?"
							+ "origin="
							+ params[0].latitude
							+ ","
							+ params[0].longitude
							+ "&destination="
							+ params[1].latitude
							+ ","
							+ params[1].longitude
							+ "&sensor=false&units=metric&mode=legs";

					HttpClient httpClient = new DefaultHttpClient();
					HttpContext localContext = new BasicHttpContext();
					HttpPost httpPost = new HttpPost(url);
					HttpResponse response = httpClient.execute(httpPost,
							localContext);
					InputStream in = response.getEntity().getContent();

					BufferedReader br = new BufferedReader(
							new InputStreamReader(in));
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

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				
				
				v.setText(getDistance(result));
			}

			// Passes retrieved info from server, gets only distance field
			private String getDistance(String r) {
				try {
					JSONObject topMostObj = new JSONObject(r);
					JSONArray routes = topMostObj.getJSONArray("routes");

					JSONObject route = routes.getJSONObject(0);

					JSONArray legsArray = route.getJSONArray("legs");

					JSONObject legs = legsArray.getJSONObject(0);

					JSONObject distance = legs.getJSONObject("distance");

					String dist = distance.getString("text");
					// routes.get
					return dist;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// In case nothing was received from server
				v.setVisibility(View.INVISIBLE);
				return "-- km";
			}

		}.execute(a, b);

	}

	@Override
	public void updateAfterDownload(String resultingInput) {
		try {
			if (resultingInput == null)
			try {
				dialog = new NetworkErrorDialog();
				Bundle args = new Bundle();
				args.putString("message", "Sorry, something is wrong!");
				dialog.setArguments(args);
				dialog.setListener(ObjectList.this);
				dialog.show(getSupportFragmentManager(), null);
				return;
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
			getObjects(resultingInput);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void retry() {
		startDownload(createReqest(type));
		// TODO Auto-generated method stub
		
	}

}
