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
import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.GetUserInfoUtils;
import com.app.gaolonglong.fragmenttabhost.utils.JsonUtils;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.app.gaolonglong.fragmenttabhost.view.MyGridView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.luoxudong.app.threadpool.ThreadPoolHelp;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
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
 * Created by yanqi on 2017/10/3.
 */

public class AddCarActivity extends BaseActivity implements View.OnClickListener{
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

    private String NEWGUID = "addcar_newguid";

    private int position = 0;

    @BindViews({R.id.add_car_carnum,R.id.add_car_xsznum,R.id.add_car_time,R.id.add_car_cartype,
                R.id.add_car_carlength})
    public List<EditText> mEdit;

    @BindViews({R.id.carrenzheng_zbh,R.id.carrenzheng_carweight,R.id.carrz_zaizhong,
                R.id.carrz_rongji,R.id.carrenzheng_kuan,R.id.carrenzheng_gao,
                })
    public List<EditText> mCarinfo;

    @BindViews({R.id.carrc_common_zbgcode,R.id.carrc_common_hgcode,R.id.carrc_common_zbgnum,
                R.id.carrc_common_zaizhong,R.id.carrenzheng_common_headweight,
                R.id.carrenzheng_common_guiweight,R.id.carrenzheng_common_jiaweight})
    public List<EditText> mZbginfo;

    private File tempFile;
    private String truckguid;
    private File file;
    private File file1;
    private String flag;
    private View popView;
    private PopupWindow typePopmenu;
    private WindowManager.LayoutParams param;

    @OnClick(R.id.title_back_txt)
    public void back()
    {
        finish();
    }
    @OnClick(R.id.title_back)
    public void backs()
    {
        finish();
    }
    @BindView(R.id.top_title)
    public TextView title;

    @BindViews({R.id.addcar_card_pic,R.id.addcar_with_card,R.id.addcar_car_face,R.id.addcar_car_back,
                R.id.addcar_car_content,R.id.addcar_zbg_ce,R.id.addcar_zbg_back})
    public List<SimpleDraweeView> mImg;

    @BindViews({R.id.add_car_zbgce_ll,R.id.add_car_zbgback_ll})
    public List<LinearLayout> mZbg;

    @BindView(R.id.add_car_carinfo)
    public LinearLayout carinfo;

    @BindView(R.id.carrenzheng_zbg_info)
    public LinearLayout zbgInfo;

    @BindView(R.id.add_car_parent)
    public LinearLayout parent;

