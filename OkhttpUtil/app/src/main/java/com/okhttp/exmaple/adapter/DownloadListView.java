package com.okhttp.exmaple.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.okhttp.bean.DownLoad;
import com.okhttp.exmaple.R;
import com.okhttp.listener.Callback;
import com.okhttp.net.download.DownloadManger;
import com.okhttp.utils.IOUtils;

import java.io.File;

/**
 * Created by DELL on 2017/1/17.
 */
public class DownloadListView extends RecyclerView.ViewHolder {

    public TextView name;
    TextView download_size;
    TextView percentage;
    ProgressBar progress_bar;
    TextView start;
    TextView pause;
    TextView resume;
    TextView cancel;
    TextView restart;


    public DownloadListView(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.name);
        download_size = (TextView) itemView.findViewById(R.id.download_size);
        percentage = (TextView) itemView.findViewById(R.id.percentage);
        progress_bar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
        start = (TextView) itemView.findViewById(R.id.start);
        pause = (TextView) itemView.findViewById(R.id.pause);
        resume = (TextView) itemView.findViewById(R.id.resume);
        cancel = (TextView) itemView.findViewById(R.id.cancel);
        restart = (TextView) itemView.findViewById(R.id.restart);
    }

    public void bindData(final Context mContext , final DownLoad data){
        name.setText(data.getName());
        download_size.setText(IOUtils.formatSize(data.getCurrentLength()) + "/"
                + IOUtils.formatSize(data.getTotalLength()));
        percentage.setText(data.getPercentage() + "%");
        progress_bar.setProgress((int) data.getPercentage());



        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadManger.getInstance(mContext).start(data.getUrl());
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadManger.getInstance(mContext).pause(data.getUrl());
            }
        });
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadManger.getInstance(mContext).resume(data.getUrl());
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadManger.getInstance(mContext).cancel(data.getUrl());
            }
        });
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadManger.getInstance(mContext).restart(data.getUrl());
            }
        });

        setListener(mContext , data);
    }


    private void setListener(final Context mContext, final DownLoad downloadData) {

        DownloadManger.getInstance(mContext).setOnDownloadCallback(downloadData, new Callback() {
            @Override
            public void onStart(long currentSize, long totalSize, float progress) {
                name.setText(downloadData.getName() + ":准备中");
            }

            @Override
            public void onProgress(long currentSize, long totalSize, float progress) {
                name.setText( downloadData.getName() + ":下载中");
                download_size.setText(IOUtils.formatSize(currentSize) + "/" + IOUtils.formatSize(totalSize));
                percentage.setText( progress + "%");
                progress_bar.setProgress((int) progress);
            }

            @Override
            public void onPause() {
                name.setText( downloadData.getName() + ":已暂停");
            }

            @Override
            public void onCancel() {
                name.setText( downloadData.getName() + ":已取消");
            }

            @Override
            public void onFinish(File file) {
                name.setText( downloadData.getName() + ":已完成");
            }

            @Override
            public void onWait() {
                name.setText( downloadData.getName() + ":等待中");
            }

            @Override
            public void onError(String error) {
                name.setText( downloadData.getName() + ":出错");
            }
        });
    }
}
