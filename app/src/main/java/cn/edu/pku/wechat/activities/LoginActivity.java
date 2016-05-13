package cn.edu.pku.wechat.activities;

import com.alibaba.fastjson.JSONObject;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.Response;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import cn.edu.pku.wechat.R;
import cn.edu.pku.wechat.globals.Constants;
import cn.edu.pku.wechat.nohttp.CallServer;
import cn.edu.pku.wechat.nohttp.FastJsonRequest;
import cn.edu.pku.wechat.nohttp.HttpListener;
import cn.edu.pku.wechat.tools.Toast;

public class LoginActivity extends Activity implements View.OnClickListener, HttpListener<JSONObject> {

	Button btnLogin, btnRegister;
	EditText etMobile, etPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		etMobile = (EditText) findViewById(R.id.login_et_mobile);
		etPassword = (EditText) findViewById(R.id.login_et_password);
		btnLogin = (Button) findViewById(R.id.login_btn_login);
		btnRegister = (Button) findViewById(R.id.login_btn_register);

		btnLogin.setOnClickListener(this);
		btnRegister.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_btn_login:// 登录
			String strMobile = etMobile.getText().toString();
			String strPwd = etPassword.getText().toString();
			if (strMobile.isEmpty() || strPwd.isEmpty()) {
				Toast.show("请输入手机号或密码");
				return;
			}
			login(strMobile, strPwd);
			break;
		case R.id.login_btn_register:// 注册
			register();
			break;
		}

	}

	/**
	 * 登录
	 * 
	 * @param mobile
	 * @param password
	 */
	private void login(String mobile, String password) {

		Intent intent = new Intent(LoginActivity.this, MainActivity.class);
		startActivity(intent);
		finish();
		// 两个参数分别表示进入的动画,退出的动画
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

		// 没服务器，不需要连接
		// Request<JSONObject> request = new
		// FastJsonRequest(Constants.URL_USER_LOGIN);
		// request.add("mobile", mobile);
		// request.add("pwd", password);
		//
		// CallServer.getRequestInstance().add(this, 0, request, this, false,
		// true);
	}

	/**
	 * 注册
	 */
	private void register() {
		Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
		startActivity(intent);
		finish();
		// 两个参数分别表示进入的动画,退出的动画
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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
			Toast.show(builder.toString());
		}
	}

	@Override
	public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
		// mTvResult.setText("请求失败\n" + exception.getMessage());
		Toast.show("请求失败\n" + exception.getMessage());
	}
}
