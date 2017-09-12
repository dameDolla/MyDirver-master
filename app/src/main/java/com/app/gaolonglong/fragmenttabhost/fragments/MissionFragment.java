package com.app.gaolonglong.fragmenttabhost.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.gaolonglong.fragmenttabhost.R;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Created by yanqi on 2017/8/2.
 */

public class MissionFragment extends Fragment implements View.OnClickListener{

    private View mRootView;
    public static final int MISSION_DOING = 1;
    public static final int MISSION_DONE = 2;
    public static final int MISSION_CANCLE = 3;
    private MissionDoing doing;
    private MissionDone done;
    private MissionCancle cancle;
    public int currentFragmentType = -1;

    @BindViews({R.id.commission_doing,R.id.commission_done,R.id.commission_cancle})
    public List<Button> top_tab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null){
            Log.e("666","MineFragment");
            mRootView = inflater.inflate(R.layout.mission_fragment,container,false);
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
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    private void init()
    {
        initView();
    }
    private void initView()
    {
        top_tab.get(0).setOnClickListener(this);
        top_tab.get(1).setOnClickListener(this);
        top_tab.get(2).setOnClickListener(this);
        loadFragment(MISSION_DOING);
    }
    private void switchFragment(int type) {
        switch (type) {
            case MISSION_DOING:
                loadFragment(MISSION_DOING);
                break;
            case MISSION_DONE:
                loadFragment(MISSION_DONE);
                break;
            case MISSION_CANCLE:
                loadFragment(MISSION_CANCLE);
                break;

        }

    }

    private void loadFragment(int type) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (type == MISSION_DOING) {
            if (doing == null) {
                doing = new MissionDoing();

                transaction.add(R.id.fl_content, doing, "doing");
            } else {
                transaction.show(doing);
            }
            if (done != null && cancle != null) {
                transaction.hide(done);
                transaction.hide(cancle);
            }
            currentFragmentType = MISSION_DOING;
        } else if (type == MISSION_DONE){
            if (done == null) {
                done = new MissionDone();
                transaction.add(R.id.fl_content, done, "done");
            } else {
                transaction.show(done);
            }
            if (doing != null && cancle != null) {
                transaction.hide(cancle);
                transaction.hide(doing);
            }
            currentFragmentType = MISSION_DONE;
        }else if (type == MISSION_CANCLE){
            if (cancle == null) {
                cancle = new MissionCancle();
                transaction.add(R.id.fl_content, cancle, "cancle");
            } else {
                transaction.show(cancle);
            }
            if (doing != null && done != null) {
                transaction.hide(done);
                transaction.hide(doing);
            }
            currentFragmentType = MISSION_CANCLE;
        }
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.commission_doing:
                top_tab.get(0).setTextColor(getResources().getColor(R.color.shen_blue));
                top_tab.get(1).setTextColor(Color.WHITE);
                top_tab.get(2).setTextColor(Color.WHITE);
                top_tab.get(0).setBackgroundResource(R.drawable.left_bold);
                top_tab.get(1).setBackgroundResource(R.drawable.right_transparent);
                top_tab.get(2).setBackgroundResource(R.drawable.right_transparent);
                switchFragment(MISSION_DOING);
                break;
            case R.id.commission_done:
                top_tab.get(1).setTextColor(getResources().getColor(R.color.shen_blue));
                top_tab.get(0).setTextColor(Color.WHITE);
                top_tab.get(2).setTextColor(Color.WHITE);
                top_tab.get(1).setBackgroundResource(R.drawable.left_bold);
                top_tab.get(0).setBackgroundResource(R.drawable.left_transparent);
                top_tab.get(2).setBackgroundResource(R.drawable.right_transparent);
                switchFragment(MISSION_DONE);
                break;
            case R.id.commission_cancle:
                top_tab.get(2).setTextColor(getResources().getColor(R.color.shen_blue));
                top_tab.get(1).setTextColor(Color.WHITE);
                top_tab.get(0).setTextColor(Color.WHITE);
                top_tab.get(2).setBackgroundResource(R.drawable.right_bold);
                top_tab.get(1).setBackgroundResource(R.drawable.left_transparent);
                top_tab.get(0).setBackgroundResource(R.drawable.left_transparent);
                switchFragment(MISSION_CANCLE);
                break;

        }
    }
}
