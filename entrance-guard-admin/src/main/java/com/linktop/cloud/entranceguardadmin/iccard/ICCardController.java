package com.linktop.cloud.entranceguardadmin.iccard;

import com.linktop.cloud.commonutils.ServiceUrls;
import com.linktop.cloud.commonutils.VersionNumbers;
import com.linktop.cloud.entranceguardclient.feignclient.ICCardClient;
import com.linktop.cloud.entranceguardmodel.database.ICCard;
import com.linktop.cloud.entranceguardmodel.PageWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "IC Card", description = "IC卡接口")
public class ICCardController {

    @Autowired
    private ICCardClient iccardClient;

    @RequestMapping(value = ServiceUrls.ICCard.LISTALL, method = RequestMethod.GET)
    @ApiOperation(value = "获取IC卡列表", notes = "获取IC卡列表")
    List<ICCard> getICCardList() {
        return iccardClient.getICCardList();
    };

    @RequestMapping(value = ServiceUrls.ICCard.PAGE, method = RequestMethod.GET)
    @ApiOperation(value = "获取IC卡列表分页", notes = "获取IC卡列表分页")
    PageWrapper<ICCard> getICCardPage(@ApiParam(name = "page", value = "第几页", defaultValue = "")
                               @RequestParam(value = "page", required = true) int page,
                                      @ApiParam(name = "count", value = "每页数量", defaultValue = "")
                               @RequestParam(value = "count", required = true) int count,
                                      @ApiParam(name = "filter", value = "查询过滤字符串", defaultValue = "")
                               @RequestParam(value = "filter", required = false) String filter) {
        return iccardClient.getICCardPage(page, count, filter);
    }

    @RequestMapping(value = ServiceUrls.ICCard.GETBYID, method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取IC卡", notes = "根据id获取IC卡")
    ICCard getICCardById(
            @ApiParam(name = "id", value = "数据库id", defaultValue = "")
            @RequestParam(value = "id", required = true) int id){
        return iccardClient.getICCardById(id);
    }

    @RequestMapping(value = ServiceUrls.ICCard.UPDATEBYID, method = RequestMethod.PUT)
    @ApiOperation(value = "更新IC卡", notes = "更新IC卡")
    String updateICCard(
            @ApiParam(name = "iccard", value = "IC卡实体", defaultValue = "")
            @RequestBody(required = true) ICCard iccard) {
        return iccardClient.updateICCard(iccard);
    }

    @RequestMapping(value = ServiceUrls.ICCard.DELBYID, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据id删除IC卡", notes = "根据id删除IC卡")
    String delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = true) int id) {
        return iccardClient.delete(id);
    }

    @RequestMapping(value = ServiceUrls.ICCard.BATCHDEL, method = RequestMethod.GET)
    @ApiOperation(value = "根据id删除IC卡", notes = "根据id删除IC卡")
    String batchDelete(
            @ApiParam(name = "ids", value = "ids", defaultValue = "")
            @RequestParam(value = "ids", required = true) List<Integer> ids) {
        return iccardClient.batchDelete(ids);
    }

    @RequestMapping(value = ServiceUrls.ICCard.ADD, method = RequestMethod.POST)
    @ApiOperation(value = "添加IC卡", notes = "添加IC卡")
    String addICCard(@ApiParam(name = "iccard", value = "IC卡实体", defaultValue = "")
                     @RequestBody(required = true) ICCard iccard) {
        return iccardClient.addICCard(iccard);
    }
}
