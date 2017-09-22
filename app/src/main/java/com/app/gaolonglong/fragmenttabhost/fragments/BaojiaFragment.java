package com.app.gaolonglong.fragmenttabhost.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.activities.BaojiaDetailActivity;
import com.app.gaolonglong.fragmenttabhost.adapter.BaojiaListAdapter;
import com.app.gaolonglong.fragmenttabhost.bean.BaojiaInfoBean;
import com.app.gaolonglong.fragmenttabhost.bean.BaojiaListBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.app.gaolonglong.fragmenttabhost.view.MyLinearLayoutManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yanqi on 2017/8/2.
 */

public class BaojiaFragment extends Fragment {
    private View mRootView;

    @BindView(R.id.title_back)
    ImageView back;

    @BindViews({R.id.title_back_txt,R.id.top_title})
    public List<TextView> mTextView;
    @BindView(R.id.baojia_fragment_list)
    public RecyclerView rcl;

    private List<BaojiaListBean.DataBean> list = new ArrayList<BaojiaListBean.DataBean>();
    private BaojiaListAdapter adapter;

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
        if (ToolsUtils.getInstance().isLogin(getContext()))
        {
            //ToolsUtils.getInstance().toastShowStr(getContext(),"success");
        }else
        {
            //ToolsUtils.getInstance().toastShowStr(getContext(),"fail");
        }
    }
    private void initView()
    {
        mTextView.get(0).setVisibility(View.INVISIBLE);
        back.setVisibility(View.INVISIBLE);
        mTextView.get(1).setText("报价");
        JSONObject json = new JSONObject();
        try {
            json.put("GUID",ToolsUtils.getString(getContext(),Constant.LOGIN_GUID,""));
            json.put(Constant.MOBILE,ToolsUtils.getString(getContext(),Constant.MOBILE,""));
            json.put(Constant.KEY,ToolsUtils.getString(getContext(),Constant.KEY,""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter = new BaojiaListAdapter(getContext(),list);
        MyLinearLayoutManager manager = new MyLinearLayoutManager(getContext());
        rcl.setLayoutManager(manager);
        rcl.setAdapter(adapter);
        getBaojiaList(json.toString());

        adapter.setOnclick(new BaojiaListAdapter.OnClickListener() {
            @Override
            public void onOlick(int postion, String tel) {
                Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));//跳转到拨号界面，同时传递电话号码
                startActivity(dialIntent);
            }
        });
        adapter.setItemClick(new BaojiaListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view,BaojiaInfoBean bean) {
                Intent intent = new Intent(getContext(), BaojiaDetailActivity.class);
                intent.putExtra("baojaiInfo",bean);
                startActivity(intent);
                //ToolsUtils.getInstance().toastShowStr(getContext(),bean.getTotalchargeM());
            }
        });
    }
    private void getBaojiaList(String json)
    {
        RetrofitUtils.getRetrofitService()
                .getBaojiaList(Constant.PRICE_PAGENAME, Config.GET_BAOJIALIST,json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaojiaListBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaojiaListBean baojiaListBean) {
                        Log.e("999999999999999999",baojiaListBean.getErrorMsg());
                        ToolsUtils.getInstance().toastShowStr(getContext(),baojiaListBean.getErrorMsg());
                        list.clear();
                        list.addAll(baojiaListBean.getData());
                        adapter.notifyDataSetChanged();
                    }
                });

    }
}
