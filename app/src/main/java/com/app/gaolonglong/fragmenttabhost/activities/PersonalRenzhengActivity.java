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
import com.app.gaolonglong.fragmenttabhost.bean.UpdateIdCardBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.LoadingDialog;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yanqi on 2017/8/28.
 */

public class PersonalRenzhengActivity extends BaseActivity implements View.OnClickListener{

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

    private LoadingDialog dialog;

    private JSONObject mJson;

    private Call<GetCodeBean> call;


    @BindView(R.id.upload_head_rl)
    public RelativeLayout upload_head;

    @BindViews({R.id.renzheng_head,R.id.card_face,R.id.card_back,R.id.person_with_card})
    public List<ImageView> icon;

    @BindViews({R.id.top_title,R.id.cargroup_next})
    public List<TextView> mText;

    @BindViews({R.id.cargroup_name,R.id.cargroup_num})
    public List<EditText> mEdit;
    private String guid;
    private String key;
    private String mobile;


    @OnClick(R.id.title_back)
    public void back()
    {
       finish();
    }



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
        mText.get(0).setText("个体司机认证");
        upload_head.setOnClickListener(this);
        icon.get(1).setOnClickListener(this);
        icon.get(2).setOnClickListener(this);
        icon.get(3).setOnClickListener(this);
        mText.get(1).setOnClickListener(this);
        dialog = LoadingDialog.showDialog(PersonalRenzhengActivity.this);

        guid = ToolsUtils.getString(PersonalRenzhengActivity.this, Constant.LOGIN_GUID,"");
        key = ToolsUtils.getString(PersonalRenzhengActivity.this, Constant.KEY, "");
        mobile = ToolsUtils.getString(PersonalRenzhengActivity.this, Constant.MOBILE,"");
        mEdit.get(1).setText(ToolsUtils.getString(PersonalRenzhengActivity.this,"idcard",""));
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
        dialog.show();
        String name = mEdit.get(0).getText().toString();
        String num = mEdit.get(1).getText().toString();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(num))
        {
            ToolsUtils.getInstance().toastShowStr(PersonalRenzhengActivity.this,"请填写完整的信息");
        }
        else
        {

            try {
                mJson = new JSONObject();
                mJson.put("GUID",guid);
                mJson.put("mobile",mobile);
                mJson.put("idcard",num);
                mJson.put("truename",name);
                mJson.put(Constant.KEY,key);
                mJson.put("usertype","2");//个体司机
            }
            catch (Exception e)
            {

            }

            UpdateIdCardBean bodys = new UpdateIdCardBean(mobile,guid,num,key,name,"3");
            RetrofitUtils.getRetrofitService()
                    .upload_cargoupinfo_one(/*bodys,*/"YZ",Config.CARGROUP_ONE,mJson.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<GetCodeBean>() {
                        @Override
                        public void onCompleted() {
                            dialog.dismiss();
                        }

                        @Override
                        public void onError(Throwable e) {
                            dialog.dismiss();
                            //ToolsUtils.getInstance().toastShowStr(PersonalRenzhengActivity.this,e.getMessage());
                        }

                        @Override
                        public void onNext(GetCodeBean s) {
                            dialog.dismiss();
                            String code = s.getErrorCode();
                            if(code.equals("200") )
                            {

                                startActivity(new Intent(PersonalRenzhengActivity.this,PersonalRenzheng2Activity.class));
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
                if (ContextCompat.checkSelfPermission(PersonalRenzhengActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(PersonalRenzhengActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
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
                if (ContextCompat.checkSelfPermission(PersonalRenzhengActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请READ_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(PersonalRenzhengActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
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
            Uri contentUri = FileProvider.getUriForFile(PersonalRenzhengActivity.this, Constant.APPLICATION_ID + ".fileProvider", tempFile);
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

                    String path = ToolsUtils.getRealFilePath(PersonalRenzhengActivity.this,Uri.fromFile(tempFile));
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
                    String picPath = ToolsUtils.getRealFilePath(PersonalRenzhengActivity.this,uri);
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
                       // ToolsUtils.getInstance().toastShowStr(PersonalRenzhengActivity.this,file.length()+"");
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

        if(position == 0)
        {
            builder.addFormDataPart("ImgType","1");
        }else if(position == 1)
        {
            builder.addFormDataPart("ImgType","2");
        }else if(position == 2)
        {
            builder.addFormDataPart("ImgType","3");
        }else if(position == 3)
        {
            builder.addFormDataPart("ImgType","3");
        }
        RetrofitUtils.getRetrofitService().
                upload_avatar(builder.build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        //ToolsUtils.getInstance().toastShowStr(PersonalRenzhengActivity.this,"上传完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToolsUtils.getInstance().toastShowStr(PersonalRenzhengActivity.this,e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {

                            String info = responseBody.string();
                            try {
                                JSONObject json = new JSONObject(info);
                                String str = json.get("errorMsg").toString();
                                ToolsUtils.getInstance().toastShowStr(PersonalRenzhengActivity.this, str);
                            } catch (Exception e) {

                            }

                        } catch (IOException e) {

                        }

                    }
                });
    }
}
