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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.bean.RequestPostBody;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.service.MyService;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.Subscriber;

/**
 * Created by yanqi on 2017/8/22.
 */

public class CarGroupRenzhengActivity extends BaseActivity implements View.OnClickListener{

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

    private File tempFile,file;

    private List<Uri> uList = new ArrayList<Uri>();

    private int position = 0;


    @BindView(R.id.upload_head_rl)
    public RelativeLayout upload_head;

    @BindViews({R.id.renzheng_head,R.id.card_face,R.id.card_back,R.id.person_with_card})
    public List<ImageView> icon;

    @BindViews({R.id.top_title,R.id.cargroup_next})
    public List<TextView> mText;

    @BindViews({R.id.cargroup_name,R.id.cargroup_num})
    public List<EditText> mEdit;
    private JSONObject mJson;
    private Call<GetCodeBean> call;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cargroup_renzheng_one);
        ButterKnife.bind(this);
        init();
    }
    private void init()
    {
        initView();
    }

    private void initView()
    {
        mText.get(0).setText("车队司机认证");
        upload_head.setOnClickListener(this);
        icon.get(1).setOnClickListener(this);
        icon.get(2).setOnClickListener(this);
        icon.get(3).setOnClickListener(this);
        mText.get(1).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.upload_head_rl:
                uploadImage();
                position=0;
                break;
            case R.id.card_face:
                uploadImage();
                position=1;
                break;
            case R.id.card_back:
                uploadImage();
                position=2;
                break;
            case R.id.person_with_card:
                uploadImage();
                position=3;
                break;
            case R.id.cargroup_next:
                next();
                break;
        }
    }

    /**
     * 下一步
     */
    private void next()
    {
       String name = mEdit.get(0).getText().toString();
       String num = mEdit.get(1).getText().toString();
       // startActivity(new Intent(CarGroupRenzhengActivity.this,CarGroupRenzheng2Activity.class));
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(num))
        {
            ToolsUtils.getInstance().toastShowStr(CarGroupRenzhengActivity.this,"请填写完整的信息");
        }
        else
        {

           try {
               mJson = new JSONObject();
               mJson.put("GUID",Constant.GUID);
               mJson.put("mobile","15908690321");
               mJson.put("idcard",num);
               mJson.put("truename",name);
           }
           catch (Exception e)
           {

           }
            /*HashMap<String,String> params = new HashMap<String,String>();
            params.put("PageName","YZ");
            params.put("MethodName",Config.CARGROUP_ONE);
            params.put("JsonValue",mJson.toString());
            params.put("MDSValue",ToolsUtils.getInstance().getMD5Val(mJson.toString()));*/
            RequestPostBody body = new RequestPostBody("YZ",Config.CARGROUP_ONE,mJson.toString(),ToolsUtils.getInstance().getMD5Val(mJson.toString()));
            /*body.setJsonValue(mJson.toString());
            body.setMDSValue(ToolsUtils.getInstance().getMD5Val(mJson.toString()));
            body.setMethodName(Config.CARGROUP_ONE);
            body.setPageName("YZ");*/
            /*try {
               String jsonStr = new String((mJson.toString()).getBytes("UTF-8"), "UTF-8");
                call = RetrofitUtils.getRetrofitService().upload_info(Config.CARGROUP_ONE
                        , ToolsUtils.getInstance().getMD5Val(mJson.toString())
                        ,jsonStr
                        ,"YZ");
            }
            catch (Exception e)
            {

            }



            call.enqueue(new Callback<GetCodeBean>() {
                @Override
                public void onResponse(Call<GetCodeBean> call, Response<GetCodeBean> response) {
                    if(response.isSuccessful())
                    {
                        GetCodeBean code = response.body();
                        ToolsUtils.getInstance().toastShowStr(CarGroupRenzhengActivity.this,code.getErrorMsg());
                    }
                }

                @Override
                public void onFailure(Call<GetCodeBean> call, Throwable t) {

                }
            });*/
            RetrofitUtils.getRetrofitService()
                    .upload_cargoupinfo_one(body)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<GetCodeBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            ToolsUtils.getInstance().toastShowStr(CarGroupRenzhengActivity.this,e.getMessage());
                        }

                        @Override
                        public void onNext(GetCodeBean s) {
                            try {

                                if(s.getErrorCode() != "")
                                {
                                    startActivity(new Intent(CarGroupRenzhengActivity.this,CarGroupRenzheng2Activity.class));
                                }
                            }
                            catch (Exception e)
                            {

                            }

                        }
                    });
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
                if (ContextCompat.checkSelfPermission(CarGroupRenzhengActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(CarGroupRenzhengActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
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
                if (ContextCompat.checkSelfPermission(CarGroupRenzhengActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请READ_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(CarGroupRenzhengActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
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
        tempFile = new File(checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/image/"), System.currentTimeMillis() + ".jpg");

        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //设置7.0中共享文件，分享路径定义在xml/file_paths.xml
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(CarGroupRenzhengActivity.this, Constant.APPLICATION_ID + ".fileProvider", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, REQUEST_CAPTURE);
    }

    /**
     * 检查文件是否存在
     */
    private static String checkDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return "";
        }
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
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

                    String path = ToolsUtils.getRealFilePath(CarGroupRenzhengActivity.this,Uri.fromFile(tempFile));
                    Bitmap bitmap=BitmapFactory.decodeFile(path,getBitmapOption(2));
                    if(position != 0)
                    {
                        icon.get(position).setImageBitmap( ToolsUtils.centerSquareScaleBitmap(bitmap,250));
                    }
                    else
                    {
                        icon.get(position).setImageBitmap( ToolsUtils.centerSquareScaleBitmap(bitmap,120));
                    }


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
                    String picPath = ToolsUtils.getRealFilePath(CarGroupRenzhengActivity.this,uri);
                    Bitmap bitmap=BitmapFactory.decodeFile(picPath,getBitmapOption(2));
                    if(position != 0)
                    {
                        icon.get(position).setImageBitmap( ToolsUtils.centerSquareScaleBitmap(bitmap,250));
                    }
                    else
                    {
                        icon.get(position).setImageBitmap( ToolsUtils.centerSquareScaleBitmap(bitmap,120));
                    }
                    file = ToolsUtils.compressImage(BitmapFactory.decodeFile(picPath));
                    if(file.exists())
                    {
                        upload();
                    }
                }
                break;
            case REQUEST_CROP_PHOTO:  //剪切图片返回
                /*if (resultCode == RESULT_OK) {
                    final Uri uri = intent.getData();
                    if (uri == null) {
                        return;
                    }
                    String cropImagePath = getRealFilePathFromUri(getApplicationContext(), uri);
                    Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
                    if (type == 1) {
                        headImage1.setImageBitmap(bitMap);
                    } else {
                        headImage2.setImageBitmap(bitMap);
                    }*/
                    //此处后面可以将bitMap转为二进制上传后台网络
                    //......
                    //info.setText(getRealFilePath(MainActivity.this,uri));
                    //upload(bitMap);
                   /* File file = new File(getRealFilePath(MainActivity.this,uri));
                    info.setText(file.length()+"");
                    RequestBody fileBody = RequestBody.create(MediaType.parse(TYPE), file);
                    RequestBody requestBody = new MultipartBuilder().type(MultipartBuilder.FORM).addFormDataPart("MemberGUID","hello").addFormDataPart("filename", file.getName(), fileBody).build();

                    Request requestPostFile = new Request.Builder()
                            .url(URL)
                            .post(requestBody)
                            .build();
                    // info.setText(requestBody.toString());
                    client.newCall(requestPostFile).enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {

                        }

                        @Override
                        public void onResponse(final Response response) throws IOException {
                            //info.setText("success");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //info.setText(response.toString());
                                }
                            });
                        }


                    });*/

                /*}*/
                break;
        }
    }

    /**
     * 图片上传的方法
     */
    private void upload()
    {
        MultipartBody.Builder builder =  new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("MemberGUID",Constant.GUID);
        builder.addFormDataPart("ImgType","1");
        builder.addFormDataPart("headimgurl", "avatar", RequestBody.create(MediaType.parse("image/png/jpg; charset=utf-8"), file));
        RetrofitUtils.getRetrofitService().
                upload_avatar(builder.build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        //ToolsUtils.getInstance().toastShowStr(CarGroupRenzhengActivity.this,"上传完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                         ToolsUtils.getInstance().toastShowStr(CarGroupRenzhengActivity.this,e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {

                            String info = responseBody.string();
                            try {
                                JSONObject json = new JSONObject(info);
                                String str = json.get("errorMsg").toString();
                                ToolsUtils.getInstance().toastShowStr(CarGroupRenzhengActivity.this,str);
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
}
