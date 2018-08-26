package com.youngbook.common.wf.processdefine;

import java.util.List;
import java.util.ArrayList;
import javax.servlet.jsp.JspWriter;
import com.youngbook.common.wf.processdefine.BizData;
import com.youngbook.common.wf.common.BizField;
import com.youngbook.common.wf.common.Tools;

/**
 * 程序：李扬
 * 时间：2004-11-12
 * 说明：根据data.xml和数据库中相关信息，自动生成相应的BizDao类
 *      生成的类实现了com.youngbook.common.wf.processdefine.IBizDao接口
 */
public class CodeMaker {

    private String strClassName = new String(); //类名
    private String strPackageName = new String(); //包结构
    private BizData bizdata = new BizData();
    private String strTableName = new String(); //数据库表名
    private List listField = new ArrayList(); //由BizField类组成的List
    private String strPrimaryKey = new String(); //记录了关键字的


    /**
     * 程序：李扬
     * 说明：生成代码
     * @param out JspWriter
     * @throws Exception
     */
    public void makeCode(JspWriter out) throws Exception {
        //生成开始信息
        this.makeBegin(out);
        //生成类的成员
        this.makeMember(out);
        //生成构造函数
        this.makeConstructor(out);
        //生成buildObject()方法
        this.makeBuildObject(out);
        //生成query()方法
        this.makeQuery(out);
        //生成queryExact()方法
        this.makeQueryExact(out);
        //生成insert()方法
        this.makeInsert(out);
        //生成update()方法
        this.makeUpdate(out);
        //生成delete()方法
        this.makeDelete(out);
        //生成getYWID()方法
        this.makeGetSetYWID(out);
        //生成SatifyForwardCondition()方法
        this.makeSatisfyForwardCondition(out);
        //生成DateSnapShot()方法
        this.makeDataSnapShot(out);
        //生成getXXX(), setXXX(), isEmptyXXX()方法
        this.makeGetSetMethod(out);
        //生成类的结束信息
        this.makeEnd(out);
    }

    /**
     * 程序：李扬
     * 时间：2004-12-2
     * 说明：生成模糊查询方法
     * @param out JspWriter
     * @return int
     * @throws Exception
     *
     * 修改：李扬
     * 修改说明：修改对数值的模糊查询，生成的SQL语句应该为 NUMBER_TYPE LIKE 32.0
     */
    public int makeQuery(JspWriter out) throws Exception {
        StringBuffer sbSQL = new StringBuffer();
        for (int i = 0; listField != null && i < listField.size(); i++) {
            BizField bf = (BizField) listField.get(i);
            sbSQL.append(bf.getName());
            sbSQL.append(",");
        }
        if (sbSQL != null && sbSQL.length() > 0) {
            sbSQL.delete(sbSQL.length() - 1, sbSQL.length());
        }
        out.println("  /**");
        out.println("   * 程序：代码生成器");
        out.println("   * 说明：生成模糊查询方法");
        out.println("   */");
        out.println("  public List query() throws Exception {");
        out.println("    Connection conn = null;");
        out.println("    Statement statement = null;");
        out.println("    ResultSet resultset = null;");
        out.println("    try {");
        out.println("      List listResult = new ArrayList();");
        out.println("      StringBuffer sbSQL = new StringBuffer();");
        out.println("      sbSQL.append(\"SELECT " + sbSQL.toString() + " FROM " +
                strTableName + "\");");
        out.println("      StringBuffer sbWhere = new StringBuffer();");
        for (int i = 0; listField != null && i < listField.size(); i++) {
            BizField bf = (BizField) listField.get(i);
            if (bf.getCodeType().toUpperCase().equals("INT")) {
                out.println("      if (!this.isEmpty" + bf.getName() + "()) {");
                out.println("        sbWhere.append(\"" + bf.getName() +
                        " LIKE \");");
                out.println("        sbWhere.append(this.get" + bf.getName() + "());");
                out.println("        sbWhere.append(\" AND \");");
                out.println("      }");
                out.println();

            }
            else if (bf.getType().toUpperCase().equals("DATE")) {
                out.println("      if (!this.isEmpty" + bf.getName() + "()) {");
                out.println("        sbWhere.append(\"" + bf.getName() +
                        " LIKE \");");
                out.println("        sbWhere.append(\"TO_DATE('\");");
                out.println("        sbWhere.append(this.get" + bf.getName() + "());");
                out.println("        sbWhere.append(\"','YYYY-MM-DD HH24:MI:SS')\");");
                out.println("        sbWhere.append(\" AND \");");
                out.println("      }");
                out.println();

            }
            else {
                out.println("      if (!this.isEmpty" + bf.getName() + "()) {");
                out.println("        sbWhere.append(\"" + bf.getName() +
                        " LIKE '%\");");
                out.println("        sbWhere.append(this.get" + bf.getName() + "());");
                out.println("        sbWhere.append(\"%' AND \");");
                out.println("      }");
                out.println();
            }
        }
        out.println("      if (sbWhere.length() > 0) {");
        out.println("        sbWhere.insert(0, \" WHERE \");");
        out.println(
                "        sbWhere.delete(sbWhere.length() - 5, sbWhere.length());");
        out.println("        sbSQL.append(sbWhere.toString());");
        out.println("      }");
        out.println("      System.out.println(\"" + strClassName +
                ".query():\"+sbSQL.toString());");
        out.println("      conn = Tools.getDBConn();");
        out.println("      statement = conn.createStatement();");
        out.println("      resultset = statement.executeQuery(sbSQL.toString());");
        out.println("      while (resultset.next()) {");
        out.println("        " + strClassName + " voResult = new " + strClassName +
                "();");
        out.println("        voResult.BuildObject(resultset);");
        out.println("        listResult.add(voResult);");
        out.println("      }");
        out.println("      return listResult;");
        out.println("    }");
        out.println("    catch (Exception e) {");
        out.println("      throw e;");
        out.println("    }");
        out.println("    finally {");
        out.println("      if (resultset != null) {");
        out.println("        resultset.close();");
        out.println("      }");
        out.println("      if (statement != null) {");
        out.println("        statement.close();");
        out.println("      }");
        out.println("      if (conn != null) {");
        out.println("        conn.close();");
        out.println("      }");
        out.println("    }");
        out.println("  }");
        out.println();
        return 1;
    }

