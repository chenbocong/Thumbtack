package darroo.com.PCall.activity;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.baidu.mapapi.SDKInitializer;
import darroo.com.PCall.R;
import darroo.com.PCall.application.PCallApplication;
import darroo.com.PCall.dialog.AlterDialog;
import darroo.com.PCall.util.Convert;
import darroo.com.PCall.util.HelpAssistor;
import darroo.com.PCall.util.ToastUtils;
import lidroid.xutils.HttpUtils;
import lidroid.xutils.exception.HttpException;
import lidroid.xutils.http.RequestParams;
import lidroid.xutils.http.ResponseInfo;
import lidroid.xutils.http.callback.RequestCallBack;
import lidroid.xutils.http.client.HttpRequest;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Hankkin on 2015/7/13.
 * 求助Fragment
 */
public class HelpFragment extends Fragment implements View.OnClickListener {

    public static Handler handler;

    private Button btnHelp;
    private Button btnReport;

    private CreateTraceThread createTraceThread;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SDKInitializer.initialize(getActivity().getApplicationContext());
        return inflater.inflate(R.layout.fragment_help, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        HelpAssistor.createView(getActivity().getApplicationContext());
//        HelpAssistor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlterDialog.showAlertView(getActivity(), "温馨提示",
//                        "您确定要一键求助吗？", "确定", null,
//                        new String[]{"取消"}, null).show();
//            }
//        });
        initViews();

    }

    /**
     * 初始化组件
     * by Hankkin at:2015年7月15日 22:39:40
     * 增加网络判断
     * by Hankkin at:2015年7月15日 23:25:51
     */
    private void initViews() {
        btnReport = (Button) getActivity().findViewById(R.id.btn_report);
        btnReport.setOnClickListener(this);
        btnHelp = (Button) getActivity().findViewById(R.id.btn_help);
        btnHelp.setOnClickListener(this);
        createTraceThread = new CreateTraceThread();
    }


    /**
     * 一键求助小助手创建、点击事件
     * by Hankkin at:2015年7月14日 22:08:34
     */
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_help:
                createTraceThread.start();
                btnHelp.setEnabled(false);
                break;
            case R.id.btn_report:
                Intent intent = new Intent(getActivity().getApplicationContext(), ReportDetailActivity.class);
                startActivity(intent);

                break;
        }
    }


    /**
     * 创建轨迹
     * by Hankkin at:2015-8-2 18:18:13
     */
    private void createTrack(){
        RequestParams params = new RequestParams();
        params.addBodyParameter("ak","jedhM2SwGLR9zylx0dTZ217h");
        params.addBodyParameter("service_id", Convert.serviceID);
        params.addBodyParameter("latitude", PCallApplication.mCurrentLantitude+"");
        params.addBodyParameter("longitude",PCallApplication.mCurrentLongitude+"");
        Log.e("经纬度:",PCallApplication.mCurrentLantitude+"\n"+PCallApplication.mCurrentLongitude);
        params.addBodyParameter("coord_type", 3 + "");
        params.addBodyParameter("loc_time",System.currentTimeMillis()/1000+"");
        params.addBodyParameter("track_name",Convert.phoneImei+"");
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST,
                "http://api.map.baidu.com/trace/v1/track/create",
                params, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        if (responseInfo!=null){
                            try {
                                JSONObject json = new JSONObject(responseInfo.result);
                                String message = json.optString("message");
                                ToastUtils.showLToast(getActivity().getApplicationContext(),message);
                                Log.e(">>>>>>>>>>>>>>>>>>>>>>>",message);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        ToastUtils.showLToast(getActivity().getApplicationContext(),msg.toString());
                    }
                });
    }



    /**
     * 创建轨迹线程
     * by Hankkin at:2015年8月2日16:25:38
     */
    private class CreateTraceThread extends Thread{
        @Override
        public void run() {
            while (true){
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                createTrack();
            }

        }
    }

}

