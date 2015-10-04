package darroo.com.PCall.util;

import android.app.Activity;
import android.content.SharedPreferences;
import darroo.com.PCall.application.PCallApplication;
import lidroid.xutils.HttpUtils;
import lidroid.xutils.exception.HttpException;
import lidroid.xutils.http.RequestParams;
import lidroid.xutils.http.ResponseInfo;
import lidroid.xutils.http.callback.RequestCallBack;
import lidroid.xutils.http.client.HttpRequest;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Hankkin on 2015/8/2.
 */
public class Service {


    /**
     * 创建鹰眼服务,并将服务ID保存到本地
     * by Hankkin at:2015年7月26日 22:57:07
     */
    public static void creatService(final Activity activity) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("ak", "FHBqahWRkIYrKZtqr39UQ0CV");
//        params.addBodyParameter("ak", "jedhM2SwGLR9zylx0dTZ217h");
        params.addBodyParameter("name", "" + Convert.phoneImei + "");
        params.addBodyParameter("desc", "这是一款....apk");
        params.addBodyParameter("type", 0 + "");
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.POST,
                "http://api.map.baidu.com/trace/v1/service/create",
                params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        if (responseInfo != null) {
                            try {
                                JSONObject object = new JSONObject(responseInfo.result);
                                String serviceId = object.getString("service_id");
                                SharedPreferences sp = activity.getSharedPreferences("SERVICE_ID", activity.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("service_id", serviceId);
                                editor.commit();
                                Convert.serviceID = serviceId;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        ToastUtils.showLToast(activity, msg);
                    }
                });
    }


//
//    private void detailService(final Activity activity){
//        HttpUtils httpUtils = new HttpUtils();
//        httpUtils.send(HttpRequest.HttpMethod.GET,
//                "http://api.map.baidu.com/trace/v1/service/detail?ak=jedhM2SwGLR9zylx0dTZ217h&&service_id=100808&&",
//                new RequestCallBack<String>() {
//                    @Override
//                    public void onSuccess(ResponseInfo<String> responseInfo) {
//                        if (responseInfo!=null){
//                            ToastUtils.showLToast(activity, responseInfo.toString());
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(HttpException error, String msg) {
//                        ToastUtils.showLToast(activity,msg);
//                    }
//                });
//    }
}
