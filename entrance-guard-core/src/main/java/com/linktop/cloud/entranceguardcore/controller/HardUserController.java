package com.linktop.cloud.entranceguardcore.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.linktop.cloud.commonutils.ServiceUrls;
import com.linktop.cloud.commonutils.VersionNumbers;
import com.linktop.cloud.entranceguardcore.service.HardUserService;
import com.linktop.cloud.entranceguardcore.tcp.TcpServerHandler;
import com.linktop.cloud.entranceguardcore.util.*;
import com.linktop.cloud.entranceguardmodel.ResultFront;
import com.linktop.cloud.entranceguardmodel.database.HardUser;
import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)

public class HardUserController extends TcpServerHandler {

    @Autowired
    HardUserService hardUserService;

    //添加用户
    @RequestMapping(value = ServiceUrls.HardUsers.ADD, method = RequestMethod.POST)
    public ResultFront add(@RequestBody(required = true) HardUser hardUser){
        ResultFront resultFront = new ResultFront();
        if(null != hardUser){
            hardUser.setRowId( GetTools.GetGUID() );
            hardUser = hardUserService.add( hardUser );
            if(null != hardUser){
                resultFront.setCode( 200 );
                resultFront.setMsg( "新增用户成功" );
                HttpSession session = SessionUtil.getSession();
                session.setAttribute(GlobalConstants.SESSION_USER_ID ,hardUser.getRowId()  );
                //SessionUtil.getSession().setAttribute( GlobalConstants.SESSION_USER_ID ,hardUser2.getRowId() );
                SessionUtil.getSession().setAttribute(SessionUtil.getSession().getAttribute(GlobalConstants.SESSION_USER_ID)+GlobalConstants.COMMAND_ADD,ComMand.ADDHARDUSER.getCode()  );
            }else {
                resultFront.setCode( 400 );
                resultFront.setMsg( "新增用户失败" );
            }

        }
        return resultFront;
    }

    //删除用户
    @RequestMapping(value = ServiceUrls.HardUsers.DELETE, method = RequestMethod.POST)
    public ResultFront delete(@RequestParam(value = "userCardNumber", required = true) String userCardNumber, @RequestParam(value = "cardType", required = true) String cardType) {
        Result result = new Result();
        result.setComMand( ComMand.DELETEHARDUSER.getCode() );


        List<HardUser> comdDate = new ArrayList<>();
        HardUser hardUser = new HardUser();
        hardUser.setUserCardNumber( userCardNumber );
        hardUser.setCardType( cardType );
        comdDate.add( hardUser );
        result.setComdDate( comdDate );
        SessionUtil.putUserIntoSession( "del", result );

        return null;
    }

    //查询用户
    @RequestMapping(value = ServiceUrls.HardUsers.SELECT, method = RequestMethod.POST)
    public ResultFront select(@RequestParam(value = "userCardNumber", required = true) String userCardNumber, @RequestParam(value = "cardType", required = true) String cardType) {


        return null;
    }
}


