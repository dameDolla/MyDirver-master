package com.app.gaolonglong.fragmenttabhost.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.utils.AMapUtil;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.app.gaolonglong.fragmenttabhost.view.route.DrivingRouteOverLay;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yanqi on 2017/10/9.
 */

public class RouteMapActivity extends BaseActivity implements RouteSearch.OnRouteSearchListener {
    private AMap aMap;
    private DriveRouteResult mDriveRouteResult;
    //private LatLonPoint mStartPoint = new LatLonPoint(39.942295, 116.335891);//起点，116.335891,39.942295
    //private LatLonPoint mEndPoint = new LatLonPoint(22.581832, 113.918597);//终点，116.481288,39.995576
    //终点，116.481288,39.995576
    @BindView(R.id.route_map)
    public MapView map;
    private RouteSearch mRouteSearch;
    private LatLonPoint mStartPoint;
    private LatLonPoint mEndPoint;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_map);
        ButterKnife.bind(this);
        map.onCreate(savedInstanceState);
        init();
    }



    private void init() {
        String fromsite = getIntent().getStringExtra("fromsitelatlng");
        String tosite = getIntent().getStringExtra("tositelatlng");
        String[] from = fromsite.split(",");
        String[] to = tosite.split(",");
        mStartPoint = new LatLonPoint(Double.parseDouble(from[1]), Double.parseDouble(from[0]));
        mEndPoint = new LatLonPoint(Double.parseDouble(to[1]), Double.parseDouble(to[0]));
        mRouteSearch = new RouteSearch(this);
        mRouteSearch.setRouteSearchListener(this);
        aMap = map.getMap();
        setfromandtoMarker();
        searchRouteResult();
    }

    private void setfromandtoMarker() {
        aMap.addMarker(new MarkerOptions()
                .position(new LatLng(39.942295, 116.335891))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.start)));
        aMap.addMarker(new MarkerOptions()
                .position(new LatLng(39.995576, 116.481288))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.end)));
    }

    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult() {

        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                mStartPoint, mEndPoint);

        RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, RouteSearch.DrivingDefault, null,
                null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
        mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult result, int i) {
        aMap.clear();// 清理地图上的所有覆盖物
        if (i == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    mDriveRouteResult = result;
                    final DrivePath drivePath = mDriveRouteResult.getPaths()
                            .get(0);
                    DrivingRouteOverLay drivingRouteOverlay = new DrivingRouteOverLay(
                            RouteMapActivity.this, aMap, drivePath,
                            mDriveRouteResult.getStartPos(),
                            mDriveRouteResult.getTargetPos(), null);
                    drivingRouteOverlay.setNodeIconVisibility(false);//设置节点marker是否显示
                    drivingRouteOverlay.setIsColorfulline(true);//是否用颜色展示交通拥堵情况，默认true
                    drivingRouteOverlay.removeFromMap();
                    drivingRouteOverlay.addToMap();
                    drivingRouteOverlay.zoomToSpan();
                    //mBottomLayout.setVisibility(View.VISIBLE);
                    int dis = (int) drivePath.getDistance();
                    int dur = (int) drivePath.getDuration();
                    String des = AMapUtil.getFriendlyTime(dur) + "(" + AMapUtil.getFriendlyLength(dis) + ")";
                    /*mRotueTimeDes.setText(des);
                    mRouteDetailDes.setVisibility(View.VISIBLE);
                    int taxiCost = (int) mDriveRouteResult.getTaxiCost();
                    mRouteDetailDes.setText("打车约"+taxiCost+"元");
                    mBottomLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(RouteMapActivity.this,
                                    DriveRouteDetailActivity.class);
                            intent.putExtra("drive_path", drivePath);
                            intent.putExtra("drive_result",
                                    mDriveRouteResult);
                            startActivity(intent);
                        }
                    });*/
                } else if (result != null && result.getPaths() == null) {
                    ToolsUtils.getInstance().toastShowStr(RouteMapActivity.this, "对不起，没有搜索到相关数据");
                }

            } else {
                ToolsUtils.getInstance().toastShowStr(RouteMapActivity.this, "对不起，没有搜索到相关数据");
            }
        } else {
            ToolsUtils.getInstance().toastShowStr(RouteMapActivity.this, i + "");
        }
    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }
}
