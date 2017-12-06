package com.mr.truck.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mr.truck.R;
import com.mr.truck.activities.BaojiaEditActivity;
import com.mr.truck.activities.FindDetailActivity;
import com.mr.truck.adapter.BaojiaListAdapter;
import com.mr.truck.bean.BaojiaListBean;
import com.mr.truck.bean.GetCodeBean;
import com.mr.truck.bean.ToSrcDetailBean;
import com.mr.truck.config.Config;
import com.mr.truck.config.Constant;
import com.mr.truck.utils.GetUserInfoUtils;
import com.mr.truck.utils.JsonUtils;
import com.mr.truck.utils.LoadingDialog;
import com.mr.truck.utils.RetrofitUtils;
import com.mr.truck.utils.ThreadManager;
import com.mr.truck.utils.ToolsUtils;
import com.mr.truck.view.EmptyLayout;
import com.mr.truck.view.MyLinearLayoutManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yanqi on 2017/8/2.
 */

public class BaojiaFragment extends Fragment implements View.OnClickListener{
    private View mRootView;

    @BindView(R.id.title_back)
    ImageView back;

    @BindViews({R.id.title_back_txt, R.id.top_title})
    public List<TextView> mTextView;
    @BindView(R.id.baojia_fragment_list)
    public RecyclerView rcl;

    @BindView(R.id.baojia_refresh)
    public SwipeRefreshLayout refresh;

    @BindView(R.id.baojia_fragment_empty)
    public EmptyLayout empty;

    @BindView(R.id.baojia_fragment_main)
    public LinearLayout main;

    @BindViews({R.id.baojia_fragment_doing,R.id.baojia_fragment_cancel,R.id.baojia_fragment_product})
    public List<TextView> select;

    private List<BaojiaListBean.DataBean> list = new ArrayList<BaojiaListBean.DataBean>();
    private BaojiaListAdapter adapter;
    private String guid;
    private String mobile;
    private String key;
    private JSONObject json;
    private int SELECT =0;
    private LoadingDialog loadingDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            //Log.e("666", "MineFragment");
            mRootView = inflater.inflate(R.layout.baojia_fragment, container, false);
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

    private void init() {
        initView();
        if (ToolsUtils.getInstance().isLogin(getContext())) {
            //ToolsUtils.getInstance().toastShowStr(getContext(),"success");
        } else {
            //ToolsUtils.getInstance().toastShowStr(getContext(),"fail");
        }
    }

