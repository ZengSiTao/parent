package com.linktop.cloud.entranceguardcore.controller;


import com.linktop.cloud.commonutils.ServiceUrls;
import com.linktop.cloud.commonutils.VersionNumbers;
import com.linktop.cloud.entranceguardcore.service.DeviceConfigService;
import com.linktop.cloud.entranceguardmodel.database.DeviceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
public class DeviceConfigController {

    @Autowired
    DeviceConfigService deviceConfigService;

    @RequestMapping(value = ServiceUrls.DeviceConfig.GETBYDEVID, method = RequestMethod.GET)
    public DeviceConfig getByDevId(@RequestParam(value = "devId", required = true) int devId) {

        return deviceConfigService.getByDevId(devId);
    }

    @RequestMapping(value = ServiceUrls.DeviceConfig.UPDATEBYID, method = RequestMethod.PUT)
    public String update(@RequestBody(required = true) DeviceConfig devcfg) {
        int t = 0;
        if(devcfg.getId() == 0) {
            if(StringUtils.isEmpty(devcfg.getDeviceAliaId())) {
                return "fail";
            }
            t = deviceConfigService.add(devcfg);
        } else {
            t = deviceConfigService.update(devcfg);
        }

        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = ServiceUrls.DeviceConfig.UPDATEBYDEVICEALIAID, method = RequestMethod.PUT)
    public String updateByDeviceAliaId(
            @RequestParam(value = "deviceAliaId", required = true) String deviceAliaId,
            @RequestParam(value = "fnOnServer", required = true)String fnOnServer) {
        int t = 0;
        t = deviceConfigService.updateByDeviceAliaId(deviceAliaId, fnOnServer);
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = ServiceUrls.DeviceConfig.DELBYID, method = RequestMethod.DELETE)
    public String delete(@RequestParam(value = "id", required = true) int id) {
        int t = deviceConfigService.delete(id);
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = ServiceUrls.DeviceConfig.BATCHDEL, method = RequestMethod.GET)
    public String batchDel(@RequestParam(value = "ids", required = true) List<Integer> ids) {
        int t = deviceConfigService.batchDel(ids);
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = ServiceUrls.DeviceConfig.ADD, method = RequestMethod.POST)
    public String add(@RequestBody(required = true) DeviceConfig devCfg) {
        int t = deviceConfigService.add(devCfg);
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = ServiceUrls.DeviceConfig.PUSHDEFAULTBYDEVID, method = RequestMethod.GET)
    public String pushDefaultByDevId(@RequestParam(value = "devId", required = true) int devId) {
        int t = deviceConfigService.pushDefaultByDevId(devId);
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = ServiceUrls.DeviceConfig.PUSHDEFAULTBYPTUID, method = RequestMethod.GET)
    public String pushDefaultByDevId(@RequestParam(value = "deviceAliaId", required = true) String deviceAliaId) {
        int t = deviceConfigService.pushDefaultByDeviceAliaId(deviceAliaId);
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }

}