package com.youngbook.action.oa.finance;

import com.youngbook.action.BaseAction;
import com.youngbook.common.Database;
import com.youngbook.common.Tree;
import com.youngbook.common.TreeOperator;
import com.youngbook.dao.JSONDao;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.DepartmentPO;
import com.youngbook.entity.po.system.JsonColumnPO;
import com.youngbook.common.utils.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class MoneyLogReportDetailInOrOutAction extends BaseAction {

    DepartmentPO department = new DepartmentPO();

    public String listColumn() throws Exception {
        String groupName = this.getGroupName();
        String sql = "select k field, v title, 100 width from system_kv where GroupName='"+Database.encodeSQL(groupName)+"'";
        List<JsonColumnPO> list = MySQLDao.query(sql, JsonColumnPO.class, null);
        JsonColumnPO department = new JsonColumnPO();
        department.setTitle("部门");
        department.setField("departmentName");
        department.setWidth(250);
        JsonColumnPO total = new JsonColumnPO();
        total.setTitle("合计");
        total.setField("total");
        total.setWidth(130);
        list.add(0, department);
        list.add(total);
        JSONArray array = new JSONArray();
        array.add(JSONDao.getArray(list));
        getResult().setReturnValue(array);
        return SUCCESS;
    }

    private String getGroupName() {
        String inOrOut = getRequest().getParameter("InOrOut");
        if (StringUtils.isEmpty(inOrOut)) {
            inOrOut = "0";
        }
        String groupName = "OA_Finance_MoneyLog_Type";
        if (inOrOut.equalsIgnoreCase("0")) {
            groupName = "OA_Finance_MoneyLog_Type_In";
        }
        return groupName;
    }

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
        String id = getRequest().getParameter("id");
        String start = getRequest().getParameter("moneyTime_Start");
        String end = getRequest().getParameter("moneyTime_End");
        if (id != null && !id.equals("")) {
            department.setId(id);
            Tree departmentTree = TreeOperator.find(departmentRoot, department.getId());
            for (int i = 0; departmentTree != null && departmentTree.getChildren() != null && i < departmentTree.getChildren().size(); i++) {
                Tree leaf = departmentTree.getChildren().get(i);
                List<Map<String, Object>> details = this.getMoneyLogDetail(leaf, start, end);

                JSONObject json = buildJSON(details, leaf);
                array.add(json);

            }
        } else {
            // 获得传入部门编号的部门树
            if (department == null || department.getId().equals("")) {
                department = new DepartmentPO();
                department.setId("1");
            }
            Tree leaf = TreeOperator.find(departmentRoot, department.getId());
            List<Map<String, Object>> details = this.getMoneyLogDetail(leaf, start, end);
            JSONObject json = buildJSON(details, leaf);
            array.add(json);
        }

        getResult().setReturnValue(array);
        return SUCCESS;
    }

    private JSONObject buildJSON(List<Map<String, Object>> details, Tree leaf) {
        JSONObject json = new JSONObject();
        double total = 0;
        for (int j = 0; j < details.size(); j++) {
            String type = "";
            double money = 0;
            Map<String, Object> map = details.get(j);
            Set<String> keys = map.keySet();
            for (String key : keys) {
                if (key.equalsIgnoreCase("k")) {
                    type = (String) map.get(key);
                }
                else if (key.equalsIgnoreCase("money")) {
                    money = Double.parseDouble(map.get(key).toString());
                }
            }
            total += money;
            json.element(type, money);
        }
        if (leaf.getChildren().size() > 0) {
            json.element("state", "closed");
        }
        else {
            json.element("state", "open");
        }
        json.element("id", leaf.getId());
        json.element("departmentName", leaf.getName());
        json.element("total", total);
        return json;
    }

    private List<Map<String, Object>> getMoneyLogDetail(Tree department, String start, String end) throws Exception{
        List<Tree> children = TreeOperator.getChildren(department);
        StringBuffer sbDepartmentId = new StringBuffer();
        for (Tree leaf : children) {
            sbDepartmentId.append("'").append(leaf.getId()).append("',");
        }
        sbDepartmentId = StringUtils.removeLastLetters(sbDepartmentId, ",");
        String groupName = this.getGroupName();
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT k, if (sum(ml.Money) is null, 0,sum(ml.Money))  money");
        sbSQL.append(" FROM");
        sbSQL.append("     system_kv kv");
        sbSQL.append(" LEFT JOIN finance_moneylog ml");
        sbSQL.append(" ON ml.type = kv.K");
        if (sbDepartmentId.length() > 0) {
            sbSQL.append("     and ml.DepartmentId in ("+Database.encodeSQL(sbDepartmentId.toString())+")");
        }
        if (!StringUtils.isEmpty(start)) {
            sbSQL.append("     and ml.MoneyTime>='"+ Database.encodeSQL(start)+"'");
        }
        if (!StringUtils.isEmpty(end)) {
            sbSQL.append("     and ml.MoneyTime<='"+Database.encodeSQL(end)+"'");
        }
        sbSQL.append(" left join system_department dp on dp.id=ml.DepartmentId");
        sbSQL.append(" where kv.GroupName = '"+Database.encodeSQL(groupName)    +"'");
        sbSQL.append(" GROUP BY k");
        List<Map<String, Object>> list = MySQLDao.query(sbSQL.toString());
        return list;
    }

    public DepartmentPO getDepartment() {
        return department;
    }
    public void setDepartment(DepartmentPO department) {
        this.department = department;
    }
}
