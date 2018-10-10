package com.cn.demo.Controller;


import com.cn.demo.Utils.Detect;
import com.cn.demo.Utils.RegexUtil;
import com.cn.demo.model.MsgDataModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController{

    private static final String msgBank = "【浦发银行】您尾号0245卡您的信用卡可用额度为239.90元。带你飞往来款【浦发q2银行】";
    private static final String msgBank2 = "【浦发银行】您尾号0246卡您的信用卡可用额度为100.90元。带你飞往来款【浦发q2银行】";
    private static final String msgBank3 = "【浦发78银行】您尾号0246卡您的信用卡可用额度为100.90元。带你飞往来款【浦发q2银行】";
    private static final String msgXinYong = "韩猛先生：尾号0247您个人信用卡已过缴截止日，本期仍应还￥12.00，最低￥1.20，点 cmbt.cn/mjf 还款【招商银行】";


    //正则
    private static final String regexAllLable = "【[^【】]{0,}】";
    private static final String regexBankLable = ".*银行";
    private static final String regexCardNoStr = "尾号.?[0-9]{4,}";
    private static final String regexNum = "[0-9]+";
    private static final String regexCreditCardLabel = ".*信用卡.*";
    private static final String regexAvailableCredit = "可用额度.?.?.?.?-?[0-9]{0,}";
    private static final String regexAvailableCreditNum = "-?[0-9]+";
    private static final String regexRepay1 = "应还.?.?.?.?.?.?[0-9]{0,}";
    private static final String regexRepay2 = "需还.?.?.?.?.?.?[0-9]{0,}";




    public static void main(String[] args) {
        List bankTotals = new ArrayList();//总银行数
        Map<String, Object> cardTotals = new HashMap<>();//总卡数信息
        List creditCardTotals = new ArrayList();// 总信用卡数
        Map<String, Object> availableCreditTotals = new HashMap<>();//信用卡总的可用额度
        Map<String, Object> needRepayTotals = new HashMap<>();//需要还给机构的总钱

        parseMsgContent(msgBank, bankTotals,cardTotals, creditCardTotals, availableCreditTotals, needRepayTotals );
        //parseMsgContent(msgBank3, bankTotals,cardTotals, creditCardTotals, availableCreditTotals, needRepayTotals );
        parseMsgContent(msgXinYong, bankTotals,cardTotals, creditCardTotals, availableCreditTotals, needRepayTotals );

        //生成变量
        MsgDataModel msgDataModel = new MsgDataModel();
        msgDataModel.setBankNum(bankTotals.size());
        msgDataModel.setCardNum(cardTotals.size());
        msgDataModel.setCreditCardNum(creditCardTotals.size());
        msgDataModel.setAvailableCreditNum(sum(availableCreditTotals));
        msgDataModel.setNeedRepayNum(sum(needRepayTotals));

        msgDataModel.toString();
    }


    private  static void parseMsgContent(String msg,
                                           List bankTotals,
                                           Map<String, Object> cardTotals,
                                           List creditCardTotals,
                                           Map<String, Object> availableCreditTotals,
                                           Map<String, Object> needRepayTotals) {
        String label = RegexUtil.getMatcher(regexAllLable,msg);
        if (Detect.notEmpty(label)) {
            String msgLabel = StringUtils.strip(label, "【】");;
            Boolean isBankLable = RegexUtil.checkMatcher(regexBankLable, msgLabel);
            if (isBankLable) {//银行标签
                if (!bankTotals.contains(msgLabel)) {
                    bankTotals.add(msgLabel);//总银行数
                }
                getMsgData(msg,cardTotals, creditCardTotals, availableCreditTotals);
            } else {//机构标签
                if (RegexUtil.checkMatcher(regexRepay1,msg) && RegexUtil.checkMatcher(regexRepay2,msg)) {
                    String needRepayStr = RegexUtil.getMatcher(regexRepay2,msg);
                    String needRepayNum = RegexUtil.getMatcher(regexNum,needRepayStr);
                    needRepayTotals.put(msgLabel,needRepayNum);//需要还给机构的总钱
                }
            }
        }
    }

    public static void getMsgData(String msg,
                                   Map<String, Object> cardTotals,
                                   List creditCardTotals,
                                   Map<String, Object> availableCreditTotals){
        String cardNoStr = RegexUtil.getMatcher(regexCardNoStr,msg);//匹配银行卡尾号
        if (Detect.notEmpty(cardNoStr)) {
            String cardNo = RegexUtil.getMatcher(regexNum,cardNoStr);
            if (RegexUtil.checkMatcher(regexCreditCardLabel,msg)) {//匹配到信用卡
                cardTotals.put(cardNo, "creditCard");//总卡数
                if (!creditCardTotals.contains(cardNo)) {
                    creditCardTotals.add(cardNo);//总信用卡数
                }
                //匹配到可用额度
                String availableCredit = RegexUtil.getMatcher(regexAvailableCredit,msg);
                if(Detect.notEmpty(availableCredit)){
                    availableCreditTotals.put(cardNo,RegexUtil.getMatcher(regexAvailableCreditNum, availableCredit));
                }
            } else {
                cardTotals.put(cardNo, "notCreditCard");
            }
        }
    }

    private  static int sum (Map<String, Object> map) {
        int sum = 0;
        for (String key : map.keySet()) {
            sum = sum + Integer.parseInt(map.get(key).toString());
        }
        return sum;
    }

}


