package com.app.gaolonglong.fragmenttabhost.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.CarinfoBean;
import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.GetUserInfoUtils;
import com.app.gaolonglong.fragmenttabhost.utils.JsonUtils;
import com.app.gaolonglong.fragmenttabhost.utils.LoadingDialog;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ThreadManager;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.app.gaolonglong.fragmenttabhost.view.MyGridView;
import com.luoxudong.app.threadpool.ThreadPoolHelp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
 * Created by yanqi on 2017/8/28.
 */

public class PersonalRenzheng2Activity extends BaseActivity implements View.OnClickListener {

    private LoadingDialog dialog;

    private int position = 0;

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

    private String guid;

    private String mobile;

    private String key;

    @BindViews({R.id.top_title})
    public List<TextView> mText;

    @BindView(R.id.personal2_parent)
    public LinearLayout parent;

    @BindViews({R.id.person2_card_pic, R.id.person2_with_card,
            R.id.person2_car_face, R.id.person2_car_back,
            R.id.person2_car_content, R.id.person2_zbg_ce, R.id.person2_zbg_back})
    public List<ImageView> mImage;

    @BindViews({R.id.person2_carnum, R.id.person2_xsnum,
            R.id.person2_time, R.id.person2_cartype,
            R.id.person2_carlong})
    public List<EditText> mEdit;

    @BindViews({R.id.carrenzheng_info, R.id.carrenzheng_zbg_info})
    public List<LinearLayout> mLinear;

    @BindViews({R.id.person2_zbh, R.id.person2_carweight, R.id.person2_zaizhong,
            R.id.person2_rongji, R.id.carrenzheng_kuan, R.id.carrenzheng_gao,
    })
    public List<EditText> mCarinfo;

    @BindViews({R.id.carrc_common_zbgcode, R.id.carrc_common_hgcode, R.id.carrc_common_zbgnum,
            R.id.carrc_common_zaizhong, R.id.carrenzheng_common_headweight,
            R.id.carrenzheng_common_guiweight, R.id.carrenzheng_common_jiaweight})
    public List<EditText> mZbginfo;

    @BindViews({R.id.person2_zbgce_ll, R.id.person2_zbgback_ll})
    public List<LinearLayout> mZbgimg;

    private void setView(CarinfoBean carinfoBean) {
        CarinfoBean.DataBean data = carinfoBean.getData().get(0);
        String trucktype = TextUtils.isEmpty(data.getTrucktype()) ? "" : data.getTrucktype();
        String trucklength = TextUtils.isEmpty(data.getTrucklength()) ? "" : data.getTrucklength();
        String truckno = TextUtils.isEmpty(data.getTruckno()) ? "" : data.getTruckno();
        String customnum = TextUtils.isEmpty(data.getCustomNumber()) ? "" : data.getCustomNumber();//自编号
        String truckweight = TextUtils.isEmpty(data.getTruckWeight()) ? "" : data.getTruckWeight();//车重
        String MaximumLoad = TextUtils.isEmpty(data.getMaximumLoad()) ? "" : data.getMaximumLoad();//最大载重
        String Volume = TextUtils.isEmpty(data.getVolume()) ? "" : data.getVolume();//容积
        String newguid = TextUtils.isEmpty(ToolsUtils.getString(PersonalRenzheng2Activity.this,"newguid",""))?"":ToolsUtils.getString(PersonalRenzheng2Activity.this,"newguid","");


        mEdit.get(0).setText(truckno);
        mEdit.get(3).setText(trucktype);
        mEdit.get(4).setText(trucklength);
        mCarinfo.get(0).setText(customnum);
        mCarinfo.get(1).setText(truckweight);
        mCarinfo.get(3).setText(Volume);
        mCarinfo.get(3).setText(Volume);

        if (data.getTrucktype().equals("自备柜")) {
            mLinear.get(1).setVisibility(View.VISIBLE);
            ;
            mLinear.get(0).setVisibility(View.GONE);
            ;
            String PlateNumber = TextUtils.isEmpty(data.getPlateNumber()) ? "" : data.getPlateNumber();//自备柜车牌号
            String CustomsCode = TextUtils.isEmpty(data.getCustomNumber()) ? "" : data.getCustomsCode();//海关编码
            String CabinetNo = TextUtils.isEmpty(data.getCabinetNo()) ? "" : data.getCabinetNo();//柜号
            String HeadWeight = TextUtils.isEmpty(data.getHeadWeight()) ? "" : data.getHeadWeight();//自备柜头重
            String CabinetWeight = TextUtils.isEmpty(data.getCabinetWeight()) ? "" : data.getCabinetWeight();//自备柜柜重
            String FrameWeight = TextUtils.isEmpty(data.getFrameWeight()) ? "" : data.getFrameWeight();//自备柜架重
            mZbginfo.get(0).setText(PlateNumber);
            mZbginfo.get(1).setText(CustomsCode);
            mZbginfo.get(2).setText(CabinetNo);
            mZbginfo.get(4).setText(HeadWeight);
            mZbginfo.get(5).setText(CabinetWeight);
            mZbginfo.get(6).setText(FrameWeight);
            mZbginfo.get(3).setText(MaximumLoad);
            getHttpBitmap(GetUserInfoUtils.getImg(newguid,"17"),mImage.get(3),5);
            getHttpBitmap(GetUserInfoUtils.getImg(newguid,"18"),mImage.get(4),6);
        } else {
            String TruckWidth = TextUtils.isEmpty(data.getTruckWidth()) ? "" : data.getTruckWidth();//车宽
            String TruckHeight = TextUtils.isEmpty(data.getTruckHeight()) ? "" : data.getTruckHeight();//车宽
            mCarinfo.get(4).setText(TruckWidth);
            mCarinfo.get(5).setText(TruckHeight);
            mCarinfo.get(2).setText(MaximumLoad);
            getHttpBitmap(GetUserInfoUtils.getImg(newguid,"10"),mImage.get(0),0);
            getHttpBitmap(GetUserInfoUtils.getImg(newguid,"16"),mImage.get(1),1);
            getHttpBitmap(GetUserInfoUtils.getImg(newguid,"5"),mImage.get(2),2);
            getHttpBitmap(GetUserInfoUtils.getImg(newguid,"6"),mImage.get(3),3);
            getHttpBitmap(GetUserInfoUtils.getImg(newguid,"7"),mImage.get(4),4);
        }


    }


