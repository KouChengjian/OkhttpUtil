package com.okhttp;

import android.content.Context;

import com.okhttp.net.Upload.UploadManger;
import com.okhttp.net.download.DownloadManger;

/**
 * Created by DELL on 2017/1/17.
 */
public class DUtil {


    public static DBuilder init(Context context) {
        return new DBuilder(context);
    }

    public static DownloadBuilder initDownloadBuilder(Context context) {
        return new DownloadBuilder(context);
    }

    public static UploadBuilder initUploadBuilder(Context context) {
        return new UploadBuilder(context);
    }

    public static class DBuilder {
        private String url;//下载链接
        private String path;//保存路径
        private String name;//文件名
        private int childTaskCount;//单个任务采用几个线程下载

        private Context context;

        public DBuilder(Context context) {
            this.context = context;
        }

        public DBuilder() {

        }

        public DBuilder url(String url) {
            this.url = url;
            return this;
        }

        public DBuilder path(String path) {
            this.path = path;
            return this;
        }

        public DBuilder name(String name) {
            this.name = name;
            return this;
        }

        public DBuilder childTaskCount(int thread) {
            this.childTaskCount = thread;
            return this;
        }

        public DownloadManger build() {
            DownloadManger downloadManger = DownloadManger.getInstance(context);
            downloadManger.init(url, path, name, childTaskCount);
            return downloadManger;
        }
    }

    public static class DownloadBuilder {
        private String url;//下载链接
        private String path;//保存路径
        private String name;//文件名
        private int childTaskCount;//单个任务采用几个线程下载

        private Context context;

        public DownloadBuilder(Context context) {
            this.context = context;
        }

        public DownloadBuilder() {

        }

        public DownloadBuilder url(String url) {
            this.url = url;
            return this;
        }

        public DownloadBuilder path(String path) {
            this.path = path;
            return this;
        }

        public DownloadBuilder name(String name) {
            this.name = name;
            return this;
        }

        public DownloadBuilder childTaskCount(int thread) {
            this.childTaskCount = thread;
            return this;
        }

        public DownloadManger build() {
            DownloadManger downloadManger = DownloadManger.getInstance(context);
            downloadManger.init(url, path, name, childTaskCount);
            return downloadManger;
        }
    }

    public static class UploadBuilder {
        private String url;//下载链接
        private String path;//保存路径
        private String name;//文件名
        private int childTaskCount;//单个任务采用几个线程下载

        private Context context;

        public UploadBuilder(Context context) {
            this.context = context;
        }

        public UploadBuilder() {

        }

        public UploadBuilder url(String url) {
            this.url = url;
            return this;
        }

        public UploadBuilder path(String path) {
            this.path = path;
            return this;
        }

        public UploadBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UploadBuilder childTaskCount(int thread) {
            this.childTaskCount = thread;
            return this;
        }

        public UploadManger build() {
            UploadManger uploadManger = UploadManger.getInstance(context);
            uploadManger.init(url, path, name, childTaskCount);
            return uploadManger;
        }
    }
}
