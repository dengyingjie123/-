package com.youngbook.common.utils;

import com.youngbook.common.KVObject;
import com.youngbook.common.config.Config;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.*;
import com.youngbook.entity.po.production.ProductionInfoPO;
import com.youngbook.entity.po.production.ProductionPO;
import com.youngbook.entity.vo.production.SalesPersonInfo;
import net.sf.json.JSONObject;
import org.apache.axis.transport.http.HTTPSender;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.*;

import static com.opensymphony.xwork2.Action.*;


//todo 苟熙霖 先跑一遍看原因
public class HolidayUtil {

    public static void main(String[] args) throws Exception {

        HolidayUtil.getCurrentYearHoliday("2019");

    }


    public static List<HolidayPO> getCurrentYearHoliday(String year) throws Exception {
        List<HolidayPO> holidayPOS = new ArrayList<>();
        HolidayPO holidayPO = new HolidayPO();
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
            //打印2001年10月1日到2001年11月4日的日期
            while(tmp.getTime()<=d2.getTime()-1) {
                tmp=dd.getTime();
                String format = sdf.format(tmp);
                String workDay = getWorkDay(format);
                System.out.println(workDay+"opopop"+format);
                //天数加上1
                dd.add(Calendar.DAY_OF_MONTH, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  holidayPOS;
    }


    public static String getWorkDay (String format) throws  Exception{
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date parse = sdf.parse(format);
        Calendar dd =Calendar.getInstance();
        dd.setTime(parse);
        Date time = dd.getTime();
        String parseTime = sdf.format(time);
        URL realURL = new URL("http://api.goseek.cn/Tools/holiday?date=" + parseTime);
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