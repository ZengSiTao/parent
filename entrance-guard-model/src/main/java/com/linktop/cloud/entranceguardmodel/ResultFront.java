package com.linktop.cloud.entranceguardmodel;

import java.io.Serializable;
import java.util.List;

/**
 * @创建人 baojielei
 * @创建时间 2020/3/11
 * @描述
 */
public class ResultFront<T> implements Serializable {

    private int code;
    private String msg;
    private List<T> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultFront{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
