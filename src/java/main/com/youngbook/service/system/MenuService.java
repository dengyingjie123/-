package com.youngbook.service.system;

import com.youngbook.common.MyException;
import com.youngbook.dao.MenuDao;
import com.youngbook.entity.po.MenuPO;
import com.youngbook.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;
/**
* @description:    菜单管理service层
* @author: 徐明煜
* @createDate:     2018/12/3 13:16
* @version:        1.1
*/
@Component("menuService")
public class MenuService extends BaseService {

    @Autowired
    private MenuDao menuDao;

    /**
     * @description 单个菜单对象返回
     * @author 徐明煜
     * @date 2018/12/3 13:17
     * @param menu
     * @param clazz
     * @param conn
     * @return com.youngbook.entity.po.MenuPO
     * @throws Exception
     */
    public MenuPO loadMenuPO(MenuPO menu, Class<MenuPO> clazz, Connection conn) throws Exception {

        if(menu == null){
            MyException myException = new MyException();
            myException.setPeopleMessage("从前端获取menuPO对象失败");
            myException.throwException();
        }
        return menuDao.loadMenuPO(menu, clazz, conn);
    }


    /**
     * @description 删除菜单的实现，进行逻辑删除
     * @author 徐明煜
     * @date 2018/12/3 13:18
     * @param menu
     * @param operatorId
     * @param conn
     * @return com.youngbook.entity.po.MenuPO
     * @throws Exception
     */
    public MenuPO deleteMenuPO(MenuPO menu, String operatorId, Connection conn) throws Exception {

        if(menu == null){
            MyException myException = new MyException();
            myException.setPeopleMessage("从前端获取menuPO对象失败");
            myException.throwException();
        }
        return menuDao.deleteMenuPO(menu, operatorId, conn);
    }


    /**
     * @description 保存新建的菜单
     * @author 徐明煜
     * @date 2018/12/3 13:26
     * @param menu
     * @param operatorId
     * @param conn
     * @return com.youngbook.entity.po.MenuPO
     * @throws Exception
     */
    public MenuPO saveMenuPO(MenuPO menu, String operatorId, Connection conn) throws Exception {
        if(menu == null){
            MyException myException = new MyException();
            myException.setPeopleMessage("从前端获取menuPO对象失败");
            myException.throwException();
        }
        return menuDao.saveMenuPO(menu, operatorId, conn);
    }

    /**
     * @description 列出所有可用菜单，放在左侧菜单书
     * @author 徐明煜
     * @date 2018/12/3 13:26
     * @param clazz
     * @param conn
     * @return java.util.List<com.youngbook.entity.po.MenuPO>
     * @throws Exception
     */
    public List<MenuPO> listMenuPO(Class<MenuPO> clazz, Connection conn) throws Exception {
        return menuDao.listMenuPO(clazz, conn);
    }
}
