package com.youngbook.common.utils;

import com.youngbook.common.MyException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数字处理工具类
 */
public class NumberUtils {

    public static int parse2Int(String text) throws Exception {

        if (StringUtils.isEmpty(text) || !StringUtils.isNumeric(text)) {
            MyException.newInstance("无法转换字符串", "text=" + text).throwException();
        }

        return Integer.parseInt(text);
    }

    public static double formatDouble(double number, int scale) {
        BigDecimal bg = new BigDecimal(number);
        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }


    /**
     * 判断所给数字是否在数组内
     * @param number
     * @param numbers
     * @return
     */
    public static boolean checkNumberIn(int number, int... numbers) {

        for (int n : numbers) {
            if (n == number) {
                return true;
            }
        }

        return false;
    }



    /**
     * 格式化double型数字，以1000为单位将数字分组，如
     * 12345.12将分组为123,45.12
     * 需要输入参数precision以四舍五入的方式规定小数点后的位数
     * @param number precision
     * @return 格式化后数字的字符串形式
     */
    public static String format(double number, int precision) {
        int realPrecision = getLengthAfterDot(number);
        if(precision>realPrecision){
            precision = realPrecision;
        }
        String pattern = "%1$,."+precision+"f";
        return new String("").format(pattern , number);
    }


    /**
     * format()的重载形式，格式化float型数字 ，需要输入参数precision以四舍五入的方式规定小数点后的位数
     * @param number precision
     * @return 格式化后数字的字符串形式
     */
    public static String format(float number, int precision) {
        int realPrecision = getLengthAfterDot(number);
        if(precision>realPrecision){
            precision = realPrecision;
        }
        String pattern = "%1$,."+precision+"f";
        return new String("").format(pattern , number);
    }

    /**
     * format()的重载形式，格式化int型数字
     * @param number
     * @return 格式化后数字的字符串形式
     */
    public static String format(int number){
        String pattern = "%1$,d";
        return new String("").format(pattern , number);
    }


    /**
     * format()的重载形式，格式化long型数字
     * @param number
     * @return 格式化后数字的字符串形式
     */
    public static String format(long number){
        String pattern = "%1$,d";
        return new String("").format(pattern, number);
    }

    /**
     * 获取参数number小数点后的位数，以便于消除precision参数大于实际number
     * 的小数点位数时造成精度误差
     * 1000000000 格式化为1,000,000,000
     * @param number
     * @return
     */
    private static  int getLengthAfterDot(double number){
        Double num = number;
        String numStr = num.toString();
        String numAfterDot = numStr.substring(numStr.indexOf(".")+1);
        return numAfterDot.length();
    }
    private static  int getLengthAfterDot(float number){
        Float num = number;
        String numStr = num.toString();
        String numAfterDot = numStr.substring(numStr.indexOf(".")+1);
        return numAfterDot.length();
    }

    /**
     * 获取 9 位以内不重复的数字
     * @param count
     * @return
     */
    public static Integer randomNumbers(Integer count) {
        count = (count < 1 || count > 9) ? 9 : count;
        int[] array = {0,1,2,3,4,5,6,7,8,9};
        Random rand = new Random();
        for (int i = 10; i > 1; i--) {
            int index = rand.nextInt(i);
            int tmp = array[index];
            array[index] = array[i - 1];
            array[i - 1] = tmp;
        }
        int result = 0;
        for(int i = 0; i < count; i++) {
            if (i == 0 && array[i] == 0) {
                array[i] = 1;
            }
            result = result * 10 + array[i];
        }
        return result;
    }

    /**
     * 通过时间戳截取指定位数的数字
     * @param count
     * @return
     */
    public static Long getNumbersByTimeStamp(Integer count) {
        Date date = new Date();
        String time = date.getTime() + "";
        String resultStr = time.substring(time.length() - count, time.length());
        return Long.parseLong(resultStr);
    }


    /**
     * 判断输入字符串是否为整型
     *
     * @author leevits
     * @param input
     * @return
     */
    public static boolean isInteger(String input){
        Matcher mer = Pattern.compile("^[+-]?[0-9]+$").matcher(input);
        return mer.find();
    }


    /**
     * 货币单位元转成分
     *
     * 修改：邓超
     * 内容：移动代码和添加注释
     * 时间：2016年4月20日
     *
     * @param money
     * @return
     */
    public static int getMoney2Fen(double money) {
        return (int)Math.round(money * 100);
    }

    public static String getMoney2FenString(double money) {
        return String.valueOf(Math.round(money * 100));
    }

}
