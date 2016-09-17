package com.shine.lv.smartpicture.base;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Toast;


import com.google.gson.Gson;

import com.shine.lv.smartpicture.app.AppContext;

public abstract class BaseActivity extends Activity {

	protected String TAG;

	protected AppContext application;
	protected SharedPreferences sp;


	protected Gson gson;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TAG = this.getClass().getSimpleName();
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		application = (AppContext) getApplication();

		


		gson = new Gson();
	}
	
	protected void intent2Activity(Class<? extends Activity> tarActivity) {
		Intent intent = new Intent(this, tarActivity);
		startActivity(intent);
	}
	


}
