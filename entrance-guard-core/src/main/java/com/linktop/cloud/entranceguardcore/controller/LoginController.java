package com.linktop.cloud.entranceguardcore.controller;


import com.linktop.cloud.commonutils.ServiceUrls;
import com.linktop.cloud.commonutils.VersionNumbers;
import com.linktop.cloud.entranceguardcore.service.LoginService;

import com.linktop.cloud.entranceguardmodel.ResultFront;
import com.linktop.cloud.entranceguardmodel.database.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/*@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
public class LoginController {

    @RequestMapping(value = ServiceUrls.Login.LOGIN, method = RequestMethod.POST)
    public Account login(@RequestBody Account account) {

        return account;
    }

}*/
@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
public class LoginController {
    @Autowired
    LoginService loginService;

    @RequestMapping(value = ServiceUrls.Login.LOGIN, method = RequestMethod.POST)
    public ResultFront login(@RequestBody Account account) {
        ResultFront result = new ResultFront();
        Account acc = new Account();
        acc = loginService.getAaccount(account.getName(),account.getPasswd());
//        System.out.println(acc.toString());
        if(null == acc){
            result.setCode( 404 );
            result.setMsg( "账号或密码输入错误" );
        }else {
            result.setCode( 200 );
            result.setMsg( "登录成功" );
        }
        System.out.println(result.toString());
        System.out.println("==============");
        return result;
    }

}