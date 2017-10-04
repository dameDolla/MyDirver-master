package com.app.gaolonglong.fragmenttabhost.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.CarTeamBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by yanqi on 2017/10/3.
 */

public class CarTeamAdapter extends BaseAdapter{
    private Context context;
    private List<CarTeamBean.DataBean> list;
    private View contentView;

    public CarTeamAdapter(Context context, List<CarTeamBean.DataBean> list)
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
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyViewHolder holder = null;
        if(view == null)
        {
            holder = new MyViewHolder();
        }else {
            holder = (MyViewHolder) contentView.getTag();
        }
        CarTeamBean.DataBean data = list.get(i);

        holder.logo.setImageURI(Uri.parse(data.getTruckImg()));
        holder.carNum.setText(data.getTruckno());
        holder.length.setText(data.getTrucklength());
        holder.type.setText(data.getTrucktype());
        return view;
    }

    private class MyViewHolder{

        private final SimpleDraweeView logo;
        private final TextView carNum;
        private final TextView type;
        private final TextView length;
        private final TextView weight;
        private final TextView tiji;

        public MyViewHolder()
        {
            contentView = LayoutInflater.from(context).inflate(R.layout.carteam_item,null);

            logo = (SimpleDraweeView) contentView.findViewById(R.id.carteam_item_icon);
            carNum = (TextView) contentView.findViewById(R.id.carteam_item_carnum);
            type = (TextView) contentView.findViewById(R.id.carteam_item_cartype);
            length = (TextView) contentView.findViewById(R.id.carteam_item_carlenth);
            weight = (TextView) contentView.findViewById(R.id.carteam_item_weight);
            tiji = (TextView) contentView.findViewById(R.id.carteam_item_tiji);
            contentView.setTag(this);
        }
    }
}
