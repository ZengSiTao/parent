package com.linktop.cloud.entranceguardmodel.database;

import java.io.Serializable;

/**
 * @创建人 baojielei
 * @创建时间 2020/3/17
 * @描述 设置开门延迟时间
 */
public class DoorDelayTime implements Serializable {
    //rowId
    private String rowId;
    //门号id
    private String doorId;
    //时间间隔数值（时间间隔=时间间隔数值*0.5（秒））
    private int delayValue;


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

    public int getDelayValue() {
        return delayValue;
    }

    public void setDelayValue(int delayValue) {
        this.delayValue = delayValue;
    }


}
