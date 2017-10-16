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
import com.app.gaolonglong.fragmenttabhost.utils.LoadingDialog;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.app.gaolonglong.fragmenttabhost.view.MyGridView;
import com.luoxudong.app.threadpool.ThreadPoolHelp;

import org.json.JSONException;
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
 * Created by yanqi on 2017/8/29.
 */

public class DiaoduRenzheng3Activity extends BaseActivity implements View.OnClickListener{


    private LoadingDialog dialog;

    private int position = 0;

    private String NEWGUID = "diaodunewguid";

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
    private WindowManager.LayoutParams param;

    @BindViews({R.id.top_title})
    public List<TextView> mText;

    @BindViews({R.id.diaodu3_card_pic, R.id.diaodu3_with_card,
            R.id.diaodu3_car_face, R.id.diaodu3_car_back,
            R.id.diaodu3_car_content})
    public List<ImageView> mImage;

    @BindViews({R.id.diaodu3_carnum, R.id.diaodu3_xsnum,
            R.id.diaodu3_time, R.id.diaodu3_cartype,
            R.id.diaodu3_carlong})
    public List<EditText> mEdit;

    @BindView(R.id.diaodu3_parent)
    public LinearLayout parent;

    @BindView(R.id.diaodu3_car_ll)
    public LinearLayout carLL;
    private View popView;
    private PopupWindow typePopmenu;

    @OnClick(R.id.title_back)
    public void back() {
        finish();
    }

