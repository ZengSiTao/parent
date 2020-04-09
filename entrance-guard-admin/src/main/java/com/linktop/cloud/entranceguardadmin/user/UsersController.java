package com.linktop.cloud.entranceguardadmin.user;

import com.linktop.cloud.commonutils.ServiceUrls;
import com.linktop.cloud.commonutils.VersionNumbers;
import com.linktop.cloud.entranceguardclient.feignclient.UsersClient;
import com.linktop.cloud.entranceguardmodel.PageWrapper;
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
public class UsersController {

    @Autowired
    private UsersClient usersClient;

    @RequestMapping(value = ServiceUrls.Users.LISTALL, method = RequestMethod.GET)
    @ApiOperation(value = "获取用户列表", notes = "获取用户列表")
    List<User> getList() {
        return usersClient.getList();
    }

    @RequestMapping(value = ServiceUrls.Users.PAGE, method = RequestMethod.GET)
    @ApiOperation(value = "获取用户列表分页", notes = "获取用户列表分页")
    PageWrapper<User> getPage(@ApiParam(name = "page", value = "第几页", defaultValue = "")
                              @RequestParam("page") int page,
                              @ApiParam(name = "count", value = "每页数量", defaultValue = "")
                              @RequestParam("count") int count,
                              @ApiParam(name = "filter", value = "查询过滤字符串", defaultValue = "")
                              @RequestParam("filter") String filter,
                              @ApiParam(name = "black", value = "查询黑名单", defaultValue = "")
                              @RequestParam(value = "black", required = false) boolean black) {
        return usersClient.getPage(page, count, filter, black);
    }

    @RequestMapping(value = ServiceUrls.Users.GETBYID, method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取用户", notes = "根据id获取用户")
    User getById(@ApiParam(name = "id", value = "数据库id", defaultValue = "")
                   @RequestParam(value = "id", required = true) int id) {
        return usersClient.getById(id);
    }

    @RequestMapping(value = ServiceUrls.Users.UPDATEBYID, method = RequestMethod.PUT)
    @ApiOperation(value = "更新用户", notes = "更新用户")
    String update(@ApiParam(name = "user", value = "用户实体", defaultValue = "")
                  @RequestBody(required = true) User user) {
        return usersClient.update(user);
    }

    @RequestMapping(value = ServiceUrls.Users.DELBYID, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据id删除用户", notes = "根据id删除用户")
    String delete(@ApiParam(name = "id", value = "id", defaultValue = "")
                  @RequestParam(value = "id", required = true) int id) {
        return usersClient.delete(id);
    }

    @RequestMapping(value = ServiceUrls.Users.BATCHDEL, method = RequestMethod.GET)
    @ApiOperation(value = "根据id删除用户", notes = "根据id删除用户")
    String batchDelete(@ApiParam(name = "ids", value = "ids", defaultValue = "")
                       @RequestParam(value = "ids", required = true) List<Integer> ids) {
        return usersClient.batchDelete(ids);
    }

    @RequestMapping(value = ServiceUrls.Users.ADD, method = RequestMethod.POST)
    @ApiOperation(value = "添加用户", notes = "添加用户")
    String add(@ApiParam(name = "user", value = "用户实体", defaultValue = "")
               @RequestBody(required = true) User user) {
        return usersClient.add(user);
    }

    @RequestMapping(value = ServiceUrls.Users.GENUSERID, method = RequestMethod.GET)
    @ApiOperation(value = "获取生成用户ID", notes = "获取生成用户ID")
    String genUserId() {
        return usersClient.genUserId();
    }
}
