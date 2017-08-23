package com.app.gaolonglong.fragmenttabhost.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Created by yanqi on 2017/8/2.
 */

public class BaojiaFragment extends Fragment {
    private View mRootView;

    @BindView(R.id.title_back)
    ImageView back;

    @BindViews({R.id.title_back_txt,R.id.top_title})
    public List<TextView> mTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null){
            Log.e("666","MineFragment");
            mRootView = inflater.inflate(R.layout.baojia_fragment,container,false);
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
        mTextView.get(0).setVisibility(View.INVISIBLE);
        back.setVisibility(View.INVISIBLE);
        mTextView.get(1).setText("报价");
    }
}
