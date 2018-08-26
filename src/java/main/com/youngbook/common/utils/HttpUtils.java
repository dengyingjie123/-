package com.youngbook.common.utils;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import com.youngbook.annotation.Table;
import com.youngbook.common.KVObject;
import com.youngbook.service.system.LogService;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntity;
//import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpUtils {

    // 创建HttpClient对象
    public static HttpClient httpClient;

    public static final String ENCODE_GBK = "gbk";
    public static final String ENCODE_UTF8 = "utf-8";

    public static void main(String [] args) throws Exception {

        String url = "http://localhost:8080/core";
        String file = "d:/ticket.rar";
        Map<String, String> files = new HashMap<String, String>();
        files.put("file1", file);

        //HttpUtils.post(url, null, files);
    }

    public static <T> T getAttribute(String attributeName, Class<T> clazz, HttpServletRequest request) throws Exception {
        Object obj = request.getAttribute(attributeName);

        T exceptedObject = ObjectUtils.convert(obj, clazz);

        return exceptedObject;
    }

    public static List<KVObject> getParameters(String url) throws Exception {

        List<KVObject> parameters = new ArrayList<KVObject>();

        String [] ps = url.split("&");
        for (int i = 0; i < ps.length; i++) {
            String p = ps[i];
            if (StringUtils.isEmpty(p)) {
                continue;
            }

            String [] temp = p.split("=");
            KVObject parameter = new KVObject(temp[0], temp[1]);

            parameters.add(parameter);
        }

        return parameters;
    }

