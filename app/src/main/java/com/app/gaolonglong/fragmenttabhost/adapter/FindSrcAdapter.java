package com.app.gaolonglong.fragmenttabhost.adapter;

import android.content.Context;
import android.net.Uri;
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

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.find_src_item, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        view.setOnClickListener(this);
        return new SrcViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //0、注册用户；1、认证用户；2、诚信用户。新注册用户标记为0，完成认证标记为1.
        SrcViewHolder holder1 = (SrcViewHolder) holder;
        data = list.get(position);
        holder1.username.setText(data.getOwnername() + "");
        holder1.zhuang.setText(data.getFromSite() + "");
        holder1.xie.setText(data.getToSite() + "");
        String cartype = data.getQty() + "" + data.getTrucklength() + "米 " + data.getTrucktype();
        holder1.carType.setText(cartype);
        holder1.type.setText(data.getCargotype());
        holder1.time.setText(data.getPreloadtime() + "");
        holder1.icon.setImageURI(Uri.parse(data.getAvatarAddress()));
        //holder1.username.setText(data.getOwnername().toString());
        //holder1.isRenzheng.setText("注册用户");
        if (data.getCreditlevel().toString().equals("0")) {
            holder1.isRenzheng.setText("注册用户");
        } else if (data.getCreditlevel().toString().equals("1")) {
            holder1.isRenzheng.setText("认证用户");
        } else if (data.getCreditlevel().toString().equals("2")) {
            holder1.isRenzheng.setText("诚信用户");
        }
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {

           ToSrcDetailBean bean = new ToSrcDetailBean(
                   data.getFromSite(),
                   data.getToSite(),
                   data.getOwnername()+"",
                   data.getCargotype(),
                   data.getPreloadtime(),
                   data.getAvatarAddress(),
                   data.getCreditlevel(),
                   data.getQty()+"",
                   data.getUnit()+"",
                   data.getOwnerphone()+"",
                   data.getDriverdeposit(),
                   data.getOwneridGUID()+"",
                   data.getBillsGUID()+"",
                   data.getDriverGUID()+"",
                   data.getDrivername()+"",
                   data.getCompanyGUID()+"",
                   data.getCompany()+"",
                   data.getTruckno()+"",
                   data.getTrucklength()+""

           );
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(view,bean);
        }
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
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
        }
    }

}
