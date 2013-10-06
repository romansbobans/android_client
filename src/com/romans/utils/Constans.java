package com.romans.utils;

public final class Constans {

	//Type of request
	
	public static final int TYPE_MUSEUMS = 1;
	public static final int TYPE_MONUMENTS = 2;
	public static final int TYPE_OTHERS = 3;
	public static final int TYPE_EVENTS = 4;
	public static final int TYPE_INFORMATION = 5;
	public static final int TYPE_COWS = 6;
	
	
	//Request constants to put in json
	public static final String REQ_MUSEUMS = "museums";
	public static final String REQ_MONUMENTS = "monuments";
	public static final String REQ_OTHERS = "others";
	public static final String REQ_EVENTS = "events";
	public static final String REQ_INFO = "info";
	public static final String REQ_COWS = "cows";
	
	public static final String REQ_ALL = "all";
	public static final String REQ_COMMENTS = "send_comment";
	public static final String REQ_RATINGS = "send_ratings";
	//JSON commands
	public static final String JSON_TYPE = "type";
	public static final String JSON_GET = "get";
	public static final String JSON_ID = "id";
	public static final String JSON_COMMENT = "comment";
	public static final String JSON_COMMENT_TIME = "comment_time";
	
	
	//Shared preference
	
	public static final String SHARED_PREF_LANG = "languages";
	
	
	//Other constants
	public static final String[] langs = {"lv", "ru", "en"};
}