    /**
     * 程序：李扬
     * 时间：2004-12-2
     * 说明：生成精确查询方法
     * @param out JspWriter
     * @return int
     * @throws Exception
     */
    public int makeQueryExact(JspWriter out) throws Exception {
        StringBuffer sbSQL = new StringBuffer();
        for (int i = 0; listField != null && i < listField.size(); i++) {
            BizField bf = (BizField) listField.get(i);
            sbSQL.append(bf.getName());
            sbSQL.append(",");
        }
        if (sbSQL != null && sbSQL.length() > 0) {
            sbSQL.delete(sbSQL.length() - 1, sbSQL.length());
        }
        out.println("  /**");
        out.println("   * 程序：代码生成器");
        out.println("   * 说明：生成精确查询方法");
        out.println("   */");
        out.println("  public List queryExact() throws Exception {");
        out.println("    //获得数据库资源");
        out.println("    Connection conn = null;");
        out.println("    Statement statement = null;");
        out.println("    ResultSet resultset = null;");
        out.println("    try {");
        out.println("      List listResult = new ArrayList();");
        out.println("      StringBuffer sbSQL = new StringBuffer();");
        out.println("      sbSQL.append(\"SELECT " + sbSQL.toString() + " FROM " +
                strTableName + "\");");
        out.println("      StringBuffer sbWhere = new StringBuffer();");
        for (int i = 0; listField != null && i < listField.size(); i++) {
            BizField bf = (BizField) listField.get(i);
            out.println("      if (!this.isEmpty" + bf.getName() + "()) {");
            if (bf.getType().equalsIgnoreCase("String")) {
                out.println("        sbWhere.append(\"" + bf.getName() + "='\");");
                out.println("        sbWhere.append(this.get" + bf.getName() + "());");
                out.println("        sbWhere.append(\"' AND \");");
            }
            else if (bf.getType().equalsIgnoreCase("DATE")) {
                out.println("        sbWhere.append(\"" + bf.getName() + "=TO_DATE('\");");
                out.println("        sbWhere.append(this.get" + bf.getName() + "());");
                out.println("        sbWhere.append(\"','YYYY-MM-DD HH24:MI:SS') AND \");");
            }
            else {
                out.println("        sbWhere.append(\"" + bf.getName() + "=\");");
                out.println("        sbWhere.append(this.get" + bf.getName() + "());");
                out.println("        sbWhere.append(\" AND \");");
            }
            out.println("      }");
            out.println();
        }
        out.println("      if (sbWhere.length() > 0) {");
        out.println("        sbWhere.insert(0, \" WHERE \");");
        out.println(
                "        sbWhere.delete(sbWhere.length() - 5, sbWhere.length());");
        out.println("        sbSQL.append(sbWhere.toString());");
        out.println("      }");
        out.println("      System.out.println(\"" + strClassName +
                ".queryExact():\"+sbSQL.toString());");
        out.println("      conn = Tools.getDBConn();");
        out.println("      statement = conn.createStatement();");
        out.println("      resultset = statement.executeQuery(sbSQL.toString());");
        out.println("      while (resultset.next()) {");
        out.println("        " + strClassName + " voResult = new " + strClassName +
                "();");
        out.println("        voResult.BuildObject(resultset);");
        out.println("        listResult.add(voResult);");
        out.println("      }");
        out.println("      return listResult;");
        out.println("    }");
        out.println("    catch (Exception e) {");
        out.println("      throw e;");
        out.println("    }");
        out.println("    finally {");
        out.println("      //释放数据库资源");
        out.println("      if (resultset != null) {");
        out.println("        resultset.close();");
        out.println("      }");
        out.println("      if (statement != null) {");
        out.println("        statement.close();");
        out.println("      }");
        out.println("      if (conn != null) {");
        out.println("        conn.close();");
        out.println("      }");
        out.println("    }");
        out.println("  }");
        out.println();
//    for (int i = 0; listField != null && i < listField.size(); i++) {
//      BizField bf = (BizField)listField.get(i);
//
//    }
        return 1;
    }

    /**
     * 程序：李扬
     * 说明：构造函数
     *      初始化工作流编号和主键等信息
     * @param intWorkflowID int
     * @param PrimaryKey String
     * @throws Exception
     */
    public CodeMaker(int intWorkflowID, String PrimaryKey) throws Exception {
        this();
        bizdata = BizData.buildObject(intWorkflowID);
        bizdata.initBizField();
        bizdata.initFieldTitle();
        listField = bizdata.getBizField();
        strTableName = bizdata.getDataTable();
        strPrimaryKey = PrimaryKey;
    }

