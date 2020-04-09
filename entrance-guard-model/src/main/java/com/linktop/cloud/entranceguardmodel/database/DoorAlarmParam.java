package com.linktop.cloud.entranceguardmodel.database;

import java.io.Serializable;

/**
 * @创建人 baojielei
 * @创建时间 2020/3/17
 * @描述 设置门磁报警参数
 */
public class DoorAlarmParam implements Serializable {

    //rowId
    private String rowId;

    //门id
    private String doorId;
    //报警时间间隔数值  时间间隔=（时间间隔数值）* 0.5秒
    private int dfireTime;
    //超时报警间隔数值(从高到低分别表示#1-#4门的时间间隔数值)(例如：20110908)（每两位代表一个门的数值）
    private int overFlowTime;

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getDoorId() {
        return doorId;
    }

    public void setDoorId(String doorId) {
        this.doorId = doorId;
    }

    public int getDfireTime() {
        return dfireTime;
    }

    public void setDfireTime(int dfireTime) {
        this.dfireTime = dfireTime;
    }

    public int getOverFlowTime() {
        return overFlowTime;
    }

    public void setOverFlowTime(int overFlowTime) {
        this.overFlowTime = overFlowTime;
    }


}
