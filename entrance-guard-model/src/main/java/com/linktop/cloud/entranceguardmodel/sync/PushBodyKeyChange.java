package com.linktop.cloud.entranceguardmodel.sync;

public class PushBodyKeyChange {

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    String pid; // 	ptuid
    String from; // origin appkey
    String to; // new appkey
}
