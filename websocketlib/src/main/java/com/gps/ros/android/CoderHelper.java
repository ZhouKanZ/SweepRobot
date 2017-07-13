package com.gps.ros.android;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/10 0010
 * @Descriptiong : xxx
 */

public class CoderHelper {

    public static final String str = "hello";
    /**
     * @param imgStr base64编码字符串
     * @param path   图片路径-具体到文件
     * @return
     * @Description: 将base64编码字符串转换为图片
     * @Author:
     * @CreateTime:
     */
    public static Bitmap generateImage(String imgStr) {

        Bitmap bitmap = null;
        BASE64Decoder decoder = new BASE64Decoder();

        try {
            // 解密
            byte[] b = decoder.decodeBuffer(imgStr);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
//            OutputStream out = new FileOutputStream(path);
//            out.write(b);
//            out.flush();
//            out.close();
//            BitmapFactory.Options op = new BitmapFactory.Options();
//            op.


            return BitmapFactory.decodeByteArray(b, 0, b.length,
                    null);
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * @Description: 根据图片地址转换为base64编码字符串
     * @Author:
     * @CreateTime:
     * @return
     */
    public static String getImageStr(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    /**
     * @param inStream
     * @return byte[]
     * @throws Exception
     */
    public static byte[] readStream(InputStream inStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;

    }

}
