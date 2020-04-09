package com.linktop.cloud.entranceguardcore.service;

import com.linktop.cloud.entranceguardcore.dao.AccountMapper;
import com.linktop.cloud.entranceguardmodel.database.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @创建人 baojielei
 * @创建时间 2020/3/11
 * @描述
 */
@Service
public class LoginService {

    @Autowired
    AccountMapper accountMapper;

    public Account getAaccount(String name, String password) {
        Account account = accountMapper.findAccount1(name,password);
        return account;
    }
}
