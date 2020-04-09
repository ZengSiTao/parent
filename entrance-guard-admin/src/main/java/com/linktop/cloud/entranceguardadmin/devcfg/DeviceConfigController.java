package com.linktop.cloud.entranceguardadmin.devcfg;

import com.linktop.cloud.commonutils.ServiceUrls;
import com.linktop.cloud.commonutils.VersionNumbers;
import com.linktop.cloud.entranceguardclient.feignclient.DeviceConfigClient;
import com.linktop.cloud.entranceguardmodel.database.Device;
import com.linktop.cloud.entranceguardmodel.database.DeviceConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "devCfg", description = "设备配置接口")
public class DeviceConfigController {

    @Autowired
    private DeviceConfigClient deviceConfigClient;


    @RequestMapping(value = ServiceUrls.DeviceConfig.GETBYDEVID, method = RequestMethod.GET)
    @ApiOperation(value = "根据设备id获取设备配置", notes = "根据设备id获取设备配置")
    DeviceConfig getByDevId(
            @ApiParam(name = "devId", value = "数据库id", defaultValue = "")
            @RequestParam(value = "devId", required = true) int devId){
        return deviceConfigClient.getByDevId(devId);
    }

    @RequestMapping(value = ServiceUrls.DeviceConfig.UPDATEBYID, method = RequestMethod.PUT)
    @ApiOperation(value = "更新设备配置", notes = "更新设备配置")
    String update(
            @ApiParam(name = "DeviceConfig", value = "设备配置实体", defaultValue = "")
            @RequestBody(required = true) DeviceConfig devCfg) {
        return deviceConfigClient.update(devCfg);
    }

    @RequestMapping(value = ServiceUrls.DeviceConfig.DELBYID, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据id删除设备配置", notes = "根据id删除设备配置")
    String delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = true) int id) {
        return deviceConfigClient.delete(id);
    }

    @RequestMapping(value = ServiceUrls.DeviceConfig.BATCHDEL, method = RequestMethod.GET)
    @ApiOperation(value = "根据id删除设备配置", notes = "根据id删除设备配置")
    String batchDelete(
            @ApiParam(name = "ids", value = "ids", defaultValue = "")
            @RequestParam(value = "ids", required = true) List<Integer> ids) {
        return deviceConfigClient.batchDelete(ids);
    }

    @RequestMapping(value = ServiceUrls.DeviceConfig.ADD, method = RequestMethod.POST)
    @ApiOperation(value = "添加设备配置", notes = "添加设备配置")
    String add(@ApiParam(name = "devCfg", value = "设备配置实体", defaultValue = "")
                     @RequestBody(required = true) DeviceConfig devCfg) {
        return deviceConfigClient.addDevice(devCfg);
    }

    @RequestMapping(value = ServiceUrls.DeviceConfig.PUSHDEFAULTBYDEVID, method = RequestMethod.GET)
    @ApiOperation(value = "根据设备id推送默认配置", notes = "根据设备id推送默认配置")
    String pushDefaultByDevId(@ApiParam(name = "devId", value = "数据库id", defaultValue = "")
                                    @RequestParam(value = "devId", required = true) int devId) {
        return deviceConfigClient.pushDefaultByDevId(devId);
    }

    @RequestMapping(value = ServiceUrls.DeviceConfig.PUSHDEFAULTBYPTUID, method = RequestMethod.GET)
    @ApiOperation(value = "根据设备id推送默认配置", notes = "根据设备id推送默认配置")
    String pushDefaultByDeviceAliaId(@ApiParam(name = "deviceAliaId", value = "PTUID", defaultValue = "")
                              @RequestParam(value = "deviceAliaId", required = true) String deviceAliaId) {
        return deviceConfigClient.pushDefaultByDeviceAliaId(deviceAliaId);
    }

}
