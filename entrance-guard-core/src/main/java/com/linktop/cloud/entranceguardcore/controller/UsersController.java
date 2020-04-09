package com.linktop.cloud.entranceguardcore.controller;


import com.linktop.cloud.commonutils.ServiceUrls;
import com.linktop.cloud.commonutils.VersionNumbers;
import com.linktop.cloud.entranceguardcore.service.UsersService;
import com.linktop.cloud.entranceguardmodel.PageWrapper;
import com.linktop.cloud.entranceguardmodel.database.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.linktop.cloud.commonutils.Common.getRandomString;

@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
public class UsersController {
    private final int USER_ID_LEN = 20;
    @Autowired
    UsersService usersService;


    @RequestMapping(value = ServiceUrls.Users.LISTALL, method = RequestMethod.GET)
    public List<User> getList() {
        return usersService.getList();
    }

    @RequestMapping(value = ServiceUrls.Users.PAGE, method = RequestMethod.GET)
    PageWrapper<User> getPage(@RequestParam(value = "page", required = true) int page,
                              @RequestParam(value = "count", required = true) int count,
                              @RequestParam(value = "filter", required = false) String filter,
                              @RequestParam(value = "black", required = false) boolean black) {
        return usersService.getPage(page, count, filter, black);
    }
    @RequestMapping(value = ServiceUrls.Users.GETBYID, method = RequestMethod.GET)
    public User getById(@RequestParam(value = "id", required = true) int id) {

        return usersService.get(id);
    }

    @RequestMapping(value = ServiceUrls.Users.UPDATEBYID, method = RequestMethod.PUT)
    public String update(@RequestBody(required = true) User user) {
        int t = usersService.update(user);
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = ServiceUrls.Users.DELBYID, method = RequestMethod.DELETE)
    public String delete(@RequestParam(value = "id", required = true) int id) {
        int t = usersService.delete(id);
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = ServiceUrls.Users.BATCHDEL, method = RequestMethod.GET)
    public String batchDel(@RequestParam(value = "ids", required = true) List<Integer> ids) {
        int t = usersService.batchDel(ids);
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = ServiceUrls.Users.ADD, method = RequestMethod.POST)
    public String add(@RequestBody(required = true) User user) {
        int t = usersService.add(user.getAliaId(),
                                 user.getBuildingNum(),
                                 user.getUnitNum(),
                                 user.getRoomNum(),
                                 user.getType(),
                                 user.getName(),
                                 user.getIdentityCardNum(),
                                 user.getGender(),
                                 user.getEstateId(),
                                 user.getDeviceIds(),
                                 user.getFnOnServer(),
                                 user.getBlackListType());
        if (t == 1) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = ServiceUrls.Users.GENUSERID, method = RequestMethod.GET)
    public String getUserId() {
        return getRandomString(USER_ID_LEN);
    }

}