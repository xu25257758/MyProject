package com.example.myproject.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;

import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.OnProgressListener;
import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;
import com.downloader.Progress;
import com.downloader.Status;
import com.downloader.database.DownloadModel;
import com.downloader.internal.ComponentHolder;
import com.downloader.utils.Utils;
import com.example.library.ui.activity.BaseActivity;
import com.example.library.util.FileUtil;
import com.example.library.util.LogUtil;
import com.example.library.util.UIUtils;
import com.example.myproject.R;

import java.io.File;
import java.security.Permission;

import butterknife.BindView;
import butterknife.OnClick;

public class PrDownloaderActivity extends BaseActivity {
    @BindView(R.id.downloader_progress)
    ProgressBar progressBar;
    private String url;
    private String fileName;
    private String dirPath;
    private int id;

    @Override
    public int getContentViewId() {
        return R.layout.activity_prdownloader;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDownloader();

    }

    private void initDownloader() {
        url = "http://pop.52dongxin.net/download/plugin/dxvp_6.0.apk";
        fileName = url.substring(url.lastIndexOf("/") + 1);
        dirPath = getExternalFilesDir(Environment.DIRECTORY_MOVIES).getPath();
        int id = Utils.getUniqueId(url, dirPath, fileName);
        try {
            PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                    .setDatabaseEnabled(true)
                    .setConnectTimeout(30_000)
                    .setReadTimeout(30_000)
                    .build();
            ComponentHolder.getInstance().init(this,config);
            DownloadModel downloadModel = ComponentHolder.getInstance().getDbHelper().find(id);
            LogUtil.e("path", downloadModel.toString());
        }catch (Exception e){

        }

    }

    @OnClick({R.id.downloader_start,R.id.downloader_pause})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.downloader_start:
//                boolean check = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 0x11, new OnPermissionCallback() {
//                    @Override
//                    public void callback(int requestCode) {
//                        downloadApk();
//                    }
//                });
//                if (check) {
//                    downloadApk();
//                }
                downloadApk();
                break;
            case R.id.downloader_pause:
                PRDownloader.pause(id);
                break;
        }
    }

    private void downloadApk() {
        PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                .setDatabaseEnabled(true)
                .setConnectTimeout(30_000)
                .setReadTimeout(30_000)
                .build();
        PRDownloader.initialize(UIUtils.getContext(), config);
        id = PRDownloader.download(url, dirPath, fileName)
                .build()
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {
                        progressBar.setProgress((int) (100 * progress.currentBytes / progress.totalBytes));
                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        progressBar.setProgress(100);
                        UIUtils.showToast("完成");
                        LogUtil.e("progress", "完成");
                    }

                    @Override
                    public void onError(Error error) {
                        UIUtils.showToast("error:" + error.isServerError() + "," + error.isConnectionError());
                        LogUtil.e("progress", "error:" + error.isServerError() + "," + error.isConnectionError());
                    }
                });
        LogUtil.e("id", id + "");
    }
}
