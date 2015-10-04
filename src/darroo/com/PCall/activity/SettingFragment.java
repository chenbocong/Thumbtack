package darroo.com.PCall.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import darroo.com.PCall.R;

/**
 * Created by tangyangkai on 2015/7/27.
 * 设置Fragment
 */
public class SettingFragment extends Fragment {

    private WebView webSetting;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initviews();
    }

    private void initviews() {
        webSetting = (WebView) getActivity().findViewById(R.id.webView);
        WebSettings webSettings = webSetting.getSettings();
        webSettings.setJavaScriptEnabled(true);//设置webview可以响应js代码功能
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        //扩大比例的缩放
        webSettings.setUseWideViewPort(true);
        //设置WebView属性，能够执行Javascript
        webSetting.getSettings().setJavaScriptEnabled(true);
        webSetting.loadUrl("http://buluo.qq.com");
        webSetting.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl("http://buluo.qq.com");
                return true;
            }
        });
    }
}
