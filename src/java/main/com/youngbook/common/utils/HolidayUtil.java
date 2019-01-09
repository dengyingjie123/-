package com.youngbook.common.utils;


import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.*;

import net.sf.json.JSONObject;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;



public class HolidayUtil {

    /**
     * @description 循环每日日期判断是否是节假日，是则存入数据库,system_holiday
     *
     * @author 苟熙霖
     *
     * @date 2019/1/9 15:20
     * @param args
     * @return void
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        HolidayUtil.getCurrentYearHoliday("2020");

    }


    public static List<HolidayPO> getCurrentYearHoliday(String year) throws Exception {

        List<HolidayPO> holidayPOS = new ArrayList<>();
        //日期格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            //起始日期
            Date d1 = sdf.parse(year+"-01-01");
            //结束日期
            Date d2 = sdf.parse(year +"-12-31");
            Date tmp=d1;
            Calendar dd =Calendar.getInstance();
            dd.setTime(d1);

            while(tmp.getTime()<=d2.getTime()-1) {
                tmp=dd.getTime();
                String format = sdf.format(tmp);
                String result = getResult(format);
                String[] split = result.split("");
                Integer value = Integer.valueOf(split[21]);
                if(!value.equals(0)){
                    HolidayPO holidayPO = new HolidayPO();
                    holidayPO.setState(0);
                    holidayPO.setHoliday(format);
                    holidayPOS.add(holidayPO);
                    MySQLDao.insertOrUpdate(holidayPO);
                }
                //天数加上1
                dd.add(Calendar.DAY_OF_MONTH, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  holidayPOS;
    }


    public static String getResult (String format) throws  Exception{
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        String time = format.replaceAll("-", "");
        URL realURL = new URL("http://api.goseek.cn/Tools/holiday?date=" + time);
        HttpURLConnection httpURLConnection = (HttpURLConnection) realURL.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.connect();
        try {
            HttpURLConnection connection = (HttpURLConnection)realURL.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}