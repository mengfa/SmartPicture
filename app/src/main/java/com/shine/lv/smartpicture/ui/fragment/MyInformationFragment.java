package com.shine.lv.smartpicture.ui.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.shine.lv.smartpicture.R;



import com.shine.lv.smartpicture.base.BaseFragment;


import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyInformationFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_user_profile,null);
        ButterKnife.inject(this, view);
        initView(view);
        return view;
    }

    }


