/*
 * Copyright (C) 2010 ZXing authors
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

package com.footprint.travel.zxing.decoding;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.footprint.travel.R;
import com.footprint.travel.zxing.CaptureActivity;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;

import java.util.Map;

public final class DecodeHandler extends Handler {

    private static final String TAG = DecodeHandler.class.getSimpleName();

    private final CaptureActivity activity;
    private final MultiFormatReader multiFormatReader;
    private boolean running = true;
    private boolean zxingDecode = false;
    private int decode_number = 1;//解码失败次数

    DecodeHandler(CaptureActivity activity, Map<DecodeHintType, Object> hints) {
        multiFormatReader = new MultiFormatReader();
        multiFormatReader.setHints(hints);
        this.activity = activity;
    }

    @Override
    public void handleMessage(Message message) {
        if (!running) {
            return;
        }
        switch (message.what) {
            case R.id.decode:
                decode((byte[]) message.obj, message.arg1, message.arg2);
                break;
            case R.id.quit:
                running = false;
                if (Looper.myLooper() != null) {
                    Looper.myLooper().quit();
                }
                break;
        }
    }

    /**
     * Decode the data within the viewfinder rectangle, and time how long it
     * took. For efficiency, reuse the same reader objects from one decode to
     * the next.
     *
     * @param data   The YUV preview frame.
     * @param width  The width of the preview frame.
     * @param height The height of the preview frame.
     */
    private void decode(byte[] data, int width, int height) {
        Result rawResult = null;
        if (data == null) {
            Handler handler = activity.getHandler();
            if (handler != null) {
                Message message = Message.obtain(handler, R.id.decode_failed);
                message.sendToTarget();
            }
            return;
        }
        /***************竖屏更改3**********************/
        byte[] rotatedData = new byte[data.length];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++)
                rotatedData[x * height + height - y - 1] = data[x + y * width];
        }
        int tmp = width; // Here we are swapping, that's the difference to #11
        width = height;
        height = tmp;
        /**********************************/

        if (decode_number % 5 == 0) {
            if (zxingDecode) {
                zxingDecode = false;
            } else {
                zxingDecode = true;
            }
        }


//        if (!zxingDecode) {//zbar解码
//			LogUtils.e("－－－－－－－－－－－－－－－－－－－Zbar解码－－－－－－－－－－－－－－－－－－－－");
//            try {
//                ZBarDecoder zBarDecoder = new ZBarDecoder();
//                Rect rect = activity.getCameraManager().getFramingRect();
//                String resultString = zBarDecoder.decodeCrop(rotatedData, width, height, rect.left, rect.top, rect.width(), rect.height());
//                Handler handler = activity.getHandler();
//                if (StringUtil.isNotNull(resultString)) {
//                    // Don't log the barcode contents for security.
//                    if (handler != null) {//解码成功
//                        decode_number = 0;
//                        Message message = Message.obtain(handler,
//                                R.id.decode_succeeded, rawResult);
//                        Bundle bundle = new Bundle();
//                        bundle.putBoolean("isZbar", true);
//                        bundle.putString("result", resultString);
//                        message.setData(bundle);
//                        message.sendToTarget();
//                    }
//                } else {//解码失败
//                    if (handler != null) {
//                        decode_number++;
//                        Message message = Message.obtain(handler, R.id.decode_failed);
//                        message.sendToTarget();
//                    }
//                }
//            } catch (Exception e) {
//
//            }
//        } else {//zxing解码
//			LogUtils.e("－－－－－－－－－－－－－－－－－－－Zxing解码－－－－－－－－－－－－－－－－－－－－");
//            PlanarYUVLuminanceSource source = activity.getCameraManager()
//                    .buildLuminanceSource(rotatedData, width, height);
//            if (source != null) {
//                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
//                try {
//                    rawResult = multiFormatReader.decodeWithState(bitmap);
//                } catch (ReaderException re) {
//                    // continue
//                } finally {
//                    multiFormatReader.reset();
//                }
//            }
//            Handler handler = activity.getHandler();
//            if (rawResult != null) {
//                if (handler != null) {//解码成功
//                    decode_number = 0;
//                    Message message = Message.obtain(handler,
//                            R.id.decode_succeeded, rawResult);
//                    Bundle bundle = new Bundle();
//                    bundleThumbnail(source, bundle);
//                    bundle.putBoolean("bundle", false);
//                    message.setData(bundle);
//                    message.sendToTarget();
//                }
//            } else {//解码失败
//                if (handler != null) {
//                    decode_number++;
//                    Message message = Message.obtain(handler, R.id.decode_failed);
//                    message.sendToTarget();
//                }
//            }
//        }
//		if (decode_number % 20 ==0){//2.0s后重新聚焦
//			activity.getCameraManager().resetFocus();
//		}
    }

//    private static void bundleThumbnail(PlanarYUVLuminanceSource source,
//                                        Bundle bundle) {
//        try {
//            int[] pixels = source.renderThumbnail();
//            int width = source.getThumbnailWidth();
//            int height = source.getThumbnailHeight();
//            Bitmap bitmap = Bitmap.createBitmap(pixels, 0, width, width, height,
//                    Bitmap.Config.ARGB_8888);
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
//            bundle.putByteArray(DecodeThread.BARCODE_BITMAP, out.toByteArray());
//            bundle.putFloat(DecodeThread.BARCODE_SCALED_FACTOR, (float) width
//                    / source.getWidth());
//        } catch (Exception e) {
//            //可能造成空指针
//        }
//    }

}


