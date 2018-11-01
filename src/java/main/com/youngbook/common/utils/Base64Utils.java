package com.youngbook.common.utils;

import org.apache.http.Consts;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Created by leevits on 10/22/2018.
 */
public class Base64Utils {

    static BASE64Decoder decoder = new BASE64Decoder();

    static BASE64Encoder encoder = new BASE64Encoder();

    public static String encode(String text) {
        String encode = encoder.encode(text.getBytes());

        return encode;
    }

    public static String decode(String text) throws Exception {
        String encode = new String(decoder.decodeBuffer(text), Consts.UTF_8);

        return encode;
    }

}