    private void initView() {
        loadingDialog = LoadingDialog.showDialog(getContext());
        loadingDialog.show();
        //Log.e("threadname111",Thread.currentThread().getName());
        guid = ToolsUtils.getString(getContext(), Constant.LOGIN_GUID, "");
        mobile = ToolsUtils.getString(getContext(), Constant.MOBILE, "");
        key = ToolsUtils.getString(getContext(), Constant.KEY, "");
        mTextView.get(0).setVisibility(View.INVISIBLE);
        back.setVisibility(View.INVISIBLE);
        mTextView.get(1).setText("报价");
        select.get(0).setOnClickListener(this);
        select.get(1).setOnClickListener(this);
        select.get(2).setOnClickListener(this);
        json = new JSONObject();
        try {
            json.put("GUID", guid);
            json.put(Constant.MOBILE, mobile);
            json.put(Constant.KEY, key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter = new BaojiaListAdapter(getContext(), list);
        MyLinearLayoutManager manager = new MyLinearLayoutManager(getContext());
        rcl.setLayoutManager(manager);
        rcl.setAdapter(adapter);
        rcl.setHasFixedSize(true);
        rcl.setNestedScrollingEnabled(false);

        ThreadManager.getNormalPool().execute(new MyRunnabel());


        adapter.setOnclick(new BaojiaListAdapter.OnClickListener() {
            @Override
            public void onOlick(int postion, String tel, String caragoGUID, String time, String flag, ToSrcDetailBean bean) {
                if (flag.equals("phone")) {
                    /*Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + list.get(postion).getOwnerphone()));//跳转到拨号界面，同时传递电话号码
                    startActivity(dialIntent);*/
                } else if (flag.equals("caozuo")) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("GUID", guid);
                    map.put(Constant.MOBILE, mobile);
                    map.put(Constant.KEY, key);
                    map.put("cargopricesGUID", list.get(postion).getCargoPricesGUID() + "");
                    map.put("UpdatePriceTime", list.get(postion).getUpdatePriceTime());
                    cazuo(JsonUtils.getInstance().getJsonStr(map));
                } else if (flag.equals("cancel")) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("GUID", guid);
                    map.put(Constant.MOBILE, mobile);
                    map.put(Constant.KEY, key);
                    map.put("cargopricesGUID", list.get(postion).getCargoPricesGUID() + "");
                    map.put("UpdatePriceTime", list.get(postion).getUpdatePriceTime());
                    cancel(JsonUtils.getInstance().getJsonStr(map), postion);
                }else if (flag.equals("cxbj")){
                    Intent intent = new Intent(getContext(), BaojiaEditActivity.class);
                    intent.putExtra("srcdetail",bean);
                    intent.putExtra("flag","baojiaFragment");
                    intent.putExtra("baojiastatus",list.get(postion).getCargoPriceState());
                    intent.putExtra("Bidder",list.get(postion).getBidder());
                    intent.putExtra("cargopricesGUID",list.get(postion).getCargoPricesGUID());
                    intent.putExtra("UpdatePriceTime",list.get(postion).getUpdatePriceTime());
                    intent.putExtra("msg",list.get(postion).getFeeremark());
                    startActivity(intent);
                }else if (flag.equals("ckhy")){

                    Intent intent = new Intent(getContext(), FindDetailActivity.class);
                    intent.putExtra("findSrc", bean);
                    intent.putExtra("flag","baojiaFragment");
                    startActivity(intent);

                }
            }
        });
        adapter.setItemClick(new BaojiaListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ToSrcDetailBean bean,int position) {
                /*Intent intent = new Intent(getContext(), BaojiaDetailActivity.class);
                intent.putExtra("baojaiInfo", bean);
                startActivity(intent);*/
                Intent intent = new Intent(getContext(), BaojiaEditActivity.class);
                intent.putExtra("srcdetail",bean);
                intent.putExtra("flag","baojiaFragment");
                intent.putExtra("baojiastatus",list.get(position).getCargoPriceState());
                intent.putExtra("Bidder",list.get(position).getBidder());
                intent.putExtra("cargopricesGUID",list.get(position).getCargoPricesGUID());
                intent.putExtra("UpdatePriceTime",list.get(position).getUpdatePriceTime());
                intent.putExtra("msg",list.get(position).getFeeremark());
                startActivity(intent);
                //ToolsUtils.getInstance().toastShowStr(getContext(),bean.getCargopricesGUID());
            }
        });

        refresh.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onResume();
                refresh.setRefreshing(false);
            }
        });
    }

    private void cazuo(String json) {
        RetrofitUtils.getRetrofitService()
                .agreeBaojia(Constant.PRICE_PAGENAME, Config.AGREE_BAOJIA, json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetCodeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetCodeBean getCodeBean) {
                        ToolsUtils.getInstance().toastShowStr(getContext(), getCodeBean.getErrorMsg());
                        if (getCodeBean.getErrorCode().equals("200")) {
                            onResume();
                        }
                    }
                });
    }

    private void cancel(final String json, final int position) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RetrofitUtils.getRetrofitService()
                        .cancelBaojia(Constant.PRICE_PAGENAME, Config.CANCEL_BAOJIA, json)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<GetCodeBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(GetCodeBean getCodeBean) {
                                ToolsUtils.getInstance().toastShowStr(getContext(), getCodeBean.getErrorMsg());
                                if (getCodeBean.getErrorCode().equals("200")) {
                                    //getBaojiaList();
                                    list.get(position).setCargoPriceState("2");
                                    adapter.notifyDataSetChanged();
                                    onResume();
                                }
                            }
                        });
            }
        }).start();


    }


    private String listType = "";
    private class MyRunnabel implements Runnable {
        @Override
        public void run() {
           //getBaojiaList(json.toString());
            //Log.e("threadname2",Thread.currentThread().getName());
            RetrofitUtils.getRetrofitService()
                    .getBaojiaList(Constant.PRICE_PAGENAME, Config.GET_BAOJIALIST, json.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<BaojiaListBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            loadingDialog.dismiss();
                        }

                        @Override
                        public void onNext(BaojiaListBean baojiaListBean) {
                            loadingDialog.dismiss();
                            GetUserInfoUtils.checkKeyValue(getContext(),baojiaListBean.getErrorCode());
                            list.clear();

                            if (!GetUserInfoUtils.getUserType(getContext()).equals("3")) {
                                int listSize = (baojiaListBean.getData().size());
                                if (SELECT == 0){
                                    listType = "0";
                                }else {
                                    listType = "2";
                                }
                                for (int i=0;i<listSize;i++){
                                    if (baojiaListBean.getData().get(i).getCargoPriceState().equals(listType)){
                                        list.add(baojiaListBean.getData().get(i));
                                    }
                                }
                                //list.addAll(baojiaListBean.getData());
                            }
                            //ToolsUtils.getInstance().toastShowStr(getContext(), list.size() + "");
                            //Log.e("baojiasize",list.size()+"");
                            if (list.size() == 0) {
                                empty.setVisibility(View.VISIBLE);
                                main.setVisibility(View.GONE);
                                empty.setErrorImag(R.drawable.nobaojia, "无报价信息");
                                //empty.setNoDataContent("没有数据");
                                //empty.setErrorType(EmptyLayout.NODATA);
                            } else {
                                main.setVisibility(View.VISIBLE);
                                empty.setVisibility(View.GONE);
                                adapter.notifyDataSetChanged();
                            }

                        }
                    });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.baojia_fragment_doing:
                select.get(0).setBackgroundResource(R.drawable.buttom_stroke);
                select.get(1).setBackgroundColor(Color.WHITE);
                select.get(2).setBackgroundColor(Color.WHITE);
                select.get(0).setTextColor(this.getResources().getColor(R.color.shen_blue));
                select.get(1).setTextColor(this.getResources().getColor(R.color.black_6d));
                select.get(2).setTextColor(this.getResources().getColor(R.color.black_6d));
                SELECT = 0;
                ThreadManager.getNormalPool().execute(new MyRunnabel());
                break;
            case R.id.baojia_fragment_cancel:
                select.get(0).setBackgroundColor(Color.WHITE);
                select.get(2).setBackgroundColor(Color.WHITE);
                select.get(1).setBackgroundResource(R.drawable.buttom_stroke);
                select.get(1).setTextColor(this.getResources().getColor(R.color.shen_blue));
                select.get(0).setTextColor(this.getResources().getColor(R.color.black_6d));
                select.get(2).setTextColor(this.getResources().getColor(R.color.black_6d));
                SELECT = 1;
                ThreadManager.getNormalPool().execute(new MyRunnabel());
                break;
            case R.id.baojia_fragment_product:
                select.get(0).setBackgroundColor(Color.WHITE);
                select.get(1).setBackgroundColor(Color.WHITE);
                select.get(2).setBackgroundResource(R.drawable.buttom_stroke);
                select.get(2).setTextColor(this.getResources().getColor(R.color.shen_blue));
                select.get(0).setTextColor(this.getResources().getColor(R.color.black_6d));
                select.get(1).setTextColor(this.getResources().getColor(R.color.black_6d));
                break;
        }
    }
}
