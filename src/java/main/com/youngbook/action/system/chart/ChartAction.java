package com.youngbook.action.system.chart;

import com.youngbook.action.BaseAction;
import com.youngbook.common.Database;
import com.youngbook.dao.JSONDao;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.system.chart.PiePO;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.StringUtils;

import java.util.List;


public class ChartAction extends BaseAction {

    private PiePO pie = new PiePO();

    public String getMoneyLogSpendRatePie() throws Exception {

//        String [] departmentIds = getRequest().getParameterValues("DepartmentIds");
//        StringBuffer sbDepartmentIds = new StringBuffer();
//        for (int i = 0; departmentIds != null && i < departmentIds.length; i++) {
//            sbDepartmentIds.append("'").append(departmentIds[i]).append("',");
//        }
//        sbDepartmentIds = StringUtils.removeLastLetters(sbDepartmentIds, ",");

        List<String> listDepartmentIds = HttpUtils.getParameters(getRequest(), "DepartmentIds");
        String departmentIds = StringUtils.getSQLInQueryString(listDepartmentIds, "'");

        List<String> listTypeIds = HttpUtils.getParameters(getRequest(), "TypeIds");
        String typeIds = StringUtils.getSQLInQueryString(listTypeIds, "'");

        String operatorTimeStart = getRequest().getParameter("MoneyTime_Start");
        String operatorTimeEnd = getRequest().getParameter("MoneyTime_End");

        //String sql = "select kv.v name, sum(money) y from finance_moneylog ml, system_kv kv where ml.inOrOut=1 and ml.type=kv.K and kv.GroupName='OA_Finance_MoneyLog_Type' and ml.state=0 group by kv.v";

        StringBuffer sbSQL = new StringBuffer();

        sbSQL.append(" SELECT kv.v name ");
        sbSQL.append("      , sum(money) y ");
        sbSQL.append(" FROM ");
        sbSQL.append("     finance_moneylog ml, system_kv kv ");
        sbSQL.append(" WHERE ");
        sbSQL.append("     ml.inOrOut = 1 ");
        sbSQL.append("     AND ml.type = kv.K ");
        sbSQL.append("     AND kv.GroupName = 'OA_Finance_MoneyLog_Type' ");
        sbSQL.append("     AND ml.state = 0 ");
        if (!StringUtils.isEmpty(departmentIds)) {
            sbSQL.append("     AND ml.DepartmentId IN ("+ Database.encodeSQL(departmentIds) + ") ");
        }
        if (!StringUtils.isEmpty(typeIds)) {
            sbSQL.append("     AND ml.type IN ("+Database.encodeSQL(typeIds)+") ");
        }

        if (!StringUtils.isEmpty(operatorTimeStart)) {
            sbSQL.append("     AND ml.MoneyTime >= '"+Database.encodeSQL(operatorTimeStart)+"' ");
        }

        if (!StringUtils.isEmpty(operatorTimeEnd)) {
            sbSQL.append("     AND ml.MoneyTime <= '"+Database.encodeSQL(operatorTimeEnd)+"' ");
        }

        sbSQL.append(" GROUP BY ");
        sbSQL.append("     kv.v ");

        List<PiePO> pies = MySQLDao.query(sbSQL.toString(), PiePO.class, null);


        getResult().setReturnValue(JSONDao.get(pies));

        return SUCCESS;
    }

    public void setPie(PiePO pie) {
        this.pie = pie;
    }
}
