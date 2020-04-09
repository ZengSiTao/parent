package com.linktop.cloud.entranceguardcore.controller;

import com.linktop.cloud.commonutils.ServiceUrls;
import com.linktop.cloud.commonutils.VersionNumbers;
import com.linktop.cloud.entranceguardcore.service.DoorTypeService;
import com.linktop.cloud.entranceguardcore.tcp.TcpServiceDoorType;
import com.linktop.cloud.entranceguardcore.util.ComMand;
import com.linktop.cloud.entranceguardcore.util.GetTools;
import com.linktop.cloud.entranceguardcore.util.GlobalConstants;
import com.linktop.cloud.entranceguardcore.util.SessionUtil;
import com.linktop.cloud.entranceguardmodel.ResultFront;
import com.linktop.cloud.entranceguardmodel.database.DoorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
public class DoorTypeController extends TcpServiceDoorType {

    @Autowired
    DoorTypeService doorTypeService;

    @RequestMapping(value = ServiceUrls.DoorType.ADDDOORTYPE, method = RequestMethod.POST)
    public ResultFront addDoorType(@RequestBody(required = true) DoorType doorType){
        ResultFront resultFront = new ResultFront();
        if(null != doorType){
            doorType.setRowId( GetTools.GetGUID() );
            doorType = doorTypeService.addDoorType( doorType );
            if(null != doorType){
                resultFront.setCode( 200 );
                resultFront.setMsg( "新增门禁类型成功" );
                HttpSession session = SessionUtil.getSession();
                session.setAttribute(GlobalConstants.SESSION_USER_ID ,doorType.getRowId());
                SessionUtil.getSession().setAttribute(SessionUtil.getSession().
                        getAttribute(GlobalConstants.SESSION_USER_ID)+GlobalConstants.
                        COMMAND_ADD, ComMand.ADDENTRANCEGUARDTYPE.getCode()  );
            }else {
                resultFront.setCode( 400 );
                resultFront.setMsg( "新增门禁类型失败" );
            }

        }
        return resultFront;
    }
}
