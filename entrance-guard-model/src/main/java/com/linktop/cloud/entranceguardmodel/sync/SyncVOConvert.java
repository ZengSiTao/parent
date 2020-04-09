package com.linktop.cloud.entranceguardmodel.sync;

import com.linktop.cloud.entranceguardmodel.database.GateEvent;
import com.linktop.cloud.entranceguardmodel.database.ICCard;
import com.linktop.cloud.entranceguardmodel.database.User;

public class SyncVOConvert {
    public static void convert_User_UserForDevice(User user, UserForDevice u2d) {
        u2d.setBuildingNum(user.getBuildingNum());
        u2d.setEstateId(user.getEstateId());
        u2d.setGender(user.getGender() == 0 ? "女" : "男");
        u2d.setIdentityCardNum(user.getIdentityCardNum());
        u2d.setName(user.getName());
        u2d.setRoomNum(user.getRoomNum());
        u2d.setType(user.getType() == 0 ? "住户" : "访客");
        u2d.setUnitNum(user.getUnitNum());
        u2d.setUserId(user.getAliaId());
        u2d.setBlackListType(user.getBlackListType());
    }

    public static void convert_ICCard_UserForDevice(ICCard c, UserForDevice u) {
        u.setCardNum(c.getSerial());
    }

    public static void convert_PushDoor_GateEvent(PushBodyDoor pd, GateEvent ge) {
        ge.setLockCat(pd.getLock_cat());
        ge.setMedia(pd.getMedia());
        ge.setSrcTs(pd.getSrc_ts());
        ge.setUserAliaId(pd.getUid());
    }
}
