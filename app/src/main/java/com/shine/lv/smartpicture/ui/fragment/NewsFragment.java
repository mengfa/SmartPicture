package com.shine.lv.smartpicture.ui.fragment;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import android.view.View;
import android.widget.AdapterView;

import com.shine.lv.smartpicture.adapter.NewsAdapter;
import com.shine.lv.smartpicture.api.BMOBApi;
import com.shine.lv.smartpicture.base.BaseListFragment;
import com.shine.lv.smartpicture.base.ListBaseAdapter;
import com.shine.lv.smartpicture.bean.QiangYu;
import com.shine.lv.smartpicture.interf.OnTabReselectListener;
import com.shine.lv.smartpicture.ui.empty.EmptyLayout;
import com.shine.lv.smartpicture.util.UIHelper;

/**
 * 新闻资讯
 * 
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @created 2014年11月12日 下午4:17:45
 * 
 */
public class NewsFragment extends BaseListFragment<QiangYu> implements
        OnTabReselectListener {

    protected static final String TAG = NewsFragment.class.getSimpleName();
    private static final String CACHE_KEY_PREFIX = "newslist_";

    @Override
    protected NewsAdapter getListAdapter() {
        return new NewsAdapter();
    }

    @Override
    protected String getCacheKeyPrefix() {
        return CACHE_KEY_PREFIX + mCatalog;
    }




    @Override
    protected void sendRequestData() {
//        OSChinaApi.getNewsList(mCatalog, mCurrentPage, mHandler);
        BMOBApi.getQingYuList(mCurrentPage,findListener);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {

    }

    @Override
    protected void executeOnLoadDataSuccess(List<QiangYu> data) {
       if (true) {
           mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
           if (mState == STATE_REFRESH)
               mAdapter.clear();
           mAdapter.addData(data);
           mState = STATE_NOMORE;
           mAdapter.setState(ListBaseAdapter.STATE_NO_MORE);
           return;
       }
        super.executeOnLoadDataSuccess(data);
    }

    @Override
    public void onTabReselect() {
        onRefresh();
    }

//    @Override
//    protected long getAutoRefreshTime() {
//        // 最新资讯两小时刷新一次
//        if (mCatalog == NewsList.CATALOG_ALL) {
//
//            return 2 * 60 * 60;
//        }
//        return super.getAutoRefreshTime();
//    }
}
