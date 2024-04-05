package com.kingo.netty.ch11;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Date;

public class Client10 {
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
                        ch.pipeline().addLast(new FirstClientHandler());
                    }
                });
        bootstrap.connect(new InetSocketAddress("127.0.0.1", 8080)).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功");
            } else {
                System.out.println("连接失败");
            }
        });

    }
    private static class FirstClientHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println(new Date() + ": client write data");;
            ByteBuf buf = getByteBuf(ctx);
            ctx.channel().writeAndFlush(buf);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf buf = ((ByteBuf) msg);
            System.out.println(new Date() + ": client receive data -> " + buf.toString(Charset.defaultCharset()));
        }


        private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
            ByteBuf buf = ctx.alloc().buffer();
            byte[] bytes = "this is a connect from kingo 06".getBytes(Charset.defaultCharset());
            buf.writeBytes(bytes);
            return buf;
        }
    }
}
