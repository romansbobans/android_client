package com.romans.bakucis;

import com.romans.utils.Constans;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ParentActivity implements OnClickListener {

	Button museumsBtn, monumentsBtn, cowsBtn, othersBtn, eventsBtn;

	                    //dsfdgfdsg
	
	
	static final int TYPE_OBJECTS = 1; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		initUI();
		setListeners();
		
	}
	
	private void initUI(){
		museumsBtn = (Button) findViewById(R.id.btn_museums);
		monumentsBtn = (Button) findViewById(R.id.btn_monuments);
		cowsBtn = (Button) findViewById(R.id.btn_cows);
		othersBtn = (Button) findViewById(R.id.btn_others);
		eventsBtn = (Button) findViewById(R.id.btn_events);
		
		
		
	}
	
	private void setListeners(){
		museumsBtn.setOnClickListener(this);
		monumentsBtn.setOnClickListener(this);
		cowsBtn.setOnClickListener(this);
		othersBtn.setOnClickListener(this);
		eventsBtn.setOnClickListener(this);
	}
	


	@Override
	public void onClick(View v) {
		if (v.equals(museumsBtn)){
			startIntent(Constans.TYPE_MUSEUMS);
		}
		else if (v.equals(monumentsBtn)){
			startIntent(Constans.TYPE_MONUMENTS);
		}
		else if (v.equals(cowsBtn)){
			startIntent(Constans.TYPE_COWS);
		}
		else if (v.equals(othersBtn)){
			startIntent(Constans.TYPE_OTHERS);
		}
		else if (v.equals(eventsBtn)){
			startIntent(Constans.TYPE_EVENTS);
		}
		
	}
	
	
	private void startIntent(int extra){
		Intent i = new Intent(this, ObjectList.class);
		i.putExtra("type", extra);
		startActivity(i);
	}
	
	


}
//var/www/mongo_api
//schoolart.tdlbox.com/moadmin