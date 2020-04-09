package com.linktop.cloud.entranceguardcore.controller;


import com.linktop.cloud.commonutils.ServiceUrls;
import com.linktop.cloud.commonutils.VersionNumbers;
import com.linktop.cloud.entranceguardcore.service.GateEventService;
import com.linktop.cloud.entranceguardcore.service.UsersService;
import com.linktop.cloud.entranceguardmodel.PageWrapper;
import com.linktop.cloud.entranceguardmodel.database.GateEvent;
import com.linktop.cloud.entranceguardmodel.database.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
public class GateEventController {

    @Autowired
    GateEventService gateEventService;


    @RequestMapping(value = ServiceUrls.GateEvent.LISTALL, method = RequestMethod.GET)
    public List<GateEvent> getList() {
        return gateEventService.getList();
    }

    @RequestMapping(value = ServiceUrls.GateEvent.PAGE, method = RequestMethod.GET)
    PageWrapper<GateEvent> getPage(@RequestParam(value = "page", required = true) int page,
                                      @RequestParam(value = "count", required = true) int count,
                                      @RequestParam(value = "filter", required = false) String filter) {
        return gateEventService.getPage(page, count, filter);
    }
    @RequestMapping(value = ServiceUrls.GateEvent.GETBYID, method = RequestMethod.GET)
    public GateEvent getById(@RequestParam(value = "id", required = true) int id) {

        return gateEventService.get(id);
    }

    @RequestMapping(value = ServiceUrls.GateEvent.UPDATEBYID, method = RequestMethod.PUT)
    public String update(@RequestBody(required = true) GateEvent gateEvent) {
        int t = gateEventService.update(gateEvent);
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = ServiceUrls.GateEvent.DELBYID, method = RequestMethod.DELETE)
    public String delete(@RequestParam(value = "id", required = true) int id) {
        int t = gateEventService.delete(id);
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = ServiceUrls.GateEvent.BATCHDEL, method = RequestMethod.GET)
    public String batchDel(@RequestParam(value = "ids", required = true) List<Integer> ids) {
        int t = gateEventService.batchDel(ids);
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = ServiceUrls.GateEvent.ADD, method = RequestMethod.POST)
    public String add(@RequestBody(required = true) GateEvent gateEvent) {
        int t = gateEventService.add(gateEvent.getLockCat(),
                                     gateEvent.getSrcTs(),
                                     gateEvent.getMedia(),
                                     gateEvent.getUserAliaId(),
                                     gateEvent.getDeviceAliaId(),
                                     gateEvent.getReceipt());
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }
}