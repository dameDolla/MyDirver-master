package com.mr.truck.activities;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mr.truck.R;
import com.mr.truck.config.Constant;
import com.mr.truck.service.GetUserInfoService;
import com.mr.truck.utils.GetUserInfoUtils;
import com.mr.truck.utils.RetrofitUtils;
import com.mr.truck.utils.ToolsUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
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
 * Created by yanqi on 2017/8/9.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener{

    private ImageView back;
    private TextView title;
    private RelativeLayout rl;
    private File tempFile,file;
    private String key;
    private String mobile;
    private String guid;

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

    @BindView(R.id.setting_icon_rl)
    public RelativeLayout  rl_icon;

    @BindView(R.id.setting_call)
    public TextView call;

    @BindView(R.id.setting_icon)
    public SimpleDraweeView icon;

    @BindView(R.id.setting_phone)
    public TextView tel;

    @OnClick(R.id.setting_call_ll)
    public void call()
    {
        Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+call.getText()+""));
        startActivity(intent);
    }

    @OnClick(R.id.setting_intro)
    public void intro()
    {
        startActivity(new Intent(SettingActivity.this,IntroduceActivity.class));
    }
    private Intent intentService;

    @OnClick(R.id.exit_account)
    public void exit()
    {

        ToolsUtils.getInstance().loginOut(SettingActivity.this);
        startActivity(new Intent(SettingActivity.this,LoginActivity.class));
        finish();
    }

    @OnClick(R.id.setting_set_paycode)
    public void setPayCode()
    {
        startActivity(new Intent(SettingActivity.this,SetPayCodeMainActivity.class));
    }

    @OnClick(R.id.title_back_txt)
    public void back(){finish();}
    @OnClick(R.id.title_back)
    public void backs(){finish();}
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_content);
        ButterKnife.bind(this);
        init();
        /*intentService = new Intent(SettingActivity.this, GetUserInfoService.class);
        bindService(intentService,conn, Context.BIND_AUTO_CREATE);*/
       /* startService(intentService);
        Intent intent2 = new Intent(SettingActivity.this, GetUserInfoService.class);
        stopService(intent2);*/
    }



    private void init()
    {
        initView();
        initIcon();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //this.unbindService(conn);

    }
    private ServiceConnection conn = new ServiceConnection() {

        private GetUserInfoService userinfoService;

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            userinfoService = ((GetUserInfoService.MyBinder) iBinder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            userinfoService = null;
        }
    };
    private  void initView()
    {
        back = (ImageView)findViewById(R.id.title_back);
        title = (TextView)findViewById(R.id.top_title);
        rl = (RelativeLayout)findViewById(R.id.title_rl);
        tel.setText(GetUserInfoUtils.getMobile(SettingActivity.this));
        rl_icon.setOnClickListener(this);
        icon.setOnClickListener(this);

        title.setText("设置");
        guid = GetUserInfoUtils.getGuid(SettingActivity.this);
        try {
            InputStream data = getResources().getAssets().open("area.txt");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void initIcon()
    {
        String icons = ToolsUtils.getString(SettingActivity.this,Constant.HEADLOGO,"");
        if(!icons.equals(""))
        {
            icon.setImageURI(Uri.parse(icons));
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
                if (ContextCompat.checkSelfPermission(SettingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(SettingActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
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
                if (ContextCompat.checkSelfPermission(SettingActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请READ_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(SettingActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
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
            Uri contentUri = FileProvider.getUriForFile(SettingActivity.this, Constant.APPLICATION_ID + ".fileProvider", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, REQUEST_CAPTURE);
    }



    private BitmapFactory.Options getBitmapOption(int inSampleSize){
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

                    String path = ToolsUtils.getRealFilePath(SettingActivity.this,Uri.fromFile(tempFile));
                    Bitmap bitmap=BitmapFactory.decodeFile(path,getBitmapOption(2));
                    icon.setImageBitmap( ToolsUtils.centerSquareScaleBitmap(bitmap,120));


                    file = ToolsUtils.compressImage(BitmapFactory.decodeFile(path));

                    if(file.exists())
                    {
                        upload();
                    }

                }
                break;
            case REQUEST_PICK:  //调用系统相册返回
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    String picPath = ToolsUtils.getRealFilePath(SettingActivity.this,uri);
                    Bitmap bitmap=BitmapFactory.decodeFile(picPath,getBitmapOption(2));
                    icon.setImageBitmap( ToolsUtils.centerSquareScaleBitmap(bitmap,120));
                    file = ToolsUtils.compressImage(BitmapFactory.decodeFile(picPath));
                    if(file.exists())
                    {
                        upload();
                    }
                }
                break;

        }
    }

    /**
     * 图片上传的方法
     */
    private void upload()
    {

        MultipartBody.Builder builder =  new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("MemberGUID",guid);

        builder.addFormDataPart("headimgurl", "avatar", RequestBody.create(MediaType.parse("image/png/jpg; charset=utf-8"), file));
        builder.addFormDataPart("ImgType","1");
        RetrofitUtils.getRetrofitService().
                upload_avatar(builder.build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        //ToolsUtils.getInstance().toastShowStr(SettingActivity.this,"上传完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToolsUtils.getInstance().toastShowStr(SettingActivity.this,e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {

                            String info = responseBody.string();
                            try {
                                JSONObject json = new JSONObject(info);
                                String str = json.get("data").toString();
                                ToolsUtils.getInstance().toastShowStr(SettingActivity.this,str);
                            }
                            catch (Exception e)
                            {

                            }

                        }
                        catch (IOException e)
                        {

                        }

                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.setting_icon_rl:
                if (ToolsUtils.getInstance().isLogin(SettingActivity.this))
                {
                    uploadImage();
                }
                else
                {
                    ToolsUtils.getInstance().toastShowStr(SettingActivity.this,"请先登录");
                }
                break;
            case R.id.setting_icon:

                break;
        }
    }
}
