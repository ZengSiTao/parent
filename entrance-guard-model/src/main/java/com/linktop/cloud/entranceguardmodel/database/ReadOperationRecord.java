package com.linktop.cloud.entranceguardmodel.database;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @创建人 baojielei
 * @创建时间 2020/3/17
 * @描述 读取操作记录 提取记录
 */
public class ReadOperationRecord implements Serializable {

    //请求读取记录的某个id
    private  String rowId;
    //用户id
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



    public static void main(String[] args) {
        Date date = new Date( 2021,2,12 );
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy--MM--dd" );
            ReadOperationRecord readRecord = new ReadOperationRecord();
        String year=simpleDateFormat.format( date );
        String y = year.substring( 2,4 );
        System.out.println(y);
    }
}
