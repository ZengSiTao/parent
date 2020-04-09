package com.linktop.cloud.entranceguardmodel.database;

import com.linktop.cloud.entranceguardmodel.database.base.DatabaseObjectBase;

public class BlacklistFace extends DatabaseObjectBase {

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

    public int getFaceId() {
        return faceId;
    }

    public void setFaceId(int faceId) {
        this.faceId = faceId;
    }

    public String getFnOnServer() {
        return fnOnServer;
    }

    public void setFnOnServer(String fnOnServer) {
        this.fnOnServer = fnOnServer;
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


    public String getWebAccessName() {
        return webAccessName;
    }

    public void setWebAccessName(String webAccessName) {
        this.webAccessName = webAccessName;
    }

    private int estateId;
    private int deviceId;
    private int faceId;
    private String fnOnServer;
    private String webAccessName;
    private String userName;
    private String buildingNum;
    private String unitNum;
    private String roomNum;
    private String identityCardNum;
}
