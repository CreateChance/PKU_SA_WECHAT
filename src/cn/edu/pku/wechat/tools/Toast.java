package cn.edu.pku.wechat.tools;

import cn.edu.pku.wechat.Application;

public class Toast {
	public static void show(CharSequence msg) {
        android.widget.Toast.makeText(Application.getInstance(), msg, android.widget.Toast.LENGTH_LONG).show();
    }

    public static void show(int stringId) {
        android.widget.Toast.makeText(Application.getInstance(), stringId, android.widget.Toast.LENGTH_LONG).show();
    }
}
