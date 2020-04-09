package com.linktop.cloud.entranceguardcore.aop.handler.user;

import com.alibaba.fastjson.JSON;
import com.linktop.cloud.entranceguardcore.aop.intersector.HandlerIntersector;
import com.linktop.cloud.entranceguardcore.aop.vo.user.UserBatchDelVO;
import com.linktop.cloud.entranceguardcore.service.DeviceService;
import com.linktop.cloud.entranceguardcore.service.UsersService;
import com.linktop.cloud.entranceguardmodel.database.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserBatchDel implements HandlerIntersector<UserBatchDelVO> {
    private static Logger log = LoggerFactory.getLogger(UserBatchDel.class);
    @Autowired
    private UserCommon userCommon;
    @Autowired
    private UsersService usersService;
    @Autowired
    private DeviceService deviceService;
    private List<User> listUserBeforeModify;
    @Override
    public String getClassName() {
        return UserBatchDelVO.class.getName();
    }

    @Override
    public boolean beforeHandle(UserBatchDelVO userBatchDelVO) {
        Object[] args = (Object[]) userBatchDelVO.getArgObj();
        List<Integer> ids = (List<Integer>)args[0];
        log.info("UserBatchDel beforeHandle, userBatchDelVO:{}", JSON.toJSONString(userBatchDelVO));
        listUserBeforeModify = usersService.batchGet(ids);
        return false;
    }

    @Override
    public boolean afterHandle(UserBatchDelVO userBatchDelVO) {
        log.info("UserBatchDel afterHandle, userBatchDelVO:{}", JSON.toJSONString(userBatchDelVO));

        Object[] args = (Object[]) userBatchDelVO.getArgObj();
        List<Integer> ids = (List<Integer>)args[0];
        int ret = (int) userBatchDelVO.getRetObj();

        if(ret > 1) {
            for(User user:listUserBeforeModify) {
                List<String> listDelete = null;
                if(0 == user.getBlackListType()) {
                    listDelete = deviceService.getDeviceAliaIdList(user.getDeviceIds());
                } else {
                    listDelete = deviceService.getDeviceAliaIdList();
                }
                userCommon.notifyDel(user, listDelete);
            }
        }

        return false;
    }


}
