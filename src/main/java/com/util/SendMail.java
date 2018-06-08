package com.util;

import com.sun.mail.util.MailSSLSocketFactory;
import org.junit.Test;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;

public class SendMail {
    @Test
    public static void sendMail (){

        Properties props = new Properties();
        // 开启debug调试
        props.setProperty("mail.debug", "true");
        // 发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");
        // 设置邮件服务器主机名
        props.setProperty("mail.host", "smtp.qq.com");
        // 发送邮件协议名称
        props.setProperty("mail.transport.protocol", "smtp");

        MailSSLSocketFactory sf = null;
        Message msg = null;
        Transport transport = null;
        try {
            sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.ssl.socketFactory", sf);

            Session session = Session.getInstance(props);

            msg = new MimeMessage(session);
            msg.setSubject("错误报告");
            // 设置邮件内容
            StringBuffer strBody = new StringBuffer();
            strBody.append("糟糕！！！程序出错了！！！登不上了！！！</br>");
            strBody.append("<font size=\"7\" style=\"font-weight:bold;\">别玩了！！！</font>");
            //163  18846054508邮箱
//            msg.setText(strBody.toString());
            msg.setContent(strBody.toString(),"text/html;charset=UTF-8");
            msg.setSentDate(new Date());
            msg.setFrom(new InternetAddress("18846054508@163.com", "stackoverflow自动登录程序", "utf-8"));

            transport = session.getTransport();
            transport.connect("smtp.163.com", "18846054508@163.com", "163shouquanma");

            transport.sendMessage(msg, new Address[] { new InternetAddress("1958321982@qq.com") });
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            try {
                transport.close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

}
