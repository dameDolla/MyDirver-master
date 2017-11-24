package com.app.gaolonglong.fragmenttabhost.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.ToJiaoYiDetailBean;
import com.app.gaolonglong.fragmenttabhost.bean.WallteListBean;

import java.util.List;

/**
 * Created by yanqi on 2017/11/7.
 */

public class MyWallteAdapter extends RecyclerView.Adapter implements View.OnClickListener{
    private Context context;
    private List<WallteListBean.DataBean> list;
    private jiaoyiItemClickListener itemClickListener;

    public MyWallteAdapter(Context context,List<WallteListBean.DataBean> list){
        this.context = context;
        this.list = list;
    }
    public interface jiaoyiItemClickListener
    {
        void jiaoyiItemClick(ToJiaoYiDetailBean bean);
    }
    public void setJiaoYiItemClickListener(jiaoyiItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wallte_jiaoyi_item,null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        view.setOnClickListener(this);
        return new WallteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        WallteViewHolder mHolder = (WallteViewHolder) holder;
        WallteListBean.DataBean data = list.get(position);
        mHolder.time.setText(data.getTradetime());
        mHolder.money.setText(data.getTradeamount()+"元");//交易金额
        mHolder.what.setText(data.getRemark());
        mHolder.itemView.setTag(position);
        String tradetype = data.getTradetype();
        if (tradetype.equals("1")){
            mHolder.type.setText("充");
            mHolder.type.setBackgroundResource(R.color.bg_orange);
        }else if (tradetype.equals("2")){
            mHolder.type.setText("解");
            mHolder.type.setBackgroundResource(R.color.shen_blue);
        }else if (tradetype.equals("3")){
            mHolder.type.setText("冻");
            mHolder.type.setBackgroundResource(R.color.bg_gray);
        }else if (tradetype.equals("4")){
            mHolder.type.setText("支");
            mHolder.type.setBackgroundResource(R.color.bg_Blue);
        }else if (tradetype.equals("5")){
            mHolder.type.setText("扣");
            mHolder.type.setBackgroundResource(R.color.bg_red);
        }else if (tradetype.equals("6")){
            mHolder.type.setText("补");
            mHolder.type.setBackgroundResource(R.color.google_yellow);
        }else if (tradetype.equals("9")) {
            mHolder.type.setText("提");
            mHolder.type.setBackgroundResource(R.color.bg_green);
        }else if ((tradetype.equals("7"))){
            mHolder.type.setText("收");
            mHolder.type.setBackgroundResource(R.color.bg_orange);
        }
    }

    @Override
    public void onClick(View view) {
        ToJiaoYiDetailBean bean = new ToJiaoYiDetailBean(
               list.get((int)view.getTag()).getTradeid(),
               list.get((int)view.getTag()).getTrademarksGUID(),
                list.get((int)view.getTag()).getTradetime(),
                list.get((int)view.getTag()).getTradetype(),
                list.get((int)view.getTag()).getTradeamount(),
                list.get((int)view.getTag()).getRemark(),
                list.get((int)view.getTag()).getOrderNumber(),
                list.get((int)view.getTag()).getStates()

        );
        itemClickListener.jiaoyiItemClick(bean);
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
