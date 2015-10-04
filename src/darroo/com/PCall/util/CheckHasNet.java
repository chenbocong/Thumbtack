package darroo.com.PCall.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * 是否有网络检查
 * by Hankkin at:2015年7月15日 23:22:07
 */
public class CheckHasNet {

    /**
     * 类标识TAG
     */
    private static final String TAG = CheckHasNet.class.getSimpleName();

    /** 网络连通性服务名 */


    /**
     * 无网络
     */
    private static final int TYPE_NONETWORK = 0;
    /**
     * WIFI网络
     */
    private static final int TYPE_WIFI = 1;
    /**
     * 手机网络
     */
    private static final int TYPE_MOBILE = 2;
    /**
     * 其他网络
     */
    private static final int TYPE_OTHER = 3;

    public static int checkNet(Context context) {

        ConnectivityManager connectionManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();

        if (networkInfo != null) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {

                return TYPE_WIFI; // 返回1是 WIFI网络
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
               /* info.getSubtype() 取值列表如下：
                NETWORK_TYPE_CDMA 网络类型为CDMA
                NETWORK_TYPE_EDGE 网络类型为EDGE
                NETWORK_TYPE_EVDO_0 网络类型为EVDO0
                NETWORK_TYPE_EVDO_A 网络类型为EVDOA
                NETWORK_TYPE_GPRS 网络类型为GPRS
                NETWORK_TYPE_HSDPA 网络类型为HSDPA
                NETWORK_TYPE_HSPA 网络类型为HSPA
                NETWORK_TYPE_HSUPA 网络类型为HSUPA
                NETWORK_TYPE_UMTS 网络类型为UMTS
                联通的3G为UMTS或HSDPA，移动和联通的2G为GPRS或EDGE，电信的2G为CDMA，电信的3G为EVDO*/
                return TYPE_MOBILE; // 返回 2是移动互联网（）
            } else {

                return TYPE_OTHER; // 返回3是 未知网络
            }
        } else {
            return TYPE_NONETWORK;
        }
    }

    /**
     * 判断是否为移动网络
     * by:Hankkin at:2015年7月15日 23:22:34
     *
     * @param context 上下文
     * @return 是否使用流量网络
     */
    public static boolean isMobileNet(Context context) {
        if (checkNet(context) == TYPE_MOBILE) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取手机IP
     * by:Hankkin at:2015年7月15日 23:22:34
     *
     * @return
     */
    public static String getPhoneIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        //if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet6Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 检查是否有网络.
     *
     * @param context
     * @return
     */
    public static boolean isNetWorkOk(Context context) {
        if (checkNet(context) == 0) {
            return false;
        } else {
            return true;
        }

    }
}
