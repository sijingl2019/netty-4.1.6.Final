package com.kingo.im.protocol.response;

import lombok.Data;
import com.kingo.im.protocol.Packet;
import com.kingo.im.session.Session;

import static com.kingo.im.protocol.command.Command.GROUP_MESSAGE_RESPONSE;

@Data
public class GroupMessageResponsePacket extends Packet {

    private String fromGroupId;

    private Session fromUser;

    private String message;

    @Override
    public Byte getCommand() {

        return GROUP_MESSAGE_RESPONSE;
    }
}
