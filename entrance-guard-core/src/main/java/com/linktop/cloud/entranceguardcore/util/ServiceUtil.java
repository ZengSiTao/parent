package com.linktop.cloud.entranceguardcore.util;


import com.linktop.cloud.entranceguardcore.service.HardUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @创建人 baojielei
 * @创建时间 2020/3/26
 * @描述
 */

@Component
public class ServiceUtil {
    @Autowired
    HardUserService hardUserService;

    @PostConstruct
    public void init(){
        ServiceUtil.getInstance().hardUserService = this.hardUserService;

    }

    //实现单利 start
    public static  class SingletonHolder{
        private static final ServiceUtil serviceUtil = new ServiceUtil();
    }

    private ServiceUtil(){}
    public static final ServiceUtil getInstance(){
        return SingletonHolder.serviceUtil;
    }

    //实现单利 end
    public HardUserService getHardwareUserService(){
        return ServiceUtil.getInstance().hardUserService;
    }


















}
