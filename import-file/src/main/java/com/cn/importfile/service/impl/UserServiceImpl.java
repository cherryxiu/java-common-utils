package com.cn.importfile.service.impl;

import com.cn.importfile.mapper.UserMapper;
import com.cn.importfile.model.User;
import com.cn.importfile.service.UserService;
import com.cn.importfile.utils.ReadFileUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User>  importFile(MultipartFile file) throws Exception{
        String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1);
        InputStream is = file.getInputStream();
        Workbook book = null;
        List<User> list = new ArrayList<>();
        switch (fileType){
            case "txt":
                list = ReadFileUtil.readUserFromTxt(is);
                break;
            case "csv":
                list = ReadFileUtil.readUserFromCsv(is);
                break;
            case "xls":
                book = new HSSFWorkbook(is);
                list = ReadFileUtil.readUserFromXls(book, "xls");
                break;
            case "xlsx":
                book = new XSSFWorkbook(is);
                list = ReadFileUtil.readUserFromXls(book, "xlsx");
                break;
                default:
                    throw new Exception("文件类型错误");
        }

        //todo 将list集合的数据遍历插入数据库
        return list;
    }

    public  List<User> findAll(){
        List<User> list = userMapper.findByPrimary();
        return list;
    }
}
