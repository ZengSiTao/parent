package com.linktop.cloud.entranceguardmodel;

public class FileUploadResult extends BusiCommonResult {

    public String getFn() {
        return fn;
    }

    public void setFn(String fn) {
        this.fn = fn;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private String fn;
    private String msg;
}
