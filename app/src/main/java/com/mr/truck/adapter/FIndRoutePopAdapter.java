package com.mr.truck.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mr.truck.R;
import com.mr.truck.bean.RouteListBean;

import java.util.List;

/**
 * Created by yanqi on 2017/9/14.
 */

public class FIndRoutePopAdapter extends BaseAdapter {

    private Context context;
    private List<RouteListBean.DataBean> list;

    public FIndRoutePopAdapter(Context context, List<RouteListBean.DataBean> list)
    {
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        RouteViewHolder holder;
        if(view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.find_pop_route_item,viewGroup,false);
            holder = new RouteViewHolder();
            holder.address =(TextView) view.findViewById(R.id.route_item_addr);
            holder.isMain =(TextView) view.findViewById(R.id.route_item_main);
            view.setTag(holder);
        }else{
            holder = (RouteViewHolder) view.getTag();
        }
        RouteListBean.DataBean data = list.get(i);
        holder.address.setText(data.getFromSite()+"-"+data.getToSite());
        if(data.getMainLin().equals("1"))
        {
            holder.isMain.setVisibility(View.VISIBLE);
        }
        return view;
    }
    private class RouteViewHolder{
        TextView address ;
        TextView isMain;
    }
}
