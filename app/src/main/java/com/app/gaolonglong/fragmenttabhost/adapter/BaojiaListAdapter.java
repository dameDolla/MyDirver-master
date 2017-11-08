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
import com.app.gaolonglong.fragmenttabhost.bean.ToSrcDetailBean;

import java.util.List;

/**
 * Created by yanqi on 2017/9/20.
 */

public class BaojiaListAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private Context context;
    private List<BaojiaListBean.DataBean> list;
    private OnClickListener listener;
    private OnItemClickListener itemClickListener = null;
    private BaojiaListBean.DataBean data;

    public BaojiaListAdapter(Context context, List<BaojiaListBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    public interface OnClickListener {
        public void onOlick(int postion, String tel, String caragoGUID, String time, String flag, ToSrcDetailBean bean);
    }

    public void setOnclick(OnClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, ToSrcDetailBean bean,int position);
    }

    public void setItemClick(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(context).inflate(R.layout.baojia_item, parent, false);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mView.setLayoutParams(lp);
        mView.setOnClickListener(this);
        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        MyViewHolder mHolder = (MyViewHolder) holder;
        data = list.get(position);
        String str = "正在于" + data.getOwnername();
        mHolder.addr.setText(data.getFromSite() + " - " + data.getToSite());
        mHolder.time.setText(data.getPreloadtime());
        mHolder.info.setText(data.getTrucktypeHZ() + "/" + data.getTrucklengthHZ() + "/" + data.getCargotype());
        mHolder.itemView.setTag(position);
        final ToSrcDetailBean bean = new ToSrcDetailBean(

                data.getOwnername() + "",
                data.getCargotype(),
                data.getPreloadtime(),
                data.getAvatarAddress(),
                data.getCreditlevel(),
                data.getQty() + "",
                data.getUnit() + "",
                data.getOwnerphone() + "",
                data.getOwneridGUID() + "",
                data.getBillsGUID() + "",
                data.getDriverGUID() + "",
                data.getDrivername() + "",
                data.getCompanyGUID() + "",
                data.getTruckno() + "",
                data.getTrucklength() + "",
                data.getFromDetailedAddress(),
                data.getToDetailedAddress(),
                data.getTrucklengthHZ() + "",
                data.getTrucktypeHZ() + "",
                data.getTrucktype() + "",
                data.getLoadaddHZ() + "",
                data.getArrivedaddHZ() + "",
                data.getInvoiceType() + "",
                data.getUploadReceipt() + "",
                data.getPaperReceipt() + "",
                data.getCompany(),
                data.getMyPriceStatus(),
                data.getOwnerbill(),
                data.getRemark()

        );
        mHolder.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onOlick(position, data.getOwnerphone(), "", "", "phone", bean);
            }
        });
        mHolder.cxbj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onOlick(position, "", "", "", "cxbj", bean);
            }
        });
        mHolder.ckhy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onOlick(position, "", "", "", "ckhy", bean);
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
                listener.onOlick(position, "", data.getCargoPricesGUID() + "", data.getUpdatePriceTime(), "cancel", bean);
            }
        });
        if (data.getBidder().equals("0") && data.getCargoPriceState().equals("0")) {  //货主的报价
            mHolder.baojia.setText("货主拒绝了您的报价");
            mHolder.cxbj.setVisibility(View.VISIBLE);
        } else if (data.getBidder().equals("1")) { //车主的报价
            mHolder.baojia.setText("我的报价:" + data.getTotalchargeM() + "元");
        }
        if (data.getBidder().equals("0") && data.getCargoPriceState().equals("2")) {//货主同意了其他人的报价
            mHolder.status_txt.setText("他人成交");
            mHolder.status_txt.setTextSize(18);
            mHolder.status_txt.setTextColor(Color.RED);
            mHolder.dealprice.setVisibility(View.VISIBLE);
            mHolder.cancel.setVisibility(View.GONE);
            mHolder.ckhy.setVisibility(View.GONE);
            mHolder.cxbj.setVisibility(View.GONE);
            mHolder.dealprice.setText("成交价:" + data.getDealprice());
        }
        if (data.getCargoPriceState().equals("0")) {
            mHolder.status_txt.setText(str + "议价中");
            mHolder.status_txt.setTextSize(12);
            mHolder.cancel.setVisibility(View.VISIBLE);
            mHolder.ckhy.setVisibility(View.VISIBLE);
        } else if (data.getCargoPriceState().equals("1")) {
            mHolder.status_txt.setText("已确定");
            mHolder.status_txt.setTextSize(18);
            mHolder.dealprice.setVisibility(View.GONE);
            mHolder.status_txt.setTextColor(Color.RED);
            mHolder.cxbj.setVisibility(View.GONE);
        } else if (data.getCargoPriceState().equals("2") && data.getBidder().equals("1")) {
            mHolder.status_txt.setText("我已撤销");
            mHolder.status_txt.setTextSize(18);
            mHolder.status_txt.setTextColor(Color.RED);
            mHolder.cxbj.setVisibility(View.GONE);
            mHolder.ckhy.setVisibility(View.GONE);
            mHolder.dealprice.setVisibility(View.GONE);
            mHolder.cancel.setVisibility(View.GONE);

        } else if (data.getCargoPriceState().equals("3")) {
            mHolder.status_txt.setText("您同意货主报价");
            mHolder.status_txt.setTextSize(14);
            mHolder.dealprice.setVisibility(View.GONE);
            mHolder.status_txt.setTextColor(Color.RED);
            mHolder.cxbj.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View view) {
        if (itemClickListener != null) {
            /*BaojiaInfoBean bean = new BaojiaInfoBean(
                    list.get((int) view.getTag()).getTotalchargeM() + "",
                    list.get((int) view.getTag()).getPriceM() + "",
                    list.get((int) view.getTag()).getLoadfeeM() + "",
                    list.get((int) view.getTag()).getImforfee() + "",
                    list.get((int) view.getTag()).getFeeremarkM() + "",
                    list.get((int) view.getTag()).getUpdatePriceTime() + "",
                    list.get((int) view.getTag()).getCargoPricesGUID() + "",
                    list.get((int) view.getTag()).getUnloadfeeM() + "",
                    list.get((int) view.getTag()).getOtherfeeM() + "",
                    list.get((int) view.getTag()).getPrice() + "",
                    list.get((int) view.getTag()).getLoadfee() + "",
                    list.get((int) view.getTag()).getUnloadfee() + "",
                    list.get((int) view.getTag()).getTotalcharge() + "",
                    list.get((int) view.getTag()).getAvatarAddress() + "",
                    list.get((int) view.getTag()).getOwnername() + "",
                    list.get((int) view.getTag()).getOwnerphone() + "",
                    list.get((int) view.getTag()).getFeeremark() + "",
                    list.get((int) view.getTag()).getCargoPriceState() + "",
                    list.get((int) view.getTag()).getOwnerbill() + "",
                    list.get((int) view.getTag()).getBidder(),
                    list.get((int) view.getTag()).getCargoPriceState()
            );*/
            final ToSrcDetailBean bean = new ToSrcDetailBean(

                    list.get((int) view.getTag()).getOwnername() + "",
                    list.get((int) view.getTag()).getCargotype(),
                    list.get((int) view.getTag()).getPreloadtime(),
                    list.get((int) view.getTag()).getAvatarAddress(),
                    list.get((int) view.getTag()).getCreditlevel(),
                    list.get((int) view.getTag()).getQty() + "",
                    list.get((int) view.getTag()).getUnit() + "",
                    list.get((int) view.getTag()).getOwnerphone() + "",
                    list.get((int) view.getTag()).getOwneridGUID() + "",
                    list.get((int) view.getTag()).getBillsGUID() + "",
                    list.get((int) view.getTag()).getDriverGUID() + "",
                    list.get((int) view.getTag()).getDrivername() + "",
                    list.get((int) view.getTag()).getCompanyGUID() + "",
                    list.get((int) view.getTag()).getTruckno() + "",
                    list.get((int) view.getTag()).getTrucklength() + "",
                    list.get((int) view.getTag()).getFromDetailedAddress(),
                    list.get((int) view.getTag()).getToDetailedAddress(),
                    list.get((int) view.getTag()).getTrucklengthHZ() + "",
                    list.get((int) view.getTag()).getTrucktypeHZ() + "",
                    list.get((int) view.getTag()).getTrucktype() + "",
                    list.get((int) view.getTag()).getLoadaddHZ() + "",
                    list.get((int) view.getTag()).getArrivedaddHZ() + "",
                    list.get((int) view.getTag()).getInvoiceType() + "",
                    list.get((int) view.getTag()).getUploadReceipt() + "",
                    list.get((int) view.getTag()).getPaperReceipt() + "",
                    list.get((int) view.getTag()).getCompany(),
                    list.get((int) view.getTag()).getMyPriceStatus(),
                    list.get((int) view.getTag()).getOwnerbill(),
                    list.get((int) view.getTag()).getRemark()

            );
            itemClickListener.onItemClick(view, bean,(int)view.getTag());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView phone;
        private final TextView addr;
        private final TextView time;
        private final TextView baojia;
        private final TextView cancel;
        private final TextView info;
        private final TextView status_txt;
        private final TextView cxbj, ckhy, dealprice;

        public MyViewHolder(View itemView) {
            super(itemView);
            phone = (ImageView) itemView.findViewById(R.id.baojia_items_phone);
            addr = (TextView) itemView.findViewById(R.id.baojia_items_addr);
            time = (TextView) itemView.findViewById(R.id.baojia_items_time);
            baojia = (TextView) itemView.findViewById(R.id.baojia_items_money);
            cancel = (TextView) itemView.findViewById(R.id.baojia_items_cancel);
            info = (TextView) itemView.findViewById(R.id.baojia_items_carinfo);
            status_txt = (TextView) itemView.findViewById(R.id.baojia_items_shr);
            cxbj = (TextView) itemView.findViewById(R.id.baojia_items_cxbj);
            ckhy = (TextView) itemView.findViewById(R.id.baojia_items_ckhy);
            dealprice = (TextView) itemView.findViewById(R.id.baojia_item_dealprice);
        }
    }
}
