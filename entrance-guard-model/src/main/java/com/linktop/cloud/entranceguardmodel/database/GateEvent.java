package com.linktop.cloud.entranceguardmodel.database;

import com.linktop.cloud.entranceguardmodel.database.base.DatabaseObjectBase;

import java.util.List;

public class GateEvent extends DatabaseObjectBase {

    public String getLockCat() {
        return lockCat;
    }

    public void setLockCat(String lockCat) {
        this.lockCat = lockCat;
    }

    public Integer getSrcTs() {
        return srcTs;
    }

    public void setSrcTs(Integer srcTs) {
        this.srcTs = srcTs;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getUserAliaId() {
        return userAliaId;
    }

    public void setUserAliaId(String userAliaId) {
        this.userAliaId = userAliaId;
    }

    public String getDeviceAliaId() {
        return deviceAliaId;
    }

    public void setDeviceAliaId(String deviceAliaId) {
        this.deviceAliaId = deviceAliaId;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getUserBuildingNum() {
        return userBuildingNum;
    }

    public void setUserBuildingNum(String userBuildingNum) {
        this.userBuildingNum = userBuildingNum;
    }

    public String getUserUnitNum() {
        return userUnitNum;
    }

    public void setUserUnitNum(String userUnitNum) {
        this.userUnitNum = userUnitNum;
    }

    public String getUserRoomNum() {
        return userRoomNum;
    }

    public void setUserRoomNum(String userRoomNum) {
        this.userRoomNum = userRoomNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeviceBuildingNum() {
        return deviceBuildingNum;
    }

    public void setDeviceBuildingNum(String deviceBuildingNum) {
        this.deviceBuildingNum = deviceBuildingNum;
    }

    public String getDeviceUnitNum() {
        return deviceUnitNum;
    }

    public void setDeviceUnitNum(String deviceUnitNum) {
        this.deviceUnitNum = deviceUnitNum;
    }

    public List<String> getListPictureName() {
        return listPictureName;
    }

    public void setListPictureName(List<String> listPictureName) {
        this.listPictureName = listPictureName;
    }

    public List<String> getListVideoName() {
        return listVideoName;
    }

    public void setListVideoName(List<String> listVideoName) {
        this.listVideoName = listVideoName;
    }

    public String getFnList() {
        return fnList;
    }

    public void setFnList(String fnList) {
        this.fnList = fnList;
    }

    private String lockCat;
    private Integer srcTs;
    private String media;
    private String userAliaId;
    private String deviceAliaId;
    private String receipt;
    private String userBuildingNum;
    private String userUnitNum;
    private String userRoomNum;
    private String userName;
    private String deviceBuildingNum;
    private String deviceUnitNum;
    private String fnList;
    private List<String> listPictureName;
    private List<String> listVideoName;
}
