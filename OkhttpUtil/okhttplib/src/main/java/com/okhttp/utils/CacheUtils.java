package com.okhttp.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.okhttp.bean.DownLoad;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2017/1/17.
 */
public class CacheUtils {

    Context context;
    ACache mCache;
    static CacheUtils cacheUtils;

    public CacheUtils(Context context){
        this.context = context;
        mCache = ACache.get(context);
    }

    public static CacheUtils getInstance(Context context) {
        if (cacheUtils == null) {
            synchronized (CacheUtils.class) {
                if (cacheUtils == null) {
                    cacheUtils = new CacheUtils(context);
                }
            }
        }
        return cacheUtils;
    }

    /**
     * 保存下载信息
     */
    public void insertData(DownLoad data) {
        mCache.put(data.getUrl(), data);
    }

    public void insertDatas(List<DownLoad> datas) {
        for (DownLoad data : datas) {
            insertData(data);
        }
    }

    /**
     * 获得url对应的下载数据
     */
    public DownLoad getData(String url) {
        DownLoad data = (DownLoad) mCache.getAsObject(url);
        return data;
    }

    /**
     * 获得全部下载数据
     *
     * @return
     */
    public List<DownLoad> getAllData() {
        List<DownLoad> list = new ArrayList<>();
        return list;
    }

    /**
     * 更新下载信息
     */
    public void updateData(int currentSize, float percentage, String url) {
        DownLoad data = (DownLoad) mCache.getAsObject(url);
        data.setPercentage(percentage);
        data.setCurrentLength(currentSize);
        mCache.remove(url);
        insertData(data);
    }

    /**
     * 删除下载信息
     */
    public void deleteData(String url) {
        mCache.remove(url);
    }

}
