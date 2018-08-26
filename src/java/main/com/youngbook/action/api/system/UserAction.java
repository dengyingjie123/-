package com.youngbook.action.api.system;

import com.youngbook.action.BaseAction;
import com.youngbook.common.RequestObject;
import com.youngbook.common.ReturnObject;

import java.sql.Connection;

/**
 * Created by zq on 2015/12/3.
 */
public class UserAction extends BaseAction{

    public UserAction(RequestObject requestObject, Connection conn) {
        super(requestObject,conn);
    }

    public ReturnObject getUsers() {

        RequestObject requestObject = this.getRequestObject();
        Connection connection = this.getConnection();

        //doSomething

        //this.setResult(returnObject);
        return this.getResult();
    }

}
