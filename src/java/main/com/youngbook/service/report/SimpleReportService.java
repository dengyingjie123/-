package com.youngbook.service.report;

import com.youngbook.common.KVObject;
import com.youngbook.common.KVObjects;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.JSONUtils;
import com.youngbook.common.utils.ObjectUtils;
import com.youngbook.dao.JSONDao;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.jeasyui.DataGridColumnPO;
import com.youngbook.service.BaseService;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;


@Component("simpleReportService")
public class SimpleReportService extends BaseService {

    public static JSONArray buildColumnNames(List<KVObjects> list, String nameConfig) throws Exception {
        JSONArray columnNames = new JSONArray();
        if (list != null && list.size() > 0) {
            KVObjects kvObjects = list.get(0);


            for (int i = 0; i < kvObjects.size(); i++) {
                KVObject kv = kvObjects.get(i);

                String keyConfig = nameConfig + "_" + kv.getKey().toString();
                String columnConfigValue = Config.getReportConfig(keyConfig);
                DataGridColumnPO columnPO = JSONDao.parse(columnConfigValue,DataGridColumnPO.class);

                columnNames.add(columnPO.toJsonObject());
            }
        }

        return columnNames;
    }




    public List<KVObjects> getListReportData(String reportName, List<KVObject> parameters, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance(reportName, this);
        for (int i = 0; parameters != null && i < parameters.size(); i++) {
            KVObject parameter = parameters.get(i);
            dbSQL.addParameter4All(parameter.getKeyStringValue(), parameter.getValueStringValue());
        }
        dbSQL.initSQL();

        List<KVObjects> list = MySQLDao.search(dbSQL, conn);

        return list;
    }

}
