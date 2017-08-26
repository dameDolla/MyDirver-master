package com.app.gaolonglong.fragmenttabhost.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;

import java.util.List;

/**
 * Created by yanqi on 2017/8/25.
 */

public class CompanyInfoAdapter extends BaseAdapter {

    private Context context;
    private List list;

    public CompanyInfoAdapter(Context context, List list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if(list != null)
        {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.lv_company_info,viewGroup,false);
            view.setTag(new ViewHolder(view));
        }
        return null;
    }
    public class ViewHolder
    {
        TextView company_name,company_faren,company_lxr,company_tel,company_addr,is_shenhe,company_id;
        CheckBox check;
        public ViewHolder(View item)
        {
            company_name = (TextView)item.findViewById(R.id.company_name);
            check = (CheckBox) item.findViewById(R.id.ck_select_company);
            company_faren = (TextView)item.findViewById(R.id.company_faren);
            company_lxr = (TextView)item.findViewById(R.id.company_lxr);
            company_tel = (TextView)item.findViewById(R.id.company_tel);
            company_addr = (TextView)item.findViewById(R.id.company_addr);
            is_shenhe = (TextView)item.findViewById(R.id.is_shenhe);
            company_id = (TextView)item.findViewById(R.id.company_id);
        }
    }
}
