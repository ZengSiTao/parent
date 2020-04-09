package com.linktop.cloud.entranceguardcore.service;

import com.linktop.cloud.entranceguardcore.dao.UsersMapper;
import com.linktop.cloud.entranceguardmodel.PageWrapper;
import com.linktop.cloud.entranceguardmodel.database.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.linktop.cloud.commonutils.Common.getRandomString;

@Service
public class UsersService {
    private final int USER_ID_LEN = 20;
    private static Logger log = LoggerFactory.getLogger( UsersService.class );
    @Autowired
    private UsersMapper usersMapper;

    public int add(String aliaId,
                   String buildingNum,
                   String unitNum,
                   String roomNum,
                   Integer type,
                   String name,
                   String identityCardNum,
                   Integer gender,
                   Integer estateId,
                   String deviceIds,
                   String fnOnServer,
                   Integer blackListType) {
        int ret = 0;
        ret = usersMapper.add( aliaId,
                buildingNum,
                unitNum,
                roomNum,
                type,
                name,
                identityCardNum,
                gender,
                estateId,
                deviceIds,
                fnOnServer,
                blackListType );
        return ret;
    }

    public int update(User user) {
        int ret = 0;
        ret = usersMapper.update( user.getId(),
                user.getAliaId(),
                user.getBuildingNum(),
                user.getUnitNum(),
                user.getRoomNum(),
                user.getType(),
                user.getName(),
                user.getIdentityCardNum(),
                user.getGender(),
                user.getEstateId(),
                user.getDeviceIds(),
                user.getFnOnServer(),
                user.getBlackListType() );

        return ret;
    }

    public int delete(int id) {
        return usersMapper.delete( id );
    }

    public int batchDel(List<Integer> ids) {
        return usersMapper.batchDel( ids );
    }

    public List<User> batchGet(List<Integer> ids) {
        return usersMapper.batchGet( ids );
    }

    public User get(int id) {
        return usersMapper.get( id );
    }

    public List<User> getList() {
        return usersMapper.getList();
    }

    public PageWrapper<User> getPage(int page,
                                     int count,
                                     String filter,
                                     boolean black) {
        PageWrapper pw = new PageWrapper();
        pw.setTotal( usersMapper.getCount( filter, black ) );
        List<User> listUser = usersMapper.getPage( (page - 1) * count, count, filter, black );
        pw.setData( listUser );
        return pw;
    }

}
