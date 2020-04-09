package com.linktop.cloud.entranceguardmodel.database;

import java.io.Serializable;

/**
 * @创建人 baojielei
 * @创建时间 2020/3/17
 * @描述 增加临时用户命令(删除临时用户只需用户卡号（COMM DATE）)
 */
public class HardCasualUser implements Serializable {

    //rowId
    private String rowId;
    //用户卡号
    private String temporaryNumber;
    //起始年
    private String startYear;
    //起始月
    private String startMonth;
    //起始日
    private String startdDay;

    //结束年
    private String endYear;
    //结束月
    private String endMonth;
    //结束日
    private String endDay;
    //开门权限
    private String edCardLevel;
    //开门次数
    private int openTimes;
    //开门密码（副卡1）
    private String sercret1;
    //胁迫密码（副卡2）
    private String sercret2;

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getTemporaryNumber() {
        return temporaryNumber;
    }

    public void setTemporaryNumber(String temporaryNumber) {
        this.temporaryNumber = temporaryNumber;
    }

    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }

    public String getStartdDay() {
        return startdDay;
    }

    public void setStartdDay(String startdDay) {
        this.startdDay = startdDay;
    }

    public String getEndYear() {
        return endYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    public String getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public String getEdCardLevel() {
        return edCardLevel;
    }

    public void setEdCardLevel(String edCardLevel) {
        this.edCardLevel = edCardLevel;
    }

    public int getOpenTimes() {
        return openTimes;
    }

    public void setOpenTimes(int openTimes) {
        this.openTimes = openTimes;
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
