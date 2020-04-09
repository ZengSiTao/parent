package com.linktop.cloud.entranceguardadmin.blacklisticcard;

import com.linktop.cloud.commonutils.ServiceUrls;
import com.linktop.cloud.commonutils.VersionNumbers;
import com.linktop.cloud.entranceguardclient.feignclient.BlacklistICCardClient;
import com.linktop.cloud.entranceguardclient.feignclient.ICCardClient;
import com.linktop.cloud.entranceguardmodel.PageWrapper;
import com.linktop.cloud.entranceguardmodel.database.BlacklistICCard;
import com.linktop.cloud.entranceguardmodel.database.ICCard;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "IC Card Blacklist", description = "IC卡黑名单接口")
public class BlacklistICCardController {

    @Autowired
    private BlacklistICCardClient blacklistICCardClient;

    @RequestMapping(value = ServiceUrls.BlacklistICCard.LISTALL, method = RequestMethod.GET)
    @ApiOperation(value = "获取IC卡黑名单列表", notes = "获取IC卡黑名单列表")
    List<BlacklistICCard> getList(@ApiParam(name = "deviceId", value = "设备id", defaultValue = "")
                                  @RequestParam("deviceId") int deviceId) {
        return blacklistICCardClient.getList(deviceId);
    };

    @RequestMapping(value = ServiceUrls.BlacklistICCard.PAGE, method = RequestMethod.GET)
    @ApiOperation(value = "获取IC卡黑名单列表分页", notes = "获取IC卡黑名单列表分页")
    PageWrapper<BlacklistICCard> getPage(@ApiParam(name = "page", value = "第几页", defaultValue = "")
                                         @RequestParam(value = "page", required = true) int page,
                                         @ApiParam(name = "count", value = "每页数量", defaultValue = "")
                                         @RequestParam(value = "count", required = true) int count,
                                         @ApiParam(name = "filter", value = "查询过滤字符串", defaultValue = "")
                                         @RequestParam(value = "filter", required = false) String filter,
                                         @ApiParam(name = "deviceId", value = "设备id", defaultValue = "")
                                         @RequestParam("deviceId") int deviceId) {
        return blacklistICCardClient.getPage(page, count, filter, deviceId);
    }

    @RequestMapping(value = ServiceUrls.BlacklistICCard.GETBYID, method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取IC卡黑名单", notes = "根据id获取IC卡黑名单")
    BlacklistICCard getById(
            @ApiParam(name = "id", value = "数据库id", defaultValue = "")
            @RequestParam(value = "id", required = true) int id){
        return blacklistICCardClient.getById(id);
    }

    @RequestMapping(value = ServiceUrls.BlacklistICCard.UPDATEBYID, method = RequestMethod.PUT)
    @ApiOperation(value = "更新IC卡黑名单", notes = "更新IC卡黑名单")
    String update(
            @ApiParam(name = "blacklisticcard", value = "IC卡黑名单实体", defaultValue = "")
            @RequestBody(required = true) BlacklistICCard blacklisticcard) {
        return blacklistICCardClient.update(blacklisticcard);
    }

    @RequestMapping(value = ServiceUrls.BlacklistICCard.DELBYID, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据id删除IC卡黑名单", notes = "根据id删除IC卡黑名单")
    String delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = true) int id) {
        return blacklistICCardClient.delete(id);
    }

    @RequestMapping(value = ServiceUrls.BlacklistICCard.BATCHDEL, method = RequestMethod.GET)
    @ApiOperation(value = "根据id删除IC卡黑名单", notes = "根据id删除IC卡黑名单")
    String batchDelete(
            @ApiParam(name = "ids", value = "ids", defaultValue = "")
            @RequestParam(value = "ids", required = true) List<Integer> ids) {
        return blacklistICCardClient.batchDelete(ids);
    }

    @RequestMapping(value = ServiceUrls.BlacklistICCard.ADD, method = RequestMethod.POST)
    @ApiOperation(value = "添加IC卡黑名单", notes = "添加IC卡黑名单")
    String add(@ApiParam(name = "blacklisticcard", value = "IC卡黑名单实体", defaultValue = "")
                     @RequestBody(required = true) BlacklistICCard blacklisticcard) {
        return blacklistICCardClient.add(blacklisticcard);
    }

    @RequestMapping(value = ServiceUrls.BlacklistICCard.BATCHADD, method = RequestMethod.POST)
    @ApiOperation(value = "批量添加IC卡黑名单", notes = "批量添加IC卡黑名单")
    String batchAdd(@ApiParam(name = "blacklisticcard_list", value = "IC卡黑名单实体列表", defaultValue = "")
                    @RequestBody(required = true) List<BlacklistICCard> blacklisticcard_list) {
        return blacklistICCardClient.batchAdd(blacklisticcard_list);
    }

}
