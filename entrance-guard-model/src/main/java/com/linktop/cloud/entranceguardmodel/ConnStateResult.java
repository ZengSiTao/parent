package com.linktop.cloud.entranceguardmodel;

public class ConnStateResult extends BusiCommonResult {

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    private int begin;
    private int online;
    private String ip;
}
