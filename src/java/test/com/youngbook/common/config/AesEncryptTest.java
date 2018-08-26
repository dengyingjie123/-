package com.youngbook.common.config;

import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.customer.CustomerPersonalPO;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AesEncryptTest extends TestCase {

    @Test
    public void testDecrypt() throws Exception {
        // String sql = "select * from crm_customerpersonal c where c.state=0 and c.name<>''";

        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT");
        sbSQL.append("     ca. NAME bankName,");
        sbSQL.append("     ca.number bankNumber,");
        sbSQL.append("     c.id,");
        sbSQL.append("     ca.bank,");
        sbSQL.append("     c. NAME,");
        sbSQL.append("     kvSex.V SexName,");
        sbSQL.append("     cc.Number,");
        sbSQL.append("     c.Mobile,");
        sbSQL.append("     u.name OperatorName,");
        sbSQL.append("     c.operatetime");
        sbSQL.append(" FROM");
        sbSQL.append("     crm_customerpersonal c");
        sbSQL.append(" LEFT JOIN crm_customeraccount ca ON ca.customerId = c.id");
        sbSQL.append(" LEFT JOIN system_kv kvSex ON kvSex.K = c.Sex");
        sbSQL.append(" LEFT JOIN crm_customercertificate cc ON cc.CustomerId = c.id");
        sbSQL.append(" left JOIN system_user u on u.id=c.OperatorId");
        sbSQL.append(" WHERE");
        sbSQL.append("     1 = 1");
        sbSQL.append(" AND c.state = 0");
        sbSQL.append(" AND cc.state = 0");
        sbSQL.append(" and u.state=0");
        sbSQL.append(" AND kvSex.GroupName = 'Sex'");
        sbSQL.append(" AND c.`Name` = '童燕'");
        sbSQL.append(" AND ca.state = 0");
        sbSQL.append(" ORDER BY");
        sbSQL.append("     c. NAME");
        List<Map<String, Object>> users = MySQLDao.query(sbSQL.toString());

        int index = 1;
        for (Map<String, Object> user : users) {
            Iterator<String> keys = user.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = user.get(key).toString();

                if (key.equalsIgnoreCase("bankNumber")) {
                    value = AesEncrypt.decrypt(value);
                }

                System.out.print(value + " ");
            }
            System.out.println();
            index++;
        }
    }
}