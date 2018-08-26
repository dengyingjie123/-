package com.youngbook.entity.po.allinpaycircle;

import com.youngbook.common.KVObjects;
import com.youngbook.entity.po.BasePO;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * Created by leevits on 4/15/2018.
 */
public class TransactionPO extends BasePO {

    /**
     * head部分
     */
    private String processing_code = "";
    private String inst_id = "79040000";
    private String trans_date = "";
    private String trans_time = "";
    private String sign_code = "";
    private String ver_num = "1.00";


    private KVObjects request = new KVObjects();
    private KVObjects response = new KVObjects();

    @Override
    public String toXmlString(String encoding) throws Exception {
        Document document = DocumentHelper.createDocument();
        document.setXMLEncoding(encoding);
        Element root = document.addElement( "transaction" );
        Element head = root.addElement( "head" );
        head.addElement("processing_code").addText(processing_code);
        head.addElement("inst_id").addText(inst_id);
        head.addElement("trans_date").addText(trans_date);
        head.addElement("trans_time").addText(trans_time);
        head.addElement("sign_code").addText(sign_code);
        head.addElement("ver_num").addText(ver_num);

        if (request != null && request.size() > 0) {
            Element requestElement = root.addElement( "request" );

            for (int i = 0; i < request.size(); i++) {

                String elementName = request.get(i).getKeyStringValue();
                String elementText = request.get(i).getValueStringValue();
                requestElement.addElement(elementName).addText(elementText);

            }
        }


        return document.asXML();
    }

    @Override
    public String toXmlString() throws Exception {
        return this.toXmlString("gbk");
    }

    public String getSign_code() {
        return sign_code;
    }

    public void setSign_code(String sign_code) {
        this.sign_code = sign_code;
    }

    public String getProcessing_code() {
        return processing_code;
    }

    public void setProcessing_code(String processing_code) {
        this.processing_code = processing_code;
    }

    public String getInst_id() {
        return inst_id;
    }

    public void setInst_id(String inst_id) {
        this.inst_id = inst_id;
    }

    public String getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(String trans_date) {
        this.trans_date = trans_date;
    }

    public String getTrans_time() {
        return trans_time;
    }

    public void setTrans_time(String trans_time) {
        this.trans_time = trans_time;
    }

    public String getVer_num() {
        return ver_num;
    }

    public void setVer_num(String ver_num) {
        this.ver_num = ver_num;
    }

    public KVObjects getRequest() {
        return request;
    }

    public void setRequest(KVObjects request) {
        this.request = request;
    }

    public KVObjects getResponse() {
        return response;
    }

    public void setResponse(KVObjects response) {
        this.response = response;
    }
}
