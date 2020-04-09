package com.linktop.cloud.entranceguardcore.util;

import com.linktop.cloud.entranceguardcore.dao.DoorTypeMapper;
import com.linktop.cloud.entranceguardcore.dao.HardUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @创建人 baojielei
 * @创建时间 2020/3/31
 * @描述
 */
@Component
public class MapperUtil {

    @Autowired
    HardUserMapper hardUserMapper;
    @Autowired
    DoorTypeMapper doorTypeMapper;

    @PostConstruct
    public void init(){
        MapperUtil.getInstance().hardUserMapper = this.hardUserMapper;

    }

    //实现单利 start
    public static  class SingletonHolder{
        private static final MapperUtil MapperUtil = new MapperUtil();
    }

    private MapperUtil(){}

    public static final MapperUtil getInstance(){
        return SingletonHolder.MapperUtil;
    }

    //实现单利 end
    public HardUserMapper getHardUserMapper(){
        return MapperUtil.getInstance().hardUserMapper;
    }

    public DoorTypeMapper getDoorTypeMapper(){
        return MapperUtil.getInstance().doorTypeMapper;
    }
}
