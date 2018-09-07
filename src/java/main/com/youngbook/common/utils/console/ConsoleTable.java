package com.youngbook.common.utils.console;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 2016/5/3.
 */
public class ConsoleTable {

    public static final String Charset_EN = "UTF-8";
    public static final String Charset_CHS = "gbk";

    private String charset = "gbk";

    private List<List> rows = new ArrayList<List>();

    private int colum;

    private int[] columLen;

    private static int margin = 2;

    private boolean printHeader = false;

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public static ConsoleTable defaultTable(int columnCount) {
        ConsoleTable consoleTable = new ConsoleTable(columnCount,true);
        consoleTable.setCharset(ConsoleTable.Charset_CHS);
        return consoleTable;
    }

    public ConsoleTable(int colum, boolean printHeader) {
        this.printHeader = printHeader;
        this.colum = colum;
        this.columLen = new int[colum];
    }

    public void row() {
        List row = new ArrayList(colum);
        rows.add(row);
    }

    public void print() {
        System.out.println(this.toString());
    }

    public ConsoleTable column(Object value) {
        if (value == null) {
            value = "NULL";
        }
        List row = rows.get(rows.size() - 1);
        row.add(value);
        int len = value.toString().getBytes().length;
        if (columLen[row.size() - 1] < len)
            columLen[row.size() - 1] = len;
        return this;
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();

        int sumlen = 0;
        for (int len : columLen) {
            sumlen += len;
        }
        if (printHeader) {
            buf.append("|").append(printChar("=", sumlen + margin * 2 * colum + (colum - 1))).append("|\n");
        }
        else {
            buf.append("|").append(printChar("-", sumlen + margin * 2 * colum + (colum - 1))).append("|\n");
        }

        try {

            for (int ii = 0; ii < rows.size(); ii++) {
                List row = rows.get(ii);
                for (int i = 0; i < colum; i++) {
                    String o = "";
                    if (i < row.size())
                        o = row.get(i).toString();
                    buf.append('|').append(printChar(" ", margin)).append(o);
                    buf.append(printChar(" ", columLen[i] - o.getBytes(charset).length + margin));
                }
                buf.append("|\n");
                if (printHeader && ii == 0) {
                    buf.append("|").append(printChar("=", sumlen + margin * 2 * colum + (colum - 1))).append("|\n");
                }
                else {
                    buf.append("|").append(printChar("-", sumlen + margin * 2 * colum + (colum - 1))).append("|\n");
                }
            }


        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return buf.toString();
    }

    private String printChar(String s, int len) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < len; i++) {
            buf.append(s);
        }
        return buf.toString();
    }

}
