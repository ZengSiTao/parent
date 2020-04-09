package com.linktop.cloud.entranceguardcore.controller;


import com.linktop.cloud.commonutils.ServiceUrls;
import com.linktop.cloud.commonutils.VersionNumbers;
import com.linktop.cloud.entranceguardcore.service.ICCardService;
import com.linktop.cloud.entranceguardmodel.database.ICCard;
import com.linktop.cloud.entranceguardmodel.PageWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
public class ICCardController {

    @Autowired
    ICCardService icCardService;


    @RequestMapping(value = ServiceUrls.ICCard.LISTALL, method = RequestMethod.GET)
    public List<ICCard> getList() {
        return icCardService.getList();
    }

    @RequestMapping(value = ServiceUrls.ICCard.PAGE, method = RequestMethod.GET)
    PageWrapper<ICCard> getPage(@RequestParam(value = "page", required = true) int page,
                                      @RequestParam(value = "count", required = true) int count,
                                      @RequestParam(value = "filter", required = false) String filter) {
        return icCardService.getPage(page, count, filter);
    }
    @RequestMapping(value = ServiceUrls.ICCard.GETBYID, method = RequestMethod.GET)
    public ICCard getById(@RequestParam(value = "id", required = true) int id) {

        return icCardService.get(id);
    }

    @RequestMapping(value = ServiceUrls.ICCard.UPDATEBYID, method = RequestMethod.PUT)
    public String update(@RequestBody(required = true) ICCard iccard) {
        int t = icCardService.update(iccard);
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = ServiceUrls.ICCard.DELBYID, method = RequestMethod.DELETE)
    public String delete(@RequestParam(value = "id", required = true) int id) {
        int t = icCardService.delete(id);
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = ServiceUrls.ICCard.BATCHDEL, method = RequestMethod.GET)
    public String batchDel(@RequestParam(value = "ids", required = true) List<Integer> ids) {
        int t = icCardService.batchDel(ids);
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = ServiceUrls.ICCard.ADD, method = RequestMethod.POST)
    public String add(@RequestBody(required = true) ICCard iccard) {
        int t = icCardService.add(iccard.getSerial(),
                                  iccard.getUserId(),
                                  iccard.getDeviceIds());
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }
}