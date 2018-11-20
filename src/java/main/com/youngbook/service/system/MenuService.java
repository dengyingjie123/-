package com.youngbook.service.system;

import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.dao.MenuDao;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.MenuPO;
import com.youngbook.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Component("menuService")
public class MenuService extends BaseService {

    @Autowired
    private MenuDao menuDao;

    public MenuPO loadMenuPO (MenuPO menu, Connection conn)throws Exception{
        return menuDao.loadMenuPO(menu, conn);
    }

    public int deleteMenu(MenuPO menu, String operatorID, Connection conn)throws Exception{

        /**
         * 如果有岗位权限用到此菜单，则需要首选删除权限内容
         *
         *
         *
         */


        return menuDao.deleteMenu(menu,operatorID,conn);
    }

    public MenuPO saveMenu(MenuPO menu, String operatorId, Connection conn) throws Exception {
        return menuDao.saveMenu(menu, operatorId, conn);
    }

    public List<MenuPO> listMenu(Class<MenuPO> clazz,Connection conn)throws Exception{
        return menuDao.listMenu(clazz,conn);
    }


}