//    public static String post(String postUrl, Map<String, String> params,
//                              Map<String, String> files) throws ClientProtocolException,
//            IOException {
//        CloseableHttpResponse response = null;
//        InputStream is = null;
//        String results = null;
//        CloseableHttpClient httpclient = HttpClients.createDefault();
//
//        try {
//
//            HttpPost httppost = new HttpPost(postUrl);
//
//            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//
//            if (params != null) {
//                for (String key : params.keySet()) {
//                    StringBody value = new StringBody(params.get(key));
//                    builder.addPart(key, value);
//                }
//            }
//
//            if (files != null && files.size() > 0) {
//                for (String key : files.keySet()) {
//                    String value = files.get(key);
//                    FileBody body = new FileBody(new File(value));
//                    builder.addPart(key, body);
//                }
//            }
//
//            HttpEntity reqEntity = builder.build();
//            httppost.setEntity(reqEntity);
//
//            response = httpclient.execute(httppost);
//            // assertEquals(200, response.getStatusLine().getStatusCode());
//
//            HttpEntity entity = response.getEntity();
//            if (entity != null) {
//                is = entity.getContent();
//                StringWriter writer = new StringWriter();
//                IOUtils.copy(is, writer, "UTF-8");
//                results = writer.toString();
//            }
//
//        } finally {
//            try {
//                if (is != null) {
//                    is.close();
//                }
//            } catch (Throwable t) {
//                // No-op
//            }
//
//            try {
//                if (response != null) {
//                    response.close();
//                }
//            } catch (Throwable t) {
//                // No-op
//            }
//
//            httpclient.close();
//        }
//
//        return results;
//    }

    /**
     * 从 Request 获取实例 1
     *
     * @param request
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static  <T> T getInstanceFromRequest(HttpServletRequest request, Class<T> clazz) throws Exception {

        Table t = clazz.getAnnotation(Table.class);

        String prefix = null;
        if (t != null) {
            prefix = t.jsonPrefix();
        }

        return getInstanceFromRequest(request, prefix, clazz);
    }

    /**
     * 从 Request 获取实例 2
     *
     * @param request
     * @param prefix
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static  <T> T getInstanceFromRequest(HttpServletRequest request,String prefix, Class<T> clazz) throws Exception {

        T obj = clazz.newInstance();

        for(Field field : clazz.getDeclaredFields()) {

            String fieldName = field.getName();

            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String setter = "set" + firstLetter + fieldName.substring(1);

            Method setMethod = Utils.getMethod(clazz, setter, new Class[]{field.getType()});

            String parameterName = fieldName;
            if (!StringUtils.isEmpty(prefix)) {
                parameterName = prefix + "." + parameterName;
            }
            LogService.debug("HttpUtils.getInstanceFromRequest(): " + parameterName, HttpUtils.class);

            Object value = request.getParameter(parameterName);

            if (value != null) {

                if (field.getType().getName().equals(int.class.getName())) {

                    if (value != null && value.toString().trim().equals("")) {
                        setMethod.invoke(obj, Integer.MAX_VALUE);
                    }
                    else {
                        setMethod.invoke(obj, Integer.valueOf(value.toString()));
                    }

                }
                else if (field.getType().getName().equals(float.class.getName())) {
                    if (value != null && value.toString().trim().equals("")) {
                        setMethod.invoke(obj, Float.MAX_VALUE);
                    }
                    else {
                        setMethod.invoke(obj, Float.valueOf(value.toString()));
                    }

                }
                else if (field.getType().getName().equals(double.class.getName())) {
                    if (value != null && value.toString().trim().equals("")) {
                        setMethod.invoke(obj, Double.MAX_VALUE);
                    }
                    else {
                        setMethod.invoke(obj, Double.valueOf(value.toString()));
                    }

                }
                else if (field.getType().getName().equals(long.class.getName())) {
                    if (value != null && value.toString().trim().equals("")) {
                        setMethod.invoke(obj, Long.MAX_VALUE);
                    }
                    else {
                        setMethod.invoke(obj, Long.valueOf(value.toString()));
                    }

                }
                else {
                    setMethod.invoke(obj, new Object[] {value});
                }
            }
        }

        return obj;

    }

    /**
     * 从 Request 获取客户端 IP
     *
     * @param request
     * @return
     */
    public static String getClientIPFromRequest(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 返回request所有参数
     * @param request
     * @return
     */
    public static String getParametersStringValue(HttpServletRequest request)  {
        StringBuffer parameter = new StringBuffer();

        for (Object key : request.getParameterMap().keySet()) {
            String[] valueArray = (String[])request.getParameterMap().get(key);
            for (String value : valueArray) {
                parameter.append(key);
                parameter.append("=");
                parameter.append(value);
                parameter.append("&");
            }
        }
        // 删除参数最后一个&
        parameter = StringUtils.removeLastLetters(parameter, "&");

        return  parameter.toString();
    }

    public void uploadFile() throws Exception {


//        String url = "http://localhost:8080/core";
//        String charset = "UTF-8";
//        String param = "value";
//        File textFile = new File("D:/ticket.txt");
//        File binaryFile = new File("d:/ticket.rar");
//        String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
//        String CRLF = "\r\n"; // Line separator required by multipart/form-data.
//
//        URLConnection connection = new URL(url).openConnection();
//        connection.setDoOutput(true);
//        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
//
//        connection.connect();
//
//        try (
//                OutputStream output = connection.getOutputStream();
//                PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);
//        ) {
//            // Send normal param.
//            writer.append("--" + boundary).append(CRLF);
//            writer.append("Content-Disposition: form-data; name=\"param\"").append(CRLF);
//            writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
//            writer.append(CRLF).append(param).append(CRLF).flush();
//
//            // Send text file.
//            writer.append("--" + boundary).append(CRLF);
//            writer.append("Content-Disposition: form-data; name=\"textFile\"; filename=\"" + textFile.getName() + "\"").append(CRLF);
//            writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF); // Text file itself must be saved in this charset!
//            writer.append(CRLF).flush();
//            Files.copy(textFile.toPath(), output);
//            output.flush(); // Important before continuing with writer!
//            writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.
//
//            // Send binary file.
//            writer.append("--" + boundary).append(CRLF);
//            writer.append("Content-Disposition: form-data; name=\"binaryFile\"; filename=\"" + binaryFile.getName() + "\"").append(CRLF);
//            writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(binaryFile.getName())).append(CRLF);
//            writer.append("Content-Transfer-Encoding: binary").append(CRLF);
//            writer.append(CRLF).flush();
//            Files.copy(binaryFile.toPath(), output);
//            output.flush(); // Important before continuing with writer!
//            writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.
//
//            // End of multipart/form-data.
//            writer.append("--" + boundary + "--").append(CRLF).flush();
//        }
//
//// Request is lazily fired whenever you need to obtain information about response.
//        int responseCode = ((HttpURLConnection) connection).getResponseCode();
//        System.out.println(responseCode); // Should be 200

    }

    /**
     * 获取请求参数
     * 若为null，则返回空字符串
     * @param request
     * @param parameter
     * @return
     * @throws Exception
     */
    public static String getParameter(HttpServletRequest request, String parameter) throws Exception {

        if (!StringUtils.isEmpty(parameter)) {

            String v = request.getParameter(parameter);

            if (!StringUtils.isEmpty(v) && v.equals("null")) {
                return null;
            }

            return v;
        }
        return "";
    }


    /**
     * 匹配 parameter[0], parameter[1] 参数
     *
     * @param request
     * @param parameterName
     * @return
     */
    public static List<String> getParameters(HttpServletRequest request, String parameterName) {

        String regEx = "^"+ parameterName + "\\[[0-9]\\]$";
        Pattern pat = Pattern.compile(regEx);

        List<String> parameters = new ArrayList<String>();

        Enumeration paramNames = request.getParameterNames();

        while(paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();

            Matcher mat = pat.matcher(paramName);
            boolean found = mat.find();

            if (found) {
                parameters.add(request.getParameter(paramName));
            }
        }

        return parameters;
    }

    /**
     * 获取请求 1
     *
     * @param url 发送请求的URL
     * @return 服务器响应字符串
     * @throws Exception
     */
    public static String getRequest(String url) throws Exception {
        httpClient= new DefaultHttpClient();
        return getRequest(url, null, null);

    }

    /**
     * 获取请求 2
     *
     * @param url
     * @param parameters
     * @param encoding
     * @return
     * @throws Exception
     */
    public static String getRequest(String url, Map<String, String> parameters, String encoding) throws Exception {

        if (StringUtils.isEmpty(encoding)) {
            encoding = ENCODE_UTF8;
        }

        StringBuffer sbUrl = new StringBuffer(url);

        httpClient= new DefaultHttpClient();

        if (parameters != null) {
            Iterator<String> itKeys = parameters.keySet().iterator();
            while (itKeys.hasNext()) {
                String key = itKeys.next();
                String value = parameters.get(key);

                String encodeValue = URLEncoder.encode(value, encoding);

                sbUrl.append("&").append(key).append("=").append(encodeValue);
            }
        }

        LogService.debug("HttpUtils.Get: " + sbUrl.toString(), HttpUtils.class);

        try{
            // 创建HttpGet对象。
            HttpGet get = new HttpGet(sbUrl.toString());
//            get.addHeader("Content-Type","text/html;charset=gbk");
//            get.setHeader("Content-Type","text/html;charset=gbk");
            // 发送GET请求
            HttpResponse httpResponse = httpClient.execute(get);
            // 如果服务器成功地返回响应
            if (httpResponse.getStatusLine()
                    .getStatusCode() == 200) {
                // 获取服务器响应字符串
                String result = EntityUtils.toString(httpResponse.getEntity(), encoding);
                return result;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return "获取数据失败！";
        }
        finally{
            httpClient.getConnectionManager().shutdown();
        }

        return null;
    }

    /**
     * 发送一个 Post 请求
     *
     * @param url 发送请求的URL
     * @return 服务器响应字符串
     * @throws Exception
     */
    public static String postRequest(String url, Map<String,String> rawParams) {

        httpClient= new DefaultHttpClient();

        try{
            // 创建HttpPost对象。
            HttpPost post = new HttpPost(url);
            // 如果传递参数个数比较多的话可以对传递的参数进行封装
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            if (rawParams != null) {
                for(String key : rawParams.keySet())
                {
                    //封装请求参数
                    params.add(new BasicNameValuePair(key , rawParams.get(key)));
                }
            }

            // 设置请求参数
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,HttpUtils.ENCODE_UTF8);
            post.setEntity(entity);
            // 发送POST请求
            HttpResponse httpResponse = httpClient.execute(post);

            if( httpResponse.getStatusLine().getStatusCode() == 200 ){

                InputStream inputStream = null;
                BufferedReader bufferedReader = null;
                try {
                    inputStream = httpResponse.getEntity().getContent();
                    StringBuilder builder = new StringBuilder();
                    // 读取数据流
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line = "";
                    while( (line = bufferedReader.readLine()) != null ){
                        builder.append(line);
                    }
                    String result = new String(builder.toString().getBytes(), "utf-8");
                    return result;
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
                finally {
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }

                    if (inputStream != null) {
                        inputStream.close();
                    }
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            httpClient.getConnectionManager().shutdown();
        }
        return null;
    }
}
