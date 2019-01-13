package com.lanyu96.querylogistics.uitl;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class MailUtil {
    /**
     * 创建一封只包含文本的简单邮件
     *
     * @param session 和服务器交互的会话
     * @param sendMail 发件人邮箱
     * @param receiveMail 收件人邮箱
     * @return
     * @throws Exception
     */
    private static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail, String neirong) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);


        // 2. From: 发件人
        message.setFrom(new InternetAddress(sendMail,"发件人","UTF-8"));


        // 3. To: 收件人(可以增加多个收件人、抄送、密送)
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "收件人", "UTF-8"));


        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Log.i("TEST11", format.format(new Date()));
        // 4. Subject: 邮件主题
        message.setSubject(format.format(new Date()), "UTF-8");


        // 5. Content: 邮件正文(可以使用html标签)
        message.setContent(neirong, "text/html;charset=UTF-8");


        // 6. 设置发件时间
        message.setSentDate(new Date());


        // 7. 保存设置
        message.saveChanges();


        return message;
    }
    public static void sendMail(String neirong) throws Exception{
        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议(JavaMail规范要求)
        props.setProperty("mail.smtp.host", "smtp.163.com");     // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证


        //如果遇到ssl类错误,请打开一下代码
        /*final String smtpPort = "465";
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);*/
        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getDefaultInstance(props);
        // 设置为debug模式, 可以查看详细的发送 log
        session.setDebug(true);
        // 3. 创建一封邮件
        MimeMessage message = createMimeMessage(session, "17130381522@163.com", "zhouhang96@163.com",neirong);//我这里是以163邮箱为发信邮箱测试通过
        // 4. 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();
        transport.connect("17130381522@163.com", "zh112233");
        // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());
        // 7. 关闭连接
        transport.close();
    }

}
