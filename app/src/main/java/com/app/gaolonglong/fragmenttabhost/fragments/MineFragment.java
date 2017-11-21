package com.app.gaolonglong.fragmenttabhost.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
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
import com.app.gaolonglong.fragmenttabhost.activities.AddressActivity;
import com.app.gaolonglong.fragmenttabhost.activities.CarInfoActivity;
import com.app.gaolonglong.fragmenttabhost.activities.CompanyInfoActivity;
import com.app.gaolonglong.fragmenttabhost.activities.FaPiaoSettingActivity;
import com.app.gaolonglong.fragmenttabhost.activities.LoginActivity;
import com.app.gaolonglong.fragmenttabhost.activities.MyCarTeamActivity;
import com.app.gaolonglong.fragmenttabhost.activities.MyCardListActivity;
import com.app.gaolonglong.fragmenttabhost.activities.MyMessageActivity;
import com.app.gaolonglong.fragmenttabhost.activities.MyRouteListActivity;
import com.app.gaolonglong.fragmenttabhost.activities.MyWallteActivity;
import com.app.gaolonglong.fragmenttabhost.activities.RenzhengMainActivity;
import com.app.gaolonglong.fragmenttabhost.activities.SelectDriverActivity;
import com.app.gaolonglong.fragmenttabhost.activities.SettingActivity;
import com.app.gaolonglong.fragmenttabhost.bean.LoginBean;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.GetUserInfoUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.app.gaolonglong.fragmenttabhost.view.CommomDialog;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Created by donglinghao on 2016-01-28.
 */
public class MineFragment extends Fragment implements View.OnClickListener {

    private View mRootView;

    private Intent intent, intent1;

    private Boolean isLogin = ToolsUtils.getInstance().isLogin(getContext());

    public String login = "请先登录";

    private String tishitxt = "您已认证过了，如果重新提交资料需要重新认证,确定进行此操作吗？？";


    @BindViews({R.id.mine_setting, R.id.mine_username,
            R.id.mine_tel, R.id.mine_is_renzheng})
    public List<TextView> mList;

    @BindView(R.id.mine_fragment_jiedan)
    public TextView jiedan;

    @BindViews({R.id.renzheng_rl, R.id.mine_rl_message,
            R.id.mine_rl_card, R.id.mine_rl_car,
            R.id.mine_rl_bzjxq, R.id.mine_rl_carteam,
            R.id.mine_rl_wallte, R.id.mine_rl_szmx,
            R.id.mine_rl_route, R.id.mine_rl_drivers,
            R.id.mine_rl_company, R.id.mine_rl_fapiao})
    public List<RelativeLayout> rlList;


    @BindView(R.id.mine_icon)
    public SimpleDraweeView logo;
    private String userinfo;
    private boolean isRenzheng;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            Log.e("666", "MineFragment");
            mRootView = inflater.inflate(R.layout.mine_fragment, container, false);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       // init();
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    public void init() {
        initView();
        showItem();

    }

    public void initView() {
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
        rlList.get(9).setOnClickListener(this);
        rlList.get(10).setOnClickListener(this);
        rlList.get(11).setOnClickListener(this);
        // if (GetUserInfoUtils.getUserType(getContext()).equals(""))
        isRenzheng = GetUserInfoUtils.isRenzheng(getContext());
        jiedan.setText("接单" + GetUserInfoUtils.getDriverbill(getContext()) + "次");
        checkInfo();
    }

    /**
     * 验证登录 并修改界面信息显示
     */
    private void checkInfo() {
        //获取保存的用户信息
        String guid = GetUserInfoUtils.getGuid(getContext());
        if (!TextUtils.isEmpty(guid)) {

            isLogin = true;
            mList.get(1).setText(GetUserInfoUtils.getUserName(getContext()));
            mList.get(2).setText(GetUserInfoUtils.getMobile(getContext()));
            //ToolsUtils.getInstance().toastShowStr(getActivity(), GetUserInfoUtils.getVtrueName(getContext()));
            String url = ToolsUtils.getString(getContext(), Constant.HEADLOGO, "");
            logo.setImageURI(Uri.parse(url));
        } else {
            mList.get(1).setText("请点击登录");
            mList.get(1).setEnabled(true);
            logo.setEnabled(true);

            logo.setOnClickListener(this);
            mList.get(1).setOnClickListener(this);
        }
    }

