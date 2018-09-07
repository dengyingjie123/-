package com.youngbook.action.callcenter;

import com.youngbook.action.BaseAction;
import com.youngbook.common.Pager;
import com.youngbook.common.config.Config;
import com.youngbook.entity.callcenter.CallCenterIncomingCallRegisterPO;
import com.youngbook.entity.vo.callcenter.CC_CustomerPersonalVO;
import com.youngbook.entity.vo.callcenter.CC_CustomerinstitutionVO;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CallCenterIncomingCallDisplayAction extends BaseAction {

    public String getCustomerByTelNumber() throws Exception{
        Object result = new Object();
        String result_msg = "";
        HttpServletRequest request = getRequest();
        Connection conn = getConnection();
        String callnumber = request.getParameter("callnumber");
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        int rowcount = 0;

        //个体户
        String str_sql="SELECT customer.*,users.name as salemanname FROM crm_customerpersonal AS customer";
        sb = new StringBuffer();
        sb.append(" ");
        sb.append("INNER JOIN crm_customerdistribution AS distribution ON customer.id = distribution.CustomerId INNER JOIN crm_saleman AS SaleMan ON distribution.SaleManId = SaleMan.operatorid INNER JOIN system_user AS users ON SaleMan.userid = users.operatorid");
        sb.append(" ");
        sb.append("WHERE");
        //固定电话
        if(callnumber.length() < 10){
            sb.append(" ");
            sb.append("customer.id = (SELECT id FROM crm_customerpersonal WHERE Phone = '"+ callnumber +"' OR Phone2 = '"+ callnumber +"' OR Phone3 = '"+ callnumber +"' ORDER BY id DESC LIMIT 1)");
        }
        else{//移动电话
            sb.append(" ");
            sb.append("customer.id = (SELECT id FROM crm_customerpersonal WHERE Mobile = '"+ callnumber +"' OR Mobile2 = '"+ callnumber +"' OR Mobile3 = '"+ callnumber +"' OR Mobile4 = '"+ callnumber +"' OR Mobile5 = '"+ callnumber +"' ORDER BY sid DESC LIMIT 1)");
        }
        str_sql += sb.toString();
        ps = conn.prepareStatement(str_sql);
        rs = ps.executeQuery();
        rs.first();
        rowcount = rs.getRow();
        if(rowcount == 1){
            CC_CustomerPersonalVO customerPersonalVO= new CC_CustomerPersonalVO();
            customerPersonalVO.setSid(rs.getInt("sid"));
            customerPersonalVO.setId(rs.getString("id"));
            customerPersonalVO.setName(rs.getString("name"));
            customerPersonalVO.setSex(rs.getInt("sex"));
            customerPersonalVO.setBirthday(rs.getString("birthday"));
            customerPersonalVO.setMobile(rs.getString("mobile"));
            customerPersonalVO.setMobile2(rs.getString("mobile2"));
            customerPersonalVO.setMobile3(rs.getString("mobile3"));
            customerPersonalVO.setMobile4(rs.getString("mobile4"));
            customerPersonalVO.setMobile5(rs.getString("mobile5"));
            customerPersonalVO.setPhone(rs.getString("phone"));
            customerPersonalVO.setPhone2(rs.getString("phone2"));
            customerPersonalVO.setPhone3(rs.getString("phone3"));
            customerPersonalVO.setAddress(rs.getString("address"));
            customerPersonalVO.setPostNo(rs.getString("postno"));
            customerPersonalVO.setEmail(rs.getString("email"));
            customerPersonalVO.setEmail2(rs.getString("email2"));
            customerPersonalVO.setEmail3(rs.getString("email3"));
            customerPersonalVO.setEmail4(rs.getString("email4"));
            customerPersonalVO.setEmail5(rs.getString("email5"));
            customerPersonalVO.setCreatedate(rs.getString("creattime"));
            customerPersonalVO.setSalemanname(rs.getString("salemanname"));
            result = customerPersonalVO.toJsonObject4Form();
            result_msg = "personal";
        }
        //集体户
        else{
        str_sql="SELECT customer.*,users.name as salemanname FROM crm_customerinstitution AS customer";
        sb.setLength(0);
        sb.append(" ");
        sb.append("INNER JOIN crm_customerdistribution AS distribution ON customer.id = distribution.CustomerId INNER JOIN crm_saleman AS SaleMan ON distribution.SaleManId = SaleMan.operatorid INNER JOIN system_user AS users ON SaleMan.userid = users.operatorid");
        sb.append(" ");
        sb.append("WHERE");
        //固定电话
        if(callnumber.length() < 10){
            sb.append(" ");
            sb.append("customer.id = (SELECT id FROM crm_customerpersonal WHERE Phone = '"+ callnumber +"' OR Phone2 = '"+ callnumber +"' OR Phone3 = '"+ callnumber +"' ORDER BY id DESC LIMIT 1)");

        }
        else{//移动电话
            sb.append(" ");
            sb.append("customer.id = (SELECT id FROM crm_customerpersonal WHERE Mobile = '"+ callnumber +"' OR Mobile2 = '"+ callnumber +"' OR Mobile3 = '"+ callnumber +"' OR Mobile4 = '"+ callnumber +"' OR Mobile5 = '"+ callnumber +"' ORDER BY sid DESC LIMIT 1)");
        }
        str_sql += sb.toString();
        ps = conn.prepareStatement(str_sql);
        rs = ps.executeQuery();
        rs.first();
        rowcount = rs.getRow();
            if(rowcount>0){
            CC_CustomerinstitutionVO customerinstitutionVO= new CC_CustomerinstitutionVO();
            customerinstitutionVO.setSid(rs.getInt("sid"));
            customerinstitutionVO.setId(rs.getString("id"));
            customerinstitutionVO.setName(rs.getString("name"));
            customerinstitutionVO.setMobile(rs.getString("mobile"));
            customerinstitutionVO.setMobile2(rs.getString("mobile2"));
            customerinstitutionVO.setMobile3(rs.getString("mobile3"));
            customerinstitutionVO.setMobile4(rs.getString("mobile4"));
            customerinstitutionVO.setMobile5(rs.getString("mobile5"));
            customerinstitutionVO.setPhone(rs.getString("phone"));
            customerinstitutionVO.setPhone2(rs.getString("phone2"));
            customerinstitutionVO.setPhone3(rs.getString("phone3"));
            customerinstitutionVO.setAddress(rs.getString("address"));
            customerinstitutionVO.setPostNo(rs.getString("postno"));
            customerinstitutionVO.setEmail(rs.getString("email"));
            customerinstitutionVO.setEmail2(rs.getString("email2"));
            customerinstitutionVO.setEmail3(rs.getString("email3"));
            customerinstitutionVO.setEmail4(rs.getString("email4"));
            customerinstitutionVO.setEmail5(rs.getString("email5"));
            customerinstitutionVO.setCreatedate(rs.getString("creattime"));
            customerinstitutionVO.setSalemanname(rs.getString("salemanname"));
            result = customerinstitutionVO.toJsonObject4Form();
            result_msg = "institution";
            }
        }
        getResult().setMessage(result_msg);
        getResult().setReturnValue(result);
        return SUCCESS;
    }

    public String getCustomerIncomingRegister() throws Exception{
        HttpServletRequest request = getRequest();
        Pager pager = null;
        String str_sql = "";
        String customerid = "";
        customerid = request.getParameter("customerid");
        str_sql = "SELECT * FROM callcenter_incomingcallsregister WHERE CustomerID = '"+ customerid +"' ";
//        pager = Pager.query(str_sql, CallCenterIncomingCallRegisterPO.class, request);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

}
