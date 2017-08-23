package com.app.gaolonglong.fragmenttabhost.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.gaolonglong.fragmenttabhost.R;

/**
 * Created by yanqi on 2017/8/2.
 */

public class BackFragment extends Fragment {
    private View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null){
            Log.e("666","MineFragment");
            mRootView = inflater.inflate(R.layout.release_list,container,false);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null){
            parent.removeView(mRootView);
        }
        return mRootView;
    }
}
