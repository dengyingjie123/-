package com.youngbook.common.wf.test;
/**
 * 标题：Jxp类
 * 描述: 实现IBziDao接口，完成业务逻辑
 * 版权：
 * oracle.jdbc.driver.OracleDriver
 */
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.youngbook.common.wf.admin.Action;
import com.youngbook.common.wf.admin.RouteList;
import com.youngbook.common.wf.common.*;
import com.youngbook.common.wf.engines.*;
public class Jxp implements IBizDao {

    @Override
    public boolean satisfyForwardCondition(String Condition, Connection conn) throws Exception {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public HashMap dataSnapShot() throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public HashMap dataSnapShot(Connection conn) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * 说明：根据传入的RouteList 来操作当业务结束的时候
     * 需要处理的一些业务上的操作。
     *
     * @param routeList 流转记录对象
     * @param workflow
     * @param conn      数据库连接  @return
     * @throws Exception
     */
    @Override
    public int afterOver(RouteList routeList, Action workflow, Connection conn) throws Exception {
        return 0;
    }


    /**
     * 说明：设置再afterOver中调用的service 的CLass 的全名称
     *
     * @param className
     * @return
     */
    @Override
    public void setServiceClassName(String className) {

    }

    /**
     * 获取设置的service 的class 的全名称
     *
     * @return
     */
    @Override
    public String getServiceClassName() {
        return "";
    }

    protected int intPH = Integer.MAX_VALUE;  //票号
    protected String strSQDW = new String();  //申请单位
    protected String strSQR = new String();  //申请人
    protected String strTDLXR = new String();  //停电联系人
    protected String strLXDH = new String();  //联系电话
    protected String strSBMC = new String();  //设备名称
    protected String strWZNR = new String();  //工作内容
    protected String strTDFW = new String();  //停电范围
    protected String strSQDWYJ = new String();  //申请单位意见
    protected String strJHGZSJQ = new String();  //申请停（复）电时间起
    protected String strJHGZSJZ = new String();  //申请停（复）电时间止
    protected String strBDWSH = new String();  //本单位审批
    protected String strPZTHDSHQ = new String();  //批准停（复）电时间起
    protected String strPZTHDSHZ = new String();  //批准停（复）电时间止
    protected String strYYKYJ = new String();  //营运科意见
    protected String strYYKPZ = new String();  //营运科批准
    protected String strYYKSHYJ = new String();  //营运科审核意见
    protected String strYYKSHPZ = new String();  //营运科审核批准
    protected String strJDKYJ = new String();  //继电科科意见
    protected String strJDKPZ = new String();  //继电科科批准
    protected String strJDKSH = new String();  //继电科审核
    protected String strJDKSHPZ = new String();  //继电科审核批准
    protected String strZDHKYJ = new String();  //自动化科意见
    protected String strZDHKPZ = new String();  //自动化科批准
    protected String strLDYJ = new String();  //领导意见
    protected String strLDYJPZ = new String();  //领导批准
    protected String strDDKYJ = new String();  //调度科意见
    protected String strDDKPZ = new String();  //调度科批准 
    protected String strTZSJ = new String();  //通知时间
    protected String strTZJLR = new String();  //通知接令人
    protected String strTZSLR = new String();  //通知人
    protected String strTZPZ = new String();  //通知批准
    protected String strTDSJ = new String();  //停电时间
    protected String strTDSLR = new String();  //停电受令人
    protected String strTDDDY = new String();  //停电调度员
    protected String strTDPZ = new String();  //停电批准
    protected String strKGSJ = new String();  //开工时间
    protected String strKGSLR = new String();  //开工受令人
    protected String strKGDDY = new String();  //开工调度员
    protected String strKGPZ = new String();  //开工批准
    protected String strWGSJ = new String();  //完工时间
    protected String strWGSLR = new String();  //完工受令人
    protected String strWGDDY = new String();  //完工调度员
    protected String strWGPZ = new String();  //完工批准
    protected String strFDSJ = new String();  //复电时间
    protected String strFDSLR = new String();  //复电受令人
    protected String strFDDDY = new String();  //复电调度员
    protected String strFDPZ = new String();  //复电批准
    protected String strYQSJ = new String();  //延期时间
    protected String strYQSQR = new String();  //延期申请人
    protected String strYQPXR = new String();  //延期批准人
    protected String strYQYY = new String();  //延期原因
    protected String strYQPZ = new String();  //延期批准
    protected String strBZ = new String();  //备注

    /**
     * 标题：BuildObject方法
     * 描述：从request中获得参数，初始化Jxp类
     * 返回值：void
     */
    public void BuildObject(HttpServletRequest request) throws Exception {
        if (request.getParameter("PH") != null &&
                !request.getParameter("PH").equals("")) {
            this.setPH(Integer.parseInt(request.getParameter("PH")));
        }
        if (request.getParameter("SQDW") != null &&
                !request.getParameter("SQDW").equals("")) {
            this.setSQDW(Tools.toUTF8(request.getParameter("SQDW")));
        }
        if (request.getParameter("SQR") != null &&
                !request.getParameter("SQR").equals("")) {
            this.setSQR(Tools.toUTF8(request.getParameter("SQR")));
        }
        if (request.getParameter("TDLXR") != null &&
                !request.getParameter("TDLXR").equals("")) {
            this.setTDLXR(Tools.toUTF8(request.getParameter("TDLXR")));
        }
        if (request.getParameter("LXDH") != null &&
                !request.getParameter("LXDH").equals("")) {
            this.setLXDH(Tools.toUTF8(request.getParameter("LXDH")));
        }
        if (request.getParameter("SBMC") != null &&
                !request.getParameter("SBMC").equals("")) {
            this.setSBMC(Tools.toUTF8(request.getParameter("SBMC")));
        }
        if (request.getParameter("WZNR") != null &&
                !request.getParameter("WZNR").equals("")) {
            this.setWZNR(Tools.toUTF8(request.getParameter("WZNR")));
        }
        if (request.getParameter("TDFW") != null &&
                !request.getParameter("TDFW").equals("")) {
            this.setTDFW(Tools.toUTF8(request.getParameter("TDFW")));
        }
        if (request.getParameter("SQDWYJ") != null &&
                !request.getParameter("SQDWYJ").equals("")) {
            this.setSQDWYJ(Tools.toUTF8(request.getParameter("SQDWYJ")));
        }
        if (request.getParameter("JHGZSJQ") != null &&
                !request.getParameter("JHGZSJQ").equals("")) {
            this.setJHGZSJQ(Tools.toUTF8(request.getParameter("JHGZSJQ")));
        }
        if (request.getParameter("JHGZSJZ") != null &&
                !request.getParameter("JHGZSJZ").equals("")) {
            this.setJHGZSJZ(Tools.toUTF8(request.getParameter("JHGZSJZ")));
        }
        if (request.getParameter("BDWSH") != null &&
                !request.getParameter("BDWSH").equals("")) {
            this.setBDWSH(Tools.toUTF8(request.getParameter("BDWSH")));
        }
        if (request.getParameter("PZTHDSHQ") != null &&
                !request.getParameter("PZTHDSHQ").equals("")) {
            this.setPZTHDSHQ(Tools.toUTF8(request.getParameter("PZTHDSHQ")));
        }
        if (request.getParameter("PZTHDSHZ") != null &&
                !request.getParameter("PZTHDSHZ").equals("")) {
            this.setPZTHDSHZ(Tools.toUTF8(request.getParameter("PZTHDSHZ")));
        }
        if (request.getParameter("YYKYJ") != null &&
                !request.getParameter("YYKYJ").equals("")) {
            this.setYYKYJ(Tools.toUTF8(request.getParameter("YYKYJ")));
        }
        if (request.getParameter("YYKPZ") != null &&
                !request.getParameter("YYKPZ").equals("")) {
            this.setYYKPZ(Tools.toUTF8(request.getParameter("YYKPZ")));
        }
        if (request.getParameter("YYKSHYJ") != null &&
                !request.getParameter("YYKSHYJ").equals("")) {
            this.setYYKSHYJ(Tools.toUTF8(request.getParameter("YYKSHYJ")));
        }
        if (request.getParameter("YYKSHPZ") != null &&
                !request.getParameter("YYKSHPZ").equals("")) {
            this.setYYKSHPZ(Tools.toUTF8(request.getParameter("YYKSHPZ")));
        }
        if (request.getParameter("JDKYJ") != null &&
                !request.getParameter("JDKYJ").equals("")) {
            this.setJDKYJ(Tools.toUTF8(request.getParameter("JDKYJ")));
        }
        if (request.getParameter("JDKPZ") != null &&
                !request.getParameter("JDKPZ").equals("")) {
            this.setJDKPZ(Tools.toUTF8(request.getParameter("JDKPZ")));
        }
        if (request.getParameter("JDKSH") != null &&
                !request.getParameter("JDKSH").equals("")) {
            this.setJDKSH(Tools.toUTF8(request.getParameter("JDKSH")));
        }
        if (request.getParameter("JDKSHPZ") != null &&
                !request.getParameter("JDKSHPZ").equals("")) {
            this.setJDKSHPZ(Tools.toUTF8(request.getParameter("JDKSHPZ")));
        }
        if (request.getParameter("ZDHKYJ") != null &&
                !request.getParameter("ZDHKYJ").equals("")) {
            this.setZDHKYJ(Tools.toUTF8(request.getParameter("ZDHKYJ")));
        }
        if (request.getParameter("ZDHKPZ") != null &&
                !request.getParameter("ZDHKPZ").equals("")) {
            this.setZDHKPZ(Tools.toUTF8(request.getParameter("ZDHKPZ")));
        }
        if (request.getParameter("LDYJ") != null &&
                !request.getParameter("LDYJ").equals("")) {
            this.setLDYJ(Tools.toUTF8(request.getParameter("LDYJ")));
        }
        if (request.getParameter("LDYJPZ") != null &&
                !request.getParameter("LDYJPZ").equals("")) {
            this.setLDYJPZ(Tools.toUTF8(request.getParameter("LDYJPZ")));
        }
        if (request.getParameter("DDKYJ") != null &&
                !request.getParameter("DDKYJ").equals("")) {
            this.setDDKYJ(Tools.toUTF8(request.getParameter("DDKYJ")));
        }
        if (request.getParameter("DDKPZ") != null &&
                !request.getParameter("DDKPZ").equals("")) {
            this.setDDKPZ(Tools.toUTF8(request.getParameter("DDKPZ")));
        }
        if (request.getParameter("TZSJ") != null &&
                !request.getParameter("TZSJ").equals("")) {
            this.setTZSJ(Tools.toUTF8(request.getParameter("TZSJ")));
        }
        if (request.getParameter("TZJLR") != null &&
                !request.getParameter("TZJLR").equals("")) {
            this.setTZJLR(Tools.toUTF8(request.getParameter("TZJLR")));
        }
        if (request.getParameter("TZSLR") != null &&
                !request.getParameter("TZSLR").equals("")) {
            this.setTZSLR(Tools.toUTF8(request.getParameter("TZSLR")));
        }
        if (request.getParameter("TZPZ") != null &&
                !request.getParameter("TZPZ").equals("")) {
            this.setTZPZ(Tools.toUTF8(request.getParameter("TZPZ")));
        }
        if (request.getParameter("TDSJ") != null &&
                !request.getParameter("TDSJ").equals("")) {
            this.setTDSJ(Tools.toUTF8(request.getParameter("TDSJ")));
        }
        if (request.getParameter("TDSLR") != null &&
                !request.getParameter("TDSLR").equals("")) {
            this.setTDSLR(Tools.toUTF8(request.getParameter("TDSLR")));
        }
        if (request.getParameter("TDDDY") != null &&
                !request.getParameter("TDDDY").equals("")) {
            this.setTDDDY(Tools.toUTF8(request.getParameter("TDDDY")));
        }
        if (request.getParameter("TDPZ") != null &&
                !request.getParameter("TDPZ").equals("")) {
            this.setTDPZ(Tools.toUTF8(request.getParameter("TDPZ")));
        }
        if (request.getParameter("KGSJ") != null &&
                !request.getParameter("KGSJ").equals("")) {
            this.setKGSJ(Tools.toUTF8(request.getParameter("KGSJ")));
        }
        if (request.getParameter("KGSLR") != null &&
                !request.getParameter("KGSLR").equals("")) {
            this.setKGSLR(Tools.toUTF8(request.getParameter("KGSLR")));
        }
        if (request.getParameter("KGDDY") != null &&
                !request.getParameter("KGDDY").equals("")) {
            this.setKGDDY(Tools.toUTF8(request.getParameter("KGDDY")));
        }
        if (request.getParameter("KGPZ") != null &&
                !request.getParameter("KGPZ").equals("")) {
            this.setKGPZ(Tools.toUTF8(request.getParameter("KGPZ")));
        }
        if (request.getParameter("WGSJ") != null &&
                !request.getParameter("WGSJ").equals("")) {
            this.setWGSJ(Tools.toUTF8(request.getParameter("WGSJ")));
        }
        if (request.getParameter("WGSLR") != null &&
                !request.getParameter("WGSLR").equals("")) {
            this.setWGSLR(Tools.toUTF8(request.getParameter("WGSLR")));
        }
        if (request.getParameter("WGDDY") != null &&
                !request.getParameter("WGDDY").equals("")) {
            this.setWGDDY(Tools.toUTF8(request.getParameter("WGDDY")));
        }
        if (request.getParameter("WGPZ") != null &&
                !request.getParameter("WGPZ").equals("")) {
            this.setWGPZ(Tools.toUTF8(request.getParameter("WGPZ")));
        }
        if (request.getParameter("FDSJ") != null &&
                !request.getParameter("FDSJ").equals("")) {
            this.setFDSJ(Tools.toUTF8(request.getParameter("FDSJ")));
        }
        if (request.getParameter("FDSLR") != null &&
                !request.getParameter("FDSLR").equals("")) {
            this.setFDSLR(Tools.toUTF8(request.getParameter("FDSLR")));
        }
        if (request.getParameter("FDDDY") != null &&
                !request.getParameter("FDDDY").equals("")) {
            this.setFDDDY(Tools.toUTF8(request.getParameter("FDDDY")));
        }
        if (request.getParameter("FDPZ") != null &&
                !request.getParameter("FDPZ").equals("")) {
            this.setFDPZ(Tools.toUTF8(request.getParameter("FDPZ")));
        }
        if (request.getParameter("YQSJ") != null &&
                !request.getParameter("YQSJ").equals("")) {
            this.setYQSJ(Tools.toUTF8(request.getParameter("YQSJ")));
        }
        if (request.getParameter("YQSQR") != null &&
                !request.getParameter("YQSQR").equals("")) {
            this.setYQSQR(Tools.toUTF8(request.getParameter("YQSQR")));
        }
        if (request.getParameter("YQPXR") != null &&
                !request.getParameter("YQPXR").equals("")) {
            this.setYQPXR(Tools.toUTF8(request.getParameter("YQPXR")));
        }
        if (request.getParameter("YQYY") != null &&
                !request.getParameter("YQYY").equals("")) {
            this.setYQYY(Tools.toUTF8(request.getParameter("YQYY")));
        }
        if (request.getParameter("YQPZ") != null &&
                !request.getParameter("YQPZ").equals("")) {
            this.setYQPZ(Tools.toUTF8(request.getParameter("YQPZ")));
        }
        if (request.getParameter("BZ") != null &&
                !request.getParameter("BZ").equals("")) {
            this.setBZ(Tools.toUTF8(request.getParameter("BZ")));
        }
    }

    public void BuildObject(ResultSet resultset) throws Exception {
        if (resultset.getString("PH") != null) {
            this.setPH(resultset.getInt("PH"));
        }

        if (resultset.getString("SQDW") != null) {
            this.setSQDW(resultset.getString("SQDW"));
        }

        if (resultset.getString("SQR") != null) {
            this.setSQR(resultset.getString("SQR"));
        }

        if (resultset.getString("TDLXR") != null) {
            this.setTDLXR(resultset.getString("TDLXR"));
        }

        if (resultset.getString("LXDH") != null) {
            this.setLXDH(resultset.getString("LXDH"));
        }

        if (resultset.getString("SBMC") != null) {
            this.setSBMC(resultset.getString("SBMC"));
        }

        if (resultset.getString("WZNR") != null) {
            this.setWZNR(resultset.getString("WZNR"));
        }

        if (resultset.getString("TDFW") != null) {
            this.setTDFW(resultset.getString("TDFW"));
        }

        if (resultset.getString("SQDWYJ") != null) {
            this.setSQDWYJ(resultset.getString("SQDWYJ"));
        }

        if (resultset.getString("JHGZSJQ") != null) {
            this.setJHGZSJQ(resultset.getString("JHGZSJQ"));
        }

        if (resultset.getString("JHGZSJZ") != null) {
            this.setJHGZSJZ(resultset.getString("JHGZSJZ"));
        }

        if (resultset.getString("BDWSH") != null) {
            this.setBDWSH(resultset.getString("BDWSH"));
        }

        if (resultset.getString("PZTHDSHQ") != null) {
            this.setPZTHDSHQ(resultset.getString("PZTHDSHQ"));
        }

        if (resultset.getString("PZTHDSHZ") != null) {
            this.setPZTHDSHZ(resultset.getString("PZTHDSHZ"));
        }

        if (resultset.getString("YYKYJ") != null) {
            this.setYYKYJ(resultset.getString("YYKYJ"));
        }

        if (resultset.getString("YYKPZ") != null) {
            this.setYYKPZ(resultset.getString("YYKPZ"));
        }

        if (resultset.getString("YYKSHYJ") != null) {
            this.setYYKSHYJ(resultset.getString("YYKSHYJ"));
        }

        if (resultset.getString("YYKSHPZ") != null) {
            this.setYYKSHPZ(resultset.getString("YYKSHPZ"));
        }

        if (resultset.getString("JDKYJ") != null) {
            this.setJDKYJ(resultset.getString("JDKYJ"));
        }

        if (resultset.getString("JDKPZ") != null) {
            this.setJDKPZ(resultset.getString("JDKPZ"));
        }

        if (resultset.getString("JDKSH") != null) {
            this.setJDKSH(resultset.getString("JDKSH"));
        }

        if (resultset.getString("JDKSHPZ") != null) {
            this.setJDKSHPZ(resultset.getString("JDKSHPZ"));
        }

        if (resultset.getString("ZDHKYJ") != null) {
            this.setZDHKYJ(resultset.getString("ZDHKYJ"));
        }

        if (resultset.getString("ZDHKPZ") != null) {
            this.setZDHKPZ(resultset.getString("ZDHKPZ"));
        }

        if (resultset.getString("LDYJ") != null) {
            this.setLDYJ(resultset.getString("LDYJ"));
        }

        if (resultset.getString("LDYJPZ") != null) {
            this.setLDYJPZ(resultset.getString("LDYJPZ"));
        }

        if (resultset.getString("DDKYJ") != null) {
            this.setDDKYJ(resultset.getString("DDKYJ"));
        }

        if (resultset.getString("DDKPZ") != null) {
            this.setDDKPZ(resultset.getString("DDKPZ"));
        }

        if (resultset.getString("TZSJ") != null) {
            this.setTZSJ(resultset.getString("TZSJ"));
        }

        if (resultset.getString("TZJLR") != null) {
            this.setTZJLR(resultset.getString("TZJLR"));
        }

        if (resultset.getString("TZSLR") != null) {
            this.setTZSLR(resultset.getString("TZSLR"));
        }

        if (resultset.getString("TZPZ") != null) {
            this.setTZPZ(resultset.getString("TZPZ"));
        }

        if (resultset.getString("TDSJ") != null) {
            this.setTDSJ(resultset.getString("TDSJ"));
        }

        if (resultset.getString("TDSLR") != null) {
            this.setTDSLR(resultset.getString("TDSLR"));
        }

        if (resultset.getString("TDDDY") != null) {
            this.setTDDDY(resultset.getString("TDDDY"));
        }

        if (resultset.getString("TDPZ") != null) {
            this.setTDPZ(resultset.getString("TDPZ"));
        }

        if (resultset.getString("KGSJ") != null) {
            this.setKGSJ(resultset.getString("KGSJ"));
        }

        if (resultset.getString("KGSLR") != null) {
            this.setKGSLR(resultset.getString("KGSLR"));
        }

        if (resultset.getString("KGDDY") != null) {
            this.setKGDDY(resultset.getString("KGDDY"));
        }

        if (resultset.getString("KGPZ") != null) {
            this.setKGPZ(resultset.getString("KGPZ"));
        }

        if (resultset.getString("WGSJ") != null) {
            this.setWGSJ(resultset.getString("WGSJ"));
        }

        if (resultset.getString("WGSLR") != null) {
            this.setWGSLR(resultset.getString("WGSLR"));
        }

        if (resultset.getString("WGDDY") != null) {
            this.setWGDDY(resultset.getString("WGDDY"));
        }

        if (resultset.getString("WGPZ") != null) {
            this.setWGPZ(resultset.getString("WGPZ"));
        }

        if (resultset.getString("FDSJ") != null) {
            this.setFDSJ(resultset.getString("FDSJ"));
        }

        if (resultset.getString("FDSLR") != null) {
            this.setFDSLR(resultset.getString("FDSLR"));
        }

        if (resultset.getString("FDDDY") != null) {
            this.setFDDDY(resultset.getString("FDDDY"));
        }

        if (resultset.getString("FDPZ") != null) {
            this.setFDPZ(resultset.getString("FDPZ"));
        }

        if (resultset.getString("YQSJ") != null) {
            this.setYQSJ(resultset.getString("YQSJ"));
        }

        if (resultset.getString("YQSQR") != null) {
            this.setYQSQR(resultset.getString("YQSQR"));
        }

        if (resultset.getString("YQPXR") != null) {
            this.setYQPXR(resultset.getString("YQPXR"));
        }

        if (resultset.getString("YQYY") != null) {
            this.setYQYY(resultset.getString("YQYY"));
        }

        if (resultset.getString("YQPZ") != null) {
            this.setYQPZ(resultset.getString("YQPZ"));
        }

        if (resultset.getString("BZ") != null) {
            this.setBZ(resultset.getString("BZ"));
        }

    }
    public List query() throws Exception {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultset = null;
        try {
            List listResult = new ArrayList();
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append("SELECT PH,SQDW,SQR,TDLXR,LXDH,SBMC,WZNR,TDFW,SQDWYJ,JHGZSJQ,JHGZSJZ,BDWSH,PZTHDSHQ,PZTHDSHZ,YYKYJ,YYKPZ,YYKSHYJ,YYKSHPZ,JDKYJ,JDKPZ,JDKSH,JDKSHPZ,ZDHKYJ,ZDHKPZ,LDYJ,LDYJPZ,DDKYJ,DDKPZ,TZSJ,TZJLR,TZSLR,TZPZ,TDSJ,TDSLR,TDDDY,TDPZ,KGSJ,KGSLR,KGDDY,KGPZ,WGSJ,WGSLR,WGDDY,WGPZ,FDSJ,FDSLR,FDDDY,FDPZ,YQSJ,YQSQR,YQPXR,YQYY,YQPZ,BZ FROM testjxp");
            StringBuffer sbWhere = new StringBuffer();
            if (!this.isEmptyPH()) {
                sbWhere.append("PH=");
                sbWhere.append(this.getPH());
                sbWhere.append(" AND ");
            }

            if (!this.isEmptySQDW()) {
                sbWhere.append("SQDW='");
                sbWhere.append(this.getSQDW());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptySQR()) {
                sbWhere.append("SQR='");
                sbWhere.append(this.getSQR());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyTDLXR()) {
                sbWhere.append("TDLXR='");
                sbWhere.append(this.getTDLXR());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyLXDH()) {
                sbWhere.append("LXDH='");
                sbWhere.append(this.getLXDH());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptySBMC()) {
                sbWhere.append("SBMC='");
                sbWhere.append(this.getSBMC());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyWZNR()) {
                sbWhere.append("WZNR='");
                sbWhere.append(this.getWZNR());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyTDFW()) {
                sbWhere.append("TDFW='");
                sbWhere.append(this.getTDFW());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptySQDWYJ()) {
                sbWhere.append("SQDWYJ='");
                sbWhere.append(this.getSQDWYJ());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyJHGZSJQ()) {
                sbWhere.append("JHGZSJQ='");
                sbWhere.append(this.getJHGZSJQ());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyJHGZSJZ()) {
                sbWhere.append("JHGZSJZ='");
                sbWhere.append(this.getJHGZSJZ());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyBDWSH()) {
                sbWhere.append("BDWSH='");
                sbWhere.append(this.getBDWSH());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyPZTHDSHQ()) {
                sbWhere.append("PZTHDSHQ='");
                sbWhere.append(this.getPZTHDSHQ());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyPZTHDSHZ()) {
                sbWhere.append("PZTHDSHZ='");
                sbWhere.append(this.getPZTHDSHZ());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyYYKYJ()) {
                sbWhere.append("YYKYJ='");
                sbWhere.append(this.getYYKYJ());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyYYKPZ()) {
                sbWhere.append("YYKPZ='");
                sbWhere.append(this.getYYKPZ());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyYYKSHYJ()) {
                sbWhere.append("YYKSHYJ='");
                sbWhere.append(this.getYYKSHYJ());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyYYKSHPZ()) {
                sbWhere.append("YYKSHPZ='");
                sbWhere.append(this.getYYKSHPZ());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyJDKYJ()) {
                sbWhere.append("JDKYJ='");
                sbWhere.append(this.getJDKYJ());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyJDKPZ()) {
                sbWhere.append("JDKPZ='");
                sbWhere.append(this.getJDKPZ());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyJDKSH()) {
                sbWhere.append("JDKSH='");
                sbWhere.append(this.getJDKSH());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyJDKSHPZ()) {
                sbWhere.append("JDKSHPZ='");
                sbWhere.append(this.getJDKSHPZ());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyZDHKYJ()) {
                sbWhere.append("ZDHKYJ='");
                sbWhere.append(this.getZDHKYJ());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyZDHKPZ()) {
                sbWhere.append("ZDHKPZ='");
                sbWhere.append(this.getZDHKPZ());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyLDYJ()) {
                sbWhere.append("LDYJ='");
                sbWhere.append(this.getLDYJ());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyLDYJPZ()) {
                sbWhere.append("LDYJPZ='");
                sbWhere.append(this.getLDYJPZ());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyDDKYJ()) {
                sbWhere.append("DDKYJ='");
                sbWhere.append(this.getDDKYJ());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyDDKPZ()) {
                sbWhere.append("DDKPZ='");
                sbWhere.append(this.getDDKPZ());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyTZSJ()) {
                sbWhere.append("TZSJ='");
                sbWhere.append(this.getTZSJ());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyTZJLR()) {
                sbWhere.append("TZJLR='");
                sbWhere.append(this.getTZJLR());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyTZSLR()) {
                sbWhere.append("TZSLR='");
                sbWhere.append(this.getTZSLR());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyTZPZ()) {
                sbWhere.append("TZPZ='");
                sbWhere.append(this.getTZPZ());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyTDSJ()) {
                sbWhere.append("TDSJ='");
                sbWhere.append(this.getTDSJ());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyTDSLR()) {
                sbWhere.append("TDSLR='");
                sbWhere.append(this.getTDSLR());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyTDDDY()) {
                sbWhere.append("TDDDY='");
                sbWhere.append(this.getTDDDY());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyTDPZ()) {
                sbWhere.append("TDPZ='");
                sbWhere.append(this.getTDPZ());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyKGSJ()) {
                sbWhere.append("KGSJ='");
                sbWhere.append(this.getKGSJ());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyKGSLR()) {
                sbWhere.append("KGSLR='");
                sbWhere.append(this.getKGSLR());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyKGDDY()) {
                sbWhere.append("KGDDY='");
                sbWhere.append(this.getKGDDY());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyKGPZ()) {
                sbWhere.append("KGPZ='");
                sbWhere.append(this.getKGPZ());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyWGSJ()) {
                sbWhere.append("WGSJ='");
                sbWhere.append(this.getWGSJ());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyWGSLR()) {
                sbWhere.append("WGSLR='");
                sbWhere.append(this.getWGSLR());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyWGDDY()) {
                sbWhere.append("WGDDY='");
                sbWhere.append(this.getWGDDY());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyWGPZ()) {
                sbWhere.append("WGPZ='");
                sbWhere.append(this.getWGPZ());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyFDSJ()) {
                sbWhere.append("FDSJ='");
                sbWhere.append(this.getFDSJ());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyFDSLR()) {
                sbWhere.append("FDSLR='");
                sbWhere.append(this.getFDSLR());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyFDDDY()) {
                sbWhere.append("FDDDY='");
                sbWhere.append(this.getFDDDY());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyFDPZ()) {
                sbWhere.append("FDPZ='");
                sbWhere.append(this.getFDPZ());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyYQSJ()) {
                sbWhere.append("YQSJ='");
                sbWhere.append(this.getYQSJ());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyYQSQR()) {
                sbWhere.append("YQSQR='");
                sbWhere.append(this.getYQSQR());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyYQPXR()) {
                sbWhere.append("YQPXR='");
                sbWhere.append(this.getYQPXR());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyYQYY()) {
                sbWhere.append("YQYY='");
                sbWhere.append(this.getYQYY());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyYQPZ()) {
                sbWhere.append("YQPZ='");
                sbWhere.append(this.getYQPZ());
                sbWhere.append("' AND ");
            }

            if (!this.isEmptyBZ()) {
                sbWhere.append("BZ='");
                sbWhere.append(this.getBZ());
                sbWhere.append("' AND ");
            }

            if (sbWhere.length() > 0) {
                sbWhere.insert(0, " WHERE ");
                sbWhere.delete(sbWhere.length() - 5, sbWhere.length());
                sbSQL.append(sbWhere.toString());
            }
            System.out.println("Jxp.query():"+sbSQL.toString());
            conn = Tools.getDBConn();
            statement = conn.createStatement();
            resultset = statement.executeQuery(sbSQL.toString());
            while (resultset.next()) {
                Jxp voResult = new Jxp();
                voResult.BuildObject(resultset);
                listResult.add(voResult);
            }
            return listResult;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (resultset != null) {
                resultset.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
    /**
     * 标题：插入数据库方法
     * 描述：实现IBizDao接口中insert()方法
     *      将新业务数据插入数据库，在工作流第一个动作时需要使用。
     * 返回值：1：成功，0：失败
     */
    public int insert(Connection conn) throws Exception {
        int intReslut = 0;
        Statement statement = null;
        try {
            StringBuffer sbSQL = new StringBuffer();
            StringBuffer sbInsertField = new StringBuffer();
            StringBuffer sbInsertValue = new StringBuffer();
            sbSQL.append("INSERT INTO testjxp (");
            if (!this.isEmptyPH()) {
                sbInsertField.append("PH");
                sbInsertField.append(",");
            }
            if (!this.isEmptySQDW()) {
                sbInsertField.append("SQDW");
                sbInsertField.append(",");
            }
            if (!this.isEmptySQR()) {
                sbInsertField.append("SQR");
                sbInsertField.append(",");
            }
            if (!this.isEmptyTDLXR()) {
                sbInsertField.append("TDLXR");
                sbInsertField.append(",");
            }
            if (!this.isEmptyLXDH()) {
                sbInsertField.append("LXDH");
                sbInsertField.append(",");
            }
            if (!this.isEmptySBMC()) {
                sbInsertField.append("SBMC");
                sbInsertField.append(",");
            }
            if (!this.isEmptyWZNR()) {
                sbInsertField.append("WZNR");
                sbInsertField.append(",");
            }
            if (!this.isEmptyTDFW()) {
                sbInsertField.append("TDFW");
                sbInsertField.append(",");
            }
            if (!this.isEmptySQDWYJ()) {
                sbInsertField.append("SQDWYJ");
                sbInsertField.append(",");
            }
            if (!this.isEmptyJHGZSJQ()) {
                sbInsertField.append("JHGZSJQ");
                sbInsertField.append(",");
            }
            if (!this.isEmptyJHGZSJZ()) {
                sbInsertField.append("JHGZSJZ");
                sbInsertField.append(",");
            }
            if (!this.isEmptyBDWSH()) {
                sbInsertField.append("BDWSH");
                sbInsertField.append(",");
            }
            if (!this.isEmptyPZTHDSHQ()) {
                sbInsertField.append("PZTHDSHQ");
                sbInsertField.append(",");
            }
            if (!this.isEmptyPZTHDSHZ()) {
                sbInsertField.append("PZTHDSHZ");
                sbInsertField.append(",");
            }
            if (!this.isEmptyYYKYJ()) {
                sbInsertField.append("YYKYJ");
                sbInsertField.append(",");
            }
            if (!this.isEmptyYYKPZ()) {
                sbInsertField.append("YYKPZ");
                sbInsertField.append(",");
            }
            if (!this.isEmptyYYKSHYJ()) {
                sbInsertField.append("YYKSHYJ");
                sbInsertField.append(",");
            }
            if (!this.isEmptyYYKSHPZ()) {
                sbInsertField.append("YYKSHPZ");
                sbInsertField.append(",");
            }
            if (!this.isEmptyJDKYJ()) {
                sbInsertField.append("JDKYJ");
                sbInsertField.append(",");
            }
            if (!this.isEmptyJDKPZ()) {
                sbInsertField.append("JDKPZ");
                sbInsertField.append(",");
            }
            if (!this.isEmptyJDKSH()) {
                sbInsertField.append("JDKSH");
                sbInsertField.append(",");
            }
            if (!this.isEmptyJDKSHPZ()) {
                sbInsertField.append("JDKSHPZ");
                sbInsertField.append(",");
            }
            if (!this.isEmptyZDHKYJ()) {
                sbInsertField.append("ZDHKYJ");
                sbInsertField.append(",");
            }
            if (!this.isEmptyZDHKPZ()) {
                sbInsertField.append("ZDHKPZ");
                sbInsertField.append(",");
            }
            if (!this.isEmptyLDYJ()) {
                sbInsertField.append("LDYJ");
                sbInsertField.append(",");
            }
            if (!this.isEmptyLDYJPZ()) {
                sbInsertField.append("LDYJPZ");
                sbInsertField.append(",");
            }
            if (!this.isEmptyDDKYJ()) {
                sbInsertField.append("DDKYJ");
                sbInsertField.append(",");
            }
            if (!this.isEmptyDDKPZ()) {
                sbInsertField.append("DDKPZ");
                sbInsertField.append(",");
            }
            if (!this.isEmptyTZSJ()) {
                sbInsertField.append("TZSJ");
                sbInsertField.append(",");
            }
            if (!this.isEmptyTZJLR()) {
                sbInsertField.append("TZJLR");
                sbInsertField.append(",");
            }
            if (!this.isEmptyTZSLR()) {
                sbInsertField.append("TZSLR");
                sbInsertField.append(",");
            }
            if (!this.isEmptyTZPZ()) {
                sbInsertField.append("TZPZ");
                sbInsertField.append(",");
            }
            if (!this.isEmptyTDSJ()) {
                sbInsertField.append("TDSJ");
                sbInsertField.append(",");
            }
            if (!this.isEmptyTDSLR()) {
                sbInsertField.append("TDSLR");
                sbInsertField.append(",");
            }
            if (!this.isEmptyTDDDY()) {
                sbInsertField.append("TDDDY");
                sbInsertField.append(",");
            }
            if (!this.isEmptyTDPZ()) {
                sbInsertField.append("TDPZ");
                sbInsertField.append(",");
            }
            if (!this.isEmptyKGSJ()) {
                sbInsertField.append("KGSJ");
                sbInsertField.append(",");
            }
            if (!this.isEmptyKGSLR()) {
                sbInsertField.append("KGSLR");
                sbInsertField.append(",");
            }
            if (!this.isEmptyKGDDY()) {
                sbInsertField.append("KGDDY");
                sbInsertField.append(",");
            }
            if (!this.isEmptyKGPZ()) {
                sbInsertField.append("KGPZ");
                sbInsertField.append(",");
            }
            if (!this.isEmptyWGSJ()) {
                sbInsertField.append("WGSJ");
                sbInsertField.append(",");
            }
            if (!this.isEmptyWGSLR()) {
                sbInsertField.append("WGSLR");
                sbInsertField.append(",");
            }
            if (!this.isEmptyWGDDY()) {
                sbInsertField.append("WGDDY");
                sbInsertField.append(",");
            }
            if (!this.isEmptyWGPZ()) {
                sbInsertField.append("WGPZ");
                sbInsertField.append(",");
            }
            if (!this.isEmptyFDSJ()) {
                sbInsertField.append("FDSJ");
                sbInsertField.append(",");
            }
            if (!this.isEmptyFDSLR()) {
                sbInsertField.append("FDSLR");
                sbInsertField.append(",");
            }
            if (!this.isEmptyFDDDY()) {
                sbInsertField.append("FDDDY");
                sbInsertField.append(",");
            }
            if (!this.isEmptyFDPZ()) {
                sbInsertField.append("FDPZ");
                sbInsertField.append(",");
            }
            if (!this.isEmptyYQSJ()) {
                sbInsertField.append("YQSJ");
                sbInsertField.append(",");
            }
            if (!this.isEmptyYQSQR()) {
                sbInsertField.append("YQSQR");
                sbInsertField.append(",");
            }
            if (!this.isEmptyYQPXR()) {
                sbInsertField.append("YQPXR");
                sbInsertField.append(",");
            }
            if (!this.isEmptyYQYY()) {
                sbInsertField.append("YQYY");
                sbInsertField.append(",");
            }
            if (!this.isEmptyYQPZ()) {
                sbInsertField.append("YQPZ");
                sbInsertField.append(",");
            }
            if (!this.isEmptyBZ()) {
                sbInsertField.append("BZ");
                sbInsertField.append(",");
            }
            if (sbInsertField.length() > 0) {
                sbInsertField.delete(sbInsertField.length() - 1, sbInsertField.length());
                sbSQL.append(sbInsertField.toString());
            }
            else {
                throw new Exception("Jxp|insert|NoPara|执行Jxp.insert()方法时发生参数不足异常，没有设置可更新字段");
            }
            sbSQL.append(") VALUES (");
            if (!this.isEmptyPH()) {
                sbInsertValue.append(intPH);
                sbInsertValue.append(",");
            }
            if (!this.isEmptySQDW()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strSQDW);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptySQR()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strSQR);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyTDLXR()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strTDLXR);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyLXDH()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strLXDH);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptySBMC()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strSBMC);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyWZNR()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strWZNR);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyTDFW()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strTDFW);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptySQDWYJ()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strSQDWYJ);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyJHGZSJQ()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strJHGZSJQ);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyJHGZSJZ()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strJHGZSJZ);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyBDWSH()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strBDWSH);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyPZTHDSHQ()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strPZTHDSHQ);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyPZTHDSHZ()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strPZTHDSHZ);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyYYKYJ()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strYYKYJ);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyYYKPZ()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strYYKPZ);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyYYKSHYJ()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strYYKSHYJ);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyYYKSHPZ()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strYYKSHPZ);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyJDKYJ()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strJDKYJ);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyJDKPZ()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strJDKPZ);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyJDKSH()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strJDKSH);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyJDKSHPZ()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strJDKSHPZ);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyZDHKYJ()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strZDHKYJ);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyZDHKPZ()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strZDHKPZ);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyLDYJ()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strLDYJ);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyLDYJPZ()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strLDYJPZ);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyDDKYJ()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strDDKYJ);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyDDKPZ()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strDDKPZ);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyTZSJ()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strTZSJ);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyTZJLR()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strTZJLR);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyTZSLR()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strTZSLR);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyTZPZ()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strTZPZ);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyTDSJ()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strTDSJ);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyTDSLR()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strTDSLR);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyTDDDY()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strTDDDY);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyTDPZ()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strTDPZ);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyKGSJ()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strKGSJ);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyKGSLR()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strKGSLR);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyKGDDY()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strKGDDY);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyKGPZ()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strKGPZ);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyWGSJ()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strWGSJ);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyWGSLR()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strWGSLR);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyWGDDY()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strWGDDY);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyWGPZ()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strWGPZ);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyFDSJ()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strFDSJ);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyFDSLR()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strFDSLR);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyFDDDY()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strFDDDY);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyFDPZ()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strFDPZ);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyYQSJ()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strYQSJ);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyYQSQR()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strYQSQR);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyYQPXR()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strYQPXR);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyYQYY()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strYQYY);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyYQPZ()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strYQPZ);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (!this.isEmptyBZ()) {
                sbInsertValue.append("'");
                sbInsertValue.append(strBZ);
                sbInsertValue.append("'");
                sbInsertValue.append(",");
            }
            if (sbInsertValue.length() > 0) {
                sbInsertValue.delete(sbInsertValue.length() - 1, sbInsertValue.length());
                sbSQL.append(sbInsertValue.toString());
            }
            else {
                throw new Exception("Jxp|insert|NoPara|执行Jxp.insert()方法时发生参数不足异常，没有设置可更新字段");
            }
            sbSQL.append(")");
            System.out.println("Jxp.insert():" + sbSQL.toString());
            statement = conn.createStatement();
            intReslut = statement.executeUpdate(sbSQL.toString());
            return intReslut;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    /**
     * 标题：更新数据库方法
     * 描述：实现IBizDao接口update方法
     *      更新业务数据，在工作流流转当中，需要对业务数据进行更新等操作.
     * 返回值：1：成功，0：失败
     */
    public int update(Connection conn) throws Exception {
        Statement statement = null;
        try {
            int intResult = 0;
            StringBuffer sbSQL = new StringBuffer();
            StringBuffer sbSet = new StringBuffer();
            sbSQL.append("UPDATE testjxp SET ");
            if (!isEmptySQDW()) {
                sbSet.append("SQDW=");
                sbSet.append("'");
                sbSet.append(this.getSQDW());
                sbSet.append("',");
            }
            if (!isEmptySQR()) {
                sbSet.append("SQR=");
                sbSet.append("'");
                sbSet.append(this.getSQR());
                sbSet.append("',");
            }
            if (!isEmptyTDLXR()) {
                sbSet.append("TDLXR=");
                sbSet.append("'");
                sbSet.append(this.getTDLXR());
                sbSet.append("',");
            }
            if (!isEmptyLXDH()) {
                sbSet.append("LXDH=");
                sbSet.append("'");
                sbSet.append(this.getLXDH());
                sbSet.append("',");
            }
            if (!isEmptySBMC()) {
                sbSet.append("SBMC=");
                sbSet.append("'");
                sbSet.append(this.getSBMC());
                sbSet.append("',");
            }
            if (!isEmptyWZNR()) {
                sbSet.append("WZNR=");
                sbSet.append("'");
                sbSet.append(this.getWZNR());
                sbSet.append("',");
            }
            if (!isEmptyTDFW()) {
                sbSet.append("TDFW=");
                sbSet.append("'");
                sbSet.append(this.getTDFW());
                sbSet.append("',");
            }
            if (!isEmptySQDWYJ()) {
                sbSet.append("SQDWYJ=");
                sbSet.append("'");
                sbSet.append(this.getSQDWYJ());
                sbSet.append("',");
            }
            if (!isEmptyJHGZSJQ()) {
                sbSet.append("JHGZSJQ=");
                sbSet.append("'");
                sbSet.append(this.getJHGZSJQ());
                sbSet.append("',");
            }
            if (!isEmptyJHGZSJZ()) {
                sbSet.append("JHGZSJZ=");
                sbSet.append("'");
                sbSet.append(this.getJHGZSJZ());
                sbSet.append("',");
            }
            if (!isEmptyBDWSH()) {
                sbSet.append("BDWSH=");
                sbSet.append("'");
                sbSet.append(this.getBDWSH());
                sbSet.append("',");
            }
            if (!isEmptyPZTHDSHQ()) {
                sbSet.append("PZTHDSHQ=");
                sbSet.append("'");
                sbSet.append(this.getPZTHDSHQ());
                sbSet.append("',");
            }
            if (!isEmptyPZTHDSHZ()) {
                sbSet.append("PZTHDSHZ=");
                sbSet.append("'");
                sbSet.append(this.getPZTHDSHZ());
                sbSet.append("',");
            }
            if (!isEmptyYYKYJ()) {
                sbSet.append("YYKYJ=");
                sbSet.append("'");
                sbSet.append(this.getYYKYJ());
                sbSet.append("',");
            }
            if (!isEmptyYYKPZ()) {
                sbSet.append("YYKPZ=");
                sbSet.append("'");
                sbSet.append(this.getYYKPZ());
                sbSet.append("',");
            }
            if (!isEmptyYYKSHYJ()) {
                sbSet.append("YYKSHYJ=");
                sbSet.append("'");
                sbSet.append(this.getYYKSHYJ());
                sbSet.append("',");
            }
            if (!isEmptyYYKSHPZ()) {
                sbSet.append("YYKSHPZ=");
                sbSet.append("'");
                sbSet.append(this.getYYKSHPZ());
                sbSet.append("',");
            }
            if (!isEmptyJDKYJ()) {
                sbSet.append("JDKYJ=");
                sbSet.append("'");
                sbSet.append(this.getJDKYJ());
                sbSet.append("',");
            }
            if (!isEmptyJDKPZ()) {
                sbSet.append("JDKPZ=");
                sbSet.append("'");
                sbSet.append(this.getJDKPZ());
                sbSet.append("',");
            }
            if (!isEmptyJDKSH()) {
                sbSet.append("JDKSH=");
                sbSet.append("'");
                sbSet.append(this.getJDKSH());
                sbSet.append("',");
            }
            if (!isEmptyJDKSHPZ()) {
                sbSet.append("JDKSHPZ=");
                sbSet.append("'");
                sbSet.append(this.getJDKSHPZ());
                sbSet.append("',");
            }
            if (!isEmptyZDHKYJ()) {
                sbSet.append("ZDHKYJ=");
                sbSet.append("'");
                sbSet.append(this.getZDHKYJ());
                sbSet.append("',");
            }
            if (!isEmptyZDHKPZ()) {
                sbSet.append("ZDHKPZ=");
                sbSet.append("'");
                sbSet.append(this.getZDHKPZ());
                sbSet.append("',");
            }
            if (!isEmptyLDYJ()) {
                sbSet.append("LDYJ=");
                sbSet.append("'");
                sbSet.append(this.getLDYJ());
                sbSet.append("',");
            }
            if (!isEmptyLDYJPZ()) {
                sbSet.append("LDYJPZ=");
                sbSet.append("'");
                sbSet.append(this.getLDYJPZ());
                sbSet.append("',");
            }
            if (!isEmptyDDKYJ()) {
                sbSet.append("DDKYJ=");
                sbSet.append("'");
                sbSet.append(this.getDDKYJ());
                sbSet.append("',");
            }
            if (!isEmptyDDKPZ()) {
                sbSet.append("DDKPZ=");
                sbSet.append("'");
                sbSet.append(this.getDDKPZ());
                sbSet.append("',");
            }
            if (!isEmptyTZSJ()) {
                sbSet.append("TZSJ=");
                sbSet.append("'");
                sbSet.append(this.getTZSJ());
                sbSet.append("',");
            }
            if (!isEmptyTZJLR()) {
                sbSet.append("TZJLR=");
                sbSet.append("'");
                sbSet.append(this.getTZJLR());
                sbSet.append("',");
            }
            if (!isEmptyTZSLR()) {
                sbSet.append("TZSLR=");
                sbSet.append("'");
                sbSet.append(this.getTZSLR());
                sbSet.append("',");
            }
            if (!isEmptyTZPZ()) {
                sbSet.append("TZPZ=");
                sbSet.append("'");
                sbSet.append(this.getTZPZ());
                sbSet.append("',");
            }
            if (!isEmptyTDSJ()) {
                sbSet.append("TDSJ=");
                sbSet.append("'");
                sbSet.append(this.getTDSJ());
                sbSet.append("',");
            }
            if (!isEmptyTDSLR()) {
                sbSet.append("TDSLR=");
                sbSet.append("'");
                sbSet.append(this.getTDSLR());
                sbSet.append("',");
            }
            if (!isEmptyTDDDY()) {
                sbSet.append("TDDDY=");
                sbSet.append("'");
                sbSet.append(this.getTDDDY());
                sbSet.append("',");
            }
            if (!isEmptyTDPZ()) {
                sbSet.append("TDPZ=");
                sbSet.append("'");
                sbSet.append(this.getTDPZ());
                sbSet.append("',");
            }
            if (!isEmptyKGSJ()) {
                sbSet.append("KGSJ=");
                sbSet.append("'");
                sbSet.append(this.getKGSJ());
                sbSet.append("',");
            }
            if (!isEmptyKGSLR()) {
                sbSet.append("KGSLR=");
                sbSet.append("'");
                sbSet.append(this.getKGSLR());
                sbSet.append("',");
            }
            if (!isEmptyKGDDY()) {
                sbSet.append("KGDDY=");
                sbSet.append("'");
                sbSet.append(this.getKGDDY());
                sbSet.append("',");
            }
            if (!isEmptyKGPZ()) {
                sbSet.append("KGPZ=");
                sbSet.append("'");
                sbSet.append(this.getKGPZ());
                sbSet.append("',");
            }
            if (!isEmptyWGSJ()) {
                sbSet.append("WGSJ=");
                sbSet.append("'");
                sbSet.append(this.getWGSJ());
                sbSet.append("',");
            }
            if (!isEmptyWGSLR()) {
                sbSet.append("WGSLR=");
                sbSet.append("'");
                sbSet.append(this.getWGSLR());
                sbSet.append("',");
            }
            if (!isEmptyWGDDY()) {
                sbSet.append("WGDDY=");
                sbSet.append("'");
                sbSet.append(this.getWGDDY());
                sbSet.append("',");
            }
            if (!isEmptyWGPZ()) {
                sbSet.append("WGPZ=");
                sbSet.append("'");
                sbSet.append(this.getWGPZ());
                sbSet.append("',");
            }
            if (!isEmptyFDSJ()) {
                sbSet.append("FDSJ=");
                sbSet.append("'");
                sbSet.append(this.getFDSJ());
                sbSet.append("',");
            }
            if (!isEmptyFDSLR()) {
                sbSet.append("FDSLR=");
                sbSet.append("'");
                sbSet.append(this.getFDSLR());
                sbSet.append("',");
            }
            if (!isEmptyFDDDY()) {
                sbSet.append("FDDDY=");
                sbSet.append("'");
                sbSet.append(this.getFDDDY());
                sbSet.append("',");
            }
            if (!isEmptyFDPZ()) {
                sbSet.append("FDPZ=");
                sbSet.append("'");
                sbSet.append(this.getFDPZ());
                sbSet.append("',");
            }
            if (!isEmptyYQSJ()) {
                sbSet.append("YQSJ=");
                sbSet.append("'");
                sbSet.append(this.getYQSJ());
                sbSet.append("',");
            }
            if (!isEmptyYQSQR()) {
                sbSet.append("YQSQR=");
                sbSet.append("'");
                sbSet.append(this.getYQSQR());
                sbSet.append("',");
            }
            if (!isEmptyYQPXR()) {
                sbSet.append("YQPXR=");
                sbSet.append("'");
                sbSet.append(this.getYQPXR());
                sbSet.append("',");
            }
            if (!isEmptyYQYY()) {
                sbSet.append("YQYY=");
                sbSet.append("'");
                sbSet.append(this.getYQYY());
                sbSet.append("',");
            }
            if (!isEmptyYQPZ()) {
                sbSet.append("YQPZ=");
                sbSet.append("'");
                sbSet.append(this.getYQPZ());
                sbSet.append("',");
            }
            if (!isEmptyBZ()) {
                sbSet.append("BZ=");
                sbSet.append("'");
                sbSet.append(this.getBZ());
                sbSet.append("',");
            }
            if (sbSet.length() > 0) {
                sbSet.delete(sbSet.length() - 1, sbSet.length());
                sbSQL.append(sbSet.toString());
            }
            else {
                throw new Exception("Jxp|update|NoPara|执行Jxp.update()方法发生参数不足异常，没有设置可跟新字段");
            }

            sbSQL.append(" WHERE ");
            sbSQL.append("PH=");
            sbSQL.append(intPH);
            System.out.println("Jxp.Update():" + sbSQL.toString());
            statement = conn.createStatement();
            intResult = statement.executeUpdate(sbSQL.toString());
            return intResult;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (statement != null ) {
                statement.close();
            }
        }
    }

    /**
     * 标题：删除数据库方法
     * 描述：实现IBizDao接口中delete方法
     *      根据业务编号，删除数据库信息
     * 返回值：1：成功，0：失败
     */
    public int delete(Connection conn) throws Exception {
        Statement statement = null;
        try {
            int intResult = 0;
            StringBuffer sbSQL = new StringBuffer();
            StringBuffer southere = new StringBuffer();
            sbSQL.append("DELETE testjxp");
            sbSQL.append(" WHERE ");
            sbSQL.append("PH=");
            sbSQL.append(intPH);
            System.out.println("Jxp.Delete():" + sbSQL.toString());
            statement = conn.createStatement();
            intResult = statement.executeUpdate(sbSQL.toString());
            return intResult;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (statement != null ) {
                statement.close();
            }
        }
    }

    /**
     * 标题：获得业务编号方法
     * 描述：获得业务编号
     * 返回值：String
     */
    public String getYWID() {
        return String.valueOf(intPH);
    }

    /**
     * 标题：设置业务编号方法
     * 描述：设置业务编号
     * 返回值：void
     */
    public void setYWID(String YWID) {
        intPH = Integer.parseInt(YWID);
    }

    /**
     * 标题：判断业务编号是否为空
     * 描述：判断业务编号是否为空
     * 返回值：真：业务编号为空，假：业务编号不为空
     */
    public boolean isEmptyYWID() {
        if (intPH == Integer.MAX_VALUE) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 标题：SatisfyForwardCondition方法
     * 描述：根据Condition判断是否满足节点的进入（离开）条件
     * 返回值：真：满足条件，假：不满足条件
     */
    public boolean SatisfyForwardCondition(String Condition,Connection conn) throws Exception {
        Statement statement = null;
        ResultSet resultset = null;
        boolean boolResult = false;
        try {
            StringBuffer sbSQL = new StringBuffer();
            StringBuffer southere = new StringBuffer();
            sbSQL.append("SELECT * FROM testjxp");

            if (Condition != null && !Condition.equals("")) {
                southere.append(Condition);
                southere.append(" AND ");
            }
            if (!this.isEmptyYWID()) {
                southere.append("PH=");
                southere.append(this.getPH());
                southere.append(" AND ");
            }
            else {
                throw new Exception("Jxp|SatisfyForwardCondition|NoPara|执行Jxp.SatisfyForwardCondition()方法时发生参数不足异常，无法获得业务编号(YWID)");
            }
            if (southere.length() > 0) {
                southere.insert(0, " WHERE ");
                southere.delete(southere.length() - 5, southere.length());
                sbSQL.append(southere.toString());
            }
            System.out.println("Jxp.SatisfyForwardCondition():"+sbSQL.toString());
            statement = conn.createStatement();
            resultset = statement.executeQuery(sbSQL.toString());
            if (resultset.next()) {
                boolResult = true;
            }
            return boolResult;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (resultset != null) {
                resultset.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
    }

    /**
     * 标题：DataSnapShot方法
     * 描述：获得数据库中相应的值，用于保存历史数据
     * 返回值：HashMap
     */
    public HashMap DataSnapShot(Connection conn) throws Exception {
        Statement statement = null;
        ResultSet resultset = null;
        try {
            HashMap hm = new HashMap();
            StringBuffer sbSQL = new StringBuffer();
            sbSQL.append("SELECT PH,SQDW,SQR,TDLXR,LXDH,SBMC,WZNR,TDFW,SQDWYJ,JHGZSJQ,JHGZSJZ,BDWSH,PZTHDSHQ,PZTHDSHZ,YYKYJ,YYKPZ,YYKSHYJ,YYKSHPZ,JDKYJ,JDKPZ,JDKSH,JDKSHPZ,ZDHKYJ,ZDHKPZ,LDYJ,LDYJPZ,DDKYJ,DDKPZ,TZSJ,TZJLR,TZSLR,TZPZ,TDSJ,TDSLR,TDDDY,TDPZ,KGSJ,KGSLR,KGDDY,KGPZ,WGSJ,WGSLR,WGDDY,WGPZ,FDSJ,FDSLR,FDDDY,FDPZ,YQSJ,YQSQR,YQPXR,YQYY,YQPZ,BZ FROM testjxp WHERE ");
            if (!isEmptyYWID()) {
                sbSQL.append("PH=");
                sbSQL.append(intPH);
            }
            else {
                throw new Exception("Jxp|DataSnapShot|执行Jxp.DataSnapShot()方法时发生参数不足异常，无法获得业务编号(YWID)");
            }
            System.out.println("Jxp.DataSnapShot():"+sbSQL.toString());
            statement = conn.createStatement();
            resultset = statement.executeQuery(sbSQL.toString());
            if (resultset.next()) {
                hm.put("PH", resultset.getString("PH"));
                hm.put("SQDW", resultset.getString("SQDW"));
                hm.put("SQR", resultset.getString("SQR"));
                hm.put("TDLXR", resultset.getString("TDLXR"));
                hm.put("LXDH", resultset.getString("LXDH"));
                hm.put("SBMC", resultset.getString("SBMC"));
                hm.put("WZNR", resultset.getString("WZNR"));
                hm.put("TDFW", resultset.getString("TDFW"));
                hm.put("SQDWYJ", resultset.getString("SQDWYJ"));
                hm.put("JHGZSJQ", resultset.getString("JHGZSJQ"));
                hm.put("JHGZSJZ", resultset.getString("JHGZSJZ"));
                hm.put("BDWSH", resultset.getString("BDWSH"));
                hm.put("PZTHDSHQ", resultset.getString("PZTHDSHQ"));
                hm.put("PZTHDSHZ", resultset.getString("PZTHDSHZ"));
                hm.put("YYKYJ", resultset.getString("YYKYJ"));
                hm.put("YYKPZ", resultset.getString("YYKPZ"));
                hm.put("YYKSHYJ", resultset.getString("YYKSHYJ"));
                hm.put("YYKSHPZ", resultset.getString("YYKSHPZ"));
                hm.put("JDKYJ", resultset.getString("JDKYJ"));
                hm.put("JDKPZ", resultset.getString("JDKPZ"));
                hm.put("JDKSH", resultset.getString("JDKSH"));
                hm.put("JDKSHPZ", resultset.getString("JDKSHPZ"));
                hm.put("ZDHKYJ", resultset.getString("ZDHKYJ"));
                hm.put("ZDHKPZ", resultset.getString("ZDHKPZ"));
                hm.put("LDYJ", resultset.getString("LDYJ"));
                hm.put("LDYJPZ", resultset.getString("LDYJPZ"));
                hm.put("DDKYJ", resultset.getString("DDKYJ"));
                hm.put("DDKPZ", resultset.getString("DDKPZ"));
                hm.put("TZSJ", resultset.getString("TZSJ"));
                hm.put("TZJLR", resultset.getString("TZJLR"));
                hm.put("TZSLR", resultset.getString("TZSLR"));
                hm.put("TZPZ", resultset.getString("TZPZ"));
                hm.put("TDSJ", resultset.getString("TDSJ"));
                hm.put("TDSLR", resultset.getString("TDSLR"));
                hm.put("TDDDY", resultset.getString("TDDDY"));
                hm.put("TDPZ", resultset.getString("TDPZ"));
                hm.put("KGSJ", resultset.getString("KGSJ"));
                hm.put("KGSLR", resultset.getString("KGSLR"));
                hm.put("KGDDY", resultset.getString("KGDDY"));
                hm.put("KGPZ", resultset.getString("KGPZ"));
                hm.put("WGSJ", resultset.getString("WGSJ"));
                hm.put("WGSLR", resultset.getString("WGSLR"));
                hm.put("WGDDY", resultset.getString("WGDDY"));
                hm.put("WGPZ", resultset.getString("WGPZ"));
                hm.put("FDSJ", resultset.getString("FDSJ"));
                hm.put("FDSLR", resultset.getString("FDSLR"));
                hm.put("FDDDY", resultset.getString("FDDDY"));
                hm.put("FDPZ", resultset.getString("FDPZ"));
                hm.put("YQSJ", resultset.getString("YQSJ"));
                hm.put("YQSQR", resultset.getString("YQSQR"));
                hm.put("YQPXR", resultset.getString("YQPXR"));
                hm.put("YQYY", resultset.getString("YQYY"));
                hm.put("YQPZ", resultset.getString("YQPZ"));
                hm.put("BZ", resultset.getString("BZ"));
            }
            return hm;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (resultset != null) {
                resultset.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
    }

    /**
     * 标题：DataSnapShot方法
     * 描述：获得数据库中相应的值，用于保存历史数据
     * 返回值：HashMap
     */
    public HashMap DataSnapShot() throws Exception {
        Connection conn = null;
        try {
            conn = Tools.getDBConn();
            return this.DataSnapShot(conn);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * 获得票号
     */
    public int getPH() {
        return intPH;
    }
    /**
     * 设置票号
     */
    public void setPH(int PH) {
        intPH = PH;
    }

    /**
     *判断票号是否为空
     */
    public boolean isEmptyPH() {
        if (  intPH == Integer.MAX_VALUE) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 获得申请单位
     */
    public String getSQDW() {
        return strSQDW;
    }
    /**
     * 设置申请单位
     */
    public void setSQDW(String SQDW) {
        strSQDW = SQDW;
    }

    /**
     *判断申请单位是否为空
     */
    public boolean isEmptySQDW() {
        if (strSQDW != null && !strSQDW.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得申请人
     */
    public String getSQR() {
        return strSQR;
    }
    /**
     * 设置申请人
     */
    public void setSQR(String SQR) {
        strSQR = SQR;
    }

    /**
     *判断申请人是否为空
     */
    public boolean isEmptySQR() {
        if (strSQR != null && !strSQR.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得停电联系人
     */
    public String getTDLXR() {
        return strTDLXR;
    }
    /**
     * 设置停电联系人
     */
    public void setTDLXR(String TDLXR) {
        strTDLXR = TDLXR;
    }

    /**
     *判断停电联系人是否为空
     */
    public boolean isEmptyTDLXR() {
        if (strTDLXR != null && !strTDLXR.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得联系电话
     */
    public String getLXDH() {
        return strLXDH;
    }
    /**
     * 设置联系电话
     */
    public void setLXDH(String LXDH) {
        strLXDH = LXDH;
    }

    /**
     *判断联系电话是否为空
     */
    public boolean isEmptyLXDH() {
        if (strLXDH != null && !strLXDH.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得设备名称
     */
    public String getSBMC() {
        return strSBMC;
    }
    /**
     * 设置设备名称
     */
    public void setSBMC(String SBMC) {
        strSBMC = SBMC;
    }

    /**
     *判断设备名称是否为空
     */
    public boolean isEmptySBMC() {
        if (strSBMC != null && !strSBMC.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得工作内容
     */
    public String getWZNR() {
        return strWZNR;
    }
    /**
     * 设置工作内容
     */
    public void setWZNR(String WZNR) {
        strWZNR = WZNR;
    }

    /**
     *判断工作内容是否为空
     */
    public boolean isEmptyWZNR() {
        if (strWZNR != null && !strWZNR.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得停电范围
     */
    public String getTDFW() {
        return strTDFW;
    }
    /**
     * 设置停电范围
     */
    public void setTDFW(String TDFW) {
        strTDFW = TDFW;
    }

    /**
     *判断停电范围是否为空
     */
    public boolean isEmptyTDFW() {
        if (strTDFW != null && !strTDFW.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得申请单位意见
     */
    public String getSQDWYJ() {
        return strSQDWYJ;
    }
    /**
     * 设置申请单位意见
     */
    public void setSQDWYJ(String SQDWYJ) {
        strSQDWYJ = SQDWYJ;
    }

    /**
     *判断申请单位意见是否为空
     */
    public boolean isEmptySQDWYJ() {
        if (strSQDWYJ != null && !strSQDWYJ.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得申请停（复）电时间起
     */
    public String getJHGZSJQ() {
        return strJHGZSJQ;
    }
    /**
     * 设置申请停（复）电时间起
     */
    public void setJHGZSJQ(String JHGZSJQ) {
        strJHGZSJQ = JHGZSJQ;
    }

    /**
     *判断申请停（复）电时间起是否为空
     */
    public boolean isEmptyJHGZSJQ() {
        if (strJHGZSJQ != null && !strJHGZSJQ.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得申请停（复）电时间止
     */
    public String getJHGZSJZ() {
        return strJHGZSJZ;
    }
    /**
     * 设置申请停（复）电时间止
     */
    public void setJHGZSJZ(String JHGZSJZ) {
        strJHGZSJZ = JHGZSJZ;
    }

    /**
     *判断申请停（复）电时间止是否为空
     */
    public boolean isEmptyJHGZSJZ() {
        if (strJHGZSJZ != null && !strJHGZSJZ.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得本单位审批
     */
    public String getBDWSH() {
        return strBDWSH;
    }
    /**
     * 设置本单位审批
     */
    public void setBDWSH(String BDWSH) {
        strBDWSH = BDWSH;
    }

    /**
     *判断本单位审批是否为空
     */
    public boolean isEmptyBDWSH() {
        if (strBDWSH != null && !strBDWSH.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得批准停（复）电时间起
     */
    public String getPZTHDSHQ() {
        return strPZTHDSHQ;
    }
    /**
     * 设置批准停（复）电时间起
     */
    public void setPZTHDSHQ(String PZTHDSHQ) {
        strPZTHDSHQ = PZTHDSHQ;
    }

    /**
     *判断批准停（复）电时间起是否为空
     */
    public boolean isEmptyPZTHDSHQ() {
        if (strPZTHDSHQ != null && !strPZTHDSHQ.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得批准停（复）电时间止
     */
    public String getPZTHDSHZ() {
        return strPZTHDSHZ;
    }
    /**
     * 设置批准停（复）电时间止
     */
    public void setPZTHDSHZ(String PZTHDSHZ) {
        strPZTHDSHZ = PZTHDSHZ;
    }

    /**
     *判断批准停（复）电时间止是否为空
     */
    public boolean isEmptyPZTHDSHZ() {
        if (strPZTHDSHZ != null && !strPZTHDSHZ.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得营运科意见
     */
    public String getYYKYJ() {
        return strYYKYJ;
    }
    /**
     * 设置营运科意见
     */
    public void setYYKYJ(String YYKYJ) {
        strYYKYJ = YYKYJ;
    }

    /**
     *判断营运科意见是否为空
     */
    public boolean isEmptyYYKYJ() {
        if (strYYKYJ != null && !strYYKYJ.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得营运科批准
     */
    public String getYYKPZ() {
        return strYYKPZ;
    }
    /**
     * 设置营运科批准
     */
    public void setYYKPZ(String YYKPZ) {
        strYYKPZ = YYKPZ;
    }

    /**
     *判断营运科批准是否为空
     */
    public boolean isEmptyYYKPZ() {
        if (strYYKPZ != null && !strYYKPZ.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得营运科审核意见
     */
    public String getYYKSHYJ() {
        return strYYKSHYJ;
    }
    /**
     * 设置营运科审核意见
     */
    public void setYYKSHYJ(String YYKSHYJ) {
        strYYKSHYJ = YYKSHYJ;
    }

    /**
     *判断营运科审核意见是否为空
     */
    public boolean isEmptyYYKSHYJ() {
        if (strYYKSHYJ != null && !strYYKSHYJ.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得营运科审核批准
     */
    public String getYYKSHPZ() {
        return strYYKSHPZ;
    }
    /**
     * 设置营运科审核批准
     */
    public void setYYKSHPZ(String YYKSHPZ) {
        strYYKSHPZ = YYKSHPZ;
    }

    /**
     *判断营运科审核批准是否为空
     */
    public boolean isEmptyYYKSHPZ() {
        if (strYYKSHPZ != null && !strYYKSHPZ.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得继电科科意见
     */
    public String getJDKYJ() {
        return strJDKYJ;
    }
    /**
     * 设置继电科科意见
     */
    public void setJDKYJ(String JDKYJ) {
        strJDKYJ = JDKYJ;
    }

    /**
     *判断继电科科意见是否为空
     */
    public boolean isEmptyJDKYJ() {
        if (strJDKYJ != null && !strJDKYJ.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得继电科科批准
     */
    public String getJDKPZ() {
        return strJDKPZ;
    }
    /**
     * 设置继电科科批准
     */
    public void setJDKPZ(String JDKPZ) {
        strJDKPZ = JDKPZ;
    }

    /**
     *判断继电科科批准是否为空
     */
    public boolean isEmptyJDKPZ() {
        if (strJDKPZ != null && !strJDKPZ.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得继电科审核
     */
    public String getJDKSH() {
        return strJDKSH;
    }
    /**
     * 设置继电科审核
     */
    public void setJDKSH(String JDKSH) {
        strJDKSH = JDKSH;
    }

    /**
     *判断继电科审核是否为空
     */
    public boolean isEmptyJDKSH() {
        if (strJDKSH != null && !strJDKSH.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得继电科审核批准
     */
    public String getJDKSHPZ() {
        return strJDKSHPZ;
    }
    /**
     * 设置继电科审核批准
     */
    public void setJDKSHPZ(String JDKSHPZ) {
        strJDKSHPZ = JDKSHPZ;
    }

    /**
     *判断继电科审核批准是否为空
     */
    public boolean isEmptyJDKSHPZ() {
        if (strJDKSHPZ != null && !strJDKSHPZ.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得自动化科意见
     */
    public String getZDHKYJ() {
        return strZDHKYJ;
    }
    /**
     * 设置自动化科意见
     */
    public void setZDHKYJ(String ZDHKYJ) {
        strZDHKYJ = ZDHKYJ;
    }

    /**
     *判断自动化科意见是否为空
     */
    public boolean isEmptyZDHKYJ() {
        if (strZDHKYJ != null && !strZDHKYJ.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得自动化科批准
     */
    public String getZDHKPZ() {
        return strZDHKPZ;
    }
    /**
     * 设置自动化科批准
     */
    public void setZDHKPZ(String ZDHKPZ) {
        strZDHKPZ = ZDHKPZ;
    }

    /**
     *判断自动化科批准是否为空
     */
    public boolean isEmptyZDHKPZ() {
        if (strZDHKPZ != null && !strZDHKPZ.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得领导意见
     */
    public String getLDYJ() {
        return strLDYJ;
    }
    /**
     * 设置领导意见
     */
    public void setLDYJ(String LDYJ) {
        strLDYJ = LDYJ;
    }

    /**
     *判断领导意见是否为空
     */
    public boolean isEmptyLDYJ() {
        if (strLDYJ != null && !strLDYJ.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得领导批准
     */
    public String getLDYJPZ() {
        return strLDYJPZ;
    }
    /**
     * 设置领导批准
     */
    public void setLDYJPZ(String LDYJPZ) {
        strLDYJPZ = LDYJPZ;
    }

    /**
     *判断领导批准是否为空
     */
    public boolean isEmptyLDYJPZ() {
        if (strLDYJPZ != null && !strLDYJPZ.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得调度科意见
     */
    public String getDDKYJ() {
        return strDDKYJ;
    }
    /**
     * 设置调度科意见
     */
    public void setDDKYJ(String DDKYJ) {
        strDDKYJ = DDKYJ;
    }

    /**
     *判断调度科意见是否为空
     */
    public boolean isEmptyDDKYJ() {
        if (strDDKYJ != null && !strDDKYJ.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得调度科批准 
     */
    public String getDDKPZ() {
        return strDDKPZ;
    }
    /**
     * 设置调度科批准 
     */
    public void setDDKPZ(String DDKPZ) {
        strDDKPZ = DDKPZ;
    }

    /**
     *判断调度科批准 是否为空
     */
    public boolean isEmptyDDKPZ() {
        if (strDDKPZ != null && !strDDKPZ.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得通知时间
     */
    public String getTZSJ() {
        return strTZSJ;
    }
    /**
     * 设置通知时间
     */
    public void setTZSJ(String TZSJ) {
        strTZSJ = TZSJ;
    }

    /**
     *判断通知时间是否为空
     */
    public boolean isEmptyTZSJ() {
        if (strTZSJ != null && !strTZSJ.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得通知接令人
     */
    public String getTZJLR() {
        return strTZJLR;
    }
    /**
     * 设置通知接令人
     */
    public void setTZJLR(String TZJLR) {
        strTZJLR = TZJLR;
    }

    /**
     *判断通知接令人是否为空
     */
    public boolean isEmptyTZJLR() {
        if (strTZJLR != null && !strTZJLR.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得通知人
     */
    public String getTZSLR() {
        return strTZSLR;
    }
    /**
     * 设置通知人
     */
    public void setTZSLR(String TZSLR) {
        strTZSLR = TZSLR;
    }

    /**
     *判断通知人是否为空
     */
    public boolean isEmptyTZSLR() {
        if (strTZSLR != null && !strTZSLR.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得通知批准
     */
    public String getTZPZ() {
        return strTZPZ;
    }
    /**
     * 设置通知批准
     */
    public void setTZPZ(String TZPZ) {
        strTZPZ = TZPZ;
    }

    /**
     *判断通知批准是否为空
     */
    public boolean isEmptyTZPZ() {
        if (strTZPZ != null && !strTZPZ.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得停电时间
     */
    public String getTDSJ() {
        return strTDSJ;
    }
    /**
     * 设置停电时间
     */
    public void setTDSJ(String TDSJ) {
        strTDSJ = TDSJ;
    }

    /**
     *判断停电时间是否为空
     */
    public boolean isEmptyTDSJ() {
        if (strTDSJ != null && !strTDSJ.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得停电受令人
     */
    public String getTDSLR() {
        return strTDSLR;
    }
    /**
     * 设置停电受令人
     */
    public void setTDSLR(String TDSLR) {
        strTDSLR = TDSLR;
    }

    /**
     *判断停电受令人是否为空
     */
    public boolean isEmptyTDSLR() {
        if (strTDSLR != null && !strTDSLR.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得停电调度员
     */
    public String getTDDDY() {
        return strTDDDY;
    }
    /**
     * 设置停电调度员
     */
    public void setTDDDY(String TDDDY) {
        strTDDDY = TDDDY;
    }

    /**
     *判断停电调度员是否为空
     */
    public boolean isEmptyTDDDY() {
        if (strTDDDY != null && !strTDDDY.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得停电批准
     */
    public String getTDPZ() {
        return strTDPZ;
    }
    /**
     * 设置停电批准
     */
    public void setTDPZ(String TDPZ) {
        strTDPZ = TDPZ;
    }

    /**
     *判断停电批准是否为空
     */
    public boolean isEmptyTDPZ() {
        if (strTDPZ != null && !strTDPZ.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得开工时间
     */
    public String getKGSJ() {
        return strKGSJ;
    }
    /**
     * 设置开工时间
     */
    public void setKGSJ(String KGSJ) {
        strKGSJ = KGSJ;
    }

    /**
     *判断开工时间是否为空
     */
    public boolean isEmptyKGSJ() {
        if (strKGSJ != null && !strKGSJ.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得开工受令人
     */
    public String getKGSLR() {
        return strKGSLR;
    }
    /**
     * 设置开工受令人
     */
    public void setKGSLR(String KGSLR) {
        strKGSLR = KGSLR;
    }

    /**
     *判断开工受令人是否为空
     */
    public boolean isEmptyKGSLR() {
        if (strKGSLR != null && !strKGSLR.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得开工调度员
     */
    public String getKGDDY() {
        return strKGDDY;
    }
    /**
     * 设置开工调度员
     */
    public void setKGDDY(String KGDDY) {
        strKGDDY = KGDDY;
    }

    /**
     *判断开工调度员是否为空
     */
    public boolean isEmptyKGDDY() {
        if (strKGDDY != null && !strKGDDY.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得开工批准
     */
    public String getKGPZ() {
        return strKGPZ;
    }
    /**
     * 设置开工批准
     */
    public void setKGPZ(String KGPZ) {
        strKGPZ = KGPZ;
    }

    /**
     *判断开工批准是否为空
     */
    public boolean isEmptyKGPZ() {
        if (strKGPZ != null && !strKGPZ.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得完工时间
     */
    public String getWGSJ() {
        return strWGSJ;
    }
    /**
     * 设置完工时间
     */
    public void setWGSJ(String WGSJ) {
        strWGSJ = WGSJ;
    }

    /**
     *判断完工时间是否为空
     */
    public boolean isEmptyWGSJ() {
        if (strWGSJ != null && !strWGSJ.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得完工受令人
     */
    public String getWGSLR() {
        return strWGSLR;
    }
    /**
     * 设置完工受令人
     */
    public void setWGSLR(String WGSLR) {
        strWGSLR = WGSLR;
    }

    /**
     *判断完工受令人是否为空
     */
    public boolean isEmptyWGSLR() {
        if (strWGSLR != null && !strWGSLR.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得完工调度员
     */
    public String getWGDDY() {
        return strWGDDY;
    }
    /**
     * 设置完工调度员
     */
    public void setWGDDY(String WGDDY) {
        strWGDDY = WGDDY;
    }

    /**
     *判断完工调度员是否为空
     */
    public boolean isEmptyWGDDY() {
        if (strWGDDY != null && !strWGDDY.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得完工批准
     */
    public String getWGPZ() {
        return strWGPZ;
    }
    /**
     * 设置完工批准
     */
    public void setWGPZ(String WGPZ) {
        strWGPZ = WGPZ;
    }

    /**
     *判断完工批准是否为空
     */
    public boolean isEmptyWGPZ() {
        if (strWGPZ != null && !strWGPZ.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得复电时间
     */
    public String getFDSJ() {
        return strFDSJ;
    }
    /**
     * 设置复电时间
     */
    public void setFDSJ(String FDSJ) {
        strFDSJ = FDSJ;
    }

    /**
     *判断复电时间是否为空
     */
    public boolean isEmptyFDSJ() {
        if (strFDSJ != null && !strFDSJ.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得复电受令人
     */
    public String getFDSLR() {
        return strFDSLR;
    }
    /**
     * 设置复电受令人
     */
    public void setFDSLR(String FDSLR) {
        strFDSLR = FDSLR;
    }

    /**
     *判断复电受令人是否为空
     */
    public boolean isEmptyFDSLR() {
        if (strFDSLR != null && !strFDSLR.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得复电调度员
     */
    public String getFDDDY() {
        return strFDDDY;
    }
    /**
     * 设置复电调度员
     */
    public void setFDDDY(String FDDDY) {
        strFDDDY = FDDDY;
    }

    /**
     *判断复电调度员是否为空
     */
    public boolean isEmptyFDDDY() {
        if (strFDDDY != null && !strFDDDY.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得复电批准
     */
    public String getFDPZ() {
        return strFDPZ;
    }
    /**
     * 设置复电批准
     */
    public void setFDPZ(String FDPZ) {
        strFDPZ = FDPZ;
    }

    /**
     *判断复电批准是否为空
     */
    public boolean isEmptyFDPZ() {
        if (strFDPZ != null && !strFDPZ.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得延期时间
     */
    public String getYQSJ() {
        return strYQSJ;
    }
    /**
     * 设置延期时间
     */
    public void setYQSJ(String YQSJ) {
        strYQSJ = YQSJ;
    }

    /**
     *判断延期时间是否为空
     */
    public boolean isEmptyYQSJ() {
        if (strYQSJ != null && !strYQSJ.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得延期申请人
     */
    public String getYQSQR() {
        return strYQSQR;
    }
    /**
     * 设置延期申请人
     */
    public void setYQSQR(String YQSQR) {
        strYQSQR = YQSQR;
    }

    /**
     *判断延期申请人是否为空
     */
    public boolean isEmptyYQSQR() {
        if (strYQSQR != null && !strYQSQR.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得延期批准人
     */
    public String getYQPXR() {
        return strYQPXR;
    }
    /**
     * 设置延期批准人
     */
    public void setYQPXR(String YQPXR) {
        strYQPXR = YQPXR;
    }

    /**
     *判断延期批准人是否为空
     */
    public boolean isEmptyYQPXR() {
        if (strYQPXR != null && !strYQPXR.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得延期原因
     */
    public String getYQYY() {
        return strYQYY;
    }
    /**
     * 设置延期原因
     */
    public void setYQYY(String YQYY) {
        strYQYY = YQYY;
    }

    /**
     *判断延期原因是否为空
     */
    public boolean isEmptyYQYY() {
        if (strYQYY != null && !strYQYY.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得延期批准
     */
    public String getYQPZ() {
        return strYQPZ;
    }
    /**
     * 设置延期批准
     */
    public void setYQPZ(String YQPZ) {
        strYQPZ = YQPZ;
    }

    /**
     *判断延期批准是否为空
     */
    public boolean isEmptyYQPZ() {
        if (strYQPZ != null && !strYQPZ.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得备注
     */
    public String getBZ() {
        return strBZ;
    }
    /**
     * 设置备注
     */
    public void setBZ(String BZ) {
        strBZ = BZ;
    }

    /**
     *判断备注是否为空
     */
    public boolean isEmptyBZ() {
        if (strBZ != null && !strBZ.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }


}

