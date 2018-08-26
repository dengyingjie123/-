package com.youngbook.common.wf.common;

import com.youngbook.common.Database;
import org.jdom.Document;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.sql.*;
import java.io.FileOutputStream;

import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;



public class Tools {
    public Tools() {

    }

    /**
     * 程序：李扬
     * 时间：2004-12-21
     * 说明：
     * @param strTime String
     * @return Calendar
     * @throws Exception
     */
    public static Calendar getDate(String strTime, String format) throws Exception {
        //format: "yyyy-MM-dd hh24:mm:ss"
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = sdf.parse(strTime);
        Calendar time = Calendar.getInstance();
        time.setTime(date);
        return time;
    }

    /**
     * 程序：李扬
     * 时间：2005-1-6
     * 说明：获得当前日期，形如："2005年1月6日 星期四"
     * @return String
     */
    public static String getDate() {
        //获得当前日期
        Calendar cldCurrent = Calendar.getInstance();

        //获得年月日
        String strYear = String.valueOf(cldCurrent.get(Calendar.YEAR));
        String strMonth = String.valueOf(cldCurrent.get(Calendar.MONTH) + 1);
        String strDate = String.valueOf(cldCurrent.get(Calendar.DATE));
        String strWeek = new String();
        int intWeek = cldCurrent.get(Calendar.DAY_OF_WEEK) - 1;
        switch (intWeek) {
            case 1: strWeek = "星期一"; break;
            case 2: strWeek = "星期二"; break;
            case 3: strWeek = "星期三"; break;
            case 4: strWeek = "星期四"; break;
            case 5: strWeek = "星期五"; break;
            case 6: strWeek = "星期六"; break;
            case 7: strWeek = "星期日"; break;
        }

        StringBuffer sbDate = new StringBuffer();
        sbDate.append(strYear);
        sbDate.append("年");
        sbDate.append(strMonth);
        sbDate.append("月");
        sbDate.append(strDate);
        sbDate.append("日");
        sbDate.append(" ");
        sbDate.append(strWeek);
        return sbDate.toString();
    }

    /**
     * 说明：获得当前时间
     * @return String
     */
    public static String getTime() {
        long longCalendar = 0;

        //获得当前日期
        Calendar cldCurrent = Calendar.getInstance();

        //获得年月日
        String strYear = String.valueOf(cldCurrent.get(Calendar.YEAR));
        String strMonth = String.valueOf(cldCurrent.get(Calendar.MONTH) + 1);
        String strDate = String.valueOf(cldCurrent.get(Calendar.DATE));
        String strHour = String.valueOf(cldCurrent.get(Calendar.HOUR));
        String strAM_PM = String.valueOf(cldCurrent.get(Calendar.AM_PM));
        String strMinute = String.valueOf(cldCurrent.get(Calendar.MINUTE));
        String strSecond = String.valueOf(cldCurrent.get(Calendar.SECOND));

        //把时间转换为24小时制
        //strAM_PM=="1",表示当前时间是下午，所以strHour需要加12
        if (strAM_PM.equals("1")) {
            strHour = String.valueOf(Long.parseLong(strHour) + 12);
        }

        //整理格式
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
        //组合结果
        longCalendar = Long.parseLong(strYear + strMonth + strDate + strHour +
                strMinute + strSecond);

        StringBuffer sbCalendar = new StringBuffer();

        sbCalendar.insert(0, longCalendar);

        //整理格式
        if (sbCalendar.length() == 8) {
            sbCalendar.insert(6, "-");
            sbCalendar.insert(4, "-");
        } else if (sbCalendar.length() == 14) {
            sbCalendar.insert(12, ":");
            sbCalendar.insert(10, ":");
            sbCalendar.insert(8, " ");
            sbCalendar.insert(6, "-");
            sbCalendar.insert(4, "-");
        } else {
            //错误处理
        }

        //返回格式化好的整形日期的字符串格式
        return sbCalendar.toString();
    }

    /**
     * 程序：
     * 说明：对写入xml文件的字符进行编码
     * @param code 需要编码的字符
     * @return 编码后的字符
     */
    public static String Encode(String code) {
        if (code == null) {
            return null;
        } else {
            return code.replaceAll(">", "&gt;").replaceAll("<",
                    "&lt;").replaceAll("\"", "&quot;");
        }
    }

    /**
     * 程序：
     * 说明：对从XML文件中读出的字串进行解码
     * @param code 需要解码的字符串
     * @return解码后的字串
     */
    public static String Decode(String code) {
        if (code == null) {
            return null;
        } else {
            return code.replaceAll("&gt;", ">").replaceAll("&lt;",
                    "<").replaceAll("&quot;", "\"");
        }
    }

    /**
     * 程序：李扬
     * 时间：2004-12-28
     * 说明：对代码进行HTML编码，将换行替换成<br>
     * @param code String
     * @return String
     */
    public static String HTMLEncode(String code) {
        if (code != null) {
            code = code.replaceAll("\n","<br>");
        }
        return code;
    }
    /**
     * 程序：
     * 说明：得到数据库连接公共方法
     * @return 数据库连接
     * @throws java.lang.Exception
     */
    public static Connection getDBConn() throws Exception {
        Connection conn = Database.getConnection();
        //Connection conn = null;
//        Connection conn;
//        Class.forName("oracle.jdbc.driver.OracleDriver");
//        conn = DriverManager.getConnection("jdbc:oracle:thin:@" +
//                CommonInfo.getDBAddr() + ":1521:" +
//                CommonInfo.getSid(),
//                CommonInfo.getDBUserName(),
//                CommonInfo.getDBPwd());
        return conn;
    }

    /**
     * 程序：
     * 说明：写入XML文件的公共方法
     * @param FileName XML文件名
     * @param doc JDOM DOC
     * @throws java.lang.Exception
     */
    public static void WriteXML(String FileName, Document doc) throws Exception {
        Format format = Format.getCompactFormat();
        format.setEncoding("utf-8"); //设置xml文件的字符为utf-8
        format.setIndent("    "); //设置xml文件的缩进为4个空格
        XMLOutputter XMLOut = new XMLOutputter(format); //在元素后换行，每一层元素缩排四格
        XMLOut.output(doc, new FileOutputStream(CommonInfo.getXMLPath() + FileName));

    }

    public static String toUTF8(String str) throws Exception {
//        if (str == null) {
//            return null;
//        } else {
//            return new String(str.getBytes("ISO8859_1"), "utf-8");
//        }
        return str;
    }
}
