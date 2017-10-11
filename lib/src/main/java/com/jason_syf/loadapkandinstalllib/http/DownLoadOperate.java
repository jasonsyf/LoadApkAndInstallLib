package com.jason_syf.loadapkandinstalllib.http;

import android.util.Log;

import com.jason_syf.loadapkandinstalllib.download.DownProgressListener;
import com.jason_syf.loadapkandinstalllib.download.DownloadProgressInterceptor;
import com.jason_syf.loadapkandinstalllib.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by jason_syf on 2017/9/28.
 * Email:jason_sunyf@163.com
 */

public class DownLoadOperate {
    private static final String TAG = "DownloadAPI";
    private static final int DEFAULT_TIMEOUT = 15;
    public Retrofit retrofit;


    /**
     * @param url  需要传入的apk下载地址
     * @param listener  自定义的进度条监听接口
     *   这里的okHttp，retrofit初始化实际项目中应该是统一封装的
     */
    public DownLoadOperate(String url, DownProgressListener listener) {
        DownloadProgressInterceptor interceptor = new DownloadProgressInterceptor(listener);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public void downloadAPK(@NonNull String url, final File file, Observer consumer) {
        Log.d(TAG, "downloadAPK: " + url);
        retrofit.create(DownloadApi.class)
                .download(url)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Function<ResponseBody, InputStream>() {
                    @Override
                    public InputStream apply(@NonNull ResponseBody responseBody) throws Exception {
                        return responseBody.byteStream();
                    }
                })
                .observeOn(Schedulers.computation())
                .doOnNext(new Consumer<InputStream>() {
                    @Override
                    public void accept(InputStream inputStream) throws Exception {
                        try {
                            FileUtils.writeFile(inputStream, file);
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e.getMessage(), e);
                        }
                    }

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }
}
