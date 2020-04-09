package com.linktop.cloud.entranceguardclient.feignclient;

import com.linktop.cloud.commonutils.ServiceNames;
import com.linktop.cloud.commonutils.ServiceUrls;
import com.linktop.cloud.commonutils.VersionNumbers;

import com.linktop.cloud.entranceguardmodel.ResultFront;
import com.linktop.cloud.entranceguardmodel.database.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;


@FeignClient(name = ServiceNames.ENTRANCE_GUARD_CORE)
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@ApiIgnore
public interface LoginClient {

    /*@RequestMapping(value = ServiceUrls.Login.LOGIN, method = RequestMethod.POST)
    public Account login(@RequestBody Account account);*/
    @RequestMapping(value = ServiceUrls.Login.LOGIN, method = RequestMethod.POST)
    public ResultFront login(@RequestBody Account account);
}