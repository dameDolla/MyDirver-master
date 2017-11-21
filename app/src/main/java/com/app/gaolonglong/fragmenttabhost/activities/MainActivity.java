package com.app.gaolonglong.fragmenttabhost.activities;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.DiverApplication;
import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.LocationInfo;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.fragments.BackFragment;
import com.app.gaolonglong.fragmenttabhost.fragments.BaojiaFragment;
import com.app.gaolonglong.fragmenttabhost.fragments.FindFragment;
import com.app.gaolonglong.fragmenttabhost.fragments.MineFragment;
import com.app.gaolonglong.fragmenttabhost.fragments.MissionFragment;
import com.app.gaolonglong.fragmenttabhost.service.LocationService;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.app.gaolonglong.fragmenttabhost.view.CommomDialog;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private DiverApplication mApplication;
    private FragmentTabHost mTabHost;
    private ViewPager mViewPager;
    private List<Fragment> mFragmentList;
    private Class mClass[] = {BaojiaFragment.class, BackFragment.class, FindFragment.class, MissionFragment.class, MineFragment.class};
    private Fragment mFragment[] = {new BaojiaFragment(), new BackFragment(), new FindFragment(), new MissionFragment(), new MineFragment()};
    private String mTitles[] = {"报价", "空程", "找货", "运单", "我的"};
    private int mImages[] = {
            R.drawable.tab_baojia,
            R.drawable.tab_back,
            R.drawable.tab_find,
            R.drawable.tab_mission,
            R.drawable.tab_mine
    };
    private ImageView image;
    private TextView title;
    private ImageView mCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        toFindFragment();
    }


    private void init() {
        ToolsUtils.getInstance().requestPermissions(MainActivity.this);
        initExit();

        initView();
        //ToolsUtils.getInstance().addStatusViewWithColor(this,getResources().getColor(R.color.shen_blue));
        initEvent();
        checkUpdate();
        Intent intent = new Intent(MainActivity.this, LocationService.class);
        //bindService(intent,conn, Context.BIND_AUTO_CREATE);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        //this.unbindService(conn);
        super.onDestroy();
    }

    private void initExit() {
        if (mApplication == null) {
            mApplication = (DiverApplication) getApplication();
        }
        mApplication.addActivity(MainActivity.this);
    }
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    private void initView() {

        // EventBus.getDefault().register(MainActivity.this);
        //EventBus.getDefault().post(new LocationInfo("测试","","",""));
        mCenter = (ImageView) findViewById(R.id.main_image_center);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        mFragmentList = new ArrayList<Fragment>();
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        mTabHost.getTabWidget().setDividerDrawable(null);

        for (int i = 0; i < mFragment.length; i++) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTitles[i]).setIndicator(getTabView(i));
            mTabHost.addTab(tabSpec, mClass[i], null);
            mFragmentList.add(mFragment[i]);
            mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.WHITE);
        }

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }
        });
        //toFindFragment();
    }

    private View getTabView(int index) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_item, null);

        image = (ImageView) view.findViewById(R.id.image);
        title = (TextView) view.findViewById(R.id.title);
        if (index == 2) {

        }
        image.setImageResource(mImages[index]);
        title.setText(mTitles[index]);

        return view;
    }

    private void initEvent() {

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

                mViewPager.setCurrentItem(mTabHost.getCurrentTab());
                switch (tabId) {
                    case "找货":
                        // ToolsUtils.getInstance().toastShowStr(MainActivity.this,tabId);
                        mCenter.setImageResource(R.drawable.tab_find_light);
                        //title.setTextColor(Color.parseColor("#ffd38a"));
                        break;
                    default:
                        mCenter.setImageResource(R.drawable.tab_find);
                        //title.setTextColor(Color.parseColor("#b2b2b2"));
                        break;
                }
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabHost.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void toFindFragment() {
        String flag = TextUtils.isEmpty(getIntent().getStringExtra("flag"))?"splash":getIntent().getStringExtra("flag");
        if (flag.equals("splash")) {
            mTabHost.setCurrentTab(2);
        }
        if (flag.equals("baojiaEdit")) {
            mTabHost.setCurrentTab(0);
        }
    }

    private void checkUpdate() {

        String url = ToolsUtils.getString(MainActivity.this, Constant.DOWNLOADURL, "");
        //String url = getIntent().getStringExtra("downloadUrl")+"";

        if (!url.equals("")) {
            CommomDialog dialog = new CommomDialog(MainActivity.this, R.style.dialog, "有新版本更新", new CommomDialog.OnCloseListener() {
                @Override
                public void onClick(Dialog dialog, boolean confirm) {
                    dialog.dismiss();
                    if (confirm) {
                        ToolsUtils.getInstance().downLoadApk(MainActivity.this);
                    } else {
                        ToolsUtils.putString(MainActivity.this, Constant.DOWNLOADURL, "");
                    }
                }
            });
            dialog.setNegativeButton("取消");
            dialog.setPositiveButton("更新");
            dialog.show();

            //dialog.setOnShowListener();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    /**
     * 双击退出
     */
    private long time = 0;

    private void exits() {
        if (System.currentTimeMillis() - time > 2000) {
            time = System.currentTimeMillis();
            ToolsUtils.getInstance().toastShowStr(MainActivity.this, Constant.EXIT_STR);
        } else {
            mApplication.removeAllActivity();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) exits();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {

            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.tabcontent, new BackFragment())
                    .commit();
        }
    }
}
