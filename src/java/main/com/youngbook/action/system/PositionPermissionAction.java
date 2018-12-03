package com.youngbook.action.system;

import com.youngbook.action.BaseAction;
import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.JSONDao;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.MenuPO;
import com.youngbook.entity.po.PositionPermissionPO;
import com.youngbook.service.system.LogService;
import com.youngbook.service.system.PositionPermissionService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.util.List;


public class PositionPermissionAction extends BaseAction {
    private ReturnObject result;
    private PositionPermissionPO positionPermission;

    @Autowired
    PositionPermissionService positionPermissionService;


    /**
     * @description
     * 添加或修改
     * @author 胡超怡
     *
     * @date 2018/12/3 10:42
     * @return java.lang.String
     * @throws Exception
     */
    public String insertOrUpdate() throws Exception {

        PositionPermissionPO positionPermission = HttpUtils.getInstanceFromRequest(getRequest(), "positionPermission", PositionPermissionPO.class);

        positionPermissionService.insertOrUpdate(positionPermission, getConnection());
        return SUCCESS;
    }

    public String load(){
        result = new ReturnObject();
        try {
            positionPermission = MySQLDao.load(positionPermission,PositionPermissionPO.class);
            result.setCode(ReturnObject.CODE_SUCCESS);
            result.setMessage("操作成功");
            result.setReturnValue(positionPermission.toJsonObject4Form());
        }catch (Exception e){
            result.setCode(ReturnObject.CODE_EXCEPTION);
            result.setMessage("操作失败");
            result.setException(e);
        }
        return SUCCESS;
    }


    /**
     * @description
     * 添加或修改方法，先根据岗位的id获取以前的权限并且删除掉以前的权限
     * 再根据permissionIds添加到positionPermission表
     * @author 胡超怡
     *
     * @date 2018/12/3 10:43
     * @return java.lang.String
     * @throws Exception
     */
    public String saveAll() throws Exception{


        /**
         * 删除以前的映射关系
         */
        result = new ReturnObject();
        String permissionIds = getRequest().getParameter("permissionIds");

        String positionId = getHttpRequestParameter("positionPermission.positionId");

        String sql = "delete from system_positionpermission where positionId='"+Database.encodeSQL(positionId)+"'";

        LogService.debug("删除已有的权限映射关系", this.getClass());
        LogService.info("PositionPermissionAction.saveAll(): " + sql, this.getClass());

        Connection conn = getConnection();

        int count = 0;

        count = MySQLDao.update(sql, conn);

        LogService.debug("已删除原有映射关系："+ count, this.getClass());




        /**
         * 添加现有的映射关系
         */
        String [] permissionIdsArray = permissionIds.split(Config.SPLIT_LETTER);

        LogService.debug("现有映射关系有：" + permissionIdsArray.length, this.getClass());

        for (int i = 0; permissionIdsArray != null && i < permissionIdsArray.length; i++) {
            String permissionId = permissionIdsArray[i];
            
            PositionPermissionPO po = new PositionPermissionPO();
            po.setPermissionId(permissionId);
            po.setPositionId(positionId);
            count = MySQLDao.insertOrUpdate(po,conn);

            if (count != 1) {
                getResult().setCode(ReturnObject.CODE_EXCEPTION);
                getResult().setMessage("保存失败，自定义异常");
                throw new Exception("保存失败，自定义异常");
            }
        }

        getResult().setCode(ReturnObject.CODE_SUCCESS);
        getResult().setMessage("保存成功");

        return SUCCESS;
    }

    public String list() {
        result = new ReturnObject();
        try{
            QueryType queryType = new QueryType(Database.QUERY_EXACTLY, Database.NUMBER_EQUAL);

            List<PositionPermissionPO> positionPermissionList = MySQLDao.query(positionPermission,PositionPermissionPO.class,null,queryType);
            JSONArray arrayJson = new JSONArray();
            for (int i = 0; i < positionPermissionList.size(); i++) {
                PositionPermissionPO temp = positionPermissionList.get(i);
                arrayJson.add(temp.toJsonObject());
            }
            String json = "";
            if (arrayJson != null && arrayJson.size() > 0) {
                json = arrayJson.toString();
            }
            result.setCode(ReturnObject.CODE_SUCCESS);
            result.setMessage("操作成功");
            result.setReturnValue(json);

        }
        catch (Exception e){
            e.printStackTrace();
            result.setCode(ReturnObject.CODE_DB_EXCEPTION);
            result.setMessage("操作失败");
            result.setException(e);
        }
        return SUCCESS;
    }

    public String listPermissions() throws Exception {

        String positionId = getHttpRequestParameter("positionPermission.positionId");

        String sql = "select p.* from system_menu p, system_positionpermission pp where pp.permissionid = p.id";
        if (!StringUtils.isEmpty(positionId)) {
            sql += " and pp.positionId='"+Database.encodeSQL(positionId)+"'";
        }

        List<MenuPO> menus = MySQLDao.query(sql, MenuPO.class, null);
        getResult().setReturnValue(JSONDao.getArray(menus));

        return SUCCESS;
    }

    public String delete(){
        result = new ReturnObject();
        try{
            int count = MySQLDao.deletePhysically(positionPermission);
            if (count >= 1) {
                result.setMessage("操作成功");
                result.setCode(ReturnObject.CODE_SUCCESS);
            }
            else {
                result.setMessage("删除失失败");
                result.setCode(ReturnObject.CODE_EXCEPTION);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            result.setCode(ReturnObject.CODE_EXCEPTION);
            result.setMessage("操作失败");
            result.setException(e);
        }
        return SUCCESS;
    }

    public String deleteByIds() {
        result = new ReturnObject();
        try{
            String sql = "delete from system_positionpermission where positionId='"+positionPermission.getPositionId()
                    +"' and permissionId='"+Database.encodeSQL(positionPermission.getPermissionId())+"'";
            int count = MySQLDao.update(sql);
            if (count >= 1) {
                result.setMessage("操作成功");
                result.setCode(ReturnObject.CODE_SUCCESS);
            }
            else {
                result.setMessage("删除失失败");
                result.setCode(ReturnObject.CODE_EXCEPTION);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            result.setCode(ReturnObject.CODE_EXCEPTION);
            result.setMessage("操作失败");
            result.setException(e);
        }
        return SUCCESS;
    }

    public ReturnObject getResult() {
        return result;
    }

    public void setResult(ReturnObject result) {
        this.result = result;
    }

    public PositionPermissionPO getPositionPermission() {
        return positionPermission;
    }

    public void setPositionPermission(PositionPermissionPO positionPermission) {
        this.positionPermission = positionPermission;
    }
}
