package com.linktop.cloud.entranceguardcore.service;

import com.linktop.cloud.entranceguardcore.dao.BlacklistICCardMapper;
import com.linktop.cloud.entranceguardcore.dao.ICCardMapper;
import com.linktop.cloud.entranceguardmodel.PageWrapper;
import com.linktop.cloud.entranceguardmodel.database.BlacklistICCard;
import com.linktop.cloud.entranceguardmodel.database.ICCard;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlacklistICCardService {
    @Autowired
    private BlacklistICCardMapper blacklistICCardMapper;

    public int add(int estateId,
                   int deviceId,
                   int iccardId) {
        return blacklistICCardMapper.add(estateId,
                                        deviceId,
                                        iccardId);
    }

    public int update(BlacklistICCard blacklisticcard) {
        return blacklistICCardMapper.update(blacklisticcard.getId(),
                                    blacklisticcard.getEstateId(),
                                    blacklisticcard.getDeviceId(),
                                    blacklisticcard.getIccardId());
    }

    public int delete(int id) {
        return blacklistICCardMapper.delete(id);
    }

    public int batchDel(List<Integer> ids) {
        return blacklistICCardMapper.batchDel(ids);
    }

    public BlacklistICCard get(int id) {
        return blacklistICCardMapper.get(id);
    }

    public List<BlacklistICCard> getList(int deviceId) {
        return blacklistICCardMapper.getList(deviceId);
    }

    public PageWrapper<BlacklistICCard> getPage(int page,
                                                int count,
                                                String filter,
                                                int deviceId) {
        PageWrapper pw = new PageWrapper();
        pw.setTotal(blacklistICCardMapper.getCount(filter, deviceId));
        pw.setData(blacklistICCardMapper.getPage((page - 1) * count, count, filter, deviceId));
        return pw;
    }
}
