package com.cn.sendemail.service.impl;

import com.cn.sendemail.mapper.UserMapper;
import com.cn.sendemail.model.User;
import com.cn.sendemail.service.UserService;
import com.cn.sendemail.utils.EmailUtil;
import com.cn.sendemail.utils.ExcelSave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    public List<User>  sendEmail(){
        List<User> list = userMapper.findByPrimary();
        try{
            //生成邮件需要的附件
            String filePath = ExcelSave.downloadFile(list);

            //发送邮件
            Map<String, Object> emailContent = new HashMap<>();
            emailContent.put("to","134***@163.com;;;\"\";134***@163.com");//收件人  to的正常格式  135***@163.com;134***@163.com
            emailContent.put("title","HELLO");//邮件标题
            emailContent.put("content","<html><body><h3>WELCOME TO CHINA!!!</h3></body></html>");//邮件内容
            emailContent.put("filePath",filePath);//附件路径
            EmailUtil.sendTextEmail(emailContent);
        } catch (Exception e){
            e.printStackTrace();
        }
        return   list;
    }
}
