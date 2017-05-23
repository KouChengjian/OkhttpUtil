package com.okhttp.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.okhttp.config.Constant;

import java.io.Serializable;

/**
 * Created by DELL on 2017/1/17.
 */
public class DownLoad implements Serializable {

    private String url;
    private String path;
    private String name;
    private int currentLength;
    private int totalLength;
    private float percentage;
    private int state = Constant.NONE;
    private int childTaskCount;
    private long date;
    private String lastModify;

    public DownLoad() {

    }

    public DownLoad(String url, String path, String name) {
        this.url = url;
        this.path = path;
        this.name = name;
    }

    public DownLoad(String url, String path, String name, int childTaskCount) {
        this.url = url;
        this.path = path;
        this.name = name;
        this.childTaskCount = childTaskCount;
    }

    public DownLoad(String url, String path, int childTaskCount, String name, int currentLength, int totalLength, String lastModify, long date) {
        this.url = url;
        this.path = path;
        this.childTaskCount = childTaskCount;
        this.currentLength = currentLength;
        this.name = name;
        this.totalLength = totalLength;
        this.lastModify = lastModify;
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrentLength() {
        return currentLength;
    }

    public void setCurrentLength(int currentLength) {
        this.currentLength = currentLength;
    }

    public int getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(int totalLength) {
        this.totalLength = totalLength;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getChildTaskCount() {
        return childTaskCount;
    }

    public void setChildTaskCount(int childTaskCount) {
        this.childTaskCount = childTaskCount;
    }

    public long getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getLastModify() {
        return lastModify;
    }

    public void setLastModify(String lastModify) {
        this.lastModify = lastModify;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(this.url);
//        dest.writeString(this.path);
//        dest.writeString(this.name);
//        dest.writeInt(this.currentLength);
//        dest.writeInt(this.totalLength);
//        dest.writeFloat(this.percentage);
//        dest.writeInt(this.state);
//        dest.writeInt(this.childTaskCount);
//        dest.writeLong(this.date);
//        dest.writeString(this.lastModify);
//    }
//
//    protected DownLoad(Parcel in) {
//        this.url = in.readString();
//        this.path = in.readString();
//        this.name = in.readString();
//        this.currentLength = in.readInt();
//        this.totalLength = in.readInt();
//        this.percentage = in.readFloat();
//        this.state = in.readInt();
//        this.childTaskCount = in.readInt();
//        this.date = in.readLong();
//        this.lastModify = in.readString();
//    }
//
//    public static final Parcelable.Creator<DownLoad> CREATOR = new Parcelable.Creator<DownLoad>() {
//        @Override
//        public DownLoad createFromParcel(Parcel source) {
//            return new DownLoad(source);
//        }
//
//        @Override
//        public DownLoad[] newArray(int size) {
//            return new DownLoad[size];
//        }
//    };

}
