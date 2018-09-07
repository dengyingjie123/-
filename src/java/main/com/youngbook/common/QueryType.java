package com.youngbook.common;

/**
 * 查询类型
 * User: Lee
 * Date: 14-4-2
 */
public class QueryType {
    // 默认都是精确查询
    public int stringType = Database.QUERY_EXACTLY;
    public int numberType = Database.NUMBER_EQUAL;

    public QueryType() {

    }

    public QueryType(int stringType, int numberType) {
        this.stringType = stringType;
        this.numberType = numberType;
    }
}
