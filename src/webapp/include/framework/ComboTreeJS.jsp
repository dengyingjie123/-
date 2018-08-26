<%@ page import="java.lang.reflect.Field" %><%@ page import="com.youngbook.annotation.EnumType" %><%@ page import="java.util.HashMap" %><%@ page import="java.util.Iterator" %><%@ page import="com.youngbook.common.utils.StringUtils" %><%@ page contentType="text/html;charset=UTF-8" language="java" %><%
    String className = request.getParameter("className");
    String id = request.getParameter("id");
    Class clazz = Class.forName(className);
    HashMap<String, String> hmValues = new HashMap<String, String>();

    Field[] fields = clazz.getDeclaredFields();
    for (int i = 0; i < fields.length; i++) {
        Field field = fields[i];
        EnumType enumType = field.getAnnotation(EnumType.class);
        if (enumType != null) {
            if (enumType.id().equals(id)) {
                hmValues.put(enumType.value(), enumType.display());
            }
        }
    }

    Iterator<String> iterator = hmValues.keySet().iterator();

    StringBuffer sbJSON = new StringBuffer();

    while (iterator.hasNext()) {
        String value = iterator.next();
        String display = hmValues.get(value);

        sbJSON.append("{'id':'"+value+"','text':'"+display+"'},");
    }

    sbJSON = StringUtils.removeLastLetters(sbJSON, ",");

    sbJSON.insert(0, "[").append("]");
    out.print(sbJSON);
%>