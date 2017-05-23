package com.okhttp.net.Upload;

import android.content.Context;

import com.okhttp.bean.DownLoad;
import com.okhttp.bean.Upload;
import com.okhttp.listener.Callback;
import com.okhttp.net.download.DownloadFileTask;
import com.okhttp.net.download.DownloadProgressHandler;
import com.okhttp.utils.ThreadPool;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DELL on 2017/1/18.
 */
public class UploadManger {

    private Context context;
    private Upload upload;
    private volatile static UploadManger uploadManger;

    //private Map<String, DownloadProgressHandler> progressHandlerMap = new HashMap<>();//保存任务的进度处理对象
    private Map<String, Upload> downloadDataMap = new HashMap<>();//保存任务数据
    private Map<String, Callback> callbackMap = new HashMap<>();//保存任务回调
    //private Map<String, DownloadFileTask> fileTaskMap = new HashMap<>();//保存下载线程


    public static UploadManger getInstance(Context context) {
        if (uploadManger == null) {
            synchronized (UploadManger.class) {
                if (uploadManger == null) {
                    uploadManger = new UploadManger(context);
                }
            }
        }
        return uploadManger;
    }

    private UploadManger(Context context) {
        this.context = context;
    }

    /**
     * 配置线程池
     * @param maxPoolSize
     */
    public void setTaskPoolSize(int corePoolSize, int maxPoolSize) {
        if (maxPoolSize > corePoolSize && maxPoolSize * corePoolSize != 0) {
            ThreadPool.getInstance().setCorePoolSize(corePoolSize);
            ThreadPool.getInstance().setMaxPoolSize(maxPoolSize);
        }
    }

    public void init(String url, String path, String name, int childTaskCount) {
        upload = new Upload();
        upload.setUrl(url);
        upload.setPath(path);
        upload.setName(name);
        upload.setChildTaskCount(childTaskCount);
    }

    /**
     * 链式开启下载
     *
     */
    public UploadManger start(Callback downloadCallback) {
        execute(upload, downloadCallback);
        return uploadManger;
    }







    /**
     * 执行下载任务
     */
    private void execute(Upload upload, Callback downloadCallback) {
//        //防止同一个任务多次下载
//        if (progressHandlerMap.get(downloadData.getUrl()) != null) {
//            return;
//        }
//
//        //默认每个任务不通过多个异步任务下载
//        if (downloadData.getChildTaskCount() == 0) {
//            downloadData.setChildTaskCount(1);
//        }
//
//        DownloadProgressHandler progressHandler = new DownloadProgressHandler(context, downloadData, downloadCallback);
//        DownloadFileTask fileTask = new DownloadFileTask(context, downloadData, progressHandler.getHandler());
//        progressHandler.setFileTask(fileTask);
//
//        downloadDataMap.put(downloadData.getUrl(), downloadData);
//        callbackMap.put(downloadData.getUrl(), downloadCallback);
//        fileTaskMap.put(downloadData.getUrl(), fileTask);
//        progressHandlerMap.put(downloadData.getUrl(), progressHandler);
//
//        ThreadPool.getInstance().getThreadPoolExecutor().execute(fileTask);
//
//        //如果正在下载的任务数量等于线程池的核心线程数，则新添加的任务处于等待状态
//        if (ThreadPool.getInstance().getThreadPoolExecutor().getActiveCount() == ThreadPool.getInstance().getCorePoolSize()) {
//            downloadCallback.onWait();
//        }
    }

}
