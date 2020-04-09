package com.linktop.cloud.entranceguardadmin.account;

import com.linktop.cloud.commonutils.ServiceUrls;
import com.linktop.cloud.commonutils.VersionNumbers;
import com.linktop.cloud.entranceguardclient.feignclient.AccountClient;
import com.linktop.cloud.entranceguardmodel.database.Account;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "account", description = "账号接口")
public class AccountController {

    @Autowired
    private AccountClient accountClient;


    @RequestMapping(value = ServiceUrls.Account.LISTALL, method = RequestMethod.GET)
    @ApiOperation(value = "获取账号列表", notes = "根据查询条件获组在前端表格展示")
    public List<Account> getAccounts() {
        return accountClient.getAccounts();
    }

    @RequestMapping(value = ServiceUrls.Account.GETBYID, method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取账号", notes = "根据id获取账号")
    public Account getAccountById(
            @ApiParam(name = "id", value = "数据库id", defaultValue = "")
            @PathVariable("id") int id) {
        return accountClient.getAccountById(id);
    }

    @RequestMapping(value = ServiceUrls.Account.UPDATEBYID, method = RequestMethod.PUT)
    @ApiOperation(value = "根据id更新账号", notes = "根据id更新账号")
    public String updateAccount(
            @ApiParam(name = "id", value = "数据库id", defaultValue = "")
            @PathVariable("id") int id,
            @ApiParam(name = "name", value = "名称", defaultValue = "")
            @RequestParam(value = "name", required = true) String name,
            @ApiParam(name = "password", value = "密码", defaultValue = "")
            @RequestParam(value = "password", required = true) String password) {
        return accountClient.updateAccount(id, name, password);
    }

    @RequestMapping(value = ServiceUrls.Account.DELBYID, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据id删除账号", notes = "根据id删除账号")
    public String delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @PathVariable(value = "id") int id) {
        return accountClient.delete(id);
    }

    @RequestMapping(value = ServiceUrls.Account.ADD, method = RequestMethod.POST)
    @ApiOperation(value = "添加账号", notes = "添加账号")
    public String postAccount(
            @ApiParam(name = "name", value = "name", defaultValue = "")
            @RequestParam(value = "name") String name,
            @ApiParam(name = "password", value = "password", defaultValue = "")
            @RequestParam(value = "password") String password) {
        return accountClient.postAccount(name, password);
    }
}
