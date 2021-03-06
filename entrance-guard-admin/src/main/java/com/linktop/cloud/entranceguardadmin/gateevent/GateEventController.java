package com.linktop.cloud.entranceguardadmin.gateevent;

import com.linktop.cloud.commonutils.ServiceUrls;
import com.linktop.cloud.commonutils.VersionNumbers;
import com.linktop.cloud.entranceguardclient.feignclient.GateEventClient;
import com.linktop.cloud.entranceguardmodel.PageWrapper;
import com.linktop.cloud.entranceguardmodel.database.GateEvent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "GateEvent", description = "开关门事件接口")
public class GateEventController {

    @Autowired
    private GateEventClient gateEventClient;

    @RequestMapping(value = ServiceUrls.GateEvent.LISTALL, method = RequestMethod.GET)
    @ApiOperation(value = "获取开关门事件列表", notes = "获取开关门事件列表")
    List<GateEvent> getList() {
        return gateEventClient.getList();
    }

    @RequestMapping(value = ServiceUrls.GateEvent.PAGE, method = RequestMethod.GET)
    @ApiOperation(value = "获取开关门事件列表分页", notes = "获取开关门事件列表分页")
    PageWrapper<GateEvent> getPage(@ApiParam(name = "page", value = "第几页", defaultValue = "")
                              @RequestParam("page") int page,
                              @ApiParam(name = "count", value = "每页数量", defaultValue = "")
                              @RequestParam("count") int count,
                              @ApiParam(name = "filter", value = "查询过滤字符串", defaultValue = "")
                              @RequestParam("filter") String filter) {
        return gateEventClient.getPage(page, count, filter);
    }

    @RequestMapping(value = ServiceUrls.GateEvent.GETBYID, method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取开关门事件", notes = "根据id获取开关门事件")
    GateEvent getById(@ApiParam(name = "id", value = "数据库id", defaultValue = "")
                   @RequestParam(value = "id", required = true) int id) {
        return gateEventClient.getById(id);
    }

    @RequestMapping(value = ServiceUrls.GateEvent.UPDATEBYID, method = RequestMethod.PUT)
    @ApiOperation(value = "更新开关门事件", notes = "更新开关门事件")
    String update(@ApiParam(name = "gateEvent", value = "开关门事件实体", defaultValue = "")
                  @RequestBody(required = true) GateEvent gateEvent) {
        return gateEventClient.update(gateEvent);
    }

    @RequestMapping(value = ServiceUrls.GateEvent.DELBYID, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据id删除开关门事件", notes = "根据id删除开关门事件")
    String delete(@ApiParam(name = "id", value = "id", defaultValue = "")
                  @RequestParam(value = "id", required = true) int id) {
        return gateEventClient.delete(id);
    }

    @RequestMapping(value = ServiceUrls.GateEvent.BATCHDEL, method = RequestMethod.GET)
    @ApiOperation(value = "根据id删除开关门事件", notes = "根据id删除开关门事件")
    String batchDelete(@ApiParam(name = "ids", value = "ids", defaultValue = "")
                       @RequestParam(value = "ids", required = true) List<Integer> ids) {
        return gateEventClient.batchDelete(ids);
    }

    @RequestMapping(value = ServiceUrls.GateEvent.ADD, method = RequestMethod.POST)
    @ApiOperation(value = "添加开关门事件", notes = "添加开关门事件")
    String add(@ApiParam(name = "gateEvent", value = "开关门事件实体", defaultValue = "")
               @RequestBody(required = true) GateEvent gateEvent) {
        return gateEventClient.add(gateEvent);
    }
}
