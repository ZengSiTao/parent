package com.linktop.cloud.entranceguardcore.controller;


import com.linktop.cloud.commonutils.ServiceUrls;
import com.linktop.cloud.commonutils.VersionNumbers;
import com.linktop.cloud.entranceguardmodel.database.Account;
import com.linktop.cloud.entranceguardcore.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
public class AccountController {

    @Autowired
    AccountService accountService;


    @RequestMapping(value = ServiceUrls.Account.LISTALL, method = RequestMethod.GET)
    public List<Account> getAccounts() {
        return accountService.findAccountList();
    }

    @RequestMapping(value = ServiceUrls.Account.GETBYID, method = RequestMethod.GET)
    public Account getAccountById(@PathVariable("id") int id) {
        return accountService.findAccount(id);
    }

    @RequestMapping(value = ServiceUrls.Account.UPDATEBYID, method = RequestMethod.PUT)
    public String updateAccount(@PathVariable("id") int id,
                                @RequestParam(value = "name", required = true) String name,
                                @RequestParam(value = "password", required = true) String password) {
        int t = accountService.update(name, password, id);
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = ServiceUrls.Account.DELBYID, method = RequestMethod.DELETE)
    public String delete(@PathVariable(value = "id") int id) {
        int t = accountService.delete(id);
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = ServiceUrls.Account.ADD, method = RequestMethod.POST)
    public String postAccount(@RequestParam(value = "name") String name,
                              @RequestParam(value = "password") String password) {
            int t = accountService.add(name, password);
            if (t == 1) {
                return "success";
            } else {
                return "fail";
            }
    }


}