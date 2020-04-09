package com.linktop.cloud.entranceguardmodel.database;

import java.io.Serializable;

/**
 * @创建人 baojielei
 * @创建时间 2020/3/17
 * @描述 提取信息与门禁报警(提取门禁报警信息)
 */
public class DoorAlarmInfo implements Serializable {
    //请求提取信息与门禁报警的某个参数（返回数据时，此数据为空）
    private String rowid;

    //（返回体）用户id
    private String userCardNumber;
    //年
    private String year;
    //月
    private String month;
    //日
    private String day;
    //时
    private String hour;
    //分
    private String minute;
    //秒
    private String sec;
    //门的状态
    // DATA  INFO的门锁状态字节中：
    //Bit3--Bit0分别表示#4--#1门的状态，如果某位置“1”，则对应门为
    //异常状态，反之为正常状态，Bit7--Bit4无效
    //例：传过来的是16进制，需转换为2进制进行解析判断；F 转 00001111（十进制为：15）代表4321号门异常
    private String doorStatus;

    public String getRowid() {
        return rowid;
    }

    public void setRowid(String rowid) {
        this.rowid = rowid;
    }

    public String getUserCardNumber() {
        return userCardNumber;
    }

    public void setUserCardNumber(String userCardNumber) {
        this.userCardNumber = userCardNumber;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getSec() {
        return sec;
    }

    public void setSec(String sec) {
        this.sec = sec;
    }

    public String getDoorStatus() {
        return doorStatus;
    }

    public void setDoorStatus(String doorStatus) {
        this.doorStatus = doorStatus;
    }


}
