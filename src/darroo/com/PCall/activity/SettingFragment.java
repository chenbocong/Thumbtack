package darroo.com.PCall.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.model.LatLng;
import com.zxing.activity.CaptureActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import darroo.com.PCall.R;
import darroo.com.PCall.application.PCallApplication;
import darroo.com.PCall.util.Convert;
import darroo.com.PCall.util.ToastUtils;
import lidroid.xutils.HttpUtils;
import lidroid.xutils.exception.HttpException;
import lidroid.xutils.http.RequestParams;
import lidroid.xutils.http.ResponseInfo;
import lidroid.xutils.http.callback.RequestCallBack;
import lidroid.xutils.http.client.HttpRequest;

/**
 * Created by tangyangkai on 2015/7/27. 设置Fragment
 */
public class SettingFragment extends Fragment implements View.OnClickListener {

	private Button saoyisao;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		SDKInitializer.initialize(getActivity().getApplicationContext());
		return inflater.inflate(R.layout.fragment_setting, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initviews();
	}

	private void initviews() {
//		saoyisao = (Button) getActivity().findViewById(R.id.saoyisao);
//		saoyisao.setOnClickListener(this);
		
	}
	
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.saoyisao:
			Intent intent = new Intent(getActivity().getApplicationContext(),CaptureActivity.class);
			startActivity(intent);
			break;

		}
	}
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
