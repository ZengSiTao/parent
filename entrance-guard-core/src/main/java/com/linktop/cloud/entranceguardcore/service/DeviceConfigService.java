package com.linktop.cloud.entranceguardcore.service;

import com.alibaba.fastjson.JSON;
import com.linktop.cloud.commonutils.BusiSvrUploadFileUsage;
import com.linktop.cloud.commonutils.HashMapStore;
import com.linktop.cloud.entranceguardcore.dao.DeviceConfigMapper;
import com.linktop.cloud.entranceguardmodel.BusiCommonResult;
import com.linktop.cloud.entranceguardmodel.FileDownloadResult;
import com.linktop.cloud.entranceguardmodel.FileUploadResult;
import com.linktop.cloud.entranceguardmodel.GetPtuidListResult;
import com.linktop.cloud.entranceguardmodel.database.Device;
import com.linktop.cloud.entranceguardmodel.database.DeviceConfig;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceConfigService {
    private final boolean LIVE_DETECT_ON = true;
    private final boolean REGZ_SAVE_LOCAL_ON = true;
    private final int REGZ_SAVE_LOCAL_TIME = 720;
    private final boolean REGISTER_SAVE_LOCAL_ON = false;
    private final boolean REGZ_VIDEO_ON = true;
    private final boolean REGZ_ALWAYS_ON = false;
    private final int REGZ_OFF_TIME = 120;

    private static Logger log = LoggerFactory.getLogger(DeviceConfigService.class);
    @Autowired
    private DeviceConfigMapper deviceConfigMapper;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private BusiServerApiService busiServerApiService;
    @Autowired
    private HashMapStore hashMapStore;
    @Autowired
    private ShareFileService shareFileService;

    public int add(DeviceConfig devCfg) {
        int ret = 0;
        byte[] json = null;
        try {
            json = JSON.toJSONString(devCfg).getBytes("utf-8");
        } catch (Exception e) {
            json = JSON.toJSONString(devCfg).getBytes();
        }
        FileUploadResult fr = busiServerApiService.fileUpload(BusiSvrUploadFileUsage.DEVICE_CONFIG, json);
        if(fr.getState() != 0) {
            log.info("DeviceConfigService add share to device failed, state={}", fr.getState());
        } else {
            GetPtuidListResult ptuidList = busiServerApiService.getPtuidList();
            if(ptuidList.getState() != 0) {
                log.error("cannot get ptuid list from server");
                return 0;
            }
            String exc = deviceService.getExcludeDeviceIds(
                    ptuidList.getListPtuid(),
                    devCfg.getDeviceAliaId());
            BusiCommonResult sr = busiServerApiService.shareFile(fr.getFn(), null, exc);
            if(sr.getState() != 0) {
                log.info("DeviceConfigService add share to device failed, state={}", sr.getState());
                return 0;
            } else {
                shareFileService.add(BusiSvrUploadFileUsage.DEVICE_CONFIG,
                        "add",
                        devCfg.getDeviceAliaId(),
                        null,
                        fr.getFn(),
                        false);

                devCfg.setFnOnServer(fr.getFn());
                ret = deviceConfigMapper.add(
                        devCfg.getDevId(),
                        devCfg.getFnOnServer());
            }
        }
        return ret;
    }

    public int update(DeviceConfig devCfg) {
        int ret = 0;

        byte[] json = null;
        try {
            json = JSON.toJSONString(devCfg).getBytes("utf-8");
        } catch (Exception e) {
            json = JSON.toJSONString(devCfg).getBytes();
        }
        FileUploadResult fr = busiServerApiService.fileUpload(BusiSvrUploadFileUsage.DEVICE_CONFIG, json);
        if(fr.getState() != 0) {
            log.info("DeviceConfigService add share to device failed, state={}", fr.getState());
        } else {
            GetPtuidListResult ptuidList = busiServerApiService.getPtuidList();
            if(ptuidList.getState() != 0) {
                log.error("cannot get ptuid list from server");
                return 0;
            }
            String exc = deviceService.getExcludeDeviceIds(
                    ptuidList.getListPtuid(),
                    devCfg.getDeviceAliaId());
            BusiCommonResult sr = busiServerApiService.shareFile(fr.getFn(), null, exc);
            if(sr.getState() != 0) {
                log.info("DeviceConfigService add share to device failed, state={}", sr.getState());
                return 0;
            } else {
                shareFileService.add(BusiSvrUploadFileUsage.DEVICE_CONFIG,
                        "update",
                        devCfg.getDeviceAliaId(),
                        null,
                        fr.getFn(),
                        false);

                devCfg.setFnOnServer(fr.getFn());
                ret = deviceConfigMapper.update(
                        devCfg.getId(),
                        devCfg.getDevId(),
                        devCfg.getFnOnServer());
            }
        }
        return ret;
    }


    public int updateByDeviceAliaId(String deviceAliaId,
                                    String fnOnServer) {
        int ret = 0;

        ret = deviceConfigMapper.updateByDeviceAliaId(
                deviceAliaId,
                fnOnServer);
        if(ret == 0) {
            Device device = deviceService.getByAliaId(deviceAliaId);
            ret = deviceConfigMapper.add(
                            device.getId(),
                            fnOnServer);
        }
        return ret;

    }


    public int delete(int id) {
        return deviceConfigMapper.delete(id);
    }

    public int batchDel(List<Integer> ids) {
        return deviceConfigMapper.batchDel(ids);
    }

    public DeviceConfig getByDevId(int devId) {

        Device device = deviceService.get(devId);
        if(null == device || StringUtils.isEmpty(device.getAliaId())) {
            return null;
        }

        DeviceConfig devCfg = deviceConfigMapper.getByDevId(devId);
        boolean bSetDefault = false;
        if(devCfg == null) {
            //pushDefaultByDevId(devId);
            busiServerApiService.getFile(device.getAliaId(), BusiSvrUploadFileUsage.DEVICE_CONFIG);
            devCfg = new DeviceConfig();
            devCfg.setDevId(devId);
            devCfg.setGetAgain(true);
            devCfg.setDeviceAliaId(device.getAliaId());
            bSetDefault = true;
        } else {
            if(StringUtils.isEmpty(devCfg.getFnOnServer())) {
                bSetDefault = true;
            } else {
                FileDownloadResult fdload = busiServerApiService.fileDownload(BusiSvrUploadFileUsage.DEVICE_CONFIG, devCfg.getFnOnServer());
                if(0 == fdload.getState()) {
                    String strDevCfg = new String(fdload.getBody());
                    log.info("device config JSON:{}", strDevCfg);
                    DeviceConfig devCfgOnServer = JSON.parseObject(strDevCfg, DeviceConfig.class);
                    if(devCfgOnServer == null) {
                        bSetDefault = true;
                    } else {
                        devCfgOnServer.setDeviceAliaId(devCfg.getDeviceAliaId());
                        devCfgOnServer.setId(devCfg.getId());
                        devCfg = devCfgOnServer;
                    }
                } else {
                    bSetDefault = true;
                }
            }

        }

        if(bSetDefault) {
            devCfg.setLiveDetectOn(LIVE_DETECT_ON);
            devCfg.setRegisterSaveLocalOn(REGISTER_SAVE_LOCAL_ON);
            devCfg.setRegzAlwaysOn(REGZ_ALWAYS_ON);
            devCfg.setRegzOffTime(REGZ_OFF_TIME);
            devCfg.setRegzSaveLocalOn(REGZ_SAVE_LOCAL_ON);
            devCfg.setRegzSaveLocalTime(REGZ_SAVE_LOCAL_TIME);
            devCfg.setRegzVideoOn(REGZ_VIDEO_ON);
        }
        return devCfg;
    }


    public int pushDefaultByDevId(int devId) {
        Device device = deviceService.get(devId);
        if(null == device || StringUtils.isEmpty(device.getAliaId())) {
            return 0;
        }
        String defaultFnOnServer = hashMapStore.getString(hashMapStore.KEY_DEVCFG_DEFAULT_FN_ON_SERVER);
        if(defaultFnOnServer != null) {
            String exc = deviceService.getExcludeDeviceIds(
                    deviceService.getDeviceAliaIdList(),
                    device.getAliaId());
            BusiCommonResult sr = busiServerApiService.shareFile(defaultFnOnServer, null, exc);
            if(sr.getState() != 0) {
                log.info("DeviceConfigService add share to device failed, state={}", sr.getState());
                return 0;
            }
        } else {

            DeviceConfig devCfg = new DeviceConfig();

            devCfg.setLiveDetectOn(LIVE_DETECT_ON);
            devCfg.setRegisterSaveLocalOn(REGISTER_SAVE_LOCAL_ON);
            devCfg.setRegzAlwaysOn(REGZ_ALWAYS_ON);
            devCfg.setRegzOffTime(REGZ_OFF_TIME);
            devCfg.setRegzSaveLocalOn(REGZ_SAVE_LOCAL_ON);
            devCfg.setRegzSaveLocalTime(REGZ_SAVE_LOCAL_TIME);
            devCfg.setRegzVideoOn(REGZ_VIDEO_ON);

            byte[] json = null;
            try {
                json = JSON.toJSONString(devCfg).getBytes("utf-8");
            } catch (Exception e) {
                json = JSON.toJSONString(devCfg).getBytes();
            }
            FileUploadResult fr = busiServerApiService.fileUpload(BusiSvrUploadFileUsage.DEVICE_CONFIG, json);
            if(fr.getState() != 0) {
                log.info("DeviceConfigService add share to device failed, state={}", fr.getState());
            } else {
                hashMapStore.putString(hashMapStore.KEY_DEVCFG_DEFAULT_FN_ON_SERVER, fr.getFn());
                String exc = deviceService.getExcludeDeviceIds(
                        deviceService.getDeviceAliaIdList(),
                        devCfg.getDeviceAliaId());
                BusiCommonResult sr = busiServerApiService.shareFile(fr.getFn(), null, exc);
                if(sr.getState() != 0) {
                    log.info("DeviceConfigService add share to device failed, state={}", sr.getState());
                    return 0;
                }
            }

        }
        return 1;
    }



    public int pushDefaultByDeviceAliaId(String deviceAliaId) {

        if(deviceAliaId == null) {
            return 0;
        }
        String defaultFnOnServer = hashMapStore.getString(hashMapStore.KEY_DEVCFG_DEFAULT_FN_ON_SERVER);
        if(defaultFnOnServer != null) {
            BusiCommonResult sr = busiServerApiService.shareFile(defaultFnOnServer, deviceAliaId, null);
            if(sr.getState() != 0) {
                log.info("DeviceConfigService add share to device failed, state={}", sr.getState());
                return 0;
            }
        } else {

            DeviceConfig devCfg = new DeviceConfig();

            devCfg.setLiveDetectOn(LIVE_DETECT_ON);
            devCfg.setRegisterSaveLocalOn(REGISTER_SAVE_LOCAL_ON);
            devCfg.setRegzAlwaysOn(REGZ_ALWAYS_ON);
            devCfg.setRegzOffTime(REGZ_OFF_TIME);
            devCfg.setRegzSaveLocalOn(REGZ_SAVE_LOCAL_ON);
            devCfg.setRegzSaveLocalTime(REGZ_SAVE_LOCAL_TIME);
            devCfg.setRegzVideoOn(REGZ_VIDEO_ON);

            byte[] json = null;
            try {
                json = JSON.toJSONString(devCfg).getBytes("utf-8");
            } catch (Exception e) {
                json = JSON.toJSONString(devCfg).getBytes();
            }
            FileUploadResult fr = busiServerApiService.fileUpload(BusiSvrUploadFileUsage.DEVICE_CONFIG, json);
            if(fr.getState() != 0) {
                log.info("DeviceConfigService add share to device failed, state={}", fr.getState());
            } else {
                hashMapStore.putString(hashMapStore.KEY_DEVCFG_DEFAULT_FN_ON_SERVER, fr.getFn());
                String exc = deviceService.getExcludeDeviceIds(
                        deviceService.getDeviceAliaIdList(),
                        deviceAliaId);
                BusiCommonResult sr = busiServerApiService.shareFile(fr.getFn(), null, exc);
                if(sr.getState() != 0) {
                    log.info("DeviceConfigService add share to device failed, state={}", sr.getState());
                    return 0;
                }
            }

        }
        return 1;
    }
}
