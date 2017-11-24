package com.app.gaolonglong.fragmenttabhost.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.ParametersBean;
import com.app.gaolonglong.fragmenttabhost.bean.TruckLengthBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.GetUserInfoUtils;
import com.app.gaolonglong.fragmenttabhost.utils.JsonUtils;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.view.MyGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yanqi on 2017/11/15.
 */

public class SelectTruckTypeActivity extends BaseActivity implements View.OnClickListener{

    private String guid;
    private String mobile;
    private String key;
    int resultCode = 101;
    private String lenStr = "";
    private String typeStr = "";
    private List<TruckLengthBean.DataBean> truckLenthList = new ArrayList<>();
    private List<ParametersBean.DataBean> truckTypeList = new ArrayList<>();
    private String[] len = new String[]{};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_cartype_gridview);
        guid = GetUserInfoUtils.getGuid(this);
        mobile = GetUserInfoUtils.getMobile(this);
        key = GetUserInfoUtils.getKey(this);
        TextView title = (TextView) findViewById(R.id.top_title);
        TextView back_txt = (TextView) findViewById(R.id.title_back_txt);
        ImageView back = (ImageView) findViewById(R.id.title_back);
        TextView sure = (TextView) findViewById(R.id.cartype_grid_sure);
        TextView  cancel = (TextView) findViewById(R.id.cartype_grid_nocartype);
        title.setText("车型车长");
        sure.setOnClickListener(this);
        cancel.setOnClickListener(this);
        back.setOnClickListener(this);
        back_txt.setOnClickListener(this);
        getTruckLength();
        getTruckType();
        setResult(0, new Intent());
    }



    private void getTruckLength()
    {
        Map<String,String> map = new HashMap<>();
        map.put("GUID",guid);
        map.put(Constant.MOBILE,mobile);
        map.put(Constant.KEY,key);

        RetrofitUtils.getRetrofitService()
                .getTruckLength(Constant.PARAMETER_PAGENAME, Config.GETTRUCKLENGTH, JsonUtils.getInstance().getJsonStr(map))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TruckLengthBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TruckLengthBean parametersBean) {
                        Log.i("parametersBean",parametersBean.getErrorCode()+"-"+parametersBean.getErrorMsg());
                        truckLenthList.clear();
                        truckLenthList.addAll(parametersBean.getData());
                        //getLengthData(truckLenthList);
                        Log.i("truck-length",truckLenthList.size()+"");
                        getLengthData();
                    }
                });
    }
    private void getLengthData()
    {
        List<Map<String, String>> lengthList = new ArrayList<Map<String, String>>();
        String[] len = new String[truckLenthList.size()];
        for (int i = 0;i<truckLenthList.size();i++){
            len[i] = truckLenthList.get(i).getTruckLengthText();
        }
        for (int i = 0; i < len.length; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("length", len[i]);
            lengthList.add(map);
        }
        final MyGridView lenthGrid = (MyGridView) findViewById(R.id.gridview);
        SimpleAdapter lenthAdapter = new SimpleAdapter(this, lengthList, R.layout.find_cartype_pop_item, new String[]{"length"},
                new int[]{R.id.gv_item_text});
        lenthGrid.setAdapter(lenthAdapter);
        lenthGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CharSequence len = ((TextView) lenthGrid.getChildAt(i).findViewById(R.id.gv_item_text)).getText();
                lenStr = len.toString();
                for (int m = 0; m < adapterView.getCount(); m++) {
                    TextView item = (TextView) lenthGrid.getChildAt(m).findViewById(R.id.gv_item_text);

                    if (i == m) {//当前选中的Item改变背景颜色
                        item.setBackgroundResource(R.drawable.cartype_unselect);
                    } else {
                        item.setBackgroundResource(R.drawable.cartype_select);
                    }
                }
            }
        });
    }
    private void getTruckType()
    {
        Map<String,String> map = new HashMap<>();
        map.put("GUID",guid);
        map.put(Constant.MOBILE,mobile);
        map.put(Constant.KEY,key);
        map.put("ParameterType","TruckShape");
        Log.i("truck-type",JsonUtils.getInstance().getJsonStr(map));
        RetrofitUtils.getRetrofitService()
                .getParameters(Constant.PARAMETER_PAGENAME,Config.GET_PARAMETERS,JsonUtils.getInstance().getJsonStr(map))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ParametersBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.i("truck-type",e.getMessage()+"");
                    }

                    @Override
                    public void onNext(ParametersBean parametersBean) {
                        truckTypeList.clear();
                        truckTypeList.addAll(parametersBean.getData());
                        //Log.i("truck-type-111111",parametersBean.getErrorMsg()+"-"+parametersBean.getData().get(0).getParameterText());
                        getTypeData();
                    }
                });
    }
    private void getTypeData()
    {
        List<Map<String, String>> typeList = new ArrayList<Map<String, String>>();
        String[] type = new String[truckTypeList.size()];
        for (int i = 0;i<truckTypeList.size();i++){
            type[i] = truckTypeList.get(i).getParameterText();
        }

        for (int j = 0; j < type.length; j++) {
            Map<String, String> maps = new HashMap<String, String>();
            maps.put("type", type[j]);
            typeList.add(maps);
        }
        final MyGridView typeGrid = (MyGridView) findViewById(R.id.gridview_2);
        SimpleAdapter typeAdapter = new SimpleAdapter(this, typeList, R.layout.find_cartype_pop_item, new String[]{"type"},
                new int[]{R.id.gv_item_text});
        typeGrid.setAdapter(typeAdapter);

        typeGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CharSequence type = ((TextView) typeGrid.getChildAt(i).findViewById(R.id.gv_item_text)).getText();
                typeStr = type.toString();
                for (int m = 0; m < adapterView.getCount(); m++) {
                    TextView item = (TextView) typeGrid.getChildAt(m).findViewById(R.id.gv_item_text);
                    //typeStr = (String) item.getText();
                    if (i == m) {//当前选中的Item改变背景颜色
                        item.setBackgroundResource(R.drawable.cartype_unselect);
                    } else {
                        item.setBackgroundResource(R.drawable.cartype_select);
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.cartype_grid_nocartype:
                Intent intent1 = new Intent();
                intent1.putExtra("trucktype","");
                intent1.putExtra("trucklength","");
                setResult(resultCode,intent1);
                finish();
                break;
            case R.id.cartype_grid_sure:
                Intent intent = new Intent();
                intent.putExtra("trucktype",typeStr);
                intent.putExtra("trucklength",lenStr);
                setResult(resultCode,intent);
                finish();
                break;
            case R.id.title_back:
                finish();
                break;
            case R.id.title_back_txt:
                finish();
                break;
        }
    }
}