    /**
     * 根据用户的角色显示或者隐藏一些选项
     */
    private void showItem() {
        String usertype = GetUserInfoUtils.getUserType(getContext());
        String usertypename = "";
        if (usertype.equals("2")) {//个体司机
            rlList.get(5).setVisibility(View.GONE);
            usertypename = "个体司机认证";
            rlList.get(11).setVisibility(View.GONE);
        }
        if (usertype.equals("3")) {
            usertypename = "车队司机认证";
            rlList.get(5).setVisibility(View.GONE);
            rlList.get(3).setVisibility(View.GONE);
            rlList.get(11).setVisibility(View.GONE);
        }
        if (usertype.equals("4")) {
            usertypename = "车队认证";
            rlList.get(3).setVisibility(View.GONE);
            rlList.get(9).setVisibility(View.VISIBLE);
            rlList.get(10).setVisibility(View.VISIBLE);
            rlList.get(11).setVisibility(View.GONE);
        }
        if (GetUserInfoUtils.getVtrueName(getContext()).equals("0")) {
            mList.get(3).setText("未认证");
        } else if (GetUserInfoUtils.getVtrueName(getContext()).equals("1")) {
            mList.get(3).setText(usertypename + "已提交");
        } else if (GetUserInfoUtils.getVtrueName(getContext()).equals("2")) {
            mList.get(3).setText(usertypename + "不合格");
        } else if (GetUserInfoUtils.getVtrueName(getContext()).equals("9")) {
            mList.get(3).setText(usertypename + "已认证");
        }
        Log.e("vtruneme", GetUserInfoUtils.getVtrueName(getContext()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_setting: //设置界面
                intent = new Intent(getActivity(), SettingActivity.class);
                //intent = new Intent(getActivity(), AddressActivity.class);
                startActivity(intent);
                break;

            case R.id.renzheng_rl:  //认证界面
                if (isLogin) {
                    if (GetUserInfoUtils.getVtrueName(getContext()).equals("9") || GetUserInfoUtils.getVcompany(getContext()).equals("9")) {
                        CommomDialog dialog = new CommomDialog(getContext(), R.style.dialog, tishitxt, new CommomDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                dialog.dismiss();
                                if (confirm) {
                                    intent1 = new Intent(getActivity(), RenzhengMainActivity.class);
                                    startActivity(intent1);
                                }
                            }
                        });
                        dialog.setTitle("提示");
                        dialog.show();

                    }else {
                        startActivity(new Intent(getActivity(),RenzhengMainActivity.class));
                    }

                } else {
                    ToolsUtils.getInstance().toastShowStr(getActivity(), login);
                }
                break;

            case R.id.mine_username: //登录界面
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;

            case R.id.mine_icon: //登录界面
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.mine_rl_message:
                if (isLogin) {
                    if (GetUserInfoUtils.getVtrueName(getContext()).equals("9") || GetUserInfoUtils.getVcompany(getContext()).equals("9")) {
                        startActivity(new Intent(getActivity(), MyMessageActivity.class));
                    } else {
                        //ToolsUtils.getInstance().toastShowStr(getActivity(), "请先完成认证");
                        ToolsUtils.toRenzhengMain(getActivity());
                    }

                } else {
                    ToolsUtils.getInstance().toastShowStr(getActivity(), login);
                }

                break;
            case R.id.mine_rl_wallte:
                if (isLogin) {

                        startActivity(new Intent(getActivity(), MyWallteActivity.class));
                } else {
                    ToolsUtils.getInstance().toastShowStr(getActivity(), login);
                }

                break;
            case R.id.mine_rl_szmx:

                break;
            case R.id.mine_rl_card:
                if (isLogin) {
                    if (GetUserInfoUtils.getVtrueName(getContext()).equals("9") || GetUserInfoUtils.getVcompany(getContext()).equals("9")) {
                        startActivity(new Intent(getActivity(), MyCardListActivity.class));
                    } else {
                        //ToolsUtils.getInstance().toastShowStr(getActivity(), "请先完成认证");
                        ToolsUtils.toRenzhengMain(getActivity());
                    }

                } else {
                    ToolsUtils.getInstance().toastShowStr(getActivity(), login);
                }

                break;
            case R.id.mine_rl_bzjxq:

                break;
            case R.id.mine_rl_car:
                if (isLogin) {
                    if (isRenzheng)
                    {
                    startActivity(new Intent(getContext(), CarInfoActivity.class));
                    }else {
                        //ToolsUtils.getInstance().toastShowStr(getActivity(),"请先完成认证");
                        ToolsUtils.toRenzhengMain(getActivity());
                    }

                } else {
                    ToolsUtils.getInstance().toastShowStr(getActivity(), login);
                }
                break;
            case R.id.mine_rl_carteam:
                if (isLogin) {
                    if (GetUserInfoUtils.getVtrueName(getContext()).equals("9") || GetUserInfoUtils.getVcompany(getContext()).equals("9")) {
                        startActivity(new Intent(getActivity(), MyCarTeamActivity.class));
                    } else {
                        //ToolsUtils.getInstance().toastShowStr(getActivity(), "请先完成认证");
                        ToolsUtils.toRenzhengMain(getActivity());
                    }

                } else {
                    ToolsUtils.getInstance().toastShowStr(getActivity(), login);
                }

                break;
            case R.id.mine_rl_route:
                if (isLogin) {
                    if (GetUserInfoUtils.getVtrueName(getContext()).equals("9") || GetUserInfoUtils.getVcompany(getContext()).equals("9")) {
                        startActivity(new Intent(getActivity(), MyRouteListActivity.class));
                    } else {
                        //ToolsUtils.getInstance().toastShowStr(getActivity(), "请先完成认证");
                        ToolsUtils.toRenzhengMain(getActivity());
                    }

                } else {
                    ToolsUtils.getInstance().toastShowStr(getActivity(), login);
                }

                break;
            case R.id.mine_rl_drivers:
                if (isRenzheng) {
                    Intent intent = new Intent(getContext(), SelectDriverActivity.class);
                    intent.putExtra("flags", "mineFragment");
                    startActivity(intent);
                } else {
                    //ToolsUtils.getInstance().toastShowStr(getActivity(), "请先完成认证");
                    ToolsUtils.toRenzhengMain(getActivity());
                }

                break;
            case R.id.mine_rl_company:
                if (isRenzheng) {
                    startActivity(new Intent(getContext(), CompanyInfoActivity.class));
                } else {
                    //ToolsUtils.getInstance().toastShowStr(getActivity(), "请先完成认证");
                    ToolsUtils.toRenzhengMain(getActivity());
                }
                break;
            case R.id.mine_rl_fapiao:
                if (isRenzheng) {
                    startActivity(new Intent(getContext(), FaPiaoSettingActivity.class));
                } else {
                    //ToolsUtils.getInstance().toastShowStr(getActivity(), "请先完成认证");
                    ToolsUtils.toRenzhengMain(getActivity());
                }
                break;

        }
    }

}
