package com.linktop.cloud.entranceguardmodel.database;

import java.io.Serializable;

/**
 * @创建人 baojielei
 * @创建时间 2020/3/17
 * @描述 设置单向/双向刷卡(查询也用这个)
 */
public class DoorSingleDouble implements Serializable {
    //rowId
    private String rowId;
    //门id
    private String doorId;
    //单双向值（55H--双向开卡，FFH--单项开卡）
    private String direct;

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

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct;
    }

    @Override
    public String toString() {
        return "DoorSingleDouble{" +
                "rowId=" + rowId +
                ", doorId='" + doorId + '\'' +
                ", direct='" + direct + '\'' +
                '}';
    }
}
