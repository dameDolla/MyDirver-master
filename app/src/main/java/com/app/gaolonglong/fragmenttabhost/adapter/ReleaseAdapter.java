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
import com.app.gaolonglong.fragmenttabhost.bean.ReleaseBean;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by yanqi on 2017/9/6.
 */

public class ReleaseAdapter extends RecyclerView.Adapter {

    private List<ReleaseBean.DataBean> list;
    private Context context;
    private TextInterface textInterface;
    private int i = 0;

    public ReleaseAdapter(Context context,List<ReleaseBean.DataBean> list)
    {
        this.context = context;
        this.list = list;
    }

    /**
     *按钮点击事件需要的方法
     */
    public void TextSetOnclick(TextInterface textInterface){
        this.textInterface=textInterface;
    }

    /**
     * 按钮点击事件对应的接口
     */
    public interface TextInterface{
        public void onclick( int position,String flag,String id,String status);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.releast_fabu_item,null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ReleaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        i=position;
        ReleaseViewHolder viewHolder = (ReleaseViewHolder)holder;
       final ReleaseBean.DataBean date = list.get(position);
        viewHolder.addr.setText(date.getFromSite()+"-"+date.getToSite());
        viewHolder.price.setText("￥"+date.getTransportOffer());
        viewHolder.date.setText(date.getEmptytime().substring(0,10));
       // viewHolder.fabudate.setText(get);
        if(date.getTruckplanStatus().equals("0"))
        {
            viewHolder.logo.setImageResource(R.mipmap.cancel);
            viewHolder.cancle.setVisibility(View.INVISIBLE);
            viewHolder.edit.setVisibility(View.INVISIBLE);
        }else if (date.getTruckplanStatus().equals("1"))
        {
            viewHolder.logo.setImageResource(R.mipmap.schedule);
        }else if (date.getTruckplanStatus().equals("2"))
        {
            viewHolder.logo.setImageResource(R.mipmap.over);
            viewHolder.cancle.setVisibility(View.INVISIBLE);
            viewHolder.edit.setVisibility(View.INVISIBLE);
        }
        viewHolder.cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textInterface != null)
                {
                    textInterface.onclick(position, Constant.RELEASE_CANCLE,date.getTruckplansGUID(),date.getTruckplanStatus());
                }
            }
        });
        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textInterface != null)
                {
                    textInterface.onclick(position,Constant.RELEASE_EDIT,date.getTruckplansGUID(),date.getTruckplanStatus());
                }
            }
        });
        viewHolder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textInterface != null)
                {
                    textInterface.onclick(position,Constant.RELEASE_DEL,date.getTruckplansGUID(),date.getTruckplanStatus());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ReleaseViewHolder extends RecyclerView.ViewHolder
    {

        private final TextView addr;
        private final TextView date;
        private final TextView price;
        private final TextView fabudate;
        private final TextView edit;
        private final TextView cancle;
        private final TextView del;
        private final SimpleDraweeView logo;

        public ReleaseViewHolder(View itemView) {
            super(itemView);

            logo = (SimpleDraweeView) itemView.findViewById(R.id.kong_progress);
            addr = (TextView) itemView.findViewById(R.id.release_fabuitem_addr);
            date = (TextView) itemView.findViewById(R.id.release_fabuitem_date);
            price = (TextView) itemView.findViewById(R.id.release_fabuitem_price);
            fabudate = (TextView) itemView.findViewById(R.id.release_fabuitem_fabudate);
            edit = (TextView) itemView.findViewById(R.id.release_fabuitem_edit);
            cancle = (TextView) itemView.findViewById(R.id.release_fabuitem_cancle);
            del = (TextView) itemView.findViewById(R.id.release_fabuitem_del);

        }
    }

  /*  @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.release_fabuitem_cancle:
                ToolsUtils.getInstance().toastShowStr(context,i+"");
        }
    }*/
}
