package com.troy.practice.common;

/**
 * 枚举实例
 */
public enum  CountryEnum {

    ONE(1,"齐"),
    TOW(2,"楚"),
    THREE(3,"燕"),
    FOUR(4,"赵"),
    FIVE(5,"魏"),
    SIX(6,"韩");

    private Integer retCode;
    public String retMessage;

    CountryEnum(Integer retCode, String retMessage) {
        this.retCode = retCode;
        this.retMessage = retMessage;
    }

    public static CountryEnum forEach_CountEnum(int index){
        CountryEnum[] values = CountryEnum.values();
        for (CountryEnum element:values) {
            if (index == element.getRetCode()){
                return element;
            }
        }
        return null;
    }

    public Integer getRetCode() {
        return retCode;
    }

    public void setRetCode(Integer retCode) {
        this.retCode = retCode;
    }

    public String getRetMessage() {
        return retMessage;
    }

    public void setRetMessage(String retMessage) {
        this.retMessage = retMessage;
    }

    @Override
    public String toString() {
        return "CountryEnum{" +
                "retCode=" + retCode +
                ", retMessage='" + retMessage + '\'' +
                '}';
    }}