    @OnClick(R.id.addcar_queren)
    public void querens()
    {
        if (flag.equals("carinfo")){
            updateTruck(initJsonData());
        }else {
            add(initJsonData());
        }

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_car);
        ButterKnife.bind(this);
        init();
    }
    private void init()
    {
        initView();
        initCartypePopwindow();
    }
    private void initView()
    {
        title.setText("添加车辆");
        getGuid();
        flag = getIntent().getStringExtra("flag")+"";
        if (flag.equals("carinfo")){
            title.setText("修改车辆");
            truckguid = getIntent().getStringExtra("truckguid");
            mEdit.get(0).setText(getIntent().getStringExtra("carno"));
            mEdit.get(1).setText(getIntent().getStringExtra("carxsz"));
            mEdit.get(3).setText(getIntent().getStringExtra("cartype"));
            mEdit.get(4).setText(getIntent().getStringExtra("carlength"));
            ThreadPoolHelp.Builder.cached().builder().execute(new Runnable() {
                @Override
                public void run() {
                    mImg.get(0).setImageURI(Uri.parse(GetUserInfoUtils.getImg(truckguid,"4")));
                    mImg.get(1).setImageURI(Uri.parse(GetUserInfoUtils.getImg(truckguid,"14")));
                    mImg.get(2).setImageURI(Uri.parse(GetUserInfoUtils.getImg(truckguid,"6")));
                    mImg.get(3).setImageURI(Uri.parse(GetUserInfoUtils.getImg(truckguid,"7")));
                }
            });
           // mImg.get(0).setImageURI(Uri.parse(GetUserInfoUtils.getImg(truckguid,"4")));
        }
        mImg.get(0).setOnClickListener(this);
        mImg.get(1).setOnClickListener(this);
        mImg.get(2).setOnClickListener(this);
        mImg.get(3).setOnClickListener(this);
        mImg.get(4).setOnClickListener(this);
        mImg.get(5).setOnClickListener(this);
        mImg.get(6).setOnClickListener(this);

        mEdit.get(3).setOnClickListener(this);
        mEdit.get(4).setOnClickListener(this);
    }

    private String  initJsonData()
    {
        Map<String, String> map = new HashMap<String,String>();
        String guid = GetUserInfoUtils.getGuid(AddCarActivity.this);
        String mobile = GetUserInfoUtils.getMobile(AddCarActivity.this);
        String key = GetUserInfoUtils.getKey(AddCarActivity.this);
        String carnum = mEdit.get(0).getText()+"";
        //String xsznum = mEdit.get(1).getText()+"";
        String cartype = mEdit.get(3).getText()+"";
        String carlenght = mEdit.get(4).getText()+"";
        String zbh = mCarinfo.get(0).getText()+""; //自编号
        String carweight = mCarinfo.get(1).getText()+""; //车重
        String zaizhong = mCarinfo.get(2).getText()+""; //载重
        String rongji = mCarinfo.get(3).getText()+""; //容积
        //String chang = mCarinfo.get(4).getText()+""; //长
        String kuan = mCarinfo.get(4).getText()+""; // 宽
        String gao = mCarinfo.get(5).getText()+""; //高
        String zbgcode = mZbginfo.get(0).getText()+""; //自备柜车牌号
        String hgcode = mZbginfo.get(1).getText()+"";//海关编码
        String zbgnum = mZbginfo.get(2).getText()+"";//柜号
        String zbgzaizhong = mZbginfo.get(3).getText()+"";//自备柜载重
        String headweight = mZbginfo.get(4).getText()+"";//自备柜头重
        String guiweight = mZbginfo.get(5).getText()+"";//自备柜柜重
        String jiaweight = mZbginfo.get(6).getText()+"";//自备柜架重
        String companyguid = GetUserInfoUtils.getCompanyGuid(AddCarActivity.this);
        if (TextUtils.isEmpty(carnum) || TextUtils.isEmpty(cartype))
        {
            ToolsUtils.getInstance().toastShowStr(AddCarActivity.this,"请填写完整的信息");
        }else {

            map.put("GUID",guid);
            map.put(Constant.MOBILE,mobile);
            map.put(Constant.KEY,key);
            map.put("truckno",carnum);
            map.put("trucktype",cartype);
            map.put("trucklength",carlenght);
            map.put("boardingtime","");
            map.put("trucklicence","");
            map.put("companyGUID",companyguid);
            map.put("company","");
            map.put("driverGUID","");
            map.put("drivername","");
            map.put("CustomNumber",zbh);
            map.put("TruckWeight",carweight);
            map.put("MaximumLoad",zaizhong);
            map.put("Volume",rongji);
            map.put("TruckWidth",kuan);
            map.put("TruckHeight",gao);
            map.put("PlateNumber",zbgcode);
            map.put("CustomsCode",hgcode);
            map.put("CabinetNo",zbgnum);
            map.put("HeadWeight",headweight);
            map.put("CabinetWeight",guiweight);
            map.put("FrameWeight",jiaweight);
            if (flag.equals("carinfo")){
                map.put("TrucksGUID",truckguid);
            }else {
                map.put("TrucksGUID",ToolsUtils.getString(AddCarActivity.this,NEWGUID,""));
            }
        }

        return JsonUtils.getInstance().getJsonStr(map);
    }

    private void add(String json)
    {
        RetrofitUtils.getRetrofitService()
                .addCars(Constant.TRUCK_PAGENAME, Config.ADDTRUCK,json)
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
                        //Log.e("updateTruck",getCodeBean.getErrorMsg());
                        ToolsUtils.getInstance().toastShowStr(AddCarActivity.this,getCodeBean.getErrorMsg());
                        startActivity(new Intent(AddCarActivity.this,MyCarTeamActivity.class));
                        finish();
                    }
                });
    }
    private void updateTruck(String json){
        RetrofitUtils.getRetrofitService()
                .setCarsInfo("YZ",Config.BIND_TRUCK,json)
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
                        Log.e("personinfo",getCodeBean.getErrorCode());
                        if (getCodeBean.getErrorCode().equals("200"))
                        {
                            startActivity(new Intent(AddCarActivity.this,CommitSuccessActivity.class));
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.addcar_card_pic:
                position = 0;
                uploadImage();
                break;
            case R.id.addcar_with_card:
                position = 1;
                uploadImage();
                break;
            case R.id.addcar_car_face:
                position = 2;
                uploadImage();
                break;
            case R.id.addcar_car_back:
                position = 3;
                uploadImage();
                break;
            case R.id.addcar_car_content:
                position = 4;
                uploadImage();
                break;
            case R.id.addcar_zbg_ce:
                position = 5;
                uploadImage();
                break;
            case R.id.addcar_zbg_back:
                position = 6;
                uploadImage();
                break;
            case R.id.add_car_cartype:
                showCarPop();
                break;
            case R.id.add_car_carlength:
                showCarPop();
                break;
        }
    }

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
                if (ContextCompat.checkSelfPermission(AddCarActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(AddCarActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
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
                if (ContextCompat.checkSelfPermission(AddCarActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请READ_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(AddCarActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
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
            Uri contentUri = FileProvider.getUriForFile(AddCarActivity.this, Constant.APPLICATION_ID + ".fileProvider", tempFile);
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

                    String path = ToolsUtils.getRealFilePath(AddCarActivity.this, Uri.fromFile(tempFile));
                    Bitmap bitmap = BitmapFactory.decodeFile(path, getBitmapOption(2));
                    mImg.get(position).setImageBitmap(ToolsUtils.centerSquareScaleBitmap(bitmap, 250));


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
                    String picPath = ToolsUtils.getRealFilePath(AddCarActivity.this, uri);
                    Bitmap bitmap = BitmapFactory.decodeFile(picPath, getBitmapOption(2));
                    mImg.get(position).setImageBitmap(ToolsUtils.centerSquareScaleBitmap(bitmap, 250));

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

        }
    }

    private void upload() {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (flag.equals("carinfo")){
            builder.addFormDataPart("MemberGUID", truckguid);
        }else {
            builder.addFormDataPart("MemberGUID", ToolsUtils.getString(AddCarActivity.this,NEWGUID,""));
        }

        builder.addFormDataPart("headimgurl", "avatar", RequestBody.create(MediaType.parse("image/png/jpg; charset=utf-8"), file));
        if(position == 0)
        {
            builder.addFormDataPart("ImgType", "10");
        }else if(position == 1)
        {
            builder.addFormDataPart("ImgType", "16");//营运证
        }else if(position == 2)
        {
            builder.addFormDataPart("ImgType", "5");
        }else if (position == 3)
        {
            builder.addFormDataPart("ImgType", "6");
        }else if (position == 4)
        {
            builder.addFormDataPart("ImgType", "7");
        }else if (position == 5)
        {
            builder.addFormDataPart("ImgType", "17");//柜体侧面
        }else if (position == 6)
        {
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
                                ToolsUtils.getInstance().toastShowStr(AddCarActivity.this, str);
                            } catch (Exception e) {

                            }

                        } catch (IOException e) {

                        }

                    }
                });
    }

    private void getGuid()
    {
        ThreadPoolHelp.Builder.cached().builder().execute(new Runnable() {
            @Override
            public void run() {
                RetrofitUtils.getRetrofitService()
                        .getGuid(Constant.PARAMETER_PAGENAME,Config.GETNEWGUID)
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
                                ToolsUtils.putString(AddCarActivity.this,NEWGUID,guid);
                                // upload(guid);
                            }
                        });
            }
        });
    }
    private void initCartypePopwindow()
    {
        popView = getLayoutInflater().inflate(R.layout.find_cartype_gridview,null);
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

    String lenStr = null;
    String typeStr = null;
    private void showCarPop()
    {

        List<Map<String,String>> typeList = new ArrayList<Map<String,String>>();
        List<Map<String,String>> lengthList = new ArrayList<Map<String,String>>();
        String[] length = { "不限", "4.2米", "4.5米", "5米", "5.2米", "6.2米", "6.8米",
                "7.2米", "11.7米", "12.5米", "13米", "13.5米","14米","15米","16米","17米" };

        final String[] type = {"不限","冷藏车","平板","高栏","箱式","保温","危险品","高低板","自备柜"};

        for (int j=0;j<type.length;j++)
        {
            Map<String,String> maps = new HashMap<String, String>();
            maps.put("type",type[j]);
            typeList.add(maps);
        }

        for (int i= 0;i<length.length;i++)
        {
            Map<String,String> map = new HashMap<String,String>();
            map.put("length",length[i]);
            lengthList.add(map);
        }
        final MyGridView lenthGrid = (MyGridView) popView.findViewById(R.id.gridview);
        SimpleAdapter lenthAdapter = new SimpleAdapter(AddCarActivity.this,lengthList,R.layout.find_cartype_pop_item,new String[]{"length"},
                new int[]{R.id.gv_item_text});
        lenthGrid.setAdapter(lenthAdapter);

        final MyGridView typeGrid = (MyGridView) popView.findViewById(R.id.gridview_2);
        SimpleAdapter typeAdapter = new SimpleAdapter(AddCarActivity.this,typeList,R.layout.find_cartype_pop_item,new String[]{"type"},
                new int[]{R.id.gv_item_text});
        typeGrid.setAdapter(typeAdapter);

        typePopmenu.showAtLocation(parent, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0,0);

        param = getWindow().getAttributes();
        param.alpha=0.7f;
        getWindow().setAttributes(param);
        typePopmenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                param = getWindow().getAttributes();
                param.alpha=1f;
                getWindow().setAttributes(param);
            }
        });
        lenthGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CharSequence len = ((TextView) lenthGrid.getChildAt(i).findViewById(R.id.gv_item_text)).getText();
                lenStr = len.toString();
                for(int m=0;m<adapterView.getCount();m++){
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
                for(int m=0;m<adapterView.getCount();m++){
                    TextView item = (TextView) typeGrid.getChildAt(m).findViewById(R.id.gv_item_text);
                    //typeStr = (String)item.getText();
                    if (i == m) {//当前选中的Item改变背景颜色
                        item.setBackgroundResource(R.drawable.cartype_unselect);
                    } else {
                        item.setBackgroundResource(R.drawable.cartype_select);
                    }
                }
            }
        });
        TextView sure = (TextView)popView.findViewById(R.id.cartype_grid_sure);
        TextView noLimit = (TextView)popView.findViewById(R.id.cartype_grid_nocartype);

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // flag = 2;
                mEdit.get(3).setText(typeStr);
                mEdit.get(4).setText(lenStr);
                if (typeStr.equals("自备柜")){
                    zbgInfo.setVisibility(View.VISIBLE);
                    carinfo.setVisibility(View.GONE);
                    mZbg.get(0).setVisibility(View.VISIBLE);
                    mZbg.get(1).setVisibility(View.VISIBLE);
                }else {
                    zbgInfo.setVisibility(View.GONE);
                    carinfo.setVisibility(View.VISIBLE);
                    mZbg.get(0).setVisibility(View.GONE);
                    mZbg.get(1).setVisibility(View.GONE);
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
}
