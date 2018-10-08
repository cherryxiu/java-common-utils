package com.cn.importfile.controller;


import com.cn.importfile.context.Constant;
import com.cn.importfile.context.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.context.annotation.Scope;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.AbstractController;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 基类action
 * @version 1.0
 * @author 吴国成
 * @created 2014年9月23日 下午1:48:28
 */
@Controller
@Scope("prototype")
public abstract class BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;


    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){
        this.request = request;  
        this.response = response;  
        this.session = request.getSession();  
    }  

	/**
	 * 初始化绑定
	 * 
	 * @param binder
	 */
	@InitBinder
	protected final void initBinderInternal(WebDataBinder binder) {
		registerDefaultCustomDateEditor(binder);
		registerDefaultCustomNumberEditor(binder);
		initBinder(binder);
	}

	private void registerDefaultCustomNumberEditor(WebDataBinder binder) {
		// 注册双精度数字格式化类型: #0.00
		NumberFormat numberFormat = new DecimalFormat("#0.00");
		binder.registerCustomEditor(Double.class, new CustomNumberEditor(
				Double.class, numberFormat, true));
	}

	protected void registerDefaultCustomDateEditor(WebDataBinder binder) {
		// 注册默认的日期格式化类型: yyyy-MM-dd
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
	
	/**
	 * 提供子类初始化表单, 子类如果要调用请重写该方法
	 * 
	 * @param binder
	 */
	protected void initBinder(WebDataBinder binder) {
	}


	
	
	@ExceptionHandler({Exception.class})
	public void exceptionHandler(Exception e, HttpServletResponse response) {
		 Map<String, Object> res = new HashMap<String, Object>();
		res.put(Constant.RESPONSE_CODE, "400");
		res.put(Constant.RESPONSE_CODE_MSG, "系统出错了，请检查参数是否正确");
		logger.error("Exception:", e);
		ServletUtils.writeToResponse(response, res);
	}
}
