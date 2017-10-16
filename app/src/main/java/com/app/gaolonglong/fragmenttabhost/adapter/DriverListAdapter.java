package com.app.gaolonglong.fragmenttabhost.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.DriverBean;

import java.util.List;

/**
 * Created by yanqi on 2017/10/11.
 */

public class DriverListAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<DriverBean.DataBean> list;
    private DriverOnclick driverOnclick = null;

    public DriverListAdapter(Context context, List list)
    {
        this.context = context;
        this.list = list;
    }

    public interface DriverOnclick
    {
        public void driverOnclick(String driverguid,String flag);
    }
    public void setDriverOnclick(DriverOnclick driverOnclick)
    {
        this.driverOnclick = driverOnclick;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.select_driver_item,null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(lp);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder mHolder = (MyViewHolder) holder;
        final DriverBean.DataBean data = list.get(position);
        mHolder.name.setText(data.getUsername());
       // mHolder.tel.setText(data.get);
        mHolder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                driverOnclick.driverOnclick(data.getUserGUID(),"driver_select");
            }
        });
        mHolder.jiebang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                driverOnclick.driverOnclick(data.getUserGUID(),"driver_jb");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView name;
        private final TextView tel;
        private final TextView time;
        private final TextView count;
        private final TextView jiebang;
        private final TextView select;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.driver_name);
            tel = (TextView) itemView.findViewById(R.id.driver_tel);
            time = (TextView) itemView.findViewById(R.id.driver_time);
            count = (TextView) itemView.findViewById(R.id.driver_jiedancount);
            jiebang = (TextView) itemView.findViewById(R.id.driver_jiebang);
            select = (TextView) itemView.findViewById(R.id.driver_select);
        }
    }
}
