package com.jason_syf.loadapkandinstalllib.download;

/*
 * Created by jason_syf on 2017/9/30.
 * Email:jason_sunyf@163.com
 */

import android.os.Parcel;
import android.os.Parcelable;

public class DownLoadBean implements Parcelable {
    private int progress;
    private long currentFileSize;
    private long totalFileSize;

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public long getCurrentFileSize() {
        return currentFileSize;
    }

    public void setCurrentFileSize(long currentFileSize) {
        this.currentFileSize = currentFileSize;
    }

    public long getTotalFileSize() {
        return totalFileSize;
    }

    public void setTotalFileSize(long totalFileSize) {
        this.totalFileSize = totalFileSize;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.progress);
        dest.writeLong(this.currentFileSize);
        dest.writeLong(this.totalFileSize);
    }

    public DownLoadBean() {
    }

    protected DownLoadBean(Parcel in) {
        this.progress = in.readInt();
        this.currentFileSize = in.readLong();
        this.totalFileSize = in.readLong();
    }

    public static final Creator<DownLoadBean> CREATOR = new Creator<DownLoadBean>() {
        @Override
        public DownLoadBean createFromParcel(Parcel source) {
            return new DownLoadBean(source);
        }

        @Override
        public DownLoadBean[] newArray(int size) {
            return new DownLoadBean[size];
        }
    };
}
