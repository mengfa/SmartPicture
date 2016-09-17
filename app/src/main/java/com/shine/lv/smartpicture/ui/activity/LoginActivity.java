package com.shine.lv.smartpicture.ui.activity;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shine.lv.smartpicture.R;
import com.shine.lv.smartpicture.base.BaseActivity;
import com.shine.lv.smartpicture.bean.UserProxy;

public class LoginActivity extends BaseActivity implements UserProxy.ILoginListener {
	//跳转留着.
//	 intent2Activity(MainActivity.class);
	public EditText userNameInput;
	public EditText userPasswordInput;
	// 用户代理.
	public UserProxy userProxy;
	public Button loginButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		userNameInput = (EditText) findViewById(R.id.user_name_input);

		userPasswordInput = (EditText) findViewById(R.id.user_password_input);
		loginButton = (Button) findViewById(R.id.login);
		userProxy = new UserProxy(this);
		loginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(userNameInput.getText())) {

					Toast.makeText(LoginActivity.this, "请输入用户名",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (TextUtils.isEmpty(userPasswordInput.getText())) {

					Toast.makeText(LoginActivity.this, "请输入密码",
							Toast.LENGTH_SHORT).show();
					return;
				}

				userProxy.setOnLoginListener(LoginActivity.this);

				userProxy.login(userNameInput.getText().toString().trim(),
						userPasswordInput.getText().toString().trim());

			}
		});
	}


	@Override
	public void onLoginSuccess() {
		// TODO Auto-generated method stub
		Toast.makeText(LoginActivity.this, "登录成功",
				Toast.LENGTH_SHORT).show();
		intent2Activity(MainActivity.class);
		setResult(RESULT_OK);
		finish();
	}

	@Override
	public void onLoginFailure(String msg) {
		Toast.makeText(LoginActivity.this, "登录失败",
				Toast.LENGTH_SHORT).show();
	}


}
