package com.linktop.cloud.entranceguardadmin.device;

import com.linktop.cloud.commonutils.ServiceUrls;
import com.linktop.cloud.commonutils.VersionNumbers;
import com.linktop.cloud.entranceguardclient.feignclient.DeviceClient;
import com.linktop.cloud.entranceguardmodel.database.Device;
import com.linktop.cloud.entranceguardmodel.PageWrapper;
import com.linktop.cloud.entranceguardmodel.database.ShareFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "device", description = "设备接口")
public class DeviceController {

    @Autowired
    private DeviceClient deviceClient;

    @RequestMapping(value = ServiceUrls.Device.LISTALL, method = RequestMethod.GET)
    @ApiOperation(value = "获取设备列表", notes = "获取设备列表")
    List<Device> getDeviceList() {
        return deviceClient.getDeviceList();
    };

    @RequestMapping(value = ServiceUrls.Device.PAGE, method = RequestMethod.GET)
    @ApiOperation(value = "获取设备列表分页", notes = "获取设备列表分页")
    PageWrapper<Device> getDevicePage(@ApiParam(name = "page", value = "第几页", defaultValue = "")
                               @RequestParam(value = "page", required = true) int page,
                                      @ApiParam(name = "count", value = "每页数量", defaultValue = "")
                               @RequestParam(value = "count", required = true) int count,
                                      @ApiParam(name = "filter", value = "查询过滤字符串", defaultValue = "")
                               @RequestParam(value = "filter", required = false) String filter) {
        return deviceClient.getDevicePage(page, count, filter);
    }

    @RequestMapping(value = ServiceUrls.Device.GETBYID, method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取设备", notes = "根据id获取设备")
    Device getDeviceById(
            @ApiParam(name = "id", value = "数据库id", defaultValue = "")
            @RequestParam(value = "id", required = true) int id){
        return deviceClient.getDeviceById(id);
    }

    @RequestMapping(value = ServiceUrls.Device.UPDATEBYID, method = RequestMethod.PUT)
    @ApiOperation(value = "更新设备", notes = "更新设备")
    String updateDevice(
            @ApiParam(name = "Device", value = "设备实体", defaultValue = "")
            @RequestBody(required = true) Device device) {
        return deviceClient.updateDevice(device);
    }

    @RequestMapping(value = ServiceUrls.Device.DELBYID, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据id删除设备", notes = "根据id删除设备")
    String delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = true) int id) {
        return deviceClient.delete(id);
    }

    @RequestMapping(value = ServiceUrls.Device.BATCHDEL, method = RequestMethod.GET)
    @ApiOperation(value = "根据id删除设备", notes = "根据id删除设备")
    String batchDelete(
            @ApiParam(name = "ids", value = "ids", defaultValue = "")
            @RequestParam(value = "ids", required = true) List<Integer> ids) {
        return deviceClient.batchDelete(ids);
    }

    @RequestMapping(value = ServiceUrls.Device.DELBYALIAID, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ptuid删除设备", notes = "根据ptuid删除设备")
    public String deleteByAliaId(
            @ApiParam(name = "aliaId", value = "ptuid", defaultValue = "")
            @RequestParam(value = "aliaId", required = true) String aliaId) {
        return deviceClient.deleteByAliaId(aliaId);
    }

    @RequestMapping(value = ServiceUrls.Device.BATCHDELBYALIAID, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ptuid批量删除设备", notes = "根据ptuid批量删除设备")
    public String batchDelByAliaId(
            @ApiParam(name = "aliaIds", value = "ptuid列表", defaultValue = "")
            @RequestParam(value = "aliaIds", required = true) List<String> aliaIds) {
        return deviceClient.batchDelByAliaId(aliaIds);
    }

    @RequestMapping(value = ServiceUrls.Device.ADD, method = RequestMethod.POST)
    @ApiOperation(value = "添加设备", notes = "添加设备")
    String addDevice(@ApiParam(name = "device", value = "设备实体", defaultValue = "")
                     @RequestBody(required = true) Device device) {
        return deviceClient.addDevice(device);
    }

    @RequestMapping(value = ServiceUrls.Device.GETINITFILELIST, method = RequestMethod.GET)
    @ApiOperation(value = "获取新迁移设备被共享的所有文件名", notes = "获取新迁移设备被共享的所有文件名")
    List<ShareFile> getInitFileList(@ApiParam(name = "usage", value = "usage", defaultValue = "")
                                    @RequestParam(value = "usage", required = true) String usage) {
        return deviceClient.getInitFileList(usage);
    }

    @RequestMapping(value = ServiceUrls.Device.DELINITFILELIST, method = RequestMethod.GET)
    @ApiOperation(value = "删除新迁移设备被共享的所有文件名", notes = "删除新迁移设备被共享的所有文件名")
    int delInitFileList(@ApiParam(name = "deviceAliaId", value = "deviceAliaId", defaultValue = "")
                        @RequestParam(value = "deviceAliaId", required = true) String deviceAliaId) {
        return deviceClient.delInitFileList(deviceAliaId);
    }


}
