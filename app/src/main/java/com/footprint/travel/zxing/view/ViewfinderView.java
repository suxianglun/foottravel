/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.footprint.travel.zxing.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.footprint.travel.R;
import com.footprint.travel.zxing.camera.CameraManager;
import com.google.zxing.ResultPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder
 * rectangle and partial transparency outside it, as well as the laser scanner
 * animation and result points. 这是一个位于相机顶部的预览view,它增加了一个外部部分透明的取景框，以及激光扫描动画和结果组件
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class ViewfinderView extends View {

    private static final long ANIMATION_DELAY = 10L;
    private static final int CURRENT_POINT_OPACITY = 0xA0;
    private static final int MAX_RESULT_POINTS = 20;
    private static final int POINT_SIZE = 6;

    private CameraManager cameraManager;
    private final Paint paint;
    private Bitmap resultBitmap;
    private final int maskColor; // 取景框外的背景颜色
    private final int resultColor;// result Bitmap的颜色
    private final int resultPointColor; // 特征点的颜色
    private final int frameColor;//取景框边框的颜色
    private List<ResultPoint> possibleResultPoints;
    private List<ResultPoint> lastPossibleResultPoints;
    // 扫描线
    Drawable zx_code_line;
    Drawable zx_code_kuang;

    private Rect middleRect = new Rect();
    private Rect lineRect = new Rect();

    private int measureedWidth;
    private int measureedHeight;
    private int lineHeight = 6;
    private boolean isScanHide;

    public ViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Initialize these once for performance rather than calling them every
        // time in onDraw().
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Resources resources = getResources();
        frameColor = resources.getColor(R.color.white);//取景框边框颜色
        resultPointColor = resources.getColor(R.color.possible_result_points);
        maskColor = resources.getColor(R.color.viewfinder_mask2);//取景框外颜色
        resultColor = resources.getColor(R.color.result_view);
        possibleResultPoints = new ArrayList<ResultPoint>(5);
        lastPossibleResultPoints = null;
        zx_code_kuang = context.getResources().getDrawable(R.drawable.ic_zx_code_kuang);
        zx_code_line = context.getResources().getDrawable(R.mipmap.zx_code_line);

    }

    public void setCameraManager(CameraManager cameraManager) {
        this.cameraManager = cameraManager;
    }

    public void hideScanLine(boolean isScanHide){
        this.isScanHide = isScanHide;
    }

    @SuppressLint ("DrawAllocation")
    @Override
    public void onDraw(Canvas canvas) {
        if (cameraManager == null) {
            return; // not ready yet, early draw before done configuring
        }

        // frame为取景框
        Rect frame = cameraManager.getFramingRect();
        Rect previewFrame = cameraManager.getFramingRectInPreview();
        if (frame == null || previewFrame == null) {
            return;
        }
        // 二维码背景改变
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        paint.setColor(resultBitmap != null ? resultColor : maskColor);
        //画出扫描框外面的阴影部分，共四个部分，扫描框的上面到屏幕上面，扫描框的下面到屏幕下面
        //扫描框的左边面到屏幕左边，扫描框的右边到屏幕右边
//        canvas.drawRect(0, 0, width, frame.top, paint);
//        canvas.drawRect(0, frame.top, frame.left, frame.bottom, paint);
//        canvas.drawRect(frame.right, frame.top, width, frame.bottom, paint);
//        canvas.drawRect(0, frame.bottom, width, height, paint);
        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
        canvas.drawRect(0, frame.bottom + 1, width, height, paint);
        if (resultBitmap != null) {
            // 如果有二维码结果的Bitmap，在扫取景框内绘制不透明的result Bitmap
            paint.setAlpha(CURRENT_POINT_OPACITY);
            canvas.drawBitmap(resultBitmap, null, frame, paint);
        } else {
            drawFrameBounds(canvas);
            if (!isScanHide){
                drawScanLight(canvas, frame);
            }
            drawPoint(canvas, frame, previewFrame);
            postInvalidateDelayed(ANIMATION_DELAY, frame.left - POINT_SIZE, frame.top - POINT_SIZE, frame.right
                    + POINT_SIZE, frame.bottom + POINT_SIZE);
        }
    }

    @SuppressLint ("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureedWidth = MeasureSpec.getSize(widthMeasureSpec);
        measureedHeight = MeasureSpec.getSize(heightMeasureSpec);

        Point screenPoint = cameraManager.getScreenResolution();


        if (screenPoint != null) {
            int width = screenPoint.x * 1 / 5;
            int height = (screenPoint.y) * 2 / 4;
            int topOffset = (screenPoint.y - height) / 3+40;
            middleRect = new Rect(width, topOffset+200, screenPoint.x - width+40, topOffset + screenPoint.x - width * 2+240);
        } else {
            int width = measureedWidth * 1 / 5;
            int height = (measureedHeight) * 2 / 4;
            int topOffset = (measureedHeight - height) / 3+40;
            middleRect = new Rect(width-40, topOffset+200, measureedWidth - width+40, topOffset + measureedWidth - width * 2+230);
        }

        lineRect.set(middleRect);
        lineRect.bottom = lineRect.top + lineHeight;
    }

    /**
     * 绘制取景框边框
     *
     * @param canvas
     */
    private void drawFrameBounds(Canvas canvas) {
        paint.setColor(frameColor);
        zx_code_kuang.setBounds(middleRect);
        zx_code_kuang.draw(canvas);
    }

    /**
     * 绘制扫描线周围的特征点
     * @param canvas
     * @param frame
     * @param previewFrame
     */
    private void drawPoint(Canvas canvas, Rect frame, Rect previewFrame) {
        float scaleX = frame.width() / (float) previewFrame.width();
        float scaleY = frame.height() / (float) previewFrame.height();

        List<ResultPoint> currentPossible = possibleResultPoints;
        List<ResultPoint> currentLast = lastPossibleResultPoints;
        int frameLeft = frame.left;
        int frameTop = frame.top;
        if (currentPossible.isEmpty()) {
            lastPossibleResultPoints = null;
        } else {
            possibleResultPoints = new ArrayList<ResultPoint>(5);
            lastPossibleResultPoints = currentPossible;
            paint.setAlpha(CURRENT_POINT_OPACITY);
            paint.setColor(resultPointColor);
            synchronized (currentPossible) {
                for (ResultPoint point : currentPossible) {
                    canvas.drawCircle(frameLeft + (int) (point.getX() * scaleX), frameTop
                            + (int) (point.getY() * scaleY), POINT_SIZE, paint);
                }
            }
        }
        if (currentLast != null) {
            paint.setAlpha(CURRENT_POINT_OPACITY / 2);
            paint.setColor(resultPointColor);
            synchronized (currentLast) {
                float radius = POINT_SIZE / 2.0f;
                for (ResultPoint point : currentLast) {
                    canvas.drawCircle(frameLeft + (int) (point.getX() * scaleX), frameTop
                            + (int) (point.getY() * scaleY), radius, paint);
                }
            }
        }
    }

    /**
     * 绘制移动扫描线
     *
     * @param canvas
     * @param frame
     */
    private void drawScanLight(Canvas canvas, Rect frame) {
        if (lineRect.bottom < middleRect.bottom) {
            zx_code_line.setBounds(lineRect);
            lineRect.top = lineRect.top + 6;
            lineRect.bottom = lineRect.bottom + 6;
        } else{
            lineRect.set(middleRect);
        }
        lineRect.bottom = lineRect.top + lineHeight;
        zx_code_line.setBounds(lineRect);
        zx_code_line.draw(canvas);
    }

    public void drawViewfinder() {
        Bitmap resultBitmap = this.resultBitmap;
        this.resultBitmap = null;
        if (resultBitmap != null) {
            resultBitmap.recycle();
        }
        invalidate();
    }

    /**
     * Draw a bitmap with the result points highlighted instead of the live
     * scanning display.
     *
     * @param barcode An image of the decoded barcode.
     */
    public void drawResultBitmap(Bitmap barcode) {
        resultBitmap = barcode;
        invalidate();
    }

    public void addPossibleResultPoint(ResultPoint point) {
        List<ResultPoint> points = possibleResultPoints;
        synchronized (points) {
            points.add(point);
            int size = points.size();
            if (size > MAX_RESULT_POINTS) {
                // trim it
                points.subList(0, size - MAX_RESULT_POINTS / 2).clear();
            }
        }
    }

}
