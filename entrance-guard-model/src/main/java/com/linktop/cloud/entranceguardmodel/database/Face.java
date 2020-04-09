package com.linktop.cloud.entranceguardmodel.database;

import com.linktop.cloud.entranceguardmodel.database.base.DatabaseObjectBase;

public class Face extends DatabaseObjectBase {

    public String getFnOnServer() {
        return fnOnServer;
    }

    public void setFnOnServer(String fnOnServer) {
        this.fnOnServer = fnOnServer;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBuildingNum() {
        return buildingNum;
    }

    public void setBuildingNum(String buildingNum) {
        this.buildingNum = buildingNum;
    }

    public String getUnitNum() {
        return unitNum;
    }

    public void setUnitNum(String unitNum) {
        this.unitNum = unitNum;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public String getIdentityCardNum() {
        return identityCardNum;
    }

    public void setIdentityCardNum(String identityCardNum) {
        this.identityCardNum = identityCardNum;
    }

    public String getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(String deviceIds) {
        this.deviceIds = deviceIds;
    }

    public String getWebAccessName() {
        return webAccessName;
    }

    public void setWebAccessName(String webAccessName) {
        this.webAccessName = webAccessName;
    }

    private String fnOnServer;
    private String webAccessName;
    private int userId;
    private String deviceIds;
    private String userName;
    private String buildingNum;
    private String unitNum;
    private String roomNum;
    private String identityCardNum;
}
