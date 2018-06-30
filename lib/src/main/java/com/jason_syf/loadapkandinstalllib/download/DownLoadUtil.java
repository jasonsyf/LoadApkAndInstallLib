package com.jason_syf.loadapkandinstalllib.download;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import java.io.File;

import static com.jason_syf.loadapkandinstalllib.download.DownFileService.DOWNLOAD_COMPLETE;
import static com.jason_syf.loadapkandinstalllib.download.DownFileService.DOWNLOAD_ERROR;
import static com.jason_syf.loadapkandinstalllib.download.DownFileService.DOWNLOAD_START;

/**
 * Created by jason_syf on 2017/9/30.
 * Email:jason_sunyf@163.com
 */

public class DownLoadUtil {

    /**
     * DownLoadUtil的唯一单例
     */
    public static final String MESSAGE_PROGRESS = "message_progress";
    private String apkUrl;
    private String apk_name;
     DownLoadCallBack mCallBack;
    private static DownLoadUtil INSTANCE;

    public DownLoadUtil(DownLoadCallBack callBack) {
        mCallBack = callBack;
    }

    public DownLoadUtil() {
    }

    public static DownLoadUtil getInstance(DownLoadCallBack callBack) {
        if (INSTANCE == null) {
            INSTANCE = new DownLoadUtil(callBack);
        }
        return INSTANCE;
    }

    public static DownLoadUtil getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DownLoadUtil();
        }
        return INSTANCE;
    }

    private Handler mHandler = new Handler() {
        // 接收结果，刷新界面
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWNLOAD_START:
                    mCallBack.onDownLoadStart();
                    break;
                case DOWNLOAD_COMPLETE:
                    mCallBack.onDownLoadComplete();
                    break;
                case DOWNLOAD_ERROR:
                    mCallBack.onDownLoadError();
                    break;
                default:
            }
        }
    };


    public String getNotificationTitle() {
        return notificationTitle;
    }

    public String getNotificationContent() {
        return notificationContent;
    }

    public int getNotificationIcon() {
        return notificationIcon;
    }

    public DownLoadUtil setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
        return this;
    }

    public DownLoadUtil setNotificationContent(String notificationContent) {
        this.notificationContent = notificationContent;
        return this;
    }

    public DownLoadUtil setNotificationIcon(int notificationIcon) {
        this.notificationIcon = notificationIcon;
        return this;
    }

    private String notificationTitle;
    private String notificationContent;
    private int notificationIcon;


    public DownLoadUtil startInstall(Context context, String apkUrl, String apkName, DownLoadCallBack loadCallBack) {
        this.apkUrl = apkUrl;
        this.apk_name = apkName;
        Intent intent = new Intent(context, DownFileService.class);
        intent.putExtra("messenger", new Messenger(mHandler));
        context.startService(intent);
        return this;
    }


    public String getApkUrl() {
        return apkUrl;
    }

    public void registerReceiver(Context context, BroadcastReceiver broadcastReceiver) {
        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(context);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MESSAGE_PROGRESS);
        bManager.registerReceiver(broadcastReceiver, intentFilter);

    }

    /**
     * 安装
     *
     * @param context 场景
     */
    public void installAPK(Context context) {
        File file = new File(Environment.getExternalStorageDirectory()
                , apk_name + ".apk");
        if (file.exists()) {
            openFile(file, context);
        } else {
            Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
        }

//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory()
//                , APP_NAME + ".apk")), "application/vnd.android.package-archive");
//        context.startActivity(intent);
    }

    /**
     * 重点在这里
     * 处理android7.0以上安装适配问题，暂时没有进行动态权限申请
     */
    public void openFile(File var0, Context var1) {

        Intent var2 = new Intent();
        var2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        var2.setAction(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri uriForFile = FileProvider.getUriForFile(var1, var1.getApplicationContext().getPackageName() + ".provider", var0);
            var2.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            var2.setDataAndType(uriForFile, var1.getContentResolver().getType(uriForFile));
        } else {
            var2.setDataAndType(Uri.fromFile(var0), getMIMEType(var0));
        }
        try {
            var1.startActivity(var2);
        } catch (Exception var5) {
            var5.printStackTrace();
            Toast.makeText(var1, "没有找到打开此类文件的程序", Toast.LENGTH_SHORT).show();
        }
    }

    public String getMIMEType(File var0) {
        String var1 = "";
        String var2 = var0.getName();
        String var3 = var2.substring(var2.lastIndexOf(".") + 1, var2.length()).toLowerCase();
        var1 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(var3);
        return var1;
    }

    public String getApk_name() {
        return apk_name;
    }
}
