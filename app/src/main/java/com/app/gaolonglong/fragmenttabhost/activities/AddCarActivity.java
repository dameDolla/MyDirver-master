package com.app.gaolonglong.fragmenttabhost.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.GetUserInfoUtils;
import com.app.gaolonglong.fragmenttabhost.utils.JsonUtils;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.luoxudong.app.threadpool.ThreadPoolHelp;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
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
    private File tempFile;
    private String truckguid;
    private File file;
    private File file1;
    private String flag;

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
                R.id.addcar_car_content})
    public List<SimpleDraweeView> mImg;

    @OnClick(R.id.addcar_queren)
    public void querens()
    {
        add(initJsonData());
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
    }

    private String  initJsonData()
    {
        Map<String, String> map = new HashMap<String,String>();
        String guid = GetUserInfoUtils.getGuid(AddCarActivity.this);
        String mobile = GetUserInfoUtils.getMobile(AddCarActivity.this);
        String key = GetUserInfoUtils.getKey(AddCarActivity.this);
        String carnum = mEdit.get(0).getText()+"";
        String xsznum = mEdit.get(1).getText()+"";
        String time = mEdit.get(2).getText()+"";
        String cartype = mEdit.get(3).getText()+"";
        String carlenght = mEdit.get(4).getText()+"";
        if (TextUtils.isEmpty(carnum) || TextUtils.isEmpty(xsznum)|| TextUtils.isEmpty(cartype))
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
            map.put("trucklicence",xsznum);
            map.put("companyGUID",guid);
            map.put("company","");
            map.put("driverGUID","");
            map.put("drivername","");
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
            builder.addFormDataPart("ImgType", "4");
        }else if(position == 1)
        {
            builder.addFormDataPart("ImgType", "14");//个人持照
        }else if(position == 2)
        {
            builder.addFormDataPart("ImgType", "5");
        }else if (position == 3)
        {
            builder.addFormDataPart("ImgType", "6");
        }else if (position == 4)
        {
            builder.addFormDataPart("ImgType", "7");
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
}
