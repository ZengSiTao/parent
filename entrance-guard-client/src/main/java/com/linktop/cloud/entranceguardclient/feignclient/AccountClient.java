package com.linktop.cloud.entranceguardclient.feignclient;

import com.linktop.cloud.commonutils.ServiceNames;
import com.linktop.cloud.commonutils.ServiceUrls;
import com.linktop.cloud.commonutils.VersionNumbers;
import com.linktop.cloud.entranceguardmodel.database.Account;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@FeignClient(name = ServiceNames.ENTRANCE_GUARD_CORE)
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@ApiIgnore
public interface AccountClient {


    @RequestMapping(value = ServiceUrls.Account.LISTALL, method = RequestMethod.GET)
    @ApiOperation(value = "获取账号列表", notes = "根据查询条件获组在前端表格展示")
    List<Account> getAccounts();

    @RequestMapping(value = ServiceUrls.Account.GETBYID, method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取账号", notes = "根据id获取账号")
    Account getAccountById(
            @ApiParam(name = "id", value = "数据库id", defaultValue = "")
            @PathVariable("id") int id);

    @RequestMapping(value = ServiceUrls.Account.UPDATEBYID, method = RequestMethod.PUT)
    @ApiOperation(value = "根据id更新账号", notes = "根据id更新账号")
    String updateAccount(
            @ApiParam(name = "id", value = "数据库id", defaultValue = "")
            @PathVariable("id") int id,
            @ApiParam(name = "name", value = "名称", defaultValue = "")
            @RequestParam(value = "name", required = true) String name,
            @ApiParam(name = "password", value = "余额", defaultValue = "")
            @RequestParam(value = "password", required = true) String password);

    @RequestMapping(value = ServiceUrls.Account.DELBYID, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据id删除账号", notes = "根据id删除账号")
    String delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @PathVariable(value = "id") int id);

    @RequestMapping(value = ServiceUrls.Account.ADD, method = RequestMethod.POST)
    @ApiOperation(value = "添加账号", notes = "添加账号")
    String postAccount(
            @ApiParam(name = "name", value = "name", defaultValue = "")
            @RequestParam(value = "name") String name,
            @ApiParam(name = "password", value = "password", defaultValue = "")
            @RequestParam(value = "password") String password);
}
