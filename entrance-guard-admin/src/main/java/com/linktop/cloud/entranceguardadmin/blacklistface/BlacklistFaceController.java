package com.linktop.cloud.entranceguardadmin.blacklistface;

import com.linktop.cloud.commonutils.ServiceUrls;
import com.linktop.cloud.commonutils.VersionNumbers;
import com.linktop.cloud.entranceguardclient.feignclient.BlacklistFaceClient;
import com.linktop.cloud.entranceguardclient.feignclient.BlacklistICCardClient;
import com.linktop.cloud.entranceguardmodel.PageWrapper;
import com.linktop.cloud.entranceguardmodel.database.BlacklistFace;
import com.linktop.cloud.entranceguardmodel.database.BlacklistICCard;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "Face Blacklist", description = "人脸黑名单接口")
public class BlacklistFaceController {

    @Autowired
    private BlacklistFaceClient blacklistFaceClient;

    @RequestMapping(value = ServiceUrls.BlacklistFace.LISTALL, method = RequestMethod.GET)
    @ApiOperation(value = "获取人脸黑名单列表", notes = "获取人脸黑名单列表")
    List<BlacklistFace> getList(@ApiParam(name = "deviceId", value = "设备id", defaultValue = "")
                                @RequestParam("deviceId") int deviceId) {
        return blacklistFaceClient.getList(deviceId);
    };

    @RequestMapping(value = ServiceUrls.BlacklistFace.PAGE, method = RequestMethod.GET)
    @ApiOperation(value = "获取人脸黑名单列表分页", notes = "获取人脸黑名单列表分页")
    PageWrapper<BlacklistFace> getPage(@ApiParam(name = "page", value = "第几页", defaultValue = "")
                                       @RequestParam(value = "page", required = true) int page,
                                       @ApiParam(name = "count", value = "每页数量", defaultValue = "")
                                       @RequestParam(value = "count", required = true) int count,
                                       @ApiParam(name = "filter", value = "查询过滤字符串", defaultValue = "")
                                       @RequestParam(value = "filter", required = false) String filter,
                                       @ApiParam(name = "deviceId", value = "设备id", defaultValue = "")
                                       @RequestParam("deviceId") int deviceId) {
        return blacklistFaceClient.getPage(page, count, filter, deviceId);
    }

    @RequestMapping(value = ServiceUrls.BlacklistFace.GETBYID, method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取人脸黑名单", notes = "根据id获取人脸黑名单")
    BlacklistFace getById(
            @ApiParam(name = "id", value = "数据库id", defaultValue = "")
            @RequestParam(value = "id", required = true) int id){
        return blacklistFaceClient.getById(id);
    }

    @RequestMapping(value = ServiceUrls.BlacklistFace.UPDATEBYID, method = RequestMethod.PUT)
    @ApiOperation(value = "更新人脸黑名单", notes = "更新人脸黑名单")
    String update(
            @ApiParam(name = "blacklisticcard", value = "人脸黑名单实体", defaultValue = "")
            @RequestBody(required = true) BlacklistFace blacklistface) {
        return blacklistFaceClient.update(blacklistface);
    }

    @RequestMapping(value = ServiceUrls.BlacklistFace.DELBYID, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据id删除人脸黑名单", notes = "根据id删除人脸黑名单")
    String delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = true) int id) {
        return blacklistFaceClient.delete(id);
    }

    @RequestMapping(value = ServiceUrls.BlacklistFace.BATCHDEL, method = RequestMethod.GET)
    @ApiOperation(value = "根据id删除人脸黑名单", notes = "根据id删除人脸黑名单")
    String batchDelete(
            @ApiParam(name = "ids", value = "ids", defaultValue = "")
            @RequestParam(value = "ids", required = true) List<Integer> ids) {
        return blacklistFaceClient.batchDelete(ids);
    }

    @RequestMapping(value = ServiceUrls.BlacklistFace.ADD, method = RequestMethod.POST)
    @ApiOperation(value = "添加人脸黑名单", notes = "添加人脸黑名单")
    String add(@ApiParam(name = "blacklistface", value = "人脸黑名单实体", defaultValue = "")
                     @RequestBody(required = true) BlacklistFace blacklistface) {
        return blacklistFaceClient.add(blacklistface);
    }

    @RequestMapping(value = ServiceUrls.BlacklistFace.BATCHADD, method = RequestMethod.POST)
    @ApiOperation(value = "批量添加人脸黑名单", notes = "批量添加人脸黑名单")
    String batchAdd(@ApiParam(name = "blacklistface_list", value = "人脸黑名单实体列表", defaultValue = "")
                    @RequestBody(required = true) List<BlacklistFace> blacklistface_list) {
        return blacklistFaceClient.batchAdd(blacklistface_list);
    }

}
