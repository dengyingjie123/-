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

    public MenuPO loadMenu(MenuPO menu,Class<MenuPO> clazz)throws Exception{
        return menuDao.loadMenu(menu, MenuPO.class);
    }

    public int deleteMenu(MenuPO menu, String operatorID, Connection conn)throws Exception{
        return menuDao.deleteMenu(menu,operatorID,conn);
    }

    public int saveMenu(MenuPO menu, String operatorID, Connection conn)throws Exception{
        return menuDao.saveMenu(menu,operatorID,conn);
    }

    public List<MenuPO> listMenu(Class<MenuPO> clazz,Connection conn)throws Exception{
        return menuDao.listMenu(clazz,conn);
    }


}
