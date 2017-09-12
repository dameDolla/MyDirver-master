package com.app.gaolonglong.fragmenttabhost.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.gaolonglong.fragmenttabhost.R;

import butterknife.ButterKnife;

/**
 * Created by yanqi on 2017/9/11.
 */

public class MissionDoing extends Fragment {
    private View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if(mRootView == null)
        {
            mRootView = inflater.inflate(R.layout.mission_doint,container,false);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if(parent != null)
        {
            parent.removeView(mRootView);
        }
        ButterKnife.bind(this,mRootView);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
