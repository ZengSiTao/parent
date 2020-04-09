package com.linktop.cloud.entranceguardmodel.database;

import java.io.Serializable;

/**
 * @创建人 baojielei
 * @创建时间 2020/3/17
 * @描述 用户信息
 */
public class HardUser implements Serializable {
    //id
    private  String rowId;
    //卡号
    private String userCardNumber;
    //卡片类型
    private String cardType;
    //开门时间段序号
    private String openDoorTimeNum;
    //开门权限
    private String openDoorLevel;
    //开门密码(副卡1)
    private String sercret1;
    //胁迫密码(副卡2)
    private String sercret2;

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getUserCardNumber() {
        return userCardNumber;
    }

    public void setUserCardNumber(String userCardNumber) {
        this.userCardNumber = userCardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getOpenDoorTimeNum() {
        return openDoorTimeNum;
    }

    public void setOpenDoorTimeNum(String openDoorTimeNum) {
        this.openDoorTimeNum = openDoorTimeNum;
    }

    public String getOpenDoorLevel() {
        return openDoorLevel;
    }

    public void setOpenDoorLevel(String openDoorLevel) {
        this.openDoorLevel = openDoorLevel;
    }

    public String getSercret1() {
        return sercret1;
    }

    public void setSercret1(String sercret1) {
        this.sercret1 = sercret1;
    }

    public String getSercret2() {
        return sercret2;
    }

    public void setSercret2(String sercret2) {
        this.sercret2 = sercret2;
    }


}
