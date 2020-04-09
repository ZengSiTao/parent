package com.linktop.cloud.entranceguardadmin.login;

import com.linktop.cloud.commonutils.ServiceUrls;
import com.linktop.cloud.commonutils.VersionNumbers;
import com.linktop.cloud.entranceguardclient.feignclient.LoginClient;

import com.linktop.cloud.entranceguardmodel.ResultFront;
import com.linktop.cloud.entranceguardmodel.database.Account;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "login", description = "登录接口")
public class LoginController {
    @Autowired
    LoginClient loginClient;

    @RequestMapping(value = ServiceUrls.Login.LOGIN, method = RequestMethod.POST)
    public ResultFront login(@RequestBody Account account) {
        ResultFront result = new ResultFront();
        result =  loginClient.login(account);
        return result;
    }
}
