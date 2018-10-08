package com.cn.importfile.service;


import com.cn.importfile.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    List<User> importFile(MultipartFile file) throws Exception;

    List<User> findAll();

}
