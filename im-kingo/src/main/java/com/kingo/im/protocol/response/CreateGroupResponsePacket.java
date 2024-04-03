package com.kingo.im.protocol.response;

import lombok.Data;
import com.kingo.im.protocol.Packet;

import java.util.List;

import static com.kingo.im.protocol.command.Command.CREATE_GROUP_RESPONSE;

@Data
public class CreateGroupResponsePacket extends Packet {
    private boolean success;

    private String groupId;

    private List<String> userNameList;

    @Override
    public Byte getCommand() {

        return CREATE_GROUP_RESPONSE;
    }
}
