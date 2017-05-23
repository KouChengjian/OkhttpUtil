package com.okhttp.exmaple;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SingleTaskUpLoadActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mTip;
    private TextView mProgress;
    private TextView mPause;
    private TextView mResume;
    private TextView mCancel;
    private TextView mRestart;
    private ProgressBar progressBar;

    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_task_upload);

        mTip = (TextView) findViewById(R.id.tip);
        mProgress = (TextView) findViewById(R.id.progress);
        mPause = (TextView) findViewById(R.id.pause);
        mResume = (TextView) findViewById(R.id.resume);
        mCancel = (TextView) findViewById(R.id.cancel);
        mRestart = (TextView) findViewById(R.id.restart);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mRestart.setOnClickListener(this);

    }


    private void uploadMultiFile() {
        final String url = "http://api.nx87.cn/up";
        File file = new File("/storage/emulated/0/test.jpg");
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "test.jpg", fileBody)
                .addFormDataPart("imagetype", "hahah")
                .build();



        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();


        final okhttp3.OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient = httpBuilder
                //设置超时
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG", "uploadMultiFile() e=" + e);
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("TAG", "uploadMultiFile() response=" + response.body().string());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.restart:
                uploadMultiFile();
                break;
        }
    }
}
