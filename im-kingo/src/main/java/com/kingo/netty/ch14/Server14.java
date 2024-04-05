package com.kingo.netty.ch14;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

public class Server14 {
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
                        ch.pipeline().addLast(new FixedLengthFrameDecoder("this is a connect from kingo 06".length()));
                        ch.pipeline().addLast(new LifeCycleHandler());
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

    private static class LifeCycleHandler extends ChannelInboundHandlerAdapter {
        private int count = 1;
        private int eachCount = 1;
        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            System.out.println("逻辑处理器被添加：handlerAdded()");
            super.handlerAdded(ctx);
        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
            System.out.println("逻辑处理器被移除：handlerRemoved()");
            count = 1;
            eachCount = 1;
            super.handlerRemoved(ctx);
        }

        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            System.out.println("channel 绑定到线程（NioEventLoop）:channelRegistered()");
            super.channelRegistered(ctx);
        }

        @Override
        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
            System.out.println("channel 取消线程（NioEventLoop）绑定:channelUnregistered()");
            super.channelUnregistered(ctx);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("channel 准备就绪: channelActive()");
            super.channelActive(ctx);
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("channel 被关闭: channelInactive()");
            super.channelInactive(ctx);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("channel 有数据可读: channelRead()" + ": 第" + eachCount + "次");
            eachCount ++;
            super.channelRead(ctx, msg);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            System.out.println("channel 某次数据读完: channelReadComplete()" + ": 第" + count + "次");
            count++;
            super.channelReadComplete(ctx);
        }



        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            super.exceptionCaught(ctx, cause);
        }
    }
}
