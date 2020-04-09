package com.linktop.cloud.entranceguardclient.feignclient;

import com.linktop.cloud.commonutils.ServiceNames;
import com.linktop.cloud.commonutils.ServiceUrls;
import com.linktop.cloud.commonutils.VersionNumbers;
import com.linktop.cloud.entranceguardmodel.database.Device;
import com.linktop.cloud.entranceguardmodel.PageWrapper;
import com.linktop.cloud.entranceguardmodel.database.ShareFile;
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
public interface DeviceClient {


    @RequestMapping(value = ServiceUrls.Device.LISTALL, method = RequestMethod.GET)
    @ApiOperation(value = "获取设备列表", notes = "获取设备列表")
    List<Device> getDeviceList();

    @RequestMapping(value = ServiceUrls.Device.PAGE, method = RequestMethod.GET)
    @ApiOperation(value = "获取设备列表分页", notes = "获取设备列表分页")
    PageWrapper<Device> getDevicePage(@ApiParam(name = "page", value = "第几页", defaultValue = "")
                               @RequestParam("page") int page,
                                      @ApiParam(name = "count", value = "每页数量", defaultValue = "")
                               @RequestParam("count") int count,
                                      @ApiParam(name = "filter", value = "查询过滤字符串", defaultValue = "")
                               @RequestParam("filter") String filter);

    @RequestMapping(value = ServiceUrls.Device.GETBYID, method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取设备", notes = "根据id获取设备")
    Device getDeviceById(@ApiParam(name = "id", value = "数据库id", defaultValue = "")
                         @RequestParam(value = "id", required = true) int id);

    @RequestMapping(value = ServiceUrls.Device.UPDATEBYID, method = RequestMethod.PUT)
    @ApiOperation(value = "更新设备", notes = "更新设备")
    String updateDevice(@ApiParam(name = "device", value = "设备实体", defaultValue = "")
                        @RequestBody(required = true) Device device);

    @RequestMapping(value = ServiceUrls.Device.DELBYID, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据id删除设备", notes = "根据id删除设备")
    String delete(@ApiParam(name = "id", value = "id", defaultValue = "")
                  @RequestParam(value = "id", required = true) int id);

    @RequestMapping(value = ServiceUrls.Device.BATCHDEL, method = RequestMethod.GET)
    @ApiOperation(value = "根据id删除设备", notes = "根据id删除设备")
    String batchDelete(@ApiParam(name = "ids", value = "ids", defaultValue = "")
                       @RequestParam(value = "ids", required = true) List<Integer> ids);

    @RequestMapping(value = ServiceUrls.Device.DELBYALIAID, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ptuid删除设备", notes = "根据ptuid删除设备")
    public String deleteByAliaId(
            @ApiParam(name = "aliaId", value = "ptuid", defaultValue = "")
            @RequestParam(value = "aliaId", required = true) String aliaId);

    @RequestMapping(value = ServiceUrls.Device.BATCHDELBYALIAID, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ptuid批量删除设备", notes = "根据ptuid批量删除设备")
    public String batchDelByAliaId(
            @ApiParam(name = "aliaIds", value = "ptuid列表", defaultValue = "")
            @RequestParam(value = "aliaIds", required = true) List<String> aliaIds);

    @RequestMapping(value = ServiceUrls.Device.ADD, method = RequestMethod.POST)
    @ApiOperation(value = "添加设备", notes = "添加设备")
    String addDevice(@ApiParam(name = "device", value = "设备实体", defaultValue = "")
                     @RequestBody(required = true) Device device);

    @RequestMapping(value = ServiceUrls.Device.GETINITFILELIST, method = RequestMethod.GET)
    @ApiOperation(value = "获取新迁移设备被共享的所有文件名", notes = "获取新迁移设备被共享的所有文件名")
    List<ShareFile> getInitFileList(@ApiParam(name = "usage", value = "usage", defaultValue = "")
                                    @RequestParam(value = "usage", required = true) String usage);


    @RequestMapping(value = ServiceUrls.Device.DELINITFILELIST, method = RequestMethod.GET)
    @ApiOperation(value = "删除新迁移设备被共享的所有文件名", notes = "删除新迁移设备被共享的所有文件名")
    int delInitFileList(@ApiParam(name = "deviceAliaId", value = "deviceAliaId", defaultValue = "")
                        @RequestParam(value = "deviceAliaId", required = true) String deviceAliaId);


}