    /**
     * 程序：李扬
     * 时间：2004-12-3
     * 说明：生成不带参数的构造函数
     * @param out JspWriter
     * @return int
     * @throws Exception
     */
    public int makeConstructor(JspWriter out) throws Exception {
        out.println("  /**");
        out.println("   * 构造函数");
        out.println("   */");
        out.println("  public " + strClassName + "() {");
        out.println("  ");
        out.println("  }");
        out.println("");
        return 1;
    }

    /**
     * 程序：李扬
     * 时间：2004-11-12
     * 说明：生成类开始信息，package, import...
     * @param out JspWriter
     * @return int
     * @throws Exception
     */
    public int makeBegin(JspWriter out) throws Exception {
        out.println("package " + strPackageName + ";");
        out.println("/**");
        out.println(" * 标题：" + strClassName + "类");
        out.println(" * 描述: 实现IBziDao接口，完成业务逻辑");
        out.println(" * 时间：" + Tools.getTime());
        out.println(" * 版权：");
        out.println(" */");
        out.println("import java.sql.*;");
        out.println("import java.util.*;");
        out.println("import javax.servlet.*;");
        out.println("import javax.servlet.http.*;");
        out.println("import com.youngbook.common.wf.common.*;");
        out.println("import com.youngbook.common.wf.engines.*;");
        out.println("public class " + strClassName + " implements IBizDao {");
        out.println();
        return 1;
    }

    /**
     * 程序：李扬
     * 时间：2004-11-12
     * 说明：生成代码结束信息
     * @param out JspWriter
     * @return int
     * @throws Exception
     */
    public int makeEnd(JspWriter out) throws Exception {
        out.println("}");
        return 1;
    }

    /**
     * 程序：李扬
     * 时间：2004-11-12
     * 说明：生成类的成员
     * @param out JspWriter
     * @return int
     * @throws Exception
     */
    public int makeMember(JspWriter out) throws Exception {
        //循环获得Field
        for (int i = 0; listField != null && i < listField.size(); i++) {
            BizField bf = (BizField) listField.get(i);
            out.println("  protected " + bf.getCodeType() + " " +
                    bf.getCodeName().toString() + " = " +
                    bf.getInitValue() + ";  //" + bf.getTitle());
        }
        out.println();
        return 1;
    }

    /**
     * 程序：李扬
     * 时间：2004-11-12
     * 说明：生成get(), set()方法
     * @param out JspWriter
     * @return int
     * @throws Exception
     */
    public int makeGetSetMethod(JspWriter out) throws Exception {
        for (int i = 0; listField != null && i < listField.size(); i++) {
            BizField bf = (BizField) listField.get(i);
            //----------   生成get方法
            out.println("  /**");
            out.println("   * 获得" + bf.getTitle());
            out.println("   */");
            out.println("  public " + bf.getCodeType() + " get" +
                    bf.getName() + "() {");
            out.println("    return " + bf.getCodeName().toString() + ";");
            out.println("  }");
            out.println("");
            //-----------------------
            //--------   生成set方法
            out.println("  /**");
            out.println("   * 设置" + bf.getTitle());
            out.println("   */");
            out.println("  public" + " void set" +
                    bf.getName() + "(" + bf.getCodeType() + " " + bf.getName() +
                    ") {");
            out.println("    " + bf.getCodeName().toString() + " = " + bf.getName() +
                    ";");
            out.println("  }");
            out.println();
            //-----------------------
            //----------  生成isEmpty方法
            out.println("  /**");
            out.println("   *判断" + bf.getTitle() + "是否为空");
            out.println("   */");
            out.println("  public" + " boolean isEmpty" +
                    bf.getName() + "() {");
            out.print("    if (");
            if (bf.getCodeType().equalsIgnoreCase("String")) {
                out.println(bf.getCodeName().toString() + " != null && !" +
                        bf.getCodeName().toString() + ".equals(\"\")) {");
                out.println("      return false;");
                out.println("    }");
                out.println("    else {");
                out.println("      return true;");
                out.println("    }");
            } else if (bf.getCodeType().equalsIgnoreCase("int")) {
                out.println("  " + bf.getCodeName().toString() +
                        " == Integer.MAX_VALUE) {");
                out.println("      return true;");
                out.println("    }");
                out.println("    else {");
                out.println("      return false;");
                out.println("    }");
            } else if (bf.getCodeType().equalsIgnoreCase("double")) {
                out.println("  " + bf.getCodeName().toString() +
                        " == Double.MAX_VALUE) {");
                out.println("      return true;");
                out.println("    }");
                out.println("    else {");
                out.println("      return false;");
                out.println("    }");

            }
            out.println("  }");
            out.println();
            //--------------------------
        }
        return 1;
    }

