package com.linktop.cloud.entranceguardmodel.database;

import java.io.Serializable;

/**
 * @创建人 baojielei
 * @创建时间 2020/3/17
 * @描述 设置开门时间段
 */
public class DoorOpeningPeriod implements Serializable {


    //rowId
    private String rowId;
    //时间段序号 范围（01H-0FH）（1-15）
    private String timeDelayNum;
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
    //起始时分1
    private String startTime1;
    //结束时分1
    private String endTime1;
    //起始时分2
    private String startTime2;
    //结束时分2
    private String endTime2;
    //起始时分3
    private String startTime3;
    //结束时分3
    private String endTime3;

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getTimeDelayNum() {
        return timeDelayNum;
    }

    public void setTimeDelayNum(String timeDelayNum) {
        this.timeDelayNum = timeDelayNum;
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

    public String getStartTime1() {
        return startTime1;
    }

    public void setStartTime1(String startTime1) {
        this.startTime1 = startTime1;
    }

    public String getEndTime1() {
        return endTime1;
    }

    public void setEndTime1(String endTime1) {
        this.endTime1 = endTime1;
    }

    public String getStartTime2() {
        return startTime2;
    }

    public void setStartTime2(String startTime2) {
        this.startTime2 = startTime2;
    }

    public String getEndTime2() {
        return endTime2;
    }

    public void setEndTime2(String endTime2) {
        this.endTime2 = endTime2;
    }

    public String getStartTime3() {
        return startTime3;
    }

    public void setStartTime3(String startTime3) {
        this.startTime3 = startTime3;
    }

    public String getEndTime3() {
        return endTime3;
    }

    public void setEndTime3(String endTime3) {
        this.endTime3 = endTime3;
    }

}
