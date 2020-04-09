package com.linktop.cloud.entranceguardcore.controller;


import com.linktop.cloud.commonutils.ServiceUrls;
import com.linktop.cloud.commonutils.VersionNumbers;
import com.linktop.cloud.entranceguardcore.service.BlacklistFaceService;
import com.linktop.cloud.entranceguardcore.service.BlacklistICCardService;
import com.linktop.cloud.entranceguardmodel.PageWrapper;
import com.linktop.cloud.entranceguardmodel.database.BlacklistFace;
import com.linktop.cloud.entranceguardmodel.database.BlacklistICCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
public class BlacklistFaceController {

    @Autowired
    BlacklistFaceService blacklistFaceService;


    @RequestMapping(value = ServiceUrls.BlacklistFace.LISTALL, method = RequestMethod.GET)
    public List<BlacklistFace> getList(@RequestParam("deviceId") int deviceId) {
        return blacklistFaceService.getList(deviceId);
    }

    @RequestMapping(value = ServiceUrls.BlacklistFace.PAGE, method = RequestMethod.GET)
    PageWrapper<BlacklistFace> getPage(@RequestParam(value = "page", required = true) int page,
                                       @RequestParam(value = "count", required = true) int count,
                                       @RequestParam(value = "filter", required = false) String filter,
                                       @RequestParam(value = "deviceId") int deviceId) {
        return blacklistFaceService.getPage(page, count, filter, deviceId);
    }
    @RequestMapping(value = ServiceUrls.BlacklistFace.GETBYID, method = RequestMethod.GET)
    public BlacklistFace getById(@RequestParam(value = "id", required = true) int id) {

        return blacklistFaceService.get(id);
    }

    @RequestMapping(value = ServiceUrls.BlacklistFace.UPDATEBYID, method = RequestMethod.PUT)
    public String update(@RequestBody(required = true) BlacklistFace blacklistface) {
        int t = blacklistFaceService.update(blacklistface);
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = ServiceUrls.BlacklistFace.DELBYID, method = RequestMethod.DELETE)
    public String delete(@RequestParam(value = "id", required = true) int id) {
        int t = blacklistFaceService.delete(id);
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = ServiceUrls.BlacklistFace.BATCHDEL, method = RequestMethod.GET)
    public String batchDelete(@RequestParam(value = "ids", required = true) List<Integer> ids) {
        int t = blacklistFaceService.batchDel(ids);
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = ServiceUrls.BlacklistFace.ADD, method = RequestMethod.POST)
    public String add(@RequestBody(required = true) BlacklistFace blacklistface) {
        int t = blacklistFaceService.add(blacklistface.getEstateId(),
                                        blacklistface.getDeviceId(),
                                        blacklistface.getFaceId());
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }


    @RequestMapping(value = ServiceUrls.BlacklistFace.BATCHADD, method = RequestMethod.POST)
    public String batchAdd(@RequestBody(required = true) List<BlacklistFace> blacklistface_list) {
        int t = 0;
        for (BlacklistFace c:blacklistface_list
                ) {
            t = blacklistFaceService.add(c.getEstateId(),
                    c.getDeviceId(),
                    c.getFaceId());
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