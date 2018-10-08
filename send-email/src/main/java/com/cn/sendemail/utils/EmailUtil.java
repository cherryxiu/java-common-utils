package com.cn.sendemail.utils;

import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.*;

public  class EmailUtil {

    public  static  Boolean sendTextEmail(Map<String, Object> emailContent) throws Exception {
        boolean bret = false;
        try {
            final Properties props = new Properties();
            //设置邮件基本信息
            setBasicInfo(props);

            // 使用环境属性和授权信息，创建邮件会话
            Authenticator authenticator = new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    String userName = props.getProperty("mail.user");
                    String password = props.getProperty("mail.password");
                    return new PasswordAuthentication(userName, password);
                }
            };
            Session mailSession = Session.getInstance(props, authenticator);

            // 创建邮件消息
            MimeMessage message = new MimeMessage(mailSession);
            setMsgContent(props, message, emailContent);
            //setMsgImage(props, message, emailContent);

            // 发送邮件
            Transport  transport = mailSession.getTransport();
            transport.connect();
            transport.send(message);
            transport.close();

            bret = true;
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bret;
    }

    private static void setBasicInfo(Properties props){
        props.put("mail.smtp.auth", "true");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "smtp.163.com");
        //你自己的邮箱
        props.put("mail.user", "134***@163.com");
        //你开启pop3/smtp时的验证码
        props.put("mail.password", "****");
        props.put("mail.smtp.port", "25");
        props.put("mail.smtp.starttls.enable", "true");
    }

    //发送附件
    private static void setMsgContent(Properties props, MimeMessage message, Map<String, Object> emailContent){
        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // 设置发件人
            String username = props.getProperty("mail.user");
            InternetAddress form = new InternetAddress(username);
            helper.setFrom(form);

            //设置收件人    排除to中存在 ;; 或 ;""; 的数据
            String[] toEmailArray = doToUser(emailContent.get("to").toString());
            helper.setTo(toEmailArray);

            // 设置邮件标题
            helper.setSubject(emailContent.get("title").toString());

            // 设置邮件的内容体
            helper.setText(emailContent.get("content").toString(),true);

            //设置邮件附件
            File file = new File(emailContent.get("filePath").toString());
            if (!file.exists()) {
                throw new RuntimeException("附件" + emailContent.get("filePath").toString() + "不存在！");
            }
            helper.addAttachment(file.getName(), file);

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    //使用内嵌图片
    private static void setMsgImage(Properties props, MimeMessage message, Map<String, Object> emailContent){
        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // 设置发件人
            String username = props.getProperty("mail.user");
            InternetAddress form = new InternetAddress(username);
            helper.setFrom(form);

            //设置收件人    排除to中存在 ;; 或 ;""; 的数据
            String[] toEmailArray = doToUser(emailContent.get("to").toString());
            helper.setTo(toEmailArray);

            // 设置邮件标题
            helper.setSubject(emailContent.get("title").toString());

            String id = "pic";
            // 设置邮件的内容体
            helper.setText("<html><body>WELCOME TO CHINA<img src=\"cid:" +id+ "\"/></body></html>",true);

            //设置邮件附件

            File file = new File( "E:\\test\\1.jpg");
            if (!file.exists()) {
                throw new RuntimeException("附件" + emailContent.get("filePath").toString() + "不存在！");
            }
            //FileSystemResource img = new FileSystemResource(file); 可以不使用FileSystemResource
            helper.addAttachment(file.getName(), file);
            helper.addInline(id,file);
        }catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static String[] doToUser(String to){
        String[] toEmailArray = to.split(";");
        List<String> tmp = new ArrayList<String>();
        for(String str:toEmailArray){
            if((Detect.notEmpty(str) && !("\"\"").equals(str))){
                tmp.add(str);
            }
        }
        toEmailArray = tmp.toArray(new String[0]);//将list转成数组
        return toEmailArray;
    }
}
