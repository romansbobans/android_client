package com.romans.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.romans.bakucis.R;

public class ObjectItem extends LinearLayout {

	
	private ImageView thumbnail;
	private TextView title, description;
	
	public ObjectItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		View.inflate(getContext(), R.layout.object_item_layout, this);
		initUIComponents();
		// TODO Auto-generated constructor stub
	}
	
	private void initUIComponents(){
		thumbnail = (ImageView) findViewById(R.id.thumb);
		title = (TextView) findViewById(R.id.object_name);
		description = (TextView) findViewById(R.id.object_description);
	}
	
	public void setThumbnail(Bitmap bmp){
		thumbnail.setImageBitmap(bmp);
	}
	
	public void setTitle(String text){
		title.setText(text);
	}
	
	public void setescription(String text){
		description.setText(text);
	}
	//Sets all fields together
	public void setFields(Bitmap bmp, String title, String descr){
		thumbnail.setImageBitmap(bmp);
		this.title.setText(title);
		this.description.setText(descr);
	}
	//Sets just textViews
	public void setTextFields(String title, String descr){
		this.title.setText(title);
		this.description.setText(descr);
	}
	
	

}
