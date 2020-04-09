package com.linktop.cloud.entranceguardmodel.database;

import com.linktop.cloud.entranceguardmodel.database.base.DatabaseObjectBase;

public class BlacklistICCard extends DatabaseObjectBase {


    public int getEstateId() {
        return estateId;
    }

    public void setEstateId(int estateId) {
        this.estateId = estateId;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getIccardId() {
        return iccardId;
    }

    public void setIccardId(int iccardId) {
        this.iccardId = iccardId;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private int estateId;
    private int deviceId;
    private int iccardId;
    private String serial;
    private String userName;
    private String buildingNum;
    private String unitNum;
    private String roomNum;
    private String identityCardNum;
    private String userAliaId;
}
