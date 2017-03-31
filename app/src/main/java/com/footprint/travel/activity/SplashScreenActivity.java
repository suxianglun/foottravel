package com.footprint.travel.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.footprint.travel.R;
import com.footprint.travel.base.BaseActivity;
import com.footprint.travel.utils.DialogUtils;
import com.footprint.travel.utils.LogUtil;
import com.footprint.travel.utils.PermissionsUtil;
import com.footprint.travel.utils.SPUtils;

import java.util.List;
import java.util.Random;

/**
 * @标题: SplashScreenActivity.java
 * @概述:
 * @作者: Allen
 * @日期: 2016/11/10 @版本：
 */
public class SplashScreenActivity extends BaseActivity {

    private SplashScreenActivity activity;

    private String [] permissions;
    /**
     * 定义三个切换动画
     */
    private Animation mFadeIn;

    private Animation mFadeOut;

    private Animation mFadeInScale;

    private ImageView mImageView;

    public static final int REQUEST_PERMISSION_SETTING = 0x002;

    @Override
    protected void initVariables() {
        activity= this;
    }

    @Override
    protected void initDataBinding() {
        setContentView(R.layout.activity_splashscreen);
    }
    @Override
    protected void initView() {
        mImageView= (ImageView) findViewById(R.id.iv_entrance);
        randomSelectImage();
    }
    private void randomSelectImage(){
        int type=new Random().nextInt(2);
        mImageView.setImageResource(type==1? R.mipmap.welcome:R.mipmap.welcome);
    }
    public void checkAndRequestCameraPermission() {
        permissions=new String[]{Manifest.permission.CAMERA};
        PermissionsUtil.setNeedRequestPermissions(permissions);
        PermissionsUtil.checkAndRequestPermissions(activity, new PermissionsUtil.PermissionsCallback() {
            @Override
            public void onPermissionsGranted() {
                startAnimAndToNextActivity();
            }

            @Override
            public void onPermissionsDenied(int requestCode, List<String> perms) {

            }
        });
    }
    /**
     * 开启动画并进入下一页
     * */
    private void startAnimAndToNextActivity() {
        initAnimation();
        setAnimListener();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(2000);
//                    startActivity(new Intent(SplashScreenActivity.this,MainActivity.class));
//                    finish();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }
    /**
     * 初始化进出动画
     * */
    private void initAnimation() {
        mFadeIn = AnimationUtils.loadAnimation(this, R.anim.application_fade_in);
        mFadeIn.setDuration(500);
        mFadeInScale = AnimationUtils.loadAnimation(this,
                R.anim.application_fade_in_scale);
        mFadeInScale.setDuration(2000);
        mFadeOut = AnimationUtils.loadAnimation(this,R.anim.application_fade_out);
        mFadeOut.setDuration(300);
        mImageView.startAnimation(mFadeIn);
    }
    /**
     * 建立监听事件
     */
    private void setAnimListener() {
        mFadeIn.setAnimationListener(new Animation.AnimationListener() {

            public void onAnimationStart(Animation animation) {

            }

            public void onAnimationRepeat(Animation animation) {

            }

            public void onAnimationEnd(Animation animation) {
                mImageView.startAnimation(mFadeInScale);
            }
        });
        mFadeInScale.setAnimationListener(new Animation.AnimationListener() {

            public void onAnimationStart(Animation animation) {


            }

            public void onAnimationRepeat(Animation animation) {

            }

            public void onAnimationEnd(Animation animation) {
                mImageView.startAnimation(mFadeOut);
            }
        });
        mFadeOut.setAnimationListener(new Animation.AnimationListener() {

            public void onAnimationStart(Animation animation) {
//                if (SPUtils.getInstance().isLogined()){
//                    startActivity(new Intent(SplashScreenActivity.this,MainActivity.class));
//                }else {
//                    startActivity(new Intent(SplashScreenActivity.this,LoginActivity.class));
//                }
                startActivity(new Intent(SplashScreenActivity.this,MainActivity.class));
                finish();
            }

            public void onAnimationRepeat(Animation animation) {

            }

            public void onAnimationEnd(Animation animation) {

            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==PermissionsUtil.REQUEST_STATUS_CODE){
            if (grantResults[0]== PackageManager.PERMISSION_GRANTED){//同意
                checkAndRequestCameraPermission();
            }else {//不同意
                LogUtil.d("allen","执行这里showDenyPermissionTipDialog");
                showDenyPermissionTipDialog();
            }
        }
    }

    public void showDenyPermissionTipDialog() {
        DialogUtils.showSimpleDialog(activity, R.string.permission_request, R.string.permission_camera_message,
                R.string.go_set, R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 进入App设置页面
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                        intent.setData(uri);
                        activity.startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

    }



    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= 23) {
            checkAndRequestCameraPermission();
        }else {
            startAnimAndToNextActivity();
        }
    }

    @Override
    public void onClick(View v) {

    }
}
