package com.linktop.cloud.entranceguardmodel.database;

import com.linktop.cloud.entranceguardmodel.database.base.DatabaseObjectBase;

public class User extends DatabaseObjectBase {

    public String getAliaId() {
        return aliaId;
    }

    public void setAliaId(String aliaId) {
        this.aliaId = aliaId;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
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

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getEstateId() {
        return estateId;
    }

    public void setEstateId(Integer estateId) {
        this.estateId = estateId;
    }

    public String getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(String deviceIds) {
        this.deviceIds = deviceIds;
    }

    public String getFnOnServer() {
        return fnOnServer;
    }

    public void setFnOnServer(String fnOnServer) {
        this.fnOnServer = fnOnServer;
    }

    public String getWebAccessName() {
        return webAccessName;
    }

    public void setWebAccessName(String webAccessName) {
        this.webAccessName = webAccessName;
    }

    public Integer getBlackListType() {
        return blackListType;
    }

    public void setBlackListType(Integer blackListType) {
        this.blackListType = blackListType;
    }

    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof User)) {
            return false;
        }
        User uObj = (User)obj;
        if(!this.getAliaId().equalsIgnoreCase(uObj.getAliaId())) {
            return false;
        }
        if(!this.getBuildingNum().equalsIgnoreCase(uObj.getBuildingNum())) {
            return false;
        }
        if(!this.getUnitNum().equalsIgnoreCase(uObj.getUnitNum())) {
            return false;
        }
        if(!this.getRoomNum().equalsIgnoreCase(uObj.getRoomNum())) {
            return false;
        }
        if(this.getType().intValue() != uObj.getType().intValue()) {
            return false;
        }
        if(!this.getName().equalsIgnoreCase(uObj.getName())) {
            return false;
        }
        if(!this.getIdentityCardNum().equalsIgnoreCase(uObj.getIdentityCardNum())) {
            return false;
        }
        if(this.getGender().intValue() != uObj.getGender().intValue()) {
            return false;
        }
        if(this.getEstateId().intValue() != uObj.getEstateId().intValue()) {
            return false;
        }
        if(!this.getFnOnServer().equalsIgnoreCase(uObj.getFnOnServer())) {
            return false;
        }
        if(this.getBlackListType() != uObj.getBlackListType()) {
            return false;
        }
        return true;
    }
    private String aliaId;
    private String buildingNum;
    private String unitNum;
    private String roomNum;
    private Integer type;
    private String name;
    private String identityCardNum;
    private Integer gender;
    private Integer estateId;
    private String deviceIds;
    private String fnOnServer;
    private String webAccessName;
    private Integer blackListType;
}
