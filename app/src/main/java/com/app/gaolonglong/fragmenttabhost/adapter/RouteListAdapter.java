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
import com.app.gaolonglong.fragmenttabhost.bean.RouteListBean;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.suke.widget.SwitchButton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yanqi on 2017/9/1.
 */

public class RouteListAdapter extends RecyclerView.Adapter {


    private Map<Integer,String> ischeck = new HashMap<Integer, String>();

    private Context context;

    private CheckBoxInterface checkBoxInterface;

    public static interface OnRecyclerViewListener {
        void onItemClick(int position);

        boolean onItemLongClick(int position);
    }

    private CompanyInfoAdapters.OnRecyclerViewListener onRecyclerViewListener;

    public void setOnRecyclerViewListener(CompanyInfoAdapters.OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    private static final String TAG = CompanyInfoAdapters.class.getSimpleName();
    private List<RouteListBean.DataBean> list;

    public RouteListAdapter(List<RouteListBean.DataBean> list,Context context) {
        this.list = list;
        this.context = context;
    }

    /**
     * chexkbox的点击切换状态接口
     */
    public interface CheckBoxInterface
    {
        public void change(int position, String lineGuid ,Map<Integer,String> map);
    }

    /**
     * checkBox切换的方法
     * @param checkBoxInterface
     */
    public void checkBoxCheckChange(CheckBoxInterface checkBoxInterface)
    {
        this.checkBoxInterface = checkBoxInterface;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.myroute_listview, null);
//        不知道为什么在xml设置的“android:layout_width="match_parent"”无效了，需要在这里重新设置
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new RouteListAdapter.RouteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int i) {
        final RouteListAdapter.RouteViewHolder holder = (RouteListAdapter.RouteViewHolder) viewHolder;
        final RouteListBean.DataBean route = list.get(i);
        String addrs = route.getFromSite()+"-"+route.getToSite();
        String carInfos = route.getTrucktype()+"\\"+route.getTrucklength();
        holder.addr.setText(addrs);
        holder.carInfo.setText(carInfos);

        if(route.getMainLin() == "1")
        {
            holder.isMain.setChecked(true);
            ischeck.put(i,route.getLinesGUID());
        }
        holder.isMain.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                /*if(ischeck.size() != 0)
                {
                    ToolsUtils.getInstance().toastShowStr(context,"只能订阅一条路线");
                    return;
                }*/

                if (isChecked)
                {
                    checkBoxInterface.change(i,route.getLinesGUID(),ischeck);
                    /*ischeck.put(i,route.getLinesGUID());
                    ToolsUtils.getInstance().toastShowStr(context,route.getLinesGUID());*/
                }

            }
        });
       // holder.carInfo.setText(route.getLealperson());

       /* );
        holder.id.setText(person.getCompanyID());*/


        //holder.is_select.setChecked(ischeck.get(i));
        //holder.ageTv.setText(person.getAge() + "岁");
    }
    public Map<Integer,String> getMap()
    {
        return ischeck;
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    class RouteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public View rootView;
        private final TextView carInfo;
        private final TextView addr;
        private final SwitchButton isMain;


        public RouteViewHolder(View itemView) {
            super(itemView);
            addr = (TextView) itemView.findViewById(R.id.lv_route_addr);
            carInfo = (TextView) itemView.findViewById(R.id.lv_route_carinfo);
            isMain = (SwitchButton) itemView.findViewById(R.id.is_main);

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
