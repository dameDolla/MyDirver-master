package com.mr.truck.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mr.truck.R;
import com.mr.truck.bean.InvitedSrcBean;
import com.mr.truck.bean.ToSrcDetailBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by yanqi on 2017/11/14.
 */

public class InvitedSrcAdapter extends RecyclerView.Adapter implements View.OnClickListener{
    private Context context;
    private List<InvitedSrcBean.DataBean> list;

    public InvitedSrcAdapter(Context context,List<InvitedSrcBean.DataBean> list){
        this.context = context;
        this.list = list;
    }
    private OnInvitedItemClick onInvitedItemClick = null;
    private OnInvitedClickListener onInvitedClick = null;
    public interface OnInvitedItemClick
    {
        void invitedClick(View view, ToSrcDetailBean bean);
    }
    public interface OnInvitedClickListener{
        void onInvitedClick(String id,int position);
    }
    public void setOnInvitedClickListener(OnInvitedClickListener listener) {
        this.onInvitedClick = listener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.invited_src_item,null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        view.setOnClickListener(this);
        return new InvitedHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        InvitedHolder mHolder = (InvitedHolder) holder;
        final InvitedSrcBean.DataBean data = list.get(position);
        mHolder.zhuang.setText(data.getFromDetailedAddress());
        mHolder.xie.setText(data.getToDetailedAddress());
        mHolder.icon.setImageURI(Uri.parse(data.getAvatarAddress()));
        mHolder.username.setText(data.getOwnername());
        String cartype = data.getTrucklengthHZ() + "" + data.getTrucktypeHZ() + "";
        mHolder.carType.setText(cartype);
        mHolder.type.setText(data.getCargotype() + "");
        String[] time = (data.getPreloadtime() + "").split(" ");
        mHolder.time.setText(time[0]);
        mHolder.itemView.setTag(position);
        mHolder.kcaddr.setText(data.getFromSite1()+"-"+data.getToSite1());
        mHolder.kctime.setText(data.getEmptytime());
        mHolder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onInvitedClick.onInvitedClick(data.getInvitationID(),position);
            }
        });
        if (data.getCreditlevel().toString().equals("0")) {
            mHolder.isRenzheng.setText("注册用户");
        } else if (data.getCreditlevel().toString().equals("1")) {
            mHolder.isRenzheng.setText("认证用户");
        } else if (data.getCreditlevel().toString().equals("2")) {
            mHolder.isRenzheng.setText("诚信用户");
        }
    }

    @Override
    public void onClick(View view) {
        if (onInvitedItemClick != null) {

        /*new Thread(new Runnable() {

            @Override
            public void run() {*/
            ToSrcDetailBean bean = new ToSrcDetailBean(

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
                     "",
                    "",
                    "",
                    "",
                    "",
                    list.get((int) view.getTag()).getFromDetailedAddress() + "",
                    list.get((int) view.getTag()).getToDetailedAddress() + "",
                    list.get((int) view.getTag()).getTrucklengthHZ() + "",
                    list.get((int) view.getTag()).getTrucktypeHZ() + "",
                    "",
                    list.get((int) view.getTag()).getLoadaddHZ() + "",
                    list.get((int) view.getTag()).getArrivedaddHZ() + "",
                    list.get((int) view.getTag()).getInvoiceType() + "",
                    list.get((int) view.getTag()).getUploadReceipt() + "",
                    list.get((int) view.getTag()).getPaperReceipt() + "",
                    "",
                    list.get((int) view.getTag()).getMyPriceStatus(),
                    list.get((int) view.getTag()).getOwnerbill()+"",
                    list.get((int) view.getTag()).getRemark(),
                    list.get((int) view.getTag()).getInvitationID(),
                    list.get((int) view.getTag()).getPrearrivetime()
            );
            //注意这里使用getTag方法获取position
            onInvitedItemClick.invitedClick(view, bean);
           /* }*/
       /* }).start();*/


        }
    }
    public void setOnInvitedItemClickListener(OnInvitedItemClick listener) {
        this.onInvitedItemClick = listener;
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    private class InvitedHolder extends RecyclerView.ViewHolder{
        private final SimpleDraweeView icon;
        private final TextView username;
        private final TextView isRenzheng;
        private final TextView zhuang;
        private final TextView xie;
        private final TextView type;
        private final TextView time;
        private final TextView carType;
        private final ImageView iv_phone;
        private final LinearLayout tel,cancel;
        private final TextView status,kcaddr,kctime;
        public InvitedHolder(View itemView) {
            super(itemView);
            icon = (SimpleDraweeView) itemView.findViewById(R.id.invited_iv_icon);
            username = (TextView) itemView.findViewById(R.id.invited_tv_name);
            isRenzheng = (TextView) itemView.findViewById(R.id.invited_is_renzheng);
            zhuang = (TextView) itemView.findViewById(R.id.invited_addr_zhuang);
            xie = (TextView) itemView.findViewById(R.id.invited_addr_xie);
            type = (TextView) itemView.findViewById(R.id.invited_type);
            time = (TextView) itemView.findViewById(R.id.invited_time);
            carType = (TextView) itemView.findViewById(R.id.invited_car_type);
            iv_phone = (ImageView) itemView.findViewById(R.id.invited_iv_phone);
            tel = (LinearLayout) itemView.findViewById(R.id.invited_iv_tel_ll);
            cancel = (LinearLayout) itemView.findViewById(R.id.invited_item_cancel);
            status = (TextView) itemView.findViewById(R.id.invited_iv_bjstatus);
            kcaddr = (TextView) itemView.findViewById(R.id.invited_kc_addr);
            kctime = (TextView) itemView.findViewById(R.id.invited_kc_emtime);
        }
    }
}
