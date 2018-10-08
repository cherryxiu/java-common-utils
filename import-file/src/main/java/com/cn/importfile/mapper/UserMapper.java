package com.cn.importfile.mapper;

import com.cn.importfile.model.User;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

@Mapper
public interface UserMapper {

     List<User> findByPrimary();
}
