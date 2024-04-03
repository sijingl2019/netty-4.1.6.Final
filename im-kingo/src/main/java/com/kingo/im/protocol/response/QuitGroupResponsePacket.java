package com.kingo.im.protocol.response;

import lombok.Data;
import com.kingo.im.protocol.Packet;

import static com.kingo.im.protocol.command.Command.QUIT_GROUP_RESPONSE;

@Data
public class QuitGroupResponsePacket extends Packet {

    private String groupId;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {

        return QUIT_GROUP_RESPONSE;
    }
}
