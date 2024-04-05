package com.kingo.netty.ch16;

import com.kingo.im.protocol.request.LoginRequestPacket;
import com.kingo.im.protocol.response.LoginResponsePacket;
import com.kingo.im.session.Session;
import com.kingo.im.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

public class SessionLoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    private final Random random = new Random();
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        LoginResponsePacket responsePacket = new LoginResponsePacket();
        String userId = random.nextLong() + "";
        responsePacket.setUserId(userId);
        responsePacket.setSuccess(true);
        SessionUtil.bindSession(new Session(userId, msg.getUserName()), ctx.channel());

        ctx.channel().writeAndFlush(responsePacket);
    }
}
