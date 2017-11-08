package com.app.gaolonglong.fragmenttabhost.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.WallteListBean;

import java.util.List;

/**
 * Created by yanqi on 2017/11/7.
 */

public class MyWallteAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<WallteListBean.DataBean> list;

    public MyWallteAdapter(Context context,List<WallteListBean.DataBean> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wallte_jiaoyi_item,null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        view.setTag(viewType);
        return new WallteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        WallteViewHolder mHolder = (WallteViewHolder) holder;
        WallteListBean.DataBean data = list.get(position);
        mHolder.time.setText(data.getTradetime());
        mHolder.money.setText(data.getTradeamount());//交易金额
        mHolder.what.setText(data.getRemark());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    private class WallteViewHolder extends RecyclerView.ViewHolder{

        private final TextView time;
        private final TextView type;
        private final TextView money;
        private final TextView what;
        private final LinearLayout main;

        public WallteViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.wallte_item_time);
            type = (TextView) itemView.findViewById(R.id.wallte_item_type);
            money = (TextView) itemView.findViewById(R.id.wallte_item_money);
            what = (TextView) itemView.findViewById(R.id.wallte_item_what);
            main = (LinearLayout) itemView.findViewById(R.id.wallte_item_main);
        }
    }
}
