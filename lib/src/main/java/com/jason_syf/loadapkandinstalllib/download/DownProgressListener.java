package com.jason_syf.loadapkandinstalllib.download;

/**
 * Created by jason_syf on 2017/9/28.
 * Email:jason_sunyf@163.com
 */

public interface DownProgressListener {
    /**   下载进度的监听
     * @param bytesRead  已下载的长度
     * @param contentLength 下载的总长度
     * @param done 下载是否完成
     */
    void update(long bytesRead, long contentLength, boolean done);

}
