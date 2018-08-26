package com.youngbook.common;

/**
 * 创建人：张舜清
 * 创建时间：2015-05-07
 */
import org.junit.Test;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class Money {
    private BigDecimal m = null;

    private static NumberFormat CN = NumberFormat.getCurrencyInstance(Locale.CHINA);
    private static NumberFormat US = NumberFormat.getCurrencyInstance(Locale.US);
    private static String volumn = null;

    /**
     * 格式化货币，通过上面声明的对应变量，转换成对应需要的中文货币或者英文货币
     */
    public static String show4Web2(double money) {
        String num = CN.format(money);
        return num;
    }

    /**
     * 格式化货币为100万
     */
    public static String show4Web1Million(double money) {
        BigDecimal unit = new BigDecimal(money);
        if(String.valueOf(unit).length()>=5 && String.valueOf(unit).length()<9){
            volumn = "万";
            unit = unit.divide(new BigDecimal(10000));
        }else if(String.valueOf(unit).length()==9){
            volumn = "亿";
            unit = unit.divide(new BigDecimal(100000000));
        }else{
        }
        return unit+volumn;
    }

    @Test
    public void test(){
        show4Web2(1234567890);
        show4Web1Million(100000000);
    }
}
