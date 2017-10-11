package com.jason_syf.loadapkandinstalllib.download;

/**
 * Created by jason_syf on 2017/9/28.
 * Email:jason_sunyf@163.com
 */

public interface DownProgressListener {
    void update(long bytesRead, long contentLength, boolean done);

}
