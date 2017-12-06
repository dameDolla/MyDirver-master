package com.mr.truck.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mr.truck.R;
import com.mr.truck.config.Constant;
import com.mr.truck.utils.ToolsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yanqi on 2017/10/2.
 */

public class GuidActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.guid_viewpager)
    public ViewPager viewPager;

    @BindView(R.id.guid_indicator)
    public LinearLayout linearLayout;

    @BindView(R.id.guide_ib_start)
    public Button start;

    private int[] imgArr = new int[]{R.mipmap.splash,R.mipmap.splash,R.mipmap.splash,R.mipmap.splash};
    private final int GUID_PAGE_COUNT = 4;
    private List<View> viewList;
    private ImageView[] indicatorImg;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        initData();
        initView();

    }



    private void initData()
    {
        indicatorImg = new ImageView[GUID_PAGE_COUNT];
        viewList = new ArrayList<View>(GUID_PAGE_COUNT);
        for (int i=0;i<GUID_PAGE_COUNT;i++)
        {
            View view = LayoutInflater.from(GuidActivity.this).inflate(R.layout.activity_guide_item,null);
            view.setBackgroundResource(R.color.white);
            ((ImageView) view.findViewById(R.id.guide_image)).setBackgroundResource(imgArr[i]);
            viewList.add(view);
            indicatorImg[i] = new ImageView(this);
            if (i == 0)
            {
                indicatorImg[i].setBackgroundResource(R.mipmap.ic_indicators_selected);
            }
            else
            {
                indicatorImg[i].setBackgroundResource(R.mipmap.ic_indicators_default);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2,-2);
                params.setMargins(20,0,0,0);
                indicatorImg[i].setLayoutParams(params);
            }
            linearLayout.addView(indicatorImg[i]);
        }
    }
    private void initView()
    {
        start.setOnClickListener(this);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return viewList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(viewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewList.get(position));
                return viewList.get(position);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               // setIndicator(position);
                setIndicator(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    public void setIndicator(int targetIndex) {
        for (int i = 0; i < indicatorImg.length; i++) {
            indicatorImg[i].setBackgroundResource(R.mipmap.ic_indicators_selected);
            if (targetIndex != i) {
                indicatorImg[i].setBackgroundResource(R.mipmap.ic_indicators_default);
            }
        }
        if (targetIndex == indicatorImg.length-1)
        {
            start.setVisibility(View.VISIBLE);
        }else {
            start.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        if (ToolsUtils.getInstance().isLogin(GuidActivity.this))
        {
            intent = new Intent(GuidActivity.this,MainActivity.class);
        }else{
            intent = new Intent(GuidActivity.this,LoginActivity.class);
        }

        intent.putExtra("flag","splash");
        startActivity(intent);
        ToolsUtils.putBoolean(GuidActivity.this, Constant.iSFIRST,false);
    }
}