    /**
     * 程序：李扬
     * 时间：2004-11-12
     * 说明：生成 insert(Connection conn)方法
     * @param out JspWriter
     * @return int
     * @throws Exception
     */
    public int makeInsert(JspWriter out) throws Exception {
        out.println("  /**");
        out.println("   * 标题：插入数据库方法");
        out.println("   * 描述：实现IBizDao接口中insert()方法");
        out.println("   *      将新业务数据插入数据库，在工作流第一个动作时需要使用。");
        out.println("   * 返回值：1：成功，0：失败");
        out.println("   */");
        out.println("   public int insert() throws Exception {");
        out.println("       Connection conn = null;");
        out.println("       try {");
        out.println("           conn = Tools.getDBConn();");
        out.println("           return insert(conn);");
        out.println("       } catch (Exception e) {");
        out.println("           throw e;");
        out.println("       } finally {");
        out.println("           if (conn != null) {");
        out.println("               conn.close();");
        out.println("           }");
        out.println("       }");
        out.println("   }");
        out.println();
        out.println("  /**");
        out.println("   * 标题：插入数据库方法");
        out.println("   * 描述：实现IBizDao接口中insert()方法");
        out.println("   *      将新业务数据插入数据库，在工作流第一个动作时需要使用。");
        out.println("   * 返回值：1：成功，0：失败");
        out.println("   */");

        out.println("  public int insert(Connection conn) throws Exception {");
        out.println("    int intResult = 0;");
        out.println("    Statement statement = null;");
        out.println("    try {");
        out.println("      StringBuffer sbSQL = new StringBuffer();");
        out.println("      StringBuffer sbInsertField = new StringBuffer();");
        out.println("      StringBuffer sbInsertValue = new StringBuffer();");
        out.println("      sbSQL.append(\"INSERT INTO " + strTableName + " (\");");
        for (int i = 0; listField != null && i < listField.size(); i++) {
            BizField bf = (BizField) listField.get(i);
            out.println("      if (!this.isEmpty" + bf.getName() + "()) {");
            out.println("        sbInsertField.append(\"" + bf.getName() + "\");");
            out.println("        sbInsertField.append(\",\");");
            out.println("      }");
        }
        out.println("      if (sbInsertField.length() > 0) {");
        out.println("        sbInsertField.delete(sbInsertField.length() - 1, sbInsertField.length());");
        out.println("        sbSQL.append(sbInsertField.toString());");
        out.println("      }");
        out.println("      else {");
        out.println("        throw new Exception(\"" + strClassName +
                "|insert|NoPara|执行" + strClassName +
                ".insert()方法时发生参数不足异常，没有设置可更新字段\");");
        out.println("      }");
        out.println("      sbSQL.append(\") VALUES (\");");
        for (int i = 0; listField != null && i < listField.size(); i++) {
            BizField bf = (BizField) listField.get(i);
            out.println("        if (!this.isEmpty" + bf.getName() + "()) {");
            if (bf.getType().equalsIgnoreCase("String")) {
                out.println("          sbInsertValue.append(\"'\");");
                out.println("          sbInsertValue.append(" + bf.getCodeName() + ");");
                out.println("          sbInsertValue.append(\"'\");");
                out.println("          sbInsertValue.append(\",\");");
            }
            else if (bf.getType().toUpperCase().equals("DATE")) {
                out.println("          sbInsertValue.append(\"TO_DATE('\");");
                out.println("          sbInsertValue.append(" + bf.getCodeName() + ");");
                out.println("          sbInsertValue.append(\"','YYYY-MM-DD HH24:MI:SS')\");");
                out.println("          sbInsertValue.append(\",\");");

            }
            else {
                out.println("          sbInsertValue.append(" + bf.getCodeName() + ");");
                out.println("          sbInsertValue.append(\",\");");
            }
            out.println("        }");
        }
        out.println("      if (sbInsertValue.length() > 0) {");
        out.println("        sbInsertValue.delete(sbInsertValue.length() - 1, sbInsertValue.length());");
        out.println("        sbSQL.append(sbInsertValue.toString());");
        out.println("      }");
        out.println("      else {");
        out.println("        throw new Exception(\"" + strClassName +
                "|insert|NoPara|执行" + strClassName +
                ".insert()方法时发生参数不足异常，没有设置可更新字段\");");
        out.println("      }");
        out.println("      sbSQL.append(\")\");");
        out.println("      System.out.println(\"" + strClassName +
                ".insert():\" + sbSQL.toString());");
        out.println("      statement = conn.createStatement();");
        out.println("      intResult = statement.executeUpdate(sbSQL.toString());");
        out.println("      return intResult;");
        out.println("    }");
        out.println("    catch (Exception e) {");
        out.println("      throw e;");
        out.println("    }");
        out.println("    finally {");
        out.println("      if (statement != null) {");
        out.println("        statement.close();");
        out.println("      }");
        out.println("    }");
        out.println("  }");
        out.println();
        return 1;
    }

