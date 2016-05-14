package com.way.baidupush.client;

import android.app.IntentService;
import android.content.Intent;
import android.text.TextUtils;

import com.way.common.util.JsonUtil;
import com.way.common.util.L;
import com.way.common.util.SendMsgAsyncTask;

public class PreParseService extends IntentService {

	public PreParseService() {
		super("PreParseService");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		L.i("PreParseService onCreate");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		String message = intent.getStringExtra("message");
		L.i("PreParseService :" + message);
		if (TextUtils.isEmpty(message))
			return;

		String userId = JsonUtil.getFromUserId(message);

		L.i("response start");
		new SendMsgAsyncTask(JsonUtil.createJsonMsg(System.currentTimeMillis(),
				"hi",PushMessageReceiver.RESPONSE), userId).send();// 同时也回一条消息给对方1
		L.i("response end");
	}

}
