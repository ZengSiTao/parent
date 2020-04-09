package com.linktop.cloud.entranceguardcore.util;

import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * @创建人 baojielei
 * @创建时间 2020/3/17
 * @描述
 */
@Service
public class Result<T> implements Serializable {
    //起始码：FFH
    private String soi;
    //命令码
    private String comMand;
    //随机数
    private int rand;
    //备用
    private String bkup;
    //数据长度
    private int len;
    //命令数据
    private List<T> comdDate;
    //数据格式
    private String chk;

    public String getSoi() {
        return soi;
    }

    public void setSoi(String soi) {
        this.soi = soi;
    }

    public String getComMand() {
        return comMand;
    }

    public void setComMand(String comMand) {
        this.comMand = comMand;
    }

    public int getRand() {
        return rand;
    }

    public void setRand(int rand) {
        this.rand = rand;
    }

    public String getBkup() {
        return bkup;
    }

    public void setBkup(String bkup) {
        this.bkup = bkup;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public List<T> getComdDate() {
        return comdDate;
    }

    public void setComdDate(List<T> comdDate) {
        this.comdDate = comdDate;
    }

    public String getChk() {
        return chk;
    }

    public void setChk(String chk) {
        this.chk = chk;
    }

    @Override
    public String toString() {
        return "Result{" +
                "soi='" + soi + '\'' +
                ", comMand=" + comMand +
                ", rand=" + rand +
                ", bkup='" + bkup + '\'' +
                ", len=" + len +
                ", comdDate=" + comdDate +
                ", chk='" + chk + '\'' +
                '}';
    }
}