    /**
     * 程序：李扬
     * 时间：2004-11-12
     * 说明：生成：BuildObject(HttpServletRequest request)方法
     * @param out JspWriter
     * @return int
     * @throws Exception
     */
    public int makeBuildObject(JspWriter out) throws Exception {

        //--------------------- buildObject(HttpServletRequest request)
        out.println("  /**");
        out.println("   * 标题：BuildObject方法");
        out.println("   * 描述：从request中获得参数，初始化" + strClassName + "类");
        out.println("   * 返回值：void");
        out.println("   */");
        out.println(
                "  public void BuildObject(HttpServletRequest request) throws Exception {");
        for (int i = 0; listField != null && i < listField.size(); i++) {
            BizField bf = (BizField) listField.get(i);
            out.println("    if (request.getParameter(\"" + bf.getName() +
                    "\") != null &&");
            out.println("        !request.getParameter(\"" + bf.getName() +
                    "\").equals(\"\")) {");
            if (bf.getCodeType().equalsIgnoreCase("String")) {
                out.println("      this.set" + bf.getName() +
                        "(Tools.toUTF8(request.getParameter(\"" +
                        bf.getName() + "\")));");
            } else {
                if (bf.getCodeType().equalsIgnoreCase("int")) {
                    out.println("      this.set" + bf.getName() +
                            "(Integer.parseInt(request.getParameter(\"" +
                            bf.getName() +
                            "\")));");
                } else if (bf.getCodeType().equalsIgnoreCase("double")) {
                    out.println("      this.set" + bf.getName() +
                            "(Double.parseDouble(request.getParameter(\"" +
                            bf.getName() +
                            "\")));");
                }
            }
            out.println("    }");
        }
        out.println("  }");
        out.println();

        // ------------------- buildObject(ResultSet resultset)
        out.println(
                "  public void BuildObject(ResultSet resultset) throws Exception {");
        for (int i = 0; listField != null && i < listField.size(); i++) {
            BizField bf = (BizField) listField.get(i);
            out.println("    if (resultset.getString(\"" + bf.getName() +
                    "\") != null) {");
            if (bf.getCodeType().equalsIgnoreCase("String")) {
                out.println("      this.set" + bf.getName() + "(resultset.getString(\"" +
                        bf.getName() + "\"));");
            } else if (bf.getCodeType().equalsIgnoreCase("int")) {
                out.println("      this.set" + bf.getName() + "(resultset.getInt(\"" +
                        bf.getName() + "\"));");
            } else if (bf.getCodeType().equalsIgnoreCase("double")) {
                out.println("      this.set" + bf.getName() + "(resultset.getDouble(\"" +
                        bf.getName() + "\"));");
            }
            out.println("    }");
            out.println();
        }
        out.println("  }");
        return 1;
    }

    /**
     * 程序：李扬
     * 时间：2004-11-12
     * 说明：生成update(Connection conn)方法
     * @param out JspWriter
     * @return int
     * @throws Exception
     */
    public int makeUpdate(JspWriter out) throws Exception {
        BizField bfPK = new BizField();
        out.println("  /**");
        out.println("   * 标题：更新数据库方法");
        out.println("   * 描述：实现IBizDao接口update方法");
        out.println("   *      更新业务数据，在工作流流转当中，需要对业务数据进行更新等操作.");
        out.println("   * 返回值：1：成功，0：失败");
        out.println("   */");

        out.println("   public int update() throws Exception {");
        out.println("       Connection conn = null;");
        out.println("       try {");
        out.println("           conn = Tools.getDBConn();");
        out.println("           return update(conn);");
        out.println("       } catch (Exception e) {");
        out.println("           throw e;");
        out.println("       } finally {");
        out.println("           if (conn != null) {");
        out.println("               conn.close();");
        out.println("           }");
        out.println("       }");
        out.println("   }");
        out.println();
        out.println("  /**");
        out.println("   * 标题：更新数据库方法");
        out.println("   * 描述：实现IBizDao接口update方法");
        out.println("   *      更新业务数据，在工作流流转当中，需要对业务数据进行更新等操作.");
        out.println("   * 返回值：1：成功，0：失败");
        out.println("   */");

        out.println("  public int update(Connection conn) throws Exception {");
        out.println("    Statement statement = null;");
        out.println("    try {");
        out.println("      int intResult = 0;");
        out.println("      StringBuffer sbSQL = new StringBuffer();");
        out.println("      StringBuffer sbSet = new StringBuffer();");
        out.println("      sbSQL.append(\"UPDATE " + strTableName + " SET \");");

        for (int i = 0; listField != null && i < listField.size(); i++) {
            BizField bf = (BizField) listField.get(i);

            if (!bf.getName().equalsIgnoreCase(strPrimaryKey)) {
                out.println("      if (!isEmpty" + bf.getName() + "()) {");
                if (bf.getType().equalsIgnoreCase("String")) {
                    out.println("        sbSet.append(\"" + bf.getName() + "=\");");
                    out.println("        sbSet.append(\"'\");");
                    out.println("        sbSet.append(this.get" + bf.getName() + "());");
                    out.println("        sbSet.append(\"',\");");
                }
                else if (bf.getType().equalsIgnoreCase("Date")) {
                    out.println("        sbSet.append(\"" + bf.getName() + "=\");");
                    out.println("        sbSet.append(\"TO_DATE('\");");
                    out.println("        sbSet.append(this.get" + bf.getName() + "());");
                    out.println("        sbSet.append(\"','YYYY-MM-DD HH24:MI:SS'),\");");

                }
                else {
                    out.println("        sbSet.append(\"" + bf.getName() + "=\");");
                    out.println("        sbSet.append(this.get" + bf.getName() + "());");
                    out.println("        sbSet.append(\",\");");
                }
                out.println("      }");
            } else {
                bfPK = bf;
            }
        }
        out.println("      if (sbSet.length() > 0) {");
        out.println("        sbSet.delete(sbSet.length() - 1, sbSet.length());");
        out.println("        sbSQL.append(sbSet.toString());");
        out.println("      }");
        out.println("      else {");
        out.println("        throw new Exception(\"" + strClassName +
                "|update|NoPara|执行" + strClassName +
                ".update()方法发生参数不足异常，没有设置可跟新字段\");");
        out.println("      }");
        out.println();
        out.println("      sbSQL.append(\" WHERE \");");
        out.println("      sbSQL.append(\"" + bfPK.getName() + "=\");");
        if (bfPK.getCodeType().equalsIgnoreCase("String")) {
            out.println("      sbSQL.append(\"'\");");
            out.println("      sbSQL.append(" + bfPK.getCodeName().toString() + ");");
            out.println("      sbSQL.append(\"'\");");
        } else {
            out.println("      sbSQL.append(" + bfPK.getCodeName().toString() + ");");
        }
        out.println("      System.out.println(\"" + strClassName +
                ".update():\" + sbSQL.toString());");
        out.println("      statement = conn.createStatement();");
        out.println(
                "      intResult = statement.executeUpdate(sbSQL.toString());");
        out.println("      return intResult;");
        out.println("    }");
        out.println("    catch (Exception e) {");
        out.println("      throw e;");
        out.println("    }");
        out.println("    finally {");
        out.println("      if (statement != null ) {");
        out.println("        statement.close();");
        out.println("      }");
        out.println("    }");
        out.println("  }");
        out.println();
        return 1;
    }

