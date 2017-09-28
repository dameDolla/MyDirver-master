package com.app.gaolonglong.fragmenttabhost.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;

import java.util.List;

/**
 * Created by yanqi on 2017/9/27.
 */

public class MissionListAdapter extends RecyclerView.Adapter implements View.OnClickListener{
    private Context context;
    private List<MissionListBean.DataBean> list;

    public MissionListAdapter(Context context,List<MissionListBean.DataBean> list)
    {
        this.context = context;
        this.list = list;
    }
    private OnMissionItemClick onMissionItemClick;
    public interface OnMissionItemClick{
        void onMissionItemClick(View view);
    }
    public void setOnMissionItemClick(OnMissionItemClick onMissionItemClick)
    {
        this.onMissionItemClick = onMissionItemClick;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.missionlist_item,null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        view.setOnClickListener(this);
        return new MissionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MissionViewHolder mHolder = (MissionViewHolder) holder;
        MissionListBean.DataBean data = list.get(position);
        mHolder.addr.setText(data.getFromSite()+"-"+data.getToSite());
        mHolder.time.setText(data.getPreloadtime()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class MissionViewHolder extends RecyclerView.ViewHolder
    {

        private final TextView addr;
        private final TextView carInfo;
        private final TextView time;
        private final TextView status;
        private final TextView carNum;
        private final TextView cancel;
        private final TextView caozuo;
        private final ImageView phone;

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
        }
    }

    @Override
    public void onClick(View view) {
        onMissionItemClick.onMissionItemClick(view);
    }
}
