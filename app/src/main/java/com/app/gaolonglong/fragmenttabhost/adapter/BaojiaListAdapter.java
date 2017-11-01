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

        View mView = LayoutInflater.from(context).inflate(R.layout.baojia_item,parent,false);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        mView.setLayoutParams(lp);
        mView.setOnClickListener(this);
        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        MyViewHolder mHolder = (MyViewHolder)holder;
        data = list.get(position);
        String str = "正在于"+data.getOwnername();
        mHolder.addr.setText(data.getFromSite()+" - "+ data.getToSite());
        mHolder.time.setText(data.getPreloadtime());
        mHolder.info.setText(data.getTrucktypeHZ()+"\\"+ data.getTrucklengthHZ()+"\\"+ data.getCargotype());
        mHolder.itemView.setTag(position);

        mHolder.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onOlick(position, data.getOwnerphone(),"","","phone");
            }
        });
        /*mHolder.caozuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onOlick(position,"",data.getCargoPricesGUID()+"",data.getUpdatePriceTime(),"caozuo");
            }
        });*/
        mHolder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onOlick(position,"",data.getCargoPricesGUID()+"",data.getUpdatePriceTime(),"cancel");
            }
        });
        if (data.getCargoPriceState().equals("0")){
            mHolder.status_txt.setText(str+"议价中");
            mHolder.status_txt.setTextSize(12);
        }else if (data.getCargoPriceState().equals("1")){
            mHolder.status_txt.setText("已确定");
            mHolder.status_txt.setTextSize(18);
            mHolder.status_txt.setTextColor(Color.RED);
            mHolder.cxbj.setVisibility(View.GONE);
        }else if (data.getCargoPriceState().equals("2")){
            mHolder.status_txt.setText("已撤销");
            mHolder.status_txt.setTextSize(18);
            mHolder.status_txt.setTextColor(Color.RED);
            mHolder.cxbj.setVisibility(View.GONE);
        }else if (data.getCargoPriceState().equals("3")){
            mHolder.status_txt.setText("您同意货主报价");
            mHolder.status_txt.setTextSize(14);
            mHolder.status_txt.setTextColor(Color.RED);
            mHolder.cxbj.setVisibility(View.GONE);
        }
        if (data.getBidder().equals("0")&&data.getCargoPriceState().equals("0")){  //货主的报价
            mHolder.baojia.setText("货主拒绝了您的报价");
        }else if (data.getBidder().equals("1")){ //车主的报价
            mHolder.baojia.setText("我的最新报价:"+data.getTotalchargeM()+"元");
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
                    list.get((int)view.getTag()).getOwnerphone()+"",
                    list.get((int)view.getTag()).getFeeremark()+"",
                    list.get((int)view.getTag()).getCargoPriceState()+"",
                    list.get((int)view.getTag()).getOwnerbill()+"",
                    list.get((int)view.getTag()).getBidder(),
                    list.get((int)view.getTag()).getCargoPriceState()
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
        private final TextView baojia;
        private final TextView cancel;
        private final TextView info;
        private final TextView status_txt;
        private final TextView cxbj,ckhy;

        public MyViewHolder(View itemView) {
            super(itemView);
            phone = (ImageView)itemView.findViewById(R.id.baojia_items_phone);
            addr = (TextView)itemView.findViewById(R.id.baojia_items_addr);
            time = (TextView)itemView.findViewById(R.id.baojia_items_time);
            baojia = (TextView)itemView.findViewById(R.id.baojia_items_money);
            cancel = (TextView)itemView.findViewById(R.id.baojia_items_cancel);
            info = (TextView)itemView.findViewById(R.id.baojia_items_carinfo);
            status_txt = (TextView)itemView.findViewById(R.id.baojia_items_shr);
            cxbj = (TextView) itemView.findViewById(R.id.baojia_items_cxbj);
            ckhy = (TextView) itemView.findViewById(R.id.baojia_items_ckhy);
        }
    }
}
