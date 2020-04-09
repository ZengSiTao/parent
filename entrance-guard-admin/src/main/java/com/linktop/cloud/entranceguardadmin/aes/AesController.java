package com.linktop.cloud.entranceguardadmin.aes;

import com.linktop.cloud.commonutils.ServiceUrls;
import com.linktop.cloud.commonutils.VersionNumbers;
import com.linktop.cloud.entranceguardclient.feignclient.AesClient;
import com.linktop.cloud.entranceguardclient.feignclient.UsersClient;
import com.linktop.cloud.entranceguardmodel.PageWrapper;
import com.linktop.cloud.entranceguardmodel.database.ICCard;
import com.linktop.cloud.entranceguardmodel.database.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "User", description = "用户接口")
public class AesController {

    @Autowired
    private AesClient aesClient;


    @RequestMapping(value = "aes/encrypt", method = RequestMethod.GET)
    @ApiOperation(value = "加密", notes = "加密")
    byte[] encrypt(@ApiParam(name = "secret", value = "秘钥", defaultValue = "")
                   @RequestParam("secret") String secret,
                   @ApiParam(name = "content", value = "待加密字符串", defaultValue = "")
                   @RequestParam("content") String content) {
        return aesClient.encrypt(secret, content);
    }

    @RequestMapping(value = "aes/decrypt", method = RequestMethod.GET)
    @ApiOperation(value = "解密", notes = "解密")
    byte[] decrypt(@ApiParam(name = "secret", value = "秘钥", defaultValue = "")
                   @RequestParam("secret") String secret,
                   @ApiParam(name = "content", value = "待解密字符串", defaultValue = "")
                   @RequestParam("content") String content) {
        return aesClient.decrypt(secret, content);
    }

}
