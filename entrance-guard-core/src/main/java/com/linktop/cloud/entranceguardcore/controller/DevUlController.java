package com.linktop.cloud.entranceguardcore.controller;


import com.linktop.cloud.commonutils.ServiceUrls;
import com.linktop.cloud.commonutils.VersionNumbers;
import com.linktop.cloud.entranceguardcore.service.DevUlService;
import com.linktop.cloud.entranceguardcore.service.DeviceConfigService;
import com.linktop.cloud.entranceguardmodel.database.DevUl;
import com.linktop.cloud.entranceguardmodel.database.DeviceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
public class DevUlController {

    @Autowired
    DevUlService devUlService;

    @RequestMapping(value = ServiceUrls.DevUl.ADD, method = RequestMethod.POST)
    public String add(@RequestBody(required = true) DevUl devUl) {
        int t = devUlService.add(devUl);
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }

}