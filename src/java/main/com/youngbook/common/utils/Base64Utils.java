package com.youngbook.common.utils;

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
}
