package com.youngbook.common.config;

import com.youngbook.common.MyException;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class XmlHelper {

    protected Document doc  = null;
    protected Element  root = null;


    /**
     * 构造方法，通过 xml 文件来使用该帮助类
     *
     * 修改：邓超
     * 内容：添加注释
     * 时间：2016年7月5日
     *
     * @param xmlFile
     * @throws Exception
     */
    public XmlHelper (File xmlFile) throws Exception {
        try {
            SAXReader reader = new SAXReader ();
            this.doc = reader.read ( xmlFile );

            this.root = this.doc.getRootElement ();
        }
        catch (Exception e) {
            throw e;
        }
    }


    public XmlHelper (InputStream stram) throws Exception {
        try {
            SAXReader reader = new SAXReader ();
            this.doc = reader.read ( stram );

            this.root = this.doc.getRootElement ();
        }
        catch (Exception e) {
            throw e;
        }
    }

    /**
     * 构造方法，通过 xml 字符串来使用该帮助类
     *
     * 修改：邓超
     * 内容：添加注释
     * 时间：2016年7月5日
     *
     * @param strXml
     * @throws Exception
     */
    public XmlHelper (String strXml) throws Exception {
        try {
            SAXReader reader = new SAXReader ();

            Reader xmlReader = new StringReader ( strXml );
            this.doc = reader.read ( xmlReader );
            this.root = this.doc.getRootElement ();
        }
        catch (Exception e) {
            MyException.newInstance("无法初始化XmlHelper", "xml=" + strXml + "&e=" + e.getMessage()).throwException();
        }
    }

    public static Document createDocument(String rootElementName) throws Exception {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement(rootElementName);

        return document;
    }


    public static String buildNode(String name, String value) {
        return "<"+name+">"+value+"</"+name+">";
    }

    /**
     * 测试大数据量解析
     */
    public static void testHugeData () {

        StringBuffer sbXml = new StringBuffer ();
        sbXml.append ( "<?xml version=\"1.0\" encoding=\"utf-8\" ?>" );
        sbXml.append ( "<root></root>" );

        try {
            int size = 150000;
            XmlHelper helper = new XmlHelper ( sbXml.toString () );
            for (int i = 0; i < size; i++) {
                String elementName = "data_" + i;
                helper.getRoot ().addElement ( elementName );
                Element element = helper.getRoot ().element ( elementName );
                element.setText ( "sbXml.append" + i );
            }

            int length = helper.getRoot ().asXML ().getBytes ().length;
            System.out.println ( "size: " + (int) (length / (1024 * 1024)) );
            File file = new File ( "d:/huge.xml" );
            FileWriter writer = new FileWriter ( file );
            writer.write ( helper.getDoc ().asXML () );
            writer.flush ();
            writer.close ();

            // 解析
            XmlHelper helper2 = new XmlHelper ( file );
            System.out.println ( helper2.getRoot () );
        }
        catch (Exception e) {
            e.printStackTrace ();
        }
    }

    public static void main ( String[] args ) throws Exception {
//        testHugeData ();
        //        try {
        //            XmlHelper xml = new XmlHelper ( Tools.getClassFolder () + "waleve-config.xml" );
        //
        //            System.out.println ( xml.getRoot () );
        //
        //            Element root = xml.getRoot ();
        //
        //            System.out.println ( xml.getRoot ().getName () );
        //
        //            List list = xml.getDoc ().selectNodes ( "/config" );
        //
        //            System.out.println ( list.size () );
        //        }
        //        catch (Exception e) {
        //            e.printStackTrace ();
        //        }


        String xml = XmlHelper.createDocument("transaction").asXML();

        System.out.println(xml);
    }

    /**
     * 通过 XML 关系路径取值
     *
     * 修改：邓超
     * 内容：添加注释
     * 时间：2016年7月5日
     *
     * @param xpath
     * @return
     */
    public String getValue ( String xpath ) {
        String value = new String ();
        List list = this.getDoc ().selectNodes ( xpath );
        if (list != null && list.size () == 1) {
            Element node = (Element) list.get ( 0 );
            value = node.getText ();
        }
        return value;
    }

    /**
     * 通过 XML 关系路径取节点文本
     *
     * 修改：邓超
     * 内容：添加注释
     * 时间：2016年7月5日
     *
     * @param xpath
     * @return
     */
    public String getText ( String xpath ) {
        String value = new String ();
        List list = this.getDoc ().selectNodes ( xpath );
        if (list != null && list.size () == 1) {
            Element node = (Element) list.get ( 0 );
            value = node.getStringValue ();
        }
        return value;
    }

    /**
     * 通过 XML 关系路径获取节点
     *
     * 修改：邓超
     * 内容：添加注释
     * 时间：2016年7月5日
     *
     * @param xpath
     * @return
     */
    public Element getElementByXPath(String xpath) {

        List list = this.getDoc ().selectNodes ( xpath );
        if (list != null && list.size () == 1) {
            Element node = (Element) list.get ( 0 );
            return node;
        }

        return null;
    }

    /**
     * 通过 XML 关系路径获取多个节点
     *
     * 修改：邓超
     * 内容：添加注释
     * 时间：2016年7月5日
     *
     * @param xpath
     * @return
     */
    public List<Element> getElementsByXPath(String xpath) {

        List list = this.getDoc ().selectNodes ( xpath );

        return list;
    }

    public List<Element> getElements(String xpath) {
        List<Element> elements = new ArrayList<Element>();
        List list = this.getDoc ().selectNodes ( xpath );
        if (list != null && list.size () == 1) {
            Element node = (Element) list.get ( 0 );
            elements = node.elements();
        }
        return elements;
    }

    public List<Element> getElements(String xpath, String elementName) {
        List<Element> elements = new ArrayList<Element>();
        List list = this.getDoc ().selectNodes ( xpath );
        if (list != null && list.size () == 1) {
            Element node = (Element) list.get ( 0 );
            elements = node.elements(elementName);
        }
        return elements;
    }


    public List<Map> parseFuiouQueryCallbackXML(String xpath) throws Exception {

        List list = this.getDoc().selectNodes(xpath);
        if(list != null) {
            for(int i = 0; i < list.size(); i ++) {
                Element node = (Element) list.get(i);
                System.out.print(node.getUniquePath(node));
            }
        }

        return null;

    }

    /**
     * 获取节点的属性值
     *
     * 修改：邓超
     * 内容：添加注释
     * 时间：2016年7月5日
     *
     * @param xpath
     * @param attriveteName
     * @return
     */
    public String getAttributeValue(String xpath, String attriveteName) {
        String value = new String ();
        List list = this.getDoc ().selectNodes ( xpath );
        if (list != null && list.size () == 1) {
            Element node = (Element) list.get ( 0 );
            value = node.attributeValue(attriveteName);
        }
        return value;
    }

    public Document getDoc () {return doc;}
    public void setDoc ( Document doc ) {
        this.doc = doc;
    }
    public Element getRoot () {
        return root;
    }
    public void setRoot ( Element root ) {
        this.root = root;
    }

}
