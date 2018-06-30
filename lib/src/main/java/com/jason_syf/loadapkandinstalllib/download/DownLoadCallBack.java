package com.jason_syf.loadapkandinstalllib.download;

/**
 * @author : Jason_Sunyf
 * @date : 2018年06月30日11时11分
 * E-mail  : jason_sunyf@163.com
 */
public interface DownLoadCallBack {
    /**
     * 开始下载
     */
    void onDownLoadStart();

    /**
     * 下载完成
     */
    void onDownLoadComplete();

    /**
     *下载错误
     */
    void onDownLoadError();
}
