package com.shine.lv.smartpicture.api;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.shine.lv.smartpicture.bean.QiangYu;

import java.util.Date;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.listener.FindListener;

public class BMOBApi{

//    /**
//     * 获取新闻列表
//     *
//     * @param catalog
//     *            类别 （1，2，3）
//     * @param page
//     *            第几页
//     * @param handler
//     */
//    public static void getQingYuList(int catalog, int page,
//                                     AsyncHttpResponseHandler handler) {
//        RequestParams params = new RequestParams();
//        params.put("catalog", catalog);
//        params.put("pageIndex", page);
//        params.put("pageSize", AppContext.PAGE_SIZE);
//        if (catalog == NewsList.CATALOG_WEEK) {
//            params.put("show", "week");
//        } else if (catalog == NewsList.CATALOG_MONTH) {
//            params.put("show", "month");
//        }
//        ApiHttpClient.get("action/api/news_list", params, handler);
//    }


    public static void getQingYuList(int page,
                                     FindListener<QiangYu> findListener) {
        BmobQuery<QiangYu> query = new BmobQuery<QiangYu>();
        query.order("-createdAt");
        // query.setCachePolicy(CachePolicy.NETWORK_ONLY);
        query.setLimit(20);
        BmobDate date = new BmobDate(new Date(System.currentTimeMillis()));
        query.addWhereLessThan("createdAt", date);
        query.setSkip(page * 20);
        query.include("author");
        query.findObjects(findListener);
    }

    public static void getFavourite(int page,
                                     FindListener<QiangYu> findListener) {
        BmobQuery<QiangYu> query = new BmobQuery<QiangYu>();
        query.order("-createdAt");
        // query.setCachePolicy(CachePolicy.NETWORK_ONLY);
        query.setLimit(20);
        BmobDate date = new BmobDate(new Date(System.currentTimeMillis()));
        query.addWhereLessThan("createdAt", date);
        query.setSkip(page * 20);
        query.addWhereExists("Contentfigureurl");
        query.findObjects(findListener);


    }




}