package com.romans.utils;

import java.util.ArrayList;

import com.google.android.gms.maps.model.LatLng;

public final class RetrievedObject {
/*
 * type - type of object, 1 - object, 2 - museums, 3 - cows, 4 - others
 */
	 private int type;
	 private String id;
	 private String title;
	 private String description;
	 private String plot;
	 private ArrayList<String> comments = new ArrayList<String>();
	 private String workingHrs; 
	 private String prices;
	 private LatLng location;
	 private String address;
	 private String email;
	 private String homepage;
	 private String phoneNo;
	
	
	
	

	public RetrievedObject(int type, String id, String title, String description,
			String plot,
			ArrayList<String> comments, String workingHrs, String prices,
			LatLng location, String address, String email, String homepage,
			String phoneNo) {
		super();
		this.type = type;
		this.id = id;
		this.title = title;
		this.description = description;
		this.plot = plot;
		this.comments = comments;
		this.workingHrs = workingHrs;
		this.prices = prices;
		this.location = location;
		this.address = address;
		this.email = email;
		this.homepage = homepage;
		this.phoneNo = phoneNo;
	}



	public String getHomepage() {
		return homepage;
	}



	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}



	public String getPhoneNo() {
		return phoneNo;
	}



	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public LatLng getLocation() {
		return location;
	}



	public void setLocation(LatLng location) {
		this.location = location;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getPlot() {
		return plot;
	}


	public void setPlot(String plot) {
		this.plot = plot;
	}

	public ArrayList<String> getComments() {
		return comments;
	}


	public void setComments(ArrayList<String> comments) {
		this.comments = comments;
	}


	public String getWorkingHrs() {
		return workingHrs;
	}


	public void setWorkingHrs(String workingHrs) {
		this.workingHrs = workingHrs;
	}


	public String getPrices() {
		return prices;
	}


	public void setPrices(String prices) {
		this.prices = prices;
	}
	
	
	
	
	
	

	
}
