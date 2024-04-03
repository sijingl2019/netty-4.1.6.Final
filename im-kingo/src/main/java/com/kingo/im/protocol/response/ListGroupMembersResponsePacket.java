package com.kingo.im.protocol.response;

import lombok.Data;
import com.kingo.im.protocol.Packet;
import com.kingo.im.session.Session;

import java.util.List;

import static com.kingo.im.protocol.command.Command.LIST_GROUP_MEMBERS_RESPONSE;

@Data
public class ListGroupMembersResponsePacket extends Packet {

    private String groupId;

    private List<Session> sessionList;

    @Override
    public Byte getCommand() {

        return LIST_GROUP_MEMBERS_RESPONSE;
    }
}
