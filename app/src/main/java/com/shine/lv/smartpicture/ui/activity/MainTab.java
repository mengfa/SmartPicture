package com.shine.lv.smartpicture.ui.activity;

import com.shine.lv.smartpicture.R;
import com.shine.lv.smartpicture.ui.fragment.DiscoverFragment;
import com.shine.lv.smartpicture.ui.fragment.ExploreFragment;
import com.shine.lv.smartpicture.ui.fragment.FavouriteFragment;
import com.shine.lv.smartpicture.ui.fragment.MyInformationFragment;
import com.shine.lv.smartpicture.ui.fragment.NewsFragment;
import com.shine.lv.smartpicture.ui.fragment.NewsViewPagerFragment;
import com.shine.lv.smartpicture.ui.fragment.TweetsViewPagerFragment;


public enum MainTab {

//    NEWS(0, R.string.main_tab_name_news, R.drawable.icon_home,
//            NewsViewPagerFragment.class),
    NEWS(0, R.string.main_tab_name_news, R.drawable.icon_home,
        NewsFragment.class),




    TWEET(1, R.string.main_tab_name_tweet, R.drawable.icon_square,
            FavouriteFragment.class),

    QUICK(2, R.string.main_tab_name_quick, R.drawable.icon_add,
            null),

    EXPLORE(3, R.string.main_tab_name_explore, R.drawable.icon_search,
            DiscoverFragment.class),

    ME(4, R.string.main_tab_name_my, R.drawable.icon_selfinfo,
            MyInformationFragment.class);

    private int idx;
    private int resName;
    private int resIcon;
    private Class<?> clz;

    private MainTab(int idx, int resName, int resIcon, Class<?> clz) {
        this.idx = idx;
        this.resName = resName;
        this.resIcon = resIcon;
        this.clz = clz;
        //
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getResName() {
        return resName;
    }

    public void setResName(int resName) {
        this.resName = resName;
    }

    public int getResIcon() {
        return resIcon;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }
}
