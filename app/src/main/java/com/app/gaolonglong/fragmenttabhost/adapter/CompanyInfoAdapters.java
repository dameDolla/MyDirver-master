package com.app.gaolonglong.fragmenttabhost.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.CompanyInfoBean;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by yanqi on 2017/8/30.
 */

public class CompanyInfoAdapters extends RecyclerView.Adapter {

    private Map<String,String> ischeck = new HashMap<String, String>();

    private Context context;

    public static interface OnRecyclerViewListener {
        void onItemClick(int position);

        boolean onItemLongClick(int position);
    }

    private OnRecyclerViewListener onRecyclerViewListener;

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    private static final String TAG = CompanyInfoAdapters.class.getSimpleName();
    private List<CompanyInfoBean.DataBean> list;

    public CompanyInfoAdapters(List<CompanyInfoBean.DataBean> list,Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lv_company_info, null);
//        不知道为什么在xml设置的“android:layout_width="match_parent"”无效了，需要在这里重新设置
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int i) {
        PersonViewHolder holder = (PersonViewHolder) viewHolder;
        final CompanyInfoBean.DataBean person = list.get(i);
        holder.companyName.setText(person.getCompanyName());
        holder.farten.setText(person.getLealperson());
        holder.lxr.setText(person.getPerson());
        holder.addr.setText(person.getAddress());
        holder.tel.setText(person.getMobile());
        //holder.id.setText(person.getCompanyID());
       /* );
        holder.id.setText(person.getCompanyID());*/
        holder.is_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(ischeck.size() == 0)
                {
                    ischeck.put("cguid",person.getCompanysGUID());
                }
                else
                {
                    ischeck.clear();
                    ischeck.put("cguid",person.getCompanysGUID());
                    //ToolsUtils.getInstance().toastShowStr(context,"只能绑定一个公司");
                }

            }
        });
        // 设置CheckBox的状态
        if (ischeck.get(i) == null) {
            //ischeck.put(i, false);
        }
        //holder.is_select.setChecked(ischeck.get(i));
        //holder.ageTv.setText(person.getAge() + "岁");
    }
    public Map<String,String> getMap()
    {
        return ischeck;
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public View rootView;
        private final TextView companyName;
        private final TextView farten;
        private final TextView lxr;
        private final TextView tel;
        private final TextView addr;
        private final TextView is_shenhe;
        private final TextView id;
        private final CheckBox is_select;


        public PersonViewHolder(View itemView) {
            super(itemView);
            companyName = (TextView) itemView.findViewById(R.id.company_name);
            farten = (TextView) itemView.findViewById(R.id.company_faren);
            lxr = (TextView) itemView.findViewById(R.id.company_lxr);
            tel = (TextView) itemView.findViewById(R.id.company_tel);
            addr = (TextView) itemView.findViewById(R.id.company_addr);
            is_shenhe = (TextView) itemView.findViewById(R.id.company_faren);
            id = (TextView) itemView.findViewById(R.id.company_id);
            is_select = (CheckBox) itemView.findViewById(R.id.ck_select_company);
            // rootView = itemView.findViewById(R.id.recycler_view_test_item_person_view);
            //rootView.setOnClickListener(this);
            //rootView.setOnLongClickListener(this);
        }



        @Override
        public void onClick(View v) {
            if (null != onRecyclerViewListener) {
                onRecyclerViewListener.onItemClick(this.getPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (null != onRecyclerViewListener) {
                return onRecyclerViewListener.onItemLongClick(this.getPosition());
            }
            return false;
        }
    }
}
