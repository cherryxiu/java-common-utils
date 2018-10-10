package com.cn.demo.model;

public class MsgDataModel {
    private int bankNum;
    private int cardNum;
    private int creditCardNum;
    private int availableCreditNum;
    private int needRepayNum;

    public int getBankNum() {
        return bankNum;
    }

    public void setBankNum(int bankNum) {
        this.bankNum = bankNum;
    }

    public int getCardNum() {
        return cardNum;
    }

    public void setCardNum(int cardNum) {
        this.cardNum = cardNum;
    }

    public int getCreditCardNum() {
        return creditCardNum;
    }

    public void setCreditCardNum(int creditCardNum) {
        this.creditCardNum = creditCardNum;
    }

    public int getAvailableCreditNum() {
        return availableCreditNum;
    }

    public void setAvailableCreditNum(int availableCreditNum) {
        this.availableCreditNum = availableCreditNum;
    }

    public int getNeedRepayNum() {
        return needRepayNum;
    }

    public void setNeedRepayNum(int needRepayNum) {
        this.needRepayNum = needRepayNum;
    }

    @Override
    public String toString() {
        return "MsgDataModel{" +
                "bankNum=" + bankNum +
                ", cardNum=" + cardNum +
                ", creditCardNum=" + creditCardNum +
                ", availableCreditNum=" + availableCreditNum +
                ", needRepayNum=" + needRepayNum +
                '}';
    }
}
