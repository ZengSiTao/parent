package com.linktop.cloud.entranceguardadmin.doortype;

import com.linktop.cloud.commonutils.ServiceUrls;
import com.linktop.cloud.commonutils.VersionNumbers;
import com.linktop.cloud.entranceguardclient.feignclient.DoorTypeClient;
import com.linktop.cloud.entranceguardmodel.database.DoorType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "DoorType", description = "门禁类型接口")
public class DoorTypeController {

    @Autowired
    DoorTypeClient doorTypeClient;

    @RequestMapping(value = ServiceUrls.DoorType.ADDDOORTYPE, method = RequestMethod.POST)
    @ApiOperation(value = "新增门禁类型", notes = "新增门禁类型")
    String addDoorType(@ApiParam(name = "user", value = "用户实体", defaultValue = "")
               @RequestBody(required = true) DoorType doorType) {
        return doorTypeClient.addDoorType(doorType);
    }
}
