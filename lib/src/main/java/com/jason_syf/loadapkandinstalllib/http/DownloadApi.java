package com.jason_syf.loadapkandinstalllib.http;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by jason_syf on 2017/9/28.
 * Email:jason_sunyf@163.com
 */

public interface DownloadApi {
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);
}
