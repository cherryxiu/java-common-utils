package com.cn.importfile.controller;


import com.cn.importfile.context.Constant;
import com.cn.importfile.context.ExportConstant;
import com.cn.importfile.context.ServletUtils;
import com.cn.importfile.model.User;
import com.cn.importfile.service.UserService;
import com.cn.importfile.utils.excel.JsGridReportBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    /**
     * 导入文件
     * @throws Exception
     */
    @RequestMapping(value = "/importFile",method= RequestMethod.POST)
    public void importFile(@RequestParam(value = "file") MultipartFile file) throws Exception{
        Map<String, Object> result = new HashMap<>();
        try {
            List<User> list =  userService.importFile(file);
            result.put(Constant.RESPONSE_DATA, list);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        } catch (Exception ex) {
            result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
        }
        ServletUtils.writeToResponse(response, result);
    }

    /**
     * 导出数据库文件
     *
     * @throws Exception
     */
    @RequestMapping(value = "/exportFile")
    public void borrowExport() throws Exception {
        List list =  userService.findAll();
        response.setContentType("application/msexcel;charset=UTF-8");
        // 记录取得
        String title = "用户信息Excel表";
        String[] hearders = ExportConstant.EXPORT_LIST_HEARDERS;
        String[] fields = ExportConstant.EXPORT_LIST_FIELDS;
        JsGridReportBase report = new JsGridReportBase(request, response);
        report.exportExcel(list,title,hearders,fields,"cherryxiu");
    }
}
