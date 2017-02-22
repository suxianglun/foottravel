package com.footprint.travel.zxing;

import android.graphics.Bitmap;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.footprint.travel.R;
import com.footprint.travel.zxing.android.BeepManager;
import com.footprint.travel.zxing.android.CaptureActivityHandler;
import com.footprint.travel.zxing.android.InactivityTimer;
import com.footprint.travel.zxing.camera.CameraManager;
import com.footprint.travel.zxing.view.ViewfinderView;
import com.footprint.travel.base.BaseActivity;
import com.footprint.travel.utils.LogUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;


/**
 * 这个activity打开相机，在后台线程做常规的扫描；它绘制了一个结果view来帮助正确地显示条形码，在扫描的时候显示反馈信息，
 * 然后在扫描成功的时候覆盖扫描结果
 */
public final class CaptureActivity extends BaseActivity implements SurfaceHolder.Callback {


    private String str ;// 二维码
    // 相机控制
    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Collection<BarcodeFormat> decodeFormats;
    private Map<DecodeHintType, ?> decodeHints;
    private String characterSet;
    // 电量控制、声音、震动控制
    private InactivityTimer inactivityTimer;
    private BeepManager beepManager;
    private static boolean isContinue = true;//跳转帮助页面不finish


    //扫码自动登陆相关参数
    private String event_id;
    private String lid;
    private String name;
    private String url;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initDataBinding() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.scan);
    }

    @Override
    protected void initView() {
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraManager = new CameraManager(getApplication());
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        viewfinderView.setCameraManager(cameraManager);
        handler = null;
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
        }
        beepManager.updatePrefs();
        inactivityTimer.onResume();

        decodeFormats = null;
        characterSet = null;
    }

    @Override
    protected void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        beepManager.close();
        cameraManager.closeDriver();
        if (!hasSurface) {
            SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            surfaceHolder.removeCallback(this);
        }
//        dismissProgressDialog();
        super.onPause();
    }

    @Override
    protected void onDestroy() {

        inactivityTimer.shutdown();
        super.onDestroy();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isContinue) {
            finish();
        }
        isContinue = true;
    }

    /**
     * 初始化Camera
     *
     * @param surfaceHolder
     */
    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            return;
        }
        try {
            // 打开Camera硬件设备
            cameraManager.openDriver(surfaceHolder);
            // 创建一个handler来打开预览，并抛出一个运行时异常
            if (handler == null) {
                handler = new CaptureActivityHandler(this, decodeFormats, decodeHints, characterSet, cameraManager);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
            finish();
        } catch (RuntimeException e) {
            e.printStackTrace();
            finish();
        }
    }

    /**
     * 处理扫描结果
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode, float scaleFactor, boolean isZbar, String res) {
        inactivityTimer.onActivity();
        boolean fromLiveScan = barcode != null;
        // 这里处理解码完成后的结果，此处将参数回传到Activity处理
        if (isZbar) {
            beepManager.playBeepSoundAndVibrate();
            str = res;
            LogUtils.d("allen", "allen str---------->" + str);
            viewfinderView.hideScanLine(true);
//            addurl(str);
        } else if (fromLiveScan) {
            beepManager.playBeepSoundAndVibrate();
            String resultString = result.getText();
            if (resultString.equals("")) {
//                showToast("scan_failed");
            } else {
                str = resultString;
                LogUtils.d("allen", "allen str---------->" + str);
                viewfinderView.hideScanLine(true);
//                addurl(str);
            }

        }

    }



    /**
     * 再次扫码
     */
    public void continuePriveiw() {
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        viewfinderView.hideScanLine(false);
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        if (handler != null) {
            handler.restartPreviewAndDecode();
            handler.sendEmptyMessageDelayed(CaptureActivityHandler.RESET_AUTO_FOCUS, CaptureActivityHandler.AUTO_FOCUS_INTERVAL_MS);//1.5s后
        }
    }



    /**
     * 修改二维码中间的非法字符 0，1
     *
     * @param str
     * @return
     */
    private String getEnteredKey(String str) {
        String enteredKey = str;
        return enteredKey.replace('1', 'I').replace('0', 'O');
    }

    // 截取用户名
    private String validateAndGetUserInPath(String path) {
        if (path == null || !path.startsWith("/")) {
            return null;
        }
        // 路径是“ /用户”，所以删除前导“/” ，并尾随空格
        String user = path.substring(1).trim();
        if (user.length() == 0) {
            return null; // only white spaces.
        }
        return user;
    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

}
