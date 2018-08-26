package com.youngbook.entity.vo.system;



import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;
import net.sf.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-10-14
 * Time: 上午9:10
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "crm_customerpersonal_vo", jsonPrefix = "clientVO")
public class ClientVO extends BaseVO {

    // 序号
    @Id
    private int sid = Integer.MAX_VALUE;

    // 编号
    private String id = new String();

    // 状态
    private int state = Integer.MAX_VALUE;

    //姓名
    private String Name = new String();

    //性别
    private String Sex = new String();


    //电话
    private String Mobile = new String();

    //邮箱
    private String Email = new String();

    //生日
    private String Birthday = new String();


    @Override
    public JSONObject toJsonObject4Tree() {
        JSONObject json = new JSONObject();
        json.element("id", this.getId());
        json.element("text", this.getName());

        return json;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }
}
