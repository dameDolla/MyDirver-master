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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.CompanyInfoBean;
import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.bean.UpdateIdCardBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.GetUserInfoUtils;
import com.app.gaolonglong.fragmenttabhost.utils.JsonUtils;
import com.app.gaolonglong.fragmenttabhost.utils.LoadingDialog;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ThreadManager;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.luoxudong.app.threadpool.ThreadPoolHelp;

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
import retrofit2.Call;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yanqi on 2017/8/29.
 */

public class DiaoduRenzheng2Activity extends BaseActivity implements View.OnClickListener{


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




    @BindViews({R.id.diaodu2_zheng_pic,R.id.diaodu2_fan_pic,R.id.diaodu2_yyzz})
    public List<ImageView> icon;

    @BindViews({R.id.top_title})
    public List<TextView> mText;

    @BindViews({R.id.diaodu2_name,R.id.diaodu2_carnum,
                R.id.diaodu2_comname,R.id.diaodu2_comcode,
                R.id.diaodu2_addr,R.id.diaodu2_lxrname,
                R.id.diaodu2_bindtel})
    public List<EditText> mEdit;

    @BindView(R.id.diaodu2_next)
    public Button next;

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
        setContentView(R.layout.diaodu_renzheng_two);
        ButterKnife.bind(this);
        init();
    }
    private void init()
    {
        initView();
    }

    private void initView()
    {
        mText.get(0).setText("车队调度认证");
        icon.get(0).setOnClickListener(this);
        icon.get(1).setOnClickListener(this);
        icon.get(2).setOnClickListener(this);
        next.setOnClickListener(this);
       // mText.get(1).setOnClickListener(this);

        dialog = LoadingDialog.showDialog(DiaoduRenzheng2Activity.this);

        guid = ToolsUtils.getString(DiaoduRenzheng2Activity.this, Constant.LOGIN_GUID,"");
        key = ToolsUtils.getString(DiaoduRenzheng2Activity.this, Constant.KEY,"");
        mobile = ToolsUtils.getString(DiaoduRenzheng2Activity.this, Constant.MOBILE,"");
        String companyguid = GetUserInfoUtils.getCompanyGuid(DiaoduRenzheng2Activity.this);
        if (!TextUtils.isEmpty(companyguid)){
            getCompanyInfo();
        }
        getHttpBitmap(GetUserInfoUtils.getImg(guid,"9"),icon.get(0),0);
        getHttpBitmap(GetUserInfoUtils.getImg(guid,"19"),icon.get(1),1);
        getHttpBitmap(GetUserInfoUtils.getImg(guid,"20"),icon.get(2),2);
        mEdit.get(6).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 11){
                    String tel = mEdit.get(6).getText().toString();
                    if (!ToolsUtils.isChinaPhoneLegal(tel)){
                        ToolsUtils.getInstance().toastShowStr(DiaoduRenzheng2Activity.this,"请输入正确格式的手机号");
                        mEdit.get(6).selectAll();
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {

            case R.id.diaodu2_zheng_pic:
                position=0;
                uploadImage();
                break;
            case R.id.diaodu2_fan_pic:
                position=1;
                uploadImage();
                break;
            case R.id.diaodu2_yyzz:
                position=2;
                uploadImage();
                break;
            case R.id.diaodu2_next:
                next();
                break;
            /*case R.id.cargroup_next:
                startActivity(new Intent(DiaoduRenzheng2Activity.this,CarGroupRenzheng2Activity.class));
                //next();
                break;*/
        }
    }

    /**
     * 下一步
     */
    private void next()
    {
        //dialog.show();
        //String name = mEdit.get(0).getText().toString();
        //String num = mEdit.get(1).getText().toString();
        String camname = mEdit.get(2).getText().toString();
        String comcode = mEdit.get(3).getText().toString();
        //String addr = mEdit.get(4).getText().toString();
        String lxrname = mEdit.get(5).getText().toString();
        String bindTel = mEdit.get(6).getText().toString();

        // startActivity(new Intent(DiaoduRenzheng2Activity.this,CarGroupRenzheng2Activity.class));
        if (TextUtils.isEmpty(camname) || TextUtils.isEmpty(comcode) || TextUtils.isEmpty(lxrname)||TextUtils.isEmpty(bindTel))
        {
            ToolsUtils.getInstance().toastShowStr(DiaoduRenzheng2Activity.this,"请填写完整的信息");
        }
        else
        {

            try {
                mJson = new JSONObject();
                mJson.put("GUID",guid);
                mJson.put("mobile",mobile);
                mJson.put("lealperson","");
                mJson.put("lealpersonIdcard","");
                mJson.put("companyName",camname);
                mJson.put("companycode",comcode);
                mJson.put("address","");
                mJson.put("person",lxrname);
                mJson.put("phone",bindTel);
                mJson.put(Constant.KEY,key);
                mJson.put("type","1");
            }
            catch (Exception e)
            {

            }
            //UpdateIdCardBean bodys = new UpdateIdCardBean(mobile,guid,num,key,name,"3");
            RetrofitUtils.getRetrofitService()
                    .setDiaoduInfo(/*bodys,*/"YZ", Config.DIAODU_INFO,mJson.toString())
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
                            ToolsUtils.getInstance().toastShowStr(DiaoduRenzheng2Activity.this,e.getMessage());
                        }

                        @Override
                        public void onNext(GetCodeBean s) {
                            //dialog.dismiss();
                            Log.e("ddCompany",s.getErrorMsg());
                            ToolsUtils.getInstance().toastShowStr(DiaoduRenzheng2Activity.this,s.getErrorCode());
                            String code = s.getErrorCode();
                            if(code.equals("200") )
                            {
                                ToolsUtils.putString(DiaoduRenzheng2Activity.this,Constant.COMPANYGUID,s.getErrorMsg());
                                startActivity(new Intent(DiaoduRenzheng2Activity.this,CommitSuccessActivity.class));
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
                if (ContextCompat.checkSelfPermission(DiaoduRenzheng2Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(DiaoduRenzheng2Activity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
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
                if (ContextCompat.checkSelfPermission(DiaoduRenzheng2Activity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请READ_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(DiaoduRenzheng2Activity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
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
            Uri contentUri = FileProvider.getUriForFile(DiaoduRenzheng2Activity.this, Constant.APPLICATION_ID + ".fileProvider", tempFile);
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

                    String path = ToolsUtils.getRealFilePath(DiaoduRenzheng2Activity.this,Uri.fromFile(tempFile));
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
                    String picPath = ToolsUtils.getRealFilePath(DiaoduRenzheng2Activity.this,uri);
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
        builder.addFormDataPart("MemberGUID",guid);
        builder.addFormDataPart("headimgurl", "avatar", RequestBody.create(MediaType.parse("image/png/jpg; charset=utf-8"), file));
        if(position == 0)
        {
            builder.addFormDataPart("ImgType","9");
        }else if(position == 1)
        {
            builder.addFormDataPart("ImgType","19");
        }else if(position == 2)
        {
            builder.addFormDataPart("ImgType","20");
        }
        RetrofitUtils.getRetrofitService().
                upload_avatar(builder.build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        //ToolsUtils.getInstance().toastShowStr(DiaoduRenzheng2Activity.this,"上传完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToolsUtils.getInstance().toastShowStr(DiaoduRenzheng2Activity.this,e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {

                            String info = responseBody.string();
                            try {
                                JSONObject json = new JSONObject(info);
                                String str = json.get("errorMsg").toString();
                                ToolsUtils.getInstance().toastShowStr(DiaoduRenzheng2Activity.this,str);
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
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                    final Bitmap finalBitmap = bitmap;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (finalBitmap.getByteCount() != 0){
                                if (position == 0){
                                    iv.setImageBitmap(ToolsUtils.zoomImg(finalBitmap,40,40));
                                }else if (position == 1){
                                    iv.setImageBitmap(ToolsUtils.zoomImg(finalBitmap,200,200));
                                }else if (position == 2){
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
    private void getCompanyInfo(){
        String companyGuid = TextUtils.isEmpty(GetUserInfoUtils.getCompanyGuid(DiaoduRenzheng2Activity.this))?"":GetUserInfoUtils.getCompanyGuid(DiaoduRenzheng2Activity.this);
            Map<String,String> map = new HashMap<>();
            map.put("GUID",guid);
            map.put(Constant.MOBILE,mobile);
            map.put(Constant.KEY,key);
            map.put("companyName", "");
            map.put("companyID", "-1");
            map.put("companysGUID", companyGuid);
            RetrofitUtils.getRetrofitService()
                    .getConpanyInfo("YZ",Config.GET_COMPANYINFO, JsonUtils.getInstance().getJsonStr(map))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<CompanyInfoBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(CompanyInfoBean companyInfoBean) {
                            if (companyInfoBean.getErrorCode().equals("200")){
                                CompanyInfoBean.DataBean data = companyInfoBean.getData().get(0);
                                mEdit.get(2).setText(data.getCompanyName());
                                mEdit.get(3).setText(data.getCompanycode());
                                mEdit.get(5).setText(data.getPerson());
                                mEdit.get(6).setText(data.getPhone());
                            }
                        }
                    });
        }

}
