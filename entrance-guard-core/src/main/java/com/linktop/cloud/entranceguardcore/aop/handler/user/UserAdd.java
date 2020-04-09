package com.linktop.cloud.entranceguardcore.aop.handler.user;

import com.alibaba.fastjson.JSON;
import com.linktop.cloud.entranceguardcore.aop.intersector.HandlerIntersector;
import com.linktop.cloud.entranceguardcore.aop.vo.user.UserAddVO;
import com.linktop.cloud.entranceguardcore.service.DeviceService;
import com.linktop.cloud.entranceguardmodel.database.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.linktop.cloud.commonutils.Common.getRandomString;

@Component
public class UserAdd implements HandlerIntersector<UserAddVO> {

    private static Logger log = LoggerFactory.getLogger(UserAdd.class);
    @Autowired
    private UserCommon userCommon;
    @Autowired
    private DeviceService deviceService;

    @Override
    public String getClassName() {
        return UserAddVO.class.getName();
    }

    @Override
    public boolean beforeHandle(UserAddVO userAddVO) {
        Object[] args = (Object[]) userAddVO.getArgObj();
        //String aliaId,0
        //String buildingNum,1
        //String unitNum,2
        //String roomNum,3
        //Integer type,4
        //String name,5
        //String identityCardNum,6
        //Integer gender,7
        //Integer estateId,8
        //String deviceIds,9
        //String fnOnServer,10
        //Integer blackListType 11
        User user = (User) args[0];
        log.info("UserAdd beforeHandle, userAddVO:{}", JSON.toJSONString(userAddVO));
        return false;
    }

    @Override
    public boolean afterHandle(UserAddVO userAddVO) {
        Object[] args = (Object[]) userAddVO.getArgObj();
        int ret = (int) userAddVO.getRetObj();
        String aliaId = (String)args[0];
        String buildingNum = (String)args[1];
        String unitNum = (String)args[2];
        String roomNum = (String)args[3];
        Integer type = (Integer)args[4];
        String name = (String)args[5];
        String identityCardNum = (String)args[6];
        Integer gender = (Integer)args[7];
        Integer estateId = (Integer)args[8];
        String deviceIds = (String)args[9];
        String fnOnServer = (String)args[10];
        Integer blackListType = (Integer)args[11];

        if(ret == 1) {
            User user = new User();
            user.setAliaId(aliaId);
            user.setBuildingNum(buildingNum);
            user.setUnitNum(unitNum);
            user.setRoomNum(roomNum);
            user.setType(type);
            user.setName(name);
            user.setIdentityCardNum(identityCardNum);
            user.setGender(gender);
            user.setEstateId(estateId);
            user.setDeviceIds(deviceIds);
            user.setFnOnServer(fnOnServer);
            user.setBlackListType(blackListType);
            List<String> listAdd = null;
            if(0 == user.getBlackListType()) {
                listAdd = deviceService.getDeviceAliaIdList(user.getDeviceIds());
            } else {
                listAdd = deviceService.getDeviceAliaIdList();
            }
            userCommon.notifyAdd(user, listAdd);
        }
        log.info("UserAdd afterHandle, userAddVO:{}", JSON.toJSONString(userAddVO));

        return false;
    }


}
