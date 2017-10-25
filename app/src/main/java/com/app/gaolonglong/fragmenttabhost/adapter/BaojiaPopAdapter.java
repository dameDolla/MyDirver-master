package com.app.gaolonglong.fragmenttabhost.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.CarTeamBean;
import com.app.gaolonglong.fragmenttabhost.bean.RouteListBean;

import java.util.List;

/**
 * Created by yanqi on 2017/10/19.
 */

public class BaojiaPopAdapter extends BaseAdapter {

    private Context context;
    private List<CarTeamBean.DataBean> list;

    public BaojiaPopAdapter(Context context, List<CarTeamBean.DataBean> list)
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
        BaojiaPopAdapter.RouteViewHolder holder;
        if(view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.find_pop_route_item,viewGroup,false);
            holder = new BaojiaPopAdapter.RouteViewHolder();
            holder.address =(TextView) view.findViewById(R.id.route_item_addr);
            holder.isMain =(TextView) view.findViewById(R.id.route_item_main);
            view.setTag(holder);
        }else{
            holder = (BaojiaPopAdapter.RouteViewHolder) view.getTag();
        }
        CarTeamBean.DataBean data = list.get(i);

        holder.address.setText(data.getTruckno()+"");
        String type = data.getTrucktype()+"/"+data.getTrucklength();
        holder.isMain.setVisibility(View.VISIBLE);
        holder.isMain.setText(type);


        return view;
    }
    private class RouteViewHolder{
        TextView address ;
        TextView isMain;
    }

}
