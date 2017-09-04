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
import com.app.gaolonglong.fragmenttabhost.activities.MyCarTeamActivity;
import com.app.gaolonglong.fragmenttabhost.activities.MyCardListActivity;
import com.app.gaolonglong.fragmenttabhost.activities.MyMessageActivity;
import com.app.gaolonglong.fragmenttabhost.activities.MyRouteListActivity;
import com.app.gaolonglong.fragmenttabhost.activities.MyWallteActivity;
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

    @BindViews({R.id.mine_setting,R.id.mine_username,
                R.id.mine_tel})
    public List<TextView> mList;

    @BindViews({R.id.renzheng_rl,R.id.mine_rl_message,
                R.id.mine_rl_card,R.id.mine_rl_car,
                R.id.mine_rl_bzjxq,R.id.mine_rl_carteam,
                R.id.mine_rl_wallte,R.id.mine_rl_szmx,
                R.id.mine_rl_route})
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
        rlList.get(1).setOnClickListener(this);
        rlList.get(2).setOnClickListener(this);
        rlList.get(3).setOnClickListener(this);
        rlList.get(4).setOnClickListener(this);
        rlList.get(5).setOnClickListener(this);
        rlList.get(6).setOnClickListener(this);
        rlList.get(7).setOnClickListener(this);
        rlList.get(8).setOnClickListener(this);

        checkInfo();
    }

    /**
     * 验证登录 并修改界面信息显示
     */
    private void checkInfo()
    {
        //获取保存的用户信息
        String guid = ToolsUtils.getString(getActivity(),Constant.LOGIN_GUID,"");
        //ToolsUtils.getInstance().toastShowStr(getActivity(),guid);
        if(!TextUtils.isEmpty(guid))
        {

            isLogin =true;
            mList.get(1).setText(ToolsUtils.getString(getActivity(),Constant.MOBILE,""));
            mList.get(2).setText(ToolsUtils.getString(getActivity(),Constant.MOBILE,""));
            ToolsUtils.getInstance().toastShowStr(getActivity(),ToolsUtils.getString(getActivity(),Constant.USERNAME,""));
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
            case R.id.mine_rl_message:
                startActivity(new Intent(getActivity(), MyMessageActivity.class));
                break;
            case R.id.mine_rl_wallte:
                startActivity(new Intent(getActivity(), MyWallteActivity.class));
                break;
            case R.id.mine_rl_szmx:

                break;
            case R.id.mine_rl_card:
                startActivity(new Intent(getActivity(), MyCardListActivity.class));
                break;
            case R.id.mine_rl_bzjxq:

                break;
            case R.id.mine_rl_car:

                break;
            case R.id.mine_rl_carteam:
                startActivity(new Intent(getActivity(), MyCarTeamActivity.class));
                break;
            case R.id.mine_rl_route:
                startActivity(new Intent(getActivity(), MyRouteListActivity.class));
                break;
        }
    }
}
