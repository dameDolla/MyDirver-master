package com.mr.truck.activities;

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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

import com.mr.truck.R;
import com.mr.truck.bean.GetCodeBean;
import com.mr.truck.bean.UpdateIdCardBean;
import com.mr.truck.config.Config;
import com.mr.truck.config.Constant;
import com.mr.truck.utils.GetUserInfoUtils;
import com.mr.truck.utils.LoadingDialog;
import com.mr.truck.utils.RetrofitUtils;
import com.mr.truck.utils.ThreadManager;
import com.mr.truck.utils.ToolsUtils;
import com.luoxudong.app.threadpool.ThreadPoolHelp;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
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
 * Created by yanqi on 2017/8/29.
 */

public class DiaoDuRenzhengActivity extends BaseActivity implements View.OnClickListener{

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

   /* @BindView(R.id.renzheng_heads)
    public SimpleDraweeView head;*/
   /*@BindView(R.id.renzheng_logo)
   public SimpleDraweeView logo;*/

    @BindViews({R.id.cargroup_name,R.id.cargroup_num})
    public List<EditText> mEdit;
    private String guid;
    private String key;
    private String mobile;
    private LoadingDialog dialog;

    @OnClick(R.id.title_back)
    public void back()
    {
        finish();
    }
    private JSONObject mJson;
    private Call<GetCodeBean> call;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diaodu_renzheng_one);
        ButterKnife.bind(this);
        init();
    }



    private void init()
    {
        initView();
    }

    private void initView()
    {
        //SimpleDraweeView logo = (SimpleDraweeView) findViewById(R.id.renzheng_logo);
        mText.get(0).setText("车队调度认证");
        upload_head.setOnClickListener(this);
        icon.get(1).setOnClickListener(this);
        icon.get(2).setOnClickListener(this);
        icon.get(3).setOnClickListener(this);
        mText.get(1).setOnClickListener(this);
        mEdit.get(0).setText(GetUserInfoUtils.getUserName(DiaoDuRenzhengActivity.this));
        mEdit.get(1).setText(GetUserInfoUtils.getIdCard(DiaoDuRenzhengActivity.this));
       //logo.setImageURI(Uri.parse(ToolsUtils.getString(DiaoDuRenzhengActivity.this,Constant.HEADLOGO,"")));
        dialog = LoadingDialog.showDialog(DiaoDuRenzhengActivity.this);

        guid = ToolsUtils.getString(DiaoDuRenzhengActivity.this, Constant.LOGIN_GUID,"");
        key = ToolsUtils.getString(DiaoDuRenzhengActivity.this, Constant.KEY,"");
        mobile = ToolsUtils.getString(DiaoDuRenzhengActivity.this, Constant.MOBILE,"");
        String url = ToolsUtils.getString(DiaoDuRenzhengActivity.this,Constant.HEADLOGO,"");
        if (!GetUserInfoUtils.getVtrueName(DiaoDuRenzhengActivity.this).equals("0")){
            if (!TextUtils.isEmpty(url)){
                getHttpBitmap(url,icon.get(0),0);
            }
           try {
               getHttpBitmap(GetUserInfoUtils.getImg(guid,"2"),icon.get(1),1);
               getHttpBitmap(GetUserInfoUtils.getImg(guid,"3"),icon.get(2),2);
               getHttpBitmap(GetUserInfoUtils.getImg(guid,"15"),icon.get(3),3);
           }catch (OutOfMemoryError e){
               e.printStackTrace();
           }
        }

        mEdit.get(1).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 18){
                    String idcard = mEdit.get(1).getText().toString();
                    try {
                        String str = ToolsUtils.IDCardValidate(idcard);
                        if (!TextUtils.isEmpty(str)){
                            mEdit.get(1).selectAll();
                            ToolsUtils.getInstance().toastShowStr(DiaoDuRenzhengActivity.this,str);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
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
                //startActivity(new Intent(DiaoDuRenzhengActivity.this,DiaoduRenzheng2Activity.class));
                next();
                break;
        }
    }

    /**
     * 下一步
     */
    private void next()
    {
        //dialog.show();
        final String name = mEdit.get(0).getText().toString();
        final String num = mEdit.get(1).getText().toString();
        // startActivity(new Intent(DiaoDuRenzhengActivity.this,CarGroupRenzheng2Activity.class));
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(num))
        {
            ToolsUtils.getInstance().toastShowStr(DiaoDuRenzhengActivity.this,"请填写完整的信息");
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
                mJson.put("usertype","4");
            }
            catch (Exception e)
            {

            }
            UpdateIdCardBean bodys = new UpdateIdCardBean(mobile,guid,num,key,name,"3");
            RetrofitUtils.getRetrofitService()
                    .upload_cargoupinfo_one(/*bodys,*/"YZ", Config.CARGROUP_ONE,mJson.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<GetCodeBean>() {
                        @Override
                        public void onCompleted() {
                            //dialog.dismiss();
                        }

                        @Override
                        public void onError(Throwable e) {
                            // dialog.dismiss();
                            ToolsUtils.getInstance().toastShowStr(DiaoDuRenzhengActivity.this,e.getMessage());
                        }

                        @Override
                        public void onNext(GetCodeBean s) {
                            //dialog.dismiss();
                            ToolsUtils.getInstance().toastShowStr(DiaoDuRenzhengActivity.this,s.getErrorCode());
                            String code = s.getErrorCode();
                            if(code.equals("200") )
                            {
                                ToolsUtils.putString(DiaoDuRenzhengActivity.this,Constant.USERNAME,name);
                                ToolsUtils.putString(DiaoDuRenzhengActivity.this,"idcard",num);

                                ToolsUtils.putString(DiaoDuRenzhengActivity.this,Constant.VTRUENAME,"1");
                                startActivity(new Intent(DiaoDuRenzhengActivity.this,DiaoduRenzheng2Activity.class));
                                finish();
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
                if (ContextCompat.checkSelfPermission(DiaoDuRenzhengActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(DiaoDuRenzhengActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
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
                if (ContextCompat.checkSelfPermission(DiaoDuRenzhengActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请READ_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(DiaoDuRenzhengActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
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
            Uri contentUri = FileProvider.getUriForFile(DiaoDuRenzhengActivity.this, Constant.APPLICATION_ID + ".fileProvider", tempFile);
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

                    String path = ToolsUtils.getRealFilePath(DiaoDuRenzhengActivity.this,Uri.fromFile(tempFile));
                    Bitmap bitmap=BitmapFactory.decodeFile(path,getBitmapOption(2));
                    try{
                        if(position != 0)
                        {
                            icon.get(position).setImageBitmap( ToolsUtils.centerSquareScaleBitmap(bitmap,250));
                        }
                        else
                        {
                            icon.get(position).setImageBitmap( ToolsUtils.centerSquareScaleBitmap(bitmap,120));
                        }
                    }catch (OutOfMemoryError o){
                        o.printStackTrace();
                    }

                    file = ToolsUtils.compressImage(BitmapFactory.decodeFile(path));

                    if(file.exists())
                    {
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
                    String picPath = ToolsUtils.getRealFilePath(DiaoDuRenzhengActivity.this,uri);
                    Bitmap bitmap=BitmapFactory.decodeFile(picPath,getBitmapOption(2));
                    try{
                        if(position != 0)
                        {
                            icon.get(position).setImageBitmap( ToolsUtils.centerSquareScaleBitmap(bitmap,250));
                        }
                        else
                        {
                            icon.get(position).setImageBitmap( ToolsUtils.centerSquareScaleBitmap(bitmap,120));
                        }
                    }catch (OutOfMemoryError e){
                        e.printStackTrace();
                    }
                    try {
                        file = ToolsUtils.compressImage(BitmapFactory.decodeFile(picPath));
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                    if(file.exists())
                    {
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

    /**
     * 图片上传的方法
     */
    private void upload()
    {

        MultipartBody.Builder builder =  new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (position == 0){
            builder.addFormDataPart("ImgType","1");
        }else if (position == 1){
            builder.addFormDataPart("ImgType","2");
        }else if (position == 2){
            builder.addFormDataPart("ImgType","3");
        }else if (position == 3){
            builder.addFormDataPart("ImgType","15");
        }

        builder.addFormDataPart("MemberGUID",guid);
        builder.addFormDataPart("headimgurl", "avatar", RequestBody.create(MediaType.parse("image/png/jpg; charset=utf-8"), file));
        RetrofitUtils.getRetrofitService().
                upload_avatar(builder.build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        //ToolsUtils.getInstance().toastShowStr(DiaoDuRenzhengActivity.this,"上传完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToolsUtils.getInstance().toastShowStr(DiaoDuRenzhengActivity.this,e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {

                            String info = responseBody.string();
                            try {
                                JSONObject json = new JSONObject(info);
                                String str = json.get("errorMsg").toString();

                                if (position == 0)
                                {
                                    ToolsUtils.getInstance().toastShowStr(DiaoDuRenzhengActivity.this,"上传图片成功");
                                    ToolsUtils.putString(DiaoDuRenzhengActivity.this,Constant.HEADLOGO,str);
                                }else {
                                    ToolsUtils.getInstance().toastShowStr(DiaoDuRenzhengActivity.this,str);
                                }
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
                    BitmapFactory.Options opt = new BitmapFactory.Options();
                    opt.inPreferredConfig = Bitmap.Config.RGB_565;
                    opt.inPurgeable = true;
                    opt.inInputShareable = true;
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is,null,opt);
                    is.close();
                    final Bitmap finalBitmap = bitmap;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (finalBitmap.getByteCount() != 0){
                                if (position == 0){
                                    iv.setImageBitmap(ToolsUtils.zoomImg(finalBitmap,100,100));
                                }else if (position == 1){
                                    iv.setImageBitmap(ToolsUtils.zoomImg(finalBitmap,200,200));
                                }else if (position == 2){
                                    iv.setImageBitmap(ToolsUtils.zoomImg(finalBitmap,200,200));
                                }else if (position == 3){
                                    iv.setImageBitmap(ToolsUtils.zoomImg(finalBitmap,200,200));
                                }
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
