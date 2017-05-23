package com.okhttp.net.download;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.okhttp.bean.DownLoad;
import com.okhttp.config.Constant;
import com.okhttp.listener.Callback;
import com.okhttp.utils.CacheUtils;
import com.okhttp.utils.IOUtils;

import java.io.File;


public class DownloadProgressHandler {
    private String url;
    private String path;
    private String name;
    private int childTaskCount;

    private Context context;

    private Callback downloadCallback;
    private DownLoad downloadData;

    private DownloadFileTask fileTask;

    private int mCurrentState = Constant.NONE;

    //是否支持断点续传
    private boolean isSupportRange;

    //重新开始下载需要先进行取消操作
    private boolean isNeedRestart;

    //记录已经下载的大小
    private int currentLength = 0;
    //记录文件总大小
    private int totalLength = 0;
    //记录已经暂停或取消的线程数
    private int tempChildTaskCount = 0;

    private long lastProgressTime;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            int mLastSate = mCurrentState;
            mCurrentState = msg.what;
            downloadData.setState(mCurrentState);

            switch (mCurrentState) {
                case Constant.START:
                    Bundle bundle = msg.getData();
                    totalLength = bundle.getInt("totalLength");
                    currentLength = bundle.getInt("currentLength");
                    String lastModify = bundle.getString("lastModify");
                    isSupportRange = bundle.getBoolean("isSupportRange");

                    if (!isSupportRange) {
                        childTaskCount = 1;
                    } else if (currentLength == 0) {
                        CacheUtils.getInstance(context).insertData(new DownLoad(url, path, childTaskCount, name, currentLength, totalLength, lastModify, System.currentTimeMillis()));
                    }
                    if (downloadCallback != null) {
                        downloadCallback.onStart(currentLength, totalLength, IOUtils.getPercentage(currentLength, totalLength));
                    }
                    break;
                case Constant.PROGRESS:
                    synchronized (this) {
                        currentLength += msg.arg1;

                        downloadData.setPercentage(IOUtils.getPercentage(currentLength, totalLength));

                        if (downloadCallback != null && (System.currentTimeMillis() - lastProgressTime >= 20 || currentLength == totalLength)) {
                            downloadCallback.onProgress(currentLength, totalLength, IOUtils.getPercentage(currentLength, totalLength));
                            lastProgressTime = System.currentTimeMillis();
                        }

                        if (currentLength == totalLength) {
                            sendEmptyMessage(Constant.FINISH);
                        }
                    }
                    break;
                case Constant.CANCEL:
                    synchronized (this) {
                        tempChildTaskCount++;
                        if (tempChildTaskCount == childTaskCount || mLastSate == Constant.PAUSE || mLastSate == Constant.ERROR) {
                            tempChildTaskCount = 0;
                            if (downloadCallback != null) {
                                downloadCallback.onProgress(0, totalLength, 0);
                            }
                            currentLength = 0;
                            if (isSupportRange) {
                                CacheUtils.getInstance(context).deleteData(url);
                                IOUtils.deleteFile(new File(path, name + ".temp"));
                            }
                            IOUtils.deleteFile(new File(path, name));
                            if (downloadCallback != null) {
                                downloadCallback.onCancel();
                            }

                            if (isNeedRestart) {
                                isNeedRestart = false;
                                DownloadManger.getInstance(context).innerRestart(url);
                            }
                        }
                    }
                    break;
                case Constant.PAUSE:
                    synchronized (this) {
                        if (isSupportRange) {
                            CacheUtils.getInstance(context).updateData(currentLength, IOUtils.getPercentage(currentLength, totalLength), url);
                        }
                        tempChildTaskCount++;
                        if (tempChildTaskCount == childTaskCount) {
                            if (downloadCallback != null) {
                                downloadCallback.onPause();
                            }
                            tempChildTaskCount = 0;
                        }
                    }
                    break;
                case Constant.FINISH:
                    if (isSupportRange) {
                        IOUtils.deleteFile(new File(path, name + ".temp"));
                        CacheUtils.getInstance(context).deleteData(url);
                    }
                    if (downloadCallback != null) {
                        downloadCallback.onFinish(new File(path, name));
                    }
                    break;
                case Constant.DESTROY:
                    synchronized (this) {
                        if (isSupportRange) {
                            CacheUtils.getInstance(context).updateData(currentLength, IOUtils.getPercentage(currentLength, totalLength), url);
                        }
                    }
                    break;
                case Constant.ERROR:
                    if (isSupportRange) {
                        CacheUtils.getInstance(context).updateData(currentLength, IOUtils.getPercentage(currentLength, totalLength), url);
                    }
                    if (downloadCallback != null) {
                        downloadCallback.onError((String) msg.obj);
                    }
                    break;
            }
        }
    };

    public DownloadProgressHandler(Context context, DownLoad downloadData, Callback downloadCallback) {
        this.context = context;
        this.downloadCallback = downloadCallback;

        this.url = downloadData.getUrl();
        this.path = downloadData.getPath();
        this.name = downloadData.getName();
        this.childTaskCount = downloadData.getChildTaskCount();

        DownLoad dbData = CacheUtils.getInstance(context).getData(url);
        this.downloadData = dbData == null ? downloadData : dbData;
    }

    public Handler getHandler() {
        return mHandler;
    }

    public int getCurrentState() {
        return mCurrentState;
    }

    public DownLoad getDownloadData() {
        return downloadData;
    }

    public void setFileTask(DownloadFileTask fileTask) {
        this.fileTask = fileTask;
    }

    /**
     * 下载中退出时保存数据、释放资源
     */
    public void destroy() {
        if (mCurrentState == Constant.CANCEL || mCurrentState == Constant.PAUSE) {
            return;
        }
        fileTask.destroy();
    }

    /**
     * 暂停（正在下载才可以暂停）
     * 如果文件不支持断点续传则不能进行暂停操作
     */
    public void pause() {
        if (mCurrentState == Constant.PROGRESS) {
            fileTask.pause();
        }
    }

    /**
     * 取消（已经被取消、下载结束则不可取消）
     */
    public void cancel(boolean isNeedRestart) {
        this.isNeedRestart = isNeedRestart;
        if (mCurrentState == Constant.PROGRESS) {
            fileTask.cancel();
        } else if (mCurrentState == Constant.PAUSE || mCurrentState == Constant.ERROR) {
            mHandler.sendEmptyMessage(Constant.CANCEL);
        }
    }
}
