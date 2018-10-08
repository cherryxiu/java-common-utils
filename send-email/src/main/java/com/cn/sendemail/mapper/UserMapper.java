package com.cn.sendemail.mapper;

import com.cn.sendemail.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

        List<User> findByPrimary();
}
