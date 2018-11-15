package com.youngbook.service.system;

import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.MenuPO;
import com.youngbook.service.BaseService;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Component("menuService")
public class MenuService extends BaseService {

    public MenuPO loadMenu(MenuPO menu,Class<MenuPO> clazz)throws Exception{
        MenuPO menus = MySQLDao.load(menu, MenuPO.class);
        return menus;
    }

    public int deleteMenu(MenuPO menu, String operatorID, Connection conn)throws Exception{
        return MySQLDao.remove(menu,operatorID,conn);
    }

    public int saveMenu(MenuPO menu, String operatorID, Connection conn)throws Exception{
        return MySQLDao.insertOrUpdate(menu,operatorID,conn);
    }

    public List<MenuPO> listMenu(Class<MenuPO> clazz,Connection conn)throws Exception{
        DatabaseSQL dbSQL = DatabaseSQL.getInstance("select * from system_menu where state=0 ORDER BY orders asc");
        dbSQL.initSQL();
        List<MenuPO> menus = MySQLDao.search(dbSQL,clazz,conn);
        return menus;
    }


}
