package com.youngbook.common.utils;

import com.youngbook.common.MyException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class TimeUtils {

    public static final String Format_YYYY_MM_DD_HH_M_S = "yyyy-MM-dd HH:m:s";
    public static final String Format_YYYYMMDDHHMMSS= "yyyyMMddHHmmss";
    public static final String Format_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String Format_YYYY_MM_DD_MysQL = "%Y-%m-%d";
    public static final String Format_YYYYMMDD = "yyyyMMdd";

    public static final String YEAR = "YEAR";
    public static final String MONTH = "MONTH";
    public static final String DATE = "DATE";
    public static final String HOUR = "HOUR";
    public static final String MINUTE = "MINUTE";
    public static final String SECOND = "SECOND";


    public static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        return c.getTime();
    }

    public static String getLastDayOfWeek(String dateString) throws Exception {

        Date date = TimeUtils.getDate(dateString, "yyyy-MM-dd").getTime();
        Date lastDayOfWeek = getLastDayOfWeek(date);

        String lastDayOfWeekString = TimeUtils.getDateShortString(lastDayOfWeek);

        return lastDayOfWeekString;
    }

    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Sunday
        return c.getTime();
    }

    public static String getFirstDayOfWeek(String dateString) throws Exception {

        Date date = TimeUtils.getDate(dateString, "yyyy-MM-dd").getTime();
        Date lastDayOfWeek = getFirstDayOfWeek(date);

        String lastDayOfWeekString = TimeUtils.getDateShortString(lastDayOfWeek);

        return lastDayOfWeekString;
    }


    public static int getWeekOfYear(Date date) {

        Calendar time = Calendar.getInstance();
        time.setTime(date);
        time.setFirstDayOfWeek(Calendar.MONDAY);

        int weekOfYear = time.get(Calendar.WEEK_OF_YEAR);

        return weekOfYear;
    }

    public static String getWeekDayChinese(Date date) {

        DateFormat format = new SimpleDateFormat("u");

        int weekOfDay = Integer.parseInt(format.format(date));


        String s = "未知";

        switch (weekOfDay) {
            case 1: s = "一"; break;
            case 2: s = "二"; break;
            case 3: s = "三"; break;
            case 4: s = "四"; break;
            case 5: s = "五"; break;
            case 6: s = "六"; break;
            case 7: s = "日"; break;
        }

        return s;

    }

    public static Date getDate(String dateText) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.parse(dateText);
    }




    /**
     * @description
     * 根据yyyy-MM-dd格式化
     * @author 胡超怡
     *
     * @date 2018/12/7 13:06
     * @param dateText
     * @return java.util.Date
     * @throws Exception
     */
    public static Date getNewDate(String dateText) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(dateText);
    }

    public static Date getDateCommon(String dateText) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(dateText);
    }

    public static String getNow() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }

    public static long getSimpleTimestamp() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyMMddHHmmss");
        String date = sDateFormat.format(new java.util.Date());

        return  Long.parseLong(date);
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     * @param format
     * @return
     */
    public static String getNow(String format) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(format);
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }

    public static String getNowMonth() {
        String month = getNowDate();

        month = StringUtils.removeLastLetters(month, 3);

        return month;
    }

    public static String getNowDate() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }

    public static String getNowDateYYYYMMDD() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }

    public static String getYear() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }

    public static String getMonth() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("MM");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }

    public static String getNowTimeHH24MMSS() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("HHmmss");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }

    public static String getDateString(Date date) {
        String path = "yyyy-MM-dd HH:mm:ss.SSS";
        SimpleDateFormat sdf = new SimpleDateFormat(path);
        return sdf.format(date);
    }

    public static String getDateSimpleString(Date date) {
        String path = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(path);
        return sdf.format(date);
    }

    public static String getDateShortString(String time_YYYYMMDDSSMISS) throws Exception {

        String formatedString = format(time_YYYYMMDDSSMISS, Format_YYYY_MM_DD_HH_M_S, Format_YYYY_MM_DD);

        return formatedString;
    }

    public static String getDateShortString(Date date) {
        String path = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(path);
        return sdf.format(date);
    }

    public static String getDatePaymentString(Date date) {
        String path = "yyyyMMdd";
        SimpleDateFormat sdf = new SimpleDateFormat(path);
        return sdf.format(date);
    }

    public static String getDateFromTimestamp(String timestampString) {
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new java.util.Date(timestamp));
        return date;
    }

    /**
     * 程序：李扬
     * 时间：2005-12-14
     * 说明：获得以初始化时间为基准，前后数年数月数日等的时间
     * 例如：获得2005-12-14 14:52:58
     * 前3个小时的时间 Tools.GetTime("2005-12-14 14:52:58", -3, "HOUR");
     * 获得2005-12-14 14:52:58 后2天的时间 Tools.GetTime("2005-12-14 14:52:58", 2, "DATE");
     * <p/>
     * 注意：初始化时间格式必须为 YYYY-MM-DD HH24-MI-SS 或 YYYY-MM-DD HH24-MI 或 YYYY-MM-DD
     *
     * @param initTime    String 初始化时间
     * @param BetweenTime int 时间间隔
     * @param Target      String YEAR:年 MONTH：月 DATE：日 HOUR：时 MINUTE：分 SECOND：秒
     * @return String
     * @throws Exception
     */
    public static String getTime(String initTime, int BetweenTime, String Target) throws Exception {
        boolean isYMD = false;
        boolean isYMDHM = false;
        boolean isYMDHMS = false;
        StringBuffer sbTime = new StringBuffer();
        String strFormat = "yyyy-MM-dd H:m:s";
        String[] arrayTime = initTime.split(" ");
        if (arrayTime.length == 1) {
            strFormat = "yyyy-MM-dd";
            isYMD = true;
        } else if (arrayTime.length == 2) {
            String[] strTime = arrayTime[1].split(":");
            if (strTime.length == 2) {
                strFormat = "yyyy-MM-dd H:m";
                isYMDHM = true;
            } else if (strTime.length == 3) {
                strFormat = "yyyy-MM-dd H:m:s";
                isYMDHMS = true;
            }
        }
        Calendar calendar = TimeUtils.getDate(initTime, strFormat);
        if (Target.equalsIgnoreCase(TimeUtils.YEAR)) {
            calendar.add(Calendar.YEAR, BetweenTime);
        } else if (Target.equalsIgnoreCase(TimeUtils.MONTH)) {
            calendar.add(Calendar.MONTH, BetweenTime);
        } else if (Target.equalsIgnoreCase(TimeUtils.DATE)) {
            calendar.add(Calendar.DATE, BetweenTime);
        } else if (Target.equalsIgnoreCase(TimeUtils.HOUR) && (isYMDHMS || isYMDHM)) {
            calendar.add(Calendar.HOUR, BetweenTime);
        } else if (Target.equalsIgnoreCase(TimeUtils.MINUTE) && (isYMDHMS || isYMDHM)) {
            calendar.add(Calendar.MINUTE, BetweenTime);
        } else if (Target.equalsIgnoreCase(TimeUtils.SECOND) && isYMDHMS) {
            calendar.add(Calendar.SECOND, BetweenTime);
        }
        String strYear = String.valueOf(calendar.get(Calendar.YEAR));
        String strMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String strDate = String.valueOf(calendar.get(Calendar.DATE));
        String strHour = String.valueOf(calendar.get(Calendar.HOUR));
        String strAM_PM = String.valueOf(calendar.get(Calendar.AM_PM));
        String strMinute = String.valueOf(calendar.get(Calendar.MINUTE));
        String strSecond = String.valueOf(calendar.get(Calendar.SECOND));

        // 把时间转换为24小时制
        // strAM_PM=="1",表示当前时间是下午，所以strHour需要加12
        if (strAM_PM.equals("1")) {
            strHour = String.valueOf(Long.parseLong(strHour) + 12);
        }

        // 整理格式
        if (strMonth.length() < 2) {
            strMonth = "0" + strMonth;
        }
        if (strDate.length() < 2) {
            strDate = "0" + strDate;
        }
        if (strHour.length() < 2) {
            strHour = "0" + strHour;
        }
        if (strMinute.length() < 2) {
            strMinute = "0" + strMinute;
        }
        if (strSecond.length() < 2) {
            strSecond = "0" + strSecond;
        }

        // 组合结果
        sbTime.append(strYear).append("-");
        sbTime.append(strMonth).append("-");
        sbTime.append(strDate).append(" ");
        sbTime.append(strHour).append(":");
        sbTime.append(strMinute).append(":");
        sbTime.append(strSecond);

        return sbTime.toString();
    }

    /**
     * 程序：李扬
     * 时间：2004-12-21
     * 说明：
     *
     * @param strTime String 输入要格式的日期 形如 2005-3-4 20:33:23
     * @param format  日期的格式 例如： "yyyy-MM-dd HH:m:s"
     * @return Calendar
     * @throws Exception
     */
    public static Calendar getDate(String strTime, String format) throws Exception {
        // format: "yyyy-MM-dd HH:m:s"
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = sdf.parse(strTime);
        Calendar time = Calendar.getInstance();
        time.setTime(date);
        return time;
    }

    public static String format(String time, String oldFormat, String newFormat) throws Exception {


        if (StringUtils.isEmpty(oldFormat) || (!TimeUtils.Format_YYYYMMDD.equals(oldFormat) && !TimeUtils.Format_YYYYMMDDHHMMSS.equals(oldFormat) && !TimeUtils.Format_YYYY_MM_DD_HH_M_S.equals(oldFormat))) {
            MyException.newInstance("日期格式转换错误，不支持格式。").throwException();
        }

        if (StringUtils.isEmpty(newFormat) || (!TimeUtils.Format_YYYY_MM_DD_HH_M_S.equals(newFormat) && !TimeUtils.Format_YYYY_MM_DD.equals(newFormat))) {
            MyException.newInstance("日期格式转换错误，不支持格式。").throwException();
        }


        Calendar calendar = TimeUtils.getDate(time, oldFormat);


        String strYear = String.valueOf(calendar.get(Calendar.YEAR));
        String strMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String strDate = String.valueOf(calendar.get(Calendar.DATE));
        String strHour = String.valueOf(calendar.get(Calendar.HOUR));
        String strAM_PM = String.valueOf(calendar.get(Calendar.AM_PM));
        String strMinute = String.valueOf(calendar.get(Calendar.MINUTE));
        String strSecond = String.valueOf(calendar.get(Calendar.SECOND));

        // 把时间转换为24小时制
        // strAM_PM=="1",表示当前时间是下午，所以strHour需要加12
        if (strAM_PM.equals("1")) {
            strHour = String.valueOf(Long.parseLong(strHour) + 12);
        }

        // 整理格式
        if (strMonth.length() < 2) {
            strMonth = "0" + strMonth;
        }
        if (strDate.length() < 2) {
            strDate = "0" + strDate;
        }
        if (strHour.length() < 2) {
            strHour = "0" + strHour;
        }
        if (strMinute.length() < 2) {
            strMinute = "0" + strMinute;
        }
        if (strSecond.length() < 2) {
            strSecond = "0" + strSecond;
        }

        StringBuffer sbTime = new StringBuffer();

        if (TimeUtils.Format_YYYY_MM_DD.equals(newFormat)) {
            sbTime.append(strYear).append("-");
            sbTime.append(strMonth).append("-");
            sbTime.append(strDate);

            return sbTime.toString();
        }

        if (TimeUtils.Format_YYYY_MM_DD_HH_M_S.equals(newFormat)) {
            sbTime.append(strYear).append("-");
            sbTime.append(strMonth).append("-");
            sbTime.append(strDate).append(" ");
            sbTime.append(strHour).append(":");
            sbTime.append(strMinute).append(":");
            sbTime.append(strSecond);
            return  sbTime.toString();
        }

        MyException.newInstance("日期格式转换错误，不支持格式。").throwException();

        return time;
    }



    public static Map<String, String> getDiffTime (String time1, String time2,String format) throws Exception {

        Map<String, String> map = new HashMap<String, String>();

        DateFormat df = new SimpleDateFormat(format);
        Date t1 = df.parse(time1);
        Date t2 = df.parse(time2);

        Calendar c1 = Calendar.getInstance();
        c1.setTime(t1);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(t2);

        long diffSeconds = (c1.getTimeInMillis() - c2.getTimeInMillis()) / 1000;

        long MinuteSeconds = 60;
        long HourSeconds = MinuteSeconds * 60;
        long DaySeconds = HourSeconds * 24;

        long days = diffSeconds / DaySeconds;
        long hours = (diffSeconds - days * DaySeconds) / HourSeconds;

        map.put("days", String.valueOf(days));
        map.put("hours", String.valueOf(hours));

        return map;

    }

    /**
     * 比较两个时间的月份差，参数类型为 String 类型
     * 用法：TimeUtils.getMonthDifference(string0, string1, format) 即可
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @param string0 第一个时间的字符串
     * @param string1 第二个时间的字符串
     * @param format  格式
     * @return 适用于交易平台的 json
     * @throws Exception
     * @author 邓超
     */
    public static int getMonthDifference(String string0, String string1, String format) throws Exception {
        DateFormat df = new SimpleDateFormat(format);
        Date date0 = df.parse(string0);
        Date date1 = df.parse(string1);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date0);
        int year0 = calendar.get(Calendar.YEAR);
        int month0 = calendar.get(Calendar.MONTH);
        calendar.setTime(date1);
        int year1 = calendar.get(Calendar.YEAR);
        int month1 = calendar.get(Calendar.MONTH);

        int result;
        if (year0 == year1) {
            result = month1 - month0;
        } else {
            result = 12 * (year1 - year0) + month1 - month0;
        }
        return result;
    }

    /**
     * 比较两个时间的月份差（时间参数为 Date 类型）
     * 用法：TimeUtils.getMonthDifference(date0, data1) 即可，返回 int 型，如 3 则表示 3 个月
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @param date0 第一个时间
     * @param date1 第二个时间
     * @return int
     * @throws Exception
     * @author 邓超
     */
    public static int getMonthDifference(Date date0, Date date1) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date0);
        int year0 = calendar.get(Calendar.YEAR);
        int month0 = calendar.get(Calendar.MONTH);
        calendar.setTime(date1);
        int year1 = calendar.get(Calendar.YEAR);
        int month1 = calendar.get(Calendar.MONTH);

        int result;
        if (year0 == year1) {
            result = month1 - month0;
        } else {
            result = 12 * (year1 - year0) + month1 - month0;
        }
        return result;
    }

    /**
     * 创建人：张舜清
     * 时间：7/22/2015
     * 内容：根据传入的两个日期计算出相差的天数
     *
     * @param firstString  第一个字符串格式的日期
     * @param secondString 第二个字符串格式的日期
     * @param format       日期的格式 例如： "yyyy-MM-dd HH:m:s"
     * @return
     * @throws Exception
     */
    public static int getDayDifference(String firstString, String secondString, String format) throws Exception {
        DateFormat df = new SimpleDateFormat(format);
        Date firstDate = df.parse(firstString);
        Date secondDate = df.parse(secondString);

        Calendar cal = Calendar.getInstance();
        cal.setTime(firstDate);
        long firstTime = cal.getTimeInMillis();
        cal.setTime(secondDate);
        long secondTime = cal.getTimeInMillis();
        long between_days = (secondTime - firstTime) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    public static long getSecondDifference(String firstString, String secondString, String format) throws Exception {
        DateFormat df = new SimpleDateFormat(format);
        Date firstDate = df.parse(firstString);
        Date secondDate = df.parse(secondString);

        Calendar cal = Calendar.getInstance();
        cal.setTime(firstDate);
        long firstTime = cal.getTimeInMillis();
        cal.setTime(secondDate);
        long secondTime = cal.getTimeInMillis();
        long difference = (secondTime - firstTime) / 1000;

        return difference;
    }

    public static long getSecondsOfOneDay() {
        return 60 * 60 * 24;
    }

    /**
     * 创建人：张舜清
     * 时间：7/22/2015
     * 内容：根据传入的两个日期参数计算出相差多少年
     *
     * @param firstString   第一个字符串日期参数
     * @param secondString  第二个字符串日期参数
     * @param format    日期的格式 例如： "yyyy-MM-dd HH:m:s"
     * @return
     * @throws Exception
     */
    public static int getYearDifference(String firstString, String secondString, String format) throws Exception {
        DateFormat df = new SimpleDateFormat(format);
        Date firstDate = df.parse(firstString);
        Date secondDate = df.parse(secondString);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(firstDate);
        int firstYear = calendar.get(Calendar.YEAR);
        calendar.setTime(secondDate);
        int secondYear = calendar.get(Calendar.YEAR);
        return secondYear - firstYear;
    }

    /**
     * 创建人：姚章鹏
     * 时间：2015年8月28日16:08:03
     * 内容：根据传入的两个日期参数计算出相差多少分
     *
     * @param firstString   第一个字符串日期参数
     * @param secondString  第二个字符串日期参数
     * @param format    日期的格式 例如： "yyyy-MM-dd HH:m:s"
     * @return
     * @throws Exception
     */
    public static int getMinuteDifference(String firstString, String secondString, String format) throws Exception {
        DateFormat df = new SimpleDateFormat(format);
        Date firstDate = df.parse(firstString);
        Date secondDate = df.parse(secondString);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(firstDate);
        long firstMinute = calendar.getTimeInMillis();
        calendar.setTime(secondDate);
        long secondMinute = calendar.getTimeInMillis();
       long between_minute= (secondMinute - firstMinute)/(1000 * 60);
        return Integer.parseInt(String.valueOf(between_minute));
    }

}
