package com.kingo.netty.ch12;

import com.kingo.im.protocol.Packet;
import com.kingo.im.protocol.PacketCodec;
import com.kingo.im.protocol.request.LoginRequestPacket;
import com.kingo.im.protocol.request.MessageRequestPacket;
import com.kingo.im.protocol.response.LoginResponsePacket;
import com.kingo.im.protocol.response.MessageResponsePacket;
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

public class Client12 {

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
                        ch.pipeline().addLast(new ClientHandler());
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
                if (LoginUtil.hasLogin(channel)) {
                    System.out.println("输入消息发送服务端：");
                    Scanner sc = new Scanner(System.in);
                    String line = sc.nextLine();

                    MessageRequestPacket packet = new MessageRequestPacket();
                    packet.setMessage(line);
                    ByteBuf byteBuf = channel.alloc().buffer();
                    PacketCodec.INSTANCE.encode(byteBuf,packet);
                    channel.writeAndFlush(byteBuf);
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
            ByteBuf buf = ctx.alloc().buffer();
            PacketCodec.INSTANCE.encode(buf, loginRequestPacket);
            ctx.channel().writeAndFlush(buf);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf byteBuf = ((ByteBuf) msg);
            Packet packet = PacketCodec.INSTANCE.decode(byteBuf);
            if (packet instanceof LoginResponsePacket) {
                LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;
                if (loginResponsePacket.isSuccess()) {
                    LoginUtil.markAsLogin(ctx.channel());
                    System.out.println(new Date() + ":客户端登录成功");
                } else{
                    System.out.println(new Date() + ":客户端登录失败");
                }
            } else if (packet instanceof MessageResponsePacket) {
                MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
                System.out.println(new Date()+ ":收到服务器的回复：" + messageResponsePacket.getMessage());
            }
        }
    }
}
