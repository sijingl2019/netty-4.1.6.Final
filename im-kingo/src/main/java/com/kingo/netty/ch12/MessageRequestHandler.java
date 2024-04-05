package com.kingo.netty.ch12;

import com.kingo.im.protocol.request.MessageRequestPacket;
import com.kingo.im.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        ctx.channel().writeAndFlush(receiveMessage(msg));
    }

    private MessageResponsePacket receiveMessage(MessageRequestPacket packet) {
        MessageResponsePacket responsePacket = new MessageResponsePacket();
        System.out.println("服务器接收消息：" + packet.getMessage());
        responsePacket.setMessage("服务器回复：" + packet.getMessage());
        return responsePacket;
    }
}
