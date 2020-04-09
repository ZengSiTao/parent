package com.linktop.cloud.entranceguardmodel.database;

/**
 * @创建人 baojielei
 * @创建时间 2020/3/17
 * @描述 查询用户数量时，comm date无数据；返回info date格式如下
 */
public class SelectUserNum {
    //返回数量值
    private int UserNum;

    public int getUserNum() {
        return UserNum;
    }

    public void setUserNum(int userNum) {
        UserNum = userNum;
    }

    @Override
    public String toString() {
        return "SelectUserNum{" +
                "UserNum=" + UserNum +
                '}';
    }
}
