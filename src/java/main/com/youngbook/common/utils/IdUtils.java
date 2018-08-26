package com.youngbook.common.utils;


import com.relops.snowflake.Snowflake;

import java.util.UUID;

/**
 * Created by Lee on 2017/1/5.
 */
public class IdUtils {

    public static void main(String [] args) throws Exception {

        System.out.println(System.currentTimeMillis());

        for (int i = 0; i < 100; i++) {
            System.out.println(IdUtils.newLongId());
        }
    }

    /**
     * Snowflake方式获取一个唯一long型数字，可作为ID
     * @return
     * @throws Exception
     */
    public static long newLongId() throws Exception {

        // 防止冲突
        Thread.sleep(1);

        int node = (int)(Math.random() * 10);
        Snowflake s = new Snowflake(node);
        long id = s.next();
        return id;
    }

    public static String getNewLongIdString() throws Exception {
        return String.valueOf(newLongId());
    }

    public static String getUUID36() {
        String s = UUID.randomUUID().toString().toUpperCase();
        return s;
    }
    /**
     * 获得一个UUID
     * @return String UUID
     */
    public static String getUUID32(){
        String s = UUID.randomUUID().toString().toUpperCase();
        //去掉“-”符号
        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
    }
}
