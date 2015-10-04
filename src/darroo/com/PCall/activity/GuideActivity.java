package darroo.com.PCall.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.widget.ProgressBar;
import android.widget.Toast;
import darroo.com.PCall.R;
import darroo.com.PCall.application.PCallApplication;
import darroo.com.PCall.util.CheckHasNet;
import darroo.com.PCall.util.Convert;
import darroo.com.PCall.util.Service;
import darroo.com.PCall.util.ToastUtils;
import lidroid.xutils.HttpUtils;
import lidroid.xutils.exception.HttpException;
import lidroid.xutils.http.ResponseInfo;
import lidroid.xutils.http.callback.RequestCallBack;
import lidroid.xutils.http.client.HttpRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hannkin on 2015/7/19.
 */
public class GuideActivity extends Activity implements DialogInterface.OnCancelListener {

    private GuideActivity instance;


    private Handler handler;

    private Handler tempHandler;

    /*服务器上的服务名字--根据手机唯一标识创建的*/
    List<String> names = new ArrayList<String>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.guide);


        /**
         * 获取服务Id后接受通知判断是否需要创建服务
         * by Hankkin at:2015年8月4日 21:57:38
         */
        tempHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==0){
                    for (int i = 0; i < names.size(); i++) {
                        if (Convert.phoneImei.equals(names.get(i))) {
                            return;
                        } else {
                            Service.creatService(instance);
                        }
                    }
                }
                super.handleMessage(msg);
            }
        };

        /**
         * handler接受通知
         * by Hankkin at:2015-8-2 21:56:51
         */
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Intent intent = new Intent(instance, LoginActivity.class);
                startActivity(intent);
                finish();
                if (msg.what == 0) {
                    Toast.makeText(GuideActivity.this, "当前无网络连接，无法进行定位，请检查网络设置", Toast.LENGTH_LONG).show();
                }
                if (msg.what == 1) {    //有网络，第一次进来保存标识，若服务器上有手机标识的服务则不创建
                    savePhoneImei();
                    Convert.phoneImei = getPhoneImei();
                    if (getServiceID() == null || "".equals(getServiceID())) {
                        getServiceNamesFromHttp();
                    } else {
                        Convert.serviceID = getServiceID();
                    }
                }
                if (msg.what == 2) { //有网络，不是第一次进来，有服务ID不创建服务
                    Convert.phoneImei = getPhoneImei();
                    if (getServiceID() != null && !"".equals(getServiceID())) {
                        Convert.serviceID = getServiceID();
                    } else {
                        getServiceNamesFromHttp();
                    }
                }
            }


        };

        /**
         * 开启线程检查网络
         * by Hankkin at:2015年8月2日16:48:35
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message msg = new Message();
                String imei = getPhoneImei();
                String imeiSP = getPhoneImeiFromSP();
                int net = CheckHasNet.checkNet(instance);
                if (net == 0) {
                    msg.what = 0;
                } else {
                    if ("".equals(imeiSP)) { //本地唯一标识为空时，证明为第一次，保存标识
                        msg.what = 1;
                    } else {
                        msg.what = 2;   //不为空时则不是第一次
                    }
                }
                handler.sendMessage(msg);

            }
        }).start();


    }

    /**
     * 检查GPS连接
     * by tangyangkai at 2015年7月27日20:44
     */
    private void checkGPS() {
        LocationManager locationManager
                = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (gps == false) {
            Toast.makeText(GuideActivity.this, "GPS未打开，进行精确定位请打开GPS", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 获取手机唯一标示
     * by Hankkin at:2015-8-2 16:55:38
     * @return
     */
    private String getPhoneImei() {
        String imei = null;
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        imei = telephonyManager.getDeviceId();
        return imei;
    }

    /**
     * 保存手机唯一标识
     * by Hankkin at:2015-8-2 17:06:50
     */
    private void savePhoneImei() {
        SharedPreferences sp = getSharedPreferences("PHONEIMEI", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("IMEI", getPhoneImei());
        editor.commit();
    }

    /**
     * 从内存中获取手机唯一标识
     * by Hankkin  at:2015-8-2 17:10:05
     *
     * @return
     */
    private String getPhoneImeiFromSP() {
        SharedPreferences sp = getSharedPreferences("PHONEIMEI", MODE_PRIVATE);
        String imei = sp.getString("IMEI", "");
        return imei;
    }


    /**
     * 从内存中获取服务ID
     * by Hankkin at:2015年8月3日 20:40:14
     *
     * @return
     */
    private String getServiceID() {
        SharedPreferences sp = instance.getSharedPreferences("SERVICE_ID", Activity.MODE_PRIVATE);
        String serviceID = sp.getString("service_id", "");
        return serviceID;
    }

    /**
     * 列举服务器上的服务名字
     * by Hankkin at:2015年8月3日 20:46:00
     */
    private void getServiceNamesFromHttp() {
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET,
                "http://api.map.baidu.com/trace/v1/service/list?ak=jedhM2SwGLR9zylx0dTZ217h&&name=" + Convert.phoneImei + "&&page_size=" + 100 + "",
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        if (responseInfo != null) {
                            try {
                                JSONObject object = new JSONObject(responseInfo.result);
                                int size = object.optInt("size");
                                JSONArray servicesArray = object.optJSONArray("services");
                                for (int i = 0; i < size; i++) {
                                    JSONObject service = (JSONObject) servicesArray.get(i);
                                    String name = service.optString("name");
                                    names.add(name);
                                }
                                Message msg = new Message();
                                msg.what = 0;
                                tempHandler.sendMessage(msg);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        ToastUtils.showLToast(instance, msg);
                    }
                });
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {

    }
}