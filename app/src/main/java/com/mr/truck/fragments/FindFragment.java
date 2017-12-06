package com.mr.truck.fragments;

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
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mr.truck.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yanqi on 2017/8/2.
 */

public class FindFragment extends ForResultNestedCompatFragment implements View.OnClickListener{


    private  View mRootView;
    private View contentView;
    private PopupWindow popMenu;
    private TextView allSrc,pipei,emptyPipei;
    public static final int ALLSRC = 1;
    public static final int ROUTESRC = 2;
    public static final int KCSRC = 3;
    private FindAllSrcFragment all;
    private FindRouteSrcFragment routeSrc;
    private FindKCSrcFragment kcSrc;
    public int currentFragmentType = -1;

    /*@BindView(R.id.find_src_rlv)
    public RecyclerView find_rcl;*/

    @BindView(R.id.find_fl_content)
    public FrameLayout fl_content;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null){
            Log.e("666","MineFragment");
            mRootView = inflater.inflate(R.layout.find_fragment,container,false);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null){
            parent.removeView(mRootView);
        }
        ButterKnife.bind(this,mRootView);
        return mRootView;
    }


    private void init()
    {

        initView();
        initData();

    }
    private void  initView()
    {
        allSrc = (TextView) mRootView.findViewById(R.id.find_all_src);        //全部商品
        pipei = (TextView)mRootView.findViewById(R.id.find_xianlu_pipei);            //匹配商品
        emptyPipei = (TextView)mRootView.findViewById(R.id.find_kongcheng); //空程匹配

        allSrc.setOnClickListener(this);
        pipei.setOnClickListener(this);
        emptyPipei.setOnClickListener(this);
        //loadFragment(ALLSRC);
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment mainFragment = fragmentManager.findFragmentByTag("message");
        if (mainFragment != null) {
            transaction.replace(R.id.fl_content, mainFragment);
            transaction.commit();
        } else {
            loadFragment(ALLSRC);
            allSrc.setTextColor(getResources().getColor(R.color.shen_blue));
            pipei.setTextColor(Color.WHITE);
            emptyPipei.setTextColor(Color.WHITE);
            allSrc.setBackgroundResource(R.drawable.left_bold);
            pipei.setBackgroundResource(R.drawable.right_transparent);
            emptyPipei.setBackgroundResource(R.drawable.right_transparent);
        }

    }
    private void initData()
    {

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

    private void switchFragment(int type) {
        switch (type) {
            case ALLSRC:
                loadFragment(ALLSRC);
                break;
            case ROUTESRC:
                loadFragment(ROUTESRC);
                break;
            case KCSRC:
                loadFragment(KCSRC);
                break;

        }

    }

    private void loadFragment(int type) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (type == ALLSRC) {
            if (all == null) {
                all = new FindAllSrcFragment();

                transaction.add(R.id.find_fl_content, all, "all");
            } else {
                transaction.show(all);
            }
            if (routeSrc != null) {
                transaction.hide(routeSrc);
            }
            if (kcSrc != null)
            {
                transaction.hide(kcSrc);
            }
            currentFragmentType = ALLSRC;
        } else if (type == ROUTESRC) {
            if (all != null) {
                transaction.hide(all);
            }
            if ( kcSrc != null){
                transaction.hide(kcSrc);
            }
            if (routeSrc == null) {
                routeSrc = new FindRouteSrcFragment();
                transaction.add(R.id.find_fl_content, routeSrc, "routeSrc");
            } else {
                transaction.show(routeSrc);
            }

            currentFragmentType = ROUTESRC;
        } else if (type == KCSRC) {
            if (all != null) {
                transaction.hide(all);
            }
            if (routeSrc != null){
                transaction.hide(routeSrc);
            }
            if (kcSrc == null) {
                kcSrc = new FindKCSrcFragment();
                transaction.add(R.id.find_fl_content, kcSrc, "cancle");
            } else {
                transaction.show(kcSrc);
            }

            currentFragmentType = KCSRC;
        }
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.find_all_src:

                allSrc.setTextColor(getResources().getColor(R.color.shen_blue));
                pipei.setTextColor(Color.WHITE);
                emptyPipei.setTextColor(Color.WHITE);
                allSrc.setBackgroundResource(R.drawable.left_bold);
                pipei.setBackgroundResource(R.drawable.right_transparent);
                emptyPipei.setBackgroundResource(R.drawable.right_transparent);
                switchFragment(ALLSRC);
                break;
            case R.id.find_xianlu_pipei:
                pipei.setTextColor(getResources().getColor(R.color.shen_blue));
                allSrc.setTextColor(Color.WHITE);
                emptyPipei.setTextColor(Color.WHITE);
                pipei.setBackgroundResource(R.drawable.left_bold);
                allSrc.setBackgroundResource(R.drawable.left_transparent);
                emptyPipei.setBackgroundResource(R.drawable.right_transparent);
               // getSrcFromside(initJsonData(-1,""),Config.SRC_ROUTE);
                switchFragment(ROUTESRC);
                break;
            case R.id.find_kongcheng:
                emptyPipei.setTextColor(getResources().getColor(R.color.shen_blue));
                allSrc.setTextColor(Color.WHITE);
                pipei.setTextColor(Color.WHITE);
                emptyPipei.setBackgroundResource(R.drawable.right_bold);
                allSrc.setBackgroundResource(R.drawable.left_transparent);
                pipei.setBackgroundResource(R.drawable.left_transparent);
                switchFragment(KCSRC);
                break;



    }
    }


}
