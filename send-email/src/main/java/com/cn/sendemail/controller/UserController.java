package com.cn.sendemail.controller;

import com.cn.sendemail.context.Constant;
import com.cn.sendemail.context.ServletUtils;
import com.cn.sendemail.model.User;
import com.cn.sendemail.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/sendEmail",method= RequestMethod.GET)
    public void findAll(HttpServletResponse response){
        Map<String, Object> result = new HashMap<>();
        try {
            List<User> list =  userService.sendEmail();
            result.put(Constant.RESPONSE_DATA, list);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        } catch (Exception ex) {
            result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
        }
        ServletUtils.writeToResponse(response, result);
    }

}
