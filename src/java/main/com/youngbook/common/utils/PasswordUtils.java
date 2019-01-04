package com.youngbook.common.utils;

/**
* @description: 密码加密
* @author: 徐明煜
* @createDate: 2019/1/4 14:39
* @version: 1.1
*/
public class PasswordUtils {

    public static String getUserPassswordInMd5(String rawPassword) throws Exception {

        String passwordForSave = StringUtils.md5(rawPassword);

        return passwordForSave;
    }
}
