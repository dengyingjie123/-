package com.youngbook.service.oa.finance;

import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.oa.finance.MoneyLogPO;
import com.youngbook.entity.vo.oa.finance.MoneyLogVO;
import com.youngbook.service.BaseService;
import com.youngbook.service.system.LogService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;


public class MoneyLogService extends BaseService {

    public Pager list(MoneyLogVO moneyLogVO, List<KVObject> conditions, String departments) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, "MoneyTime desc"));

        //
        if (departments != null && !departments.equals("")) {
            KVObject kvDepartment = new KVObject("departmentId", " in ("+Database.encodeSQL(departments)+")" );
            conditions.add(kvDepartment);
        }

        //Pager pager = Pager.query(moneyLog,conditions,request,queryType);
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT");
        sbSQL.append(" 	ml.SID,");
        sbSQL.append(" 	ml.id,");
        sbSQL.append(" 	ml.state,");
        sbSQL.append(" 	kv.V typename,");
        sbSQL.append(" 	ml. NAME,");
        sbSQL.append(" 	ml.Money,");
        sbSQL.append("     d.id departmentId,");
        sbSQL.append("     d. NAME departmentName,");
        sbSQL.append("     ml.MoneyTime,");
        sbSQL.append("     kv_inOrOut.v inOrOutName,");
        sbSQL.append("     ml.`Comment`,");
        sbSQL.append("     USER . NAME OperatorName,");
        sbSQL.append("     ml.OperateTime");
        sbSQL.append(" FROM");
        sbSQL.append("     finance_moneylog ml,");
        sbSQL.append("     system_kv kv,");
        sbSQL.append("     system_kv kv_inOrOut,");
        sbSQL.append("     system_user USER,");
        sbSQL.append("     system_department d");
        sbSQL.append(" WHERE");
        sbSQL.append("     ml.type = kv.K");
        sbSQL.append(" AND d.id = ml.departmentId");
        sbSQL.append(" AND kv_inOrOut.GroupName = 'OA_Finance_MoneyLog_InOrOut'");
        sbSQL.append(" AND ml.OperatorId = USER .id");
        sbSQL.append(" AND ml.state = 0");
        sbSQL.append(" AND ml.inOrOut = kv_inOrOut.k");
        sbSQL.append(" and USER.state=0");

        LogService.debug("查询记账列表：MoneyLogService.list(): " + sbSQL.toString(), this.getClass());

        Pager pager = Pager.query(sbSQL.toString() ,moneyLogVO,conditions,request,queryType);

        return pager;
    }

    public int insertOrUpdate(MoneyLogPO moneyLog, UserPO user, Connection conn) throws Exception{

        int count = 0;
        // 新增
        if (moneyLog.getId().equals("")) {
            moneyLog.setSid(MySQLDao.getMaxSid("finance_moneyLog", conn));
            moneyLog.setId(IdUtils.getUUID32());
            moneyLog.setState(Config.STATE_CURRENT);
            moneyLog.setOperatorId(user.getId());
            moneyLog.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(moneyLog, conn);
        }
        // 更新
        else {
            MoneyLogPO temp = new MoneyLogPO();
            temp.setSid(moneyLog.getSid());
            temp = MySQLDao.load(temp, MoneyLogPO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                moneyLog.setSid(MySQLDao.getMaxSid("finance_moneyLog", conn));
                moneyLog.setState(Config.STATE_CURRENT);
                moneyLog.setOperatorId(user.getId());
                moneyLog.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(moneyLog, conn);
            }
        }

        if (count != 1) {
            throw new Exception("数据库异常");
        }

        return count;
    }

    public int delete(MoneyLogPO moneyLog, UserPO user, Connection conn) throws Exception {
        int count = 0;

        moneyLog = MySQLDao.load(moneyLog, MoneyLogPO.class);
        moneyLog.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(moneyLog, conn);
        if (count == 1) {
            moneyLog.setSid(MySQLDao.getMaxSid("finance_moneyLog", conn));
            moneyLog.setState(Config.STATE_DELETE);
            moneyLog.setOperateTime(TimeUtils.getNow());
            moneyLog.setOperatorId(user.getId());
            count = MySQLDao.insert(moneyLog, conn);
        }

        if (count != 1) {
            throw new Exception("数据库异常");
        }

        return count;
    }
}
