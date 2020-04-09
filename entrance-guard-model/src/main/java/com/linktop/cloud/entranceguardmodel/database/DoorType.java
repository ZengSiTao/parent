package com.linktop.cloud.entranceguardmodel.database;

import java.io.Serializable;

/**
 * @创建人 baojielei
 * @创建时间 2020/3/17
 * @描述 设置门禁类型
 */
public class DoorType implements Serializable {

    //rowId
    private String rowId;
    //门id
    private String doorId;
    //门禁类型
    private String doorType;

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

    public String getDoorType() {
        return doorType;
    }

    public void setDoorType(String doorType) {
        this.doorType = doorType;
    }


}
