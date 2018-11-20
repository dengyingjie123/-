package com.youngbook.action.system;

import com.youngbook.action.BaseAction;
import com.youngbook.annotation.Permission;
import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.dao.MenuDao;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.MenuPO;
import com.youngbook.entity.po.PermissionPO;
import com.youngbook.entity.po.UserPO;
import com.youngbook.service.system.LogService;
import com.youngbook.service.system.MenuService;
import com.youngbook.service.system.PermissionService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MenuAction extends BaseAction {
    private ReturnObject result;
    private MenuPO menu = new MenuPO();

    @Autowired
    private MenuService menuService;

    @Autowired
    private PermissionService permissionService;


    public String load() throws Exception {

        menu = menuService.loadMenuPO(menu, getConnection());

        getResult().setReturnValue(menu.toJsonObject4Form());

        return SUCCESS;
    }


    public String delete() throws Exception {


        // 逻辑删除菜单，修改state=2，添加operateId.
        MenuPO menu = HttpUtils.getInstanceFromRequest(getRequest(), "menu", MenuPO.class);
        menuService.deleteMenu(menu, getLoginUser().getId(), getConnection());


//        // 删除相关权限
//        if(menu.getId() != null){
//            List<PermissionPO> listPermissionPOs = permissionService.listPermissionPOById(menu.getId(),getConnection());
//
//            for (int i = 0; listPermissionPOs != null && i < listPermissionPOs.size(); i++) {
//                PermissionPO temp = listPermissionPOs.get(i);
//                MySQLDao.remove(temp, getLoginUser().getId(), getConnection());
//            }
//        }

        return SUCCESS;
    }


    /**
     *
     * @return
     * @throws Exception
     */
    public String save() throws Exception {
        /**
         * 获取需要修改的菜单数据
         */
        MenuPO menu = HttpUtils.getInstanceFromRequest(getRequest(), "menu", MenuPO.class);




        /**
         *
         * 保存菜单数据
         */
        menuService.saveMenu(menu, getLoginUser().getOperatorId(), getConnection());




        /**
         *
         */
        getResult().setReturnValue("1");

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
//        QueryType queryType = new QueryType(Database.QUERY_EXACTLY, Database.NUMBER_EQUAL);
//        List<KVObject> conditions =new ArrayList<KVObject>();
//        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY,"orders "+Database.ORDERBY_ASC));
//
//        List<MenuPO> menus = MySQLDao.query(menu, MenuPO.class,conditions, queryType);
//        List<MenuPO> menus = Config.getUserMenus("961778ddb8e1484ea44186c663f52166");
        List<MenuPO> menus = menuService.listMenu(MenuPO.class,getConnection());

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
