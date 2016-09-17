package com.shine.lv.smartpicture.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.shine.lv.smartpicture.R;
import com.shine.lv.smartpicture.base.ListBaseAdapter;
import com.shine.lv.smartpicture.bean.QiangYu;
import com.shine.lv.smartpicture.bean.User;
import com.shine.lv.smartpicture.widget.LoadingFeedItemView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FavouriteAdapter extends ListBaseAdapter<QiangYu> {

    @SuppressLint("InflateParams")
    @Override
    protected View getRealView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null || convertView.getTag() == null) {
            convertView = getLayoutInflater(parent.getContext()).inflate(
                    R.layout.item_grid_image, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        QiangYu news = mDatas.get(position);


        // IV_IMAGE主要图片
        String imageUrl = null;
        if (news.getContentfigureurl() != null) {
            // 图片.我草他妈的终于找到了.
            imageUrl = news.getContentfigureurl().getFileUrl();
        }


        imageLoader.displayImage(imageUrl, vh.iv_image);


        return convertView;
    }

    static class ViewHolder {



        @InjectView(R.id.iv_image)
        ImageView iv_image;


        TextView tvcontent;
        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
