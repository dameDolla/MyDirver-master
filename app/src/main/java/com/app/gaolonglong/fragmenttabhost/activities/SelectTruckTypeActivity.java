package com.app.gaolonglong.fragmenttabhost.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.ParametersBean;
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

public class SelectTruckTypeActivity extends BaseActivity {

    private String guid;
    private String mobile;
    private String key;
    private List<ParametersBean.DataBean> truckLenthList = new ArrayList<>();
    private String[] len = new String[]{};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_cartype_gridview);
        guid = GetUserInfoUtils.getGuid(this);
        mobile = GetUserInfoUtils.getMobile(this);
        key = GetUserInfoUtils.getKey(this);
        getTruckLength();
    }
    private void getTruckLength()
    {
        Map<String,String> map = new HashMap<>();
        map.put("GUID",guid);
        map.put(Constant.MOBILE,mobile);
        map.put(Constant.KEY,key);
        map.put("ParameterType","TruckLength");
        RetrofitUtils.getRetrofitService()
                .getParameters(Constant.PARAMETER_PAGENAME, Config.GET_PARAMETERS, JsonUtils.getInstance().getJsonStr(map))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ParametersBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ParametersBean parametersBean) {
                        Log.i("parametersBean",parametersBean.getErrorCode()+"-"+parametersBean.getErrorMsg());
                        truckLenthList.clear();
                        truckLenthList.addAll(parametersBean.getData());
                        //getLengthData(truckLenthList);
                        getLengthData();
                    }
                });
    }
    private void getLengthData()
    {

        String[] len = new String[truckLenthList.size()];
        for (int i = 0;i<truckLenthList.size();i++){
            len[i] = truckLenthList.get(i).getParameterValue()+"米";
        }

        showCarPop(len);
    }
    private void showCarPop(String[] l) {
        //initCartypePopwindow();

        List<Map<String, String>> typeList = new ArrayList<Map<String, String>>();
        List<Map<String, String>> lengthList = new ArrayList<Map<String, String>>();
        final String[] length = l;

        final String[] type = {"不限", "冷藏车", "平板", "高栏", "箱式", "保温", "危险品", "高低板"};

        for (int j = 0; j < type.length; j++) {
            Map<String, String> maps = new HashMap<String, String>();
            maps.put("type", type[j]);
            typeList.add(maps);
        }

        for (int i = 0; i < length.length; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("length", length[i]);
            lengthList.add(map);
        }
        final MyGridView lenthGrid = (MyGridView) findViewById(R.id.gridview);
        SimpleAdapter lenthAdapter = new SimpleAdapter(this, lengthList, R.layout.find_cartype_pop_item, new String[]{"length"},
                new int[]{R.id.gv_item_text});
        lenthGrid.setAdapter(lenthAdapter);

        final MyGridView typeGrid = (MyGridView) findViewById(R.id.gridview_2);
        SimpleAdapter typeAdapter = new SimpleAdapter(this, typeList, R.layout.find_cartype_pop_item, new String[]{"type"},
                new int[]{R.id.gv_item_text});
        typeGrid.setAdapter(typeAdapter);

        /*typePopmenu.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        param = getActivity().getWindow().getAttributes();
        param.alpha = 0.7f;
        getActivity().getWindow().setAttributes(param);
        typePopmenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                param = getActivity().getWindow().getAttributes();
                param.alpha = 1f;
                getActivity().getWindow().setAttributes(param);
            }
        });
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
        TextView sure = (TextView) popView.findViewById(R.id.cartype_grid_sure);
        TextView noLimit = (TextView) popView.findViewById(R.id.cartype_grid_nocartype);

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = 2;
                mText.get(2).setText(typeStr + "/" + lenStr);
                getSrcFromside(initJsonData(flag, addrs, lenStr, typeStr));
                typePopmenu.dismiss();
            }
        });
        noLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = 0;
                mText.get(2).setText("车型" + "/" + "车长");
                Log.e("findlog", initJsonData(flag, addrs, "", ""));
                getSrcFromside(initJsonData(flag, addrs, "", ""));
                typePopmenu.dismiss();
            }
        });*/
    }
}
