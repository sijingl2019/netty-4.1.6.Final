package com.kingo.im.client.console;

import io.netty.channel.Channel;
import com.kingo.im.protocol.request.ListGroupMembersRequestPacket;

import java.util.Scanner;

public class ListGroupMembersConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        ListGroupMembersRequestPacket listGroupMembersRequestPacket = new ListGroupMembersRequestPacket();

        System.out.print("输入 groupId，获取群成员列表：");
        String groupId = scanner.next();

        listGroupMembersRequestPacket.setGroupId(groupId);
        channel.writeAndFlush(listGroupMembersRequestPacket);
    }
}
