package com.app.gaolonglong.fragmenttabhost.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.activities.LoginActivity;
import com.app.gaolonglong.fragmenttabhost.activities.RenzhengMainActivity;
import com.app.gaolonglong.fragmenttabhost.activities.SettingActivity;
import com.app.gaolonglong.fragmenttabhost.bean.LoginBean;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Created by donglinghao on 2016-01-28.
 */
public class MineFragment extends Fragment implements View.OnClickListener{

    private View mRootView;

    private Intent intent,intent1;

    private Boolean isLogin = false;

    public String login="请先登录";

    @BindViews({R.id.mine_setting,R.id.mine_username})
    public List<TextView> mList;

    @BindViews({R.id.renzheng_rl})
    public List<RelativeLayout> rlList;

    @BindView(R.id.mine_icon)
    public SimpleDraweeView logo;
    private String userinfo;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null){
            Log.e("666","MineFragment");
            mRootView = inflater.inflate(R.layout.mine_fragment,container,false);
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
    public void init()
    {
        initView();
    }

    public void initView()
    {
        mList.get(0).setOnClickListener(this);
        rlList.get(0).setOnClickListener(this);
        //获取保存的用户信息
        userinfo = ToolsUtils.getString(getActivity(), Constant.USRE_INFO,"");
        if(!TextUtils.isEmpty(userinfo))
        {
            isLogin =true;
            try {
                List<LoginBean.DataBean> info = ToolsUtils.String2SceneList(userinfo);
                ToolsUtils.getInstance().toastShowStr(getActivity(),info.get(0).getMobile());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        else
        {
            mList.get(1).setText("请点击登录");
            mList.get(1).setEnabled(true);
            logo.setEnabled(true);

            logo.setOnClickListener(this);
            mList.get(1).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.mine_setting: //设置界面
                intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;

            case R.id.renzheng_rl:  //认证界面
                /*if (isLogin)
                {*/
                    intent1 =new Intent(getActivity(), RenzhengMainActivity.class);
                    startActivity(intent1);
               /* }
                else{ToolsUtils.getInstance().toastShowStr(getActivity(),login);}*/
                break;

            case R.id.mine_username: //登录界面
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;

            case R.id.mine_icon: //登录界面
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
        }
    }
}
