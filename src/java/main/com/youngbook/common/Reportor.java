package com.youngbook.common;

import com.youngbook.common.KVObject;
import com.youngbook.common.KVObjects;
import com.youngbook.common.config.AesEncrypt;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.dao.MySQLDao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Lee on 2015/11/2.
 */
public class Reportor {

    private List<String> titles = new ArrayList<String>();
    private List<List<String>> rows = new ArrayList<List<String>>();

    public String getTitle(int index) {
        return titles.get(index);
    }

    public String getColumn(int i, int j) {
        return rows.get(i).get(j);
    }

    public int getRowCount() {
        return rows.size();
    }

    public int getColumnCount() {
        return  titles.size();
    }

    public void init(DatabaseSQL dbSQL) throws Exception {
        List<KVObjects> list = MySQLDao.search(dbSQL);

        int index = 0;
        for (KVObjects objects : list) {
            List<String> column = new ArrayList<String>();
            for (int i = 0; i < objects.size(); i++) {
                KVObject o = objects.getByIndex(i);
                String k = o.getKey().toString();
                String v = o.getValue().toString();

                if (index == 0) {
                    titles.add(k);
                }
                column.add(v);
            }
            rows.add(column);
            index++;
        }
    }
}
