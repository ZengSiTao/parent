package com.linktop.cloud.entranceguardclient.feignclient;

import com.linktop.cloud.commonutils.ServiceNames;
import com.linktop.cloud.commonutils.VersionNumbers;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

@FeignClient(name = ServiceNames.ENTRANCE_GUARD_CORE)
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@ApiIgnore
public interface AesClient {


    @RequestMapping(value = "aes/encrypt", method = RequestMethod.GET)
    @ApiOperation(value = "加密", notes = "加密")
    byte[] encrypt(@ApiParam(name = "secret", value = "秘钥", defaultValue = "")
                   @RequestParam("secret") String secret,
                   @ApiParam(name = "content", value = "待加密字符串", defaultValue = "")
                   @RequestParam("content") String content);

    @RequestMapping(value = "aes/decrypt", method = RequestMethod.GET)
    @ApiOperation(value = "解密", notes = "解密")
    byte[] decrypt(@ApiParam(name = "secret", value = "秘钥", defaultValue = "")
                   @RequestParam("secret") String secret,
                   @ApiParam(name = "content", value = "待解密字符串", defaultValue = "")
                   @RequestParam("content") String content);

}
