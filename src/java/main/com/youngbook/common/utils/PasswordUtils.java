package com.youngbook.common.utils;

/**
 * Created by leevits on 01/04/2019.
 */
public class PasswordUtils {

    public static String getUserPasssword(String rawPassword) throws Exception {

        String p = StringUtils.md5(rawPassword);

        return p;
    }
}
