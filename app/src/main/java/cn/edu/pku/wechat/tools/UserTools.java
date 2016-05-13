package cn.edu.pku.wechat.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import cn.edu.pku.wechat.model.UserInfo;


public class UserTools {
	private static final String TAG = "UserTools";
	
	public static final String User_Info = "userinfo";

	private static final String User_Nickname = "nickname";
	private static final String User_Email = "email";
	private static final String User_Register_Time = "register_time";
	private static final String User_Last_Login_Time = "last_login_time";
	private static final String User_ID = "user_id";
	private static final String User_Password = "pwd";

	/**
	 * 保存基本信息
	 * 
	 * @param context
	 * @param teacher
	 * @throws Exception
	 */
	public static void saveUser(Context context, UserInfo userInfo, String pwd) {
		SharedPreferences sp = context.getSharedPreferences(User_Info,
				Context.MODE_PRIVATE);
		if (userInfo == null)
			return;
		// 存入数据
		Editor editor = sp.edit();
		try {
			editor.putString(User_Nickname, userInfo.getNickname());
			editor.putString(User_Email, EncryptDecryptTools.encrypt(GlobalTools.AESSEED, userInfo.getEmail()));
			editor.putString(User_Register_Time, DateTools.parseToString(userInfo.getRegister_time()));
			editor.putString(User_Last_Login_Time, DateTools.parseToString(userInfo.getLast_login_time()));
			editor.putString(User_ID, EncryptDecryptTools.encrypt(GlobalTools.AESSEED, userInfo.getUser_id()));
			editor.putString(User_Password, EncryptDecryptTools.encrypt(GlobalTools.AESSEED, pwd));

			editor.commit();
		} catch (Exception e) {
			Log.e(TAG, "saveUser", e);
		}
	}

	/**
	 * 获取基本信息
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static UserInfo getUser(Context context) {

		SharedPreferences sharedPre = context.getSharedPreferences(User_Info,
				Context.MODE_PRIVATE);
		if (sharedPre.contains(User_Email)) {
			String email = sharedPre.getString(User_Email, "");
			String password = sharedPre.getString(User_Password, "");
			String userID = sharedPre.getString(User_ID, "");

			try {
				email = EncryptDecryptTools.decrypt(GlobalTools.AESSEED, email);
				userID = EncryptDecryptTools.decrypt(GlobalTools.AESSEED, userID);
				if (!password.isEmpty())
					password = EncryptDecryptTools.decrypt(GlobalTools.AESSEED,
							password);

				UserInfo user = new UserInfo();
				user.setEmail(email);
				user.setPwd(password);
				user.setUser_id(userID);

				return user;
			} catch (Exception e) {
				Log.e(TAG, "getUser", e);
			}
		}

		return null;
	}

	/**
	 * 注销，移除密码
	 * 
	 * @param context
	 */
	public static void removePassword(Context context) {
		SharedPreferences sp = context.getSharedPreferences(User_Info,
				Context.MODE_PRIVATE);
		// 存入数据
		Editor editor = sp.edit();
		try {
			editor.remove(User_Password);
			editor.commit();
		} catch (Exception e) {
			Log.e(TAG, "removePassword", e);
		}
	}
}
