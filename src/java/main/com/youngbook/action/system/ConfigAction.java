package com.youngbook.action.system;

import com.youngbook.action.BaseAction;
import com.youngbook.common.MyException;
import com.youngbook.common.ReturnObjectCode;
import com.youngbook.common.config.Config;
import com.youngbook.common.config.Config4Bank;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.system.ILogDao;
import com.youngbook.entity.po.system.LogPO;
import com.youngbook.service.pay.FuiouService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.Iterator;
import java.util.Map;


public class ConfigAction  extends BaseAction {

    @Autowired
    ILogDao logDao;

    @Autowired
    FuiouService fuiouService;

    public String getFuiouMd5() throws Exception {

        String formValue = getHttpRequestParameter("formValue");

        if (StringUtils.isEmpty(formValue)) {
            MyException.newInstance("传入参数有误").throwException();
        }

        String md5 = fuiouService.getMd5(formValue);

        JSONObject json = new JSONObject();
        json.put("md5", md5);
//        json.put("formValue", formValue);

        getResult().setReturnValue(json);

        return SUCCESS;
    }

    public String getMd54PC() throws Exception {

        String formValue = getHttpRequestParameter("formValue");

        if (StringUtils.isEmpty(formValue)) {
            MyException.newInstance("传入参数有误").throwException();
        }

        String md5 = fuiouService.getMd54PC(formValue);

        JSONObject json = new JSONObject();
        json.put("md5", md5);
//        json.put("formValue", formValue);

        getResult().setReturnValue(json);

        return SUCCESS;
    }

    public String saveLog() throws Exception {

        String peopleMessage = getHttpRequestParameter("peopleMessage");
        String machineMessage = getHttpRequestParameter("machineMessage");

        LogPO logPO = new LogPO();
        logPO.setName("common log");
        logPO.setPeopleMessage(peopleMessage);
        logPO.setMachineMessage(machineMessage);

        logDao.save(logPO, getConnection());

        return SUCCESS;
    }


    public String getSystemSequence() throws Exception{

        int sequence = MySQLDao.getSequence("system");

        JSONObject json = new JSONObject();
        json.element("sequence", sequence);

        getResult().setReturnValue(json);

        return SUCCESS;
    }

    /**
     * 内容：获取系统变量值
     * @return
     * @throws Exception
     */
    public String getSystemVariable()throws  Exception{
        // 获取请求
        HttpServletRequest request = this.getRequest();
        //获取参数
        String key = HttpUtils.getParameter(request, "key");
        // 校验参数合法性
        if(StringUtils.isEmpty(key)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }
        //获取系统变量
        String variable = Config.getSystemVariable(key);
        //校验参数是否为空
        if(StringUtils.isEmpty(variable)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_CORRECT, "参数不正确").throwException();
        }
        JSONObject json = new JSONObject();
        json.element("variableId", variable);
        getResult().setReturnValue(json);
        return  SUCCESS;
    }
    /**
     * 内容：获取多个系统变量值
     * 用法：获取的params是json格式，将params格式为json对象（jsonObject），在遍历jsonObject取值获取系统变量。
     * @return
     * @throws Exception
     */
    public String getSystemVariables()throws  Exception{
        // 获取请求
        HttpServletRequest request = this.getRequest();
        //获取参数
        String params = HttpUtils.getParameter(request, "params");
        if(StringUtils.isEmpty(params)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }
        //创建返回的json对象
        JSONObject returnJson = new JSONObject();

        JSONObject jsonObject=null;
        try {
            //将传入的值格式化为json对象
            jsonObject = JSONObject.fromObject(params);
        }catch (Exception e){
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_CORRECT, "参数不正确,请确保V值为String").throwException();
        }
        try {
            //遍历json
            for(int i=1;i<=jsonObject.size();i++){
                //从1开始取值，第一个值为key1
                String key =jsonObject.getString("key" + i);
                //获取系统变量
                String variable = Config.getSystemVariable(key);
               // 封装到returnJson
                returnJson.element("key"+i,variable);
            }
        }  catch (Exception e) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_CORRECT, "参数不正确").throwException();
        }
        getResult().setReturnValue(returnJson);
        return  SUCCESS;
    }

    public String configGetSystemIcon() {

        return SUCCESS;
    }

    /**
     * 获取系统支持的银行卡列表接口
     *
     * 作者：姚章鹏
     * 内容：创建代码
     * 时间：2016年1月8日
     *
     * @return
     * @throws Exception
     */
    public String getBanks(Connection conn) throws Exception{
        // 创建返回的json对象
        Map<String, String> banks = Config4Bank.getBanks(conn);
        if(banks==null||banks.size()==0){
            MyException.newInstance(ReturnObjectCode.PUBLIC_BANKS_NOT_FOUND, "没有银行卡信息").throwException();
        }
        JSONArray array=new JSONArray();
        Iterator it = banks.entrySet().iterator();
        while (it.hasNext()) {
            JSONObject json = new JSONObject();
            Map.Entry entry = (Map.Entry) it.next();
            json.put("bankCode",entry.getKey());
            json.put("bankName",entry.getValue());
            array.add(json);
        }
        getResult().setReturnValue(array);
        return SUCCESS;
    }
}
