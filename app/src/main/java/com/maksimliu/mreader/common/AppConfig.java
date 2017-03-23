package com.maksimliu.mreader.common;

import android.os.Environment;

import java.io.File;

/**
 * Created by MaksimLiu on 2017/3/21.
 * <h3>应用配置类</h3>
 */

public class AppConfig {

    public static final String ZHIHU_CACHE_NAME = "zhihu";

    public static final String GANK_CACHE_NAME = "gank";

    public static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE=1;
    /**
     * 默认存放下载文件路径
     */
    public static final String DEFAULT_DOWNLOAD_FILE_PATH =
            Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator
                    + "MReader"
                    + File.separator;

}
