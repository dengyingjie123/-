package com.youngbook.action.system;
import com.youngbook.action.BaseAction;
import com.youngbook.annotation.Permission;
import com.youngbook.common.*;

import com.youngbook.common.config.Config;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MenuDao;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.MenuPO;
import com.youngbook.entity.po.PermissionPO;
import com.youngbook.entity.po.UserPO;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.service.system.LogService;
import net.sf.json.JSONObject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuAction extends BaseAction {
    private ReturnObject result;
    private MenuPO menu = new MenuPO();


    public String load() {
//        String id = getRequest().getParameter("menu.id");
//        HttpServletRequest request = getRequest();
//        String name = request.getParameter("menu.name");
//        System.out.println(id + " " + name);
        result = new ReturnObject();
        try {
            //menu.setId(id);
            KVObject kvObject = new KVObject("state","0");
            List condition = new ArrayList();
            condition.add(kvObject);
            menu = MySQLDao.load(menu,condition, MenuPO.class);

            if (menu != null) {
                result.setMessage("操作成功");
                result.setCode(ReturnObject.CODE_SUCCESS);
                result.setReturnValue(menu.toJsonObject4Form());
            }
            else {
                result.setCode(ReturnObject.CODE_EXCEPTION);
                result.setMessage("查询菜单失败");
            }


        } catch (Exception e) {
            result.setCode(ReturnObject.CODE_EXCEPTION);
            result.setMessage("操作失败");
            result.setException(e);
        }
        return SUCCESS;
    }


    public String delete() {

        MenuDao dao = new MenuDao();

        result = new ReturnObject();

        Connection conn = null;

        try {
            conn = Database.getConnection();
            conn.setAutoCommit(false);
            dao.delete(menu, conn);
            //逻辑删除菜单，修改state=0，添加operateId.
//            MySQLDao.remove(menu,getLoginUser().getId(),conn);

            PermissionPO permissionPO = new PermissionPO();
            permissionPO.setMenuId(menu.getId());

            List<PermissionPO> listPermissionPOs = MySQLDao.query(permissionPO, PermissionPO.class, null, null);
            for (int i = 0; i < listPermissionPOs.size(); i++) {
                PermissionPO temp = listPermissionPOs.get(i);
                MySQLDao.deletePhysically(temp, conn);
            }

            conn.commit();

            result.setCode(ReturnObject.CODE_SUCCESS);
            result.setMessage("删除成功");
        } catch (Exception e) {
            result.setCode(ReturnObject.CODE_EXCEPTION);
            result.setMessage("操作失败");
            result.setException(e);
            if (conn != null) {
                try {
                    LogService.debug("回滚数据库", this.getClass());
                    conn.rollback();
                } catch (SQLException e1) {
                    result.setCode(ReturnObject.CODE_DB_EXCEPTION);
                    result.setMessage("数据库回滚异常");
                    result.setException(e1);
                }
            }
        }
        finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    result.setCode(ReturnObject.CODE_DB_EXCEPTION);
                    result.setMessage("数据库关闭异常");
                    result.setException(e);
                }
            }
        }

        return SUCCESS;
    }

    public String save() {

        result = new ReturnObject();
        try {
            if (menu.getId() == null || menu.getId().equalsIgnoreCase("")) {
                menu.setId(IdUtils.getUUID36());
                menu.setSid((int)IdUtils.newLongId());
                menu.setState(0);
                menu.setOperatorId(getLoginUser().getId());
                menu.setOperateTime(TimeUtils.getNow());
                MySQLDao.insert(menu);
            }
            else {
                MySQLDao.update(menu);
            }
            result.setMessage("操作成功");
            result.setCode(ReturnObject.CODE_SUCCESS);
            result.setReturnValue("");
        } catch (Exception e) {
            result.setCode(ReturnObject.CODE_EXCEPTION);
            result.setMessage("操作失败");
            result.setException(e);
        }
        return SUCCESS;
    }

    @Permission(require = "")
    public String listMenu() throws Exception {
        result = new ReturnObject();

        MenuDao dao = new MenuDao();

        MenuPO menu = new MenuPO();
        menu.setType(1);
        QueryType queryType = new QueryType(Database.QUERY_EXACTLY, Database.NUMBER_EQUAL);
        //List<MenuPO> menus = MySQLDao.query(menu, MenuPO.class, queryType, null);
        UserPO user = (UserPO)getRequest().getSession().getAttribute("loginPO");
        List<MenuPO> menus = Config.getUserMenus(user.getId());

        Tree menuRoot = TreeOperator.createForest();
        for(MenuPO tempMenu : menus) {
            Tree tree = new Tree(tempMenu.getId(),tempMenu.getName(), tempMenu.getParentId(), tempMenu);
            TreeOperator.add(menuRoot, tree);
        }

        //TreeOperator.printForest(menuRoot, 0);

        JSONObject json = TreeOperator.getJson4Tree(menuRoot);

        String strJson = json.getJSONArray("children").toString();


        result.setMessage("操作成功");
        result.setCode(ReturnObject.CODE_SUCCESS);
        result.setReturnValue(strJson);

        return SUCCESS;
    }

    public String list() throws Exception {

        MenuPO menu = new MenuPO();
        //menu.setType(1);
        QueryType queryType = new QueryType(Database.QUERY_EXACTLY, Database.NUMBER_EQUAL);
        List<KVObject> conditions =new ArrayList<KVObject>();
        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY,"orders "+Database.ORDERBY_ASC));

        List<MenuPO> menus = MySQLDao.query(menu, MenuPO.class,conditions, queryType);
        //List<MenuPO> menus = Config.getUserMenus("961778ddb8e1484ea44186c663f52166");

        Tree menuRoot = TreeOperator.createForest();
        for(MenuPO tempMenu : menus) {
            Tree tree = new Tree(tempMenu.getId(),tempMenu.getName(), tempMenu.getParentId(), tempMenu);
            TreeOperator.add(menuRoot, tree);
        }

        //TreeOperator.printForest(menuRoot, 0);

        JSONObject json = TreeOperator.getJson4Tree(menuRoot);

        String strJson = json.getJSONArray("children").toString();

        result = new ReturnObject();
        result.setMessage("操作成功");
        result.setCode(ReturnObject.CODE_SUCCESS);
        result.setReturnValue(strJson);

        return SUCCESS;
    }

    public ReturnObject getResult() {
        return result;
    }

    public void setResult(ReturnObject result) {
        this.result = result;
    }

    public MenuPO getMenu() {
        return menu;
    }

    public void setMenu(MenuPO menu) {
        this.menu = menu;
    }
}
