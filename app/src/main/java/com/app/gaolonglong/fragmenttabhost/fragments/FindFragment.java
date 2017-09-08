package com.app.gaolonglong.fragmenttabhost.fragments;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.bean.GetSRCBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yanqi on 2017/8/2.
 */

public class FindFragment extends Fragment implements View.OnClickListener{


    private  View mRootView;
    private View contentView;
    private PopupWindow popMenu;
    private TextView allSrc,pipei,emptyPipei,shifadi,mudidi,carType,tv_time,username,renzheng,xie,zhuang,type,time,car_type;
    private ImageView icon,phone;
    private String guid;
    private String mobile;
    private String key;


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
        return mRootView;
    }
    private void init()
    {
        initView();
    }
    private void  initView()
    {
        allSrc = (TextView) mRootView.findViewById(R.id.find_all_src);        //全部商品
        pipei = (TextView)mRootView.findViewById(R.id.find_pipei);            //匹配商品
        emptyPipei = (TextView)mRootView.findViewById(R.id.find_empty_pipei); //空程匹配
        shifadi = (TextView)mRootView.findViewById(R.id.find_tv_origin);      //始发地
        mudidi = (TextView)mRootView.findViewById(R.id.find_tv_destination);  //目的地
        carType = (TextView)mRootView.findViewById(R.id.find_tv_cartype);     //车型
        tv_time = (TextView)mRootView.findViewById(R.id.find_tv_time);        //装车时间
        icon = (ImageView)getActivity().findViewById(R.id.find_iv_icon);          //头像
        username = (TextView)mRootView.findViewById(R.id.find_tv_name);       //货主姓名
        renzheng = (TextView)mRootView.findViewById(R.id.find_is_renzheng);   //是否是认证用户
        zhuang = (TextView)mRootView.findViewById(R.id.find_addr_zhuang);     //装货地点
        xie = (TextView)mRootView.findViewById(R.id.find_addr_xie);           //卸货地点
        type = (TextView)mRootView.findViewById(R.id.find_type);              //货物类型
        time = (TextView)mRootView.findViewById(R.id.find_time);              //货主想要的装车时间
        car_type = (TextView)mRootView.findViewById(R.id.find_car_type);      //货主想要的车型
        phone = (ImageView)mRootView.findViewById(R.id.find_iv_phone);        //打电话图标

        allSrc.setOnClickListener(this);
        pipei.setOnClickListener(this);
        emptyPipei.setOnClickListener(this);
        shifadi.setOnClickListener(this);

        guid = ToolsUtils.getString(getContext(), Constant.LOGIN_GUID,"");
        mobile = ToolsUtils.getString(getContext(), Constant.MOBILE,"");
        key = ToolsUtils.getString(getContext(), Constant.KEY,"");


        initPopwindow();
       // getSrcFromside();
    }
    private  void initPopwindow()
    {
        contentView=  getActivity().getLayoutInflater().inflate(R.layout.find_poplist,null);
        popMenu = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        popMenu.setOutsideTouchable(true);
        popMenu.setBackgroundDrawable(new BitmapDrawable());
        popMenu.setFocusable(true);
        popMenu.setAnimationStyle(R.style.find_popwin_style);
        popMenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
    }

    /**
     * 根据始发地查找货源
     */
   private void getSrcFromside()
   {
       JSONObject json = new JSONObject();
       try {
           json.put("GUID",guid);
           json.put(Constant.MOBILE,mobile);
           json.put(Constant.KEY,key);
           json.put("fromSite","深圳");
       } catch (JSONException e) {
           e.printStackTrace();
       }
       RetrofitUtils.getRetrofitService()
               .getSRCWithFromside(Constant.MYINFO_PAGENAME, Config.SRC_FROMSIDE,json.toString())
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Subscriber<GetSRCBean>() {
                   @Override
                   public void onCompleted() {

                   }

                   @Override
                   public void onError(Throwable e) {

                   }

                   @Override
                   public void onNext(GetSRCBean getSRCBean) {
                        ToolsUtils.getInstance().toastShowStr(getContext(),getSRCBean.getErrorMsg());
                   }
               });

   }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.find_all_src:
                allSrc.setBackgroundResource(R.drawable.border_content_white);
                allSrc.setTextColor(Color.parseColor("#878787"));
                pipei.setBackgroundResource(R.drawable.border_white);
                pipei.setTextColor(Color.parseColor("#ffffff"));
                emptyPipei.setBackgroundResource(R.drawable.border_white);
                emptyPipei.setTextColor(Color.parseColor("#ffffff"));
                break;
            case R.id.find_pipei:
                allSrc.setBackgroundResource(R.drawable.border_white);
                allSrc.setTextColor(Color.parseColor("#ffffff"));
                pipei.setBackgroundResource(R.drawable.border_content_white);
                pipei.setTextColor(Color.parseColor("#878787"));
                emptyPipei.setBackgroundResource(R.drawable.border_white);
                emptyPipei.setTextColor(Color.parseColor("#ffffff"));
                break;
            case R.id.find_empty_pipei:
                allSrc.setBackgroundResource(R.drawable.border_white);
                allSrc.setTextColor(Color.parseColor("#ffffff"));
                pipei.setBackgroundResource(R.drawable.border_white);
                pipei.setTextColor(Color.parseColor("#ffffff"));
                emptyPipei.setBackgroundResource(R.drawable.border_content_white);
                emptyPipei.setTextColor(Color.parseColor("#878787"));
                break;
            case R.id.find_tv_origin:
                popMenu.showAsDropDown(shifadi,0,2);
                break;
    }
    }
}
