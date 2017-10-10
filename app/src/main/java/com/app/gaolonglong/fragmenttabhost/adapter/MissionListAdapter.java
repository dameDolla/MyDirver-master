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
import com.app.gaolonglong.fragmenttabhost.bean.MissionDetailBean;
import com.app.gaolonglong.fragmenttabhost.bean.MissionListBean;

import java.util.List;

/**
 * Created by yanqi on 2017/9/27.
 */

public class MissionListAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private Context context;
    private List<MissionListBean.DataBean> list;
    private MissionDetailBean beans;

    public MissionListAdapter(Context context, List<MissionListBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    private OnMissionItemClick onMissionItemClick;

    public interface OnMissionItemClick {
        void onMissionItemClick(View view,MissionDetailBean bean);
    }

    public void setOnMissionItemClick(OnMissionItemClick onMissionItemClick) {
        this.onMissionItemClick = onMissionItemClick;
    }

    public OnMissionClick onMissionClick;
    public interface OnMissionClick{
        void onMissionClick(int position,String missionnum,String flag);
    }
    public void setOnMissionClick(OnMissionClick onMissionClick)
    {
        this.onMissionClick = onMissionClick;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.missionlist_item, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        view.setOnClickListener(this);
        return new MissionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MissionViewHolder mHolder = (MissionViewHolder) holder;
        MissionListBean.DataBean data = list.get(position);
        mHolder.addr.setText(data.getFromSite() + "-" + data.getToSite());
        mHolder.time.setText("装车时间: "+data.getPreloadtime() + "");
        mHolder.carInfo.setText(data.getCargotype());
        mHolder.itemView.setTag(position);
        mHolder.carNum.setText("车牌号:" + data.getTruckno());
        mHolder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMissionClick.onMissionClick(position,list.get(position).getBillsGUID()+"","cancel");
            }
        });
        mHolder.caozuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMissionClick.onMissionClick(position,list.get(position).getBillsGUID()+"","caozuo");
            }
        });
        mHolder.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMissionClick.onMissionClick(position,list.get(position).getOwnerphone()+"","tel");
            }
        });
        if (data.getStatus().equals("-2")){
            mHolder.status.setText("已取消");
            mHolder.gray.setVisibility(View.VISIBLE);
            mHolder.caozuo1.setVisibility(View.GONE);
        }else if (data.getStatus().equals("0")){
            mHolder.status.setText("已生成");
        }else if (data.getStatus().equals("1")){
            mHolder.status.setText("已预报");
        }else if (data.getStatus().equals("2")){
            mHolder.status.setText("已执行");
        }else if (data.getStatus().equals("3")){
            mHolder.status.setText("已卸货");
        }else if (data.getStatus().equals("4")){
            mHolder.status.setText("已签收");
        }else if (data.getStatus().equals("9")){
            mHolder.status.setText("已完成");
            mHolder.gray.setVisibility(View.VISIBLE);
            mHolder.caozuo1.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class MissionViewHolder extends RecyclerView.ViewHolder {

        private final TextView addr;
        private final TextView carInfo;
        private final TextView time;
        private final TextView status;
        private final TextView carNum;
        private final TextView cancel;
        private final TextView caozuo;
        private final ImageView phone;
        private final LinearLayout caozuo1;
        private final LinearLayout gray;

        public MissionViewHolder(View itemView) {
            super(itemView);
            addr = (TextView) itemView.findViewById(R.id.mission_item_addr);
            carInfo = (TextView) itemView.findViewById(R.id.mission_item_carinfo);
            time = (TextView) itemView.findViewById(R.id.mission_item_time);
            status = (TextView) itemView.findViewById(R.id.mission_item_status);
            carNum = (TextView) itemView.findViewById(R.id.mission_item_carnum);
            cancel = (TextView) itemView.findViewById(R.id.mission_order_cancle);
            caozuo = (TextView) itemView.findViewById(R.id.mission_order_caozuo);
            phone = (ImageView) itemView.findViewById(R.id.mission_item_phone);
            caozuo1 = (LinearLayout) itemView.findViewById(R.id.mission_item_caozuo);
            gray = (LinearLayout) itemView.findViewById(R.id.mission_item_gray);

        }
    }

    @Override
    public void onClick(View view) {
        if (onMissionItemClick != null)
        {
            beans = new MissionDetailBean(
                    list.get((int)view.getTag()).getBillsGUID()+"",
                    list.get((int)view.getTag()).getOwnername()+"",
                    list.get((int)view.getTag()).getOwnerphone()+"",
                    list.get((int)view.getTag()).getPreloadtime()+"",
                    list.get((int)view.getTag()).getFromDetailedAddress()+"",
                    list.get((int)view.getTag()).getToDetailedAddress()+"",
                    list.get((int)view.getTag()).getBillNumber()+"",
                    list.get((int)view.getTag()).getDealprice()+"",
                    list.get((int)view.getTag()).getLoadfee()+"",
                    list.get((int)view.getTag()).getUnloadfee()+"",
                    list.get((int)view.getTag()).getAvatarAddress()+"",
                    list.get((int)view.getTag()).getStatus()+"",
                    list.get((int)view.getTag()).getSigntime()+"",
                    list.get((int)view.getTag()).getSignby()+"",
                    list.get((int)view.getTag()).getDrivername()+"",
                    list.get((int)view.getTag()).getDriverphone()+"",
                    list.get((int)view.getTag()).getDriverGUID()+"",
                    (list.get((int)view.getTag()).getTruckno()).equals(null)?list.get((int)view.getTag()).getTruckno():""+""
            );
            onMissionItemClick.onMissionItemClick(view,beans);
        }

    }
}
