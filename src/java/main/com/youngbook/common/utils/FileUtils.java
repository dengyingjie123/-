package com.youngbook.common.utils;

import sun.misc.BASE64Decoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;

/**
 * Created by Lee on 10/18/2017.
 */
public class FileUtils {

    public static String getFileExtensionName(String fileName) {
        String extensionName = fileName.substring(fileName.lastIndexOf(".") + 1);

        return extensionName;
    }

    public static void decodeBase64ToFile(File file, String base64String) throws Exception {

        BASE64Decoder decoder = new BASE64Decoder();

        byte[] decoded = decoder.decodeBuffer(base64String);

        org.apache.commons.io.FileUtils.writeByteArrayToFile(file, decoded);

    }

    /**
     * 通过BASE64Decoder解码，并生成图片
     * @param imgStr 解码后的string
     */
    public boolean base642Image(String imgStr, String imgFilePath) {
        // 对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null)
            return false;
        try {
            // Base64解码
            byte[] b = new BASE64Decoder().decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    // 调整异常数据
                    b[i] += 256;
                }
            }
            // 生成Jpeg图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
