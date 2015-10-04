package darroo.com.PCall.sharepreference;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPrefsAccesser {
    private static final String PREFERENCE_NAME = "SYSTEM";

    static SharedPreferences mSP = null;

    static boolean saveData(Context context, String key, Object value) {
        if (context == null || key == null || value == null) {
            return false;
        }
        if (mSP == null) {
            mSP = context.getSharedPreferences(PREFERENCE_NAME,
                    Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = mSP.edit();

        if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else {
            return false;
        }
        return editor.commit();
    }

    static String getStringData(Context context, String key) {
        String value = null;
        if (context == null || key == null) {
            return value;
        }
        if (mSP == null) {
            mSP = context.getSharedPreferences(PREFERENCE_NAME,
                    Context.MODE_PRIVATE);
        }
        value = mSP.getString(key, null);
        return value;

    }

    static boolean getBooleanData(Context context, String key) {
        boolean value = false;
        if (context == null || key == null) {
            return value;
        }
        if (mSP == null) {
            mSP = context.getSharedPreferences(PREFERENCE_NAME,
                    Context.MODE_PRIVATE);
        }
        value = mSP.getBoolean(key, false);
        return value;

    }

    static int getIntData(Context context, String key) {
        int value = -1;
        if (context == null || key == null) {
            return value;
        }
        if (mSP == null) {
            mSP = context.getSharedPreferences(PREFERENCE_NAME,
                    Context.MODE_PRIVATE);
        }
        value = mSP.getInt(key, -1);
        return value;

    }

    static long getLongData(Context context, String key) {
        long value = -1;
        if (context == null || key == null) {
            return value;
        }
        if (mSP == null) {
            mSP = context.getSharedPreferences(PREFERENCE_NAME,
                    Context.MODE_PRIVATE);
        }
        value = mSP.getLong(key, 0);
        return value;

    }

    static boolean deleteKey(Context context, String key) {
        if (context == null || key == null) {
            return false;
        }
        if (mSP == null) {
            mSP = context.getSharedPreferences(PREFERENCE_NAME,
                    Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = mSP.edit();
        editor.remove(key);
        return editor.commit();
    }

    static boolean deleteKeys(Context context, String... keys) {
        if (context == null || keys == null) {
            return false;
        }
        if (mSP == null) {
            mSP = context.getSharedPreferences(PREFERENCE_NAME,
                    Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = mSP.edit();
        for (int i = 0; i < keys.length; i++) {
            editor.remove(keys[i]);
        }
        return editor.commit();
    }

}
