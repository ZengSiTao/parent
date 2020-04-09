package com.linktop.cloud.entranceguardcore.controller;


import com.linktop.cloud.commonutils.ServiceUrls;
import com.linktop.cloud.commonutils.VersionNumbers;
import com.linktop.cloud.entranceguardcore.service.FaceService;
import com.linktop.cloud.entranceguardmodel.database.Face;
import com.linktop.cloud.entranceguardmodel.PageWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
public class FaceController {

    @Autowired
    FaceService faceService;


    @RequestMapping(value = ServiceUrls.Face.LISTALL, method = RequestMethod.GET)
    public List<Face> getList() {
        return faceService.getList();
    }

    @RequestMapping(value = ServiceUrls.Face.PAGE, method = RequestMethod.GET)
    PageWrapper<Face> getPage(@RequestParam(value = "page", required = true) int page,
                                      @RequestParam(value = "count", required = true) int count,
                                      @RequestParam(value = "filter", required = false) String filter) {
        return faceService.getPage(page, count, filter);
    }
    @RequestMapping(value = ServiceUrls.Face.GETBYID, method = RequestMethod.GET)
    public Face getById(@RequestParam(value = "id", required = true) int id) {

        return faceService.get(id);
    }

    @RequestMapping(value = ServiceUrls.Face.UPDATEBYID, method = RequestMethod.PUT)
    public String update(@RequestBody(required = true) Face face) {
        int t = faceService.update(face);
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = ServiceUrls.Face.DELBYID, method = RequestMethod.DELETE)
    public String delete(@RequestParam(value = "id", required = true) int id) {
        int t = faceService.delete(id);
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = ServiceUrls.Face.BATCHDEL, method = RequestMethod.GET)
    public String batchDel(@RequestParam(value = "ids", required = true) List<Integer> ids) {
        int t = faceService.batchDel(ids);
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = ServiceUrls.Face.ADD, method = RequestMethod.POST)
    public String add(@RequestBody(required = true) Face face) {
        int t = faceService.add(face.getUserId(),
                                face.getDeviceIds(),
                                face.getFnOnServer());
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }
}