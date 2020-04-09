package com.linktop.cloud.entranceguardcore.aop.handler.user;

import com.alibaba.fastjson.JSON;
import com.linktop.cloud.entranceguardcore.aop.intersector.HandlerIntersector;
import com.linktop.cloud.entranceguardcore.aop.vo.user.UserUpdateVO;
import com.linktop.cloud.entranceguardcore.service.BusiServerApiService;
import com.linktop.cloud.entranceguardcore.service.DeviceService;
import com.linktop.cloud.entranceguardcore.service.UsersService;
import com.linktop.cloud.entranceguardmodel.GetPtuidListResult;
import com.linktop.cloud.entranceguardmodel.database.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserUpdate implements HandlerIntersector<UserUpdateVO> {
    private static Logger log = LoggerFactory.getLogger(UserUpdate.class);
    @Autowired
    private UserCommon userCommon;
    @Autowired
    private UsersService usersService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private BusiServerApiService busiServerApiService;
    private User userBeforeModify;

    @Override
    public String getClassName() {
        return UserUpdateVO.class.getName();
    }

    @Override
    public boolean beforeHandle(UserUpdateVO userUpdateVO) {
        Object[] args = (Object[]) userUpdateVO.getArgObj();
        User user = (User) args[0];

        userBeforeModify = usersService.get(user.getId());
        log.info("UserUpdate beforeHandle, userBeforeModify:{}", JSON.toJSONString(userBeforeModify));
        return false;
    }

    @Override
    public boolean afterHandle(UserUpdateVO userUpdateVO) {
        Object[] args = (Object[]) userUpdateVO.getArgObj();
        User user = (User)args[0];
        int ret = (int) userUpdateVO.getRetObj();
        log.info("UserUpdate afterHandle, userUpdateVO:{}", JSON.toJSONString(userUpdateVO));
        if(ret == 1) {
            // 更新数据库成功
            // 1.
            // if( 检查blackListType,新登记黑名单(变量从0变非0)的 ) {
            //   notifyAdd所有设备；
            // } else if( 取消登记黑名单(blackListType从非0变0)) {
            //   notifyUpdate deviceIds的设备，
            //   deviceIds之外的设备要notifyDelete
            // } else if( blackListType非0且有变化的 ) {
            //    nofityUpdate所有设备
            // } else { // blackListType为0不变
            // 2.
            //   检查deviceIds,取消登记的设备要notifyDelete；
            //   新加入登记的设备要notifyAdd
            // 3.
            //   检查其他各项，有变化的，deviceIds保持登记的设备要notifyUpdate
            // }
            int oriBlackType = userBeforeModify.getBlackListType().intValue();
            int newBlackType = user.getBlackListType().intValue();
            List<String> listAllIds = deviceService.getDeviceAliaIdList();
            GetPtuidListResult busiRet = busiServerApiService.getPtuidList();
            if(busiRet.getState() == 0) {
                listAllIds = busiRet.getListPtuid();
            }
            log.info("listAllIds:{}", JSON.toJSONString(listAllIds));

            List<String> listAdd = new ArrayList<>();
            List<String> listUpdate = new ArrayList<>();
            List<String> listDelete = new ArrayList<>();
            if(CollectionUtils.isEmpty(listAllIds)) {
                return true;
            }
            // 1.
            if(oriBlackType == 0 && newBlackType > 0) {
                listAdd = listAllIds;
            } else if(oriBlackType > 0 && newBlackType == 0) {
                listUpdate = deviceService.getDeviceAliaIdList(user.getDeviceIds());
                String aliaIds = deviceService.getExcludeDeviceIds(listAllIds, listUpdate);
                listDelete = deviceService.getDeviceAliaIdListByAliaIds(aliaIds);
            } else if(oriBlackType > 0 && newBlackType > 0 && oriBlackType != newBlackType) {
                listUpdate = deviceService.getDeviceAliaIdList();
            } else if(oriBlackType == newBlackType && oriBlackType > 0) {
                listUpdate = deviceService.getDeviceAliaIdList();
            } else {
                List<String> listOriIds = deviceService.getDeviceAliaIdList(userBeforeModify.getDeviceIds());
                List<String> listNewIds = deviceService.getDeviceAliaIdList(user.getDeviceIds());
                listDelete = new ArrayList<>(listOriIds);
                listAdd = new ArrayList<>(listNewIds);
                for(String newId:listNewIds) {
                    for(String oriId:listDelete) {
                        if(newId.equalsIgnoreCase(oriId)) {
                            listDelete.remove(oriId);
                            break;
                        }
                    }
                }
                for(String oriId:listOriIds) {
                    for(String newId:listAdd) {
                        if(newId.equalsIgnoreCase(oriId)) {
                            listAdd.remove(newId);
                            break;
                        }
                    }
                }
                if(!userBeforeModify.equals(user)) {
                    listUpdate = deviceService.getDeviceAliaIdList(user.getDeviceIds());
                    for(String addId:listAdd) {
                        for(String updateId:listUpdate) {
                            if(updateId.equalsIgnoreCase(addId)) {
                                listUpdate.remove(updateId);
                                break;
                            }
                        }
                    }
                }
            }
            log.info("user update, notifyUpdate:{}, notifyAdd:{}, notifyDel:{}",
                    JSON.toJSONString(listUpdate),
                    JSON.toJSONString(listAdd),
                    JSON.toJSONString(listDelete));
            userCommon.notifyUpdate(user, listUpdate);
            userCommon.notifyAdd(user, listAdd);
            userCommon.notifyDel(user, listDelete);
        }
        return false;
    }

}
