package com.cn.importfile.context;



/**
 * 公用常量类定义
 */
public class Constant {

	/** session用户 **/
	public static final String SESSION_OPERATOR = "session_operator";
	
	public static final String RESPONSE_CODE = "code";

	public static final String RESPONSE_CODE_MSG = "msg";
	
	public static final String RESPONSE_DATA = "data";
	
	
	public static final String OPERATION_SUCCESS = "操作成功";
	
	public static final String OPERATION_FAIL = "操作失败";

	public static final String BANK_BIND_FAIL = "绑卡失败，请检查您的信息是否填写正确";

	public static final int SIGN_FAIL = 99; // 验签失败

	public static final int SUCCEED_CODE_VALUE = 200; // 成功 插入 、删除 更新 修改

	public static final int FAIL_CODE_PARAM_INSUFFICIENT = 300;	//参数列表不完整 

	public static final int FAIL_CODE_VALUE = 400; // 失败 插入 、删除 更新 修改
	
	public static final int FAIL_CODE_PWD = 401; // 交易密码错误
	
	public static final int PERM_CODE_VALUE = 403; // 无权限访问

	public static final int OTHER_CODE_VALUE = 500; // 其他异常
	
	public static final int NOSESSION_CODE_VALUE = 800; // session失效
	
	public static final int CLIENT_EXCEPTION_CODE_VALUE = 998; // 连接异常（除请求超时）
	
	public static final int TIMEOUT_CODE_VALUE = 999; // 请求超时
	
	

	
}
