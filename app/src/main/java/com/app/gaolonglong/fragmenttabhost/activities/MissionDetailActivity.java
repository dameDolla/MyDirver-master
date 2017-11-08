package com.app.gaolonglong.fragmenttabhost.activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.bean.MissionDetailBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.service.LocationService;
import com.app.gaolonglong.fragmenttabhost.utils.GetUserInfoUtils;
import com.app.gaolonglong.fragmenttabhost.utils.JsonUtils;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.app.gaolonglong.fragmenttabhost.view.CommomDialog;
import com.app.gaolonglong.fragmenttabhost.view.SetSignByDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.luoxudong.app.threadpool.ThreadPoolHelp;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yanqi on 2017/9/28.
 */

public class MissionDetailActivity extends BaseActivity implements View.OnClickListener {
    private String STATUS1 = "运单已生成";
    private String STATUS2 = "司机已前往装货地点";
    private String STATUS3 = "司机已到达装货地点";
    private String STATUS4 = "运输中";
    private String STATUS5 = "司机到达卸货地点";
    private String STATUS6 = "卸货已完成";
    private String STATUS7 = "已签收";
    private String STATUS8 = "已完成";

    private String BUTTONTXT1 = "前往装货地";
    private String BUTTONTXT2 = "到达装货地";
    private String BUTTONTXT3 = "发往目的地";
    private String BUTTONTXT4 = "到达卸货地";
    private String BUTTONTXT5 = "收货人签收";
    private MissionDetailBean bean;
    private String methodName = null;
    private static final int REQUESTCODE_DRIVER = 110;
    private static final int REQUESTCODE_CAR = 111;
    private String infos = "";
    private SetSignByDialog setSignByDialog;
    private String name;
    private String mobile;
    private String date;

    //请求相机
    private static final int REQUEST_CAPTURE = 100;
    //请求相册
    private static final int REQUEST_PICK = 101;
    //请求截图
    private static final int REQUEST_CROP_PHOTO = 102;
    //请求访问外部存储
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 103;
    //请求写入外部存储
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104;

    private File tempFile, file;

    private List<Uri> uList = new ArrayList<Uri>();

    private int position = 0;
    private String usertype;

    @OnClick(R.id.title_back_txt)
    public void back() {
        finish();
    }

    @OnClick(R.id.title_back)
    public void backs() {
        finish();
    }

    @BindView(R.id.top_title)
    public TextView title;
    @BindViews({R.id.mission_detail_status, R.id.mission_detail_time, R.id.missison_detail_name,
            R.id.mission_detail_addr, R.id.mission_detail_bzj, R.id.mission_detail_yun,
            R.id.mission_detail_zhuang, R.id.mission_detail_xie, R.id.mission_detail_missionnum,
            R.id.mission_detail_donetime})
    public List<TextView> mText;

    @BindViews({R.id.mission_statustwo_nowaddrtxt, R.id.mission_statustwo_nowaddr, R.id.mission_status_two,
            R.id.mission_status_three, R.id.mission_statusthree_tositetxt, R.id.mission_statusthree_tosite,
            R.id.mission_status_four, R.id.mission_statusfour_txt,
            R.id.mission_status_five, R.id.mission_statusfive_txt, R.id.mission_statussix_txt,
            R.id.mission_status_six, R.id.mission_statusseven_txt, R.id.mission_status_seven})
    public List<TextView> status;

    @BindView(R.id.mission_detail_step_addr)
    public TextView zcaddr;

    @BindViews({R.id.mission_detail_shrname, R.id.mission_detail_shrtel, R.id.mission_status_tosite,
            R.id.mission_detail_drivername, R.id.mission_detail_drivertel, R.id.mission_detail_truckno})
    public List<TextView> info;

    @BindViews({R.id.mission_detail_changestatus, R.id.mission_detail_copy})
    public List<Button> mButton;
    @BindView(R.id.mission_detail_icon)
    public SimpleDraweeView icons;

