package com.youngbook.action.system;

import com.opensymphony.xwork2.ActionSupport;
import com.youngbook.common.*;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.PermissionPO;
import com.youngbook.common.utils.IdUtils;
import net.sf.json.JSONArray;

import java.util.List;


public class PermissionAction extends ActionSupport {
    private ReturnObject result;
    private PermissionPO permission;

    public String insertOrUpdate() throws Exception {
        result = new ReturnObject();
        int count = 0;
        try{
            if (permission.getId().equals("")) {
                permission.setId(IdUtils.getUUID32());
                count = MySQLDao.insert(permission);
            }
            else {
                count = MySQLDao.update(permission);
            }

            if(count == 1){
                result.setMessage("操作成功");
                result.setCode(ReturnObject.CODE_SUCCESS);
            }else{
                result.setMessage("操作失败");
                result.setCode(ReturnObject.CODE_EXCEPTION);
            }
        }catch (Exception e){
            result.setCode(ReturnObject.CODE_EXCEPTION);
            result.setMessage("操作失败");
            result.setException(e);
        }
        return SUCCESS;
    }

    public String load(){
        result = new ReturnObject();
        try {
            permission = MySQLDao.load(permission,PermissionPO.class);
            result.setCode(ReturnObject.CODE_SUCCESS);
            result.setMessage("操作成功");
            result.setReturnValue(permission.toJsonObject4Form());
        }catch (Exception e){
            result.setCode(ReturnObject.CODE_EXCEPTION);
            result.setMessage("操作失败");
            result.setException(e);
        }
        return SUCCESS;
    }

    public String list(){
        result = new ReturnObject();
        try{
            QueryType queryType = new QueryType(Database.QUERY_EXACTLY, Database.NUMBER_EQUAL);
                
            List<PermissionPO> permissionList = MySQLDao.query(permission,PermissionPO.class,null,queryType);
            if(permissionList.size() != 0){
                Tree menuRoot = TreeOperator.createForest();

                for(PermissionPO po : permissionList){

                    Tree tree = new Tree(po.getId(),po.getMenuId(), po.getPermissionName(),po);

                    TreeOperator.add(menuRoot, tree);
                }

                JSONArray array = TreeOperator.getJson4Tree(menuRoot).getJSONArray("children");
                String json = "";
                if (array != null) {
                    json = array.toString();
                }

                result.setCode(ReturnObject.CODE_SUCCESS);
                result.setMessage("操作成功");
                result.setReturnValue(json);
            }
            else {
                result.setMessage("操作成功");
                result.setCode(ReturnObject.CODE_SUCCESS);
                result.setReturnValue("");
            }

        }catch (Exception e){
            e.printStackTrace();
            result.setCode(ReturnObject.CODE_DB_EXCEPTION);
            result.setMessage("操作失败");
            result.setException(e);
        }
        return SUCCESS;
    }

    public String delete(){
        result = new ReturnObject();
        try{
            int count = MySQLDao.delete(permission);
            if(count != 1){ //删除失败
                result.setMessage("删除失成功");
                result.setCode(ReturnObject.CODE_EXCEPTION);
            }else{//删除成功
                result.setMessage("操作失败");
                result.setCode(ReturnObject.CODE_SUCCESS);
            }
        }catch (Exception e){
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

    public PermissionPO getPermission() {
        return permission;
    }

    public void setPermission(PermissionPO permission) {
        this.permission = permission;
    }
}
