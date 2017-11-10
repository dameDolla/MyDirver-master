package com.app.gaolonglong.fragmenttabhost.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.CarTeamBean;

import java.util.List;
import java.util.Map;

/**
 * Created by yanqi on 2017/11/9.
 */

public class AddressAdapter extends BaseAdapter {
    private Context context;
    private AddrHolder holder;
    private List<Map<String,String>> list;
    public int selectPosition = -1;

    public AddressAdapter(Context context,List<Map<String,String>> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public void setSelectedPosition(int position) {
        this.selectPosition = position;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.item_listview_popwin,viewGroup,false);
            holder = new AddrHolder();
            holder.province =(TextView) view.findViewById(R.id.listview_popwind_tv);
            holder.guid =(TextView) view.findViewById(R.id.listview_popwind);
            holder.main = (LinearLayout) view.findViewById(R.id.listview_popwind_main);
            view.setTag(holder);
        }else{
            holder = (AddrHolder) view.getTag();
        }
        holder.province.setText(list.get(i).get("cityName"));
        if (selectPosition == i) {
            holder.main.setBackgroundColor(context.getResources().getColor(R.color.color_f2));
        } else {
            holder.main.setBackgroundColor(context.getResources().getColor(R.color.white));

        }

        return view;
    }
    private class AddrHolder{
        private TextView province;
        private TextView guid;
        private LinearLayout main;
    }
}
