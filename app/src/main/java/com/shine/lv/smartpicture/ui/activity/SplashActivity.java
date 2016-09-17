package com.shine.lv.smartpicture.ui.activity;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


import com.shine.lv.smartpicture.R;
import com.shine.lv.smartpicture.base.BaseActivity;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;




public class SplashActivity extends BaseActivity {
	
	private static final int WHAT_INTENT2LOGIN = 1;
	private static final int WHAT_INTENT2MAIN = 2;
	private static final long SPLASH_DUR_TIME = 1000;



	
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			switch (msg.what) {
			case WHAT_INTENT2LOGIN:
				intent2Activity(LoginActivity.class);
				finish();
				break;
			case WHAT_INTENT2MAIN:
				intent2Activity(MainActivity.class);
				finish();
				break;
			default:
				break;
			}
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_splash);
		//Bmob SDK初始化--只需要这一段代码即可完成初始化
		//请到Bmob官网(http://www.bmob.cn/)申请ApplicationId,具体地址:http://docs.bmob.cn/android/faststart/index.html?menukey=fast_start&key=start_android
		Bmob.initialize(this, "eb2d7d54b4aa41eb92e97a7a00c812d0");
		BmobUser currentUser = BmobUser.getCurrentUser();
		if (currentUser != null) {
			handler.sendEmptyMessageDelayed(WHAT_INTENT2MAIN, SPLASH_DUR_TIME);
		} else {
			handler.sendEmptyMessageDelayed(WHAT_INTENT2LOGIN, SPLASH_DUR_TIME);

		}
		
	}
}
