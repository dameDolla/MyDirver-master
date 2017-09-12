package com.app.gaolonglong.fragmenttabhost.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.bean.GetSRCBean;

import java.util.List;

/**
 * Created by yanqi on 2017/9/8.
 */

public class FindSrcAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<GetSRCBean.DataBean> list;

    public FindSrcAdapter(Context context, List<GetSRCBean.DataBean> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.find_src_item,null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new SrcViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //0、注册用户；1、认证用户；2、诚信用户。新注册用户标记为0，完成认证标记为1.
        SrcViewHolder holder1 = (SrcViewHolder)holder;
        final GetSRCBean.DataBean data = list.get(position);
        holder1.username.setText(data.getOwnername()+"");
        holder1.zhuang.setText(data.getFromSite()+"");
        holder1.xie.setText(data.getToSite()+"");
        String cartype = data.getTrucktypea()+"吨 "+data.getTrucklength()+"米 "+data.getTrucktype();
        holder1.carType.setText(cartype);
        holder1.time.setText(data.getPreloadtime()+"");
        if(data.getCreditlevel().equals("0"))
        {
            holder1.type.setText("注册用户");
        }else if (data.getCreditlevel().equals("1"))
        {
            holder1.type.setText("认证用户");
        }else if (data.getCreditlevel().equals("2"))
        {
            holder1.type.setText("诚信用户");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    private class SrcViewHolder extends RecyclerView.ViewHolder{

        private final ImageView icon;
        private final TextView username;
        private final TextView isRenzheng;
        private final TextView zhuang;
        private final TextView xie;
        private final TextView type;
        private final TextView time;
        private final TextView carType;
        private final ImageView iv_phone;

        public SrcViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.find_iv_icon);
            username = (TextView) itemView.findViewById(R.id.find_tv_name);
            isRenzheng = (TextView) itemView.findViewById(R.id.find_is_renzheng);
            zhuang = (TextView) itemView.findViewById(R.id.find_addr_zhuang);
            xie = (TextView) itemView.findViewById(R.id.find_addr_xie);
            type = (TextView) itemView.findViewById(R.id.find_type);
            time = (TextView) itemView.findViewById(R.id.find_time);
            carType = (TextView) itemView.findViewById(R.id.find_car_type);
            iv_phone = (ImageView) itemView.findViewById(R.id.find_iv_phone);
        }
    }
}
