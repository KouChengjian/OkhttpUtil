package com.okhttp.exmaple;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.okhttp.bean.DownLoad;
import com.okhttp.exmaple.adapter.DownloadListAdapter;
import com.okhttp.net.download.DownloadManger;

import java.util.ArrayList;
import java.util.List;

public class TaskManageActivity extends AppCompatActivity {

    private String url1 = "http://1.82.242.43/imtt.dd.qq.com/16891/DC9E925209B19E7913477E7A0CCE6E52.apk";//欢乐斗地主
    private String url2 = "http://117.23.1.170/imtt.dd.qq.com/16891/37F5264B6EDC71F9A7888B5017A5A6C1.apk";//球球大作战
    private String url3 = "http://117.23.1.172/imtt.dd.qq.com/16891/8AFB093FEFF9DE2A81EDC28EB1AF89C6.apk";//节奏大师
    private String url4 = "http://1.82.242.43/imtt.dd.qq.com/16891/72517F996BB172A75F992962849B051A.apk";//部落冲突
    private String url5 = "http://1.82.242.43/imtt.dd.qq.com/16891/1DF32C4166A703FC2AB7CCE33102123E.apk";//捕鱼达人

    private String path = Environment.getExternalStorageDirectory() + "/DUtil/";

    private RecyclerView downloadList;
    private DownloadListAdapter downloadListAdapter;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_manage);


        downloadList = (RecyclerView) findViewById(R.id.download_list);
        final List<DownLoad> datas = new ArrayList<>();

//        if (CacheUtils.getInstance(mContext).getData(url1) != null){
//            datas.add(CacheUtils.getInstance(mContext).getData(url1));
//        }else{
            datas.add(new DownLoad(url1, path, "欢乐斗地主.apk"));
//        }

//        if (CacheUtils.getInstance(mContext).getData(url2) != null){
//            datas.add(CacheUtils.getInstance(mContext).getData(url2));
//        }else{
            datas.add(new DownLoad(url2, path, "球球大作战.apk"));
//        }

//        if (CacheUtils.getInstance(mContext).getData(url3) != null){
//            datas.add(CacheUtils.getInstance(mContext).getData(url3));
//        }else{
            datas.add(new DownLoad(url3, path, "节奏大师.apk"));
//        }

//        if (CacheUtils.getInstance(mContext).getData(url4) != null){
//            datas.add(CacheUtils.getInstance(mContext).getData(url4));
//        }else{
            datas.add(new DownLoad(url4, path, "部落冲突.apk"));
//        }

//        if (CacheUtils.getInstance(mContext).getData(url5) != null){
//            datas.add(CacheUtils.getInstance(mContext).getData(url5));
//        }else{
            datas.add(new DownLoad(url5, path, "捕鱼达人.apk"));
//        }

        downloadListAdapter = new DownloadListAdapter(this, datas, false);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        downloadList.setLayoutManager(layoutManager);
        downloadList.setAdapter(downloadListAdapter);


    }

    @Override
    protected void onDestroy() {
        DownloadManger.getInstance(mContext).destroy(url1, url2, url3, url4, url5);
        super.onDestroy();
    }
}
