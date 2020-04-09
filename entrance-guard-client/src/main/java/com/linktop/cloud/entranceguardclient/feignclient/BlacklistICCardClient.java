package com.linktop.cloud.entranceguardclient.feignclient;

import com.linktop.cloud.commonutils.ServiceNames;
import com.linktop.cloud.commonutils.ServiceUrls;
import com.linktop.cloud.commonutils.VersionNumbers;
import com.linktop.cloud.entranceguardmodel.PageWrapper;
import com.linktop.cloud.entranceguardmodel.database.BlacklistFace;
import com.linktop.cloud.entranceguardmodel.database.BlacklistICCard;
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
public interface BlacklistICCardClient {


    @RequestMapping(value = ServiceUrls.BlacklistICCard.LISTALL, method = RequestMethod.GET)
    @ApiOperation(value = "获取IC卡黑名单列表", notes = "获取IC卡黑名单列表")
    List<BlacklistICCard> getList(@ApiParam(name = "deviceId", value = "设备id", defaultValue = "")
                                  @RequestParam("deviceId") int deviceId);

    @RequestMapping(value = ServiceUrls.BlacklistICCard.PAGE, method = RequestMethod.GET)
    @ApiOperation(value = "获取IC卡黑名单列表分页", notes = "获取IC卡黑名单列表分页")
    PageWrapper<BlacklistICCard> getPage(@ApiParam(name = "page", value = "第几页", defaultValue = "")
                                       @RequestParam("page") int page,
                                       @ApiParam(name = "count", value = "每页数量", defaultValue = "")
                                       @RequestParam("count") int count,
                                       @ApiParam(name = "filter", value = "查询过滤字符串", defaultValue = "")
                                       @RequestParam("filter") String filter,
                                       @ApiParam(name = "deviceId", value = "设备id", defaultValue = "")
                                       @RequestParam("deviceId") int deviceId);

    @RequestMapping(value = ServiceUrls.BlacklistICCard.GETBYID, method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取IC卡黑名单", notes = "根据id获取IC卡黑名单")
    BlacklistICCard getById(@ApiParam(name = "id", value = "数据库id", defaultValue = "")
                          @RequestParam(value = "id", required = true) int id);

    @RequestMapping(value = ServiceUrls.BlacklistICCard.UPDATEBYID, method = RequestMethod.PUT)
    @ApiOperation(value = "更新IC卡黑名单", notes = "更新IC卡黑名单")
    String update(@ApiParam(name = "blacklisticcard", value = "IC卡黑名单实体", defaultValue = "")
                  @RequestBody(required = true) BlacklistICCard blacklisticcard);

    @RequestMapping(value = ServiceUrls.BlacklistICCard.DELBYID, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据id删除IC卡黑名单", notes = "根据id删除IC卡黑名单")
    String delete(@ApiParam(name = "id", value = "id", defaultValue = "")
                  @RequestParam(value = "id", required = true) int id);

    @RequestMapping(value = ServiceUrls.BlacklistICCard.BATCHDEL, method = RequestMethod.GET)
    @ApiOperation(value = "根据id删除IC卡黑名单", notes = "根据id删除IC卡黑名单")
    String batchDelete(@ApiParam(name = "ids", value = "ids", defaultValue = "")
                       @RequestParam(value = "ids", required = true) List<Integer> ids);

    @RequestMapping(value = ServiceUrls.BlacklistICCard.ADD, method = RequestMethod.POST)
    @ApiOperation(value = "添加IC卡黑名单", notes = "添加IC卡黑名单")
    String add(@ApiParam(name = "blacklisticcard", value = "IC卡黑名单实体", defaultValue = "")
               @RequestBody(required = true) BlacklistICCard blacklisticcard);

    @RequestMapping(value = ServiceUrls.BlacklistICCard.BATCHADD, method = RequestMethod.POST)
    @ApiOperation(value = "批量添加IC卡黑名单", notes = "批量添加IC卡黑名单")
    String batchAdd(@ApiParam(name = "blacklisticcard_list", value = "IC卡黑名单实体列表", defaultValue = "")
                    @RequestBody(required = true) List<BlacklistICCard> blacklisticcard_list);

}
