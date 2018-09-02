package com.youngbook.common.utils;

import com.youngbook.common.KVObject;
import com.youngbook.common.KVObjects;
import com.youngbook.common.MyException;
import com.youngbook.common.config.AesEncrypt;
import com.youngbook.common.config.Config;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class StringUtils {

    public static final String Encode_GB2312 = "GB2312";
    public static final String Encode_ISO_8859_1 = "ISO-8859-1";
    public static final String Encode_UTF_8 = "UTF-8";
    public static final String Encode_GBK = "GBK";

    public static void main(String [] args) throws Exception {

        String trans_date = "20180901";
        String trans_time = "173136";


        System.out.println(AesEncrypt.encrypt("800019374406012"));
        System.out.println(AesEncrypt.encrypt("0800019374406012"));

        // 103170160100150
        //
        // 1031701001
        // 1031701601
        // 1031802401
        // 1031802401
        String title = "1031802401";

        // 1031701601
        // 1031802401
        // 1031802601



        int begin = 131;
        int count = 10;



        for (int i = begin; i < (begin + count); i++) {
            String s = StringUtils.buildNumberString(i, 5);

            // UPDATE `sale_contract` SET `ContractNo`='103170160100151' WHERE (`sid`='10406')

            String s1 = title + s + "-1";
            String s2 = title + s + "-2";
            String s3 = title + s + "-3";


            String sql1 = "UPDATE `sale_contract` SET `ContractNo`='103170160100151' WHERE (`sid`='10406')";

            System.out.println(title + s + "-1");
            System.out.println(title + s + "-2");
            System.out.println(title + s + "-3");
        }

//        String url = "http://sfsdf?a=A&b=22&c=po";
//        KVObjects parameters = StringUtils.getUrlParameters(url);
//
//        System.out.println(parameters.size());

//        System.out.println(StringUtils.md5("123456y" + Config.MD5String));
//        System.out.println(StringUtils.md5("123456y"));



    }


    public static String getEncoding(String str) {
        String encode = "GB2312";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s = encode;
                return s;
            }
        } catch (Exception exception) {
        }
        encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
        }
        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {
        }
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
        }
        return "";
    }

    public static String urlRandom(String url) {
        if (StringUtils.isEmpty(url)) {
            return url;
        }

        if (url.contains("?")) {
            url = url + "&__r=" + NumberUtils.randomNumbers(5);
        }
        else {
            url = url + "?__r=" + NumberUtils.randomNumbers(5);
        }

        return url;
    }

    public static KVObjects getUrlParameters(String url) throws Exception {
        KVObjects parameters = new KVObjects();

        if (StringUtils.isEmpty(url)) {
            MyException.newInstance("URL数据为空").throwException();
        }

        if (url.indexOf("?") > 0) {
            url = url.substring(url.indexOf("?") + 1);
        }


        String [] ps = url.split("&");

        for (int i = 0; ps != null && i < ps.length; i++) {
            String pp = ps[i];
            String[] p = pp.split("=");

            if (p != null && p.length == 2) {
                parameters.addItem(p[0], p[1]);
            }
        }

        return parameters;
    }

    public static String buildUrlParameters(KVObjects parameters) {
        StringBuffer sbParameters = new StringBuffer();

        for (int i = 0; parameters != null && i < parameters.size(); i++) {
            KVObject parameter = parameters.get(i);
            sbParameters.append(parameter.getKey()).append("=").append(parameter.getValue()).append("&");
        }

        if (sbParameters.length() > 0) {
            sbParameters = StringUtils.removeLastLetters(sbParameters, "&");
        }

        return sbParameters.toString();
    }

    public static boolean checkIsChineseMobile(String str) throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static String removeAllHtmlTags(String html) throws Exception {
        String text = html;
        text = text.replaceAll("</?[^>]+>", "");
        text = text.replaceAll("<style[^>]*?>[\\\\s\\\\S]*?<\\\\/style>", "");
        text = text.replaceAll("<script[^>]*?>[\\\\s\\\\S]*?<\\\\/script>", "");
        text = text.replaceAll("\\\\s*|\\t|\\r|\\n", "");
        text = text.replaceAll("&", "");
        return text;
    }

    public static String urlEncode(String url) throws Exception {
        return URLEncoder.encode(url, "UTF-8");
    }


    public static String getValue(Object o) {
        if (o != null) {
            return o.toString();
        }
        return "";
    }

    public static String urlDecode(String url) throws Exception {
        String u = URLDecoder.decode(url, "UTF-8");

        return u;
    }


    public static void checkIsEmpty(String s, String message) throws Exception {
        if (isEmpty(s)) {
            MyException.newInstance(message).throwException();
        }
    }

    public static void checkIsEmpty(String s, String peopleMessage, String machineMessage) throws Exception {
        if (isEmpty(s)) {
            MyException.newInstance(peopleMessage, machineMessage).throwException();
        }
    }

    public static String maskStringKeepLast(String mobile, int keepLength) {
        if (isEmpty(mobile) || mobile.length() < keepLength) {
            return "****";
        }
        return "****" + mobile.substring(mobile.length() - keepLength) ;
    }


    public static void checkIsNotEmpty(String s, String message) throws Exception {
        if (!isEmpty(s)) {
            MyException.newInstance(message).throwException();
        }
    }


    public static String lastString(String s, int count) {

        if (!StringUtils.isEmpty(s) && s.length() > count) {
            return s.substring(s.length() - count);
        }

        return null;
    }


    /**
     * 隐藏所有大于2位的数字
     * 输入： abc 1234567, b 1234555, 010-2232123, 45, 2
     * 输出： abc 12***67, b 12***55, *10-22***23, *5, 2
     * @param sin
     * @return
     * @throws Exception
     */
    public static String maskAllNumbers(String sin) throws Exception {
        String s = sin;


        String pattern = "[0-9]{2,}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(s);

        while (m.find()) {
//            System.out.println(m.group());
//            System.out.println(m.start());


            StringBuffer sb = new StringBuffer();

            String fundString = m.group();

            int maskLength = fundString.length() / 2;
            int length  = fundString.length();
            sb.append(fundString.substring(0, maskLength - 1));

            int firstLength = sb.length();

            String maskCharacter = "*";
            String maskString = "";
            for (int i = 0; i < maskLength; i++) {
                maskString += maskCharacter;
            }
            sb.append(maskString).append(fundString.substring(firstLength + maskLength, length));

            s = s.substring(0, m.start()) + sb.toString() + s.substring(m.start() + sb.length());

//            System.out.println(s);
        }

        return s;
    }

    public static boolean contains(String regex, String string) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(string);

        return m.find();
    }

    public static String maskMobile(String mobile) {
        if (isEmpty(mobile) || mobile.length() < 4) {
            return "";
        }
        return "****" + mobile.substring(mobile.length() - 4) ;
    }

    /**
     * 把头尾区间的字符换成星号
     *
     * 用法：
     * 例如 441322000000002317，保留头 4 位，尾 4 位，则 StringUtils.replaceToStars("441322000000002317", 4, 4);
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月28日
     *
     * @param string
     * @param start
     * @param end
     * @return
     * @throws Exception
     */
    public static String replaceToStars(String string, Integer start, Integer end) throws Exception {
        Integer middle = start + end + 2;
        StringBuffer star = new StringBuffer();
        for(Integer i = 0; i < middle; i ++) {
            star.append("*");
        }
        return string.substring(0, start) + star + string.substring(string.length() - end, string.length());
    }


    public static String fill(String templateText, List<KVObject> values) throws Exception {

        if (StringUtils.isEmpty(templateText)) {
            MyException.newInstance("无法获得模版内容").throwException();
        }

        String text = templateText;


        for (int i = 0; values != null && i < values.size(); i++) {
            KVObject kv = values.get(i);
            String k = "#\\{" + kv.getKeyStringValue() + "\\}";
            String v = kv.getValueStringValue();

            text = text.replaceAll(k, v);
        }

        return text;
    }

    public static boolean checkEquals(String v1, String v2) {

        if (!StringUtils.isEmpty(v1) && !StringUtils.isEmpty(v2) && v1.equals(v2)) {
            return true;
        }

        return false;
    }

    /**
     * 构造数字字符串，形如：000345
     * @param number 传入的整形，例如345
     * @param length 最终字符串长度，例如6
     * @return
     */
    public static String buildNumberString(int number, int length) {

        StringBuffer sbNumber = new StringBuffer();

        int numberLength = String.valueOf(number).length();

        int size = length - numberLength;
        for (int i = 0; i < size; i++) {
            sbNumber.append("0");
        }

        return sbNumber.append(number).toString();
    }

    public static String buildNumberString(String intString, int length) throws Exception {

        int number = Integer.parseInt(intString);

        StringBuffer sbNumber = new StringBuffer();

        int numberLength = String.valueOf(number).length();

        int size = length - numberLength;
        for (int i = 0; i < size; i++) {
            sbNumber.append("0");
        }

        return sbNumber.append(number).toString();
    }


    public static String buildLongNumberString(String longString, int length) throws Exception {

        long number = Long.parseLong(longString);

        StringBuffer sbNumber = new StringBuffer();

        int numberLength = String.valueOf(number).length();

        int size = length - numberLength;
        for (int i = 0; i < size; i++) {
            sbNumber.append("0");
        }

        return sbNumber.append(number).toString();
    }


    public static boolean isEmpty(String text) {
        if (text == null || text.trim().equals("")) {
            return true;
        }

        return false;
    }

    public static boolean isEmptyAny(String ... strings) {

        for (int i = 0; i < strings.length; i++) {
            if (isEmpty(strings[i])) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmptyAll(String ... strings) {

        for (int i = 0; i < strings.length; i++) {
            if (!isEmpty(strings[i])) {
                return false;
            }
        }
        return true;
    }

    /***
     * 判断是否是数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){

        if (isEmpty(str)) {
            return false;
        }

        for (int i = str.length();--i>=0;){
            char c = str.charAt(i);
            if (!Character.isDigit(c) && c != '.'){
                return false;
            }
        }
        return true;
    }


    public static String getSQLInQueryString(HttpServletRequest request, String parametersName, String quote) {
        String [] ids = request.getParameterValues(parametersName);
        StringBuffer sbIds = new StringBuffer();
        for (int i = 0; ids != null && i < ids.length; i++) {
            sbIds.append(quote).append(ids[i]).append(quote).append(",");
        }
        sbIds = StringUtils.removeLastLetters(sbIds, ",");
        return sbIds.toString();
    }

    public static String getSQLInQueryString(List<String> parameters, String quote) {
        StringBuffer sbIds = new StringBuffer();
        for (int i = 0; parameters != null && i < parameters.size(); i++) {
            sbIds.append(quote).append(parameters.get(i)).append(quote).append(",");
        }
        sbIds = StringUtils.removeLastLetters(sbIds, ",");
        return sbIds.toString();
    }

    public static String md5V2(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("str cannot be null or zero length");
        }
        try {
            MessageDigest digester = MessageDigest.getInstance("MD5");
            digester.update(str.getBytes("UTF-8"));
            return new BigInteger(1, digester.digest()).toString(16);
        } catch (Exception e) {
            //一般不会有异常抛出， 该死的Java受检异常，导致丑陋的代码
        }
        return null;
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

    /**
     * 经过 16 进制运算的 MD5 加密
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年8月11日
     *
     * @param aData
     * @return
     * @throws Exception
     */
    public static String md5WithHex(String aData)throws Exception{
        String resultString = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byte2HexString(md.digest(aData.getBytes("UTF-8")));
        }catch (Exception e){
            e.printStackTrace();
            throw new SecurityException("MD5Utils 运算失败");
        }
        return resultString;
    }


    public static String byte2HexString(byte[] b){
        String ret = "";
        for (int i = 0;i<b.length;i++){
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1){
                hex = '0' + hex;
            }
            ret += hex.toUpperCase();
        }
        return ret;
    }


    public static String format(String text, Map<String, String> values) {

        if (values != null) {
            Iterator<String> itKeys = values.keySet().iterator();

            while (itKeys.hasNext()) {
                String key = itKeys.next();
                String value = values.get(key);

                text = text.replaceAll(":"+key,value);
            }
        }

        return text;
    }

    public static String upcaseFirsetLetter(String text) {
        String firstLetter = text.substring(0, 1).toUpperCase();
        return firstLetter + text.substring(1);
    }

    public static String removeLastLetters(String text, int length) {
        if (!StringUtils.isEmpty(text) && text.length() >= length) {
            return text.substring(0, text.length() - length);
        }

        return null;
    }

    public static String removeLastLetters(String text, String letters) {
        StringBuffer sbText = new StringBuffer(text);
        int lastIndex = text.lastIndexOf(letters);
        if (lastIndex != -1) {
            String lastString = sbText.substring(lastIndex, text.length());
            if (lastString.equals(letters)) {
                return sbText.delete(text.length() - letters.length(), text.length()).toString();
            }
        }
        return text;
    }

    public static StringBuffer removeLastLetters(StringBuffer sbText, String letters) {
        int lastIndex = sbText.lastIndexOf(letters);
        if (lastIndex != -1) {
            String lastString = sbText.substring(lastIndex, sbText.length());
            if (lastString.equals(letters)) {
                return sbText.delete(sbText.length() - letters.length(), sbText.length());
            }
        }
        return sbText;
    }


    public static String toUtf8(String text) throws Exception {
        return new String(text.getBytes("iso-8859-1"), "utf-8");
    }

    /**
     * 将包含有UTF-8编码的URL地址，转换为中文地址
     * @param text
     * @return
     */
    public static String UTF8URLDecode ( String text ) {
        String result = "";
        int p = 0;

        if (text != null && text.length () > 0) {
            text = text.toLowerCase ();
            p = text.indexOf ( "%e" );
            if (p == -1)
                return text;

            while (p != -1) {
                result += text.substring ( 0, p );
                text = text.substring ( p, text.length () );
                if (text == "" || text.length () < 9)
                    return result;

                result += codeToWord ( text.substring ( 0, 9 ) );
                text = text.substring ( 9, text.length () );
                p = text.indexOf ( "%e" );
            }

        }

        return result + text;
    }



    private static String codeToWord ( String text ) {
        String result;

        if (UTF8codeCheck ( text )) {
            byte[] code = new byte[3];
            code[0] = (byte) (Integer.parseInt ( text.substring ( 1, 3 ), 16 ) - 256);
            code[1] = (byte) (Integer.parseInt ( text.substring ( 4, 6 ), 16 ) - 256);
            code[2] = (byte) (Integer.parseInt ( text.substring ( 7, 9 ), 16 ) - 256);
            try {
                result = new String ( code, "UTF-8" );
            }
            catch (UnsupportedEncodingException ex) {
                result = null;
            }
        }
        else {
            result = text;
        }

        return result;
    }

    private static boolean UTF8codeCheck ( String text ) {
        String sign = "";
        if (text.startsWith("%e"))
            for (int i = 0, p = 0; p != -1; i++) {
                p = text.indexOf ( "%", p );
                if (p != -1)
                    p++;
                sign += p;
            }
        return sign.equals ( "147-1" );
    }
    /**
     * 将'\'转义成'/'
     * @param path
     * @return
     */
    public static String updatePath (String path){
        String[] pathArrays = path.split("\\\\");
        StringBuffer paths2=new StringBuffer();
        for(int i=0;i<pathArrays.length;i++){
            paths2.append(pathArrays[i]+"/");
        }
        return paths2.toString();
    }

    /**
     * 将数组字符串组成逗号连接的字符串
     * @param arr
     * @return
     */
    public static String strArr2Str(String[] arr) {
        String string = "";
        for(String str : arr) {
            string += (str + ",");
        }
        return string.substring(0, string.length() - 1);
    }

}
