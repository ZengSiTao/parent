package com.linktop.cloud.entranceguardclient.feignclient;

import com.linktop.cloud.commonutils.ServiceNames;
import com.linktop.cloud.commonutils.ServiceUrls;
import com.linktop.cloud.commonutils.VersionNumbers;
import com.linktop.cloud.entranceguardmodel.database.DeviceConfig;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@FeignClient(name = ServiceNames.ENTRANCE_GUARD_CORE)
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@ApiIgnore
public interface DeviceConfigClient {


    @RequestMapping(value = ServiceUrls.DeviceConfig.GETBYDEVID, method = RequestMethod.GET)
    @ApiOperation(value = "根据设备id获取设备配置", notes = "根据设备id获取设备配置")
    DeviceConfig getByDevId(@ApiParam(name = "devId", value = "数据库id", defaultValue = "")
                         @RequestParam(value = "devId", required = true) int devId);

    @RequestMapping(value = ServiceUrls.DeviceConfig.UPDATEBYID, method = RequestMethod.PUT)
    @ApiOperation(value = "更新设备配置", notes = "更新设备配置")
    String update(@ApiParam(name = "devCfg", value = "设备配置实体", defaultValue = "")
                        @RequestBody(required = true) DeviceConfig devCfg);

    @RequestMapping(value = ServiceUrls.DeviceConfig.UPDATEBYDEVICEALIAID, method = RequestMethod.PUT)
    @ApiOperation(value = "更新设备配置", notes = "更新设备配置")
    String updateByDeviceAliaId(
            @ApiParam(name = "deviceAliaId", value = "设备ID", defaultValue = "")
            @RequestParam(value = "deviceAliaId", required = true) String deviceAliaId,
            @ApiParam(name = "fnOnServer", value = "设备配置文件名", defaultValue = "")
            @RequestParam(value = "fnOnServer", required = true)String fnOnServer);

    @RequestMapping(value = ServiceUrls.DeviceConfig.DELBYID, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据id删除设备配置", notes = "根据id删除设备配置")
    String delete(@ApiParam(name = "id", value = "id", defaultValue = "")
                  @RequestParam(value = "id", required = true) int id);

    @RequestMapping(value = ServiceUrls.DeviceConfig.BATCHDEL, method = RequestMethod.GET)
    @ApiOperation(value = "根据id删除设备配置", notes = "根据id删除设备配置")
    String batchDelete(@ApiParam(name = "ids", value = "ids", defaultValue = "")
                       @RequestParam(value = "ids", required = true) List<Integer> ids);

    @RequestMapping(value = ServiceUrls.DeviceConfig.ADD, method = RequestMethod.POST)
    @ApiOperation(value = "添加设备配置", notes = "添加设备配置")
    String addDevice(@ApiParam(name = "devCfg", value = "设备配置实体", defaultValue = "")
                     @RequestBody(required = true) DeviceConfig devCfg);

    @RequestMapping(value = ServiceUrls.DeviceConfig.PUSHDEFAULTBYDEVID, method = RequestMethod.GET)
    @ApiOperation(value = "根据设备id推送默认配置", notes = "根据设备id推送默认配置")
    String pushDefaultByDevId(@ApiParam(name = "devId", value = "数据库id", defaultValue = "")
                               @RequestParam(value = "devId", required = true) int devId);

    @RequestMapping(value = ServiceUrls.DeviceConfig.PUSHDEFAULTBYPTUID, method = RequestMethod.GET)
    @ApiOperation(value = "根据PTUID推送默认配置", notes = "根据PTUID推送默认配置")
    String pushDefaultByDeviceAliaId(@ApiParam(name = "deviceAliaId", value = "PTUID", defaultValue = "")
                              @RequestParam(value = "deviceAliaId", required = true) String deviceAliaId);

}
