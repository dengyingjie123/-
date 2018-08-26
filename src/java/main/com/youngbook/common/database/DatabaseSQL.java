package com.youngbook.common.database;

import com.youngbook.common.KVObject;
import com.youngbook.common.MyException;
import com.youngbook.common.config.Config;
import com.youngbook.common.config.XmlHelper;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.service.customer.CustomerPersonalService;
import org.dom4j.Element;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DatabaseSQL {

    // SQL变量的正则表达式
    public static final String REGEX_PARAMETER = "#\\{[\\s]*[a-z|A-Z|0-9|_]*([\\s]*,type='[d|c|%c|c%|%c%]*')*[\\w]*\\}";


    private StringBuffer sbSQL = new StringBuffer();
    private List<KVObject> parameters = null;

    private XmlHelper xml = null;
    private List<Element> elements = null;
    private String sqlId = "";

    // 保存所有的参数，不与p-statement的index对应
    private List<KVObject> parameters4All = null;


    public static DatabaseSQL getInstance(String sql) {
        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL(sql);
        return dbSQL;
    }

    public static DatabaseSQL getInstance(StringBuffer sbSQL) {
        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL(sbSQL.toString());
        return dbSQL;
    }

    public DatabaseSQL () {

    }

    public static DatabaseSQL newInstance(String id) throws Exception {

        return newInstance(id, null);
    }

    public static DatabaseSQL newInstance(String id, Object object) throws Exception {

        String xmlName = "";
        DatabaseSQL dbSQL = new DatabaseSQL();
        if (object != null) {
            xmlName = object.getClass().getSimpleName() + "SQL";
            dbSQL.newSQL(id, xmlName, object.getClass());
        }
        else {
            dbSQL.newSQLById(id);
        }


        return dbSQL;
    }

    public static void main(String [] args) {


//        System.out.println(getFirstParameterName("select * from #{ abcd ,type='c%'} sfsdf"));

        System.out.println("#{ abcd_ ,type='c%'}".replaceAll("#\\{[\\s]*", "").replaceAll("([\\s]*,type='[d|c|%c|c%|%c%]*')*[\\w]*\\}", ""));


        // Pattern p = Pattern.compile("#\\{[a-z|A-Z|0-9|_]*(,type='[d|c|%c|c%|%c%]*')*[,|\\w]*\\}");
        Pattern p = Pattern.compile(REGEX_PARAMETER);
        String value = "#{ abcd ,type='c%'}";

        String define = getFirstDefineString("abc", value);
        System.out.println(define);
        String type = getAttribute("type", "#{sfsdf,  type = 'c%''}");
        System.out.println(type);
    }

    public static String getFirstDefineString(String name, String value) {

        Pattern p = Pattern.compile("#\\{[\\s]*"+name+"([\\s]*,type='[d|c|%c|c%|%c%]*')*[\\w]*\\}");
        Matcher m = p.matcher(value);
        int count = 0;
        if (m.find()) {
            count++;
            System.out.println("Match number " + count);
            System.out.println("start(): " + m.start());
            System.out.println("end(): "+m.end());
            return value.substring(m.start(), m.end());
        }

        return null;
    }

    public static String getAttribute(String name, String value) {
        Pattern p = Pattern.compile(",[\\s]*"+name+"[\\s]*=[\\s]*'[d|c|%c|c%|%c%]*'");
        Matcher m = p.matcher(value);
        int count = 0;
        if (m.find()) {
            count++;
//            System.out.println("Match number " + count);
//            System.out.println("start(): " + m.start());
//            System.out.println("end(): "+m.end());

//            System.out.println(value.substring(m.start(), m.end()));

            String attributeString = value.substring(m.start(), m.end());
            String regex = ",[\\s]*"+name+"[\\s]*=[\\s]*'";

            Pattern ap = Pattern.compile(regex);
            Matcher am = ap.matcher(attributeString);
            StringBuffer sbAttribute = new StringBuffer();
            // System.out.println(am.find());
            while (am.find()) {
                am.appendReplacement(sbAttribute,"");
            }
            am.appendTail(sbAttribute);

            // 删除最后一个'
            sbAttribute.delete(sbAttribute.length() - 1, sbAttribute.length());

            return sbAttribute.toString();
//            System.out.println(sbAttribute);
        }
        return null;
    }

    public void newSQLById(String sqlId) throws Exception {
        newSQL(sqlId, null, null);
    }

    public void newSQL(String sqlId, String fileName, Class clazz) throws Exception {


        Element finalSQLElement = null;



        if (!StringUtils.isEmpty(fileName) && clazz != null) {

            String path = Config.getClassFolder(clazz);
            File file = new File(path + "\\" +  fileName + ".xml");

            if (!StringUtils.isEmpty(fileName) && file != null && file.exists()) {

                xml = new XmlHelper(file);

                finalSQLElement = xml.getElementByXPath("//sql/select-sql/select[@id='" + sqlId + "']");
            }


        }

        if (finalSQLElement == null) {

            /**
             * 再从SQL池中获得 SQL
             */
            String key = sqlId;

            if (clazz != null) {
                key = clazz.getName() + "." + sqlId;
            }

            String sqlXml = Config.getSystemSQL(key);

            if (StringUtils.isEmpty(sqlXml)) {
                MyException.newInstance("无法获得SQL["+key+"]", "key=" + key).throwException();
            }

            xml = new XmlHelper(sqlXml);
            finalSQLElement = xml.getElementByXPath("//sql/select-sql/select[@id='" + sqlId + "']");
        }

        if (finalSQLElement == null) {
            MyException.newInstance("无法获得SQL["+sqlId+"]", "sqlId=" + sqlId + "&clazz=" + clazz.getName());
        }

        elements = xml.getElementsByXPath("//sql/select-sql/select[@id='"+sqlId+"']/if");
        this.sqlId = sqlId;
    }

    public void initSQL() throws Exception {

        for (Element element : elements) {
            String key4All =  element.attributeValue("test");

            // 不能同时有and和or
            String key4AllTemp = key4All.toLowerCase();
            if (key4AllTemp.contains(" and ") && key4AllTemp.contains(" or ")) {
                MyException.newInstance("不能同时有and和or").throwException();
            }

            // 处理and
            if (key4AllTemp.contains(" and ")) {
                String [] keys4And = key4All.split(" and ");
                for (String condition : keys4And) {
                    condition = condition.replaceAll(" ", "");
                    if (!passCondition(condition, parameters4All)) {
                        element.setText("");
                        break;
                    }
                }
            }
            // 处理or
            else if (key4AllTemp.contains(" or ")) {
                String [] keys4Or = key4All.split(" or ");

                boolean passOrCondition = false;
                for (String condition : keys4Or) {
                    condition = condition.replaceAll(" ", "");
                    if (passCondition(condition, parameters4All)) {
                        passOrCondition = true;
                        break;
                    }
                }

                if (!passOrCondition) {
                    element.setText("");
                }
            }
            // 没有and和or
            else {
                if (!passCondition(key4All, parameters4All)) {
                    element.setText("");
                    continue;
                }
            }

            String sql = element.getText();
            // sql = fillSQL(sql, parameters);
            element.setText(sql);
        }


        Element element = xml.getElementByXPath("//sql/select-sql/select[@id='"+sqlId+"']");


        String finalSQL = element.getStringValue();

        // 替换<和>符号
        finalSQL = finalSQL.replaceAll("&lt;", "<").replaceAll("&gt;", ">");


        // 构造p-statement
        while (StringUtils.contains(REGEX_PARAMETER, finalSQL)) {
            String parameterName = getFirstParameterName(finalSQL);

            boolean done = false;

            for (KVObject p : parameters4All) {
                if (p.getKey().toString().equalsIgnoreCase(parameterName)) {

                    String defineString = getFirstDefineString(parameterName, finalSQL);

                    if (!StringUtils.isEmpty(defineString)) {
                        defineString = defineString.replaceAll("\\{", "\\\\{").replaceAll("\\}", "\\\\}");
                        finalSQL = finalSQL.replaceFirst(defineString, "?");

                        if (parameters == null) {
                            parameters = new ArrayList<KVObject>();
                        }

                        String type = getAttribute("type", defineString);
                        if (!StringUtils.isEmpty(type) && type.contains("c")) {
                            String v  = type.replaceAll("c", p.getValue().toString());
                            p.setValue(v);
                        }

                        // 模糊查询，待优化  D1C31603
//                    if (p.getValue().getClass().isAssignableFrom(String.class)) {
//                        String s = p.getValue().toString();
//                        p.setValue("%" + s + "%");
//                    }

                        int index = parameters.size() + 1;
                        KVObject o = new KVObject(index, p.getValue());

                        parameters.add(o);
                        done = true;
                    }



                }
            }

            if (!done) {
                MyException.newInstance("传入参数与SQL不匹配").throwException();
            }
        }

        this.sbSQL = new StringBuffer(finalSQL);
    }



    private String getFirstParameterName(String sql) {
        Pattern p = Pattern.compile(REGEX_PARAMETER);
        Matcher m = p.matcher(sql);

        if (m.find()) {

            String defineString = sql.substring(m.start(), m.end());

            return defineString.replaceAll("#\\{[\\s]*", "").replaceAll("([\\s]*,type='[d|c|%c|c%|%c%]*')*\\}", "");
        }

        return null;
    }


    private static boolean passCondition(String condition, List<KVObject> parameters) throws Exception{

        String [] KEYS = new String[] {"=null", "!=null", ">", "<", ">=", "<=", "="};

        condition = condition.replaceAll(" ", "");

        String value = "";

        String conditionTemp = condition.toLowerCase();

        boolean support = false;
        for (String key : KEYS) {
            if (conditionTemp.contains(key)) {
                support = true;
                break;
            }
        }


        if (!support) {
            MyException.newInstance("控制条件暂不支持").throwException();
        }


        String op = "=null";
        if (conditionTemp.contains(op) && !conditionTemp.contains("!=null")) {
            condition = condition.replaceAll(op, "").replaceAll("#\\{", "").replaceAll("\\}", "");
            value = getKeyString(condition, parameters);

            if (value == null) {
                return true;
            }

            if (StringUtils.isEmpty(value)) {
                return true;
            }

            return false;
        }

        op = "!=null";
        if (conditionTemp.contains("!=null")) {
            condition = condition.replaceAll(op, "").replaceAll("#\\{", "").replaceAll("\\}", "");
            value = getKeyString(condition, parameters);

            if (value == null) {
                return false;
            }

            if (!StringUtils.isEmpty(value)) {
                return true;
            }
            return  false;
        }


        op = "<";
        if (conditionTemp.contains(op) && !conditionTemp.contains("<=")) {
            String [] c = condition.split(op);
            value = getKeyString(c[0], parameters);
            double number = Double.parseDouble(c[1]);

            if (!StringUtils.isEmpty(value)) {
                return Double.parseDouble(value) < number;
            }
        }


        op = "<=";
        if (conditionTemp.contains(op)) {
            String [] c = condition.split(op);
            value = getKeyString(c[0], parameters);
            double number = Double.parseDouble(c[1]);

            if (!StringUtils.isEmpty(value)) {
                return Double.parseDouble(value) <= number;
            }
        }

        op = ">";
        if (conditionTemp.contains(op) && !conditionTemp.contains(">=")) {
            String [] c = condition.split(op);
            value = getKeyString(c[0], parameters);
            double number = Double.parseDouble(c[1]);

            if (!StringUtils.isEmpty(value)) {
                return Double.parseDouble(value) > number;
            }
        }

        op = ">=";
        if (conditionTemp.contains(op)) {
            String [] c = condition.split(op);
            value = getKeyString(c[0], parameters);
            double number = Double.parseDouble(c[1]);

            if (!StringUtils.isEmpty(value)) {
                return Double.parseDouble(value) >= number;
            }
        }

        op = "=";
        if (conditionTemp.contains(op)) {
            String [] c = condition.split(op);
            value = getKeyString(c[0], parameters);
            double number = Double.parseDouble(c[1]);

            if (!StringUtils.isEmpty(value)) {
                return Double.parseDouble(value) == number;
            }
        }

        op = "!=";
        if (conditionTemp.contains(op)) {
            String [] c = condition.split(op);
            value = getKeyString(c[0], parameters);
            double number = Double.parseDouble(c[1]);

            if (!StringUtils.isEmpty(value)) {
                return Double.parseDouble(value) != number;
            }
        }

        return false;
    }


    private static String getKeyString(String key, List<KVObject> parameters) {
        key = key.replaceAll("#\\{", "").replaceAll("\\}", "");
        for (int i = 0; parameters != null && i < parameters.size(); i++) {
            KVObject parameter = parameters.get(i);
            if (parameter.getKey().toString().equalsIgnoreCase(key)) {
                if (parameter != null && parameter.getValue() != null) {
                    return parameter.getValue().toString();
                }
            }
        }
        return null;
    }


    public void newSQL(String sql) {
        sbSQL = new StringBuffer(sql);
        parameters = null;
    }

    public void setSQL(String sql) {
        sbSQL = new StringBuffer(sql);
    }

    public void newSQLWithParameters(String sql) {
        sbSQL = new StringBuffer(sql);
    }

    public DatabaseSQL addParameter(int index, Object value) {
        if (parameters == null) {
            parameters = new ArrayList<KVObject>();
        }

        KVObject parameter = new KVObject(index, value);

        parameters.add(parameter);

        return this;
    }

    public DatabaseSQL addParameter4All(String key, Object value) {
        if (parameters4All == null) {
            parameters4All = new ArrayList<KVObject>();
        }

        KVObject parameter = new KVObject(key, value);

        parameters4All.add(parameter);

        return this;
    }


    public String getSQL() {
        return sbSQL.toString();
    }

    public List<KVObject> getParameters() {
        return parameters;
    }

    public void setParameters(List<KVObject> parameters) {
        this.parameters = parameters;
    }
}
