package com.linktop.cloud.entranceguardcore.util;


/**
 * @创建人 baojielei
 * @创建时间 2020/3/16
 * @描述
 */

public enum ComMand {
    ADDHARDUSER("16","增加用户"),
    DELETEHARDUSER("17","删除用户"),
    SELECTHARDUSER("18","查询用户"),
    ADDENTRANCEGUARDTYPE("30","增加门禁类型"),
    SELENTRANCEGUARDTYPE("31","查询门禁类型");
    private String code;
    private String description;

    ComMand() {
    }

    ComMand(String code, String description) {
        this.code=code;
        this.description=description;
    }

    // 普通方法
    public static String getName(String code) {
        for (ComMand test : ComMand.values()) {
            if (test.getCode() == code) {
                return test.description;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
