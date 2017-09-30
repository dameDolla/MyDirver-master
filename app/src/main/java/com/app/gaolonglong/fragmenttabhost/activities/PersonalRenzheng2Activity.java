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
import com.app.gaolonglong.fragmenttabhost.utils.JsonUtils;
import com.app.gaolonglong.fragmenttabhost.utils.LoadingDialog;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @BindViews({R.id.person2_card_pic, R.id.person2_with_card,
            R.id.person2_car_face, R.id.person2_car_back,
            R.id.person2_car_content})
    public List<ImageView> mImage;

    @BindViews({R.id.person2_carnum, R.id.person2_xsnum,
            R.id.person2_time, R.id.person2_cartype,
            R.id.person2_carlong})
    public List<EditText> mEdit;

    @OnClick(R.id.title_back)
    public void back() {
        finish();
    }

    @OnClick(R.id.person2_next)
    public void next() {
        dialog.show();
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
    }

    private void initView() {
        mText.get(0).setText("个体司机认证");
        mImage.get(0).setOnClickListener(this);
        mImage.get(1).setOnClickListener(this);
        mImage.get(2).setOnClickListener(this);
        mImage.get(3).setOnClickListener(this);
        mImage.get(4).setOnClickListener(this);
        dialog = LoadingDialog.showDialog(PersonalRenzheng2Activity.this);

        guid = ToolsUtils.getString(PersonalRenzheng2Activity.this, Constant.LOGIN_GUID, "");
        mobile = ToolsUtils.getString(PersonalRenzheng2Activity.this, Constant.MOBILE, "");
        key = ToolsUtils.getString(PersonalRenzheng2Activity.this, Constant.KEY, "");
    }
    private String initJsonData()
    {
        Map<String,String> map  = new HashMap<>();
        map.put("GUID",guid);
        map.put(Constant.KEY,key);
        map.put(Constant.MOBILE,mobile);
        map.put("truckno", mEdit.get(0).getText().toString());
        map.put("trucklicence", mEdit.get(1).getText().toString());
        map.put("trucktype", mEdit.get(3).getText().toString());
        map.put("trucklength", mEdit.get(4).getText().toString());
        map.put("boardingtime", mEdit.get(2).getText().toString());

        return JsonUtils.getInstance().getJsonStr(map);
    }
    private void toNext(String json) {

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
                        Log.e("personerror",e.getMessage());
                    }

                    @Override
                    public void onNext(GetCodeBean getCodeBean) {
                        Log.e("personinfo",getCodeBean.getErrorMsg());
                        if (getCodeBean.getErrorCode().equals("200"))
                        {
                            startActivity(new Intent(PersonalRenzheng2Activity.this,CommitSuccessActivity.class));
                        }
                    }
                });

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
                        upload();
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
                        upload();
                    }
                }
                break;

        }
    }

    private void upload() {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("MemberGUID", Constant.LOGIN_GUID);
        builder.addFormDataPart("headimgurl", "avatar", RequestBody.create(MediaType.parse("image/png/jpg; charset=utf-8"), file));
        if(position == 0)
        {
            builder.addFormDataPart("ImgType", "4");
        }else if(position == 1)
        {
            builder.addFormDataPart("ImgType", "1");//个人持照
        }else if(position == 2)
        {
            builder.addFormDataPart("ImgType", "1");
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
                        ToolsUtils.getInstance().toastShowStr(PersonalRenzheng2Activity.this, e.getMessage());
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
        }
    }
}
