package com.linktop.cloud.entranceguardcore.controller;


import com.linktop.cloud.commonutils.ServiceUrls;
import com.linktop.cloud.commonutils.VersionNumbers;
import com.linktop.cloud.entranceguardcore.service.BlacklistICCardService;
import com.linktop.cloud.entranceguardcore.service.ICCardService;
import com.linktop.cloud.entranceguardmodel.PageWrapper;
import com.linktop.cloud.entranceguardmodel.database.BlacklistICCard;
import com.linktop.cloud.entranceguardmodel.database.ICCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
public class BlacklistICCardController {

    @Autowired
    BlacklistICCardService blacklistICCardService;


    @RequestMapping(value = ServiceUrls.BlacklistICCard.LISTALL, method = RequestMethod.GET)
    public List<BlacklistICCard> getList(@RequestParam(value = "deviceId", required = true) int deviceId) {
        return blacklistICCardService.getList(deviceId);
    }

    @RequestMapping(value = ServiceUrls.BlacklistICCard.PAGE, method = RequestMethod.GET)
    PageWrapper<BlacklistICCard> getPage(@RequestParam(value = "page", required = true) int page,
                                         @RequestParam(value = "count", required = true) int count,
                                         @RequestParam(value = "filter", required = false) String filter,
                                         @RequestParam(value = "deviceId", required = true) int deviceId) {
        return blacklistICCardService.getPage(page, count, filter, deviceId);
    }
    @RequestMapping(value = ServiceUrls.BlacklistICCard.GETBYID, method = RequestMethod.GET)
    public BlacklistICCard getById(@RequestParam(value = "id", required = true) int id) {

        return blacklistICCardService.get(id);
    }

    @RequestMapping(value = ServiceUrls.BlacklistICCard.UPDATEBYID, method = RequestMethod.PUT)
    public String update(@RequestBody(required = true) BlacklistICCard blacklisticcard) {
        int t = blacklistICCardService.update(blacklisticcard);
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = ServiceUrls.BlacklistICCard.DELBYID, method = RequestMethod.DELETE)
    public String delete(@RequestParam(value = "id", required = true) int id) {
        int t = blacklistICCardService.delete(id);
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = ServiceUrls.BlacklistICCard.BATCHDEL, method = RequestMethod.GET)
    public String batchDel(@RequestParam(value = "ids", required = true) List<Integer> ids) {
        int t = blacklistICCardService.batchDel(ids);
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = ServiceUrls.BlacklistICCard.ADD, method = RequestMethod.POST)
    public String add(@RequestBody(required = true) BlacklistICCard blacklisticcard) {
        int t = blacklistICCardService.add(blacklisticcard.getEstateId(),
                                            blacklisticcard.getDeviceId(),
                                            blacklisticcard.getIccardId());
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = ServiceUrls.BlacklistICCard.BATCHADD, method = RequestMethod.POST)
    public String batchAdd(@RequestBody(required = true) List<BlacklistICCard> blacklisticcard_list) {
        int t = 0;
        for (BlacklistICCard c:blacklisticcard_list
                ) {
            t = blacklistICCardService.add(c.getEstateId(),
                    c.getDeviceId(),
                    c.getIccardId());
            if (t != 1) {
                break;
            }
        }
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }
}