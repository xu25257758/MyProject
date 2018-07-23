package com.example.library.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class FileUtil {

    private static final String TAG = FileUtil.class.getSimpleName();
    private static final String DIR = "Flyup";


    /**
     * 从assets 文件夹中获取文件并读取数据
     *
     * @param context
     * @param fileName
     * @param isGBKEncoding
     * @return
     */
/*    public static String getFromAssets(Context context, String fileName, boolean isGBKEncoding) {
        String result = "";
        try {
            InputStream in = context.getResources().getAssets().open(fileName);
            // 获取文件的字节数
            int lenght = in.available();
            // 创建byte数组
            byte[] buffer = new byte[lenght];
            // 将文件中的数据读到byte数组中
            in.read(buffer);
            if (isGBKEncoding) {
                result = EncodingUtils.getString(buffer, "GBK");
            } else {
                result = EncodingUtils.getString(buffer, "UTF-8");
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }*/


    /**
     * 获取可以使用的缓存目录
     *
     * @param context
     * @param uniqueName 目录名称
     * @return
     */
    public static File getDiskCacheDir(Context context, String uniqueName) {
        final String cachePath = checkSDCard() ? getExternalCacheDir(context).getPath() : getAppCacheDir(context);

        File cacheDirFile = new File(cachePath,uniqueName);
        if (!cacheDirFile.exists()) {
            cacheDirFile.mkdirs();
        }

        return cacheDirFile;
    }

    /**
     * 检查SD卡是否存在
     *
     * @return
     */
    public static boolean checkSDCard() {
        final String status = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(status)) {
            return true;
        }
        return false;
    }

    /**
     * 获取程序外部的缓存目录
     * 这个sd卡中文件路径下的内容会随着，程序卸载或者设置中清除缓存后一起清空
     * 路径："/storage/emulated/0/Android/data/+包名+/cache/"
     * @param context
     * @return
     */
    public static File getExternalCacheDir(Context context) {
        if (checkSDCard()){
            File dir =  context.getExternalCacheDir();
            if(!dir.exists()){
                dir.mkdirs();
            }
        }
        return null;
    }


    /**
     * 缓存目录
     */
    public static File getCacheDir(Context context) {
        return context.getCacheDir();
    }


    /**
     * 目录
     */
    public static File getDir(Context context) {
        if (Environment.MEDIA_MOUNTED.equalsIgnoreCase(Environment.getExternalStorageState())) {
            File dir = new File(Environment.getExternalStorageDirectory(), DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            return dir;
        } else {
            return context.getFilesDir();
        }
    }


    /**
     * 获取安装在用户手机上的com.youyuan.yyapp下的cache目录
     *
     * @return cache path
     */
    public static String getAppCacheDir(Context context) {
        return context.getCacheDir().getPath();
    }
    /**
     * 获取安装在用户手机上的com.youyuan.yyapp下的cache目录
     *
     * @return cache path
     */
    public static File getAppDir(Context context) {
        return context.getFilesDir();
    }


}
