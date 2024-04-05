package com.kingo.netty.ch16;

import com.kingo.im.protocol.Packet;
import com.kingo.im.protocol.PacketCodec;
import com.kingo.im.protocol.request.LoginRequestPacket;
import com.kingo.im.protocol.request.MessageRequestPacket;
import com.kingo.im.protocol.response.LoginResponsePacket;
import com.kingo.im.protocol.response.MessageResponsePacket;
import com.kingo.im.util.SessionUtil;
import com.kingo.netty.ch12.PacketDecoder;
import com.kingo.netty.ch12.PacketEncoder;
import com.kingo.netty.util.LoginUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Scanner;

public class Client16 {

    public static void main(String[] args) throws Exception {
        EventLoopGroup workGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new ClientHandler());
                        ch.pipeline().addLast(new SessionLoginResponseHandler());
                        ch.pipeline().addLast(new DirectMessageResponseHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });
        bootstrap.connect(new InetSocketAddress("127.0.0.1", 8080)).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功");
                Channel channel = ((ChannelFuture)future).channel();
                startConsoleThread(channel);
            } else {
                System.out.println("连接失败");
            }
        });

    }

    private static void startConsoleThread(Channel channel) {
        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (SessionUtil.hasLogin(channel)) {
                    System.out.println("输入消息发送服务端：");
                    Scanner sc = new Scanner(System.in);
                    String userId = sc.next();
                    String message = sc.next();

                    MessageRequestPacket packet = new MessageRequestPacket();
                    packet.setToUserId(userId);
                    packet.setMessage(message);
                    channel.writeAndFlush(packet);
                }
            }
        }).start();
    }

    private static class ClientHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println(new Date() + ": 客户端开始登录");
            LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
            loginRequestPacket.setUserName("kingo");
            loginRequestPacket.setPassword("123456");
            ctx.channel().writeAndFlush(loginRequestPacket);
        }
    }
}
