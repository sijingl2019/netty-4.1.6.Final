package com.kingo.netty.ch12;

import cn.hutool.core.util.StrUtil;
import com.kingo.im.protocol.request.LoginRequestPacket;
import com.kingo.im.protocol.response.LoginResponsePacket;
import com.kingo.netty.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        ctx.channel().writeAndFlush(login(ctx, msg));
    }

    private LoginResponsePacket login(ChannelHandlerContext ctx, LoginRequestPacket packet) {
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        if (valid(packet)) {
            System.out.println("登录成功");
            loginResponsePacket.setSuccess(true);
            LoginUtil.markAsLogin(ctx.channel());
        } else {
            System.out.println("用户名或密码错误");
            loginResponsePacket.setSuccess(false);
        }
        return loginResponsePacket;
    }



    private boolean valid(LoginRequestPacket packet) {
        return true;
    }
}
