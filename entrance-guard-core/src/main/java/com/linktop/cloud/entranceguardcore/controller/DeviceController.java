package com.linktop.cloud.entranceguardcore.controller;


import com.linktop.cloud.commonutils.ServiceUrls;
import com.linktop.cloud.commonutils.VersionNumbers;
import com.linktop.cloud.entranceguardcore.service.DeviceService;
import com.linktop.cloud.entranceguardmodel.database.Device;
import com.linktop.cloud.entranceguardmodel.PageWrapper;
import com.linktop.cloud.entranceguardmodel.database.ShareFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
public class DeviceController {

    @Autowired
    DeviceService deviceService;


    @RequestMapping(value = ServiceUrls.Device.LISTALL, method = RequestMethod.GET)
    public List<Device> getList() {
        return deviceService.getList();
    }

    @RequestMapping(value = ServiceUrls.Device.PAGE, method = RequestMethod.GET)
    PageWrapper<Device> getPage(@RequestParam(value = "page", required = true) int page,
                                      @RequestParam(value = "count", required = true) int count,
                                      @RequestParam(value = "filter", required = false) String filter) {
        return deviceService.getPage(page, count, filter);
    }
    @RequestMapping(value = ServiceUrls.Device.GETBYID, method = RequestMethod.GET)
    public Device getById(@RequestParam(value = "id", required = true) int id) {

        return deviceService.get(id);
    }

    @RequestMapping(value = ServiceUrls.Device.UPDATEBYID, method = RequestMethod.PUT)
    public String update(@RequestBody(required = true) Device device) {
        int t = deviceService.update(device);
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = ServiceUrls.Device.DELBYID, method = RequestMethod.DELETE)
    public String delete(@RequestParam(value = "id", required = true) int id) {
        int t = deviceService.delete(id);
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = ServiceUrls.Device.BATCHDEL, method = RequestMethod.GET)
    public String batchDel(@RequestParam(value = "ids", required = true) List<Integer> ids) {
        int t = deviceService.batchDel(ids);
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = ServiceUrls.Device.DELBYALIAID, method = RequestMethod.DELETE)
    public String deleteByAliaId(@RequestParam(value = "aliaId", required = true) String aliaId) {
        int t = deviceService.deleteByAliaId(aliaId);
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = ServiceUrls.Device.BATCHDELBYALIAID, method = RequestMethod.DELETE)
    public String batchDelByAliaId(@RequestParam(value = "aliaIds", required = true) List<String> aliaIds) {
        int t = deviceService.batchDelByAliaId(aliaIds);
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = ServiceUrls.Device.ADD, method = RequestMethod.POST)
    public String add(@RequestBody(required = true) Device device) {
        int t = deviceService.add(device.getBuildingNum(),
                                  device.getUnitNum(),
                                  device.getMacAddr(),
                                  device.getType(),
                                  device.getEstateId(),
                                  device.getAliaId());
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = ServiceUrls.Device.GETINITFILELIST, method = RequestMethod.GET)
    List<ShareFile> getInitFileList(@RequestParam(value = "usage", required = true) String usage) {
        return deviceService.getInitFileList(usage);
    }

    @RequestMapping(value = ServiceUrls.Device.DELINITFILELIST, method = RequestMethod.GET)
    int delInitFileList(@RequestParam(value = "deviceAliaId", required = true) String deviceAliaId) {
        return deviceService.delInitFileList(deviceAliaId);
    }
}