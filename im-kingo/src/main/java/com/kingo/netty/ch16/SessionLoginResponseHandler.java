package com.kingo.netty.ch16;

import com.kingo.im.protocol.response.LoginResponsePacket;
import com.kingo.im.session.Session;
import com.kingo.im.util.SessionUtil;
import com.kingo.netty.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

public class SessionLoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
        if (msg.isSuccess()) {
            System.out.println(new Date() + ":客户端登录成功; user id: " + msg.getUserId());
            SessionUtil.bindSession(new Session(msg.getUserId(), msg.getUserName()), ctx.channel());
        } else {
            System.out.println(new Date() + ":客户端登录失败");
        }
    }
}
