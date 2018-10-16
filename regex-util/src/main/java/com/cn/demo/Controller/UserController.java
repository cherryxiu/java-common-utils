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

    public static void main(String[] args) {
        List<String> bankTotals = new ArrayList();//总银行数
        List<String> cardTotals = new ArrayList();//总卡数信息
        List<String> creditCardTotals = new ArrayList();// 总信用卡数
        Map<String, Object> availableCreditTotals = new HashMap<>();//信用卡总的可用额度
        Map<String, Object> needRepayTotals = new HashMap<>();//需要还给机构的总钱

        parseMsgContent(msgBank, bankTotals,cardTotals, creditCardTotals, availableCreditTotals, needRepayTotals );

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
                                         List<String> bankTotals,
                                         List<String> cardTotals,
                                         List<String> creditCardTotals,
                                         Map<String, Object> availableCreditTotals,
                                         Map<String, Object> needRepayTotals){
        String label = RegexUtil.getMatcher(RegexUtil.regexAllLable,msg);
        if (Detect.notEmpty(label)) {
            String msgLabel = StringUtils.strip(label, "【】");;
            Boolean isBankLabel = RegexUtil.checkMatcher(RegexUtil.regexBankLable, msgLabel);
            if (isBankLabel) {//银行标签
                addListNotRepeat(bankTotals, msgLabel);//总银行数
                getMsgData(msg,cardTotals, creditCardTotals, availableCreditTotals);
            } else {//机构标签
                //需要还给机构的总钱
                getNeedRepayTotals(msgLabel, needRepayTotals,RegexUtil.regexRepay1,msg);
                getNeedRepayTotals(msgLabel,needRepayTotals,RegexUtil.regexRepay2,msg);
            }
        }
    }

    public static void getMsgData(String msg, List<String> cardTotals,
                                  List<String> creditCardTotals, Map<String, Object> availableCreditTotals){
        String cardNoStr = RegexUtil.getMatcher(RegexUtil.regexCardNoStr,msg);//匹配银行卡尾号
        if (Detect.notEmpty(cardNoStr)) {
            String cardNo = RegexUtil.getMatcher(RegexUtil.regexNum,cardNoStr);
            addListNotRepeat(cardTotals, cardNo);//总卡数
            if (RegexUtil.checkMatcher(RegexUtil.regexCreditCardLabel,msg)) {//匹配到信用卡
                addListNotRepeat(creditCardTotals, cardNo);//总信用卡数
                //匹配到可用额度
                getAvailableCredit(cardNo, availableCreditTotals, msg);
            }
        }
    }

    private static void addListNotRepeat(List<String> list, String str){
        if (!list.contains(str)) {
            list.add(str);
        }
    }

    private static void getAvailableCredit(String cardNo, Map<String, Object> availableCreditTotals, String msg){
        String availableCreditStr = RegexUtil.getMatcher(RegexUtil.regexAvailableCredit,msg);
        if(Detect.notEmpty(availableCreditStr)){
            String availableCreditNum = RegexUtil.getMatcher(RegexUtil.regexAvailableCreditNum, availableCreditStr);
            if(Detect.notEmpty(availableCreditNum)){
                availableCreditTotals.put(cardNo, availableCreditNum);
            }
        }
    }

    private static void getNeedRepayTotals(String msgLabel, Map<String, Object> needRepayTotals, String regex, String msg){
        String needRepayNumStr = RegexUtil.getMatcher(regex,msg);
        if (Detect.notEmpty(needRepayNumStr)){
            String needRepayNum = RegexUtil.getMatcher(RegexUtil.regexNum,needRepayNumStr);
            if(Detect.notEmpty(needRepayNum)){
                needRepayTotals.put(msgLabel,needRepayNum);
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


