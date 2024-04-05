package com.kingo.netty.ch09;

import cn.hutool.core.util.StrUtil;
import com.kingo.im.protocol.Packet;
import com.kingo.im.protocol.PacketCodec;
import com.kingo.im.protocol.request.LoginRequestPacket;
import com.kingo.im.protocol.request.MessageRequestPacket;
import com.kingo.im.protocol.response.LoginResponsePacket;
import com.kingo.im.protocol.response.MessageResponsePacket;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;

import java.util.Date;

public class Server09 {
    public static void main(String[] args) throws Exception {
        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        bootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 2048)
                .handler(new LoggingHandler())
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ServerHandler());
                    }
                });
        bootstrap.bind(8080).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("服务器启动成功");
            } else {
                System.out.println("服务器启动失败");
            }
        });
    }

    private static class ServerHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf requestBuf = (ByteBuf)msg;
            Packet packet = PacketCodec.INSTANCE.decode(requestBuf);
            if (packet instanceof LoginRequestPacket) {
                LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;

                LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
                if (valid(loginRequestPacket)) {
                    System.out.println("登录成功");
                    loginResponsePacket.setSuccess(true);
                } else {
                    System.out.println("用户名或密码错误");
                    loginResponsePacket.setSuccess(false);
                }

                ByteBuf responseBuf = ctx.alloc().buffer();
                PacketCodec.INSTANCE.encode(responseBuf, loginResponsePacket);
                ctx.channel().writeAndFlush(responseBuf);
            } else if (packet instanceof MessageRequestPacket) {
                MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet;
                System.out.println(new Date() + ":收到客户端消息：" + messageRequestPacket.getMessage());
                MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
                messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");
                ByteBuf responseByteBuf = ctx.alloc().buffer();
                PacketCodec.INSTANCE.encode(responseByteBuf, messageResponsePacket);
                ctx.channel().writeAndFlush(responseByteBuf);
            }
        }

        private boolean valid(LoginRequestPacket packet) {
            return StrUtil.equals("kingo", packet.getUserName()) && StrUtil.equals("123456", packet.getPassword());
        }
    }
}
