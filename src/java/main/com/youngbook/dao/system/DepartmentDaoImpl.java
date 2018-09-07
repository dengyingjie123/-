package com.youngbook.dao.system;

import com.youngbook.common.MyException;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.system.UserPositionInfoPO;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Lee on 2016/5/28.
 */
@Component("departmentDao")
public class DepartmentDaoImpl implements IDepartmentDao {

    public List<UserPositionInfoPO> getUserDepartmentInfo(String userId, Connection conn) throws Exception {

        if (StringUtils.isEmpty(userId)) {
            MyException.newInstance("无法获得用户编号").throwException();
        }

        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT");
        sbSQL.append("     u.id UserId,");
        sbSQL.append("     u. NAME UserName,");
        sbSQL.append("     d.ID DepartmentId,");
        sbSQL.append("     d.fromName DepartmentFullName,");
        sbSQL.append("     d.`name` DepartmentName,");
        sbSQL.append("     d.typeID DepartmentTypeId,");
        sbSQL.append("     p.`Name` PositionName,");
        sbSQL.append("     p.Id PositionId,");
        sbSQL.append("     pu.states `Status`");
        sbSQL.append(" FROM");
        sbSQL.append("     system_department d,");
        sbSQL.append("     system_position p,");
        sbSQL.append("     system_positionuser pu,");
        sbSQL.append("     system_user u");
        sbSQL.append(" WHERE");
        sbSQL.append("     1 = 1");
        sbSQL.append(" AND u.state = 0");
        sbSQL.append(" AND u.Id = ?");
        sbSQL.append(" AND pu.userId = u.id");
        sbSQL.append(" AND d.ID = p.DepartmentId");
        sbSQL.append(" AND pu.positionId = p.Id");
        sbSQL.append(" AND p.DepartmentId = d.ID");

        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL(sbSQL.toString());
        dbSQL.addParameter(1, userId);

        List<UserPositionInfoPO> list = MySQLDao.search(dbSQL.getSQL(), dbSQL.getParameters(), UserPositionInfoPO.class, null, conn);
        return list;
    }

    public UserPositionInfoPO getDefaultUserPositionInfo(String userId, Connection conn) throws Exception {

        if (StringUtils.isEmpty(userId)) {
            MyException.newInstance("无法获得用户编号").throwException();
        }

        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT");
        sbSQL.append("     u.id UserId,");
        sbSQL.append("     u.`name` UserName,");
        sbSQL.append("     d.id DepartmentId,");
        sbSQL.append("     d.`name` DepartmentName,");
        sbSQL.append("     d.fromName DepartmentFullName,");
        sbSQL.append("     p.id positionId,");
        sbSQL.append("     p.`Name` PositionName");
        sbSQL.append(" FROM");
        sbSQL.append("     system_position p,");
        sbSQL.append("     system_positionuser pu,");
        sbSQL.append("     system_user u,");
        sbSQL.append("     system_department d");
        sbSQL.append(" WHERE");
        sbSQL.append("     1 = 1");
        sbSQL.append(" AND u.state = 0");
        sbSQL.append(" AND pu.states = 1");
        sbSQL.append(" AND u.id =?");
        sbSQL.append(" AND pu.userId = u.id");
        sbSQL.append(" AND p.Id = pu.positionId");
        sbSQL.append(" AND p.DepartmentId = d.id");

        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL(sbSQL.toString());
        dbSQL.addParameter(1, userId);

        List<UserPositionInfoPO> list = MySQLDao.search(dbSQL.getSQL(), dbSQL.getParameters(), UserPositionInfoPO.class, null, conn);

        if (list != null && list.size() == 1) {
            return list.get(0);
        }

        return null;
    }
}
