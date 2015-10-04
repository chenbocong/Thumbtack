package darroo.com.PCall.sharepreference;

import android.content.Context;

public class MyPreferencesManager {
    /**
     * DB是否创建 KEY.
     */
    private static final String DB_IS_CREATED_KEY = "db_has_created";

    public static boolean setDBIsCreatedFlag(Context context, boolean value) {
        return MySharedPrefsAccesser.saveData(context, DB_IS_CREATED_KEY,
                value);
    }

    public static boolean getDBIsCreatedFlag(Context context) {
        return MySharedPrefsAccesser.getBooleanData(context,
                DB_IS_CREATED_KEY);
    }


    public static int getNeedAssistorLocationX(Context context) {
        return MySharedPrefsAccesser.getIntData(context, "needAssistorX");
    }

    public static int getNeedAssistorLocationY(Context context) {
        return MySharedPrefsAccesser.getIntData(context, "needAssistorY");
    }

    public static boolean setNeedAssistorLocationX(Context context, int value) {
        return MySharedPrefsAccesser.saveData(context, "needAssistorX", value);
    }

    public static boolean setNeedAssistorLocationY(Context context, int value) {
        return MySharedPrefsAccesser.saveData(context, "needAssistorY", value);
    }

    public static boolean getNeedHasInited(Context context) {
        return MySharedPrefsAccesser.getBooleanData(context, "needAssistorInited");
    }

    public static boolean setNeedHasInit(Context context, boolean value) {
        return MySharedPrefsAccesser.saveData(context, "needAssistorInited", value);
    }


    public static String getVersionNameInConfig(Context context) {
        return MySharedPrefsAccesser.getStringData(context, "versionName");
    }

    public static boolean setVersionNameInConfig(Context context, String value) {
        return MySharedPrefsAccesser.saveData(context, "versionName", value);
    }


    // add qq 第三方登录是否可用
    public static Boolean setQQLoginEnable(Context context, boolean value) {
        return MySharedPrefsAccesser
                .saveData(context, "qq_login_enable", value);
    }

    public static Boolean getQQLoginEnable(Context context) {
        return MySharedPrefsAccesser.getBooleanData(context, "qq_login_enable");
    }

    // add qq 第三方登录QQappid
    public static Boolean setQQLoginAppId(Context context, String qqAppId) {
        return MySharedPrefsAccesser.saveData(context, "qq_login_qqAppId",
                qqAppId);
    }

    public static String getQQLoginAppId(Context context) {
        return MySharedPrefsAccesser.getStringData(context, "qq_login_qqAppId");
    }

    // add qqWeibo 是否开启
    public static Boolean setQQWeiboEnable(Context context, boolean value) {
        return MySharedPrefsAccesser
                .saveData(context, "qq_weibo_enable", value);
    }

    public static Boolean getQQWeiboEnable(Context context) {
        return MySharedPrefsAccesser.getBooleanData(context, "qq_weibo_enable");
    }

    // 微博相关
    // sina
    public static boolean setSinaAppKey(Context context, String value) {
        return MySharedPrefsAccesser.saveData(context, "sina_app_key", value);
    }

    public static boolean setSinaAppSecret(Context context, String value) {
        return MySharedPrefsAccesser
                .saveData(context, "sina_app_secret", value);
    }

    public static boolean setSinaRedirectUrl(Context context, String value) {
        return MySharedPrefsAccesser.saveData(context, "sina_redirect_url",
                value);
    }

    public static String getSinaAppKey(Context context) {
        return MySharedPrefsAccesser.getStringData(context, "sina_app_key");
    }

    public static String getSinaAppSecret(Context context) {
        return MySharedPrefsAccesser.getStringData(context, "sina_app_secret");
    }

    public static String getSinaRedirectUrl(Context context) {
        return MySharedPrefsAccesser
                .getStringData(context, "sina_redirect_url");
    }

    // qq
    public static boolean setQQAppKey(Context context, String value) {
        return MySharedPrefsAccesser.saveData(context, "qq_app_key", value);
    }

    public static boolean setQQAppSecret(Context context, String value) {
        return MySharedPrefsAccesser.saveData(context, "qq_app_secret", value);
    }

    public static boolean setQQRedirectUrl(Context context, String value) {
        return MySharedPrefsAccesser
                .saveData(context, "qq_redirect_url", value);
    }

    public static String getQQAppKey(Context context) {
        return MySharedPrefsAccesser.getStringData(context, "qq_app_key");
    }

    public static String getQQAppSecret(Context context) {
        return MySharedPrefsAccesser.getStringData(context, "qq_app_secret");
    }

    public static String getQQRedirectUrl(Context context) {
        return MySharedPrefsAccesser.getStringData(context, "qq_redirect_url");
    }

    // weichat微信
    public static boolean setWeichatAppKey(Context context, String value) {
        return MySharedPrefsAccesser.saveData(context, "weichat_key", value);
    }

    public static String getWeiChatAppKey(Context context) {
        return MySharedPrefsAccesser.getStringData(context, "weichat_key");
    }

    public static String getPwd(Context context) {
        return MySharedPrefsAccesser.getStringData(context, "pwd");
    }

    public static boolean setPwd(Context context, String pwd) {
        return MySharedPrefsAccesser.saveData(context, "pwd", pwd);
    }

    public static boolean setUsername(Context context, String uname) {
        return MySharedPrefsAccesser.saveData(context, "uname", uname);
    }

    public static String getUsername(Context context) {
        return MySharedPrefsAccesser.getStringData(context, "uname");
    }

    public static boolean setLogined(Context context, boolean isLogined) {
        return MySharedPrefsAccesser.saveData(context, "isLogined", isLogined);
    }

    public static boolean isLogined(Context context) {
        return MySharedPrefsAccesser.getBooleanData(context, "isLogined");
    }

    public static boolean setUid(Context context, String uid) {
        return MySharedPrefsAccesser.saveData(context, "uid", uid);
    }

    public static String getUid(Context context) {
        return MySharedPrefsAccesser.getStringData(context, "uid");
    }

    // 注销用户
    public static void logoutClean(Context context) {
        // MySharedPrefsAccesser.deleteKeys(context, "uid", "pwd", "uname",
        // "isLogined",IME_KEY,DEVICE_ID_KEY,"aliasSuccess");
        MySharedPrefsAccesser.deleteKeys(context, "uid", "pwd", "uname",
                "isLogined", "needAssistorX", "needAssistorY");
    }

    // 程序退出时清除部分SharedPreference
    public static void exitClean(Context context) {
        MySharedPrefsAccesser.deleteKeys(context, "sina_app_key",
                "sina_app_secret", "sina_redirect_url", "qq_app_key",
                "qq_app_secret", "qq_redirect_url", "can_wappay", "can_embed",
                "can_comepay", "can_return", "can_pay_on_delivery",
                "support_shop_delivery");
    }
}
