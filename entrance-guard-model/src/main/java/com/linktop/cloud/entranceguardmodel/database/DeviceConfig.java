package com.linktop.cloud.entranceguardmodel.database;

import com.linktop.cloud.entranceguardmodel.database.base.DatabaseObjectBase;

public class DeviceConfig extends DatabaseObjectBase {

    public int getDevId() {
        return devId;
    }

    public void setDevId(int devId) {
        this.devId = devId;
    }

    public String getDeviceAliaId() {
        return deviceAliaId;
    }

    public void setDeviceAliaId(String deviceAliaId) {
        this.deviceAliaId = deviceAliaId;
    }

    public String getFnOnServer() {
        return fnOnServer;
    }

    public void setFnOnServer(String fnOnServer) {
        this.fnOnServer = fnOnServer;
    }

    public boolean isLiveDetectOn() {
        return liveDetectOn;
    }

    public void setLiveDetectOn(boolean liveDetectOn) {
        this.liveDetectOn = liveDetectOn;
    }

    public boolean isRegzSaveLocalOn() {
        return regzSaveLocalOn;
    }

    public void setRegzSaveLocalOn(boolean regzSaveLocalOn) {
        this.regzSaveLocalOn = regzSaveLocalOn;
    }

    public int getRegzSaveLocalTime() {
        return regzSaveLocalTime;
    }

    public void setRegzSaveLocalTime(int regzSaveLocalTime) {
        this.regzSaveLocalTime = regzSaveLocalTime;
    }

    public boolean isRegisterSaveLocalOn() {
        return registerSaveLocalOn;
    }

    public void setRegisterSaveLocalOn(boolean registerSaveLocalOn) {
        this.registerSaveLocalOn = registerSaveLocalOn;
    }

    public boolean isRegzVideoOn() {
        return regzVideoOn;
    }

    public void setRegzVideoOn(boolean regzVideoOn) {
        this.regzVideoOn = regzVideoOn;
    }

    public boolean isRegzAlwaysOn() {
        return regzAlwaysOn;
    }

    public void setRegzAlwaysOn(boolean regzAlwaysOn) {
        this.regzAlwaysOn = regzAlwaysOn;
    }

    public int getRegzOffTime() {
        return regzOffTime;
    }

    public void setRegzOffTime(int regzOffTime) {
        this.regzOffTime = regzOffTime;
    }

    public boolean isGetAgain() {
        return getAgain;
    }

    public void setGetAgain(boolean getAgain) {
        this.getAgain = getAgain;
    }

    public boolean isRegzStranger() {
        return regzStranger;
    }

    public void setRegzStranger(boolean regzStranger) {
        this.regzStranger = regzStranger;
    }

    public boolean isFuncStop() {
        return funcStop;
    }

    public void setFuncStop(boolean funcStop) {
        this.funcStop = funcStop;
    }

    public boolean isGateOpen() {
        return gateOpen;
    }

    public void setGateOpen(boolean gateOpen) {
        this.gateOpen = gateOpen;
    }

    public int getDoorCloseAlarmTime() {
        return doorCloseAlarmTime;
    }

    public void setDoorCloseAlarmTime(int doorCloseAlarmTime) {
        this.doorCloseAlarmTime = doorCloseAlarmTime;
    }

    private int devId;
    private String deviceAliaId;
    private String fnOnServer;
    private boolean getAgain;

    private boolean liveDetectOn; // 活体检测开关 true
    private boolean regzSaveLocalOn; // 识别数据本地保存 true
    private int regzSaveLocalTime; // 识别数据本地保存时间　单位小时 720
    private boolean registerSaveLocalOn; // 本地注册相关保存开关 false
    private boolean regzVideoOn; // 识别录像开关 true
    private boolean regzAlwaysOn; // 识别常开开关 false
    private int regzOffTime; // 识别常开开关为OFF时，红外感应多长时间关屏关识别　单位秒 120
    private boolean regzStranger; // 识别陌生人 false
    private boolean funcStop; // 停机状态 false
    private boolean gateOpen; // 停机状态时，闸机口常开 false
    private int doorCloseAlarmTime; // 关门超时报警时间 单位秒 30
}
