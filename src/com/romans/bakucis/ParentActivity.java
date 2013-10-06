package com.romans.bakucis;

import java.util.Locale;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.romans.utils.AppPreferences;
import com.romans.utils.ChosenObject;
import com.romans.utils.Constans;

public class ParentActivity extends SherlockFragmentActivity {

	protected NetworkErrorDialog dialog;

	private AppPreferences preferences;
	
	protected ActionBar bar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		setActionBar();

		preferences = new AppPreferences(getBaseContext());
		
		setLanguage(preferences.getString(Constans.SHARED_PREF_LANG, Constans.langs[0]));
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {

		com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}
	
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.lv_lang:

			setLanguage(Constans.langs[0]);
			ChosenObject.setLanguageIndex(0);
			preferences.putString(Constans.SHARED_PREF_LANG, Constans.langs[0]);
			break;
		case R.id.ru_lang:

			setLanguage(Constans.langs[1]);
			ChosenObject.setLanguageIndex(1);
			preferences.putString(Constans.SHARED_PREF_LANG, Constans.langs[1]);
			break;
		case R.id.en_lang:

			setLanguage(Constans.langs[2] + "-US");
			ChosenObject.setLanguageIndex(2);
			preferences.putString(Constans.SHARED_PREF_LANG, Constans.langs[2]);
			
			break;

		default:
			break;
		}
		restartActivity();
		return super.onOptionsItemSelected(item);
	}
	
	private void setActionBar(){
		bar = getSupportActionBar();
		bar.setHomeButtonEnabled(false);
		bar.setDisplayHomeAsUpEnabled(false);
		bar.setDisplayShowCustomEnabled(true);
		bar.setLogo(R.drawable.icon72);
		String title = getResources().getString(R.string.app_name);
		bar.setTitle(title);
	}
	
	private void setLanguage(String lang){
		Locale locale = new Locale(lang);
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		getApplicationContext().getResources()
		.updateConfiguration(config, null);
	}
	
	private void restartActivity() {
	    Intent intent = getIntent();
	    finish();
	    startActivity(intent);
	}

}
