package com.cn.importfile.context;

/**
 * TODO 后台列表数据导出 常量
 *
 * @author lyang
 * @date 2017年4月13日 下午14:40:52
 */
public final class ExportConstant {

    /** 导出暂定为5000条，如有需求，酌情更改*/
    /*** 分页起始*/
    public static final int STRAT_LIMIT = 0;

    /*** 分页结束*/
    public static final int END_LIMIT = 5000;

    /**
     * 导出 表头
     */
    public static final String[] EXPORT_LIST_HEARDERS = {"id", "用户名", "密码", "地址"};
    /**
     * 导出 属性数组
     */
    public static final String[] EXPORT_LIST_FIELDS = {"id", "username", "password", "startDate"};

}

