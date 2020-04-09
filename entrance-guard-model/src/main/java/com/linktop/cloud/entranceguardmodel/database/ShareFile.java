package com.linktop.cloud.entranceguardmodel.database;

import com.linktop.cloud.entranceguardmodel.database.base.DatabaseObjectBase;

public class ShareFile extends DatabaseObjectBase {

    public String getUserAliaId() {
        return userAliaId;
    }

    public void setUserAliaId(String userAliaId) {
        this.userAliaId = userAliaId;
    }

    public String getFnOnServer() {
        return fnOnServer;
    }

    public void setFnOnServer(String fnOnServer) {
        this.fnOnServer = fnOnServer;
    }

    public String getFileUsage() {
        return fileUsage;
    }

    public void setFileUsage(String fileUsage) {
        this.fileUsage = fileUsage;
    }

    public boolean isNeedDel() {
        return needDel;
    }

    public void setNeedDel(boolean needDel) {
        this.needDel = needDel;
    }

    public String getDeviceAliaId() {
        return deviceAliaId;
    }

    public void setDeviceAliaId(String deviceAliaId) {
        this.deviceAliaId = deviceAliaId;
    }

    private String fileUsage;
    private String userAliaId;
    private String deviceAliaId;
    private String fnOnServer;
    private boolean needDel;
}
