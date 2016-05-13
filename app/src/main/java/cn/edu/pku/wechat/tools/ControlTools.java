package cn.edu.pku.wechat.tools;

import java.lang.reflect.Field;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.view.ViewConfiguration;

public class ControlTools {
	/**
	 * force to show overflow menu in actionbar for android 4.4 below
	 * 
	 * @param context
	 */
	public static void getOverflowMenu(Context context) {
		try {
			ViewConfiguration config = ViewConfiguration.get(context);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 创建提示框 2键
	 * 
	 * @param act
	 *            容器activity
	 * @param title
	 *            提示框标题
	 * @param content
	 *            提示框内容
	 * @param btnTitle1
	 *            按钮1文字
	 * @param btnTitle2
	 *            按钮2文字
	 * @param click1
	 *            第一个按钮点击实现
	 * @param click2
	 *            第二个按钮点击实现
	 */
	public static void showDialog(Activity act, String title, String content,
			String btnTitle1, String btnTitle2, OnClickListener click1,
			OnClickListener click2) {
		new AlertDialog.Builder(act).setTitle(title).setMessage(content)
				.setPositiveButton(btnTitle1, click1)
				.setNegativeButton(btnTitle2, click2).show();
	}
}