    /**
     * 程序：李扬
     * 时间：2004-11-12
     * 说明：生成delete(Connection conn)方法
     * @param out JspWriter
     * @return int
     * @throws Exception
     */
    public int makeDelete(JspWriter out) throws Exception {
        boolean boolHavePK = false;
        out.println("  /**");
        out.println("   * 标题：删除数据库方法");
        out.println("   * 描述：实现IBizDao接口中delete方法");
        out.println("   *      根据业务编号，删除数据库信息");
        out.println("   * 返回值：1：成功，0：失败");
        out.println("   */");

        out.println("   public int delete() throws Exception {");
        out.println("       Connection conn = null;");
        out.println("       try {");
        out.println("           conn = Tools.getDBConn();");
        out.println("           return delete(conn);");
        out.println("       } catch (Exception e) {");
        out.println("           throw e;");
        out.println("       } finally {");
        out.println("           if (conn != null) {");
        out.println("               conn.close();");
        out.println("           }");
        out.println("       }");
        out.println("   }");
        out.println();
        out.println("  /**");
        out.println("   * 标题：删除数据库方法");
        out.println("   * 描述：实现IBizDao接口中delete方法");
        out.println("   *      根据业务编号，删除数据库信息");
        out.println("   * 返回值：1：成功，0：失败");
        out.println("   */");

        out.println("  public int delete(Connection conn) throws Exception {");
        out.println("    Statement statement = null;");
        out.println("    try {");
        out.println("      int intResult = 0;");
        out.println("      StringBuffer sbSQL = new StringBuffer();");
        out.println("      StringBuffer southere = new StringBuffer();");

        out.println("      sbSQL.append(\"DELETE FROM " + strTableName + "\");");
        for (int i = 0; listField != null && i < listField.size(); i++) {
            BizField bf = (BizField) listField.get(i);
            if (bf.getName().equalsIgnoreCase(strPrimaryKey)) {
                boolHavePK = true;
                out.println("      if (this.isEmpty"+bf.getName()+"()) {");
                out.println("        throw new Exception(\""+strClassName+"|delete|NoPara|执行"+strClassName+".delete()方法是发生参数不足异常，无法获得"+bf.getName()+"\");");
                out.println("      }");
                out.println();
                out.println("      sbSQL.append(\" WHERE \");");
                out.println("      sbSQL.append(\"" + bf.getName() + "=\");");
                if (bf.getCodeType().equalsIgnoreCase("String")) {
                    out.println("      sbSQL.append(\"'\");");
                    out.println("      sbSQL.append(" + bf.getCodeName().toString() +
                            ");");
                    out.println("      sbSQL.append(\"'\");");
                } else {
                    out.println("      sbSQL.append(" + bf.getCodeName().toString() +
                            ");");
                }
                break;
            }
        }
        out.println();
        if (!boolHavePK) {
            throw new Exception("CodeMaker|makeDelete|NoPara|执行CodeMaker.makeDelete()方法是发生参数不足异常，无法获得数据库主键");
        }
        out.println("      System.out.println(\"" + strClassName +
                ".Delete():\" + sbSQL.toString());");
        out.println("      statement = conn.createStatement();");
        out.println("      intResult = statement.executeUpdate(sbSQL.toString());");
        out.println("      return intResult;");
        out.println("    }");
        out.println("    catch (Exception e) {");
        out.println("      throw e;");
        out.println("    }");
        out.println("    finally {");
        out.println("      if (statement != null ) {");
        out.println("        statement.close();");
        out.println("      }");
        out.println("    }");
        out.println("  }");
        out.println();
        return 1;
    }

