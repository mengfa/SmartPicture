package com.shine.lv.smartpicture.bean;

import cn.bmob.v3.BmobObject;





/**
 * @author kingofglory
 *         email: kingofglory@yeah.net
 *         blog:  http:www.google.com
 * @date 2014-4-2
 * TODO
 */

public class Comment extends BmobObject{
	
	public static final String TAG = "Comment";
	private String created_at;
	private User user;
	private String commentContent;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	
	public String getCreated_at() {
		return created_at;
	}
	public String setCreated_at() {
		return created_at;
	}


}
