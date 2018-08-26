package com.youngbook.common.utils;

import com.youngbook.common.config.Config;
import com.youngbook.common.config.XmlHelper;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 14-10-6
 * Time: 下午1:20
 * To change this template use File | Settings | File Templates.
 */
public class EmailClient {

    private String host = "";
    private String port = "";
    private String userName = "";
    private String password = "";

    public EmailClient() {
    }

    public EmailClient(String host, String port, String userName, String password) {
        this.host = host;
        this.port = port;
        this.userName = userName;
        this.password = password;
    }

    public static EmailClient getInstanceFromConfig(String accountName) throws Exception {
        XmlHelper xml = new XmlHelper(Config.getConfigDefaultFile());
        String xmlEmailRoot = "//config/email-server/account[@name='" + accountName + "']";
        String host = xml.getValue(xmlEmailRoot + "/smtp-host");
        String port = xml.getValue(xmlEmailRoot + "/smtp-port");
        String userName = xml.getValue(xmlEmailRoot + "/user-name");
        String password = xml.getValue(xmlEmailRoot + "/user-password");
        return new EmailClient(host, port, userName, password);
    }

    public void popTest() throws Exception {
        Properties props = new Properties();

        String host = "pop.yeah.net";
        String username = "eliyang";
        String password = "8049391";
        String provider = "pop3";

        Session session = Session.getDefaultInstance(props, null);
        Store store = session.getStore(provider);
        store.connect(host, username, password);

        System.out.println("开始");
        Folder folder = store.getDefaultFolder();
        if (folder == null) {
            System.out.println("No INBOX");
            System.exit(1);
        }
        folder = folder.getFolder("INBOX");
        if (folder == null) {
            System.out.println("Invalid folder");
            System.exit(1);
        }
        folder.open(Folder.READ_ONLY);
        System.out.println("Message: " + folder.getMessageCount());
        Message[] messages = folder.getMessages();
        for (int i = 0; i < messages.length; i++) {
            System.out.println("Message " + (i + 1));
            messages[i].writeTo(System.out);
        }
        folder.close(false);
        store.close();
        System.out.println("结束");
    }

    public void send(String subject, String content) {
        this.send(null, subject, content);
    }

    public void toEmail(String loginUserID) throws Exception{
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT ");
        sql.append(" u.`name`, ");
        sql.append(" email.* ");
        sql.append(" FROM ");
        sql.append(" system_user u,system_emailaccount email ");
        sql.append(" WHERE ");
        sql.append(" 1 = 1 ");
        sql.append(" AND u.state = 0 ");
        sql.append(" AND u.EmailAccountId = email.Id ");
        sql.append(" AND u.Id = '"+loginUserID+"' ");
        List<Map<String, Object>> list = MySQLDao.query(sql.toString());
//        for (int i = 0;i<list.size();i++){
//            Map<String, Object> user = list.get(i);
//            System.out.println(user);
//        }
        String from = list.get(0).get("Email").toString();
        String password = list.get(0).get("Password").toString();
        String name = list.get(0).get("name").toString();
        String to = list.get(1).get("Email").toString();
        send("zhangshunqing@hopecore.cn","4j217jh1","张舜清","zhangshunqing@hopecore.cn","测试","内容",null);
    }
    public void send(String from,String passwd,String accountName, String to, String subject, String content, String fileName) throws Exception {
//    public void send(String from, String to, String subject, String content, String fileName) throws Exception {
//        String host = "smtp.yeah.net";
//        String port = "25";
//        String userName = "eliyang";
//        String password = "8049391";
        if (accountName.equals("admin")){
            host = "smtp.yeah.net";
        }else {
            host = "smtp.qq.com";
        }
        port = "25";
        userName = from;
        password = passwd;



        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        Session session = Session.getDefaultInstance(props);
        try {
            // 显示发送人名称
            String name="";
            name=javax.mail.internet.MimeUtility.encodeText(accountName);

            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(name+"<"+from+">"));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(subject);
            msg.setText(content);

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            //fill message
            messageBodyPart.setText(content);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            messageBodyPart = new MimeBodyPart();
            if (fileName != null && !fileName.equals("")) {
                DataSource source = new FileDataSource(fileName);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(fileName);
                multipart.addBodyPart(messageBodyPart);
            }
            msg.setContent(multipart);
            msg.saveChanges();
            Transport transport = session.getTransport("smtp");
            transport.connect(host, userName, password);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send(String to, String subject, String content) {
        if (to == null || to.equals("")) {
            to = "eliyang@qq.com";
        }
        String host = "smtp.yeah.net";
        String port = "25";
        String userName = "eliyang";
        String password = "8049391";
        String from = "eliyang@yeah.net";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        Session session = Session.getDefaultInstance(props);
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(subject);
            msg.setText(content);
            msg.saveChanges();
            Transport transport = session.getTransport("smtp");
            transport.connect(host, userName, password);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
//        EmailClient email = new EmailClient();
//        System.out.println("Begin");
//        email.send("eliyang@yeah.net", "932283463@qq.com", "这是主21312题3", "这是内容Good", null);
//        email.toEmail("22792462064448F4989AB30DDA13F898");
//        email.send("eliyang@yeah.net","8049391","admin","zhangshunqing@hopecore.cn","测试","内容",null);
//        System.out.println("end");

        // 生成UUID
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);
    }

}
