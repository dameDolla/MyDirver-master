package com.app.gaolonglong.fragmenttabhost.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.MissionDetailBean;
import com.app.gaolonglong.fragmenttabhost.bean.MissionListBean;
import com.app.gaolonglong.fragmenttabhost.utils.GetUserInfoUtils;

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
        void onMissionClick(int position,String missionnum,String flag,String status);
    }
    public void setOnMissionClick(OnMissionClick onMissionClick)
    {
        this.onMissionClick = onMissionClick;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mission_item, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        view.setOnClickListener(this);
        return new MissionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MissionViewHolder mHolder = (MissionViewHolder) holder;
        MissionListBean.DataBean data = list.get(position);
        String trucktype = TextUtils.isEmpty(data.getTrucktypeHZ())?"":data.getTrucktypeHZ();
        String trucklength = TextUtils.isEmpty(data.getTrucklength())?"":data.getTrucklength();
        mHolder.fromsite.setText(data.getFromDetailedAddress());
        mHolder.tosite.setText(data.getToDetailedAddress());
        mHolder.time.setText(data.getPreloadtime() + "");
        mHolder.carInfo.setText(data.getCargotype()+"/"+trucktype+"/"+trucklength);
        mHolder.itemView.setTag(position);
       // mHolder.carNum.setText(data.getTruckno());
        mHolder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMissionClick.onMissionClick(position,list.get(position).getBillsGUID()+"","cancel",list.get(position).getStatus());
            }
        });
       /* mHolder.caozuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMissionClick.onMissionClick(position,list.get(position).getBillsGUID()+"","caozuo");
            }
        });*/
       String usertype = GetUserInfoUtils.getUserType(context);
        mHolder.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMissionClick.onMissionClick(position,list.get(position).getConsigneePhone()+"","tel",list.get(position).getStatus());
            }
        });
        if (data.getStatus().equals("-2")){
            mHolder.status.setText("已取消");
            mHolder.cancel.setVisibility(View.GONE);
            mHolder.phone.setVisibility(View.GONE);
        }else if (data.getStatus().equals("0")){
            mHolder.status.setText("已生成");
            if (!usertype.equals("3")){
                mHolder.cancel.setVisibility(View.VISIBLE);
            }
        }else if (data.getStatus().equals("1")){
            mHolder.status.setText("出发接货");
            if (!usertype.equals("3")){
                mHolder.cancel.setVisibility(View.VISIBLE);
            }
        }else if (data.getStatus().equals("2")){
            mHolder.status.setText("到达装货");
        }else if (data.getStatus().equals("3")){
            mHolder.status.setText("运输中");
        }else if (data.getStatus().equals("4")){
            mHolder.status.setText("到达卸货");
        }else if (data.getStatus().equals("5")){
            mHolder.status.setText("已签收");
        }else if (data.getStatus().equals("9")){
            mHolder.status.setText("已完成");

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class MissionViewHolder extends RecyclerView.ViewHolder {

        private final TextView fromsite,tosite;
        private final TextView carInfo;
        private final TextView time;
        private final TextView status;
        private final TextView carNum;
        private final TextView cancel;
        private final TextView phone;
        private final TextView caozuo;

        public MissionViewHolder(View itemView) {
            super(itemView);
            fromsite = (TextView) itemView.findViewById(R.id.mission_item_fromsite);
            tosite = (TextView) itemView.findViewById(R.id.mission_item_tosite);
            carInfo = (TextView) itemView.findViewById(R.id.mission_items_carinfo);
            time = (TextView) itemView.findViewById(R.id.mission_items_time);
            status = (TextView) itemView.findViewById(R.id.mission_items_status);
            carNum = (TextView) itemView.findViewById(R.id.mission_items_carnum);
            cancel = (TextView) itemView.findViewById(R.id.mission_orders_cancle);
            phone = (TextView) itemView.findViewById(R.id.mission_item_call);
            caozuo = (TextView) itemView.findViewById(R.id.mission_items_caozuo);


        }
    }

    @Override
    public void onClick(View view) {
        if (onMissionItemClick != null)
        {
            beans = new MissionDetailBean(
                    list.get((int)view.getTag()).getBillsGUID(),
                    list.get((int)view.getTag()).getOwnername(),
                    list.get((int)view.getTag()).getOwnerphone(),
                    list.get((int)view.getTag()).getPreloadtime(),
                    list.get((int)view.getTag()).getFromDetailedAddress(),
                    list.get((int)view.getTag()).getToDetailedAddress(),
                    list.get((int)view.getTag()).getBillNumber(),
                    list.get((int)view.getTag()).getDealprice(),
                    list.get((int)view.getTag()).getLoadfee(),
                    list.get((int)view.getTag()).getUnloadfee(),
                    list.get((int)view.getTag()).getAvatarAddress(),
                    list.get((int)view.getTag()).getStatus(),
                    list.get((int)view.getTag()).getSigntime(),
                    list.get((int)view.getTag()).getSignby(),
                    list.get((int)view.getTag()).getDrivername(),
                    list.get((int)view.getTag()).getDriverphone(),
                    list.get((int)view.getTag()).getDriverGUID(),
                    list.get((int)view.getTag()).getTruckno(),
                    list.get((int)view.getTag()).getSigntime(),
                    list.get((int)view.getTag()).getConsignee(),
                    list.get((int)view.getTag()).getConsigneePhone(),
                    list.get((int)view.getTag()).getInsertDate(),
                    list.get((int)view.getTag()).getArrivalLoadingTime(),
                    list.get((int)view.getTag()).getLoadtime(),
                    list.get((int)view.getTag()).getArrivedtime(),
                    list.get((int)view.getTag()).getDepartureTime(),
                    list.get((int)view.getTag()).getCargotype(),
                    list.get((int)view.getTag()).getLoadaddHZ(),
                    list.get((int)view.getTag()).getArrivedaddHZ()

            );
            onMissionItemClick.onMissionItemClick(view,beans);
        }

    }
}
