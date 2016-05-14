package com.way.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.way.app.PushApplication;
import com.way.common.util.SharePreferenceUtil;
import com.way.push.R;

public class WelcomeActivity extends Activity {
	private SharePreferenceUtil spUtil;
	private Handler handler;

	Runnable startAct = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (!TextUtils.isEmpty(spUtil.getUserId())) {
				Intent intent = new Intent(WelcomeActivity.this,
						MainActivity.class);
				startActivity(intent);
			} else {
				startActivity(new Intent(WelcomeActivity.this,
						FirstSetActivity.class));
			}
			finish();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		spUtil = PushApplication.getInstance().getSpUtil();
		handler = new Handler();
		handler.postDelayed(startAct, 3000);

	}

}
