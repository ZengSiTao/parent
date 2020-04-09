package com.linktop.cloud.entranceguardcore.aop.handler.user;

import com.alibaba.fastjson.JSON;
import com.linktop.cloud.entranceguardcore.aop.intersector.HandlerIntersector;
import com.linktop.cloud.entranceguardcore.aop.vo.user.UserDeleteVO;
import com.linktop.cloud.entranceguardcore.service.DeviceService;
import com.linktop.cloud.entranceguardcore.service.UsersService;
import com.linktop.cloud.entranceguardmodel.database.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDelete implements HandlerIntersector<UserDeleteVO> {
    private static Logger log = LoggerFactory.getLogger(UserDelete.class);
    @Autowired
    private UserCommon userCommon;
    @Autowired
    private UsersService usersService;
    @Autowired
    private DeviceService deviceService;
    private User userBeforeModify;

    @Override
    public String getClassName() {
        return UserDeleteVO.class.getName();
    }

    @Override
    public boolean beforeHandle(UserDeleteVO userDeleteVO) {
        Object[] args = (Object[]) userDeleteVO.getArgObj();
        int id = (int) args[0];
        log.info("UserDelete beforeHandle, userDeleteVO:{}", JSON.toJSONString(userDeleteVO));
        userBeforeModify = usersService.get(id);
        return false;
    }

    @Override
    public boolean afterHandle(UserDeleteVO userDeleteVO) {
        Object[] args = (Object[]) userDeleteVO.getArgObj();
        int ret = (int)userDeleteVO.getRetObj();
        int id = (int) args[0];
        log.info("UserDelete afterHandle, userDeleteVO:{}", JSON.toJSONString(userDeleteVO));
        if(ret == 1) {
            List<String> listDelete = null;
            if(0 == userBeforeModify.getBlackListType()) {
                listDelete = deviceService.getDeviceAliaIdList(userBeforeModify.getDeviceIds());
            } else {
                listDelete = deviceService.getDeviceAliaIdList();
            }
            userCommon.notifyDel(userBeforeModify, listDelete);
        }
        return false;
    }


}
