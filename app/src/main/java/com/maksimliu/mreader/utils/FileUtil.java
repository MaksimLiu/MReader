package com.maksimliu.mreader.utils;

import android.graphics.Bitmap;
import android.os.Environment;

import com.maksimliu.mreader.common.AppConfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by MaksimLiu on 2017/3/23.
 * <h3>文件管理</h3>
 */

public class FileUtil {

    /**
     * 保存图片到存储卡
     * @param fileName 文件名称
     * @param bitmap bitmap
     * @return
     */
    public static File saveImage(String fileName, Bitmap bitmap) {

        File dir = new File(Environment.getExternalStorageDirectory(),"MReader");

        if (!dir.exists()){
            dir.mkdir();
        }

        String path=fileName+".jpg";

        File file=new File(dir,path);

        if (!file.exists()){

            try {
                FileOutputStream fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return file;
    }
}