    @OnClick(R.id.diaodu3_next)
    public void next() {
        dialog.show();
        toNext();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diaodu_renzheng_three);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initView();
    }

    private void initView() {
        mText.get(0).setText("车队调度认证");
        mImage.get(0).setOnClickListener(this);
        mImage.get(1).setOnClickListener(this);
        mImage.get(2).setOnClickListener(this);
        mImage.get(3).setOnClickListener(this);
        mImage.get(4).setOnClickListener(this);
        carLL.setOnClickListener(this);
        dialog = LoadingDialog.showDialog(DiaoduRenzheng3Activity.this);
        getGuid();
        guid = ToolsUtils.getString(DiaoduRenzheng3Activity.this, Constant.LOGIN_GUID, "");
        mobile = ToolsUtils.getString(DiaoduRenzheng3Activity.this, Constant.MOBILE, "");
        key = ToolsUtils.getString(DiaoduRenzheng3Activity.this, Constant.KEY, "");
    }

    private void toNext() {

        JSONObject json = new JSONObject();
        try {
            json.put("GUID", guid);
            json.put(Constant.KEY, key);
            json.put(Constant.MOBILE, mobile);
            json.put("truckno", mEdit.get(0).getText());
            json.put("trucklicence", mEdit.get(1).getText());
            json.put("trucktype", mEdit.get(3).getText());
            json.put("trucklength", mEdit.get(4).getText());
            json.put("boardingtime", "");
            json.put("TruckImg","");
            json.put("TrucksGUID",ToolsUtils.getString(DiaoduRenzheng3Activity.this,NEWGUID,""));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RetrofitUtils.getRetrofitService().setCarsInfo(
                "YZ",
                Config.BIND_TRUCK,
                json.toString())
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
                        Log.e("bindtruckerror",e.getMessage());
                        //ToolsUtils.getInstance().toastShowStr(DiaoduRenzheng3Activity.this,e.getMessage());
                    }

                    @Override
                    public void onNext(GetCodeBean getCodeBean) {
                        dialog.dismiss();
                        Log.e("bindtruck",getCodeBean.getErrorMsg());
                        if (getCodeBean.getErrorCode().equals("200"))
                        {
                            startActivity(new Intent(DiaoduRenzheng3Activity.this,CommitSuccessActivity.class));
                        }
                        //ToolsUtils.getInstance().toastShowStr(DiaoduRenzheng3Activity.this,getCodeBean.getErrorMsg());
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
                if (ContextCompat.checkSelfPermission(DiaoduRenzheng3Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(DiaoduRenzheng3Activity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
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
                if (ContextCompat.checkSelfPermission(DiaoduRenzheng3Activity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请READ_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(DiaoduRenzheng3Activity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
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
            Uri contentUri = FileProvider.getUriForFile(DiaoduRenzheng3Activity.this, Constant.APPLICATION_ID + ".fileProvider", tempFile);
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

                    String path = ToolsUtils.getRealFilePath(DiaoduRenzheng3Activity.this, Uri.fromFile(tempFile));
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
                               upload(ToolsUtils.getString(DiaoduRenzheng3Activity.this,NEWGUID,""));
                            }
                        });
                    }

                }
                break;
            case REQUEST_PICK:  //调用系统相册返回
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    String picPath = ToolsUtils.getRealFilePath(DiaoduRenzheng3Activity.this, uri);
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
                                upload(ToolsUtils.getString(DiaoduRenzheng3Activity.this,NEWGUID,""));
                            }
                        });

                    }
                }
                break;

        }
    }

    private void upload(String g) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("MemberGUID", g);

        builder.addFormDataPart("headimgurl", "avatar", RequestBody.create(MediaType.parse("image/png/jpg; charset=utf-8"), file));
        if (position == 2){
            builder.addFormDataPart("ImgType", "6");
        }else if (position == 3){
            builder.addFormDataPart("ImgType", "7");
        }else if (position == 4){
            builder.addFormDataPart("ImgType", "8");
        }
        RetrofitUtils.getRetrofitService().
                upload_avatar(builder.build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        //ToolsUtils.getInstance().toastShowStr(DiaoduRenzheng3Activity.this,"上传完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                       // ToolsUtils.getInstance().toastShowStr(DiaoduRenzheng3Activity.this, e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {

                            String info = responseBody.string();
                            try {
                                JSONObject json = new JSONObject(info);
                                String str = json.get("errorMsg").toString();
                                ToolsUtils.getInstance().toastShowStr(DiaoduRenzheng3Activity.this, str);
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
                                ToolsUtils.putString(DiaoduRenzheng3Activity.this,NEWGUID,guid);
                               // upload(guid);
                            }
                        });
            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.diaodu3_card_pic:
                position = 0;
                uploadImage();
                break;
            case R.id.diaodu3_with_card:
                position = 1;
                uploadImage();
                break;
            case R.id.diaodu3_car_face:
                position = 2;
                uploadImage();
                break;
            case R.id.diaodu3_car_back:
                position = 3;
                uploadImage();
                break;
            case R.id.diaodu3_car_content:
                position = 4;
                uploadImage();
                break;
            case R.id.diaodu3_car_ll:
                showCarPop();
                break;

        }
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

    String lenStr = "";
    String typeStr = "";
    private void showCarPop()
    {

        List<Map<String,String>> typeList = new ArrayList<Map<String,String>>();
        List<Map<String,String>> lengthList = new ArrayList<Map<String,String>>();
        String[] length = { "4.2米", "4.5米", "5米", "5.2米", "6.2米", "6.8米",
                "7.2米", "11.7米", "12.5米", "13米", "13.5米","14米","15米","16米","17米" };

        final String[] type = {"冷藏车","平板","高栏","箱式","保温","危险品","高低板"};

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
        SimpleAdapter lenthAdapter = new SimpleAdapter(DiaoduRenzheng3Activity.this,lengthList,R.layout.find_cartype_pop_item,new String[]{"length"},
                new int[]{R.id.gv_item_text});
        lenthGrid.setAdapter(lenthAdapter);

        final MyGridView typeGrid = (MyGridView) popView.findViewById(R.id.gridview_2);
        SimpleAdapter typeAdapter = new SimpleAdapter(DiaoduRenzheng3Activity.this,typeList,R.layout.find_cartype_pop_item,new String[]{"type"},
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
                    typeStr = (String)item.getText();
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
                //.get(2).setText(lenStr+"/"+typeStr);
                if (!typeStr.equals(""))
                {
                    mEdit.get(3).setText("");
                    mEdit.get(3).setText(typeStr);
                }
                if (!lenStr.equals(""))
                {
                    mEdit.get(4).setText("");
                    mEdit.get(4).setText(lenStr);
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
