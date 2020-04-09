package com.linktop.cloud.entranceguardclient.feignclient;

import com.linktop.cloud.commonutils.ServiceNames;
import com.linktop.cloud.commonutils.ServiceUrls;
import com.linktop.cloud.commonutils.VersionNumbers;
import com.linktop.cloud.entranceguardmodel.database.ICCard;
import com.linktop.cloud.entranceguardmodel.PageWrapper;
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
public interface ICCardClient {


    @RequestMapping(value = ServiceUrls.ICCard.LISTALL, method = RequestMethod.GET)
    @ApiOperation(value = "获取IC卡列表", notes = "获取IC卡列表")
    List<ICCard> getICCardList();

    @RequestMapping(value = ServiceUrls.ICCard.PAGE, method = RequestMethod.GET)
    @ApiOperation(value = "获取IC卡列表分页", notes = "获取IC卡列表分页")
    PageWrapper<ICCard> getICCardPage(@ApiParam(name = "page", value = "第几页", defaultValue = "")
                                      @RequestParam("page") int page,
                                      @ApiParam(name = "count", value = "每页数量", defaultValue = "")
                                      @RequestParam("count") int count,
                                      @ApiParam(name = "filter", value = "查询过滤字符串", defaultValue = "")
                                      @RequestParam("filter") String filter);

    @RequestMapping(value = ServiceUrls.ICCard.GETBYID, method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取IC卡", notes = "根据id获取IC卡")
    ICCard getICCardById(@ApiParam(name = "id", value = "数据库id", defaultValue = "")
                         @RequestParam(value = "id", required = true) int id);

    @RequestMapping(value = ServiceUrls.ICCard.UPDATEBYID, method = RequestMethod.PUT)
    @ApiOperation(value = "更新设备", notes = "更新设备")
    String updateICCard(@ApiParam(name = "iccard", value = "IC卡实体", defaultValue = "")
                        @RequestBody(required = true) ICCard iccard);

    @RequestMapping(value = ServiceUrls.ICCard.DELBYID, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据id删除IC卡", notes = "根据id删除IC卡")
    String delete(@ApiParam(name = "id", value = "id", defaultValue = "")
                  @RequestParam(value = "id", required = true) int id);

    @RequestMapping(value = ServiceUrls.ICCard.BATCHDEL, method = RequestMethod.GET)
    @ApiOperation(value = "根据id删除IC卡", notes = "根据id删除IC卡")
    String batchDelete(@ApiParam(name = "ids", value = "ids", defaultValue = "")
                       @RequestParam(value = "ids", required = true) List<Integer> ids);

    @RequestMapping(value = ServiceUrls.ICCard.ADD, method = RequestMethod.POST)
    @ApiOperation(value = "添加IC卡", notes = "添加IC卡")
    String addICCard(@ApiParam(name = "iccard", value = "设备实体", defaultValue = "")
                     @RequestBody(required = true) ICCard iccard);
}
