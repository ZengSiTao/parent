package com.linktop.cloud.entranceguardcore.tcp;

import com.alibaba.fastjson.JSON;
import com.linktop.cloud.entranceguardcore.util.GlobalConstants;
import com.linktop.cloud.entranceguardcore.util.MapperUtil;
import com.linktop.cloud.entranceguardcore.util.Result;
import com.linktop.cloud.entranceguardcore.util.SessionUtil;
import com.linktop.cloud.entranceguardmodel.database.DoorType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class TcpServiceDoorType extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        HttpSession session = SessionUtil.getSession();
        String rowId = (String) session.getAttribute(GlobalConstants.SESSION_USER_ID);
        String user_add = (String) SessionUtil.getSession().getAttribute(rowId + GlobalConstants.COMMAND_ADD);
        String user_del = (String) SessionUtil.getSession().getAttribute(rowId + GlobalConstants.COMMAND_DEL);

        if (null != user_add) {
            //执行添加
            DoorType doorType = MapperUtil.getInstance().getDoorTypeMapper().getRowId(rowId);
            Result result = new Result();
            result.setComMand(user_add);
            List<DoorType> list = new ArrayList<>();
            list.add(doorType);
            result.setComdDate(list);
            String str = JSON.toJSONString(result);
            System.out.println("客户端发送信息____________________________" + str);
            ctx.writeAndFlush(str);
            //移除此相关session
            SessionUtil.getSession().removeAttribute(GlobalConstants.SESSION_USER_ID);
            SessionUtil.getSession().removeAttribute(rowId + GlobalConstants.COMMAND_ADD);

        }else if (null != user_del) {
            //执行删除
            //移除此相关session
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }

}
