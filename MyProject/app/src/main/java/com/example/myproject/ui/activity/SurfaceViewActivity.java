package com.example.myproject.ui.activity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.library.ui.activity.BaseActivity;
import com.example.library.util.UIUtils;
import com.example.myproject.R;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

public class SurfaceViewActivity extends BaseActivity implements SurfaceHolder.Callback {

    @BindView(R.id.surface_view_camera2)
    SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private Paint mPaint;
    private boolean run;

    private Timer mTimer;
    private MyTimerTask mTimerTask;
    private int dx, dy;

    @Override
    public int getContentViewId() {
        return R.layout.activity_surfaceview;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceView.setZOrderOnTop(true);

        //动态绘制正弦波的定时器
        mTimer = new Timer();
        mTimerTask = new MyTimerTask();

        initPaint();
    }

    @OnClick({R.id.surface_simple, R.id.surface_time})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.surface_simple:
                break;
            case R.id.surface_time:
                mTimer.schedule(mTimerTask, 0, 1000);//动态绘制正弦波
                break;
        }
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(4);
    }

    @Override
    public void surfaceCreated(final SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mSurfaceHolder.removeCallback(this);
        mTimer.cancel();
        run = false;
    }

    class MyTimerTask extends TimerTask {
        @Override
        public void run() {

            SimpleDraw();
        }

    }

    /*
     * 绘制指定区域
     */
    void SimpleDraw() {
        Canvas canvas = mSurfaceHolder.lockCanvas(new Rect(0, 0,
                UIUtils.getScreenWidth(), UIUtils.getScreenHeight()));// 关键:获取画布
        canvas.drawColor(Color.WHITE);
        canvas.drawCircle(dx, dy, 30, mPaint);
        dx += 40;
        dy += 40;
        mSurfaceHolder.unlockCanvasAndPost(canvas);// 解锁画布，提交画好的图像
    }

    void ClearDraw() {
        Canvas canvas = mSurfaceHolder.lockCanvas(null);
        canvas.drawColor(Color.BLACK);// 清除画布
        mSurfaceHolder.unlockCanvasAndPost(canvas);

    }
}
