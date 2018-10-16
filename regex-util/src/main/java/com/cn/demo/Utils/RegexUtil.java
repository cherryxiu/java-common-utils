package com.cn.demo.Utils;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

    public enum regexp {

        EMAIL("(?:\\w[-._\\w]*\\w@\\w[-._\\w]*\\w\\.\\w{2,3}$)"), //
        INT("^-?\\d+$"), NUM("^-?\\d*\\.{0,1}\\d+$"), //
        MOBILE("^1[3,4,5,7,8]{1}[0-9]{9}$"), //
        PASSWORD("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$");//


        private String value;

         regexp(String value) {
            this.value = value;
        }

    }

    public static boolean checkEmail(String email) {
        return Pattern.matches(regexp.EMAIL.value, email);
    }

    public static boolean checkInt(String num) {
        return Pattern.matches(regexp.INT.value, num);
    }

    public static boolean checkNum(String num) {
        return Pattern.matches(regexp.NUM.value, num);
    }

    public static boolean checkMobile(String mobile) {
        return Pattern.matches(regexp.MOBILE.value, mobile);
    }

    public static boolean checkPassword(String password) {
        if (password == null)
            return false;
        return Pattern.matches(regexp.PASSWORD.value, password);
    }


    //正则
    public static final String regexAllLable = "【[^【】]{0,}】";
    public static final String regexBankLable = ".*银行.*";
    public static final String regexCardNoStr = "尾号.?[0-9]{4,}";
    public static final String regexNum = "[0-9]+";
    public static final String regexCreditCardLabel = ".*信用卡.*";
    public static final String regexAvailableCredit = "可用额度.?.?.?.?-?[0-9]{0,}";
    public static final String regexAvailableCreditNum = "-?[0-9]+";
    public static final String regexRepay1 = "应还.?.?.?.?.?.?[0-9]{0,}";
    public static final String regexRepay2 = "需还.?.?.?.?.?.?[0-9]{0,}";

    public static String getMatcher(String regex, String str) {
        String result = null;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            result = matcher.group();
            break;
        }
        System.out.println("result为" + result);
        return result;
    }

    public static Boolean  checkMatcher(String regex, String str) {
        Boolean result = false;
        result = str.matches(regex);
        return result;
    }
}
