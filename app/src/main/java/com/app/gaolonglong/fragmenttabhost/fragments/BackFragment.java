package com.app.gaolonglong.fragmenttabhost.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.activities.AddReleaseActivity;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yanqi on 2017/8/2.
 */

public class BackFragment extends Fragment implements View.OnClickListener{
    private View mRootView;

    @BindView(R.id.rl_toaddrelease)
    public RelativeLayout rl;

    @OnClick(R.id.rl_toaddrelease)
    public void release()
    {
        startActivity(new Intent(getActivity(),AddReleaseActivity.class));
    }

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
        ButterKnife.bind(this,mRootView);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }
    private void init()
    {
        initView();
    }
    private void initView()
    {
       // rl.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {

        }
    }
}
