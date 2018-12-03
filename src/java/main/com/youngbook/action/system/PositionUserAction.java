package com.youngbook.action.system;

import com.youngbook.action.BaseAction;
import com.youngbook.common.*;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.PositionUserPO;
import com.youngbook.entity.vo.system.PositionUserVO;
import com.youngbook.service.system.PositionUserService;
import com.youngbook.service.system.UserService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class PositionUserAction extends BaseAction {
    private ReturnObject result;
    private PositionUserPO positionUser = new PositionUserPO();
    private com.youngbook.entity.po.system.PositionUserPO positionUserPO = new com.youngbook.entity.po.system.PositionUserPO();
    private PositionUserVO positionUserVO = new PositionUserVO();
    private PositionUserService service = new PositionUserService();
    private List<Map<String,Object>> list  = new ArrayList<Map<String, Object>>();

    @Autowired
    UserService userService;

    @Autowired
    PositionUserService positionUserService;


    /**
     * @description
     * 添加和修改，注意PO是po.system里面的PO
     * @author 胡超怡
     *
     * @date 2018/12/3 10:39
     * @return java.lang.String
     * @throws Exception
     */
    public String insertOrUpdate() throws Exception {

        com.youngbook.entity.po.system.PositionUserPO positionUserPO1 = HttpUtils.getInstanceFromRequest(getRequest(), "positionUser",
                com.youngbook.entity.po.system.PositionUserPO.class);

        positionUserService.insertOrUpdate(positionUserPO1,getConnection());

        return SUCCESS;
    }
    public String load() {
        result = new ReturnObject();
        try {
            positionUser = MySQLDao.load(positionUser, PositionUserPO.class);
            result.setCode(ReturnObject.CODE_SUCCESS);
            result.setMessage("操作成功");
            result.setReturnValue(positionUser.toJsonObject4Form());
        } catch (Exception e) {
            result.setCode(ReturnObject.CODE_EXCEPTION);
            result.setMessage("操作失败");
            result.setException(e);
        }
        return SUCCESS;
    }

    public String list() {
        result = new ReturnObject();
        try {
            QueryType queryType = new QueryType(Database.QUERY_EXACTLY, Database.NUMBER_EQUAL);

            List<PositionUserPO> positionUserList = MySQLDao.query(positionUser, PositionUserPO.class, null, queryType);
            JSONArray arrayJson = new JSONArray();
            for (int i = 0; i < positionUserList.size(); i++) {
                PositionUserPO temp = positionUserList.get(i);
                arrayJson.add(temp.toJsonObject());
            }
            String json = "";
            if (arrayJson != null && arrayJson.size() > 0) {
                json = arrayJson.toString();
            }
            result.setCode(ReturnObject.CODE_SUCCESS);
            result.setMessage("操作成功");
            result.setReturnValue(json);

        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(ReturnObject.CODE_DB_EXCEPTION);
            result.setMessage("操作失败");
            result.setException(e);
        }
        return SUCCESS;
    }


    /**
     * @description
     * 获取列表（state=0）
     * @author 胡超怡
     *
     * @date 2018/12/3 10:40
     * @return java.lang.String
     * @throws Exception
     */
    public String listUsers() throws Exception {

        /**
         * 获取列表并响应json
         */
        String positionId = getHttpRequestParameter("positionUser.positionId");

        Pager pager = Pager.getInstance(getRequest());

        pager = userService.listPagerUserPOs(positionId, pager.getCurrentPage(), pager.getShowRowCount(), getConnection());
        getResult().setReturnValue(pager.toJsonObject());

        return SUCCESS;
    }


    /**
     * @description
     * 删除user
     * @author 胡超怡
     *
     * @date 2018/12/3 10:41
     * @return java.lang.String
     * @throws Exception
     */
    public String delete() throws Exception{

        com.youngbook.entity.po.system.PositionUserPO positionUser1 = HttpUtils.getInstanceFromRequest(getRequest(), "positionUser",
                com.youngbook.entity.po.system.PositionUserPO.class);

        int count = positionUserService.remove(positionUser1,getLoginUser().getId(),getConnection());

        if (count != 1) {
            throw new Exception("操作失败");
        }
        return SUCCESS;

    }

    public String deleteByIds() throws Exception {
        com.youngbook.entity.po.system.PositionUserPO positionUser2 = HttpUtils.getInstanceFromRequest(getRequest(), "positionUser",
                com.youngbook.entity.po.system.PositionUserPO.class);
        List<com.youngbook.entity.po.system.PositionUserPO> positionUserList= positionUserService.searchByPositionAndUser(positionUser2,com.youngbook.entity.po.system.PositionUserPO.class,getConnection());

        if (null != positionUserList && positionUserList.size() > 0) {
            for (com.youngbook.entity.po.system.PositionUserPO positionU : positionUserList) {
                positionUserService.remove(positionU,getLoginUser().getId(),getConnection());
            }
        }

        return SUCCESS;
    }


    /**
     * 获取数据列表
     *
     * 修改：leevits
     * 内容：增加过滤条件，使得排除已经在department里删除的部门
     * 时间：2015年6月17日 11:35:59
     * @return
     * @throws Exception
     */
    public String showList() throws Exception {

        // 构造SQL语句
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("SELECT distinct pu.id,pu.positionId,pu.states,(CASE pu.states WHEN 0 THEN '否' ELSE '是' END) AS statesName,pu.userId,u.`name` AS userName,pt.DepartmentId as departmentId,pt.DepartmentName as departmentName,pt.`Name` as positionName ");
        sbSQL.append(" FROM system_positionuser pu,system_user u,system_position pt ");
        sbSQL.append(" WHERE 1 = 1 AND u.id = pu.userId AND pu.positionId = pt.Id");
        sbSQL.append(" and pt.DepartmentId in (select id from system_department )");

        HttpServletRequest request = getRequest();

        List<KVObject> conditions = new ArrayList<KVObject>();
        conditions = MySQLDao.getQueryDatetimeParameters(request, positionUserVO.getClass(), conditions);
        //整型范围查询
        conditions = MySQLDao.getQueryNumberParameters(request, positionUserVO.getClass(), conditions);

        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        //获取分页对象
        Pager pager = Pager.query(sbSQL.toString(), positionUserVO, conditions, request, queryType);
        //返回数据
        getResult().setReturnValue(pager.toJsonObject());

        return SUCCESS;
    }

    /**
     * 获取单条数据
     *
     * @return
     * @throws Exception
     */
    public String loadPositionUser() throws Exception {
        positionUserPO = service.loadPositionUserPO(positionUserPO.getId());
        getResult().setReturnValue(positionUserPO.toJsonObject4Form());

        return SUCCESS;
    }


    public String updatePositionUser() throws Exception {
        if (positionUserPO.getStates() == 0){
            getResult().setCode(32537);
            return SUCCESS;
        }else {
            String show = "SELECT * FROM system_positionuser WHERE userId = '" +Database.encodeSQL( positionUserPO.getUserId()) + "'";
            List ls = MySQLDao.query(show, positionUserPO.getClass(), null);
            if (ls.size() > 0) {
                String update = "UPDATE system_positionuser SET states = 0 WHERE userId = '" +Database.encodeSQL( positionUserPO.getUserId()) + "'";
                MySQLDao.update(update);
                update = "UPDATE system_positionuser SET states = 1 WHERE userId = '"+Database.encodeSQL(positionUserPO.getUserId())+"' AND id = '"+Database.encodeSQL(positionUserPO.getId())+"'";
                MySQLDao.update(update);
            }
            return SUCCESS;
        }
    }



    public ReturnObject getResult() {
        return result;
    }

    public void setResult(ReturnObject result) {
        this.result = result;
    }

    public PositionUserPO getPositionUser() {
        return positionUser;
    }

    public void setPositionUser(PositionUserPO positionUser) {
        this.positionUser = positionUser;
    }

    public PositionUserVO getPositionUserVO() {
        return positionUserVO;
    }

    public void setPositionUserVO(PositionUserVO positionUserVO) {
        this.positionUserVO = positionUserVO;
    }

    public com.youngbook.entity.po.system.PositionUserPO getPositionUserPO() {
        return positionUserPO;
    }

    public void setPositionUserPO(com.youngbook.entity.po.system.PositionUserPO positionUserPO) {
        this.positionUserPO = positionUserPO;
    }
}
