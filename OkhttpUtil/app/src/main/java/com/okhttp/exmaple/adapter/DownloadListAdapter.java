package com.okhttp.exmaple.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.okhttp.bean.DownLoad;
import com.okhttp.exmaple.R;

import java.util.List;

/**
 * Created by DELL on 2017/1/17.
 */
public class DownloadListAdapter extends RecyclerView.Adapter<DownloadListView> {

    Context context;
    List<DownLoad> datas;
    boolean isOpenLoadMore;

    public DownloadListAdapter(Context context, List<DownLoad> datas, boolean isOpenLoadMore){
        this.context = context;
        this.datas = datas;
        this.isOpenLoadMore = isOpenLoadMore;
    }

    @Override
    public DownloadListView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_download_layout,parent , false);
        return new DownloadListView(view);
    }

    @Override
    public void onBindViewHolder(DownloadListView holder, int position) {
        holder.bindData(context , datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
