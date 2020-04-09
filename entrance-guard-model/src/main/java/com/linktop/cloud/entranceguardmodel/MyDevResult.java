package com.linktop.cloud.entranceguardmodel;

public class MyDevResult extends BusiCommonResult {
    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    private String list; // 逗号分隔的设备列表
}
