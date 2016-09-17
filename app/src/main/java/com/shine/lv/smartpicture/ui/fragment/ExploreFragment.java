package com.shine.lv.smartpicture.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shine.lv.smartpicture.R;
import com.shine.lv.smartpicture.api.BMOBApi;
import com.shine.lv.smartpicture.bean.QiangYu;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreFragment extends Fragment {


    public ExploreFragment() {
        // Required empty public constructor
    }

    public Button get;
    public TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.text_fragment, null);
        get = (Button) view.findViewById(R.id.get);
        tv = (TextView) view.findViewById(R.id.tv);


        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BMOBApi.getQingYuList(1,new FindListener<QiangYu>() {
                    @Override
                    public void done(List<QiangYu> object, BmobException e) {
                        if(e==null){
                           tv.setText(object.get(0).toString());
                        }else{
                            Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                        }
                    }
                });





















//

            }
        });










        return view;

    }

}
