package com.mr.truck.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mr.truck.R;
import com.mr.truck.bean.ReleaseBean;

import java.util.List;

/**
 * Created by yanqi on 2017/10/18.
 */

public class FindKCPopAdapter extends BaseAdapter {
    private Context context;
    private List<ReleaseBean.DataBean> list;

    public  FindKCPopAdapter(Context context,List<ReleaseBean.DataBean> list)
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
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       FindKCPopAdapter.RouteViewHolder holder;
        if(view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.find_pop_route_item,viewGroup,false);
            holder = new FindKCPopAdapter.RouteViewHolder();
            holder.address =(TextView) view.findViewById(R.id.route_item_addr);
            holder.time =(TextView) view.findViewById(R.id.route_item_main);
            view.setTag(holder);
        }else{
            holder = (FindKCPopAdapter.RouteViewHolder) view.getTag();
        }
        ReleaseBean.DataBean data = list.get(i);
        holder.address.setText(data.getFromSite()+"-"+data.getToSite());
        holder.time.setText(data.getBacktime());
        return view;
    }
    private class RouteViewHolder{
        TextView address ;
        TextView time;
    }
}
