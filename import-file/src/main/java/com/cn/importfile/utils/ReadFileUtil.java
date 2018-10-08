package com.cn.importfile.utils;

import com.cn.importfile.model.User;
import com.csvreader.CsvReader;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import tool.util.DateUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReadFileUtil {
    /**
     * 从 xls 或 xlsx 文件中读取  第一种方式
     *
     * @param fileType
     * @return
     */
    public static List<User> readUserFromXls(Workbook book, String fileType){
        List<User> list = new ArrayList<>();
        if (book != null) {
            Sheet sheet = book.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                System.out.println(row);
                String[] cells = new String[4];
                cells[0] = row.getCell(0).getStringCellValue();
                cells[1] = row.getCell(1).getStringCellValue();
                cells[2] = row.getCell(2).getStringCellValue();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//日期格式化
                cells[3] = sdf.format(row.getCell(3).getDateCellValue());
                User user = wrapUser(cells);
                list.add(user);
            }
        }
        return list;
    }

    /**
     * 读取Excel数据内容 第一种方式(这种更具有通用性)
     *
     * @return Map 包含单元格数据内容的Map对象
     */
    public static  List<List<String>> readExcelContent(Workbook book) {
        List<List<String>> content = new ArrayList<List<String>>();
        Sheet sheet = book.getSheetAt(0);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        for (int i = 1; i <= rowNum; i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                int colNum = row.getPhysicalNumberOfCells();
                int j = 0;
                List<String> cellValue = new ArrayList<String>();
                while (j < colNum) {
                    Cell cell = row.getCell(j);
                    if (cell != null) {
                        Object obj = getCellFormatValue(cell);
                        String value = obj != null ? obj.toString().replace("/t", "").trim() : "";
                        cellValue.add(value);
                    }
                    j++;
                }
                content.add(cellValue);
            } else {
                continue;
            }
        }
        return content;
    }

    /**
     * 从 txt 文件中读取
     *
     * @param is
     * @return
     */
    public static List<User> readUserFromTxt(InputStream is) throws Exception{
        List<User> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, getStreamCharset(is)))) {
            String line;
            // 第一行为表头，需要进行跳过
            boolean firstLine = true;
            while (null != (line = reader.readLine())) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                if (StringUtils.isNotBlank(StringUtils.trim(line))) {
                    String[] cells = StringUtils.splitByWholeSeparatorPreserveAllTokens(line, ",");
                    if (cells.length != 4) {
                        throw new Exception("模板中的数据不足4个");
                    } else {
                        list.add(wrapUser(cells));
                    }
                }
            }
        } catch (IOException e) {
            throw new IOException("解析非特邀用户txt文件失败");
        }

        return list;
    }


    /**
     * 从 csv 文件中读取
     *
     * @param is
     * @return
     * @throws IOException
     */
    public static List<User> readUserFromCsv(InputStream is) throws IOException {
        CsvReader reader = new CsvReader(is, Charset.forName(getStreamCharset(is)));
        // 跳过第一行，第一行是表头
        reader.readHeaders();
        List<User> list = new ArrayList<>();
        while (reader.readRecord()) {
            String[] cells = StringUtils.splitByWholeSeparatorPreserveAllTokens(reader.getRawRecord(), ",");
            if (cells.length == 1) {
                continue;
            } else {
                list.add(wrapUser(cells));
            }
        }
        return list;
    }

    /**
     * 包装出一个  User
     *
     * @param cells
     * @return
     */
    private static User wrapUser(String[] cells) {
        User user = new User();
        Optional.ofNullable(StringUtils.trimToNull(cells[0])).ifPresent(user::setId);
        Optional.ofNullable(StringUtils.trimToNull(cells[1])).ifPresent(user::setUsername);
        Optional.ofNullable(StringUtils.trimToNull(cells[2])).ifPresent(user::setPassword);
        Optional.ofNullable(StringUtils.trimToNull(cells[3])).ifPresent(value -> user.setStartDate(formatDate(value)));
        return user;
    }

    /**
     * 获取流的编码，只支持 UTF-8 和 GBK
     *
     * @param inputStream
     * @return
     */
    private static String getStreamCharset(InputStream inputStream) {
        inputStream.mark(3);
        String charset = "GBK";
        byte[] b = new byte[3];
        try {
            inputStream.read(b);
            if (b[0] == -17 && b[1] == -69 && b[2] == -65) {
                charset = "UTF-8";
            } else if (b[0] == -27 && b[1] == -89 && b[2] == -109) {
                charset = "UTF-8";
            }
        } catch (IOException e) {

        } finally {
            try {
                inputStream.reset();
            } catch (IOException e) {
            }
        }
        return charset;
    }

    /**
     * 日期格式化
     *
     * @param date
     * @return
     */
    private static Date formatDate(String date) {
        return Optional.ofNullable(DateUtil.valueOf(date, "yyyy-MM-dd HH:mm"))
                .orElse(DateUtil.valueOf(date, "yyyy/MM/dd HH:mm"));
    }

    /**
     * 根据Cell类型设置数据
     *
     * @param cell
     * @return
     * @author zengwendong
     */
    private static Object getCellFormatValue(Cell cell) {
        Object cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:// 如果当前Cell的Type为NUMERIC
                case Cell.CELL_TYPE_FORMULA: {
                    // 判断当前的cell是否为Date
                    if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        cellvalue = tool.util.DateUtil.dateStr(date, "yyyy/MM/dd HH:mm:ss");
                    } else {// 如果是纯数字

                        // 取得当前Cell的数值
                        cellvalue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case Cell.CELL_TYPE_STRING:// 如果当前Cell的Type为STRING
                    // 取得当前的Cell字符串
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                default:// 默认的Cell值
                    cellvalue = "";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;
    }
}
