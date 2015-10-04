package darroo.com.PCall.application;

import android.app.Application;
import android.view.WindowManager;

/**
 * Created by Hankkin on 2015/7/23.
 */
public class PCallApplication extends Application {
    //need小助手悬浮窗需要用的权限
    private WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();

    public WindowManager.LayoutParams getWindowParams() {
        return windowParams;
    }

    public static String locInfo;

    /*获取的经纬度*/
    public static double mCurrentLantitude;
    public static double mCurrentLongitude;
}
