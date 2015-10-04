
package darroo.com.PCall.util;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import darroo.com.PCall.R;
import darroo.com.PCall.application.PCallApplication;
import darroo.com.PCall.sharepreference.MyPreferencesManager;
import darroo.com.PCall.view.FloatView;


/**
 * @author Hankkin
 * @ClassName: NeedAssistor
 * @Description: 一键求助悬浮按钮的初始化、remove和点击事件方法
 * @date 2015-04-13
 */
public class HelpAssistor {
	private static WindowManager windowManager = null;
	private static LayoutParams windowManagerParams = null;
	private static FloatView floatView = null;

	public static void createView(final Context context) {
		if (floatView == null)
			floatView = new FloatView(context);
		floatView.setBackgroundResource(R.drawable.help_assistor_selector); // 这里简单的用自带的icon来做演示
		// 获取WindowManager
		windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		// 设置LayoutParams(全局变量）相关参数
		windowManagerParams = ((PCallApplication) context).getWindowParams();
		windowManagerParams.type = LayoutParams.TYPE_PHONE; // 设置window type
		windowManagerParams.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明
		// 设置Window flag
		windowManagerParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
				| LayoutParams.FLAG_NOT_FOCUSABLE;
        /*
		 * 注意，flag的值可以为： LayoutParams.FLAG_NOT_TOUCH_MODAL 不影响后面的事件
		 * LayoutParams.FLAG_NOT_FOCUSABLE 不可聚焦 LayoutParams.FLAG_NOT_TOUCHABLE
		 * 不可触摸
		 */
		// 调整悬浮窗口至左上角，便于调整坐标
		windowManagerParams.gravity = Gravity.LEFT | Gravity.TOP;
		// 以屏幕右下角为原点，设置x、y初始值
		if (!MyPreferencesManager.getNeedHasInited(context)) {
			int w = windowManager.getDefaultDisplay().getWidth();
			int h = windowManager.getDefaultDisplay().getHeight();
			windowManagerParams.x = w;
			windowManagerParams.y = h;
			MyPreferencesManager.setNeedAssistorLocationX(context, w);
			MyPreferencesManager.setNeedAssistorLocationY(context, h);
			MyPreferencesManager.setNeedHasInit(context, true);
		} else {
			windowManagerParams.x = MyPreferencesManager
					.getNeedAssistorLocationX(context);
			windowManagerParams.y = MyPreferencesManager
					.getNeedAssistorLocationY(context);
		}
/*old 小助手初始位置（0,0） --start--*/
//		int w=windowManager.getDefaultDisplay().getWidth();
//		int h=windowManager.getDefaultDisplay().getHeight();
//		windowManagerParams.x = MyPreferencesManager
//				.getNeedAssistorLocationX(context);
//		windowManagerParams.y = MyPreferencesManager
//				.getNeedAssistorLocationY(context);
//		windowManagerParams.x = w;
//		windowManagerParams.y = h;
/*old 小助手初始位置（0,0） --end--*/
		// 设置悬浮窗口长宽数据
		windowManagerParams.width = LayoutParams.WRAP_CONTENT;
		windowManagerParams.height = LayoutParams.WRAP_CONTENT;
		// 显示myFloatView图像
		windowManager.addView(floatView, windowManagerParams);
	}

	public static void setOnClickListener(OnClickListener listener) {
		if (floatView != null) {
			floatView.setOnClickListener(listener);
		}
	}

	public static void removeNeedAssistor() {
		windowManager.removeView(floatView);
	}
}
