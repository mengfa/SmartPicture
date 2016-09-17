package com.shine.lv.smartpicture.bean;







import android.content.Context;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class UserProxy {

	public static final String TAG = "UserProxy";
	
	private Context mContext;
	
	public UserProxy(Context context){
		this.mContext = context;
	}
	

	
	public interface ISignUpListener{
		void onSignUpSuccess();
		void onSignUpFailure(String msg);
	}
	private ISignUpListener signUpLister;
	public void setOnSignUpListener(ISignUpListener signUpLister){
		this.signUpLister = signUpLister;
	}
	
	
//	public User getCurrentUser(){
//		User user = BmobUser.getCurrentUser(mContext, User.class);
//		if(user != null){
//			return user;
//		}else{
//		}
//		return null;
//	}
	//这是登录.
	public void login(String userName,String password){
		BmobUser user = new BmobUser();
		user.setUsername(userName);
		user.setPassword(password);
		user.login(new SaveListener<BmobUser>() {

			@Override
			public void done(BmobUser bmobUser, BmobException e) {
				if(e==null){

					if (loginListener != null) {
						loginListener.onLoginSuccess();}
					//通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
					//如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户信息
				}else{
					if (loginListener != null) {
						loginListener.onLoginFailure("失败");
					}
				}
			}
		});
	}
	public interface ILoginListener{
		void onLoginSuccess();
		void onLoginFailure(String msg);
	}
	private  ILoginListener loginListener;
	public void setOnLoginListener(ILoginListener loginListener){
		this.loginListener  = loginListener;
	}
	


}
