package com.linktop.cloud.entranceguardcore.service;


import com.linktop.cloud.entranceguardcore.dao.HardUserMapper;
import com.linktop.cloud.entranceguardmodel.database.HardUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @创建人 baojielei
 * @创建时间 2020/3/25
 * @描述
 */
@Service
@Slf4j
public class HardUserService {

    @Autowired
    HardUserMapper hardUserMapper;

    public HardUser add(HardUser hardUser) {
        HardUser hardUser2 = hardUserMapper.getHardUser(hardUser);
        if (null != hardUser2) {
            log.info("用户已存在");
            return null;
        } else {
            int i = hardUserMapper.add(hardUser);
            if (i > 0) {
                log.info("用户新增成功");
                hardUser = hardUserMapper.getHardUser(hardUser);
            }

        }
        return hardUser;
    }


    public HardUser getUser(HardUser hardUser) {
        hardUserMapper.getHardUser(hardUser);
        return hardUser;
    }


    public HardUser getHardUser(String userCardNumber, String cardType) {

        return hardUserMapper.getUser(userCardNumber, cardType);
    }

    public int del(String userCardNumber, String cardType) {
        return hardUserMapper.del(userCardNumber, cardType);
    }
}
