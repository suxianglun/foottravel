package com.footprint.travel.utils.RSA;

import com.footprint.travel.utils.RSA.sun.BASE64Decoder;
import com.footprint.travel.utils.RSA.sun.BASE64Encoder;

public class Base64 {
    // 加密
    public static String getBase64(byte[] b) {
        String s = null;
        if (b != null) {
            s = new BASE64Encoder().encode(b);
        }
        return s;
    }

    // 解密
    public static byte[] getFromBase64(String s) {
        byte[] b = null;
        if (s != null) {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                b = decoder.decodeBuffer(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return b;
    }
}
