package com.cn.sendemail.utils;

import com.cn.sendemail.model.ExcelHelper;
import com.cn.sendemail.model.User;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.enmus.ExcelType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelSave {
    /**
     * 下载文件到服务器
     */
    public static String downloadFile(List list){
        String dt = DateFormatUtil.getFormatDateTimeByExpress();
        String filePath = "./未来数据" + dt + ".xls";
        try{
            List<ExcelHelper> excelHelpers = new ArrayList<>();
            excelHelpers.add(new ExcelHelper("未来数据" + dt, User.class, list));
            Workbook workbook = ExcelSave.excelExportSheet(excelHelpers);
            File file = new File(filePath);
            OutputStream out = new FileOutputStream(file);//如果文件不存在会自动创建
            workbook.write(out);
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return filePath;
    }

    /**
     * 导出excel
     */
    private static Workbook excelExportSheet(List<ExcelHelper> list) {
        List<Map<String, Object>> sheetsList = new ArrayList<>();
        for (ExcelHelper helper : list) {
            ExportParams params = new ExportParams();
            params.setSheetName(helper.getSheetName());
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("title", params);
            dataMap.put("entity", helper.getCls());
            dataMap.put("data", helper.getList());
            sheetsList.add(dataMap);
        }
        return ExcelExportUtil.exportExcel(sheetsList, ExcelType.HSSF);
    }
}

