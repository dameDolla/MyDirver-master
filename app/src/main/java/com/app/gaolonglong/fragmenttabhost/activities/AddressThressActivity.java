package com.app.gaolonglong.fragmenttabhost.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.adapter.AddressAdapter;
import com.app.gaolonglong.fragmenttabhost.utils.ThreadManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by yanqi on 2017/11/21.
 */

public class AddressThressActivity extends BaseActivity implements AdapterView.OnItemClickListener,View.OnClickListener{

    private Map<String, String> provinceMap;
    private List<Map<String, String>> provinceList;
    private Map<String, String> citiesMap;
    private List<Map<String, String>> citiesList;
    private ListView provinceListView;
    private ListView cityListView,localListView;
    private String file;
    private Map<String, String> localMap;
    private List<Map<String, String>> localList = null;
    private AddressAdapter cityAdapter;
    private int RESULTCODE = 6;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addr_thress_content);
        TextView title = (TextView) findViewById(R.id.top_title);
        TextView back_txt = (TextView) findViewById(R.id.title_back_txt);
        ImageView back = (ImageView) findViewById(R.id.title_back);
        back.setOnClickListener(this);
        back_txt.setOnClickListener(this);
        provinceListView = (ListView) findViewById(R.id.addr_three_province);
        cityListView = (ListView) findViewById(R.id.addr_three_city);
        localListView = (ListView) findViewById(R.id.addr_three_qu);
        provinceListView.setOnItemClickListener(this);
        cityListView.setOnItemClickListener(this);
        localListView.setOnItemClickListener(this);
        file = readFile();
        title.setText("选择地址");
        ThreadManager.getNormalPool().execute(new Runnable() {
            @Override
            public void run() {
                getProvice(file);
            }
        });

        setResult(RESULTCODE,new Intent());

    }

    private  String readFile() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String str = null;
        try {
            InputStream is = getResources().getAssets().open("area.txt");
            byte[] bytes = new byte[1024];
            int length = 0;
            while ((length = is.read(bytes)) != -1) {
                outputStream.write(bytes, 0, length);
            }
            is.close();
            outputStream.close();
            //str = outputStream.toString();
            byte[] lens = outputStream.toByteArray();
            str = new String(lens,"gbk");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
    private AddressAdapter adapter;
    private void getProvice(String jsonData)
    {
        provinceList = new ArrayList<Map<String,String>>();
        try {
            JSONArray json = new JSONArray(jsonData);
            for (int i=0;i<31;i++){
                provinceMap = new HashMap<String,String>();
                JSONObject obj = json.getJSONObject(i);
                String code = obj.getString("item_code");

                if (code.substring(2).equals("0000"))//获取省
                {
                    String name = obj.getString("item_name");
                    provinceMap.put("cityName",name);
                    provinceMap.put("guid",code);
                    provinceList.add(provinceMap);
                }

            }
            runOnUiThread(new Runnable() {



                @Override
                public void run() {
                    adapter = new AddressAdapter(AddressThressActivity.this, provinceList);
                    provinceListView.setAdapter(adapter);
                }
            });
            Log.i("hahahah",provinceList.size()+"");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void getCitied(String provinceCode,String jsonData)
    {

        citiesList = new ArrayList<>();
        try {
            JSONArray json = new JSONArray(jsonData);
            for (int i=0;i<json.length();i++){
                citiesMap = new HashMap<String,String>();
                JSONObject obj = json.getJSONObject(i);
                String code = obj.getString("item_code");
                Log.i("city-code",provinceCode);
                if (code.substring(0,3).equals(provinceCode.substring(0,3))&& !code.substring(2).equals("0000") && code.substring(4).equals("00")){
                    String name = obj.getString("item_name");
                    citiesMap.put("cityName",name);
                    citiesMap.put("guid",code);
                    citiesList.add(citiesMap);
                    Log.i("xixixixi",name);
                }
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    cityAdapter = new AddressAdapter(AddressThressActivity.this, citiesList);
                    cityListView.setAdapter(cityAdapter);
                }
            });
            Log.i("cities-size",citiesList.size()+"");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    private void getLocal(String cityVal,String jsonData)
    {
        localList = new ArrayList<>();
        try {
            JSONArray local = new JSONArray(jsonData);
            for (int i=0;i<local.length();i++){
                localMap = new HashMap<>();
                JSONObject obj = local.getJSONObject(i);
                String code = obj.getString("item_code");
                if (cityVal == "110100" || cityVal == "120100" || cityVal == "310100" || cityVal == "500100"){
                    if (code.substring(0, 4).equals(cityVal.substring(0,4)) && !code.substring(4).equals("00")){
                        localMap.put("cityName",obj.getString("item_name"));
                        localList.add(localMap);
                    }
                }else {
                    if (code.substring(0, 5).equals(cityVal.substring(0, 5))  && !code.substring(4).equals("00")){
                        localMap.put("cityName",obj.getString("item_name"));
                        localList.add(localMap);
                    }
                }
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AddressAdapter adapter = new AddressAdapter(AddressThressActivity.this, localList);
                    localListView.setAdapter(adapter);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private String provice = "";
    private String city = "";
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId())
        {
            case R.id.addr_three_province:

                if (localList instanceof List){
                    localList.clear();
                 }
                adapter.setSelectedPosition(i);
                adapter.notifyDataSetChanged();
                provice = provinceList.get(i).get("cityName");
               final String guid = provinceList.get(i).get("guid");
               ThreadManager.getNormalPool().execute(new Runnable() {
                   @Override
                   public void run() {
                       getCitied(guid,file);
                   }
               });
                break;
            case R.id.addr_three_city:
                cityAdapter.setSelectedPosition(i);
                cityAdapter.notifyDataSetChanged();
                city = citiesList.get(i).get("cityName");
                final String cityGuid = citiesList.get(i).get("guid");
                ThreadManager.getNormalPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        getLocal(cityGuid,file);
                    }
                });
                break;
            case R.id.addr_three_qu:
                String a = provice+city+localList.get(i).get("cityName");
                //ToolsUtils.getInstance().toastShowStr(AddressThressActivity.this,a);
                Intent intent = new Intent();
                intent.putExtra("address",a);
                setResult(RESULTCODE,intent);
                finish();
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back:
                finish();
                break;
            case R.id.title_back_txt:
                finish();
                break;
        }
    }
}
