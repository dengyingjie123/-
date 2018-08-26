package com.youngbook.action.oa.finance;

import com.youngbook.action.BaseAction;
import com.youngbook.common.*;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.DepartmentPO;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.entity.vo.oa.finance.MoneyLogSummaryVO;
import net.sf.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MoneyLogReportSummaryAction extends BaseAction {

    DepartmentPO department = new DepartmentPO();
    private MoneyLogSummaryVO moneyLogSummaryVO = new MoneyLogSummaryVO();

    public String listCompany() throws Exception {
        // 构建整个部门树
        List<DepartmentPO> list = MySQLDao.query(new DepartmentPO(), DepartmentPO.class, null,null);
        Tree departmentRoot = TreeOperator.createForest();
        for (int i = 0; i < list.size(); i++) {
            DepartmentPO d = list.get(i);
            Tree leaf = new Tree(d.getId(), d.getName(), d.getParentId(), d);
            TreeOperator.add(departmentRoot, leaf);
        }
        JSONArray array = new JSONArray();
        List<MoneyLogSummaryVO> listSummary = new ArrayList<MoneyLogSummaryVO>();
        String id = getRequest().getParameter("id");
        String start = getRequest().getParameter("moneyTime_Start");
        String end = getRequest().getParameter("moneyTime_End");
        MoneyLogSummaryVO summary = new MoneyLogSummaryVO();
        if (id != null && !id.equals("")) {
            department.setId(id);
            Tree departmentTree = TreeOperator.find(departmentRoot, department.getId());
            for (int i = 0; departmentTree != null && departmentTree.getChildren() != null && i < departmentTree.getChildren().size(); i++) {
                Tree leaf = departmentTree.getChildren().get(i);
                summary = this.getMoneyLogSummary(leaf, start, end);
                if (leaf.getChildren().size() > 0) {
                    summary.set_state("closed");
                }
                summary.setId(leaf.getId());
                summary.setName(leaf.getName());
                summary.setParentId(leaf.getParentId());
                double total = summary.getMoneyIn() - summary.getMoneyOut();
                summary.setTotal(total);
                array.add(summary.toJsonObject4Treegrid());
            }
        } else {
            // 获得传入部门编号的部门树
            if (department == null || department.getId().equals("")) {
                department = new DepartmentPO();
                department.setId("1");
            }
            Tree leaf = TreeOperator.find(departmentRoot, department.getId());
            summary = this.getMoneyLogSummary(leaf, start, end);
            if (leaf.getChildren().size() > 0) {
                summary.set_state("closed");
            }
            summary.setId(leaf.getId());
            summary.setName(leaf.getName());
            summary.setParentId(leaf.getParentId());
            double total = summary.getMoneyIn() - summary.getMoneyOut();
            summary.setTotal(total);
            array.add(summary.toJsonObject4Treegrid());
        }
        getResult().setReturnValue(array);
        return SUCCESS;
    }

    private MoneyLogSummaryVO getMoneyLogSummary(Tree department, String start, String end) throws Exception{
        List<Tree> children = TreeOperator.getChildren(department);
        StringBuffer sbDepartmentId = new StringBuffer();
        for (Tree leaf : children) {
            sbDepartmentId.append("'").append(leaf.getId()).append("',");
        }
        sbDepartmentId = StringUtils.removeLastLetters(sbDepartmentId, ",");
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" select sum(money_out) moneyOut, sum(money_in) moneyIn from");
        sbSQL.append(" (");
        sbSQL.append(" select sum(ml.Money) money_out, 0 money_in");
        sbSQL.append(" from system_department dp left join finance_moneylog ml on ml.DepartmentId=dp.ID and ml.state=0 and ml.inOrOut=1");
        if (!StringUtils.isEmpty(start)) {
            sbSQL.append("     and ml.MoneyTime>='"+Database.encodeSQL(start)+"'");
        }
        if (!StringUtils.isEmpty(end)) {
            sbSQL.append("     and ml.MoneyTime<='"+Database.encodeSQL(end)+"'");
        }
        if (sbDepartmentId.length() > 0) {
            sbSQL.append("     and dp.ID in ("+Database.encodeSQL(sbDepartmentId.toString())+")");
        }
        sbSQL.append(" UNION ALL");
        sbSQL.append(" select 0 money_out, sum(ml.Money) money_in");
        sbSQL.append(" from system_department dp left join finance_moneylog ml on ml.DepartmentId=dp.ID and ml.state=0 and ml.inOrOut=0");
        if (!StringUtils.isEmpty(start)) {
            sbSQL.append("     and ml.MoneyTime>='"+Database.encodeSQL(start)+"'");
        }
        if (!StringUtils.isEmpty(end)) {
            sbSQL.append("     and ml.MoneyTime<='"+Database.encodeSQL(end)+"'");
        }
        if (sbDepartmentId.length() > 0) {
            sbSQL.append("     and dp.ID in ("+Database.encodeSQL(sbDepartmentId.toString())+")");
        }
        sbSQL.append(" ) t");
        List<MoneyLogSummaryVO> list = MySQLDao.query(sbSQL.toString(), MoneyLogSummaryVO.class,null);
        MoneyLogSummaryVO summary = new MoneyLogSummaryVO();
        if (list != null && list.size() == 1) {
            summary = list.get(0);
        }
        return summary;
    }

    public MoneyLogSummaryVO getMoneyLogSummaryVO() {
        return moneyLogSummaryVO;
    }
    public void setMoneyLogSummaryVO(MoneyLogSummaryVO moneyLogSummaryVO) {this.moneyLogSummaryVO = moneyLogSummaryVO;}

    public DepartmentPO getDepartment() {
        return department;
    }
    public void setDepartment(DepartmentPO department) {
        this.department = department;
    }

}
