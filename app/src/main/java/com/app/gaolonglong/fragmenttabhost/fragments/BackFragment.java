package com.app.gaolonglong.fragmenttabhost.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.activities.AddReleaseActivity;
import com.app.gaolonglong.fragmenttabhost.adapter.InvitedSrcAdapter;
import com.app.gaolonglong.fragmenttabhost.adapter.ReleaseAdapter;
import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.bean.GetSRCBean;
import com.app.gaolonglong.fragmenttabhost.bean.InvitedSrcBean;
import com.app.gaolonglong.fragmenttabhost.bean.ReleaseBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.GetUserInfoUtils;
import com.app.gaolonglong.fragmenttabhost.utils.JsonUtils;
import com.app.gaolonglong.fragmenttabhost.utils.LoadingDialog;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.app.gaolonglong.fragmenttabhost.view.CommomDialog;
import com.app.gaolonglong.fragmenttabhost.view.EmptyLayout;
import com.app.gaolonglong.fragmenttabhost.view.MyLinearLayoutManager;
import com.app.gaolonglong.fragmenttabhost.view.RecycleViewDivider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yanqi on 2017/8/2.
 */

public class BackFragment extends Fragment implements View.OnClickListener{
    private View mRootView;

    @BindView(R.id.iv_toaddrelease)
    public ImageView rl;



    @BindViews({R.id.release_list_fabu,R.id.release_list_invited})
    public List<TextView> top_item;

    private String guid;
    private String mobile;
    private String key;
    private LoadingDialog dialog;
    private  List<ReleaseBean.DataBean> list = new ArrayList<ReleaseBean.DataBean>();
    private  List<ReleaseBean.DataBean> canclelist = new ArrayList<ReleaseBean.DataBean>();

    private ReleaseAdapter adapter;
    private JSONObject mJson;
    private int positon = 0;
    private ReleaseAdapter cancleAdapter;
    private InvitedSrcAdapter invitedSrcAdapter;

    public static final int ALLSRC = 1;
    public static final int ROUTESRC = 2;
    private KCDataFragment  kcFragment;
    private InvitedSrcFragment  invited;
    public int currentFragmentType = -1;


    @OnClick(R.id.iv_toaddrelease)
    public void release()
    {
        String usertype = GetUserInfoUtils.getUserType(getContext());
        if (ToolsUtils.getInstance().isLogin(getContext()))
        {
            if(usertype.equals("3"))
            {
                new CommomDialog(getContext(),"对不起,您的权限不够").setTitle("友情提示").show();
            }
            else
            {
                if (GetUserInfoUtils.isRenzheng(getContext())){
                    startActivityForResult(new Intent(getActivity(),AddReleaseActivity.class),1);
                }else {
                    ToolsUtils.toRenzhengMain(getActivity());
                }
            }
        }
        else {
            ToolsUtils.getInstance().toastShowStr(getContext(),"请先登录");
        }
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
       // init();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //getData();
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    private void init()
    {
        top_item.get(0).setOnClickListener(this);
        top_item.get(1).setOnClickListener(this);
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment mainFragment = fragmentManager.findFragmentByTag("message");
        if (mainFragment != null) {
            transaction.replace(R.id.fl_content, mainFragment);
            transaction.commit();
        } else {
            loadFragment(ALLSRC);
        }
    }

    private void switchFragment(int type) {
        switch (type) {
            case ALLSRC:
                loadFragment(ALLSRC);
                break;
            case ROUTESRC:
                loadFragment(ROUTESRC);
                break;
        }

    }

    private void loadFragment(int type) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (type == ALLSRC) {
            if (invited != null) {
                transaction.hide(invited);
            }
            if (kcFragment == null) {
                kcFragment = new KCDataFragment();

                transaction.add(R.id.release_content, kcFragment, "kc");
            } else {
                transaction.show(kcFragment);
            }


            currentFragmentType = ALLSRC;
        } else if (type == ROUTESRC) {
            if (kcFragment != null) {
                transaction.hide(kcFragment);
            }

            if (invited == null) {
                invited = new InvitedSrcFragment();
                transaction.add(R.id.release_content, invited, "invited");
            } else {
                transaction.show(invited);
            }

            currentFragmentType = ROUTESRC;
        }
        transaction.commitAllowingStateLoss();
    }

    private void getCancleData()
    {
        RetrofitUtils.getRetrofitService()
                .getFabuRelease(Constant.MYINFO_PAGENAME, Config.RELEASE_CANCEL,mJson.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ReleaseBean>() {
                    @Override
                    public void onCompleted() {
                        dialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNext(ReleaseBean releaseBean) {
                        //ToolsUtils.getInstance().toastShowStr(getContext(),releaseBean.getErrorMsg());
                        if(releaseBean.getData().size() == 0)
                        {

                        }
                        else {
                            canclelist.clear();
                            canclelist.addAll(releaseBean.getData());
                            cancleAdapter.notifyDataSetChanged();
                        }
                        dialog.dismiss();


                    }
                });
    }



    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.release_list_fabu:
                top_item.get(0).setBackgroundResource(R.drawable.buttom_stroke);
                top_item.get(1).setBackgroundColor(Color.WHITE);
                top_item.get(0).setTextColor(this.getResources().getColor(R.color.shen_blue));
                top_item.get(1).setTextColor(this.getResources().getColor(R.color.black_6d));
                switchFragment(ALLSRC);
               // rcv.get(0).setAdapter(adapter);

               // adapter.notifyDataSetChanged();
                break;
            case R.id.release_list_invited:
                top_item.get(1).setBackgroundResource(R.drawable.buttom_stroke);
                top_item.get(0).setBackgroundColor(Color.WHITE);
                top_item.get(1).setTextColor(this.getResources().getColor(R.color.shen_blue));
                top_item.get(0).setTextColor(this.getResources().getColor(R.color.black_6d));
                switchFragment(ROUTESRC);
                //rcv.get(1).setVisibility(View.VISIBLE);
                /*rcv.get(0).setAdapter(null);
                adapter.notifyDataSetChanged();*/
                break;
        }
    }
}