    private View popView;
    private PopupWindow typePopmenu;
    private WindowManager.LayoutParams param;
    private String newguid;

    @OnClick(R.id.title_back)
    public void back() {
        finish();
    }

    @OnClick(R.id.person2_next)
    public void next() {
        dialog.show();
        Log.e("personjson", initJsonData());
        toNext(initJsonData());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_renzheng_two);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initView();
        initCartypePopwindow();
    }

    private void initView() {
        mText.get(0).setText("个体司机认证");
        mImage.get(0).setOnClickListener(this);
        mImage.get(1).setOnClickListener(this);
        mImage.get(2).setOnClickListener(this);
        mImage.get(3).setOnClickListener(this);
        mImage.get(4).setOnClickListener(this);
        mImage.get(5).setOnClickListener(this);
        mImage.get(6).setOnClickListener(this);
        mEdit.get(3).setOnClickListener(this);
        mEdit.get(4).setOnClickListener(this);

        dialog = LoadingDialog.showDialog(PersonalRenzheng2Activity.this);
        newguid = ToolsUtils.getString(PersonalRenzheng2Activity.this, "newguid", "");
        if (TextUtils.isEmpty(newguid)){
            getGuid();
        }
        Log.e("personnalguid", newguid);
        guid = ToolsUtils.getString(PersonalRenzheng2Activity.this, Constant.LOGIN_GUID, "");
        mobile = ToolsUtils.getString(PersonalRenzheng2Activity.this, Constant.MOBILE, "");
        key = ToolsUtils.getString(PersonalRenzheng2Activity.this, Constant.KEY, "");
        getCarInfo();
    }

    private String initJsonData() {
        String zbh = mCarinfo.get(0).getText() + ""; //自编号
        String carweight = mCarinfo.get(1).getText() + "吨"; //车重
        String zaizhong = mCarinfo.get(2).getText() + "吨"; //载重
        String rongji = mCarinfo.get(3).getText() + "方"; //容积
        //String chang = mCarinfo.get(4).getText()+"米"; //长
        String kuan = mCarinfo.get(4).getText() + "米"; // 宽
        String gao = mCarinfo.get(5).getText() + "米"; //高
        String zbgcode = mZbginfo.get(0).getText() + ""; //自备柜车牌号
        String hgcode = mZbginfo.get(1).getText() + "";//海关编码
        String zbgnum = mZbginfo.get(2).getText() + "";//柜号
        String zbgzaizhong = mZbginfo.get(3).getText() + "吨";//自备柜载重
        String headweight = mZbginfo.get(4).getText() + "吨";//自备柜头重
        String guiweight = mZbginfo.get(5).getText() + "吨";//自备柜柜重
        String jiaweight = mZbginfo.get(6).getText() + "吨";//自备柜架重
        Map<String, String> map = new HashMap<>();
        map.put("GUID", guid);
        map.put(Constant.KEY, key);
        map.put(Constant.MOBILE, mobile);
        map.put("truckno", mEdit.get(0).getText().toString());
        map.put("trucklicence", mEdit.get(1).getText().toString());
        map.put("trucktype", mEdit.get(3).getText().toString());
        map.put("trucklength", mEdit.get(4).getText().toString());
        map.put("TrucksGUID", ToolsUtils.getString(PersonalRenzheng2Activity.this, "newguid", ""));
        map.put("CustomNumber", zbh);
        map.put("TruckWeight", carweight);
        map.put("MaximumLoad", zaizhong);
        map.put("Volume", rongji);
        map.put("TruckWidth", kuan);
        map.put("TruckHeight", gao);
        map.put("PlateNumber", zbgcode);
        map.put("CustomsCode", hgcode);
        map.put("CabinetNo", zbgnum);
        map.put("HeadWeight", headweight);
        map.put("CabinetWeight", guiweight);
        map.put("FrameWeight", jiaweight);
        //map.put("boardingtime", mEdit.get(2).getText().toString());

        return JsonUtils.getInstance().getJsonStr(map);
    }

    private void toNext(String json) {

        RetrofitUtils.getRetrofitService()
                .setCarsInfo("YZ", Config.BIND_TRUCK, json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetCodeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                        //Log.e("personerror",e.getMessage());
                        ToolsUtils.getInstance().toastShowStr(PersonalRenzheng2Activity.this, e.getMessage());
                    }

                    @Override
                    public void onNext(GetCodeBean getCodeBean) {
                        dialog.dismiss();
                        if (getCodeBean.getErrorCode().equals("200")) {
                            startActivity(new Intent(PersonalRenzheng2Activity.this, CommitSuccessActivity.class));
                            finish();
                        }
                    }
                });

    }


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
                if (ContextCompat.checkSelfPermission(PersonalRenzheng2Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(PersonalRenzheng2Activity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
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
                if (ContextCompat.checkSelfPermission(PersonalRenzheng2Activity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请READ_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(PersonalRenzheng2Activity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
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
            Uri contentUri = FileProvider.getUriForFile(PersonalRenzheng2Activity.this, Constant.APPLICATION_ID + ".fileProvider", tempFile);
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

                    String path = ToolsUtils.getRealFilePath(PersonalRenzheng2Activity.this, Uri.fromFile(tempFile));
                    Bitmap bitmap = BitmapFactory.decodeFile(path, getBitmapOption(2));
                    if (position != 0) {
                        mImage.get(position).setImageBitmap(ToolsUtils.centerSquareScaleBitmap(bitmap, 250));
                    } else {
                        mImage.get(position).setImageBitmap(ToolsUtils.centerSquareScaleBitmap(bitmap, 120));
                    }


                    file = ToolsUtils.compressImage(BitmapFactory.decodeFile(path));

                    if (file.exists()) {
                        ThreadPoolHelp.Builder.cached().builder().execute(new Runnable() {
                            @Override
                            public void run() {

                                upload(ToolsUtils.getString(PersonalRenzheng2Activity.this, "newguid", ""));
                            }
                        });
                    }

                }
                break;
            case REQUEST_PICK:  //调用系统相册返回
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    String picPath = ToolsUtils.getRealFilePath(PersonalRenzheng2Activity.this, uri);
                    Bitmap bitmap = BitmapFactory.decodeFile(picPath, getBitmapOption(2));
                    if (position != 0) {
                        mImage.get(position).setImageBitmap(ToolsUtils.centerSquareScaleBitmap(bitmap, 250));
                    } else {
                        mImage.get(position).setImageBitmap(ToolsUtils.centerSquareScaleBitmap(bitmap, 120));
                    }
                    file = ToolsUtils.compressImage(BitmapFactory.decodeFile(picPath));
                    if (file.exists()) {
                        ThreadPoolHelp.Builder.cached().builder().execute(new Runnable() {
                            @Override
                            public void run() {

                                upload(ToolsUtils.getString(PersonalRenzheng2Activity.this, "newguid", ""));
                            }
                        });

                    }
                }
                break;

        }
    }

    private void getGuid() {
        ThreadPoolHelp.Builder.cached().builder().execute(new Runnable() {
            @Override
            public void run() {
                RetrofitUtils.getRetrofitService()
                        .getGuid(Constant.PARAMETER_PAGENAME, Config.GETNEWGUID)
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
                                String guid = getCodeBean.getErrorMsg();
                                ToolsUtils.putString(PersonalRenzheng2Activity.this, "newguid", guid);
                                //upload(guid);
                            }
                        });
            }
        });
    }

    private void upload(String s) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("MemberGUID", s);
        builder.addFormDataPart("headimgurl", "avatar", RequestBody.create(MediaType.parse("image/png/jpg; charset=utf-8"), file));
        if (position == 0) {
            builder.addFormDataPart("ImgType", "10"); //行驶证
        } else if (position == 1) {
            builder.addFormDataPart("ImgType", "16");//营运证
        } else if (position == 2) {
            builder.addFormDataPart("ImgType", "5");
        } else if (position == 3) {
            builder.addFormDataPart("ImgType", "6");
        } else if (position == 4) {
            builder.addFormDataPart("ImgType", "7");
        } else if (position == 5) {
            builder.addFormDataPart("ImgType", "17");//柜体侧面
        } else if (position == 6) {
            builder.addFormDataPart("ImgType", "18");//柜体后面
        }
        RetrofitUtils.getRetrofitService().
                upload_avatar(builder.build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        //ToolsUtils.getInstance().toastShowStr(PersonalRenzheng2Activity.this,"上传完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        // ToolsUtils.getInstance().toastShowStr(PersonalRenzheng2Activity.this, e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {

                            String info = responseBody.string();
                            try {
                                JSONObject json = new JSONObject(info);
                                String str = json.get("errorMsg").toString();
                                ToolsUtils.getInstance().toastShowStr(PersonalRenzheng2Activity.this, str);
                            } catch (Exception e) {

                            }

                        } catch (IOException e) {

                        }

                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.person2_card_pic:
                position = 0;
                uploadImage();
                break;
            case R.id.person2_with_card:
                position = 1;
                uploadImage();
                break;
            case R.id.person2_car_face:
                position = 2;
                uploadImage();
                break;
            case R.id.person2_car_back:
                position = 3;
                uploadImage();
                break;
            case R.id.person2_car_content:
                position = 4;
                uploadImage();
                break;
            case R.id.person2_zbg_ce:
                position = 5;
                uploadImage();
                break;
            case R.id.person2_zbg_back:
                position = 6;
                uploadImage();
                break;
            case R.id.person2_cartype:
                showCarPop();
                break;
            case R.id.person2_carlong:
                showCarPop();
                break;
        }
    }

    private void initCartypePopwindow() {
        popView = getLayoutInflater().inflate(R.layout.find_cartype_gridview, null);
        typePopmenu = new PopupWindow(popView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        typePopmenu.setOutsideTouchable(true);
        typePopmenu.setBackgroundDrawable(dw);
        typePopmenu.setFocusable(true);
        typePopmenu.setTouchable(true);
        typePopmenu.setAnimationStyle(R.style.mypopwindow_anim_style);
    }

    String lenStr = "";
    String typeStr = "";

    private void showCarPop() {

        List<Map<String, String>> typeList = new ArrayList<Map<String, String>>();
        List<Map<String, String>> lengthList = new ArrayList<Map<String, String>>();
        String[] length = {"4.2米", "4.5米", "5米", "5.2米", "6.2米", "6.8米",
                "7.2米", "11.7米", "12.5米", "13米", "13.5米", "14米", "15米", "16米", "17米"};

        final String[] type = {"冷藏车", "平板", "高栏", "箱式", "保温", "危险品", "高低板", "自备柜"};

        for (int j = 0; j < type.length; j++) {
            Map<String, String> maps = new HashMap<String, String>();
            maps.put("type", type[j]);
            typeList.add(maps);
        }

        for (int i = 0; i < length.length; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("length", length[i]);
            lengthList.add(map);
        }
        final MyGridView lenthGrid = (MyGridView) popView.findViewById(R.id.gridview);
        SimpleAdapter lenthAdapter = new SimpleAdapter(PersonalRenzheng2Activity.this, lengthList, R.layout.find_cartype_pop_item, new String[]{"length"},
                new int[]{R.id.gv_item_text});
        lenthGrid.setAdapter(lenthAdapter);

        final MyGridView typeGrid = (MyGridView) popView.findViewById(R.id.gridview_2);
        SimpleAdapter typeAdapter = new SimpleAdapter(PersonalRenzheng2Activity.this, typeList, R.layout.find_cartype_pop_item, new String[]{"type"},
                new int[]{R.id.gv_item_text});
        typeGrid.setAdapter(typeAdapter);

        typePopmenu.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        param = getWindow().getAttributes();
        param.alpha = 0.7f;
        getWindow().setAttributes(param);
        typePopmenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                param = getWindow().getAttributes();
                param.alpha = 1f;
                getWindow().setAttributes(param);
            }
        });
        lenthGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CharSequence len = ((TextView) lenthGrid.getChildAt(i).findViewById(R.id.gv_item_text)).getText();
                lenStr = len.toString();
                for (int m = 0; m < adapterView.getCount(); m++) {
                    TextView item = (TextView) lenthGrid.getChildAt(m).findViewById(R.id.gv_item_text);

                    if (i == m) {//当前选中的Item改变背景颜色
                        item.setBackgroundResource(R.drawable.cartype_unselect);
                    } else {
                        item.setBackgroundResource(R.drawable.cartype_select);
                    }
                }
            }
        });
        typeGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CharSequence type = ((TextView) typeGrid.getChildAt(i).findViewById(R.id.gv_item_text)).getText();
                typeStr = type.toString();
                for (int m = 0; m < adapterView.getCount(); m++) {
                    TextView item = (TextView) typeGrid.getChildAt(m).findViewById(R.id.gv_item_text);
                    if (i == m) {//当前选中的Item改变背景颜色
                        item.setBackgroundResource(R.drawable.cartype_unselect);
                    } else {
                        item.setBackgroundResource(R.drawable.cartype_select);
                    }
                }
            }
        });
        TextView sure = (TextView) popView.findViewById(R.id.cartype_grid_sure);
        TextView noLimit = (TextView) popView.findViewById(R.id.cartype_grid_nocartype);

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //.get(2).setText(lenStr+"/"+typeStr);
                if (!typeStr.equals("")) {
                    mEdit.get(3).setText("");
                    mEdit.get(3).setText(typeStr);
                }
                if (!lenStr.equals("")) {
                    mEdit.get(4).setText("");
                    mEdit.get(4).setText(lenStr);
                }
                if (typeStr.equals("自备柜"))//选择的是自备柜就显示填写自备柜信息的条目
                {
                    mZbgimg.get(0).setVisibility(View.VISIBLE);
                    mZbgimg.get(1).setVisibility(View.VISIBLE);
                    mLinear.get(0).setVisibility(View.GONE);
                    mLinear.get(1).setVisibility(View.VISIBLE);
                } else {
                    mZbgimg.get(0).setVisibility(View.GONE);
                    mZbgimg.get(1).setVisibility(View.GONE);
                    mLinear.get(0).setVisibility(View.VISIBLE);
                    mLinear.get(1).setVisibility(View.GONE);
                }
                typePopmenu.dismiss();
            }
        });
        noLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void getCarInfo() {
        final Map<String, String> map = new HashMap<>();
        map.put("GUID", guid);
        map.put(Constant.MOBILE, mobile);
        map.put(Constant.KEY, key);
        ThreadManager.getNormalPool().execute(new Runnable() {
            @Override
            public void run() {
                RetrofitUtils.getRetrofitService()
                        .getCarInfo(Constant.TRUCK_PAGENAME, Config.GETCARINFO, JsonUtils.getInstance().getJsonStr(map))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<CarinfoBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(final CarinfoBean carinfoBean) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.e("personnal-carinfo",carinfoBean.getErrorCode()+"--"+carinfoBean.getErrorMsg());
                                        if (carinfoBean.getErrorCode().equals("200")){
                                            setView(carinfoBean);
                                        }
                                    }
                                });

                            }
                        });
            }
        });
    }
    /**
     * 从服务器取图片
     *http://bbs.3gstdy.com
     * @param url
     * @return
     */
    public void getHttpBitmap(final String url, final ImageView iv, final int position) {

        ThreadManager.getNormalPool().execute(new Runnable() {
            @Override
            public void run() {
                URL myFileUrl = null;
                Bitmap bitmap = null;
                try {
                    //Log.d(TAG, url);
                    myFileUrl = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                    conn.setConnectTimeout(0);
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                    final Bitmap finalBitmap = bitmap;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (finalBitmap.getByteCount() != 0){
                                if (position == 0){
                                    iv.setImageBitmap(ToolsUtils.zoomImg(finalBitmap,200,200));
                                }else if (position == 1){
                                    iv.setImageBitmap(ToolsUtils.zoomImg(finalBitmap,200,200));
                                }else if (position == 2){
                                    iv.setImageBitmap(ToolsUtils.zoomImg(finalBitmap,200,200));
                                }else if (position == 3){
                                    iv.setImageBitmap(ToolsUtils.zoomImg(finalBitmap,200,200));
                                }else if (position == 4){
                                    iv.setImageBitmap(ToolsUtils.zoomImg(finalBitmap,200,200));
                                }
                            }else {
                                iv.setImageResource(R.mipmap.pic);
                            }


                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        //return bitmap;
    }

}
