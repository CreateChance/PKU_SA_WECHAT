package cn.edu.pku.wechat.activities;

import com.alibaba.fastjson.JSONObject;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.Response;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import cn.edu.pku.wechat.R;
import cn.edu.pku.wechat.globals.Constants;
import cn.edu.pku.wechat.nohttp.CallServer;
import cn.edu.pku.wechat.nohttp.FastJsonRequest;
import cn.edu.pku.wechat.nohttp.HttpListener;
import cn.edu.pku.wechat.tools.Toast;

public class RegisterActivity extends Activity implements View.OnClickListener, HttpListener<JSONObject> {

	Button btnRegister, btnRegisterGetCode;
	private String identifying_code;
	private EditText et_register_phonenum, et_register_identifying_code;
	private IdentifyingCodeTimeCount time;
	private String phonenum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		init();
	}

	// 输入格式检查
	private String formatJudgment() {
		// TODO Auto-generated method stub
		phonenum = et_register_phonenum.getText().toString();
		if (phonenum.matches("[0-9]+") && phonenum.substring(0, 1).equals("1")) {
			if (phonenum.matches("[0-9]{11}")) {
				return phonenum;
			} else {
				et_register_phonenum.setText("");
				Toast.show("请您输入11位手机号");				
				return "wrong";
			}
		} else {
			et_register_phonenum.setText("");
			Toast.show("请输入您正确的手机号");			
			return "wrong";
		}
	}

	private void init() {
		// TODO Auto-generated method stub
		et_register_phonenum = (EditText) findViewById(R.id.et_register_phonenum);
		et_register_identifying_code = (EditText) findViewById(R.id.et_register_identifying_code);

		btnRegister = (Button) findViewById(R.id.btn_register);
		btnRegisterGetCode = (Button) findViewById(R.id.btn_register_get_code);

		identifying_code = btnRegisterGetCode.getText().toString();

		time = new IdentifyingCodeTimeCount(60000, 1000);// 构造CountDownTimer对象

		btnRegister.setOnClickListener(this);
		btnRegisterGetCode.setOnClickListener(this);
	}

	public void RegisterBack(View v) {
		/// RegisterActivity MainActivity LoginActivity
		Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
		startActivity(intent);
		finish();
		// 两个参数分别表示进入的动画,退出的动画
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_register:
			registerHandler();
			break;

		case R.id.btn_register_get_code:
			getCodeHandler();
			break;
		}

	}

	private void registerHandler() {
		Request<JSONObject> request = new FastJsonRequest(Constants.URL_USER_REGISTER);
		CallServer.getRequestInstance().add(this, 0, request, this, false, true);
	}


	@Override
	public void onSucceed(int what, Response<JSONObject> response) {
		JSONObject jsonObject = response.get();
		if (0 == jsonObject.getIntValue("error")) {
			StringBuilder builder = new StringBuilder(jsonObject.toString());
			builder.append("\n\n解析数据: \n\n请求方法: ").append(jsonObject.getString("method")).append("\n");
			builder.append("请求地址: ").append(jsonObject.getString("url")).append("\n");
			builder.append("响应数据: ").append(jsonObject.getString("data")).append("\n");
			builder.append("错误码: ").append(jsonObject.getIntValue("error"));
			// mTvResult.setText(builder.toString());
		}
	}

	@Override
	public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
		// mTvResult.setText("请求失败\n" + exception.getMessage());
	}
	
	

	private void getCodeHandler() {
		if (!formatJudgment().equals("wrong")) {
			String phonenum = formatJudgment();
			time.start();
			//获取验证码, 需要调用短信接口，不实现

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
					Toast.show("验证码发送成功");
				};
			}.execute(new Void[] {});			
		}
	}
	

	/*
	 * 验证码计时
	 */
	public class IdentifyingCodeTimeCount extends CountDownTimer {

		public IdentifyingCodeTimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			btnRegisterGetCode.setClickable(false);
			btnRegisterGetCode.setEnabled(false);
			btnRegisterGetCode.setText(millisUntilFinished / 1000 + "秒");
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub

			btnRegisterGetCode.setText("获取验证码");
			btnRegisterGetCode.setClickable(true);
			btnRegisterGetCode.setEnabled(true);

		}

	}

}
