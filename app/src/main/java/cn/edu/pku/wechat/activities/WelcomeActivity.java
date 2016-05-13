package cn.edu.pku.wechat.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import cn.edu.pku.wechat.R;

public class WelcomeActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

		new AsyncTask<Void, Void, Integer>() {

			@Override
			protected Integer doInBackground(Void... params) {
				try {
					Thread.sleep(2500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return 0;
			}

			@Override
			protected void onPostExecute(Integer result) {
				/// RegisterActivity MainActivity LoginActivity
				Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
				startActivity(intent);
				finish();
				// 两个参数分别表示进入的动画,退出的动画
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			};
		}.execute(new Void[] {});

	}
}
