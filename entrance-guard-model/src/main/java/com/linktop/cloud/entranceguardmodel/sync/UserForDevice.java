package com.linktop.cloud.entranceguardmodel.sync;

public class UserForDevice {

    public String getImgB64() {
        return imgB64;
    }

    public void setImgB64(String imgB64) {
        this.imgB64 = imgB64;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentityCardNum() {
        return identityCardNum;
    }

    public void setIdentityCardNum(String identityCardNum) {
        this.identityCardNum = identityCardNum;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getEstateId() {
        return estateId;
    }

    public void setEstateId(Integer estateId) {
        this.estateId = estateId;
    }

    public Integer getBlackListType() {
        return blackListType;
    }

    public void setBlackListType(Integer blackListType) {
        this.blackListType = blackListType;
    }

    private String imgB64;
    private String cardNum;

    private String userId;
    private String buildingNum;
    private String unitNum;
    private String roomNum;
    private String type;
    private String name;
    private String identityCardNum;
    private String gender;
    private Integer estateId;
    private Integer blackListType;
}
