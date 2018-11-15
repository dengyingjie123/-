package com.youngbook.service.system;

import com.youngbook.common.*;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.system.IDepartmentDao;
import com.youngbook.dao.system.IPositionDao;
import com.youngbook.dao.system.IPositionPermissionDao;
import com.youngbook.dao.system.IPositionUserDao;
import com.youngbook.entity.po.DepartmentPO;
import com.youngbook.entity.po.PositionPO;
import com.youngbook.entity.po.PositionPermissionPO;
import com.youngbook.entity.po.system.PositionUserPO;
import com.youngbook.entity.po.system.UserPositionInfoPO;
import com.youngbook.service.BaseService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 2016/1/24.
 */

@Component("departmentService")
public class DepartmentService extends BaseService {



    @Autowired
    IDepartmentDao departmentDao;
    @Autowired
    IPositionDao positionDao;
    @Autowired
    IPositionUserDao positionUserDao;
    @Autowired
    IPositionPermissionDao positionPermissionDao;

    public String getDepartments4FortuneCenter(DepartmentPO department, Connection conn) throws Exception {


        QueryType queryType = new QueryType(Database.NUMBER_EQUAL, Database.QUERY_EXACTLY);
        List<KVObject> conditions = new ArrayList<KVObject>();
        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, " orders "));
        List<DepartmentPO> departmentList = MySQLDao.query(department, DepartmentPO.class,conditions, queryType, conn);

        if (departmentList == null && departmentList.size() == 0) {
            MyException.newInstance("部门加载失败").throwException();
        }

        Tree root = TreeOperator.createForest();
        for(DepartmentPO departmentpo : departmentList){
            Tree tree = new Tree(departmentpo.getId(),departmentpo.getName(),departmentpo.getParentId(),departmentpo);
            TreeOperator.add(root,tree);
        }
        JSONObject json = TreeOperator.getJson4Tree(root);
        String jsonStr = "";

        if (json.get("children") != null) {
            jsonStr = json.getJSONArray("children").toString();
        }

        return jsonStr;
    }

    public List<UserPositionInfoPO> getUserDepartmentInfo(String userId, Connection conn) throws Exception {
        return departmentDao.getUserDepartmentInfo(userId, conn);
    }


    public UserPositionInfoPO getDefaultUserPositionInfo(String userId, Connection conn) throws Exception {
        return departmentDao.getDefaultUserPositionInfo(userId, conn);
    }


    public DepartmentPO load(String departmentId, Connection conn) throws Exception{

        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("SELECT * from system_department where id=?");
        dbSQL.addParameter(1, departmentId);

        List<DepartmentPO> list = MySQLDao.search(dbSQL.getSQL(), dbSQL.getParameters(), DepartmentPO.class, null, conn);

        if (list != null && list.size() == 1) {
            return list.get(0);
        }

        return null;
    }



    public UserPositionInfoPO getUserPositionInfo(String userId, String positionId, Connection conn) throws Exception {

        if (StringUtils.isEmpty(userId)) {
            MyException.newInstance("无法获得用户编号").throwException();
        }

        if (StringUtils.isEmpty(positionId)) {
            MyException.newInstance("无法获得用户岗位编号").throwException();
        }

        List<UserPositionInfoPO> list = departmentDao.getUserDepartmentInfo(userId, conn);
        for (int i = 0; list != null && i < list.size(); i++) {
            UserPositionInfoPO userPositionInfoPO = list.get(i);
            if (userPositionInfoPO.getPositionId().equals(positionId)) {
                return userPositionInfoPO;
            }
        }

        return null;
    }


    /**
     * 级联删除
     * @param department
     * @param id
     * @param conn
     * @return
     * @throws Exception
     */
    public int remove(DepartmentPO department, String id, Connection conn) throws Exception {

        //通过department查询PositionPO，在遍历后更改状态
        List<PositionPO> positionList = positionDao.searchByDepartment(department,conn);

        if (null != positionList && positionList.size() > 0){
            for (PositionPO position: positionList) {
                //更改状态
                positionDao.remove(position,id,conn);
                //通过position查询PositionUserPO，在遍历后更改状态
                List<PositionUserPO> positionUserList = positionUserDao.searchByPosition(position,conn);
                if (null != positionUserList && positionUserList.size() > 0){
                    for (PositionUserPO positionUser:positionUserList) {
                        positionUserDao.remove(positionUser,id,conn);
                    }
                }
                //通过position查询positionPermissionPO，在遍历后更改状态
                List<PositionPermissionPO> positionPermissionList = positionPermissionDao.searchByPosition(position,conn);
                if (null != positionPermissionList && positionPermissionList.size() > 0){
                    for (PositionPermissionPO positionPermission:positionPermissionList) {
                        positionPermissionDao.remove(positionPermission,id,conn);
                    }
                }
            }
        }

        return departmentDao.remove(department,id,conn);


    }

    public List<DepartmentPO> search(DepartmentPO department, Class<DepartmentPO> departmentPOClass, List<KVObject> conditions, QueryType queryType, Connection conn) throws  Exception
    {

        return departmentDao.search(department, DepartmentPO.class,conditions, queryType, conn);

    }

    public void insertOrUpdate(DepartmentPO department, Connection conn) throws Exception {
        departmentDao.insertOrUpdate(department, conn);
    }
}