    /**
     * 程序：李扬
     * 时间：2004-11-12
     * 说明：生成getYWID(), setYWID, isEmptyYWID()方法
     * @param out JspWriter
     * @return int
     * @throws Exception
     */
    public int makeGetSetYWID(JspWriter out) throws Exception {
        boolean boolIsHaveYWID = false;
        BizField bfYWID = new BizField();
        for (int i = 0; listField != null && i < listField.size(); i++) {
            BizField bf = (BizField) listField.get(i);
            if (bf.getName().equalsIgnoreCase("YWID")) {
                boolIsHaveYWID = true;
            }
            if (bf.getName().equalsIgnoreCase(strPrimaryKey)) {
                bfYWID = bf;
            }
        }
        if (!boolIsHaveYWID) {
            //------------  生成getYWID方法
            out.println("  /**");
            out.println("   * 标题：获得业务编号方法");
            out.println("   * 描述：获得业务编号");
            out.println("   * 返回值：String");
            out.println("   */");
            out.println("  public String getYWID() {");
            out.print("    return ");
            if (bfYWID.getCodeType().equalsIgnoreCase("String")) {
                out.println("" + bfYWID.getCodeName().toString() + ";");
            } else {
                out.println("String.valueOf(" + bfYWID.getCodeName().toString() + ");");
            }
            out.println("  }");
            out.println();
            //----------- 生成setYWID方法
            out.println("  /**");
            out.println("   * 标题：设置业务编号方法");
            out.println("   * 描述：设置业务编号");
            out.println("   * 返回值：void");
            out.println("   */");

            out.println("  public void setYWID(String YWID) {");
            if (bfYWID.getCodeType().equalsIgnoreCase("String")) {
                out.println("    " + bfYWID.getCodeName().toString() + " = YWID;");
            } else if (bfYWID.getCodeType().equalsIgnoreCase("int")) {
                out.println("    " + bfYWID.getCodeName().toString() +
                        " = Integer.parseInt(YWID);");
            } else if (bfYWID.getCodeType().equalsIgnoreCase("double")) {
                out.println("    " + bfYWID.getCodeName().toString() +
                        " = Double.parseDouble(YWID);");
            } else {
                throw new Exception(
                        "Code|makeGetSetYWID()|NoPara|执行Code.makeGetSetYWID()方法时发生参数不足异常，无法获得业务编号");
            }
            out.println("  }");
            out.println();
            //---------  生成isEmptyYWID方法
            out.println("  /**");
            out.println("   * 标题：判断业务编号是否为空");
            out.println("   * 描述：判断业务编号是否为空");
            out.println("   * 返回值：真：业务编号为空，假：业务编号不为空");
            out.println("   */");
            out.println("  public boolean isEmptyYWID() {");
            if (bfYWID.getCodeType().equalsIgnoreCase("String")) {
                out.println("    if (" + bfYWID.getCodeName().toString() +
                        " != null && !" +
                        bfYWID.getCodeName().toString() + ".equals(\"\")) {");
                out.println("      return false;");
                out.println("    }");
                out.println("    else {");
                out.println("      return true;");
                out.println("    }");
            } else if (bfYWID.getCodeType().equalsIgnoreCase("int")) {
                out.println("    if (" + bfYWID.getCodeName().toString() +
                        " == Integer.MAX_VALUE) {");
                out.println("      return true;");
                out.println("    }");
                out.println("    else {");
                out.println("      return false;");
                out.println("    }");
            } else if (bfYWID.getCodeType().equalsIgnoreCase("double")) {
                out.println("    if (" + bfYWID.getCodeName().toString() +
                        " == Double.MAX_VALUE) {");
                out.println("      return true;");
                out.println("    }");
                out.println("    else {");
                out.println("      return false;");
                out.println("    }");
            }
            out.println("  }");
            out.println();
        }
        return 1;
    }

    /**
     * 程序：李扬
     * 时间：2004-11-12
     * 说明：生成SatisfyForwardCondition()方法
     * @param out JspWriter
     * @return int
     * @throws Exception
     */
    public int makeSatisfyForwardCondition(JspWriter out) throws Exception {
        out.println("  /**");
        out.println("   * 标题：SatisfyForwardCondition方法");
        out.println("   * 描述：根据Condition判断是否满足节点的进入（离开）条件");
        out.println("   * 返回值：真：满足条件，假：不满足条件");
        out.println("   */");

        out.println("  public boolean SatisfyForwardCondition(String Condition,Connection conn) throws Exception {");
        out.println("    Statement statement = null;");
        out.println("    ResultSet resultset = null;");
        out.println("    boolean boolResult = false;");
        out.println("    try {");
        out.println("      StringBuffer sbSQL = new StringBuffer();");
        out.println("      StringBuffer southere = new StringBuffer();");
        out.println("      sbSQL.append(\"SELECT * FROM " + strTableName + "\");");
        out.println(); ;
        out.println("      if (Condition != null && !Condition.equals(\"\")) {");
        out.println("        southere.append(Condition);");
        out.println("        southere.append(\" AND \");");
        out.println("      }");
        out.println("      if (!this.isEmptyYWID()) {");
        for (int i = 0; listField != null && i < listField.size(); i++) {
            BizField bf = (BizField) listField.get(i);
            if (bf.getName().equals(strPrimaryKey)) {
                if (bf.getCodeType().equalsIgnoreCase("String")) {
                    out.println("        southere.append(\"" + bf.getName() + "='\");");
                    out.println("        southere.append(this.get" + bf.getName() +
                            "());");
                    out.println("        southere.append(\"' AND \");");
                } else {
                    out.println("        southere.append(\"" + bf.getName() + "=\");");
                    out.println("        southere.append(this.get" + bf.getName() +
                            "());");
                    out.println("        southere.append(\" AND \");");
                }
                break;
            }
        }
        out.println("      }");
        out.println("      else {");
        out.println("        throw new Exception(\"" + strClassName +
                "|SatisfyForwardCondition|NoPara|执行" + strClassName +
                ".SatisfyForwardCondition()方法时发生参数不足异常，无法获得业务编号(YWID)\");");
        out.println("      }");
        out.println("      if (southere.length() > 0) {");
        out.println("        southere.insert(0, \" WHERE \");");
        out.println(
                "        southere.delete(southere.length() - 5, southere.length());");
        out.println("        sbSQL.append(southere.toString());");
        out.println("      }");
        out.println("      System.out.println(\"" + strClassName +
                ".SatisfyForwardCondition():\"+sbSQL.toString());");
        out.println("      statement = conn.createStatement();");
        out.println("      resultset = statement.executeQuery(sbSQL.toString());");
        out.println("      if (resultset.next()) {");
        out.println("        boolResult = true;");
        out.println("      }");
        out.println("      return boolResult;");
        out.println("    }");
        out.println("    catch (Exception e) {");
        out.println("      throw e;");
        out.println("    }");
        out.println("    finally {");
        out.println("      if (resultset != null) {");
        out.println("        resultset.close();");
        out.println("      }");
        out.println("      if (statement != null) {");
        out.println("        statement.close();");
        out.println("      }");
        out.println("    }");
        out.println("  }");
        out.println();
        return 1;
    }

