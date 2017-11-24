package com.app.gaolonglong.fragmenttabhost.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.adapter.AddressAdapter;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yanqi on 2017/11/9.
 */

public class AddressActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    String province, city;//最终选择结果放在这两个变量******************************************
    //因为spinner在加载视图的时候会自动调用点击响应事件，这两个变量在那个时候就已经初始化了


    String[] provinceString = new String[34];


    HashMap<String, String> cityHash = new HashMap<>();
    String[] cityString;

    String file;

    String cityNo = null;// 最重要的参数，选中的城市的cityNo

    private ArrayAdapter<String> provinceAdapter;
    private ArrayAdapter<String> cityAdapter;
    private Map<String, String> provinceHash;
    private List<Map<String, String>> provinceList;
    private ListView city1;
    private AddressAdapter adapter;
    private List<Map<String, String>> ci;
    private TextView back;
    private ImageView backs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addr_listview_item);
        TextView title = (TextView) findViewById(R.id.top_title);
        back = (TextView) findViewById(R.id.title_back_txt);
        backs = (ImageView) findViewById(R.id.title_back);
        back.setOnClickListener(this);
        backs.setOnClickListener(this);
        title.setText("选择地址");
        // ToolsUtils.getInstance().addStatusViewWithColor(this,getResources().getColor(R.color.shen_blue));
        ListView province = (ListView) findViewById(R.id.addr_item_province);
        city1 = (ListView) findViewById(R.id.addr_item_city);
        file = readFile();
        getProvinces(file);
        city1.setOnItemClickListener(this);
        adapter = new AddressAdapter(AddressActivity.this, provinceList);
        Log.e("province-size", file);
       /* SimpleAdapter adapter = new SimpleAdapter(AddressActivity.this,provinceList,R.layout.item_listview_popwin,
                new String[]{"cityName","guid"},new int[]{R.id.listview_popwind_tv,R.id.listview_popwind});*/
        province.setAdapter(adapter);
        province.setOnItemClickListener(this);
        setResult(0, new Intent());
    }


    public String readFile() {

        /*
         * 读取文件中数据的方法
         */

        InputStream myFile = null;
        myFile = getResources().openRawResource(R.raw.ub_city);
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(myFile, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("debug", e.toString());
        }
        String temp;
        String str = "";
        try {
            while ((temp = br.readLine()) != null) {
                str = str + temp;
                // Log.i("zhiyinqing", "断点3"+temp);
            }
            br.close();
            myFile.close();
            return str;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "error";
        }
    }

    public void getProvinces(String jsonData) {

        /*
         * 从json字符串中得到省的信息
         */
        provinceList = new ArrayList<Map<String, String>>();

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < 35; i++) {
                // 获取了34个省市区信息
                provinceHash = new HashMap<>();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String guid = jsonObject.getString("guid");
                String cityName = jsonObject.getString("cityName");
                //Log.i("zhiyinqing", i+guid+cityName);
                provinceHash.put("cityName", cityName);
                provinceHash.put("guid", guid);
                // provinceString[i] = cityName;
                provinceList.add(provinceHash);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String[] getCitys(String guid, String jsonData) {
        /*
         * 此方法用于查找一个省下的所有城市
         */
        // 初始化hashmap
        cityHash.clear();
        // 暂时存放城市的数组
        String[] cityBuffer = new String[21];
        int j = 0;

        // 解析数据
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(jsonData);
            int length = jsonArray.length();
            int i = 33;
            int continuous = 0;// 这个变量用于判断是否连续几次没有找到，如果是，则该省信息全部找到了
            boolean isFind = false;

            while (i < length) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String fGuid = jsonObject.getString("fGuid");
                String cityName = jsonObject.getString("cityName");
                String cityNo = jsonObject.getString("cityNo");
                if (fGuid.equals(guid)) {
                    isFind = true;
                    cityHash.put(cityName, cityNo);
                    cityBuffer[j] = cityName;
                    j++;
                    // Log.i("zhiyinqing", cityName);
                } else {
                    if (isFind == true) {
                        continuous += 1;

                        if (continuous > 5) {
                            Log.i("zhiyinqing", "城市数:" + j);
                            break;
                        }
                    }
                }
                i++;
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String[] citys = new String[j];
        for (int i = 0; i < j; i++) {
            citys[i] = cityBuffer[i];
        }
        return citys;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.addr_item_province:
                adapter.setSelectedPosition(i);
                adapter.notifyDataSetChanged();
                String guid = provinceList.get(i).get("guid");
                String[] cities = getCitys(guid, file);
                ci = new ArrayList<>();
                for (int x = 0; x < cities.length; x++) {
                    Map<String, String> c = new HashMap<String, String>();
                    c.put("city", cities[x]);
                    c.put("guid", "");
                    ci.add(c);
                }
                SimpleAdapter cityAdatper = new SimpleAdapter(AddressActivity.this, ci, R.layout.f2bg_listview_item,
                        new String[]{"city", "guid"}, new int[]{R.id.listview_f2bg_tv, R.id.listview_f2bg});
                city1.setAdapter(cityAdatper);
                break;
            case R.id.addr_item_city:
                String s = ci.get(i).get("city");
                Intent intent = new Intent();
                intent.putExtra("address", s);
                setResult(4, intent);
                finish();
                //ToolsUtils.getInstance().toastShowStr(AddressActivity.this,s);
                break;
        }

        //ToolsUtils.getInstance().toastShowStr(AddressActivity.this,provinceList.get(i).get("guid"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_back_txt:
                finish();
                break;
        }
    }
}
