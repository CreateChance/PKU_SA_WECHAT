package com.way.common.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.way.push.R;

public class DialogUtil {
	public static Dialog getLoginDialog(Activity context) {

		final Dialog dialog = new Dialog(context, R.style.Dialog);
		dialog.setContentView(R.layout.firset_dialog_view);
		Window window = dialog.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();

		int screenW = getScreenWidth(context);
		lp.width = (int) (0.6 * screenW);

		TextView titleTxtv = (TextView) dialog.findViewById(R.id.tvLoad);
		titleTxtv.setText(R.string.first_start_dialog_text);
		return dialog;
	}

	public static Dialog getCustomDialog(Activity context) {
		final Dialog dialog = new Dialog(context, R.style.Dialog);
		return dialog;
	}
	/**
	 * 非activity的context获取自定义对话框
	 * @param context
	 * @return
	 */
	public static Dialog getWinDialog(Context context) {
		final Dialog dialog = new Dialog(context, R.style.Dialog);
		dialog.getWindow()
				.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		return dialog;
	}

	public static int getScreenWidth(Activity context) {
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	public static int getScreenHeight(Activity context) {
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}
}
