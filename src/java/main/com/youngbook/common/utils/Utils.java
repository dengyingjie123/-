package com.youngbook.common.utils;

import com.youngbook.annotation.Table;
import com.youngbook.common.utils.runner.Task;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;


public class Utils {

    public static void main(String [] args) {
        System.out.println(Utils.getRandomCode());
    }

    public static String getRandomCode() {
        String r = String.valueOf(System.currentTimeMillis());

        r = r.substring(7);
        return String.valueOf(r);
    }

    public static String md5(String plainText) {
        String md5text = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            md5text = buf.toString();
            //System.out.println("result: " + buf.toString());// 32位的加密
            //System.out.println("result: " + buf.toString().substring(8, 24));// 16位的加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        }
        return md5text;
    }

    public static Method getMethod(Class clazz, String methodName, Class [] clazzes) throws Exception {

        try {
            for(Method method : clazz.getMethods()) {
                //System.out.println("Find:" + methodName +" Now: "+method.getName());
                if (method.getName().equalsIgnoreCase(methodName)) {
                    // System.out.println("Find:" + methodName +" Now: "+method.getName());
                    if (method.getParameterTypes().length == clazzes.length) {
                        boolean isOk = true;
                        for (int i = 0; i < method.getParameterTypes().length; i++) {
                            Class paramType = method.getParameterTypes()[i];
                            Class temp = clazzes[i];
                            if (!paramType.getName().equals(temp.getName())) {
                                isOk = false;
                                break;
                            }
                        }

                        if (isOk) {
                            //System.out.println("Found: " + method.getName());
                            return method;
                        }
                    }

                }
            }
        }
        catch (Exception e) {
            throw e;
        }


        return null;
    }
}
