
package darroo.com.PCall.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast提示工具类
 * by Hankkin  at 2015年4月16日 14:08:24
 */
public class ToastUtils {
	/**
	 * 提示字符串
	 * short Toast
	 * @param context
	 * @param text
	 */
	public static void showToast(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 提示字符串
	 * short Toast
	 * @param context
	 * @param text
	 */
	public static void showLToast(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
	/**
	 * 提示根据ResId关联字符串
	 * short Toast
	 * by:李斐 at:2015年4月30日 14:39:41
	 * @param context
	 * @param resId
	 */
	public static void showToast(Context context, int resId) {
		Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 提示根据ResId关联字符串
	 * 时常long	Toast
	 * by:李斐 at:2015年4月30日 14:39:41
	 * @param context
	 * @param resId
	 */
	public static void showLToast(Context context, int resId) {
		Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
	}
}
