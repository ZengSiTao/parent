package com.linktop.cloud.entranceguardmodel.database;

import java.io.Serializable;

/**
 * @创建人 baojielei
 * @创建时间 2020/3/17
 * @描述 设置扩展输出方式，comm data的格式
 */
public class Outputexpand implements Serializable {

    //rowId
    private String rowId;
    //扩展输出方式（00H--同步输出（刷任何卡小继电器输出信号，用于摄像机抓拍）；40H--双向输出（呼梯））
    private String expandop;

    public String getExpandop() {
        return expandop;
    }

    public void setExpandop(String expandop) {
        this.expandop = expandop;
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }


}
