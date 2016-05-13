/*
 * Copyright © YOLANDA. All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.edu.pku.wechat;

import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;

import cn.edu.pku.wechat.config.AppConfig;

/**
 * Created in Oct 23, 2015 12:59:13 PM.
 *
 * @author YOLANDA;
 */
public class Application extends android.app.Application {

    private static Application _instance;

    public String[] nohttpTitleList;

    public String[] cacheTitle;

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;

        NoHttp.init(this);

        Logger.setTag("NoHttpSample");
        Logger.setDebug(true);// 开始NoHttp的调试模式, 这样就能看到请求过程和日志

        AppConfig.getInstance();
    }

    public static Application getInstance() {
        return _instance;
    }

}
