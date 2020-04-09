package com.linktop.cloud.entranceguardmodel.database;

import com.linktop.cloud.entranceguardmodel.database.base.DatabaseObjectBase;

import java.util.List;

public class DevUl extends DatabaseObjectBase {

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getFn() {
        return fn;
    }

    public void setFn(String fn) {
        this.fn = fn;
    }

    private String receipt;
    private String usage;
    private String fn;
}
