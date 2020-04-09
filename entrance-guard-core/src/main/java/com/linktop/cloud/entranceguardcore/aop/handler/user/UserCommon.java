package com.linktop.cloud.entranceguardcore.aop.handler.user;

import com.alibaba.fastjson.JSON;
import com.linktop.cloud.coder.Coder;
import com.linktop.cloud.commonutils.BusiSvrUploadFileUsage;
import com.linktop.cloud.entranceguardcore.service.BusiServerApiService;
import com.linktop.cloud.entranceguardcore.service.DeviceService;
import com.linktop.cloud.entranceguardcore.service.ShareFileService;
import com.linktop.cloud.entranceguardcore.service.UsersService;
import com.linktop.cloud.entranceguardmodel.BusiCommonResult;
import com.linktop.cloud.entranceguardmodel.FileDownloadResult;
import com.linktop.cloud.entranceguardmodel.FileUploadResult;
import com.linktop.cloud.entranceguardmodel.GetPtuidListResult;
import com.linktop.cloud.entranceguardmodel.database.User;
import com.linktop.cloud.entranceguardmodel.sync.SyncVOConvert;
import com.linktop.cloud.entranceguardmodel.sync.UserForDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class UserCommon {
    private static Logger log = LoggerFactory.getLogger(UserCommon.class);

    @Autowired
    private ShareFileService shareFileService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private BusiServerApiService busiServerApiService;

    public int notifyAdd(User user, List<String> listDeviceAliaId) {
        int ret = 0;
        if(user == null || CollectionUtils.isEmpty(listDeviceAliaId)) {
            return 1;
        }
        UserForDevice u2d = getUserForDevice(user, "add");

        try {
            FileUploadResult fr = busiServerApiService.fileUpload(BusiSvrUploadFileUsage.USER_INFO, JSON.toJSONString(u2d).getBytes("utf-8"));
            if (fr.getState() != 0) {
                log.info("UserService add to device failed, state={}", fr.getState());
            } else {
                log.info("user add,shareFile to {}", "".join(",", listDeviceAliaId));
                GetPtuidListResult busiRet = busiServerApiService.getPtuidList();
                if(busiRet.getState() != 0) {
                    throw new Exception("get ptuid list from busi server failed!");
                }
                List<String> listAllIds = busiRet.getListPtuid();
                log.info("listAllIds:{}", JSON.toJSONString(listAllIds));
                String exc = deviceService.getExcludeDeviceIds(listAllIds, listDeviceAliaId);
                log.info("exc:{}", exc);
                BusiCommonResult sr = busiServerApiService.shareFile(fr.getFn(), null, exc);
                if (sr.getState() != 0) {
                    ret = 1;
                    log.error("userService add to device failed, state={}", sr.getState());
                } else {
                    shareFileService.addMutiDev(BusiSvrUploadFileUsage.USER_INFO,
                            "add",
                            listDeviceAliaId,
                            user.getAliaId(),
                            fr.getFn());
                }
            }
        }  catch (Exception e) {
            log.error("userService add to device failed, exception={}", e.getMessage());
        }
        return ret;
    }

    public int notifyUpdate(User user, List<String> listDeviceAliaId) {
        int ret = 0;
        if(user == null || CollectionUtils.isEmpty(listDeviceAliaId)) {
            return 1;
        }

        UserForDevice u2d = getUserForDevice(user, "update");

        try {
            FileUploadResult fr = busiServerApiService.fileUpload(BusiSvrUploadFileUsage.USER_INFO, JSON.toJSONString(u2d).getBytes("utf-8"));
            if(fr.getState() == 0) {
                log.info("user update,shareFile to {}", "".join(",", listDeviceAliaId));
                GetPtuidListResult busiRet = busiServerApiService.getPtuidList();
                if(busiRet.getState() != 0) {
                    throw new Exception("get ptuid list from busi server failed!");
                }
                List<String> listAllIds = busiRet.getListPtuid();
                String exc = deviceService.getExcludeDeviceIds(listAllIds, listDeviceAliaId);
                BusiCommonResult sr = busiServerApiService.shareFile(fr.getFn(), null, exc);
                if(sr.getState() != 0) {
                    ret = 1;
                    log.info("userService update to device failed, state={}", sr.getState());
                } else {
                    shareFileService.addMutiDev(BusiSvrUploadFileUsage.USER_INFO,
                            "update",
                            listDeviceAliaId,
                            user.getAliaId(),
                            fr.getFn());
                }
            }
        } catch (Exception e) {
            log.info("userService update to device failed, exception={}", e.getMessage());
        }

        return ret;
    }

    public int notifyDel(User user, List<String> listDeviceAliaId) {
        int ret = 0;
        log.info("notifyDel user:{} listDeviceAliaId:{}",
                JSON.toJSONString(user),
                JSON.toJSONString(listDeviceAliaId));
        if(user == null || CollectionUtils.isEmpty(listDeviceAliaId)) {
            return ret;
        }
        for(String deviceAliaId:listDeviceAliaId) {
            shareFileService.delCurrentFile(
                    BusiSvrUploadFileUsage.USER_INFO,
                    deviceAliaId,
                    user.getAliaId());
        }
        return ret;
    }

    private UserForDevice getUserForDevice(User user, String op) {

        UserForDevice u2d = new UserForDevice();
        SyncVOConvert.convert_User_UserForDevice(user, u2d);
        //u2d.setOpType(op);
        FileDownloadResult fdload = null;
        if(op.equalsIgnoreCase("delete")) {
            u2d.setImgB64("");
        } else {
            if(!StringUtils.isEmpty(user.getFnOnServer())) {
                fdload = busiServerApiService.fileDownload("face", user.getFnOnServer());
                if(0 == fdload.getState()){
                    try {
                        u2d.setImgB64(Coder.encryptBASE64(fdload.getBody()));
                    } catch (Exception e) {
                        log.info("userService update to device failed, exception={}", e.getMessage());
                    }
                }
            }
        }

        return u2d;
    }

}
