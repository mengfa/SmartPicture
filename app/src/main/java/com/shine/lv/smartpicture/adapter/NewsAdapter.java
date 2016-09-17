package com.shine.lv.smartpicture.adapter;


import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;

import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.shine.lv.smartpicture.R;

import com.shine.lv.smartpicture.base.ListBaseAdapter;
import com.shine.lv.smartpicture.bean.QiangYu;
import com.shine.lv.smartpicture.bean.User;
import com.shine.lv.smartpicture.util.StringUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;



public class NewsAdapter extends ListBaseAdapter<QiangYu> {

    @SuppressLint("InflateParams")
    @Override
    protected View getRealView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null || convertView.getTag() == null) {
            convertView = getLayoutInflater(parent.getContext()).inflate(
                    R.layout.item_feed, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

       QiangYu news = mDatas.get(position);
//        final QiangYu status = datas.get(position);
        User user = news.getAuthor();
//		BaseApplication.getInstance().setCurrentQiangYu(status);
        if (user == null) {
//            LogUtils.i("user", "USER IS NULL");
        }
        if (user.getAvatar() == null) {
//            LogUtils.i("user", "USER avatar IS NULL");
        }
        String avatarUrl = null;
        if (user.getAvatar() != null) {
            // 头像.我草他妈的终于找到了.
            avatarUrl = user.getAvatar().getFileUrl();
        }
        // 头像
        imageLoader.displayImage(avatarUrl, vh.ivUserProfile);
        // 名字
        vh.userName.setText(news.getAuthor().getUsername());
//        vh.tv_subhead_down.setText(status.getAuthor().getUsername());
        // holder.tv_caption.setText(DateUtils.getShortTime(status.getCreated_at())
        // + " 来自 " + Html.fromHtml(status.getSource()));

        // IV_IMAGE主要图片
        String imageUrl = null;
        if (news.getContentfigureurl() != null) {
            // 图片.我草他妈的终于找到了.
            imageUrl = news.getContentfigureurl().getFileUrl();
        }
        if (imageUrl == null) {
            vh.vImageRoot.setVisibility(View.GONE);
        } else {
            vh.vImageRoot.setVisibility(View.VISIBLE);
            imageLoader.displayImage(imageUrl, vh.ivFeedCenter);
        }
        vh.tvcontent.setText( news.getContent());

//        // 内容
//        vh.tsLikesCounter.setText(news.getContent());
//        vh.tv_content.setText(StringUtils.getWeiboContent(context,
//                holder.tv_content, status.getContent()));
//
//        vh.iv_avatar.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Intent intent = new Intent(context, UserInfoActivity.class);
//                // intent.putExtra("userName",
//                // status.getAuthor().getUsername());
//                // context.startActivity(intent);
//            }
//        });
//        vh.text.setText(news.getContent());
        return convertView;
    }

    static class ViewHolder {
//        @InjectView(R.id.text)
//        TextView text;
//        @InjectView(R.id.tv_description)
//        TextView description;
//        @InjectView(R.id.tv_source)
//        TextView source;
//        @InjectView(R.id.tv_time)
//        TextView time;
//        @InjectView(R.id.tv_comment_count)
//        TextView comment_count;


        @InjectView(R.id.ivFeedCenter)
        ImageView ivFeedCenter;

        @InjectView(R.id.btnComments)
        ImageButton btnComments;
        @InjectView(R.id.btnLike)
        ImageButton btnLike;
        @InjectView(R.id.btnMore)
        ImageButton btnMore;
        @InjectView(R.id.vBgLike)
        View vBgLike;
        @InjectView(R.id.ivLike)
        ImageView ivLike;
        @InjectView(R.id.tsLikesCounter)
        TextSwitcher tsLikesCounter;
        @InjectView(R.id.ivUserProfile)
        ImageView ivUserProfile;
        @InjectView(R.id.vImageRoot)
        FrameLayout vImageRoot;
        @InjectView(R.id.user_name)
        TextView userName;
        @InjectView(R.id.tvcontent)
        TextView tvcontent;
        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
