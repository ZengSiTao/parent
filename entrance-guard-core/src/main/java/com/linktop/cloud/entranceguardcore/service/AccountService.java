package com.linktop.cloud.entranceguardcore.service;

import com.linktop.cloud.entranceguardcore.dao.AccountMapper;
import com.linktop.cloud.entranceguardmodel.database.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountMapper accountMapper;

    public int add(String name, String password) {
        return accountMapper.add(name, password);
    }

    public int update(String name, String password, int id) {
        return accountMapper.update(name, password, id);
    }

    public int delete(int id) {
        return accountMapper.delete(id);
    }

    public Account findAccount(int id) {
        return accountMapper.findAccount(id);
    }

    public List<Account> findAccountList() {
        return accountMapper.findAccountList();
    }


    /*public Account getAaccount(String name, String password) {
        Account account = accountMapper.findAccount1(name,password);
        return account;
    }*/
}
