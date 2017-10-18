package com.example.library.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.DisplayMetrics;

import java.io.File;

/**
 * Created by xuzhiqiang on 2017/10/18.
 */

public class DeviceUtil {
    /**
     * 获得手机屏幕宽度
     *
     * @return 屏幕宽度
     */
    public static int getWidth(Context mContext) {
        DisplayMetrics display = mContext.getResources()
                .getDisplayMetrics();
        return display.widthPixels;
    }

    /**
     * 获得手机屏幕高度
     *
     * @return 屏幕高度
     */
    public static int getHeight(Context mContext) {
        DisplayMetrics display = mContext.getResources()
                .getDisplayMetrics();
        return display.heightPixels;
    }

    /**
     * 获得手机屏幕密度
     *
     * @return 屏幕密度
     */
    public static float getDensityDpi(Context mContext) {
        DisplayMetrics display = mContext.getResources()
                .getDisplayMetrics();
        return display.densityDpi;
    }

    //设备系统版本号
    public static String getSystemVersion(Context mContext) {
        PackageManager pm = mContext.getPackageManager();
        try {
            return pm.getPackageInfo(mContext.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    //手机型号
    public static String getPhoneType() {
        return Build.BRAND + "_" + Build.MODEL;
    }

    /**
     * 获取手机内部(ROM)空间大小
     *
     * @return
     */
    public static String getROMMemory(Context mContext) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            File path = Environment.getDataDirectory();
            StatFs mStatFs = new StatFs(path.getPath());
            long blockSize = mStatFs.getBlockSizeLong();
            long totalBlocks = mStatFs.getBlockCountLong();
            long availableBlocks = mStatFs.getAvailableBlocksLong();
            String totalSize = Formatter.formatFileSize(mContext, totalBlocks * blockSize);
            String availableSize = Formatter.formatFileSize(mContext, blockSize * availableBlocks);
            return totalSize + "/" + availableSize;
        }
        return "";
    }

    /**
     * 获取可用手机内存(RAM)
     *
     * @return
     */
    public static String getRAMMemory(Context mContext) {
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return Formatter.formatFileSize(mContext, mi.totalMem) + "/" + Formatter.formatFileSize(mContext, mi.availMem);
    }

    /**
     * 获取手机服务商信息
     */
    public static String getProvidersName(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String ProvidersName = "";
        String IMSI = telephonyManager.getSubscriberId();
        // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
        if (IMSI == null || "".equals(IMSI)) {
            ProvidersName = "获取手机号码失败";
        } else if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
            ProvidersName = "中国移动";
        } else if (IMSI.startsWith("46001")) {
            ProvidersName = "中国联通";
        } else if (IMSI.startsWith("46003")) {
            ProvidersName = "中国电信";
        }
        return ProvidersName;
    }

    public static String getNetworkTypeId(Context context) {
        try {
            return getNetworkType(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取网络类型
     *
     * @param context
     * @return
     */
    public static String getNetworkType(Context context) {
        String netTypeMode = "";
        try {
            final NetworkInfo mNetworkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            if (mNetworkInfo == null) {
                return "";
            }
            final int netType = mNetworkInfo.getType();
            if (netType == ConnectivityManager.TYPE_WIFI) {
                // wifi上网
                netTypeMode = "wifi";
            } else if (netType == ConnectivityManager.TYPE_MOBILE) {
                // 接入点上网
                final String netMode = mNetworkInfo.getExtraInfo();
                if (!TextUtils.isEmpty(netMode)) {
                    return netMode;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return netTypeMode;
    }

    /**
     * 获取经纬度
     *
     * @param context
     * @return
     */
    public static String getLngAndLat(Context context) {
        double latitude = 0.0;
        double longitude = 0.0;
        try {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {  //从gps获取经纬度
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                } else {//当GPS信号弱没获取到位置的时候又从网络获取
                    return getLngAndLatWithNetwork(context);
                }
            } else {    //从网络获取经纬度
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return longitude + "," + latitude;
    }

    /**
     * 判断手机是否ROOT
     */
    public static boolean isRoot() {
        boolean root = false;
        try {
            if ((!new File("/system/bin/su").exists())
                    && (!new File("/system/xbin/su").exists())) {
                root = false;
            } else {
                root = true;
            }
        } catch (Exception e) {
        }
        return root;
    }

    //从网络获取经纬度
    public static String getLngAndLatWithNetwork(Context context) {
        double latitude = 0.0;
        double longitude = 0.0;
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
        return longitude + "," + latitude;
    }


    static LocationListener locationListener = new LocationListener() {

        // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        // Provider被enable时触发此函数，比如GPS被打开
        @Override
        public void onProviderEnabled(String provider) {

        }

        // Provider被disable时触发此函数，比如GPS被关闭
        @Override
        public void onProviderDisabled(String provider) {

        }

        //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location) {
        }
    };
}
