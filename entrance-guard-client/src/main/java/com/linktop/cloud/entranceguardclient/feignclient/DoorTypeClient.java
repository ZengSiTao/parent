package com.linktop.cloud.entranceguardclient.feignclient;

import com.linktop.cloud.commonutils.ServiceNames;
import com.linktop.cloud.commonutils.ServiceUrls;
import com.linktop.cloud.commonutils.VersionNumbers;
import com.linktop.cloud.entranceguardmodel.database.DoorType;
import com.linktop.cloud.entranceguardmodel.database.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.annotations.ApiIgnore;

@FeignClient(name = ServiceNames.ENTRANCE_GUARD_CORE)
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@ApiIgnore
public interface DoorTypeClient {

    @RequestMapping(value = ServiceUrls.DoorType.ADDDOORTYPE, method = RequestMethod.POST)
    @ApiOperation(value = "新增门禁类型", notes = "新增门禁类型")
    String addDoorType(@ApiParam(name = "doorType", value = "门禁类型实体", defaultValue = "")
               @RequestBody(required = true) DoorType doorType);
}
