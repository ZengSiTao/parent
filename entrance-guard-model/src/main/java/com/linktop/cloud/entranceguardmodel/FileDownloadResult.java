package com.linktop.cloud.entranceguardmodel;

public class FileDownloadResult extends BusiCommonResult {

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private byte[] body;
    private String msg;
}
