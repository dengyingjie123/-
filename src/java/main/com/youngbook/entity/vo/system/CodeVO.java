package com.youngbook.entity.vo.system;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created by Administrator on 2015/4/22.
 * 系统管理
 */

@Table(name = "System_Code", jsonPrefix = "codeVO")
public class CodeVO extends BaseVO {
    // sid
    @Id
    // id
    private String id = new String();


    /**
     * code
     */
    private String code = new String();

    /**
     * 创建时间 : 时间段查询
     */
    @DataAdapter(fieldType = FieldType.DATE)
    private String createTime = new String();

    /**
     * 类型 : 下拉菜单
     */
    private int type = Integer.MAX_VALUE;

    /**
     * 有效时间 : 时间段查询
     */
    @DataAdapter(fieldType = FieldType.DATE)
    private String availableTime = new String();

    /**
     * 失效时间
     */
    @DataAdapter(fieldType = FieldType.DATE)
    private String expiredTime = new String();

    /**
     * 使用时间 : 时间段查询
     */
    @DataAdapter(fieldType = FieldType.DATE)
    private String usedTime = new String();

    /**
     * 使用用户编号 : 支持查询
     */
    private String userId = new String();

    /**
     * 使用者IP : 支持查询
     */
    private String iP = new String();

    //使用者名称
    private String userName  = new String();
    //类型名称
    private String typeName = new String();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {return code;}

    public void setCode(String code) {this.code = code;}

    public String getiP() {return iP;}

    public void setiP(String iP) {this.iP = iP;}

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAvailableTime() {
        return availableTime;
    }

    public void setAvailableTime(String availableTime) {
        this.availableTime = availableTime;
    }

    public String getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(String expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(String usedTime) {
        this.usedTime = usedTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIP() {
        return iP;
    }

    public void setIP(String iP) {
        this.iP = iP;
    }


    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public CodeVO() {
    }
}
