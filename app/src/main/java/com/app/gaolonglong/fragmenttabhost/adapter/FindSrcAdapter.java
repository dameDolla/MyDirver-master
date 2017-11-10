package com.app.gaolonglong.fragmenttabhost.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.bean.GetSRCBean;
import com.app.gaolonglong.fragmenttabhost.bean.TestBean;
import com.app.gaolonglong.fragmenttabhost.bean.ToSrcDetailBean;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanqi on 2017/9/8.
 */

public class FindSrcAdapter extends RecyclerView.Adapter implements View.OnClickListener{

    private Context context;
    private List<GetSRCBean.DataBean> list;
    private GetSRCBean.DataBean data;

    public FindSrcAdapter(Context context, List<GetSRCBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }
    private OnItemClickListener mOnItemClickListener = null;
    public static interface OnItemClickListener {
        /*void onItemClick(View view, List<String> list);*/
        void onItemClick(View view, ToSrcDetailBean bean);
    }
    private OnFindClickListener onFindClickListener = null;
    public static interface OnFindClickListener{
        void onFindClick(int position,String tel);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.find_src_item, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        view.setOnClickListener(this);
        return new SrcViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //0、注册用户；1、认证用户；2、诚信用户。新注册用户标记为0，完成认证标记为1.
        SrcViewHolder holder1 = (SrcViewHolder) holder;
        data = list.get(position);
        if (!(data.getFromDetailedAddress()+"").equals("")&&!(data.getToDetailedAddress()+"").equals(""))
        {
            //Log.e("tositefromfite",data.getFromDetailedAddress()+"--"+data.getToDetailedAddress());

            /*String[] from = (data.getFromDetailedAddress()+"").split("省");
            String[] to = (data.getToDetailedAddress()+"").split("省");*/
            holder1.zhuang.setText(data.getFromDetailedAddress()+"");
            holder1.xie.setText(data.getToDetailedAddress()+"");
        }else {
            holder1.zhuang.setText(data.getFromSite()+"");
            holder1.xie.setText(data.getToSite()+"");
        }

        holder1.username.setText(data.getOwnername() + "");

        String cartype = data.getTrucklengthHZ()+""  + data.getTrucktypeHZ()+"";
        holder1.carType.setText(cartype);
        holder1.type.setText(data.getCargotype()+"");
        String[] time = (data.getPreloadtime()+"").split(" ");
        holder1.time.setText(time[0]);
        holder1.icon.setImageURI(Uri.parse(data.getAvatarAddress()));
        holder1.itemView.setTag(position);
        //holder1.tel.setVisibility(View.GONE);
        //holder1.username.setText(data.getOwnername().toString());
        //holder1.isRenzheng.setText("注册用户");
        if (data.getCreditlevel().toString().equals("0")) {
            holder1.isRenzheng.setText("注册用户");
        } else if (data.getCreditlevel().toString().equals("1")) {
            holder1.isRenzheng.setText("认证用户");
        } else if (data.getCreditlevel().toString().equals("2")) {
            holder1.isRenzheng.setText("诚信用户");
        }

        holder1.tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFindClickListener.onFindClick(position,list.get(position).getOwnerphone()+"");
            }
        });
       // holder1.status.setText(data.getMyPriceStatus());
        if (data.getMyPriceStatus().equals("1")){
            holder1.status.setVisibility(View.VISIBLE);
        }else {
            holder1.status.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(final View view) {

        if (mOnItemClickListener != null) {

        /*new Thread(new Runnable() {

            @Override
            public void run() {*/
                ToSrcDetailBean bean = new ToSrcDetailBean(

                        list.get((int)view.getTag()).getOwnername()+"",
                        list.get((int)view.getTag()).getCargotype(),
                        list.get((int)view.getTag()).getPreloadtime(),
                        list.get((int)view.getTag()).getAvatarAddress(),
                        list.get((int)view.getTag()).getCreditlevel(),
                        list.get((int)view.getTag()).getQty()+"",
                        list.get((int)view.getTag()).getUnit()+"",
                        list.get((int)view.getTag()).getOwnerphone()+"",
                        list.get((int)view.getTag()).getOwneridGUID()+"",
                        list.get((int)view.getTag()).getBillsGUID()+"",
                        list.get((int)view.getTag()).getDriverGUID()+"",
                        list.get((int)view.getTag()).getDrivername()+"",
                        list.get((int)view.getTag()).getCompanyGUID()+"",
                        list.get((int)view.getTag()).getTruckno()+"",
                        list.get((int)view.getTag()).getTrucklength()+"",
                        list.get((int)view.getTag()).getFromDetailedAddress()+"",
                        list.get((int)view.getTag()).getToDetailedAddress()+"",
                        list.get((int)view.getTag()).getTrucklengthHZ()+"",
                        list.get((int)view.getTag()).getTrucktypeHZ()+"",
                        list.get((int)view.getTag()).getTrucktype()+"",
                        list.get((int)view.getTag()).getLoadaddHZ()+"",
                        list.get((int)view.getTag()).getArrivedaddHZ()+"",
                        list.get((int)view.getTag()).getInvoiceType()+"",
                        list.get((int)view.getTag()).getUploadReceipt()+"",
                        list.get((int)view.getTag()).getPaperReceipt()+"",
                        list.get((int)view.getTag()).getCompany(),
                        list.get((int)view.getTag()).getMyPriceStatus(),
                        list.get((int)view.getTag()).getOwnerbill(),
                        list.get((int)view.getTag()).getRemark()
                );
                //注意这里使用getTag方法获取position
                mOnItemClickListener.onItemClick(view,bean);
           /* }*/
       /* }).start();*/


        }
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    public void setOnFindClickListener(OnFindClickListener onFindClickListener)
    {
        this.onFindClickListener = onFindClickListener;
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    private class SrcViewHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView icon;
        private final TextView username;
        private final TextView isRenzheng;
        private final TextView zhuang;
        private final TextView xie;
        private final TextView type;
        private final TextView time;
        private final TextView carType;
        private final ImageView iv_phone;
        private final LinearLayout tel;
        private final TextView status;

        public SrcViewHolder(View itemView) {
            super(itemView);
            icon = (SimpleDraweeView) itemView.findViewById(R.id.find_iv_icon);
            username = (TextView) itemView.findViewById(R.id.find_tv_name);
            isRenzheng = (TextView) itemView.findViewById(R.id.find_is_renzheng);
            zhuang = (TextView) itemView.findViewById(R.id.find_addr_zhuang);
            xie = (TextView) itemView.findViewById(R.id.find_addr_xie);
            type = (TextView) itemView.findViewById(R.id.find_type);
            time = (TextView) itemView.findViewById(R.id.find_time);
            carType = (TextView) itemView.findViewById(R.id.find_car_type);
            iv_phone = (ImageView) itemView.findViewById(R.id.find_iv_phone);
            tel = (LinearLayout) itemView.findViewById(R.id.find_iv_tel_ll);
            status = (TextView) itemView.findViewById(R.id.find_iv_bjstatus);
        }
    }

}
