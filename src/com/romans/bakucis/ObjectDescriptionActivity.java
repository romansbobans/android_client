package com.romans.bakucis;

import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.romans.bakucis.NetworkErrorDialog.DialogAction;
import com.romans.utils.RetrievedObject;
import com.romans.utils.ChosenObject;
import com.romans.utils.Constans;
import com.romans.utils.DownloadService;
import com.romans.utils.DownloadService.DownloadCallback;
import com.romans.utils.HelperMethods;

public class ObjectDescriptionActivity extends ParentActivity implements
		DownloadCallback, OnClickListener, DialogAction {

	TextView title, plot, price, workingHours, commentTitle;
	RetrievedObject selectedObject;
	Button commentBtn;
	EditText commentText;
	ListView commentsBox;
	LinearLayout mapsButton;

	ScrollView scroll;

	// Contact info
	TextView address, homepage, phoneNumber, email;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_object_description);
		initVars();
		fillFields();

		// ().scrollTo(0, 0);
	}

	private void initVars() {
		title = (TextView) findViewById(R.id.title_text);
		plot = (TextView) findViewById(R.id.plot_text);
		price = (TextView) findViewById(R.id.price_text);
		workingHours = (TextView) findViewById(R.id.workinghrs_text);
		commentTitle = (TextView) findViewById(R.id.comments_text_title);
		commentText = (EditText) findViewById(R.id.comment_text);
		commentsBox = (ListView) findViewById(R.id.comments_list);

		commentBtn = (Button) findViewById(R.id.btn_comment);

		mapsButton = (LinearLayout) findViewById(R.id.location_layout);

		mapsButton.setOnClickListener(this);
		commentBtn.setOnClickListener(this);

		address = (TextView) findViewById(R.id.address_tv);
		phoneNumber = (TextView) findViewById(R.id.phone_no_tv);
		email = (TextView) findViewById(R.id.email_tv);
		homepage = (TextView) findViewById(R.id.homepage_tv);

		scroll = (ScrollView) findViewById(R.id.scrollView1);

		scroll.postDelayed(new Runnable() {

			@Override
			public void run() {
				scroll.fullScroll(ScrollView.FOCUS_UP);
				// TODO Auto-generated method stub

			}
		}, 100);

		selectedObject = ChosenObject.getSelectedItem();
	}

	private void fillFields() {
		title.setText(selectedObject.getTitle());
		plot.setText(selectedObject.getPlot());
		if (selectedObject.getPrices().equals("")) {
			price.setVisibility(View.GONE);
		} else {
			price.setText(selectedObject.getPrices());
		}
		if (selectedObject.getWorkingHrs().equals("")) {
			workingHours.setVisibility(View.GONE);
		} else {
			workingHours.setText(selectedObject.getWorkingHrs());
		}

		commentsBox.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, selectedObject
						.getComments()));

		HelperMethods.setListViewHeightBasedOnChildren(commentsBox);

		Log.e("TAG", selectedObject.getAddress());

		if (!selectedObject.getEmail().equals("")) {
			email.append(selectedObject.getEmail());
		} else {
			email.setVisibility(View.GONE);
		}
		if (!selectedObject.getHomepage().equals("")) {
			homepage.append(selectedObject.getHomepage());
		} else {
			homepage.setVisibility(View.GONE);
		}
		if (!selectedObject.getAddress().equals("")) {
			address.append(selectedObject.getAddress());
		} else {
			address.setVisibility(View.GONE);
		}
		if (!selectedObject.getPhoneNo().equals("")) {
			phoneNumber.append(selectedObject.getPhoneNo());
		} else {
			phoneNumber.setVisibility(View.GONE);
		}
	}

	private void addComment(String text) {
		if (text.equals("") || text == null) {
			Toast.makeText(this, "txt", Toast.LENGTH_SHORT).show();
			return;
		}
		final DownloadService s = new DownloadService();
		s.setCallback(this);
		s.execute(createCommentRequest(text));
	}

	private String createCommentRequest(String text) {
		JSONObject o = new JSONObject();
		try {
			// Calendar d = Calendar.getInstance();
			String date = Calendar.DATE + "." + Calendar.MONTH + "."
					+ Calendar.YEAR;
			o.put(Constans.JSON_GET, Constans.REQ_COMMENTS);
			o.put(Constans.JSON_COMMENT, text);
			o.put(Constans.JSON_ID, selectedObject.getId());
			o.put(Constans.JSON_COMMENT_TIME, date);
			return o.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public void updateAfterDownload(String resultingInput) {
		if (resultingInput == null)
			try {
				dialog = new NetworkErrorDialog();
				Bundle args = new Bundle();
				args.putString("message", "Sorry, something is wrong!");
				dialog.setArguments(args);
				dialog.setListener(ObjectDescriptionActivity.this);
				dialog.show(getSupportFragmentManager(), null);
				return;
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		if (resultingInput.equals("true")) {
			Toast.makeText(getApplicationContext(),
					getResources().getString(R.string.comment_added),
					Toast.LENGTH_SHORT).show();

			selectedObject.getComments().add(resultingInput.substring(5));
			HelperMethods.setListViewHeightBasedOnChildren(commentsBox);
			commentText.setText("");

		}

	}

	@Override
	public void onClick(View arg0) {
		if (arg0.equals(mapsButton)) {
			Intent i = new Intent(ObjectDescriptionActivity.this, MapView.class);
			startActivity(i);
		} else if (arg0.equals(commentBtn)) {
			addComment(commentText.getText().toString());
		}

	}

	@Override
	public void retry() {

		addComment(commentText.getText().toString());

	}

}
