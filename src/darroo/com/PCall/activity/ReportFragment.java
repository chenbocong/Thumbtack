package darroo.com.PCall.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.model.LatLng;
import darroo.com.PCall.R;
import darroo.com.PCall.application.PCallApplication;
import darroo.com.PCall.util.CheckHasNet;


/**
 * Created by Hankkin on 2015/7/13.
 * 上报Fragment
 */
public class ReportFragment extends Fragment implements View.OnClickListener {

    private Marker markerA;
    private final int WAYS = 1;

    /*定位信息文本*/
//    private TextView tvLocInfo;
    /*定位相关*/
    private LocationClient mLocClient;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private BitmapDescriptor mCurrentMarker;
    private MapView mMapView;
    private BaiduMap mBaiduMap;

    public MyLocationListenner myListener = new MyLocationListenner();
    /* 是否首次定位*/
    private boolean isFirstLoc = true;

    /*上报按钮*/
    private Button btnReport;
    private InfoWindow mInfoWindow;//点击标记显示的view
    /*重新定位按钮*/
    private Button btnLoc;
    /*加载数据对话框*/
    private ProgressDialog dialog;


    private android.os.Handler handler;

    private String address;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SDKInitializer.initialize(getActivity().getApplicationContext());
        return inflater.inflate(R.layout.fragment_report, container, false);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViews();


        /**
         * 点击当前位置图标弹出位置信息popwindow
         * by Hankkin at:2015年8月4日 21:15:09
         */
        mBaiduMap.setOnMyLocationClickListener(new BaiduMap.OnMyLocationClickListener() {
            @Override
            public boolean onMyLocationClick() {
                TextView tv = new TextView(getActivity().getApplicationContext());
                tv.setBackgroundResource(R.drawable.popup);
                tv.setText(address);
                tv.setTextSize(16);
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mBaiduMap.hideInfoWindow();
                        }
                    });
                    LatLng ll = new LatLng(PCallApplication.mCurrentLantitude, PCallApplication.mCurrentLongitude);
                    mInfoWindow = new InfoWindow(tv, ll, -47);
                    mBaiduMap.showInfoWindow(mInfoWindow);
                return false;
            }
        });


//        handler = new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                double lat = msg.getData().getDouble("lat");
//                double lon = msg.getData().getDouble("lon");
//                if(lat!=0&&lon!=0){
//                    LatLng ll = new LatLng(lat, lon);
//                    MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
//                    mBaiduMap.animateMapStatus(u);
//                }
//                dialog.dismiss();
//            }
//
//        };

    }

    private void initViews() {
        btnLoc = (Button) getActivity().findViewById(R.id.btn_report_locRepeat);
        btnLoc.setOnClickListener(this);
        btnReport = (Button) getActivity().findViewById(R.id.btn_report);
        btnReport.setOnClickListener(this);
        mMapView = (MapView) getActivity().findViewById(R.id.bmapView);
        // 开启定位图层
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        //设置初始化地图精度
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);//zoom越大越精确，一般14、16就可以了
        mBaiduMap.setMapStatus(msu);


        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_marka);
        mBaiduMap
                .setMyLocationConfigeration(new MyLocationConfiguration(
                        mCurrentMode, true, null));
//        btnLocWay.setText("普通");
        // 地图初始化

        // 定位初始化
        if (CheckHasNet.isMobileNet(getActivity().getApplicationContext()) || CheckHasNet.isNetWorkOk(getActivity().getApplicationContext())) {
            mLocClient = new LocationClient(getActivity());
            mLocClient.registerLocationListener(myListener);
            LocationClientOption option = new LocationClientOption();
            option.setOpenGps(true);// 打开gps
            option.setCoorType("bd09ll"); // 设置坐标类型
            option.setScanSpan(1000);
            option.setAddrType("all");  //设置获取位置信息，若不设置address为null
            mLocClient.setLocOption(option);
            mLocClient.start();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_report:
                Intent intent = new Intent(getActivity().getApplicationContext(), ReportDetailActivity.class);
                startActivity(intent);

                break;
            case R.id.btn_report_locRepeat: //重新定位按钮
//                dialog = ProgressDialog.show(getActivity(),"","正在加载数据");
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                       new LocationThread();
//                    }
//                }).start();
                LatLng ll = new LatLng(PCallApplication.mCurrentLantitude, PCallApplication.mCurrentLongitude);
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);
                break;
        }
    }



    /**
     * 定位SDK监听函数
     * by Hankkin at:2015年7月14日 21:28:12
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null)
                return;

            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            address = location.getAddrStr();
            PCallApplication.mCurrentLantitude = location.getLatitude();
            PCallApplication.mCurrentLongitude = location.getLongitude();
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);
            }
            StringBuffer sb = new StringBuffer(256);
            if (location.getLocType() == BDLocation.TypeGpsLocation) {
                sb.append(location.getCity());
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                sb.append(location.getAddrStr());
            }
            PCallApplication.locInfo = sb.toString();
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    private class LocationThread implements Runnable{

        @Override
        public void run() {

            LatLng ll = new LatLng(PCallApplication.mCurrentLantitude,PCallApplication.mCurrentLongitude);
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
            mBaiduMap.animateMapStatus(u);

            Message msg = new Message();
            Bundle data = new Bundle();
            data.putDouble("lat", PCallApplication.mCurrentLantitude);
            data.putDouble("lon", PCallApplication.mCurrentLongitude);
            msg.setData(data);
            handler.sendMessage(msg);
        }
    }

    @Override
    public void onDestroy() {
        // 退出时销毁定位
        if (null != mLocClient) {
            mLocClient.stop();
        }
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }


}