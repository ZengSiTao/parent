package com.linktop.cloud.entranceguardcore.tcp;

/**
 * @创建人 baojielei
 * @创建时间 2020/3/16
 * @描述
 */

import com.alibaba.fastjson.JSON;


import com.linktop.cloud.entranceguardcore.util.*;
import com.linktop.cloud.entranceguardmodel.database.HardUser;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


public class TcpServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        HttpSession session = SessionUtil.getSession();
        Integer userid = (Integer) session.getAttribute(GlobalConstants.SESSION_USER_ID);
        String user_add = (String) SessionUtil.getSession().getAttribute(userid + GlobalConstants.COMMAND_ADD);
        String user_del = (String) SessionUtil.getSession().getAttribute(userid + GlobalConstants.COMMAND_DEL);

        if (null != user_add) {
            //执行添加
            HardUser hardUser = MapperUtil.getInstance().getHardUserMapper().getId(userid);
            Result result = new Result();
            result.setComMand(user_add);
            List<HardUser> comdDate = new ArrayList<>();
            comdDate.add(hardUser);
            result.setComdDate(comdDate);
            String str = JSON.toJSONString(result);
            System.out.println("客户端发送信息____________________________" + str);
            ctx.writeAndFlush(str);
            //移除此相关session
            SessionUtil.getSession().removeAttribute(GlobalConstants.SESSION_USER_ID);
            SessionUtil.getSession().removeAttribute(userid + GlobalConstants.COMMAND_ADD);

        } else if (null != user_del) {
            //执行删除
            //移除此相关session
        }
    }

//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//        System.out.println( "服务端收到信息@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + msg );
//        if (msg.toString().startsWith( "{" ) && msg.toString().endsWith( "}" )) {
//            JSONObject jsonObject = JSONObject.parseObject( (String) msg );
//            Result result2 = JSON.toJavaObject( jsonObject, Result.class );
//            System.out.println( result2.toString() );
//            List<JSONObject> userlist = result2.getComdDate();
//            if (result2.getComMand().equals( ComMand.ADDHARDUSER.getCode() )) {
//
//                for (int i = 0; i < userlist.size(); i++) {
//                    HardUser hardUser = new HardUser();
//                    hardUser.setUserCardNumber( userlist.get( i ).getString( "userCardNumber" ) );
//                    hardUser.setCardType( userlist.get( i ).getString( "cardType" ) );
//                    hardUser.setOpenDoorTimeNum( userlist.get( i ).getString( "openDoorTimeNum" ) );
//                    hardUser.setOpenDoorLevel( userlist.get( i ).getString( "openDoorLevel" ) );
//                    hardUser.setSercret1( userlist.get( i ).getString( "sercret1" ) );
//                    hardUser.setSercret2( userlist.get( i ).getString( "sercret2" ) );
//                    //ServiceUtil.getInstance().getHardwareUserService().add(hardUser.getUserCardNumber(),hardUser.getCardType(),hardUser.getOpenDoorTimeNum(),hardUser.getOpenDoorLevel()
////                                                                        ,hardUser.getSercret1(),hardUser.getSercret2());
//                    ServiceUtil.getInstance().getHardwareUserService().add( hardUser );
//                }
//            } else if (result2.getComMand().equals( "17" )) {
//
//                for (int i = 0; i < userlist.size(); i++) {
//                    HardUser hardUser = new HardUser();
//                    hardUser.setUserCardNumber( userlist.get( i ).getString( "userCardNumber" ) );
//                    hardUser.setCardType( userlist.get( i ).getString( "cardType" ) );
//
//                    int del = ServiceUtil.getInstance().getHardwareUserService().del( hardUser.getUserCardNumber(), hardUser.getCardType() );
//                    if (del > 0) {
//                        System.out.println( "删除成功" );
//                    }
//                }
//
//            }
//
//
//        }
//        try {
//            Result result = SessionUtil.getUserFromSession( "add" );
//            String string = JSON.toJSONString( result );
//            System.out.println( "服务端发送信息____________________________" + string );
//            ctx.writeAndFlush( string );
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            Result result = SessionUtil.getUserFromSession( "del" );
//            String string = JSON.toJSONString( result );
//            System.out.println( "服务端发送信息____________________________" + string );
//            ctx.writeAndFlush( string );
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }
//
//
//    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }


}