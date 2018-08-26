package com.youngbook.common.wf.common;

/**
 * 程序：李扬
 * 时间：2004-10-20
 * 说明：业务数据结构，对应数据库中的表结构
 */
public class BizField {

    protected int intIndex = Integer.MAX_VALUE; //字段索引
    protected String strName = new String(); //字段名
    protected String strTitle = new String(); //显示的字段名
    protected String strType = new String(); //字段类型，数据库中定义的类型
    protected boolean bolNullable = false; //是否为空
    protected String strDefault = new String(); //字段默认值
    protected String strComments = new String(); //字段说明
    protected int intPrecision = Integer.MAX_VALUE; //字段的范围， 例如 NUMBER(10,2)，此处的值为10
    protected int intScale = Integer.MAX_VALUE; //字段的精度， 例如 NUMBER(10,2)，此处的值为2
    protected int intState = 1; // 该字段是否可以编辑0：可编辑，1：不可编辑(默认值，可不出现)，2：隐藏
    //下面的成员将在用于代码自动生成
    protected StringBuffer sbCodeName = new StringBuffer(); //根据数据库字段名生成代码中的变量，如 strNAME, intAGE
    protected String strCodeType = new String(); //变量类型String, int, double
    protected String strInitValue = new String(); //变量的初始值

    public BizField() {
    }

    public int getIndex() {
        return intIndex;
    }

    public String getTitle() {
        return strTitle;
    }

    public String getName() {
        return strName;
    }

    public String getType() {
        return strType;
    }

    public boolean getNullable() {
        return bolNullable;
    }

    public String getDefault() {
        return strDefault;
    }

    public String getComments() {
        return strComments;
    }

    public int getState() {
        return intState;
    }

    public StringBuffer getCodeName() {
        return sbCodeName;
    }

    public String getCodeType() {
        return strCodeType;
    }

    public String getInitValue() {
        return strInitValue;
    }

    public int getPrecision() {
        return intPrecision;
    }

    public int getScale() {
        return intScale;
    }

    public void setScale(int Scale) {
        intScale = Scale;
    }

    public void setIndex(int index) {
        intIndex = index;
    }

    public void setTitle(String title) {
        strTitle = title;
    }

    public void setName(String name) {
        strName = name;
    }

    public void setType(String type) {
        strType = type;
    }

    public void setNullable(boolean nullable) {
        bolNullable = nullable;
    }

    public void setDefault(String defaults) {
        strDefault = defaults;
    }

    public void setComments(String comments) {
        strComments = comments;
    }

    public void setState(int state) {
        intState = state;
    }

    public void setPrecision(int Precision) {
        intPrecision = Precision;
    }

    public void setCodeName(StringBuffer CodeName) {
        sbCodeName = CodeName;
    }

    public void setCodeType(String CodeType) {
        strCodeType = CodeType;
    }

    public void setInitValue(String InitValue) {
        strInitValue = InitValue;
    }

    public boolean isEmptyPrecision() {
        if (intPrecision == Integer.MAX_VALUE) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isEmptyCodeName() {
        if (sbCodeName != null && !sbCodeName.toString().equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean isEmptyCodeType() {
        if (strCodeType != null && !strCodeType.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean isEmptyInitValue() {
        if (strInitValue != null && !strInitValue.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean isEmptyScale() {
        if (intScale == Integer.MAX_VALUE) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isEmptyName() {
        if (strName != null && !strName.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

}
