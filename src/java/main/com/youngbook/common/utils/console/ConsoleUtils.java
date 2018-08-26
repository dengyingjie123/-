package com.youngbook.common.utils.console;

/**
 * Created by Lee on 2016/5/3.
 */
public class ConsoleUtils {

    public static void main(String[] args) throws Exception {

//        ConsoleTable t = new ConsoleTable(4, true);
        ConsoleTable t = ConsoleTable.defaultTable(4);
        t.row();
        t.column("序号").column("姓名").column("性别").column("年龄");

        t.row();
        t.column("1").column("张12adad").column("男").column("11");

        t.row();
        t.column("22").column("23123强3333");

        t.print();

    }
}
