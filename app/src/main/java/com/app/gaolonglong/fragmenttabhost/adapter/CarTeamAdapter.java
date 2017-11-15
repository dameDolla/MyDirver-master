package com.app.gaolonglong.fragmenttabhost.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.CarTeamBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by yanqi on 2017/10/3.
 */

public class CarTeamAdapter extends RecyclerView.Adapter{
    private Context context;
    private List<CarTeamBean.DataBean> list;
    private View contentView;
    private carteamClick onclick;
    private String flag;

    public CarTeamAdapter(Context context, List<CarTeamBean.DataBean> list)
    {
        this.context = context;
        this.list = list;
    }
    public void setFlag(String flag)
    {
        this.flag = flag;
    }
    public interface carteamClick{
         void OnCarteamClick(String trucktype,String truckno,String trucklength,String truckguid,String flags);
    }
    public void setCarteamClick(carteamClick onclick)
    {
        this.onclick = onclick;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.carteam_item,null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(lp);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder mHolder = (MyViewHolder) holder;
        final CarTeamBean.DataBean data = list.get(position);
        String vtruck = data.getVtruck()+"";
        mHolder.carNum.setText(data.getTruckno());
        mHolder.type.setText(data.getTrucktype());
        mHolder.length.setText("长"+data.getTrucklength());
        mHolder.weight.setText("重"+data.getTruckWeight());
        mHolder.kuan.setText("宽"+data.getTruckWidth());
        mHolder.gao.setText("高"+data.getTruckHeight());
        mHolder.logo.setImageURI(Uri.parse(data.getTruckImg()));
        if (vtruck.equals("0")) { //未认证
            mHolder.status.setText("未认证");
            //mHolder.status.setTextColor(Color.RED);
        }else if (vtruck.equals("1")) {
            mHolder.status.setText("已提交");
            //mHolder.status.setTextColor(Color.YELLOW);
        }else if (vtruck.equals("2")){
            mHolder.status.setText("不合格");
            //mHolder.status.setTextColor(Color.RED);
        }else if (vtruck.equals("9")){
            mHolder.status.setText("已认证");
            //mHolder.status.setTextColor(Color.GREEN);
        }
        mHolder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclick.OnCarteamClick(data.getTrucktype(),data.getTruckno(),data.getTrucklength(),data.getTrucksGUID(),"del");
            }
        });
        mHolder.bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclick.OnCarteamClick(data.getTrucktype(),data.getTruckno(),data.getTrucklength(),data.getTrucksGUID(),"bind");
            }
        });
        if (flag.equals("missiondetail")){
            mHolder.bind.setVisibility(View.VISIBLE);
            mHolder.del_ll.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder{

        private final SimpleDraweeView logo;
        private final TextView carNum;
        private final TextView type;
        private final TextView length;
        private final TextView weight;
        private final TextView tiji,del,kuan,gao;
        private final TextView status;
        private final LinearLayout bind,del_ll;

        public MyViewHolder(View itemView) {
            super(itemView);
            logo = (SimpleDraweeView) itemView.findViewById(R.id.carteam_item_icon);
            carNum = (TextView) itemView.findViewById(R.id.carteam_item_carnum);
            type = (TextView) itemView.findViewById(R.id.carteam_item_cartype);
            length = (TextView) itemView.findViewById(R.id.carteam_item_carlenth);
            kuan = (TextView) itemView.findViewById(R.id.carteam_item_carkuan);
            gao = (TextView) itemView.findViewById(R.id.carteam_item_carheight);
            weight = (TextView) itemView.findViewById(R.id.carteam_item_weight);
            tiji = (TextView) itemView.findViewById(R.id.carteam_item_tiji);
            del = (TextView) itemView.findViewById(R.id.carteam_item_del);
            status = (TextView) itemView.findViewById(R.id.carteam_item_status);
            bind = (LinearLayout) itemView.findViewById(R.id.carteam_item_bind_ll);
            del_ll = (LinearLayout) itemView.findViewById(R.id.carteam_item_ll);
        }


    }


}
