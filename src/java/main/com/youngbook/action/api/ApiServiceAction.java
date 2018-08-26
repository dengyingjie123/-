package com.youngbook.action.api;

import com.youngbook.action.BaseAction;
import com.youngbook.common.RequestObject;
import com.youngbook.common.ReturnObject;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.entity.app.mapper.AppServiceMapperInvokeInfo;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Map;

/**
 * 创建人：zhouhaihong
 * 创建时间：2015/11/23
 * 描述：
 * AppServiceAction:
 *
 * 修改人：quan.zeng
 * 修改时间：2015/12/1
 * 描述：
 *
 * 修改人：quan.zeng
 * 修改时间：2015/12/3
 * 描述：
 */
public class ApiServiceAction extends BaseAction {

    private RequestObject params;

    public String services() throws Exception {
        //设置header
        setResponseHeader();
        //初始化请求参数
        initParams();
        //从application域中取出服务执行的映射信息
        Map<String,AppServiceMapperInvokeInfo> appServiceMapper = (Map<String,AppServiceMapperInvokeInfo>) getRequest().getSession().getServletContext().getAttribute("APP_SERVICE_MAPPER");
        AppServiceMapperInvokeInfo appServiceMapperInvokeInfo = appServiceMapper.get(params.getName()+"_"+params.getVersion());
        if(appServiceMapperInvokeInfo!=null){
            Class clazz = appServiceMapperInvokeInfo.getInvokeClazz(); //取出class

            //System.out.println(System.currentTimeMillis()); //1448960868795
            BaseAction baseService = (BaseAction)clazz.getConstructor(RequestObject.class,Connection.class).newInstance(params,getConnection());//通过实例化SERVICE
            //System.out.println(System.currentTimeMillis()); //1448960868795 实例化之前和实例化之后打印的时间一样

            Method method = appServiceMapperInvokeInfo.getInvokeMethod(); //取出映射执行的方法

            ReturnObject object = (ReturnObject) method.invoke(baseService);//执行该方法

            if (object != null) {
                setResultObj(object.getCode(), object.getMessage(), object.getToken(), object.getReturnValue());
            }else {
                setResultObj(400, "系统错误", "", "{}");
            }

        }else {
            setResultObj(404, "服务不存在", "", "{}");
        }

        return SUCCESS;
    }

    public void setResultObj(int code,String message,String token,Object returnValue){
        getResult().setCode(code);
        getResult().setMessage(message);
        getResult().setToken(token);
        getResult().setReturnValue(returnValue);
    }

    private void setResponseHeader(){
        HttpServletResponse response = this.getResponse();
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET"); //允许的请求方法，一般是GET,POST,PUT,DELETE,OPTIONS
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type"); //允许的请求头
    }

    private void initParams() throws Exception {
        HttpServletRequest request = getRequest();
        this.params = new RequestObject();
        this.params.setName(HttpUtils.getParameter(request, "name"));
        this.params.setVersion(HttpUtils.getParameter(request, "version"));
        if(!StringUtils.isEmpty(HttpUtils.getParameter(request, "data"))){
            JSONObject jsonObject = JSONObject.fromObject(HttpUtils.getParameter(request, "data"));
            //this.params.setData(jsonObject);
        }
        this.params.setToken(HttpUtils.getParameter(request, "token"));
    }

}
