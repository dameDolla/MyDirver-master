package com.app.gaolonglong.fragmenttabhost.activities;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.SidePropagation;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.adapter.CityListAdapter;
import com.app.gaolonglong.fragmenttabhost.adapter.ResultListAdapter;
import com.app.gaolonglong.fragmenttabhost.bean.City;
import com.app.gaolonglong.fragmenttabhost.bean.LocationInfo;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.DBManager;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.app.gaolonglong.fragmenttabhost.view.SideLetterBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yanqi on 2017/9/4.
 */

public class SearchAddrActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.listview_all_city)
    public ListView listView;

    @BindView(R.id.tv_letter_overlay)
    public TextView overlay;

    @BindView(R.id.side_letter_bar)
    public SideLetterBar letter_bar;

    @BindView(R.id.iv_search_clear)
    public ImageView mClearbtn;

    @BindView(R.id.et_search)
    public EditText search;

    @BindView(R.id.empty_view)
    public ViewGroup emptyView;

   // @BindView(R.id.listview_search_result)
    public ListView searchResult;

    @BindView(R.id.top_title)
    public TextView title;

    @OnClick(R.id.top_title)
    public void back()
    {
        finish();
    }

    private DBManager manager;
    private List<City> mAllCities;
    private CityListAdapter mCityAdapter;
    private ResultListAdapter mResultListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_addr);
        ButterKnife.bind(this);
        init();
    }
    private void init()
    {
        initData();
        initView();
    }
    private void initData()
    {
       // EventBus.getDefault().register(SearchAddrActivity.this);
        manager = new DBManager(this);
        manager.copyDBFile();
        mAllCities = manager.getAllCities();
        mCityAdapter = new CityListAdapter(SearchAddrActivity.this, mAllCities);
        mCityAdapter.setOnCityClickListener(new CityListAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(String name) {
                Intent intent = new Intent();
                intent.putExtra("address",name);
                setResult(4,intent);
                finish();
            }

            @Override
            public void onLocateClick() {

            }
        });
        mResultListAdapter = new ResultListAdapter(this,null);
        setResult(0,new Intent());
    }
    private void initView()
    {
        title.setText("选择地址");
        mCityAdapter.updateLocateState(888,ToolsUtils.getString(SearchAddrActivity.this,Constant.CITY,""));

        listView.setAdapter(mCityAdapter);
        letter_bar.setOverlay(overlay);
        letter_bar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                int position = mCityAdapter.getLetterPosition(letter);
                listView.setSelection(position);
            }
        });
        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String keyword = editable.toString();
                if(TextUtils.isEmpty(keyword))
                {
                    mClearbtn.setVisibility(View.GONE);
                    emptyView.setVisibility(View.GONE);
                    searchResult.setVisibility(View.GONE);
                }else
                {
                    mClearbtn.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.VISIBLE);
                    List<City> result = manager.searchCity(keyword);
                    if(result == null || result.size() == 0)
                    {
                        emptyView.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                    ToolsUtils.getInstance().toastShowStr(SearchAddrActivity.this,result.get(0).getName()+"");
                    emptyView.setVisibility(View.GONE);
                    mResultListAdapter.changeData(result);
                }
                }
            }
        });
        searchResult =(ListView) findViewById(R.id.listview_search_result);
        searchResult.setAdapter(mResultListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ToolsUtils.getInstance().toastShowStr(SearchAddrActivity.this,"222");
            }
        });
        mClearbtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.iv_search_clear:
                mClearbtn.setVisibility(View.GONE);
                emptyView.setVisibility(View.GONE);
                searchResult.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