    /**
     * 程序：李扬
     * 时间：2004-11-12
     * 说明：生成DataSnapShot(), DataSnapShot(Connection conn)方法
     * @param out JspWriter
     * @return int
     * @throws Exception
     */
    public int makeDataSnapShot(JspWriter out) throws Exception {
        BizField bfPK = new BizField();
        StringBuffer sbSelect = new StringBuffer();
        //--------------  生成 DataSnapShot(Connection conn) 方法
        out.println("  /**");
        out.println("   * 标题：DataSnapShot方法");
        out.println("   * 描述：获得数据库中相应的值，用于保存历史数据");
        out.println("   * 返回值：HashMap");
        out.println("   */");
        out.println(
                "  public HashMap DataSnapShot(Connection conn) throws Exception {");
        out.println("    Statement statement = null;");
        out.println("    ResultSet resultset = null;");
        out.println("    try {");
        out.println("      HashMap hm = new HashMap();");
        out.println("      StringBuffer sbSQL = new StringBuffer();");
        out.print("      sbSQL.append(\"SELECT ");
        for (int i = 0; listField != null && i < listField.size(); i++) {
            BizField bf = (BizField) listField.get(i);
            sbSelect.append(bf.getName());
            sbSelect.append(",");
            if (bf.getName().equalsIgnoreCase(strPrimaryKey)) {
                bfPK = bf; ;
            }
        }
        if (sbSelect.length() > 0) {
            sbSelect.delete(sbSelect.length() - 1, sbSelect.length());
        }
        out.println(sbSelect.toString() + " FROM " + strTableName + " WHERE \");");
        out.println("      if (!isEmptyYWID()) {");
        out.println("        sbSQL.append(\"" + bfPK.getName() + "=\");");
        if (bfPK.getCodeType().equalsIgnoreCase("String")) {
            out.println("        sbSQL.append(\"'\");");
            out.println("        sbSQL.append(" + bfPK.getCodeName().toString() +
                    ");");
            out.println("        sbSQL.append(\"'\");");
        } else {
            out.println("        sbSQL.append(" + bfPK.getCodeName().toString() +
                    ");");
        }
        out.println("      }");
        out.println("      else {");
        out.println("        throw new Exception(\"" + strClassName +
                "|DataSnapShot|执行" + strClassName +
                ".DataSnapShot()方法时发生参数不足异常，无法获得业务编号(YWID)\");");
        out.println("      }");
        out.println("      System.out.println(\"" + strClassName +
                ".DataSnapShot():\"+sbSQL.toString());");
        out.println("      statement = conn.createStatement();");
        out.println("      resultset = statement.executeQuery(sbSQL.toString());");
        out.println("      if (resultset.next()) {");
        for (int i = 0; listField != null && i < listField.size(); i++) {
            BizField bf = (BizField) listField.get(i);
            out.println("        hm.put(\"" + bf.getName() +
                    "\", resultset.getString(\"" +
                    bf.getName() + "\"));");
        }
        out.println("      }");
        out.println("      return hm;");
        out.println("  }");
        out.println("    catch (Exception e) {");
        out.println("      throw e;");
        out.println("    }");
        out.println("    finally {");
        out.println("      if (resultset != null) {");
        out.println("        resultset.close();");
        out.println("      }");
        out.println("      if (statement != null) {");
        out.println("        statement.close();");
        out.println("      }");
        out.println("    }");
        out.println("  }");
        out.println();
        //------------  生成 DataSnapShot() 方法
        out.println("  /**");
        out.println("   * 标题：DataSnapShot方法");
        out.println("   * 描述：获得数据库中相应的值，用于保存历史数据");
        out.println("   * 返回值：HashMap");
        out.println("   */");

        out.println("  public HashMap DataSnapShot() throws Exception {");
        out.println("    Connection conn = null;");
        out.println("    try {");
        out.println("      conn = Tools.getDBConn();");
        out.println("      return this.DataSnapShot(conn);");
        out.println("    }");
        out.println("    catch (Exception e) {");
        out.println("      throw e;");
        out.println("    }");
        out.println("    finally {");
        out.println("      if (conn != null) {");
        out.println("        conn.close();");
        out.println("      }");
        out.println("    }");
        out.println("  }");
        out.println();
        return 1;
    }

    /**
     * 程序：李扬
     * 时间：2004-12-3
     * 说明：设置类名
     * @param ClassName String
     */
    public void setClassName(String ClassName) {
        strClassName = ClassName;
    }

    /**
     * 程序：李扬
     * 时间：2004-12-3
     * 说明：设置包结构
     * @param PackageName String
     */
    public void setPackageName(String PackageName) {
        strPackageName = PackageName;
    }

    /**
     * 程序：李扬
     * 时间：2004-12-3
     * 说明：设置主键
     * @param PrimaryKey String
     */
    public void setPrimaryKey(String PrimaryKey) {
        strPrimaryKey = PrimaryKey;
    }

    /**
     * 程序：李扬
     * 时间：2004-12-3
     * 说明：构造函数
     * @throws Exception
     */
    public CodeMaker() throws Exception {
    }
}

