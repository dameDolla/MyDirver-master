package com.app.gaolonglong.fragmenttabhost.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by yanqi on 2017/9/8.
 */

public class JsonUtils {
    private static JsonUtils mJsonUtils;

    public static JsonUtils getInstance()
    {
        if (mJsonUtils == null)
        {
            mJsonUtils = new JsonUtils();
        }
        return mJsonUtils;
    }
    public  String getJsonStr(Map<String,String> map)
    {
        JSONObject mJson = new JSONObject();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
            try {
                mJson.put(entry.getKey(),entry.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return mJson.toString();
    }
}
