package com.lanyu96.querylogistics.uitl;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * 设备相关信息工具类
 * <p>
 * 需添的加权限：
 * {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>}
 * {@code <uses-permission android:name="android.permission.INTERNET"/>}
 * </p>
 */
public final class DeviceUtil {

    private static final String TODO = "";
    private static final String TAG = "TEST11";

    /**
     * 获取设备宽度（px）
     * @param context
     * @return
     */
    public static int deviceWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取设备高度（px）
     * @param context
     * @return
     */
    public static int deviceHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 是否有网
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 返回版本名字
     * 对应build.gradle中的versionName
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 返回版本号
     * 对应build.gradle中的versionCode
     *
     * @param context
     * @return
     */
    public static String getVersionCode(Context context) {
        String versionCode = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = String.valueOf(packInfo.versionCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取设备的唯一标识，deviceId
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        assert tm != null;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return TODO;
        }
        String deviceId = tm.getDeviceId();
        if (deviceId == null) {
            return "";
        } else {
            return deviceId;
        }
    }
    /**
     * 获取手机品牌
     *
     * @return
     */
    public static String getPhoneBrand() {
        return android.os.Build.BRAND;
    }
    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getPhoneModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机Android API等级（22、23 ...）
     *
     * @return
     */
    public static int getBuildLevel() {
        return android.os.Build.VERSION.SDK_INT;
    }
    /**
     * 获取手机Android 版本（4.4、5.0、5.1 ...）
     *
     * @return
     */
    public static String getBuildVersion() {
        return android.os.Build.VERSION.RELEASE;
    }
    /**
     * 获取当前App进程的id
     *
     * @return
     */
    public static int getAppProcessId() {
        return android.os.Process.myPid();
    }
    /**
     * 获取当前App进程的Name
     *
     * @param context
     * @param processId
     * @return
     */
    public static String getAppProcessName(Context context, int processId) {
        String processName = "";
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        // 获取所有运行App的进程集合
        assert am != null;
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = context.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == processId) {
                    CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));

                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                Log.e(DeviceUtil.class.getName(), e.getMessage(), e);
            }
        }
        return processName;
    }
    /**
     * 创建App文件夹
     *
     * @param appName
     * @param application1
     * @param application
     * @return
     */
    public static String createAPPFolder(String appName, Application application1, Application application) {
        return createAPPFolder(appName, application, (Application) null);
    }

    /**
     * 通过Uri找到File
     *
     * @param context
     * @param uri
     * @return
     */
    public static File uri2File(Activity context, Uri uri) {
        File file;
        String[] project = {MediaStore.Images.Media.DATA};
        Cursor actualImageCursor = context.getContentResolver().query(uri, project, null, null, null);
        if (actualImageCursor != null) {
            int actual_image_column_index = actualImageCursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualImageCursor.moveToFirst();
            String img_path = actualImageCursor
                    .getString(actual_image_column_index);
            file = new File(img_path);
        } else {
            file = new File(uri.getPath());
        }
        if (actualImageCursor != null) actualImageCursor.close();
        return file;
    }


    public StringBuilder DeviceInfo(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        int deviceWidth = DeviceUtil.deviceWidth(context);
        Log.i(TAG, "宽度为" + deviceWidth);

        stringBuilder.append("宽度为").append(deviceWidth).append("\n");
        int deviceHeight = DeviceUtil.deviceHeight(context);
        Log.i(TAG, "高度为" + deviceHeight);

        stringBuilder.append("高度为").append(deviceHeight).append("\n");
        boolean networkConnected = DeviceUtil.isNetworkConnected(context);
        if (networkConnected) {
            Log.i(TAG, "有网");
        } else {
            Log.i(TAG, "无网");
        }
        String versionCode = DeviceUtil.getVersionCode(context);
        Log.i(TAG, "App版本号为 : " + versionCode);

        String deviceId = DeviceUtil.getDeviceId(context);
        Log.i(TAG, "设备唯一ID" + deviceId);
        stringBuilder.append("设备唯一ID").append(deviceId).append("\n");

        String phoneBrand = DeviceUtil.getPhoneBrand();
        Log.i(TAG, "手机品牌为:" + phoneBrand);
        stringBuilder.append("手机品牌为:").append(phoneBrand).append("\n");

        String phoneModel = DeviceUtil.getPhoneModel();
        Log.i(TAG, "手机型号为 " + phoneModel);
        stringBuilder.append("手机型号为 ").append(phoneModel).append("\n");

        int buildLevel = DeviceUtil.getBuildLevel();
        Log.i(TAG, "androidAPI等级" + buildLevel);
        stringBuilder.append("androidAPI等级").append(buildLevel).append("\n");

        String buildVersion = DeviceUtil.getBuildVersion();
        Log.i(TAG, "系统安卓版本" + buildVersion);
        stringBuilder.append("系统安卓版本").append(buildVersion).append("\n");

        int appProcessId = DeviceUtil.getAppProcessId();
        Log.i(TAG, "App进程ID" + appProcessId);
        stringBuilder.append("App进程ID").append(appProcessId).append("\n");

        String appProcessName = DeviceUtil.getAppProcessName(context, appProcessId);
        Log.i(TAG, "App进程名称" + appProcessName);
        stringBuilder.append("App进程名称").append(appProcessName).append("\n");

        return stringBuilder;
    }

}