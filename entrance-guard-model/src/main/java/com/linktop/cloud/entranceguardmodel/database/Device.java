package com.linktop.cloud.entranceguardmodel.database;

import com.linktop.cloud.entranceguardmodel.database.base.DatabaseObjectBase;

public class Device extends DatabaseObjectBase {

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

    public String getMacAddr() {
        return macAddr;
    }

    public void setMacAddr(String macAddr) {
        this.macAddr = macAddr;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getEstateId() {
        return estateId;
    }

    public void setEstateId(int estateId) {
        this.estateId = estateId;
    }

    public String getAliaId() {
        return aliaId;
    }

    public void setAliaId(String aliaId) {
        this.aliaId = aliaId;
    }

    public int getDevcfgId() {
        return devcfgId;
    }

    public void setDevcfgId(int devcfgId) {
        this.devcfgId = devcfgId;
    }

    public int getConnBegin() {
        return connBegin;
    }

    public void setConnBegin(int connBegin) {
        this.connBegin = connBegin;
    }

    public String getConnIp() {
        return connIp;
    }

    public void setConnIp(String connIp) {
        this.connIp = connIp;
    }

    public int getConnOnline() {
        return connOnline;
    }

    public void setConnOnline(int connOnline) {
        this.connOnline = connOnline;
    }

    private String buildingNum;
    private String unitNum;
    private String macAddr;
    private String aliaId;
    private int type; // 0--单元机 1--门口机
    private int estateId;
    private int devcfgId;

    private int connBegin;
    private String connIp;
    private int connOnline;
}
