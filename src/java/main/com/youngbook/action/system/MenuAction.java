package com.youngbook.action.system;

import com.youngbook.action.BaseAction;
import com.youngbook.annotation.Permission;
import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.dao.MenuDao;
import com.youngbook.entity.po.MenuPO;
import com.youngbook.entity.po.UserPO;
import com.youngbook.service.system.MenuService;
import com.youngbook.service.system.PermissionService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MenuAction extends BaseAction {
    private MenuPO menu = new MenuPO();
    private ReturnObject result;

    @Autowired
    private MenuService menuService;


    /**
     * @description 修改时显示菜单详情
     * @author 徐明煜
     * @date 2018/12/2 18:20
     * @param
     * @return java.lang.String
     * @throws Exception
     */
    public String load() throws Exception {

        menu = menuService.loadMenuPO(menu, MenuPO.class, getConnection());




        getResult().setReturnValue(menu.toJsonObject4Form());
        return SUCCESS;
    }


    /**
     * @description 逻辑删除菜单，修改state=2，添加operateId.
     * @author 徐明煜
     * @date 2018/12/2 18:22
     * @param
     * @return java.lang.String
     * @throws
     */
    public String delete() throws Exception {

        MenuPO menu = HttpUtils.getInstanceFromRequest(getRequest(), "menu", MenuPO.class);
        menuService.deleteMenuPO(menu, getLoginUser().getId(), getConnection());




        getResult().setReturnValue("0");




//        删除相关权限
//        if(menu.getId()!=null){
//            List<PermissionPO> listPermissionPOs = permissionService.listById(menu.getId(),getConnection());
//
//            for (int i = 0; i < listPermissionPOs.size(); i++) {
//                PermissionPO temp = listPermissionPOs.get(i);
//                MySQLDao.remove(temp, getLoginUser().getId(), getConnection());
//            }
//        }

        return SUCCESS;
    }


    /**
     * @description 保存获取到的menuPO，设置state=0，如原有该对象，设置其state=1
     * @author 徐明煜
     * @date 2018/12/2 18:23
     * @param
     * @return java.lang.String
     * @throws
     */
    public String save() throws Exception {

        MenuPO menu = HttpUtils.getInstanceFromRequest(getRequest(), "menu", MenuPO.class);
        menuService.saveMenuPO(menu, getLoginUser().getOperatorId(), getConnection());




        getResult().setReturnValue("0");
        return SUCCESS;
    }


    /**
     * 主页左侧菜单栏数据返回
     * @return
     * @throws Exception
     */
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

    /**
     * @description 菜单管理页面菜单树返回
     * @author 徐明煜
     * @date 2018/12/2 18:25
     * @param
     * @return java.lang.String
     * @throws Exception
     */
    public String list() throws Exception {

        List<MenuPO> menus = menuService.listMenuPO(MenuPO.class, getConnection());




        Tree menuRoot = TreeOperator.createForest();
        for(MenuPO tempMenu : menus) {
            Tree tree = new Tree(tempMenu.getId(), tempMenu.getName(), tempMenu.getParentId(), tempMenu);
            TreeOperator.add(menuRoot, tree);
        }




        JSONObject json = TreeOperator.getJson4Tree(menuRoot);
        String strJson = json.getJSONArray("children").toString();
        getResult().setMessage("操作成功");
        getResult().setCode(ReturnObject.CODE_SUCCESS);
        getResult().setReturnValue(strJson);
        return SUCCESS;
    }


    public MenuPO getMenu() {
        return menu;
    }

    public void setMenu(MenuPO menu) {
        this.menu = menu;
    }

    public ReturnObject getResult() {
        return result;
    }

    public void setResult(ReturnObject result) {
        this.result = result;
    }
}
