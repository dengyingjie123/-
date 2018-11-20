package com.youngbook.dao;

import com.youngbook.common.Database;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.entity.po.MenuPO;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class MenuDao {
    /**
     * 精确查询
     * Version: 0.1.5
     * @param po 传入查询条件
     * @return List<MenuPO> 返回查询结果
     */
    public List<MenuPO> queryExactly(MenuPO po) {
        // 保存查询结果
        List<MenuPO> list = new ArrayList<MenuPO>();

        // 检查输入Vo参数合法性
        if (po == null) {
            // 返回空数值
            return list;
        }

        try {
            // 初始化数据库资源
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;

            try {
                // 获得数据库资源
                conn = Database.getConnection();
                stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);

                // 组织查询语句
                StringBuffer sbSQL = new StringBuffer();
                sbSQL.append("SELECT * FROM system_menu");

                // 组织查询条件
                StringBuffer sbWhereSQL = new StringBuffer();

                // 如果查询条件“编号”不为空，加入Where语句
                if (po.getId() != null && !po.getId().equals("")) {
                    sbWhereSQL.append("id='");
                    sbWhereSQL.append(po.getId());
                    sbWhereSQL.append("' AND ");
                }

                // 如果查询条件“图标”不为空，加入Where语句
                if (po.getIcon() != null && !po.getIcon().equals("")) {
                    sbWhereSQL.append("icon='");
                    sbWhereSQL.append(po.getIcon());
                    sbWhereSQL.append("' AND ");
                }

                // 如果查询条件“权限名称”不为空，加入Where语句
                if (po.getPermissionName() != null && !po.getPermissionName().equals("")) {
                    sbWhereSQL.append("permissionName='");
                    sbWhereSQL.append(po.getPermissionName());
                    sbWhereSQL.append("' AND ");
                }

                // 如果查询条件“父级编号”不为空，加入Where语句
                if (po.getParentId() != null && !po.getParentId().equals("")) {
                    sbWhereSQL.append("parentId='");
                    sbWhereSQL.append(po.getParentId());
                    sbWhereSQL.append("' AND ");
                }

                // 如果查询条件“菜单名称”不为空，加入Where语句
                if (po.getName() != null && !po.getName().equals("")) {
                    sbWhereSQL.append("name='");
                    sbWhereSQL.append(po.getName());
                    sbWhereSQL.append("' AND ");
                }

                // 如果查询条件“类型”不为空，加入Where语句

                // 如果查询条件“链接”不为空，加入Where语句
                if (po.getUrl() != null && !po.getUrl().equals("")) {
                    sbWhereSQL.append("url='");
                    sbWhereSQL.append(po.getUrl());
                    sbWhereSQL.append("' AND ");
                }


                // 删除Where语句最后一个“ AND ”
                if (sbWhereSQL.length() > 0) {
                    sbWhereSQL.delete(sbWhereSQL.length() - 5, sbWhereSQL.length());
                    sbSQL.append(" WHERE ");
                    sbSQL.append(sbWhereSQL.toString());
                }

                // 打印查询日志
                System.out.println("MenuDao.queryExactly(): " + sbSQL.toString());

                // 执行查询操作
                rs = stmt.executeQuery(sbSQL.toString());

                // 组织Vo对象
                while (rs.next()) {
                    MenuPO voResult = new MenuPO();
                    voResult.setId(rs.getString("id"));
                    voResult.setIcon(rs.getString("icon"));
                    voResult.setPermissionName(rs.getString("permissionName"));
                    voResult.setParentId(rs.getString("parentId"));
                    voResult.setName(rs.getString("name"));
                    voResult.setType(rs.getInt("type"));
                    voResult.setUrl(rs.getString("url"));
                    list.add(voResult);
                }
            }
            catch (Exception e) {
                // 打印异常日志
                System.out.println("MenuDao.queryExactly(): 查询失败。");
                e.printStackTrace();
                list = new ArrayList<MenuPO>();
            }
            finally {
                // 释放数据库资源
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        // 返回查询结果
        return list;
    }

    /**
     * 模糊查询
     * Version: 0.1.5
     * @param po 传入查询条件
     * @return List<MenuPO> 返回查询结果
     */
    public List<MenuPO> query(MenuPO po) {
        // 保存查询结果
        List<MenuPO> list = new ArrayList<MenuPO>();

        // 检查输入Vo参数合法性
        if (po == null) {
            // 返回空数值
            return list;
        }

        try {
            // 初始化数据库资源
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;

            try {
                // 获得数据库资源
                conn = Database.getConnection();
                stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);

                // 组织查询语句
                StringBuffer sbSQL = new StringBuffer();
                sbSQL.append("SELECT * FROM system_menu");

                // 组织查询条件
                StringBuffer sbWhereSQL = new StringBuffer();

                // 如果查询条件“编号”不为空，加入Where语句
                if (po.getId() != null && !po.getId().equals("")) {
                    sbWhereSQL.append("id LIKE '%");
                    sbWhereSQL.append(po.getId());
                    sbWhereSQL.append("%' AND ");
                }

                // 如果查询条件“图标”不为空，加入Where语句
                if (po.getIcon() != null && !po.getIcon().equals("")) {
                    sbWhereSQL.append("icon LIKE '%");
                    sbWhereSQL.append(po.getIcon());
                    sbWhereSQL.append("%' AND ");
                }

                // 如果查询条件“权限名称”不为空，加入Where语句
                if (po.getPermissionName() != null && !po.getPermissionName().equals("")) {
                    sbWhereSQL.append("permissionName LIKE '%");
                    sbWhereSQL.append(po.getPermissionName());
                    sbWhereSQL.append("%' AND ");
                }

                // 如果查询条件“父级编号”不为空，加入Where语句
                if (po.getParentId() != null && !po.getParentId().equals("")) {
                    sbWhereSQL.append("parentId LIKE '%");
                    sbWhereSQL.append(po.getParentId());
                    sbWhereSQL.append("%' AND ");
                }

                // 如果查询条件“菜单名称”不为空，加入Where语句
                if (po.getName() != null && !po.getName().equals("")) {
                    sbWhereSQL.append("name LIKE '%");
                    sbWhereSQL.append(po.getName());
                    sbWhereSQL.append("%' AND ");
                }

                // 如果查询条件“类型”不为空，加入Where语句

                // 如果查询条件“链接”不为空，加入Where语句
                if (po.getUrl() != null && !po.getUrl().equals("")) {
                    sbWhereSQL.append("url LIKE '%");
                    sbWhereSQL.append(po.getUrl());
                    sbWhereSQL.append("%' AND ");
                }


                // 删除Where语句最后一个“ AND ”
                if (sbWhereSQL.length() > 0) {
                    sbWhereSQL.delete(sbWhereSQL.length() - 5, sbWhereSQL.length());
                    sbSQL.append(" WHERE ");
                    sbSQL.append(sbWhereSQL.toString());
                }

                // 打印查询日志
                System.out.println("MenuDao.query(): " + sbSQL.toString());

                // 执行查询操作
                rs = stmt.executeQuery(sbSQL.toString());

                // 组织Vo对象
                while (rs.next()) {
                    MenuPO voResult = new MenuPO();
                    voResult.setId(rs.getString("id"));
                    voResult.setIcon(rs.getString("icon"));
                    voResult.setPermissionName(rs.getString("permissionName"));
                    voResult.setParentId(rs.getString("parentId"));
                    voResult.setName(rs.getString("name"));
                    voResult.setType(rs.getInt("type"));
                    voResult.setUrl(rs.getString("url"));
                    list.add(voResult);
                }
            }
            catch (Exception e) {
                // 打印异常日志
                System.out.println("MenuDao.query(): 查询失败。");
                e.printStackTrace();
                list = new ArrayList<MenuPO>();        }
            finally {
                // 释放数据库资源
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        // 返回查询结果
        return list;
    }

    /**
     * 按ID删除
     * Version: 0.1.5
     * @param po 保存条件ID
     * @return int 0:失败；1：成功
     */
    public int delete(MenuPO po) throws Exception {
        // 保存成功标识
        int intCount = 0;

        try {
            // 初始化数据库信息
            Connection conn = null;

            try {
                // 获得数据库资源
                conn = Database.getConnection();

                // 执行删除操作
                return this.delete(po, conn);
            }
            catch (Exception e) {
                // 打印日志
                System.out.println("MenuDao.delete(): 删除失败。");
                e.printStackTrace();

                // 设置失败标识
                intCount = 0;
            }
            finally {
                // 释放数据库资源
                if (conn != null) {
                    conn.close();
                }
            }
        }
        catch (Exception ex) {
            // 打印日志
            ex.printStackTrace();
            throw ex;
        }

        // 返回是否成功信息。
        return intCount;
    }


    /**
     * 按ID删除
     * Version: 0.1.5
     * @param po 保存条件ID
     * @return int 0:失败；1：成功
     */
    public int delete(MenuPO po, Connection conn) throws Exception {
        // 保存成功标识
        int intCount = 0;

        try {
            // 初始化数据库信息
            Statement stmt = null;

            try {
                // 获得数据库资源
                stmt = conn.createStatement();

                // 组织删除SQL语句
                StringBuffer sbSQL = new StringBuffer();
//                sbSQL.append("DELETE FROM system_menu WHERE id='" + po.getId() + "'");
                //逻辑删除
                sbSQL.append("UPDATE system_menu SET state=0 WHERE id='" + po.getId() + "'");

                // 打印日志
                System.out.println("MenuDao.delete(): " + sbSQL.toString());

                // 执行删除操作
                intCount = stmt.executeUpdate(sbSQL.toString());
            }
            catch (Exception e) {
                // 打印日志
                System.out.println("MenuDao.delete(): 删除失败。");
                e.printStackTrace();

                // 设置失败标识
                intCount = 0;
            }
            finally {
                // 释放数据库资源
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (Exception ex) {
            // 打印日志
            ex.printStackTrace();
            throw ex;
        }

        // 返回是否成功信息。
        return intCount;
    }

    /**
     * 新增记录
     * Version: 0.1.5
     * @param po 记录内容
     * @return int 0:失败；1：成功
     */
    public int insert(MenuPO po) {
        // 保存是否成功
        int intCount = 0;
        try {
            // 初始化数据库资源
            Connection conn = null;
            try {
                // 获得数据库资源
                conn = Database.getConnection();
                return this.insert(po, conn);
            }
            catch (Exception e) {
                // 打印日志
                System.out.println("MenuDao.insert(): 插入失败。");
                e.printStackTrace();

                // 设置失败标识
                intCount = 0;
            }
            finally {

                // 释放数据库资源
                if (conn != null) {
                    conn.close();
                }
            }
        }
        catch (Exception ex) {

            // 打印日志
            ex.printStackTrace();
        }

        // 返回是否成功信息
        return intCount;
    }


    /*
     * 新增记录
     * Version: 0.1.5
     * @param po 记录内容
     * @return int 0:失败；1：成功
     */
    public int insert(MenuPO po, Connection conn) {
        // 保存是否成功
        int intCount = 0;
        try {
            // 初始化数据库资源
            Statement stmt = null;
            try {
                // 获得数据库资源
                stmt = conn.createStatement();

                // 组织新增记录SQL
                StringBuffer sbSQL = new StringBuffer();

                // 组织新增记录SQL中的字段
                StringBuffer sbFields = new StringBuffer();

                // 组织新增记录SQL数值
                StringBuffer sbValues = new StringBuffer();

                // 开始组织SQL语句
                sbSQL.append("INSERT INTO system_menu (");

                if (po.getId() != null) {
                    sbFields.append("id");
                    sbFields.append(",");
                }

                if (po.getIcon() != null) {
                    sbFields.append("icon");
                    sbFields.append(",");
                }

                if (po.getPermissionName() != null) {
                    sbFields.append("permissionName");
                    sbFields.append(",");
                }

                if (po.getParentId() != null) {
                    sbFields.append("parentId");
                    sbFields.append(",");
                }

                if (po.getName() != null) {
                    sbFields.append("name");
                    sbFields.append(",");
                }

                if (po.getType() != Integer.MAX_VALUE) {
                    sbFields.append("type");
                    sbFields.append(",");
                }

                if (po.getUrl() != null) {
                    sbFields.append("url");
                    sbFields.append(",");
                }

                // 删除Fields中的最后一个“,”
                if (sbFields.length() > 0) {
                    sbFields.delete(sbFields.length() - 1, sbFields.length());
                    sbSQL.append(sbFields.toString());
                }
                sbSQL.append(") VALUES (");

                if (po.getId() != null) {
                    sbValues.append("'" + po.getId() + "'");
                    sbValues.append(",");
                }

                if (po.getIcon() != null) {
                    sbValues.append("'" + po.getIcon() + "'");
                    sbValues.append(",");
                }

                if (po.getPermissionName() != null) {
                    sbValues.append("'" + po.getPermissionName() + "'");
                    sbValues.append(",");
                }

                if (po.getParentId() != null) {
                    sbValues.append("'" + po.getParentId() + "'");
                    sbValues.append(",");
                }

                if (po.getName() != null) {
                    sbValues.append("'" + po.getName() + "'");
                    sbValues.append(",");
                }

                if (po.getType() != Integer.MAX_VALUE) {
                    sbValues.append(po.getType());
                    sbValues.append(",");
                }

                if (po.getUrl() != null) {
                    sbValues.append("'" + po.getUrl() + "'");
                    sbValues.append(",");
                }


                // 删除Values的最后一个“,”
                if (sbValues.length() > 0) {
                    sbValues.delete(sbValues.length() - 1, sbValues.length());
                    sbSQL.append(sbValues.toString());
                }
                sbSQL.append(")");

                // 打印日志
                System.out.println("MenuDao.insert(): " + sbSQL.toString());

                // 执行增加操作
                intCount = stmt.executeUpdate(sbSQL.toString());
            }
            catch (Exception e) {
                // 打印日志
                System.out.println("MenuDao.insert(): 插入失败。");
                e.printStackTrace();

                // 设置失败标识
                intCount = 0;
            }
            finally {

                // 释放数据库资源
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (Exception ex) {

            // 打印日志
            ex.printStackTrace();
        }

        // 返回是否成功信息
        return intCount;
    }


    /**
     * 按ID更新数据
     * Version: 0.1.5
     * @param po 保存更新内容
     * @return int 0:失败；1：成功
     */
    public int update(MenuPO po) throws Exception {
        // 保存是否成功
        int intCount = 0;
        try {
            // 初始化数据库资源
            Connection conn = null;
            Statement stmt = null;
            try {
                // 获得数据库资源
                conn = Database.getConnection();
                stmt = conn.createStatement();

                return this.update(po, conn);
            }
            catch (Exception e) {
                // 打印日志
                System.out.println("MenuDao.update(): 更新操作失败。");
                e.printStackTrace();

                // 设置失败标识
                intCount = 0;
            }
            finally {
                // 释放数据库资源
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (Exception ex) {
            //
            ex.printStackTrace();
            throw ex;
        }
        // 返回是否成功信息
        return intCount;
    }


    /**
     * 按ID更新数据
     * Version: 0.1.5
     * @param po 保存更新内容
     * @return int 0:失败；1：成功
     */
    public int update(MenuPO po, Connection conn) throws Exception {
        // 保存是否成功
        int intCount = 0;
        try {
            // 初始化数据库资源
            Statement stmt = null;
            try {
                // 获得数据库资源
                stmt = conn.createStatement();

                // 组织更新记录SQL
                StringBuffer sbSQL = new StringBuffer();
                StringBuffer sbValues = new StringBuffer();
                sbSQL.append("UPDATE system_menu SET ");

                if (po.getIcon() != null && !po.getIcon().equals("")) {
                    sbValues.append("icon='");
                    sbValues.append(po.getIcon());
                    sbValues.append("', ");
                }

                if (po.getPermissionName() != null && !po.getPermissionName().equals("")) {
                    sbValues.append("permissionName='");
                    sbValues.append(po.getPermissionName());
                    sbValues.append("', ");
                }

                if (po.getParentId() != null && !po.getParentId().equals("")) {
                    sbValues.append("parentId='");
                    sbValues.append(po.getParentId());
                    sbValues.append("', ");
                }

                if (po.getName() != null && !po.getName().equals("")) {
                    sbValues.append("name='");
                    sbValues.append(po.getName());
                    sbValues.append("', ");
                }


                if (po.getUrl() != null && !po.getUrl().equals("")) {
                    sbValues.append("url='");
                    sbValues.append(po.getUrl());
                    sbValues.append("', ");
                }
                if (sbValues.length() > 0) {
                    sbValues.delete(sbValues.length() - 2, sbValues.length());
                    sbSQL.append(sbValues.toString());
                }

                sbSQL.append(" WHERE id='");
                sbSQL.append(po.getId());
                sbSQL.append("'");


                // 打印日志
                System.out.println("MenuDao.update(): " + sbSQL.toString());

                // 执行更新操作
                intCount = stmt.executeUpdate(sbSQL.toString());
            }
            catch (Exception e) {
                // 打印日志
                System.out.println("MenuDao.update(): 更新操作失败。");
                e.printStackTrace();

                // 设置失败标识
                intCount = 0;
            }
            finally {
                // 释放数据库资源
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (Exception ex) {
            //
            ex.printStackTrace();
            throw ex;
        }
        // 返回是否成功信息
        return intCount;
    }


    /**
     * 从Request构建对象实例
     * Version: 0.1.5
     * @param request 传入XML
     * @return MenuPO 对象实例
     */
    public MenuPO getInstance(HttpServletRequest request) {
        // 保存实例
        MenuPO po = new MenuPO();


        try {
            // 设置 “编号”
            po.setId(request.getParameter("id"));

            // 设置 “图标”
            po.setIcon(request.getParameter("icon"));

            // 设置 “权限名称”
            po.setPermissionName(request.getParameter("permissionName"));

            // 设置 “父级编号”
            po.setParentId(request.getParameter("parentId"));

            // 设置 “菜单名称”
            po.setName(request.getParameter("name"));

            // 设置 “类型”
            po.setType(Integer.parseInt(request.getParameter("type")));

            // 设置 “链接”
            po.setUrl(request.getParameter("url"));

        }
        catch (Exception e) {
            // 打印异常日志
            System.out.println("MenuDao.getInstance(): 从HttpServletRequest构建失败。");
            e.printStackTrace();
        }

        // 返回实例
        return po;
    }

    public MenuPO loadMenuPO(MenuPO menu, Connection conn) throws Exception {

        menu.setState(Config.STATE_CURRENT);
        MenuPO menuPO = MySQLDao.load(menu, MenuPO.class, conn);
        return menuPO;
    }

    public int deleteMenu(MenuPO menu, String operatorID, Connection conn)throws Exception{
        return MySQLDao.remove(menu,operatorID,conn);
    }

    public MenuPO saveMenu(MenuPO menu, String operatorId, Connection conn) throws Exception {

        MySQLDao.insertOrUpdate(menu, operatorId, conn);

        return menu;
    }

    public List<MenuPO> listMenu(Class<MenuPO> clazz,Connection conn)throws Exception{
        DatabaseSQL dbSQL = DatabaseSQL.getInstance("select * from system_menu where state=0 ORDER BY orders asc");
        dbSQL.initSQL();
        List<MenuPO> menus = MySQLDao.search(dbSQL,clazz,conn);
        return menus;
    }
}