    @BindView(R.id.mission_status_verticalline)
    public ImageView vertical;

    @BindView(R.id.mission_detail_phone)
    public LinearLayout phone;

    @BindViews({R.id.mission_status1_time, R.id.mission_status2_time, R.id.mission_status3_time,
            R.id.mission_status4_time, R.id.mission_status5_time, R.id.mission_status6_time,
            R.id.mission_status7_time})
    public List<TextView> mTime;

    @BindView(R.id.mission_status_upload)
    public Button upload;

    @BindView(R.id.mission_status_qsd)
    public SimpleDraweeView pic;

    @BindView(R.id.mission_detail_selectcar)
    public Button selectCar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.misssion_status);
        ButterKnife.bind(this);
        init();
    }


    private void init() {
        initView();
    }

    private void initView() {
        title.setText("运单详情");
        mButton.get(0).setOnClickListener(this);
        mButton.get(1).setOnClickListener(this);
        selectCar.setOnClickListener(this);
        phone.setOnClickListener(this);
        SimpleDateFormat mDataFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        date = mDataFormat.format(Calendar.getInstance().getTime());
        showData();
        usertype = GetUserInfoUtils.getUserType(MissionDetailActivity.this);
        if (usertype.equals("3") || usertype.equals("2")){
            initLocation();
        }
       // Intent intentService = new Intent(MissionDetailActivity.this, LocationService.class);
        //bindService(intentService,conn,Context.BIND_AUTO_CREATE);

    }

    private ServiceConnection conn = new ServiceConnection() {

        private LocationService service;

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            service = ((LocationService.MyBinder)iBinder).getLocationService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    private void showData() {
        bean = (MissionDetailBean) getIntent().getSerializableExtra("missionDetail");
        mText.get(1).setText("预计装车时间: " + bean.getPreloadtime());
        mText.get(2).setText(bean.getOwnername());
        mText.get(3).setText(bean.getFromDetailedAddress());
        mText.get(4).setText("200");
        mText.get(5).setText(bean.getDealprice());
        mText.get(6).setText(bean.getLoadfee());
        mText.get(7).setText(bean.getUnloadfee());
        mText.get(8).setText(bean.getBillNumber());
        info.get(0).setText(TextUtils.isEmpty((bean.getConsignee())) ? "" : bean.getConsignee());
        info.get(1).setText(TextUtils.isEmpty((bean.getConsigneePhone())) ? "" : bean.getConsigneePhone());
        info.get(2).setText(bean.getToDetailedAddress());
        info.get(3).setText(bean.getDrivername());
        info.get(4).setText(bean.getDriverphone());


        if (TextUtils.isEmpty(bean.getTruckno())){
            selectCar.setVisibility(View.VISIBLE);
        }else {
            info.get(5).setText(bean.getTruckno());
        }

        mTime.get(0).setText(TextUtils.isEmpty((bean.getInsertDate())) ? "" : bean.getInsertDate());
        mTime.get(1).setText(TextUtils.isEmpty((bean.getDepartureTime())) ? "" : bean.getDepartureTime());
        mTime.get(2).setText(TextUtils.isEmpty((bean.getArrivalLoadingTime())) ? "" : bean.getArrivalLoadingTime());
        mTime.get(3).setText(TextUtils.isEmpty((bean.getLoadtime())) ? "" : bean.getLoadtime());
        mTime.get(4).setText(TextUtils.isEmpty((bean.getArrivedtime())) ? "" : bean.getArrivedtime());
        mTime.get(5).setText(TextUtils.isEmpty((bean.getSigntime())) ? "" : bean.getSigntime());
        //mTime.get(6).setText(bean.getInsertDate());
        icons.setImageURI(Uri.parse(bean.getAvatarAddress()));
        String usertype = GetUserInfoUtils.getUserType(MissionDetailActivity.this);
        /*else{*/
        //Log.e("missiondetail",bean.getDriverGUID());
        if (bean.getStatus().equals("0")) {  //已生成
            if (usertype.equals("4") && !TextUtils.isEmpty(bean.getDriverGUID())) {
                mText.get(0).setText("已调度");
                Log.e("missiond-driverguid",bean.getDriverGUID()+"0");
            } else {
                mText.get(0).setText(STATUS1);
            }

            methodName = Config.MISSION_STATUS_YUBAO;
            mButton.get(0).setText(BUTTONTXT1);
            infos = BUTTONTXT1;
            mText.get(3).setTextColor(Color.GRAY);
            zcaddr.setText(bean.getFromDetailedAddress());
            //status.get(1).setText("当前位置:"+ToolsUtils.getString(MissionDetailActivity.this,Constant.ADDRESS,""));
        } else if (bean.getStatus().equals("1")) {  //出发接货
            infos = BUTTONTXT2;
            mText.get(0).setText(STATUS2);
            methodName = Config.MISSION_STATUS_ARRIVED;
            vertical.setImageResource(R.drawable.vertical_line_2);
            mButton.get(0).setText(BUTTONTXT2);
            zcaddr.setText(bean.getFromDetailedAddress());
            status.get(1).setText("当前位置:" + ToolsUtils.getString(MissionDetailActivity.this, Constant.ADDRESS, ""));
            status.get(0).setTextColor(Color.BLACK);
            status.get(1).setTextColor(Color.GRAY);
            status.get(2).setTextColor(Color.BLACK);
            mText.get(3).setTextColor(Color.GRAY);

        } else if (bean.getStatus().equals("2")) {  //到达装货
            infos = BUTTONTXT3;
            mText.get(0).setText(STATUS3);
            methodName = Config.MISSION_STATUS_ZHIXING;
            status.get(1).setText("当前位置:" + ToolsUtils.getString(MissionDetailActivity.this, Constant.ADDRESS, ""));
            mButton.get(0).setText(BUTTONTXT3);
            vertical.setImageResource(R.drawable.vertical_line_3);
            zcaddr.setText(bean.getFromDetailedAddress());
            status.get(5).setText("当前位置:" + ToolsUtils.getString(MissionDetailActivity.this, Constant.ADDRESS, ""));
            status.get(0).setTextColor(Color.BLACK);
            status.get(1).setTextColor(Color.GRAY);
            status.get(2).setTextColor(Color.BLACK);
            status.get(3).setTextColor(Color.BLACK);
            status.get(4).setTextColor(Color.BLACK);
            status.get(5).setTextColor(Color.GRAY);
            mText.get(3).setTextColor(Color.GRAY);

        } else if (bean.getStatus().equals("3")) {//运输中
            infos = BUTTONTXT4;
            mText.get(0).setText(STATUS4);
            methodName = Config.MISSION_STATUS_XIEHUO;
            status.get(1).setText("当前位置:" + ToolsUtils.getString(MissionDetailActivity.this, Constant.ADDRESS, ""));
            mButton.get(0).setText(BUTTONTXT4);
            vertical.setImageResource(R.drawable.vertical_line_4);
            zcaddr.setText(bean.getFromDetailedAddress());
            status.get(5).setText("当前位置:" + ToolsUtils.getString(MissionDetailActivity.this, Constant.ADDRESS, ""));
            status.get(7).setText("当前位置:" + ToolsUtils.getString(MissionDetailActivity.this, Constant.ADDRESS, ""));
            status.get(0).setTextColor(Color.BLACK);
            status.get(1).setTextColor(Color.GRAY);
            status.get(2).setTextColor(Color.BLACK);
            status.get(3).setTextColor(Color.BLACK);
            status.get(4).setTextColor(Color.BLACK);
            status.get(5).setTextColor(Color.GRAY);
            status.get(6).setTextColor(Color.BLACK);
            status.get(7).setTextColor(Color.GRAY);
            mText.get(3).setTextColor(Color.GRAY);
        } else if (bean.getStatus().equals("4")) {  //到达卸货
            mText.get(0).setText(STATUS5);
            mButton.get(0).setText(BUTTONTXT5);
            mButton.get(0).setEnabled(true);
            methodName = Config.MISSION_STATUS_QIANSHOU;
            vertical.setImageResource(R.drawable.vertical_line_4);
            zcaddr.setText(bean.getFromDetailedAddress());
            status.get(5).setText("当前位置:" + ToolsUtils.getString(MissionDetailActivity.this, Constant.ADDRESS, ""));
            status.get(7).setText("当前位置:" + ToolsUtils.getString(MissionDetailActivity.this, Constant.ADDRESS, ""));
            status.get(9).setText("当前位置:" + ToolsUtils.getString(MissionDetailActivity.this, Constant.ADDRESS, ""));
            status.get(1).setText("当前位置:" + ToolsUtils.getString(MissionDetailActivity.this, Constant.ADDRESS, ""));
            status.get(0).setTextColor(Color.BLACK);
            status.get(1).setTextColor(Color.GRAY);
            status.get(2).setTextColor(Color.BLACK);
            status.get(3).setTextColor(Color.BLACK);
            status.get(4).setTextColor(Color.BLACK);
            status.get(5).setTextColor(Color.GRAY);
            status.get(6).setTextColor(Color.BLACK);
            status.get(7).setTextColor(Color.GRAY);
            status.get(8).setTextColor(Color.BLACK);
            status.get(9).setTextColor(Color.GRAY);
            mText.get(3).setTextColor(Color.GRAY);
        } else if (bean.getStatus().equals("5")) {  //已签收
            mText.get(0).setText(STATUS7);
            mButton.get(0).setText(STATUS7);
            mButton.get(0).setEnabled(false);
            methodName = Config.MISSION_STATUS_QIANSHOU;
            vertical.setImageResource(R.drawable.vertical_line_4);
            zcaddr.setText(bean.getFromDetailedAddress());
            status.get(5).setText("当前位置:" + ToolsUtils.getString(MissionDetailActivity.this, Constant.ADDRESS, ""));
            status.get(7).setText("当前位置:" + ToolsUtils.getString(MissionDetailActivity.this, Constant.ADDRESS, ""));
            status.get(9).setText("当前位置:" + ToolsUtils.getString(MissionDetailActivity.this, Constant.ADDRESS, ""));
            status.get(10).setText("货物已签收,签收人:" + bean.getSignby());
            status.get(1).setText("当前位置:" + ToolsUtils.getString(MissionDetailActivity.this, Constant.ADDRESS, ""));
            status.get(0).setTextColor(Color.BLACK);
            status.get(1).setTextColor(Color.GRAY);
            status.get(2).setTextColor(Color.BLACK);
            status.get(3).setTextColor(Color.BLACK);
            status.get(4).setTextColor(Color.BLACK);
            status.get(5).setTextColor(Color.GRAY);
            status.get(6).setTextColor(Color.BLACK);
            status.get(7).setTextColor(Color.GRAY);
            status.get(8).setTextColor(Color.BLACK);
            status.get(9).setTextColor(Color.GRAY);
            status.get(10).setTextColor(Color.RED);
            status.get(11).setTextColor(Color.RED);
            mText.get(3).setTextColor(Color.GRAY);
            pic.setVisibility(View.VISIBLE);

            String url = "http://120.78.77.63:8023/Handler/GetImg.ashx?MemberGUID="+bean.getBillsGUID()+"&ImgType=21";
            Log.e("mieeisndetail",url);

            pic.setImageURI(Uri.parse(ToolsUtils.getString(MissionDetailActivity.this,Constant.HEADLOGO,"")));
            upload.setVisibility(View.VISIBLE);
            upload.setOnClickListener(onClickListener);
        } else if (bean.getStatus().equals("9")) {  //运单已完成
            mText.get(0).setText(STATUS8);
            mButton.get(0).setText(STATUS8);
            mButton.get(0).setEnabled(false);
            vertical.setImageResource(R.drawable.vertical_line_5);
            zcaddr.setText(bean.getFromDetailedAddress());
            status.get(5).setText(ToolsUtils.getString(MissionDetailActivity.this, Constant.ADDRESS, ""));
            status.get(7).setText(ToolsUtils.getString(MissionDetailActivity.this, Constant.ADDRESS, ""));
            status.get(9).setText(ToolsUtils.getString(MissionDetailActivity.this, Constant.ADDRESS, ""));
            status.get(10).setText("货物已签收,签收人:" + bean.getSignby());
            status.get(1).setText(ToolsUtils.getString(MissionDetailActivity.this, Constant.ADDRESS, ""));
            mText.get(9).setText(bean.getSigntime());
            status.get(0).setTextColor(Color.BLACK);
            status.get(1).setTextColor(Color.GRAY);
            status.get(2).setTextColor(Color.BLACK);
            status.get(3).setTextColor(Color.BLACK);
            status.get(4).setTextColor(Color.BLACK);
            status.get(5).setTextColor(Color.GRAY);
            status.get(6).setTextColor(Color.BLACK);
            status.get(7).setTextColor(Color.GRAY);
            status.get(8).setTextColor(Color.BLACK);
            status.get(9).setTextColor(Color.GRAY);
            status.get(10).setTextColor(Color.RED);
            status.get(11).setTextColor(Color.BLACK);
            status.get(12).setTextColor(Color.RED);
            status.get(13).setTextColor(Color.RED);
            mText.get(3).setTextColor(Color.GRAY);
            pic.setVisibility(View.VISIBLE);
            String url = "http://120.78.77.63:8023/Handler/GetImg.ashx?MemberGUID="+bean.getBillsGUID()+"&ImgType=21";
            Log.e("mieeisndetail",url);
            pic.setImageURI(Uri.parse(url));
        }else if (bean.getStatus().equals("-2")){
            mText.get(0).setText("运单已取消");
            mButton.get(0).setEnabled(false);
            selectCar.setBackgroundResource(R.drawable.button_enable_bg);
            selectCar.setEnabled(false);
            mButton.get(0).setBackgroundResource(R.drawable.button_enable_bg);
            phone.setEnabled(false);
        }
        if (usertype.equals("4")) {
           // Log.e("misson-driverguid",bean.getDriverGUID());
            if (TextUtils.isEmpty(bean.getDriverGUID())) {
                mButton.get(0).setText("选择运输司机");
            } else {
                mButton.get(0).setText("更换司机");
            }

        }
       /* }*/
        /*if (bean.getDriverGUID().equals("") && GetUserInfoUtils.getVcompany(MissionDetailActivity.this).equals("9"))
        {
            mButton.get(0).setText("选择运输司机");
        }else {

        }*/


    }

    private void change() {
        if (bean.getStatus().equals("0")) {
            bean.setStatus("1");
            bean.setDepartureTime(date);
            mButton.get(0).setText(BUTTONTXT2);
        } else if (bean.getStatus().equals("1")) {
            bean.setStatus("2");
            bean.setArrivalLoadingTime(date);
            mButton.get(0).setText(BUTTONTXT3);
        } else if (bean.getStatus().equals("2")) {
            bean.setStatus("3");
            bean.setLoadtime(date);
            mButton.get(0).setText(BUTTONTXT4);
        } else if (bean.getStatus().equals("3")) {
            bean.setStatus("4");
            bean.setArrivedtime(date);
            mButton.get(0).setText(STATUS4);
        } else if (bean.getStatus().equals("4")) {
            mButton.get(0).setText(STATUS5);
            bean.setSigntime(date);
            bean.setStatus("5");
            //mButton.get(0).setEnabled(false);
        } else if (bean.getStatus().equals("5")) {
            //bean.setSignby(name);
            mButton.get(0).setText(STATUS7);
            mButton.get(0).setEnabled(false);
        } else if (bean.getStatus().equals(9)) {
            mButton.get(0).setText(STATUS5);
            mButton.get(0).setEnabled(false);
        }
        onCreate(null);

    }

    private void changeStatus(String method) {
        Map<String, String> map = new HashMap<>();
        map.put("GUID", GetUserInfoUtils.getGuid(MissionDetailActivity.this));
        map.put(Constant.MOBILE, GetUserInfoUtils.getMobile(MissionDetailActivity.this));
        map.put(Constant.KEY, GetUserInfoUtils.getKey(MissionDetailActivity.this));
        map.put("billsGUID", bean.getBillsGUID());
        String addr = ToolsUtils.getString(MissionDetailActivity.this, Constant.ADDRESS, "");
        if (method.equals(Config.MISSION_STATUS_YUBAO)) {
            map.put("DeparturePlace", addr);
        } else if (method.equals(Config.MISSION_STATUS_ARRIVED)) {
            map.put("ArrivalLoadingPlace", addr);
        } else if (method.equals(Config.MISSION_STATUS_ZHIXING)) {
            map.put("loadadd", addr);
        } else if (method.equals(Config.MISSION_STATUS_XIEHUO)) {
            map.put("arrivedadd", addr);
        } else if (method.equals(Config.MISSION_STATUS_QIANSHOU)) {
            map.put("signadd", addr);
            map.put("signby", name);
            map.put("signPhone", mobile);
        }
        Log.e("missionDetail", JsonUtils.getInstance().getJsonStr(map));
        RetrofitUtils.getRetrofitService()
                .changeMissionStatus(Constant.MYINFO_PAGENAME, method, JsonUtils.getInstance().getJsonStr(map))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetCodeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetCodeBean getCodeBean) {
                        Log.e("missiondetail", getCodeBean.getErrorCode() + "--" + getCodeBean.getErrorMsg());
                        if (getCodeBean.getErrorCode().equals("200")) {
                            bean.setSignby(name);
                            change();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mission_detail_copy:
                ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                manager.setText(mText.get(8).getText().toString());
                ToolsUtils.getInstance().toastShowStr(MissionDetailActivity.this, "复制成功");
                break;
            case R.id.mission_detail_changestatus:
                if (GetUserInfoUtils.getUserType(MissionDetailActivity.this).equals("4")) {
                    if (TextUtils.isEmpty(bean.getTruckno())){
                        ToolsUtils.getInstance().toastShowStr(MissionDetailActivity.this,"您还没有调度车辆，请先调度车辆");
                    }else {
                        Intent intent = new Intent(MissionDetailActivity.this, SelectDriverActivity.class);
                        intent.putExtra("missionguid", bean.getBillsGUID());
                        intent.putExtra("flags", Constant.MISSIONFLAGS);
                        startActivityForResult(intent, REQUESTCODE_DRIVER);
                    }

                } else {
                    new CommomDialog(MissionDetailActivity.this, R.style.dialog, "您确定要执行" + infos + "这个操作吗?", new CommomDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            dialog.dismiss();
                            if (confirm) {
                                if (methodName.equals(Config.MISSION_STATUS_QIANSHOU)) {
                                    showEditDialog(MissionDetailActivity.this);
                                } else {
                                    changeStatus(methodName);
                                }

                            }
                        }
                    }).setTitle("提示").show();

                }
                break;
            case R.id.mission_detail_phone:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + bean.getOwnerphone()));
                startActivity(intent);

                break;
            case R.id.mission_detail_selectcar:
                Intent intent2 = new Intent(MissionDetailActivity.this,MyCarTeamActivity.class);
                intent2.putExtra("flag","missiondetail");
                intent2.putExtra("missionguid", bean.getBillsGUID());
                startActivityForResult(intent2,REQUESTCODE_CAR);
                break;
        }

    }

    public void showEditDialog(Activity view) {
        setSignByDialog = new SetSignByDialog(this, R.style.dialog, onClickListener);
        setSignByDialog.show();
    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btn_save_pop:

                    name = setSignByDialog.text_name.getText().toString().trim();
                    mobile = setSignByDialog.text_mobile.getText().toString().trim();
                    changeStatus(methodName);
                    setSignByDialog.dismiss();
                    break;
                case R.id.mission_status_upload:
                    uploadImage();
                    break;
            }
        }
    };


    /**
     * 上传头像
     */
    private void uploadImage() {
        View view = LayoutInflater.from(this).inflate(R.layout.upload_popwindow, null);
        TextView btnCarema = (TextView) view.findViewById(R.id.btn_camera);
        TextView btnPhoto = (TextView) view.findViewById(R.id.btn_photo);
        TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        popupWindow.setOutsideTouchable(true);
        View parent = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        //popupWindow在弹窗的时候背景半透明
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1.0f;
                getWindow().setAttributes(params);
            }
        });

        btnCarema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //权限判断
                if (ContextCompat.checkSelfPermission(MissionDetailActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(MissionDetailActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    //跳转到调用系统相机
                    gotoCamera();
                }
                popupWindow.dismiss();
            }
        });
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //权限判断
                if (ContextCompat.checkSelfPermission(MissionDetailActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请READ_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(MissionDetailActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            READ_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    //跳转到相册
                    gotoPhoto();
                }
                popupWindow.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    /**
     * 跳转到相册
     */
    private void gotoPhoto() {
        Log.d("evan", "*****************打开图库********************");
        //跳转到调用系统图库
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"), REQUEST_PICK);
    }


    /**
     * 跳转到照相机
     */
    private void gotoCamera() {
        Log.d("evan", "*****************打开相机********************");
        //创建拍照存储的图片文件
        tempFile = new File(ToolsUtils.getInstance().checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/image/"), System.currentTimeMillis() + ".jpg");

        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //设置7.0中共享文件，分享路径定义在xml/file_paths.xml
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(MissionDetailActivity.this, Constant.APPLICATION_ID + ".fileProvider", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, REQUEST_CAPTURE);
    }


    private BitmapFactory.Options getBitmapOption(int inSampleSize) {
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
    }


    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent intent) {
        switch (requestCode) {
            case REQUEST_CAPTURE: //调用系统相机返回
                if (resultCode == RESULT_OK) {

                    String path = ToolsUtils.getRealFilePath(MissionDetailActivity.this, Uri.fromFile(tempFile));
                    Bitmap bitmap = BitmapFactory.decodeFile(path, getBitmapOption(2));

                    pic.setImageBitmap(ToolsUtils.centerSquareScaleBitmap(bitmap, 250));


                    file = ToolsUtils.compressImage(BitmapFactory.decodeFile(path));

                    if (file.exists()) {
                        ThreadPoolHelp.Builder.cached().builder().execute(new Runnable() {
                            @Override
                            public void run() {
                                upload();
                            }
                        });
                    }

                }
                break;
            case REQUEST_PICK:  //调用系统相册返回
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    String picPath = ToolsUtils.getRealFilePath(MissionDetailActivity.this, uri);
                    Bitmap bitmap = BitmapFactory.decodeFile(picPath, getBitmapOption(2));

                    pic.setImageBitmap(ToolsUtils.centerSquareScaleBitmap(bitmap, 250));

                    file = ToolsUtils.compressImage(BitmapFactory.decodeFile(picPath));
                    if (file.exists()) {
                        ThreadPoolHelp.Builder.cached().builder().execute(new Runnable() {
                            @Override
                            public void run() {
                                upload();
                            }
                        });
                    }
                }
                break;
            case REQUESTCODE_DRIVER:

                final String name = TextUtils.isEmpty(intent.getStringExtra("drivername"))?" ":intent.getStringExtra("drivername");
                final String tel = TextUtils.isEmpty(intent.getStringExtra("drivertel"))?" ":intent.getStringExtra("drivertel");

                new CommomDialog(MissionDetailActivity.this, R.style.dialog, "您选择的司机为" + name, new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        dialog.dismiss();
                        if (confirm) {
                            mButton.get(0).setText(name);
                            info.get(3).setText(name);
                            info.get(4).setText(tel);
                        }
                    }
                }).setTitle("提示").show();

                break;
            case REQUESTCODE_CAR:
                String truckno = TextUtils.isEmpty(intent.getStringExtra("truckno"))?" ":intent.getStringExtra("truckno");
                bean.setTruckno(truckno);
                info.get(5).setText(truckno);
                break;

        }
    }

    /**
     * 图片上传的方法
     */
    private void upload() {

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("ImgType", "21");

        builder.addFormDataPart("MemberGUID", bean.getBillsGUID());
        builder.addFormDataPart("headimgurl", "avatar", RequestBody.create(MediaType.parse("image/png/jpg; charset=utf-8"), file));
        RetrofitUtils.getRetrofitService().
                upload_avatar(builder.build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        //ToolsUtils.getInstance().toastShowStr(MissionDetailActivity.this,"上传完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                       // ToolsUtils.getInstance().toastShowStr(MissionDetailActivity.this, e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {

                            String info = responseBody.string();
                            try {
                                JSONObject json = new JSONObject(info);
                                String str = json.get("errorMsg").toString();
                                ToolsUtils.getInstance().toastShowStr(MissionDetailActivity.this, str);

                            } catch (Exception e) {

                            }

                        } catch (IOException e) {

                        }

                    }
                });
    }
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private AMapLocationClient mLocationClient;

    private void initLocation() {

        mLocationClient = new AMapLocationClient(this.getApplicationContext());
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setInterval(10000);
        //给定位客户端对象设置定位参数mLocationOption.setHttpTimeOut(20000);
        mLocationClient.setLocationOption(mLocationOption);


        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                String location = amapLocation.getLatitude()+","+amapLocation.getLongitude();
                setNewLoad(location);
                ToolsUtils.putString(MissionDetailActivity.this, Constant.CITY, amapLocation.getCity());
                ToolsUtils.putString(MissionDetailActivity.this, Constant.ADDRESS, amapLocation.getAddress());
                //ToolsUtils.getInstance().toastShowStr(SplashActivity.this,amapLocation.getErrorInfo());
            }
        });
        //启动定位
        mLocationClient.startLocation();
    }
    @Override
    protected void onDestroy() {
        //this.unbindService(conn);
        super.onDestroy();
        if (usertype.equals("3")||usertype.equals("2")){
            mLocationClient.stopLocation();
        }
    }
    private void setNewLoad(String load)
    {
        Map<String,String> map = new HashMap<>();
        map.put("GUID",GetUserInfoUtils.getGuid(MissionDetailActivity.this));
        map.put(Constant.MOBILE,GetUserInfoUtils.getMobile(MissionDetailActivity.this));
        map.put(Constant.KEY,GetUserInfoUtils.getKey(MissionDetailActivity.this));
        map.put("billsGUID",bean.getBillsGUID());
        map.put("Newload",load);
        RetrofitUtils.getRetrofitService()
                .setBillNewload(Constant.MYINFO_PAGENAME,Config.SETBILLNEWLOAD,JsonUtils.getInstance().getJsonStr(map))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetCodeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetCodeBean getCodeBean) {
                        Log.e("mission-newload",getCodeBean.getErrorCode()+"--"+getCodeBean.getErrorMsg());
                    }
                });
    }
}
