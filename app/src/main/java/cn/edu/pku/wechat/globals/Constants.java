package cn.edu.pku.wechat.globals;

public class Constants {
	

    /**
     * 服务器地址.
     */
    public static final String SERVER;

    /**
     * 
     */
    static {
    	SERVER = "http://192.168.1.103:9080/HttpServer/";
    }
    
    /**
     * 用户注册
     * <pre>
     * + method `POST`
		+ request
		    {
				"email": "xxx", // email
				"nickname" : "xxx" //昵称
				"pwd" : "xxx", // 密码
		    }
		+ response
		success:
		    {
				"ret":"true"
		    }
		failed:
		   {
				"err_code":40400,
				"err_msg":"xxx"
		   }
     * </pre>
     */
    public static final String URL_USER_REGISTER = SERVER + "user/register";
    
    /**
     * 用户登录
     * <pre>
     * + method `POST`
		+ request
		    {
				"email": "xxx", // 用户名
				"pwd": "xxx", // 密码
		    }
		+ response
		    {
		    		"user_id":"xx"
		    		"email":"xx"
		    		"register_time":"2016-03-13 00:00:00"
		    		"last_login_time" : "2016-03-14 00:00:00"
		    }
     * </pre>
     */
    public static final String URL_USER_LOGIN = SERVER + "user/login";
    
    /**
     * 用户登出
     * <pre>
     * + method `POST`
		+ request
		    {
				"email": "xxx", // email
		    }
		+ response
		    {
				"ret": "true"
		    }
     * </pre>
     */
    public static final String URL_USER_LOGOUT = SERVER + "user/logout";
    
    /**
     * 验证邮箱有效性(get)
     */
    public static final String URL_USER_VALIDATE_EMAIL = SERVER + "user/validate_email";
    
    
    /**
     * 修改密码
     */
    public static final String URL_USER_CHANGE_PASSWORD = SERVER + "user/change_password";
    
    /**
     * 忘记密码,系统后台自动发送修改密码链接到用户注册邮箱
     */  
    public static final String URL_USER_FORGOT_PASSWORD = SERVER + "user/forgot_password";
     
    /**
     * 重置密码
     */
    public static final String URL_USER_RESET_PASSWORD = SERVER + "user/set_password";
    
    /**
     * 添加好友
     */
    public static final String URL_USER_ADD_FRIEND = SERVER + "user/add_friend";
    
    /**
     * 好友列表
     */
    public static final String URL_USER_FRIENDS_LIST = SERVER + "user/friends_list";
    
    /**
     * 消息队列 -- 入队
     */
    public static final String URL_MESSAGE_PUSH = SERVER + "message/push";
    
    /**
     * 消息队列 -- 出队
     */
    public static final String URL_MESSAGE_pop = SERVER + "message/pop";
}
