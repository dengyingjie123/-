package com.youngbook.common;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 2/12/15
 * Time: 1:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class Permission {
    private String permission  = "";

    public static Permission getInstance(HttpServletRequest request) {
        Permission permission = new Permission();
        String permissionName = (String) request.getSession().getAttribute("PERMISSION_STRING");
        permission.setPermission(permissionName);
        return permission;
    }

    public boolean has(String permissionName) {
        return permission.contains(permissionName);
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
