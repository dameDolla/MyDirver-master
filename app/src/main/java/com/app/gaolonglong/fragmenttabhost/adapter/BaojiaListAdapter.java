package com.app.gaolonglong.fragmenttabhost.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.BaojiaInfoBean;
import com.app.gaolonglong.fragmenttabhost.bean.BaojiaListBean;

import java.util.List;

/**
 * Created by yanqi on 2017/9/20.
 */

public class BaojiaListAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private Context context;
    private List<BaojiaListBean.DataBean> list;
    private OnClickListener listener;
    private OnItemClickListener itemClickListener  = null;
    private BaojiaListBean.DataBean data;

    public BaojiaListAdapter(Context context, List<BaojiaListBean.DataBean> list)
    {
        this.context = context;
        this.list = list;
    }
    public interface OnClickListener
    {
        public void onOlick(int postion,String tel,String caragoGUID,String time,String flag);
    }
    public void setOnclick(OnClickListener listener)
    {
        this.listener = listener;
    }

    public interface OnItemClickListener
    {
        public void onItemClick(View view,BaojiaInfoBean bean);
    }
    public void setItemClick(OnItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(context).inflate(R.layout.baojia_list_item,parent,false);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        mView.setLayoutParams(lp);
        mView.setOnClickListener(this);
        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder mHolder = (MyViewHolder)holder;
        data = list.get(position);
        mHolder.addr.setText(data.getFromSite()+" - "+ data.getToSite());
        mHolder.time.setText(data.getPreloadtime());
        mHolder.info.setText(data.getTrucktype()+"\\"+ data.getTrucklength()+"\\"+ data.getCargotype());
        mHolder.czBaojia.setText(data.getTotalchargeM()+"");
        mHolder.mBaojia.setText(data.getTotalcharge()+"");
        mHolder.itemView.setTag(position);
        mHolder.tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onOlick(position, data.getOwnerphone(),"","","phone");
            }
        });
        mHolder.caozuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onOlick(position,"",data.getCargoPricesGUID()+"",data.getUpdatePriceTime(),"caozuo");
            }
        });
        mHolder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onOlick(position,"",data.getCargoPricesGUID()+"",data.getUpdatePriceTime(),"cancel");
            }
        });
        if (data.getCargoPriceState().equals("0")){
            mHolder.status_txt.setText("议价中");
            mHolder.status_txt.setTextSize(12);
            mHolder.status_logo.setVisibility(View.VISIBLE);
            mHolder.status_logo.setImageResource(R.drawable.baojiaing);
            mHolder.caozuo_gray.setVisibility(View.GONE);
        }else if (data.getCargoPriceState().equals("1")){
            mHolder.status_logo.setVisibility(View.GONE);
            mHolder.status_txt.setText("已确定");
            mHolder.status_txt.setTextSize(18);
            mHolder.status_txt.setTextColor(Color.RED);
            mHolder.ll.setVisibility(View.GONE);
            mHolder.caozuo_gray.setVisibility(View.VISIBLE);
        }else if (data.getCargoPriceState().equals("2")){
            mHolder.status_logo.setVisibility(View.GONE);
            mHolder.status_txt.setText("已撤销");
            mHolder.status_txt.setTextSize(18);
            mHolder.status_txt.setTextColor(Color.RED);
            mHolder.ll.setVisibility(View.GONE);
            mHolder.caozuo_gray.setVisibility(View.VISIBLE);
        }else if (data.getCargoPriceState().equals("3")){
            mHolder.status_logo.setVisibility(View.GONE);
            mHolder.status_txt.setText("已撤销");
            mHolder.status_txt.setTextSize(18);
            mHolder.status_txt.setTextColor(Color.RED);
            mHolder.ll.setVisibility(View.GONE);
            mHolder.caozuo_gray.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        if(itemClickListener != null)
        {
            BaojiaInfoBean bean = new BaojiaInfoBean(
                    list.get((int)view.getTag()).getTotalchargeM()+"",
                    list.get((int)view.getTag()).getPriceM()+"",
                    list.get((int)view.getTag()).getLoadfeeM()+"",
                    list.get((int)view.getTag()).getImforfee()+"",
                    list.get((int)view.getTag()).getFeeremarkM()+"",
                    list.get((int)view.getTag()).getUpdatePriceTime()+"",
                    list.get((int)view.getTag()).getCargoPricesGUID()+"",
                    list.get((int)view.getTag()).getUnloadfeeM()+"",
                    list.get((int)view.getTag()).getOtherfeeM()+"",
                    list.get((int)view.getTag()).getPrice()+"",
                    list.get((int)view.getTag()).getLoadfee()+"",
                    list.get((int)view.getTag()).getUnloadfee()+"",
                    list.get((int)view.getTag()).getTotalcharge()+"",
                    list.get((int)view.getTag()).getAvatarAddress()+"",
                    list.get((int)view.getTag()).getOwnername()+"",
                    list.get((int)view.getTag()).getOwnerphone()+""
            );
            itemClickListener.onItemClick(view,bean);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    private class MyViewHolder extends RecyclerView.ViewHolder
    {

        private final ImageView phone;
        private final TextView addr;
        private final TextView time;
        private final TextView mBaojia;
        private final TextView czBaojia;
        private final TextView cancel;
        private final TextView caozuo;
        private final TextView info;
        private final ImageView status_logo;
        private final TextView status_txt;
        private final TextView caozuo_gray;
        private final LinearLayout ll,tel;

        public MyViewHolder(View itemView) {
            super(itemView);
            phone = (ImageView)itemView.findViewById(R.id.mission_item_phone);
            addr = (TextView)itemView.findViewById(R.id.mission_item_addr);
            time = (TextView)itemView.findViewById(R.id.mission_item_time);
            czBaojia = (TextView)itemView.findViewById(R.id.mission_item_baojia_mine);
            mBaojia = (TextView)itemView.findViewById(R.id.mission_item_baojia_hz);
            cancel = (TextView)itemView.findViewById(R.id.mission_order_cancle);
            caozuo = (TextView)itemView.findViewById(R.id.mission_order_caozuo);
            info = (TextView)itemView.findViewById(R.id.mission_item_carinfo);
            status_logo = (ImageView)itemView.findViewById(R.id.baojia_item_status_logo);
            status_txt = (TextView)itemView.findViewById(R.id.baojia_item_status_txt);
            caozuo_gray = (TextView)itemView.findViewById(R.id.baojia_item_caozuo_gray);
            ll = (LinearLayout)itemView.findViewById(R.id.baojia_item_ll);
            tel = (LinearLayout)itemView.findViewById(R.id.baojia_item_tel_ll);
        }
    }
}
