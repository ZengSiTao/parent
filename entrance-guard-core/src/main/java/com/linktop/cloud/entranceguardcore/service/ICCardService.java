package com.linktop.cloud.entranceguardcore.service;

import com.linktop.cloud.entranceguardcore.dao.ICCardMapper;
import com.linktop.cloud.entranceguardmodel.PageWrapper;
import com.linktop.cloud.entranceguardmodel.database.ICCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ICCardService {
    @Autowired
    private ICCardMapper iccardMapper;

    public int add(String serial,
                   int userId,
                   String deviceIds) {
        return iccardMapper.add(serial,
                                userId,
                                deviceIds);
    }

    public int update(ICCard iccard) {

        return iccardMapper.update(iccard.getId(),
                                    iccard.getSerial(),
                                    iccard.getUserId(),
                                    iccard.getDeviceIds());
    }

    public int delete(int id) {
        return iccardMapper.delete(id);
    }

    public int batchDel(List<Integer> ids) {
        return iccardMapper.batchDel(ids);
    }

    public ICCard get(int id) {
        return iccardMapper.get(id);
    }

    public List<ICCard> getList() {
        return iccardMapper.getList();
    }

    public PageWrapper<ICCard> getPage(int page,
                                             int count,
                                             String filter) {
        PageWrapper pw = new PageWrapper();
        pw.setTotal(iccardMapper.getCount());
        pw.setData(iccardMapper.getPage((page - 1) * count, count, filter));
        return pw;
    }
}
