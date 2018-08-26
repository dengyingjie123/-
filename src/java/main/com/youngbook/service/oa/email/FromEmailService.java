package com.youngbook.service.oa.email;

/**
 * Created by haihong on 2015/6/5.
 *
 * @author <a href="http://c.hopewealth.net/pages/viewpage.action?pageId=27066425
 * " target="_blank">Zhouhaihong</a>
 */

import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.EmailClient;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.oa.email.FromEmailPO;
import com.youngbook.service.BaseService;
import org.apache.struts2.ServletActionContext;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.*;

public class FromEmailService extends BaseService {
    private String host = "pop.yeah.net";
    private String username = "eliyang";
    private String password = "8049391";
    private String provider = "pop3";

    /**
     * 根据sql语句进行查询数据
     * @param fromEmail
     * @param conditions
     * @param request
     * @return
     * @throws Exception
     */
    public Pager list (FromEmailPO fromEmail, List<KVObject> conditions, HttpServletRequest request) throws Exception {
        //构建sql语句
        StringBuffer sbSQL = new StringBuffer ();
        sbSQL.append ("select * from OA_FromEmail");
        sbSQL.append (" where 1=1");
        sbSQL.append (" and state=0");


        QueryType queryType = new QueryType (Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        //获取分页对象
        return Pager.query (sbSQL.toString (), fromEmail, conditions, request, queryType);
    }

    /**
     * 添加或修改数据，并修改数据状态
     *
     * @param fromEmail
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int insertOrUpdate (FromEmailPO fromEmail, UserPO user, Connection conn) throws Exception {
        int count = 0;
        // 新增
        if ( fromEmail.getId ().equals ("") ) {
            fromEmail.setSid (MySQLDao.getMaxSid ("OA_FromEmail", conn));
            fromEmail.setId (IdUtils.getUUID32 ());
            fromEmail.setState (Config.STATE_CURRENT);
            fromEmail.setOperatorId (user.getId ());
            fromEmail.setOperateTime (TimeUtils.getNow ());
            count = MySQLDao.insert (fromEmail, conn);
        }
        // 更新
        else {
            FromEmailPO temp = new FromEmailPO ();
            temp.setSid (fromEmail.getSid ());
            temp = MySQLDao.load (temp, FromEmailPO.class);
            temp.setState (Config.STATE_UPDATE);
            count = MySQLDao.update (temp, conn);
            if ( count == 1 ) {
                fromEmail.setSid (MySQLDao.getMaxSid ("OA_FromEmail", conn));
                fromEmail.setState (Config.STATE_CURRENT);
                fromEmail.setOperatorId (user.getId ());
                fromEmail.setOperateTime (TimeUtils.getNow ());
                count = MySQLDao.insert (fromEmail, conn);
            }
        }
        if ( count != 1 ) {
            throw new Exception ("数据库异常");
        }
        return count;
    }

    /**
     * 根据制定的ID获取数据
     *
     * @param id
     * @return
     * @throws Exception
     */
    public FromEmailPO loadFromEmailPO (String id) throws Exception {
        FromEmailPO po = new FromEmailPO ();
        po.setId (id);
        po.setState (Config.STATE_CURRENT);
        po = MySQLDao.load (po, FromEmailPO.class);

        return po;
    }

    /**
     * 根据条改编数据的状态
     *
     * @param fromEmail
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int delete (FromEmailPO fromEmail, UserPO user, Connection conn) throws Exception {
        int count = 0;

        fromEmail.setState (Config.STATE_CURRENT);
        fromEmail = MySQLDao.load (fromEmail, FromEmailPO.class);
        fromEmail.setState (Config.STATE_UPDATE);
        count = MySQLDao.update (fromEmail, conn);
        if ( count == 1 ) {
            fromEmail.setSid (MySQLDao.getMaxSid ("OA_FromEmail", conn));
            fromEmail.setState (Config.STATE_DELETE);
            fromEmail.setOperateTime (TimeUtils.getNow ());
            fromEmail.setOperatorId (user.getId ());
            count = MySQLDao.insert (fromEmail, conn);
        }

        if ( count != 1 ) {
            throw new Exception ("删除失败");
        }

        return count;
    }

    /**
     * 获取数据库中所有email的唯一邮件编号集合
     *
     * @param fromEmail
     * @param conn
     * @return
     * @throws Exception
     */
    public Set getEmailIDs (FromEmailPO fromEmail, Connection conn) throws Exception {
        Set<String> emailIds = new HashSet<String> ();
        String SQL = "select EmailId from OA_FromEmail where state=0";
        List list = MySQLDao.query (SQL, FromEmailPO.class, null, conn);
        for ( int i = 0; i < list.size (); i++ ) {
            fromEmail = (FromEmailPO) list.get (i);
            emailIds.add (fromEmail.getEmailID ());
        }
        return emailIds;
    }

    /**
     * 获取邮件地址 跟新列表数据
     *
     * @param conn
     * @return
     */
    public int refresh (FromEmailPO fromEmail, UserPO user, Connection conn) throws Exception {
        //获取数据库所有邮件耳朵唯一ID
        Set<String> emailIds = this.getEmailIDs (fromEmail, conn);

        Properties props = new Properties ();


        Session session = Session.getDefaultInstance (props, null);
        Store store = session.getStore (provider);
        store.connect (host, username, password);

        //返回一个folder 对象
        Folder folder = store.getDefaultFolder ();
        if ( folder == null ) {
            throw new Exception ("找不到文件夹");
        }

        folder = folder.getFolder ("INBOX");
        if ( folder == null ) {
            throw new Exception ("找不到文件夹");
        }

        folder.open (Folder.READ_ONLY);

        //System.out.println ( "Message: " + folder.getMessageCount () );

        Message[] messages = folder.getMessages ();

        StringBuffer content = null;
        int DBcount = 0;
        for ( int i = 0; i < messages.length; i++ ) {
            fromEmail = new FromEmailPO ();
            //获取邮件唯一编号
            MimeMessage mime = (MimeMessage) messages[ i ];
            if ( emailIds.contains (mime.getMessageID ()) ) {
                continue;
            }
            fromEmail.setEmailID (mime.getMessageID ());

            // 获取所有人的收件人地址对象
            content = new StringBuffer ();
            Address[] address = messages[ i ].getAllRecipients ();
            //获取收件人Email的地址
            InternetAddress internetAddress = (InternetAddress) address[ 0 ];
            fromEmail.setToEmail (internetAddress.getAddress ());
            fromEmail.setToName (internetAddress.getPersonal ());


            //获取发件人的email地址
            address = messages[ i ].getFrom ();
            //获取收件人Email的地址
            internetAddress = (InternetAddress) address[ 0 ];

            fromEmail.setFromEmail (internetAddress.getAddress ());
            fromEmail.setFromName (internetAddress.getPersonal ());
            //发送时间
            fromEmail.setToTime (TimeUtils.getDateString (messages[ i ].getSentDate ()));
            fromEmail.setEmaioTitle (messages[ i ].getSubject ()); //主题

//            //内容
            if ( messages[ i ].getContent () instanceof Multipart ) {
                Multipart multipart = (Multipart) messages[ i ].getContent ();
                int count = multipart.getCount ();
                for ( int c = 0; c < count; c++ ) {
                    BodyPart part = multipart.getBodyPart (c); // 单个部件类型

                    content.append (part.getContent ().toString ());
                }
            } else {
                content.append (messages[ i ].getContent ());
            }
            fromEmail.setEmailContent (content.toString ());
            //添加数据库数据
            DBcount = this.insertOrUpdate (fromEmail, user, conn);
            if ( DBcount != 1 ) {
                throw new Exception ("数据库异常");
            }

            content = new StringBuffer ();
        }
        folder.close (false);
        store.close ();

        return DBcount;
    }

    /**
     * 将指定指定替换成指定字符
     * @param content 提供的字符串
     * @param target 被换掉的字符
     * @param replacement 新字符
     * @return
     */
    public String ContentReplaceAll (String content, String target, String replacement) {
        return content.replaceAll (target, replacement);
    }

    /**
     *  发送邮件
     * @param fromEmail
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int FromToEmail (FromEmailPO fromEmail, String password, UserPO user, Connection conn) throws Exception {
        EmailClient email = new EmailClient ();
        try {
            System.out.println (user.getName ());
            email.send (fromEmail.getFromEmail (), password, user.getName (), fromEmail.getToEmail (), fromEmail.getEmaioTitle (), fromEmail.getEmailContent (), null);
        } catch ( Exception e ) {
            new Exception ("邮件发送失败");
            return -1;
        }

        return this.insertOrUpdate (fromEmail, user, conn);

    }

    public List<Map<String, Object>> loadEmailForUser (UserPO user) throws Exception {
        StringBuffer sql = new StringBuffer ();
        sql.append (" SELECT ");
        sql.append (" u.`name`, ");
        sql.append (" email.* ");
        sql.append (" FROM ");
        sql.append (" system_user u,system_emailaccount email ");
        sql.append (" WHERE ");
        sql.append (" 1 = 1 ");
        sql.append (" AND u.state = 0 ");
        sql.append (" AND u.EmailAccountId = email.Id ");
        sql.append (" AND u.Id = '" + Database.encodeSQL (user.getId ()) + "' ");
        List<Map<String, Object>> list = MySQLDao.query (sql.toString ());
        if ( list.size () < 0 ) {
            new Exception ("数据库异常");
        }
        return list;
    }

    public static void sendEmailAttachment (FromEmailPO fromEmail, String attachmentPath) throws Exception {
        System.out.println ("START++++++++++++++++++");
        System.out.println (fromEmail.getEmailContent ());
        System.out.println (fromEmail.getFromEmail ());
        System.out.println (fromEmail.getEmaioTitle ());
        System.out.println (attachmentPath);
        String sendHost = "localhost";
        String sendProtocol = "smtp";
        String fromAddr = fromEmail.getFromEmail ();
        String toAddr = fromEmail.getToEmail ();

        Properties prop = new Properties ();
        //  prop.put("mail.Transport.protocol" , "smtp");
        prop.put ("mail.Transport.protocol", sendProtocol);
        prop.put ("mail.smtp.host", sendHost);

        Session session = Session.getDefaultInstance (prop);


        Message msg = new MimeMessage (session);
        InternetAddress[] toAddrs = InternetAddress.parse (fromAddr);
        msg.setRecipients (Message.RecipientType.TO, toAddrs);
        msg.setSubject (fromEmail.getEmaioTitle ());
        msg.setSentDate (new Date ());
        msg.setFrom (new InternetAddress (fromAddr));

        Multipart multipart = new MimeMultipart ();
        //设置正文
        MimeBodyPart p1 = new MimeBodyPart ();
        p1.setText (fromEmail.getEmailContent ());
        multipart.addBodyPart (p1);
        //设置附件
        MimeBodyPart p2 = new MimeBodyPart ();
        FileDataSource fds = new FileDataSource (attachmentPath);
        p2.setDataHandler (new DataHandler (fds));
        p2.setDisposition (Part.ATTACHMENT);
        p2.setFileName (fds.getName ());
        multipart.addBodyPart (p2);

        msg.setContent (multipart);
        Transport.send (msg);
        System.out.println ("Done");

    }

    public void downloadAll () throws Exception {
        System.out.println ("Enter download Service");
        Properties props = new Properties ();


        Session session = Session.getDefaultInstance (props, null);
        Store store = session.getStore (provider);
        store.connect (host, username, password);
        System.out.println ("Enter download Service 1");
        //返回一个folder 对象
        Folder folder = store.getDefaultFolder ();
        if ( folder == null ) {
            throw new Exception ("找不到文件夹");
        }

        folder = folder.getFolder ("INBOX");
        if ( folder == null ) {
            throw new Exception ("找不到文件夹");
        }

        folder.open (Folder.READ_ONLY);
        System.out.println ("Enter download Service 2");
        //System.out.println ( "Message: " + folder.getMessageCount () );

        Message[] messages = folder.getMessages ();
        for ( int i = 1; i < messages.length; i++ ) {
            if ( !messages[ i ].getFlags ().contains (Flags.Flag.FLAGGED) ) {
                Message msg = messages[ i ];
                Object body = msg.getContent ();
                if ( body instanceof Multipart ) {
                    processMultipart ((Multipart) body, msg);
                } else {
                    processPart (msg, msg);
                }
            }
        }


    }

    private void processMultipart (Multipart mp, Message msg) throws Exception {
        System.out.println ("Enter process Multipart ");
        for ( int i = 0; i < mp.getCount (); i++ ) {
            processPart (mp.getBodyPart (i), msg);
        }
    }

    private String uploadFileName;

    private void processPart (Part part, Message msg) throws Exception {
        System.out.println ("Enter process Part ");
        msg.setFlag (Flags.Flag.FLAGGED, true);
        String fileName = part.getFileName ();
        String disposition = part.getDisposition ();
        String contentType = part.getContentType ();

        if ( contentType.toLowerCase ().startsWith ("multipart/") ) {
            processMultipart ((Multipart) part.getContent (), msg);
        } else if ( fileName == null && (Part.ATTACHMENT.equalsIgnoreCase (disposition) || !contentType.toLowerCase ().startsWith ("text/plain")) ) {
            System.out.println ("Enter fileNAme = null");
            fileName = File.createTempFile ("attachment", ".data").getName ();
        }

        if ( fileName != null ) {
            String name = fileName;
            Date date = new Date ();
            SimpleDateFormat sdf = new SimpleDateFormat ("yyyy/MM/dd/HH");
            String time = sdf.format (date);
            // System.out.println(time);
            String path = ServletActionContext.getServletContext ().getRealPath ("/upload/" + time);
            File file = new File (path);
            if ( !file.exists () ) {
                file.mkdirs ();
            }
            String suffix = fileName.substring (name.lastIndexOf (".") + 1);
            setUploadFileName (IdUtils.getUUID32 () + "." + suffix);//将文件名设为时间+UUID的格式，如15/04/03/uuid.xxx
            File file2 = new File (file, name);
            for ( int i = 1; file2.exists (); i++ ) {
                String newName = i + "_" + uploadFileName;
                file2 = new File (file, newName);
            }

            OutputStream out = new BufferedOutputStream (new FileOutputStream (file2));
            InputStream in = new BufferedInputStream (part.getInputStream ());
            int b;
            while ( (b = in.read ()) != -1 ) {
                out.write (b);
            }
            out.close ();
            in.close ();
            System.out.println ("Download Success!");
        }
    }


//    public static void main(String[] args) throws Exception{
//        FromEmailPO e = new FromEmailPO();
//        e.setEmailContent("qwdqd");
//        e.setFromEmail("eliyang@yeah.net");
//        e.setEmaioTitle("test");
//        e.setToEmail("arctic@mydomain.com");
//        String path = "C:\\Users\\Administrator\\Pictures\\117362106.jpg";
//
//        sendEmailAttachment(e , path);
//    }


    public String getUploadFileName () {
        return uploadFileName;
    }

    public void setUploadFileName (String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }
}