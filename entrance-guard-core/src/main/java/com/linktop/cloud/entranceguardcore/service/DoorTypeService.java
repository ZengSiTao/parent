package com.linktop.cloud.entranceguardcore.service;

import com.linktop.cloud.entranceguardcore.dao.DoorTypeMapper;
import com.linktop.cloud.entranceguardmodel.database.DoorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DoorTypeService {
    @Autowired
    DoorTypeMapper doorTypeMapper;

    public DoorType addDoorType(DoorType doorType) {
        DoorType doorType1 = doorTypeMapper.getDoorType(doorType);
        if (null != doorType1) {
            log.info("门禁类型已存在");
            return null;
        } else {
            int i = doorTypeMapper.addDoorType(doorType);
            if (i > 0) {
                log.info("门禁类型新增成功");
                doorType = doorTypeMapper.getDoorType(doorType);
            }

        }
        return doorType;
    }
}
