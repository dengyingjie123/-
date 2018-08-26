package com.youngbook.entity.vo.mobile;

import com.youngbook.entity.vo.BaseVO;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-10-30
 * Time: 下午2:18
 * To change this template use File | Settings | File Templates.
 */
public class LoginUserVO extends BaseVO {
    //登陆账号--对应客户的编号
    private String account=new String();

    //登陆密码
    private String password=new String();

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
