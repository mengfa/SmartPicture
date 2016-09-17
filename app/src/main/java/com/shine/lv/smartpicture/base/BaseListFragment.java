package com.shine.lv.smartpicture.base;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;



import org.apache.http.Header;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


import com.shine.lv.smartpicture.R;
import com.shine.lv.smartpicture.app.AppContext;
import com.shine.lv.smartpicture.bean.BaseEntity;
import com.shine.lv.smartpicture.bean.QiangYu;
import com.shine.lv.smartpicture.ui.empty.EmptyLayout;
import com.shine.lv.smartpicture.util.StringUtils;

@SuppressLint("NewApi")
public abstract class BaseListFragment<T> extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener, OnItemClickListener,
        OnScrollListener {

    public static final String BUNDLE_KEY_CATALOG = "BUNDLE_KEY_CATALOG";
    protected  ArrayList<String> mDatas;
    @InjectView(R.id.swiperefreshlayout)
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    @InjectView(R.id.listview)
    protected ListView mListView;

    protected ListBaseAdapter<T> mAdapter;

    @InjectView(R.id.error_layout)
    protected EmptyLayout mErrorLayout;

    protected int mStoreEmptyState = -1;

    protected int mCurrentPage = 0;

    protected int mCatalog = 1;
    // 错误信息
//    protected Result mResult;

//    private AsyncTask<String, Void, ListEntity<T>> mCacheTask;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pull_refresh_listview;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        initView(view);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mCatalog = args.getInt(BUNDLE_KEY_CATALOG, 0);
        }

    }

    @Override
    public void initView(View view) {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.swiperefresh_color1, R.color.swiperefresh_color2,
                R.color.swiperefresh_color3, R.color.swiperefresh_color4);

        mErrorLayout.setOnLayoutClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mCurrentPage = 0;
                mState = STATE_REFRESH;
                mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
                requestData(true);
            }
        });

        mListView.setOnItemClickListener(this);
        mListView.setOnScrollListener(this);

        if (mAdapter != null) {
            mListView.setAdapter(mAdapter);
            mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
        } else {
            mAdapter = getListAdapter();
            mListView.setAdapter(mAdapter);

            if (requestDataIfViewCreated()) {
                mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
                mState = STATE_NONE;
                requestData(false);
            } else {
                mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            }

        }
        if (mStoreEmptyState != -1) {
            mErrorLayout.setErrorType(mStoreEmptyState);
        }
    }

    @Override
    public void onDestroyView() {
        mStoreEmptyState = mErrorLayout.getErrorState();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    protected abstract ListBaseAdapter<T> getListAdapter();

    // 下拉刷新数据
    @Override
    public void onRefresh() {
        Log.d("BaseListFragment","下拉刷新");
        if (mState == STATE_REFRESH) {
            return;
        }
        // 设置顶部正在刷新
        mListView.setSelection(0);
        setSwipeRefreshLoadingState();
        mCurrentPage = 0;
        mState = STATE_REFRESH;
        requestData(true);
    }

    protected boolean requestDataIfViewCreated() {
        return true;
    }

    protected String getCacheKeyPrefix() {
        return null;
    }




    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {}

    private String getCacheKey() {
        return new StringBuilder(getCacheKeyPrefix()).append("_")
                .append(mCurrentPage).toString();
    }

    // 是否需要自动刷新
    protected boolean needAutoRefresh() {
        return true;
    }

    /***
     * 获取列表数据
     * 
     * 
     * @author 火蚁 2015-2-9 下午3:16:12
     * 
     * @return void
     * @param refresh
     */
    protected void requestData(boolean refresh) {
//        String key = getCacheKey();


            sendRequestData();

    }

//    /***
//     * 判断是否需要读取缓存的数据
//     *
//     * @author 火蚁 2015-2-10 下午2:41:02
//     *
//     * @return boolean
//     * @param refresh
//     * @return
//     */
//    protected boolean isReadCacheData(boolean refresh) {
//        String key = getCacheKey();
//        if (!TDevice.hasInternet()) {
//            return true;
//        }
//        // 第一页若不是主动刷新，缓存存在，优先取缓存的
//        if (CacheManager.isExistDataCache(getActivity(), key) && !refresh
//                && mCurrentPage == 0) {
//            return true;
//        }
//        // 其他页数的，缓存存在以及还没有失效，优先取缓存的
//        if (CacheManager.isExistDataCache(getActivity(), key)
//                && !CacheManager.isCacheDataFailure(getActivity(), key)
//                && mCurrentPage != 0) {
//            return true;
//        }
//
//        return false;
//    }

    // 是否到时间去刷新数据了
    private boolean onTimeRefresh() {
        String lastRefreshTime = AppContext.getLastRefreshTime(getCacheKey());
        String currTime = StringUtils.getCurTimeStr();
        long diff = StringUtils.calDateDifferent(lastRefreshTime, currTime);
        return needAutoRefresh() && diff > getAutoRefreshTime();
    }

    /***
     * 自动刷新的时间
     * 
     * 默认：自动刷新的时间为半天时间
     * 
     * @author 火蚁 2015-2-9 下午5:55:11
     * 
     * @return long
     * @return
     */
    protected long getAutoRefreshTime() {
        return 12 * 60 * 60;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (onTimeRefresh()) {
            onRefresh();
        }
    }

    protected void sendRequestData() {}

//    private void readCacheData(String cacheKey) {
//        cancelReadCacheTask();
//        mCacheTask = new CacheTask(getActivity()).execute(cacheKey);
//    }
//
//    private void cancelReadCacheTask() {
//        if (mCacheTask != null) {
//            mCacheTask.cancel(true);
//            mCacheTask = null;
//        }
//    }

//    private class CacheTask extends AsyncTask<String, Void, ListEntity<T>> {
//        private final WeakReference<Context> mContext;

//        private CacheTask(Context context) {
//            mContext = new WeakReference<Context>(context);
//        }
//
//        @Override
//        protected ListEntity<T> doInBackground(String... params) {
//            Serializable seri = CacheManager.readObject(mContext.get(),
//                    params[0]);
//            if (seri == null) {
//                return null;
//            } else {
//                return readList(seri);
//            }
//        }
//
//        @Override
//        protected void onPostExecute(ListEntity<T> list) {
//            super.onPostExecute(list);
//            if (list != null) {
//                executeOnLoadDataSuccess(list.getList());
//            } else {
//                executeOnLoadDataError(null);
//            }
//            executeOnLoadFinish();
//        }
//    }
//
//    private class SaveCacheTask extends AsyncTask<Void, Void, Void> {
//        private final WeakReference<Context> mContext;
//        private final Serializable seri;
//        private final String key;
//
//        private SaveCacheTask(Context context, Serializable seri, String key) {
//            mContext = new WeakReference<Context>(context);
//            this.seri = seri;
//            this.key = key;
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            CacheManager.saveObject(mContext.get(), seri, key);
//            return null;
//        }
//    }
    protected FindListener<QiangYu> findListener = new FindListener<QiangYu>() {
        @Override
        public void done(List<QiangYu> list, BmobException e) {
            if(e==null){
                if (mCurrentPage == 0 && needAutoRefresh()) {
                    AppContext.putToLastRefreshTime(getCacheKey(),
                            StringUtils.getCurTimeStr());
                }
                if (isAdded()) {
                    if (mState == STATE_REFRESH) {
                        onRefreshNetworkSuccess();
                    }

                    executeOnLoadDataSuccess(list);
                    executeOnLoadFinish();
                }
            }else{

            }
        }
    };


    protected void executeOnLoadDataSuccess(List<QiangYu> data) {
        if (data == null) {
            data = new ArrayList<QiangYu>();
        }

//        if (mResult != null && !mResult.OK()) {
//            AppContext.showToast(mResult.getErrorMessage());
//            // 注销登陆，密码已经修改，cookie，失效了
//            AppContext.getInstance().Logout();
//        }

        mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
        if (mCurrentPage == 0) {
            mAdapter.clear();
        }


        int adapterState = ListBaseAdapter.STATE_EMPTY_ITEM;
        if ((mAdapter.getCount() + data.size()) == 0) {
            adapterState = ListBaseAdapter.STATE_EMPTY_ITEM;
        } else if (data.size() == 0
                || (data.size() < getPageSize() && mCurrentPage == 0)) {
            adapterState = ListBaseAdapter.STATE_NO_MORE;
            mAdapter.notifyDataSetChanged();
        } else {
            adapterState = ListBaseAdapter.STATE_LOAD_MORE;
        }
        mAdapter.setState(adapterState);
        mAdapter.addData(data);
        // 判断等于是因为最后有一项是listview的状态
        if (mAdapter.getCount() == 1) {

            if (needShowEmptyNoData()) {
                mErrorLayout.setErrorType(EmptyLayout.NODATA);
            } else {
                mAdapter.setState(ListBaseAdapter.STATE_EMPTY_ITEM);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 是否需要隐藏listview，显示无数据状态
     * 
     * @author 火蚁 2015-1-27 下午6:18:59
     * 
     */
    protected boolean needShowEmptyNoData() {
        return true;
    }



    protected int getPageSize() {
        return AppContext.PAGE_SIZE;
    }

    protected void onRefreshNetworkSuccess() {}

    protected void executeOnLoadDataError(String error) {
        if (mCurrentPage == 0
                ) {
            mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
        } else {
            mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            mAdapter.setState(ListBaseAdapter.STATE_NETWORK_ERROR);
            mAdapter.notifyDataSetChanged();
        }
    }

    // 完成刷新
    protected void executeOnLoadFinish() {
        Log.d("BaseListFragment","完成刷新");


        setSwipeRefreshLoadedState();
        mState = STATE_NONE;
    }

    /** 设置顶部正在加载的状态 */
    private void setSwipeRefreshLoadingState() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(true);
            // 防止多次重复刷新
            mSwipeRefreshLayout.setEnabled(false);
        }
    }

    /** 设置顶部加载完毕的状态 */
    private void setSwipeRefreshLoadedState() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.setEnabled(true);
        }
    }




    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        if (mAdapter == null || mAdapter.getCount() == 0) {
            return;
        }
        // 数据已经全部加载，或数据为空时，或正在加载，不处理滚动事件
        if (mState == STATE_LOADMORE || mState == STATE_REFRESH) {
            return;
        }
        // 判断是否滚动到底部
        boolean scrollEnd = false;
        try {
            if (view.getPositionForView(mAdapter.getFooterView()) == view
                    .getLastVisiblePosition())
                scrollEnd = true;
        } catch (Exception e) {
            scrollEnd = false;
        }

        if (mState == STATE_NONE && scrollEnd) {

            Log.d("BaseListFragment","底部刷新");

            if (mAdapter.getState() == ListBaseAdapter.STATE_LOAD_MORE
                    || mAdapter.getState() == ListBaseAdapter.STATE_NETWORK_ERROR) {
                mCurrentPage++;
                mState = STATE_LOADMORE;
                requestData(false);
                mAdapter.setFooterViewLoading();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
            int visibleItemCount, int totalItemCount) {



        // 数据已经全部加载，或数据为空时，或正在加载，不处理滚动事件
        // if (mState == STATE_NOMORE || mState == STATE_LOADMORE
        // || mState == STATE_REFRESH) {
        // return;
        // }
        // if (mAdapter != null
        // && mAdapter.getDataSize() > 0
        // && mListView.getLastVisiblePosition() == (mListView.getCount() - 1))
        // {
        // if (mState == STATE_NONE
        // && mAdapter.getState() == ListBaseAdapter.STATE_LOAD_MORE) {
        // mState = STATE_LOADMORE;
        // mCurrentPage++;
        // requestData(true);
        // }
        // }
    }



}
