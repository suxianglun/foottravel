/**
 *
 */
package com.footprint.travel.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * @author Allen
 * @brief Bitmap与DrawAble与byte[]与InputStream与String之间的转换工具类
 */
public class FormatTools {

    final int BUFFER_SIZE = 4096;
    private static FormatTools tools;

    public static FormatTools getInstance() {
        if (tools == null) {
            tools = new FormatTools();
            return tools;
        }
        return tools;
    }

    /**
     * @param b byte数组
     * @return InputStream
     * @brief 将byte[]转换成InputStream
     */
    public InputStream Bytes2InputStream(byte[] b) {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        return bais;
    }

    /**
     * @param b byte数组
     * @return String
     * @throws Exception
     * @brief 将byte数组转换成String
     */
    public String Bytes2String(byte[] b) {
        InputStream is;
        try {
            is = Bytes2InputStream(b);
            return InputStream2String(is);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * @param b byte数组
     * @return Bitmap
     * @brief byte[]转换成Bitmap
     */
    public Bitmap Bytes2Bitmap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        }
        return null;
    }

    /**
     * @param b byte数组
     * @return Drawable
     * @brief byte[]转换成Drawable
     */
    public Drawable Bytes2Drawable(byte[] b) {
        Bitmap bitmap = this.Bytes2Bitmap(b);
        return this.Bitmap2Drawable(bitmap);
    }

    /**
     * @param d 图片对象（Drawable）
     * @return InputStream
     * @brief Drawable转换成InputStream
     */
    public InputStream Drawable2InputStream(Drawable d) {
        Bitmap bitmap = this.Drawable2Bitmap(d);
        return this.Bitmap2InputStream(bitmap);
    }

    /**
     * @param d 图片对象（Drawable）
     * @return byte[]
     * @brief Drawable转换成byte[]
     */
    public byte[] Drawable2Bytes(Drawable d) {
        Bitmap bitmap = this.Drawable2Bitmap(d);
        return this.Bitmap2Bytes(bitmap);
    }

    /**
     * @param d 图片对象（Drawable）
     * @return Bitmap
     * @brief Drawable转换成Bitmap
     */
    public Bitmap Drawable2Bitmap(Drawable d) {
        Bitmap bitmap = Bitmap.createBitmap(d.getIntrinsicWidth(), d
                        .getIntrinsicHeight(),
                d.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        d.draw(canvas);
        return bitmap;
    }

    /**
     * @param bm 图片对象（Bitmap）
     * @return byte[]
     * @brief Bitmap转换成byte[]
     */
    public byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(CompressFormat.PNG, 50, baos);
        return baos.toByteArray();
    }

    /**
     * @param bm 图片对象（Bitmap）
     * @return Drawable
     * @brief Bitmap转换成Drawable
     */
    public Drawable Bitmap2Drawable(Bitmap bm) {
        BitmapDrawable bd = new BitmapDrawable(bm);
        Drawable d = (Drawable) bd;
        return d;
    }

    /**
     * @param bm      图片对象（Bitmap）
     * @param quality 图片质量
     * @return InputStream
     * @brief 将Bitmap转换成InputStream
     */
    public InputStream Bitmap2InputStream(Bitmap bm, int quality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(CompressFormat.PNG, quality, baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }

    /**
     * @param bm 图片对象（Bitmap）
     * @return InputStream
     * @brief 将Bitmap转换成InputStream
     */
    public InputStream Bitmap2InputStream(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(CompressFormat.JPEG, 100, baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }

    /**
     * @param in 字符串
     * @return byte[]
     * @throws Exception
     * @brief 将String转换成byte[]
     */
    public byte[] String2Bytes(String in) {
        try {
            return in.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * @param in 转化字符串
     * @return InputStream
     * @throws Exception
     * @brief 将String转换成InputStream
     */
    public InputStream String2InputStream(String in) throws Exception {
        ByteArrayInputStream is = new ByteArrayInputStream(in.getBytes());
        return is;
    }

    /**
     * @param in ：转化字符串，encoding：编码格式
     * @return InputStream
     * @throws Exception
     * @brief 将String转换成InputStream
     */
    public InputStream String2InputStream(String in, String encoding)
            throws Exception {
        ByteArrayInputStream is = new ByteArrayInputStream(
                in.getBytes(encoding));
        return is;
    }

    /**
     * @param is 数据流
     * @return byte[]
     * @brief 将InputStream转换成byte[]
     */
    public byte[] InputStream2Bytes(InputStream is) {
        String str = "";
        byte[] readByte = new byte[1024];
        int readCount = -1;
        try {
            while ((readCount = is.read(readByte, 0, 1024)) != -1) {
                str += new String(readByte).trim();
            }
            return str.getBytes();
        } catch (Exception e) {
            //e.printStackTrace();
            LogUtils.e("TAG", "异常");
        }
        return null;
    }

    /**
     * @param is 数据流
     * @return Bitmap
     * @brief 将InputStream转换成Bitmap
     */
    public Bitmap InputStream2Bitmap(InputStream is) {
        return BitmapFactory.decodeStream(is);
    }

    /**
     * @param is 数据流
     * @return Drawable
     * @brief InputStream转换成Drawable
     */
    public Drawable InputStream2Drawable(InputStream is) {
        Bitmap bitmap = this.InputStream2Bitmap(is);
        return this.Bitmap2Drawable(bitmap);
    }

    /**
     * @param in       数据流
     * @param encoding 编码格式
     * @return String
     * @throws Exception
     * @brief 将InputStream转换成某种字符编码的String
     */
    public String InputStream2String(InputStream in, String encoding)
            throws Exception {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count = -1;
        while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
            outStream.write(data, 0, count);

        data = null;
        return new String(outStream.toByteArray(), encoding);
    }

    /**
     * @param in 数据流
     * @return String
     * @throws Exception
     * @brief 将InputStream转换成String
     */
    public String InputStream2String(InputStream in) throws Exception {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count = -1;
        while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
            outStream.write(data, 0, count);

        data = null;
        return new String(outStream.toByteArray(), "UTF-8");
    }

    public Bitmap stringtoBitmap(String string) {
        // 将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
        } catch (Exception e) {
            LogUtils.e("TAG", "异常");
        }

        return bitmap;
    }

    public String bitmaptoString(Bitmap bitmap) {
        // 将Bitmap转换成字符串
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }

}
